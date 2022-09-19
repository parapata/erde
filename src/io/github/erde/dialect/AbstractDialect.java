package io.github.erde.dialect;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import io.github.erde.core.util.StringUtils;
import io.github.erde.dialect.type.IColumnType;
import io.github.erde.dialect.type.IIndexType;
import io.github.erde.dialect.type.IndexType;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.RelationshipModel;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;

/**
 * AbstractDialect.
 *
 * @author modified by parapata
 */
public abstract class AbstractDialect implements IDialect {

    private List<IColumnType> types;

    private boolean schema;
    private boolean drop;
    private boolean alterTable;
    private boolean comment;
    private String separator = ";";
    private String lineSeparator = System.lineSeparator();

    private List<IIndexType> indexTypes = Arrays.asList(IndexType.values());

    private PrintWriter writer;

    @Override
    public void createDDL(RootModel root, PrintWriter writer) {
        this.writer = writer;

        createDDL(root, root.getTables(), writer);
    }

    @Override
    public void createDDL(RootModel root, List<TableModel> tables, PrintWriter writer) {
        this.writer = writer;

        if (isDrop()) {
            // reverse
            List<TableModel> reverses = new ArrayList<>(tables);
            Collections.reverse(reverses);
//            for (ListIterator<TableModel> iterator = tables.listIterator(tables.size()); iterator.hasPrevious();) {
//                reverses.add(iterator.previous());
//            }
            dropDDL(root, reverses);
        }
        createDDL(root, tables);
    }

    protected void createDDL(RootModel root, List<TableModel> tables) {
        AtomicInteger count = new AtomicInteger();
        tables.forEach(table -> {
            if (count.get() > 0) {
                newLine();
            }
            createTableDDL(root, table);
            count.incrementAndGet();
        });
        reset(count);

        if (isAlterTable()) {
            tables.forEach(table -> {
                count.set(createPrimaryKey(table));
                reset(count);
            });

            tables.forEach(table -> {
                count.set(createForeignKey(root, table));
                reset(count);
            });
        }

        tables.forEach(table -> {
            int res = createIndex(root, table);
            count.set(count.get() + res);
        });
        reset(count);

        tables.forEach(table -> {
            int res = createUniqueIndex(root, table);
            count.set(count.get() + res);
        });
        reset(count);

        additions(root);
    }

    protected void dropDDL(RootModel root, List<TableModel> tables) {
        AtomicInteger count = new AtomicInteger();
        tables.forEach(table -> {
            String tableName = getTableName(root, table);
            table.getIndices().forEach(index -> {
                print(dropIndexDDL(tableName, index.getIndexName()));
                println(getSeparator());
                count.incrementAndGet();
            });
        });
        reset(count);

        tables.forEach(table -> {
            String tableName = getTableName(root, table);
            print(dropTableDDL(tableName));
            println(getSeparator());
            count.incrementAndGet();
        });
        reset(count);
    }

    protected String dropTableDDL(String tableName) {
        return String.format("DROP TABLE IF EXISTS %s", tableName);
    }

    protected String dropIndexDDL(String tableName, String indexName) {
        return String.format("DROP INDEX %s ON %s", indexName, tableName);
    }

    protected void createTableDDL(RootModel root, TableModel table) {

        println(String.format("CREATE TABLE %s (", getTableName(root, table)));

        // column part
        AtomicInteger count = new AtomicInteger();
        table.getColumns().forEach(column -> {
            print(TAB_SPACE);
            String str = createColumnPart(column);
            if (count.getAndIncrement() == 0) {
                println(str);
            } else {
                println(String.format(", %s", str));
            }
        });

        if (!isAlterTable()) {
            // primaryKey part
            List<String> primaryKeyNames = Arrays.asList(table.getPrimaryKeyColumns())
                    .stream()
                    .map(column -> column.getPhysicalName())
                    .collect(Collectors.toList());

            if (!primaryKeyNames.isEmpty()) {
                print(TAB_SPACE);
                print(", PRIMARY KEY");
                println(String.format(" (%s)", String.join(", ", primaryKeyNames)));
            }

            // foreignKey part
            createForeignKey(root, table);
        }

        print(")");
        setupTableOption(root, table);
    }

