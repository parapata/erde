package io.github.parapata.erde.dialect;

import java.io.PrintWriter;
import java.sql.Types;
import java.util.List;

import io.github.parapata.erde.core.util.StringUtils;
import io.github.parapata.erde.dialect.type.IColumnType;
import io.github.parapata.erde.dialect.type.IIndexType;
import io.github.parapata.erde.editor.diagram.model.RootModel;
import io.github.parapata.erde.editor.diagram.model.TableModel;
import io.github.parapata.erde.editor.validator.DiagramErrorManager;

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
     * Creates DDL from a given select model.
     *
     * @param root a root model of diagram
     * @param tables a tablemodel of diagram
     * @param writer DDL writer
     */
    void createDDL(RootModel root, List<TableModel> tables, PrintWriter writer);

    /**
     * Validates diagram models.
     *
     * @param errors
     * @param root
     */
    default void validate(DiagramErrorManager errors, RootModel root) {
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
            if (type.getPhysicalName().equalsIgnoreCase(typeName)) {
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
