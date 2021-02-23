package io.github.erde.dialect;

import java.io.PrintWriter;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import io.github.erde.core.util.JDBCConnection;
import io.github.erde.dialect.loader.DefaultSchemaLoader;
import io.github.erde.dialect.loader.ISchemaLoader;
import io.github.erde.dialect.type.IColumnType;
import io.github.erde.dialect.type.IIndexType;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.RelationshipModel;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;
import io.github.erde.editor.validator.DiagramErrorManager;

/**
 * IDialect.
 *
 * @author modified by parapata
 */
public interface IDialect {

    String SPACE = new String(Character.toChars(0x20));
    String TAB_SPACE = SPACE + SPACE + SPACE + SPACE;

    /**
     * Get Dialect provider.
     *
     * @return Diarect provider
     */
    DialectProvider getDialectProvider();

    /**
     * Creates DDL from a given model.
     *
     * @param root a root model of diagram
     * @param writer DDLs
     * @return DDL that creates all tables
     */
    default void createDDL(RootModel root, PrintWriter writer) {
        List<TableModel> tables = TableDependencyCalculator.getSortedTable(root);
        if (isDrop()) {
            // reverse
            List<TableModel> reverses = new ArrayList<>();
            for (ListIterator<TableModel> i = tables.listIterator(tables.size()); i.hasPrevious();) {
                reverses.add(i.previous());
            }
            dropDDL(root, reverses, writer);
        }
        createDDL(root, tables, writer);
    }

    private void dropDDL(RootModel root, List<TableModel> tables, PrintWriter writer) {
        AtomicInteger count = new AtomicInteger();
        tables.forEach(table -> {
            String tableName = getTableName(root, table);
            table.getIndices().forEach(index -> {
                writer.print(dropIndexDDL(tableName, index.getIndexName()));
                writer.println(getSeparator());
                count.incrementAndGet();
            });
        });
        reset(count, writer);

        tables.forEach(table -> {
            String tableName = getTableName(root, table);
            writer.print(dropTableDDL(tableName));
            writer.println(getSeparator());
            count.incrementAndGet();
        });
        reset(count, writer);
    }

    private void createDDL(RootModel root, List<TableModel> tables, PrintWriter writer) {
        AtomicInteger count = new AtomicInteger();
        tables.forEach(table -> {
            if (count.get() > 0) {
                writer.println();
            }
            createTableDDL(root, table, writer);
            count.incrementAndGet();
        });
        reset(count, writer);

        if (isAlterTable()) {
            tables.forEach(table -> {
                count.set(createForeignKey(root, table, writer));
                reset(count, writer);
            });
        }

        tables.forEach(table -> {
            int res = createIndex(root, table, writer);
            count.set(count.get() + res);
        });
        reset(count, writer);

        tables.forEach(table -> {
            int res = createUniqueIndex(root, table, writer);
            count.set(count.get() + res);
        });
        reset(count, writer);

        additions(root, writer);
    }

    default String dropTableDDL(String tableName) {
        return String.format("DROP TABLE IF EXISTS %s", tableName);
    }

    default String dropIndexDDL(String tableName, String indexName) {
        return String.format("DROP INDEX %s ON %s", indexName, tableName);
    }

    /**
     * Creates DDL that creates a given table.
     *
     * @param root a root model of diagram
     * @param model a table model
     * @param ddl DDLs
     */
    default void createTableDDL(RootModel root, TableModel table, PrintWriter writer) {

        writer.println(String.format("CREATE TABLE %s (", getTableName(root, table)));

        // column part
        AtomicInteger count = new AtomicInteger();
        table.getColumns().forEach(column -> {
            writer.print(TAB_SPACE);
            String str = createColumnPart(column);
            if (count.getAndIncrement() == 0) {
                writer.println(str);
            } else {
                writer.println(String.format(", %s", str));
            }
        });

        // primaryKey part
        List<String> primaryKeyNames = Arrays.asList(table.getPrimaryKeyColumns())
                .stream()
                .map(column -> column.getPhysicalName())
                .collect(Collectors.toList());
        if (isAlterTable() && !primaryKeyNames.isEmpty()) {
            writer.print(TAB_SPACE);
            writer.println(String.format("PRIMARY KEY (%s)", String.join(", ", primaryKeyNames)));
        }

        writer.print(")");
        setupTableOption(root, table, writer);
    }