    protected void setupTableOption(RootModel root, TableModel table) {
        println(getSeparator());
    }

    protected int createPrimaryKey(TableModel table) {
        AtomicInteger count = new AtomicInteger();
        List<String> primaryKeyNames = Arrays.asList(table.getPrimaryKeyColumns())
                .stream()
                .map(column -> column.getPhysicalName())
                .collect(Collectors.toList());
        if (!primaryKeyNames.isEmpty()) {
            println(String.format("ALTER TABLE %s ADD PRIMARY KEY (%s);",
                    table.getPhysicalName(),
                    String.join(", ", primaryKeyNames)));
            count.incrementAndGet();
        }
        return count.get();
    }

    protected int createForeignKey(RootModel root, TableModel table) {
        AtomicInteger count = new AtomicInteger();
        table.getModelTargetConnections()
                .stream()
                .filter(predicate -> predicate instanceof RelationshipModel)
                .forEach(conn -> {
                    RelationshipModel fk = (RelationshipModel) conn;
                    if (fk.getMappings().stream().anyMatch(
                            predicate -> predicate.getReferenceKey() != null && predicate.getForeignKey() != null)) {
                        TableModel source = (TableModel) conn.getSource();
                        String sourceTableName = source.getPhysicalName();
                        String targetTableName = getTableName(root, table);

                        List<String> refkeys = fk.getMappings()
                                .stream()
                                .map(mapping -> mapping.getReferenceKey().getPhysicalName())
                                .collect(Collectors.toList());

                        List<String> fkeys = fk.getMappings()
                                .stream()
                                .map(mapping -> mapping.getForeignKey().getPhysicalName())
                                .collect(Collectors.toList());

                        if (count.get() > 0) {
                            newLine();
                        }
                        if (isAlterTable()) {
                            println(String.format("ALTER TABLE %s", targetTableName));
                        }
                        print(TAB_SPACE);
                        if (isAlterTable()) {
                            println(String.format("ADD CONSTRAINT FK_%s", StringUtils.upperCase(sourceTableName)));
                        } else {
                            println(String.format("CONSTRAINT FK_%s", StringUtils.upperCase(sourceTableName)));
                        }
                        print(TAB_SPACE);
                        print("FOREIGN KEY");
                        println(String.format(" (%s)", String.join(", ", fkeys)));
                        print(TAB_SPACE);
                        print(String.format("REFERENCES %s (%s)", sourceTableName, String.join(", ", refkeys)));
                        if (StringUtils.isNotEmpty(fk.getOnUpdateOption())) {
                            newLine();
                            print(TAB_SPACE);
                            print(String.format("ON UPDATE %s", fk.getOnUpdateOption()));
                        }
                        if (StringUtils.isNotEmpty(fk.getOnDeleteOption())) {
                            newLine();
                            print(TAB_SPACE);
                            print(String.format("ON DELETE %s", fk.getOnDeleteOption()));
                        }
                        if (isAlterTable()) {
                            println(getSeparator());
                        } else {
                            newLine();
                        }
                        count.incrementAndGet();
                    }
                });
        return count.get();
    }

    protected int createIndex(RootModel root, TableModel table) {
        AtomicInteger count = new AtomicInteger();
        table.getIndices()
                .stream()
                .filter(predicate -> "INDEX".equals(predicate.getIndexType().getName()))
                .forEach(index -> {
                    String str = String.format("CREATE INDEX %s ON %s (%s)",
                            index.getIndexName(),
                            getTableName(root, table),
                            String.join(", ", index.getColumns()));
                    print(str);
                    println(getSeparator());
                    count.incrementAndGet();
                });
        return count.get();
    }

