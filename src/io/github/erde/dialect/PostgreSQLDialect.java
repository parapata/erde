package io.github.erde.dialect;

import static io.github.erde.Resource.*;
import static io.github.erde.dialect.DialectProvider.*;
import static java.sql.Types.*;

import java.util.Arrays;
import java.util.List;

import io.github.erde.dialect.type.ColumnType;
import io.github.erde.dialect.type.IColumnType;
import io.github.erde.editor.diagram.model.ColumnModel;

/**
 * PostgreSQLDialect.
 *
 * @author modified by parapata
 */
public class PostgreSQLDialect extends AbstractDialect {

    private static final List<IColumnType> COLUMN_TYPES = Arrays.asList(
            ColumnType.newInstance(PostgreSQL, "SERIAL", TYPE_INTEGER, false, INTEGER),
            ColumnType.newInstance(PostgreSQL, "BIGSERIAL", TYPE_INTEGER, false, BIGINT),
            ColumnType.newInstance(PostgreSQL, "BIGINT", TYPE_INTEGER, false, BIGINT),
            ColumnType.newInstance(PostgreSQL, "BIT", TYPE_BIT, true, BIT),
            ColumnType.newInstance(PostgreSQL, "VARBIT", TYPE_BIT, true, BIT),
            ColumnType.newInstance(PostgreSQL, "BOOLEAN", TYPE_BOOLEAN, false, BOOLEAN),
            ColumnType.newInstance(PostgreSQL, "BYTEA", TYPE_BINARY, false, BINARY),
            ColumnType.newInstance(PostgreSQL, "VARCHAR", TYPE_STRING, true, VARCHAR),
            ColumnType.newInstance(PostgreSQL, "CHARACTER", TYPE_CHAR, true, CHAR),
            ColumnType.newInstance(PostgreSQL, "DATE", TYPE_DATE, false, DATE),
            ColumnType.newInstance(PostgreSQL, "INTEGER", TYPE_INTEGER, false, INTEGER),
            ColumnType.newInstance(PostgreSQL, "NUMERIC", TYPE_NUMERIC, true, NUMERIC),
            ColumnType.newInstance(PostgreSQL, "REAL", TYPE_REAL, false, REAL),
            ColumnType.newInstance(PostgreSQL, "SMALLINT", TYPE_INTEGER, false, SMALLINT),
            ColumnType.newInstance(PostgreSQL, "TEXT", TYPE_STRING, false, VARCHAR),
            ColumnType.newInstance(PostgreSQL, "TIME", TYPE_TIME, false, TIME),
            ColumnType.newInstance(PostgreSQL, "TIMESTAMP", TYPE_DATETIME, false, TIMESTAMP),
            ColumnType.newInstance(PostgreSQL, "SERIAL", TYPE_SERIAL, false, INTEGER),
            ColumnType.newInstance(PostgreSQL, "BIGSERIAL", TYPE_SERIAL, false, BIGINT),
            ColumnType.newInstance(PostgreSQL, "XML", TYPE_XML, false, SQLXML),
            ColumnType.newInstance(PostgreSQL, "INTERVAL", TYPE_INTERVAL, false, OTHER),
            ColumnType.newInstance(PostgreSQL, "INET", TYPE_NETWORKADDRESS, false, OTHER),
            ColumnType.newInstance(PostgreSQL, "CIDR", TYPE_NETWORKADDRESS, false, OTHER),
            ColumnType.newInstance(PostgreSQL, "MACADDR", TYPE_MACADDRESS, false, OTHER));

    public PostgreSQLDialect() {
        super(COLUMN_TYPES);
    }

    @Override
    public DialectProvider getDialectProvider() {
        return DialectProvider.PostgreSQL;
    }

    @Override
    public String createColumnPart(ColumnModel column) {
        StringBuilder sb = new StringBuilder();
        sb.append(column.getPhysicalName());
        if (column.isAutoIncrement()) {
            if (column.getColumnType().getPhysicalName().equals("BIGINT")) {
                sb.append(" BIGSERIAL");
            } else {
                sb.append(" SERIAL");
            }
        } else {
            sb.append(SPACE).append(column.getColumnType().getPhysicalName());
            if (column.getColumnType().isSizeSupported() && column.getColumnSize() != null) {
                sb.append(String.format("(%s)", column.getColumnSize()));
            }
            if (column.isNotNull()) {
                sb.append(" NOT NULL");
            }
        }
        if (column.getDefaultValue().length() != 0) {
            sb.append(String.format(" DEFAULT %s", column.getDefaultValue()));
        }
        return sb.toString();
    }

    @Override
    public String getColumnMetadataSQL(String tableName) {
        return String.format("%s LIMIT 1", super.getColumnMetadataSQL(tableName));
    }
}