    default int createForeignKey(RootModel root, TableModel table, PrintWriter writer) {
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
                            writer.println();
                        }
                        writer.println(String.format("ALTER TABLE %s", targetTableName));
                        writer.print(TAB_SPACE);
                        writer.println(String.format("ADD CONSTRAINT FK_%s", StringUtils.upperCase(sourceTableName)));
                        writer.print(TAB_SPACE);
                        writer.println(String.format("FOREIGN KEY (%s)", String.join(", ", fkeys)));
                        writer.print(TAB_SPACE);
                        writer.print(String.format("REFERENCES %s (%s)", sourceTableName, String.join(", ", refkeys)));
                        if (StringUtils.isNotEmpty(fk.getOnUpdateOption())) {
                            writer.println();
                            writer.print(TAB_SPACE);
                            writer.print(String.format("ON UPDATE %s", fk.getOnUpdateOption()));
                        }
                        if (StringUtils.isNotEmpty(fk.getOnDeleteOption())) {
                            writer.println();
                            writer.print(TAB_SPACE);
                            writer.print(String.format("ON DELETE %s", fk.getOnDeleteOption()));
                        }
                        writer.println(getSeparator());
                        count.incrementAndGet();
                    }
                });
        return count.get();
    }

    default int createIndex(RootModel root, TableModel table, PrintWriter writer) {
        AtomicInteger count = new AtomicInteger();
        table.getIndices()
                .stream()
                .filter(predicate -> "INDEX".equals(predicate.getIndexType().getName()))
                .forEach(index -> {
                    String str = String.format("CREATE INDEX %s ON %s (%s)",
                            index.getIndexName(),
                            getTableName(root, table),
                            String.join(", ", index.getColumns()));
                    writer.print(str);
                    writer.println(getSeparator());
                    count.incrementAndGet();
                });
        return count.get();
    }

    default int createUniqueIndex(RootModel root, TableModel table, PrintWriter writer) {
        AtomicInteger count = new AtomicInteger();
        table.getIndices()
                .stream()
                .filter(predicate -> "UNIQUE".equals(predicate.getIndexType().getName()))
                .forEach(index -> {
                    String str = String.format("CONSTRAINT %s UNIQUE (%s)",
                            index.getIndexName(),
                            String.join(", ", index.getColumns()));

                    writer.print(String.format("ALTER TABLE %s ADD %s", getTableName(root, table), str));
                    writer.println(getSeparator());
                    count.incrementAndGet();
                });
        return count.get();
    }

    default void additions(RootModel root, PrintWriter writer) {
        return;
    }

    default String createColumnPart(ColumnModel column) {

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

    /**
     * Returns SQL which selects all columns of a given table to get table metadata for reverse
     * engineering.
     *
     * @param tableName a table name
     * @return SQL which selects all columns of a given table
     */
    default String getColumnMetadataSQL(String tableName) {
        return String.format("SELECT * FROM %s", tableName);
    }

    default String getEnumMetadataSQL() {
        return "";
    }

    /**
     * .
     *
     * @param root
     * @param table
     * @return
     */
    default String getTableName(RootModel root, TableModel table) {
        if (isSchema() && StringUtils.isNotEmpty(root.getJdbcSchema())) {
            return String.format("%s.%s", root.getJdbcSchema(), table.getPhysicalName());
        } else {
            return table.getPhysicalName();
        }
    }

    /**
     * .
     *
     * @param root
     * @param model
     * @param ddl
     * @param additions
     */
    default void setupTableOption(RootModel root, TableModel table, PrintWriter writer) {
        writer.println(getSeparator());
    }

    /**
     * Validates diagram models.
     *
     * @param validation errors
     * @param model the root model of the diagram
     */
    default void validate(DiagramErrorManager errors, RootModel root) {
    }

    /**
     * Returns an implementation of <code>ISchemaLoader</code> that used for reverse engineering.
     *
     * @return an implementation of <code>ISchemaLoader</code>
     */
    default ISchemaLoader getSchemaLoader(JDBCConnection jdbcConn) {
        return new DefaultSchemaLoader(this, jdbcConn);
    }

    /**
     * Returns a column type from a given SQL type.
     * <p>
     * If corresponded column type is not found, this method returns null.
     *
     * @param sqlType a sql type which is defined by {@link java.sql.Types}
     * @return a column type
     */
    default IColumnType getColumnType(int sqlType) {
        for (IColumnType columnType : getColumnTypes()) {
            if (sqlType == columnType.getType()) {
                return columnType;
            }
        }
        return null;
    }

    /**
     * Returns a column type from a given type name.
     * <p>
     * If corresponded column type is not found, this method returns null.
     *
     * @param typeName a type name
     * @return a column type
     */
    default IColumnType getColumnType(String typeName) {
        for (IColumnType type : getColumnTypes()) {
            if (type.getPhysicalName().toUpperCase().equals(typeName.toUpperCase())) {
                return type;
            }
        }
        return null;
    }

    /**
     * Returns a default column type.
     *
     * @return a default column type
     */
    default IColumnType getDefaultColumnType() {
        List<IColumnType> types = getColumnTypes();
        for (IColumnType columnType : types) {
            if (columnType.getType() == Types.INTEGER) {
                return columnType;
            }
        }
        return types.get(0);
    }

    /**
     * Returns a index type from a given type name.
     *
     * @param typeName a type name
     * @return a index type
     */
    default IIndexType getIndexType(String typeName) {
        for (IIndexType indexType : getIndexTypes()) {
            if (indexType.getName().equals(typeName)) {
                return indexType;
            }
        }
        return null;
    }

    /**
     * Returns a default index type.
     *
     * @return a default index type
     */
    default IIndexType getDefaultIndexType() {
        return getIndexTypes().get(0);
    }

    default void reset(AtomicInteger count, PrintWriter writer) {
        if (count.intValue() > 0) {
            writer.println();
            count.set(0);
        }
    }

    /**
     * Returns supported column types.
     *
     * @return supported column types
     */
    List<IColumnType> getColumnTypes();

    /**
     * Returns supported index types.
     *
     * @return supported index types
     */
    List<IIndexType> getIndexTypes();

    boolean isAutoIncrement();

    void setAutoIncrement(boolean autoIncrement);

    String getSeparator();

    void setSeparator(String separator);

    String getLineSeparator();

    void setLineSeparator(String lineSeparator);

    boolean isSchema();

    void setSchema(boolean schema);

    boolean isDrop();

    void setDrop(boolean drop);

    boolean isAlterTable();

    void setAlterTable(boolean alterTable);

    boolean isComment();

    void setComment(boolean comment);
}