    protected int createUniqueIndex(RootModel root, TableModel table) {
        AtomicInteger count = new AtomicInteger();
        table.getIndices()
                .stream()
                .filter(predicate -> "UNIQUE".equals(predicate.getIndexType().getName()))
                .forEach(index -> {
                    String str = String.format("CONSTRAINT %s UNIQUE (%s)",
                            index.getIndexName(),
                            String.join(", ", index.getColumns()));

                    print(String.format("ALTER TABLE %s ADD %s", getTableName(root, table), str));
                    println(getSeparator());
                    count.incrementAndGet();
                });
        return count.get();
    }

    protected void additions(RootModel root) {

        // Table Comments
        if (isComment()) {
            AtomicInteger count = new AtomicInteger(0);
            TableDependencyCalculator.getSortedTable(root.getTables()).forEach(table -> {
                if (count.getAndIncrement() > 1) {
                    newLine();
                }
                print(String.format("COMMENT ON TABLE %s IS '%s'",
                        table.getPhysicalName(),
                        table.getLogicalName()));
                println(getSeparator());

                table.getColumns().forEach(column -> {
                    print(String.format("COMMENT ON COLUMN %s.%s IS '%s'",
                            table.getPhysicalName(),
                            column.getPhysicalName(),
                            column.getLogicalName()));
                    println(getSeparator());
                });
            });
        }
    }

    protected String createColumnPart(ColumnModel column) {

        StringBuilder sb = new StringBuilder();
        sb.append(column.getPhysicalName())
                .append(SPACE)
                .append(column.getColumnType().getPhysicalName());

        if (column.getColumnType().isEnum()) {
            if (!column.getEnumNames().isEmpty()) {
                List<String> enumNemaes = new LinkedList<>();
                column.getEnumNames().forEach(enumName -> {
                    enumNemaes.add(String.format("'%s'", enumName));
                });
                sb.append(String.format("(%s)", String.join(",", enumNemaes)));
            }
        } else if (column.getColumnType().isSizeSupported() && column.getColumnSize() != null) {
            if (column.getDecimal() == null) {
                sb.append(String.format("(%d)", column.getColumnSize()));
            } else {
                sb.append(String.format("(%d, %d)", column.getColumnSize(), column.getDecimal()));
            }
        }
        if (column.getColumnType().isUnsignedSupported() && column.isUnsigned()) {
            sb.append(" UNSIGNED");
        }
        if (column.isNotNull()) {
            sb.append(" NOT NULL");
        }
        if (column.isUniqueKey()) {
            sb.append(" UNIQUE");
        }
        if (StringUtils.isNotEmpty(column.getDefaultValue())) {
            sb.append(" DEFAULT ").append(column.getDefaultValue());
        }
        return sb.toString();
    }

    protected void reset(AtomicInteger count) {
        if (count.intValue() > 0) {
            newLine();
            count.set(0);
        }
    }

    protected void print(String str) {
        writer.print(str);
    }

    protected void println(String str) {
        writer.print(str + getLineSeparator());
    }

    protected void newLine() {
        writer.print(getLineSeparator());
    }

    public AbstractDialect(List<IColumnType> types) {
        this.types = types;
    }

    @Override
    public List<IColumnType> getColumnTypes() {
        return types;
    }

    @Override
    public List<IIndexType> getIndexTypes() {
        return indexTypes;
    }

    @Override
    public boolean isAutoIncrement() {
        return false;
    }

    @Override
    public String getSeparator() {
        return separator;
    }

    @Override
    public void setSeparator(String separator) {
        this.separator = separator;
    }

    @Override
    public boolean isSchema() {
        return schema;
    }

    @Override
    public void setSchema(boolean schema) {
        this.schema = schema;
    }

    @Override
    public boolean isDrop() {
        return drop;
    }

    @Override
    public void setDrop(boolean drop) {
        this.drop = drop;
    }

    @Override
    public boolean isAlterTable() {
        return alterTable;
    }

    @Override
    public void setAlterTable(boolean alterTable) {
        this.alterTable = alterTable;
    }

    @Override
    public boolean isComment() {
        return comment;
    }

    @Override
    public void setComment(boolean comment) {
        this.comment = comment;
    }

    @Override
    public String getLineSeparator() {
        return lineSeparator;
    }

    @Override
    public void setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }
}
