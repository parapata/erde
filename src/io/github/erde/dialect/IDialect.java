package io.github.erde.dialect;

import java.io.PrintWriter;
import java.sql.Types;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import io.github.erde.core.util.JDBCConnection;
import io.github.erde.dialect.loader.DefaultSchemaLoader;
import io.github.erde.dialect.loader.ISchemaLoader;
import io.github.erde.dialect.type.IColumnType;
import io.github.erde.dialect.type.IIndexType;
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
     * Creates DDL from a given model.
     *
     * @param root a root model of diagram
     * @param writer DDLs
     */
    void createDDL(RootModel root, PrintWriter writer);

    /**
     * Creates DDL from a given table model.
     *
     * @param root a root model of diagram
     * @param table a tablemodel of diagram
     * @param writer DDL writer
     */
    void createTableDDL(RootModel root, TableModel table, PrintWriter writer);

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
