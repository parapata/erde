package io.github.erde.dialect;

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
            ColumnType.newInstance(PostgreSQL, "SERIAL", "type.integer", false, INTEGER),
            ColumnType.newInstance(PostgreSQL, "BIGSERIAL", "type.integer", false, BIGINT),
            ColumnType.newInstance(PostgreSQL, "BIGINT", "type.integer", false, BIGINT),
            ColumnType.newInstance(PostgreSQL, "BIT", "type.bit", true, BIT),
            ColumnType.newInstance(PostgreSQL, "VARBIT", "type.bit", true, BIT),
            ColumnType.newInstance(PostgreSQL, "BOOLEAN", "type.boolean", false, BOOLEAN),
            ColumnType.newInstance(PostgreSQL, "BYTEA", "type.binary", false, BINARY),
            ColumnType.newInstance(PostgreSQL, "VARCHAR", "type.string", true, VARCHAR),
            ColumnType.newInstance(PostgreSQL, "CHARACTER", "type.char", true, CHAR),
            ColumnType.newInstance(PostgreSQL, "DATE", "type.date", false, DATE),
            ColumnType.newInstance(PostgreSQL, "INTEGER", "type.integer", false, INTEGER),
            ColumnType.newInstance(PostgreSQL, "NUMERIC", "type.numeric", true, NUMERIC),
            ColumnType.newInstance(PostgreSQL, "REAL", "type.real", false, REAL),
            ColumnType.newInstance(PostgreSQL, "SMALLINT", "type.integer", false, SMALLINT),
            ColumnType.newInstance(PostgreSQL, "TEXT", "type.string", false, VARCHAR),
            ColumnType.newInstance(PostgreSQL, "TIME", "type.time", false, TIME),
            ColumnType.newInstance(PostgreSQL, "TIMESTAMP", "type.datetime", false, TIMESTAMP),
            ColumnType.newInstance(PostgreSQL, "SERIAL", "type.serial", false, INTEGER),
            ColumnType.newInstance(PostgreSQL, "BIGSERIAL", "type.serial", false, BIGINT),
            ColumnType.newInstance(PostgreSQL, "XML", "type.xml", false, SQLXML),
            ColumnType.newInstance(PostgreSQL, "INTERVAL", "type.interval", false, OTHER),
            ColumnType.newInstance(PostgreSQL, "INET", "type.networkaddress", false, OTHER),
            ColumnType.newInstance(PostgreSQL, "CIDR", "type.networkaddress", false, OTHER),
            ColumnType.newInstance(PostgreSQL, "MACADDR", "type.macaddress", false, OTHER));

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
