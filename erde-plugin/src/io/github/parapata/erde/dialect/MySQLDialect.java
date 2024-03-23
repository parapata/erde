package io.github.parapata.erde.dialect;

import static io.github.parapata.erde.Resource.*;
import static io.github.parapata.erde.dialect.DialectProvider.*;
import static java.sql.Types.*;

import java.util.Arrays;
import java.util.List;

import io.github.parapata.erde.core.util.StringUtils;
import io.github.parapata.erde.dialect.type.ColumnType;
import io.github.parapata.erde.dialect.type.IColumnType;
import io.github.parapata.erde.editor.diagram.model.ColumnModel;
import io.github.parapata.erde.editor.diagram.model.RootModel;
import io.github.parapata.erde.editor.diagram.model.TableModel;

/**
 * MySQLDialect.
 *
 * @author modified by parapata
 */
public class MySQLDialect extends AbstractDialect {

    private static final List<IColumnType> COLUMN_TYPES = Arrays.asList(
            ColumnType.newInstance(MySQL, "BOOL", TYPE_BOOLEAN, false, BOOLEAN),
            ColumnType.newInstance(MySQL, "BOOLEAN", TYPE_BOOLEAN, false, BOOLEAN),
            ColumnType.newInstance(MySQL, "BIT", TYPE_BIT, true, BIT),
            ColumnType.newInstance(MySQL, "TINYINT", TYPE_INTEGER, true, TINYINT),
            ColumnType.newInstance(MySQL, "SMALLINT", TYPE_INTEGER, true, SMALLINT),
            ColumnType.newInstance(MySQL, "MEDIUMINT", TYPE_INTEGER, true, INTEGER),
            ColumnType.newInstance(MySQL, "INT", TYPE_INTEGER, true, INTEGER),
            ColumnType.newInstance(MySQL, "INTEGER", TYPE_INTEGER, true, INTEGER),
            ColumnType.newInstance(MySQL, "BIGINT", TYPE_INTEGER, true, BIGINT),
            ColumnType.newInstance(MySQL, "FLOAT", TYPE_REAL, true, FLOAT),
            ColumnType.newInstance(MySQL, "DOUBLE", TYPE_REAL, true, DOUBLE),
            ColumnType.newInstance(MySQL, "DECIMAL", TYPE_REAL, true, DECIMAL),
            ColumnType.newInstance(MySQL, "DEC", TYPE_REAL, true, DECIMAL),
            ColumnType.newInstance(MySQL, "DATE", TYPE_DATE, false, DATE),
            ColumnType.newInstance(MySQL, "DATETIME", TYPE_DATETIME, false, DATE),
            ColumnType.newInstance(MySQL, "TIME", TYPE_TIME, false, TIME),
            ColumnType.newInstance(MySQL, "TIMESTAMP", TYPE_DATETIME, false, TIMESTAMP),
            ColumnType.newInstance(MySQL, "YEAR", TYPE_YEAR, false, INTEGER),
            ColumnType.newInstance(MySQL, "CHAR", TYPE_CHAR, true, CHAR),
            ColumnType.newInstance(MySQL, "CHARACTER", TYPE_CHAR, true, CHAR),
            ColumnType.newInstance(MySQL, "VARCHAR", TYPE_STRING, true, VARCHAR),
            ColumnType.newInstance(MySQL, "BINARY", TYPE_BINARY, true, BINARY),
            ColumnType.newInstance(MySQL, "VARBINARY", TYPE_BINARY, true, VARBINARY),
            ColumnType.newInstance(MySQL, "BLOB", TYPE_BINARY, false, BLOB),
            ColumnType.newInstance(MySQL, "TINYTEXT", TYPE_STRING, false, VARCHAR),
            ColumnType.newInstance(MySQL, "TEXT", TYPE_STRING, false, VARCHAR),
            ColumnType.newInstance(MySQL, "MEDIUMTEXT", TYPE_STRING, false, VARCHAR),
            ColumnType.newInstance(MySQL, "LONGTEXT", TYPE_STRING, false, VARCHAR),
            ColumnType.newInstance(MySQL, "ENUM", TYPE_ENUM, false, OTHER));

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
            print(String.format(" COMMENT '%s'", table.getLogicalName()));
        }
        super.setupTableOption(root, table);
    }

    @Override
    public void additions(RootModel root) {
        return;
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
