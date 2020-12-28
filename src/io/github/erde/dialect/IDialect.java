package io.github.erde.dialect;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import io.github.erde.core.util.JDBCConnection;
import io.github.erde.dialect.loader.DefaultSchemaLoader;
import io.github.erde.dialect.loader.ISchemaLoader;
import io.github.erde.dialect.type.IColumnType;
import io.github.erde.dialect.type.IIndexType;
import io.github.erde.editor.diagram.model.BaseConnectionModel;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.IndexModel;
import io.github.erde.editor.diagram.model.RelationshipMappingModel;
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

    private static Map<String, Integer> createMap() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("TINYINT", Types.TINYINT);
        map.put("SMALLINT", Types.SMALLINT);
        map.put("INTEGER", Types.INTEGER);
        map.put("BIGINT", Types.BIGINT);
        map.put("FLOAT", Types.FLOAT);
        map.put("REAL", Types.REAL);
        map.put("DOUBLE", Types.DOUBLE);
        map.put("NUMERIC", Types.NUMERIC);
        map.put("DECIMAL", Types.DECIMAL);
        map.put("CHAR", Types.CHAR);
        map.put("VARCHAR", Types.VARCHAR);
        map.put("LONGVARCHAR", Types.LONGVARCHAR);
        map.put("DATE", Types.DATE);
        map.put("TIME", Types.TIME);
        map.put("TIMESTAMP", Types.TIMESTAMP);
        map.put("BINARY", Types.BINARY);
        map.put("VARBINARY", Types.VARBINARY);
        map.put("LONGVARBINARY", Types.LONGVARBINARY);
        map.put("NULL", Types.NULL);
        map.put("OTHER", Types.OTHER);
        map.put("JAVA_OBJECT", Types.JAVA_OBJECT);
        map.put("DISTINCT", Types.DISTINCT);
        map.put("STRUCT", Types.STRUCT);
        map.put("BLOB", Types.BLOB);
        map.put("CLOB", Types.CLOB);
        map.put("DATALINK", Types.DATALINK);
        map.put("BOOLEAN", Types.BOOLEAN);
        map.put("ROWID", Types.ROWID);
        map.put("NCHAR", Types.NCHAR);
        map.put("NVARCHAR", Types.NVARCHAR);
        map.put("LONGNVARCHAR", Types.LONGNVARCHAR);
        map.put("NCLOB", Types.NCLOB);
        map.put("SQLXML", Types.SQLXML);
        map.put("REF_CURSOR", Types.REF_CURSOR);
        map.put("TIME_WITH_TIMEZONE", Types.TIME_WITH_TIMEZONE);
        map.put("TIMESTAMP_WITH_TIMEZONE", Types.TIMESTAMP_WITH_TIMEZONE);
        return Collections.unmodifiableMap(map);
    }

    String LS = System.getProperty("line.separator");
    String SPACE = new String(Character.toChars(0x20));
    String TAB_SPACE = SPACE + SPACE + SPACE + SPACE;

    Map<String, Integer> CONST_MAP = createMap();

    /**
     * Creates DDL from a given model.
     *
     * @param root a root model of diagram
     * @param ddl DDLs
     * @return DDL that creates all tables
     */
    default void createDDL(RootModel root, StringBuilder ddl) {

        List<TableModel> tables = TableDependencyCalculator.getSortedTable(root);
        StringBuilder dorpIndexDDL = new StringBuilder();
        StringBuilder dorpTableDDL = new StringBuilder();
        if (isDrop()) {
            for (int i = tables.size() - 1; i >= 0; i--) {
                TableModel table = tables.get(i);
                String tableName = getTableName(root, table);
                table.getIndices().forEach(index -> {
                    if (dorpIndexDDL.length() > 0) {
                        dorpIndexDDL.append(LS);
                    }
                    dorpIndexDDL.append(dropIndexDDL(tableName, index.getIndexName()));
                });
                if (dorpTableDDL.length() > 0) {
                    dorpTableDDL.append(LS);
                }
                dorpTableDDL.append(dropTableDDL(tableName));
            }
        }

        if (dorpIndexDDL.length() > 0) {
            ddl.append(dorpIndexDDL);
            ddl.append(LS);
        }

        if (dorpTableDDL.length() > 0) {
            if (dorpIndexDDL.length() > 0) {
                ddl.append(LS);
            }
            ddl.append(dorpTableDDL);
        }

        if (ddl.length() > 0) {
            ddl.append(LS);
        }

        StringBuilder additions = new StringBuilder();
        for (TableModel table : tables) {
            createTableDDL(root, table, ddl, additions);
        }

        for (TableModel table : tables) {
            createForeignKey(root, table, additions);
        }

        for (TableModel table : tables) {
            createIndex(root, table, additions);
        }

        for (TableModel table : tables) {
            createUniqueIndex(root, table, additions);
        }

        if (additions.length() > 0) {
            ddl.append(additions);
        }
    }

    default String dropTableDDL(String tableName) {
        return String.format("DROP TABLE IF EXISTS %s;", tableName);
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
     * @param additions additional DDLs
     */
    default void createTableDDL(RootModel root, TableModel model, StringBuilder ddl, StringBuilder additions) {

        List<String> columnBufs = new ArrayList<>();
        StringBuilder buf = null;
        for (ColumnModel column : model.getColumns()) {
            buf = new StringBuilder();
            buf.append(LS);
            buf.append(TAB_SPACE);
            createColumnDDL(root, model, column, buf, additions);
            columnBufs.add(buf.toString());
        }

        List<String> primaryKeyNames = new ArrayList<>();
        for (ColumnModel column : model.getPrimaryKeyColumns()) {
            primaryKeyNames.add(column.getPhysicalName());
        }
        if (!primaryKeyNames.isEmpty()) {
            buf = new StringBuilder();
            buf.append(LS);
            buf.append(TAB_SPACE);
            buf.append(String.format("PRIMARY KEY (%s)", String.join(", ", primaryKeyNames)));
            columnBufs.add(buf.toString());
        }

        if (ddl.length() > 0) {
            ddl.append(LS);
        }
        ddl.append(String.format("CREATE TABLE %s (", getTableName(root, model)));
        ddl.append(String.join(", ", columnBufs)).append(LS);
        ddl.append(")");
        setupTableOption(root, model, ddl, additions);
    }

    default void createForeignKey(RootModel root, TableModel model, StringBuilder additions) {
        for (BaseConnectionModel conn : model.getModelSourceConnections()) {

            if (conn instanceof RelationshipModel) {

                RelationshipModel fk = (RelationshipModel) conn;

                if (fk.getMappings().stream().anyMatch(
                        predicate -> predicate.getReferenceKey() == null || predicate.getForeignKey() == null)) {
                    continue;
                }

                TableModel source = (TableModel) conn.getSource();
                String targetTableName = getTableName(root, model);

                List<String> refkeys = new ArrayList<>();
                for (RelationshipMappingModel mapping : fk.getMappings()) {
                    refkeys.add(mapping.getReferenceKey().getPhysicalName());
                }

                List<String> fkeys = new ArrayList<>();
                for (RelationshipMappingModel mapping : fk.getMappings()) {
                    fkeys.add(mapping.getForeignKey().getPhysicalName());
                }

                additions.append(LS);
                additions.append(String.format("ALTER TABLE %s", targetTableName));
                additions.append(LS);
                additions.append(TAB_SPACE);
                additions.append(String.format("ADD CONSTRAINT FK_%s", StringUtils.upperCase(targetTableName)));
                additions.append(LS);
                additions.append(TAB_SPACE);
                additions.append(String.format("FOREIGN KEY (%s)", String.join(", ", fkeys)));
                additions.append(LS);
                additions.append(TAB_SPACE);
                additions.append(String.format("REFERENCES %s (%s)",
                        source.getPhysicalName(),
                        String.join(", ", refkeys)));
                if (StringUtils.isNotEmpty(fk.getOnUpdateOption())) {
                    additions.append(LS);
                    additions.append(TAB_SPACE);
                    additions.append(String.format("ON UPDATE %s", fk.getOnUpdateOption()));
                }
                if (StringUtils.isNotEmpty(fk.getOnDeleteOption())) {
                    additions.append(LS);
                    additions.append(TAB_SPACE);
                    additions.append(String.format("ON DELETE %s", fk.getOnDeleteOption()));
                }
                additions.append(getSeparator()).append(LS);
            }
        }
    }

    default void createIndex(RootModel root, TableModel model, StringBuilder additions) {
        for (IndexModel indexModel : model.getIndices()) {
            if ("INDEX".equals(indexModel.getIndexType().getName())) {
                String str = String.format("CREATE INDEX %s ON %s (%s)",
                        indexModel.getIndexName(),
                        getTableName(root, model),
                        String.join(", ", indexModel.getColumns()));

                additions.append(LS).append(str);
                additions.append(getSeparator());
            }
        }
    }

    default void createUniqueIndex(RootModel root, TableModel model, StringBuilder additions) {
        for (IndexModel indexModel : model.getIndices()) {
            if ("UNIQUE".equals(indexModel.getIndexType().getName())) {
                String str = String.format("CONSTRAINT %s UNIQUE (%s)",
                        indexModel.getIndexName(),
                        String.join(", ", indexModel.getColumns()));

                additions.append(LS);
                additions.append(String.format("ALTER TABLE %s ADD %s", getTableName(root, model), str));
                additions.append(getSeparator());
            }
        }
    }

    default void createColumnDDL(RootModel root, TableModel tableModel, ColumnModel columnModel,
            StringBuilder ddl, StringBuilder additions) {

        ddl.append(columnModel.getPhysicalName())
                .append(SPACE)
                .append(columnModel.getColumnType().getPhysicalName());

        if (columnModel.getColumnType().isSizeSupported() && columnModel.getColumnSize() != null) {
            if (columnModel.getDecimal() == null) {
                ddl.append(String.format("(%d)", columnModel.getColumnSize()));
            } else {
                ddl.append(String.format("(%d, %d)", columnModel.getColumnSize(), columnModel.getDecimal()));
            }
        }
        if (columnModel.getColumnType().isUnsignedSupported()) {
            ddl.append(" UNSIGNED");
        }
        if (columnModel.isNotNull()) {
            ddl.append(" NOT NULL");
        }
        if (columnModel.isUniqueKey()) {
            ddl.append(" UNIQUE");
        }
        if (StringUtils.isNotEmpty(columnModel.getDefaultValue())) {
            ddl.append(" DEFAULT ").append(columnModel.getDefaultValue());
        }
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

    /**
     * .
     *
     * @param root
     * @param table
     * @return
     */
    default String getTableName(RootModel root, TableModel table) {
        if (isSchema()) {
            if (StringUtils.isNotEmpty(root.getJdbcSchema())) {
                return String.format("%s.%s", root.getJdbcSchema(), table.getPhysicalName());
            }
        }
        return table.getPhysicalName();
    }

    /**
     * .
     *
     * @param root
     * @param model
     * @param ddl
     * @param additions
     */
    default void setupTableOption(RootModel root, TableModel model, StringBuilder ddl, StringBuilder additions) {
        ddl.append(getSeparator())
                .append(LS);
    }

    /**
     * Validates diagram models.
     *
     * @param validation errors
     * @param model the root model of the diagram
     */
    default void validate(DiagramErrorManager errors, RootModel model) {
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

    boolean isSchema();

    void setSchema(boolean schema);

    boolean isDrop();

    void setDrop(boolean drop);

    boolean isComment();

    void setComment(boolean comment);
}
