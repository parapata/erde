package io.github.erde.dialect;

import static io.github.erde.dialect.DialectProvider.*;
import static java.sql.Types.*;

import java.util.Arrays;
import java.util.List;

import io.github.erde.core.util.StringUtils;
import io.github.erde.dialect.type.ColumnType;
import io.github.erde.dialect.type.IColumnType;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;

/**
 * MySQLDialect.
 *
 * @author modified by parapata
 */
public class MySQLDialect extends AbstractDialect {

    private static final List<IColumnType> COLUMN_TYPES = Arrays.asList(
            ColumnType.newInstance(MySQL, "BOOL", "type.boolean", false, BOOLEAN),
            ColumnType.newInstance(MySQL, "BOOLEAN", "type.boolean", false, BOOLEAN),
            ColumnType.newInstance(MySQL, "BIT", "type.bit", true, BIT),
            ColumnType.newInstance(MySQL, "TINYINT", "type.integer", true, TINYINT),
            ColumnType.newInstance(MySQL, "SMALLINT", "type.integer", true, SMALLINT),
            ColumnType.newInstance(MySQL, "MEDIUMINT", "type.integer", true, INTEGER),
            ColumnType.newInstance(MySQL, "INT", "type.integer", true, INTEGER),
            ColumnType.newInstance(MySQL, "INTEGER", "type.integer", true, INTEGER),
            ColumnType.newInstance(MySQL, "BIGINT", "type.integer", true, BIGINT),
            ColumnType.newInstance(MySQL, "FLOAT", "type.real", true, FLOAT),
            ColumnType.newInstance(MySQL, "DOUBLE", "type.real", true, DOUBLE),
            ColumnType.newInstance(MySQL, "DECIMAL", "type.real", true, DECIMAL),
            ColumnType.newInstance(MySQL, "DEC", "type.real", true, DECIMAL),
            ColumnType.newInstance(MySQL, "DATE", "type.date", false, DATE),
            ColumnType.newInstance(MySQL, "DATETIME", "type.datetime", false, DATE),
            ColumnType.newInstance(MySQL, "TIME", "type.time", false, TIME),
            ColumnType.newInstance(MySQL, "TIMESTAMP", "type.datetime", false, TIMESTAMP),
            ColumnType.newInstance(MySQL, "YEAR", "type.year", false, INTEGER),
            ColumnType.newInstance(MySQL, "CHAR", "type.char", true, CHAR),
            ColumnType.newInstance(MySQL, "CHARACTER", "type.char", true, CHAR),
            ColumnType.newInstance(MySQL, "VARCHAR", "type.string", true, VARCHAR),
            ColumnType.newInstance(MySQL, "BINARY", "type.binary", true, BINARY),
            ColumnType.newInstance(MySQL, "VARBINARY", "type.binary", true, VARBINARY),
            ColumnType.newInstance(MySQL, "BLOB", "type.binary", false, BLOB),
            ColumnType.newInstance(MySQL, "TINYTEXT", "type.string", false, VARCHAR),
            ColumnType.newInstance(MySQL, "TEXT", "type.string", false, VARCHAR),
            ColumnType.newInstance(MySQL, "MEDIUMTEXT", "type.string", false, VARCHAR),
            ColumnType.newInstance(MySQL, "LONGTEXT", "type.string", false, VARCHAR),
            ColumnType.newInstance(MySQL, "ENUM", "type.string", false, OTHER));

    public MySQLDialect() {
        super(COLUMN_TYPES);
    }

    @Override
    public DialectProvider getDialectProvider() {
        return DialectProvider.MySQL;
    }

    @Override
    public String createColumnPart(ColumnModel column) {
        StringBuilder sb = new StringBuilder();
        sb.append(super.createColumnPart(column));
        if (column.isAutoIncrement()) {
            sb.append(" AUTO_INCREMENT");
        }
        if (isComment() && StringUtils.isNotEmpty(column.getLogicalName())) {
            sb.append(String.format(" COMMENT '%s'", column.getLogicalName()));
        }
        return sb.toString();
    }

    @Override
    public void setupTableOption(RootModel root, TableModel table) {
        if (isComment() && StringUtils.isNotEmpty(table.getLogicalName())) {
            print(String.format(" COMMENT = '%s'", table.getLogicalName()));
        }
        super.setupTableOption(root, table);
    }

    @Override
    public String getColumnMetadataSQL(String tableName) {
        return String.format("SELECT * FROM %s LIMIT 0, 1", tableName);
    }

    @Override
    public String getEnumMetadataSQL() {
        return "SELECT substring(column_type, 6, length(substring(column_type, 6)) - 1) AS enum_content FROM information_schema.columns WHERE table_name = ? AND column_name = ?";
    }

    @Override
    public boolean isAutoIncrement() {
        return true;
    }
}
