package io.github.erde.dialect;

import static io.github.erde.Resource.*;
import static io.github.erde.dialect.DialectProvider.*;
import static java.sql.Types.*;

import java.util.Arrays;
import java.util.List;

import io.github.erde.core.util.StringUtils;
import io.github.erde.dialect.type.ColumnType;
import io.github.erde.dialect.type.IColumnType;
import io.github.erde.editor.diagram.model.ColumnModel;

/**
 * H2Dialect.
 *
 * @author modified by parapata
 */
public class H2Dialect extends AbstractDialect {

    private static final List<IColumnType> COLUMN_TYPES = Arrays.asList(
            ColumnType.newInstance(H2, "INT", TYPE_INTEGER, false, INTEGER),
            ColumnType.newInstance(H2, "INTEGER", TYPE_INTEGER, false, INTEGER),
            ColumnType.newInstance(H2, "BOOLEAN", TYPE_BOOLEAN, false, BOOLEAN),
            ColumnType.newInstance(H2, "BIT", TYPE_BOOLEAN, false, BOOLEAN),
            ColumnType.newInstance(H2, "BOOL", TYPE_BOOLEAN, false, BOOLEAN),
            ColumnType.newInstance(H2, "TINYINT", TYPE_INTEGER, false, TINYINT),
            ColumnType.newInstance(H2, "BIGINT", TYPE_INTEGER, false, BIGINT),
            ColumnType.newInstance(H2, "DECIMAL", TYPE_NUMERIC, true, DECIMAL),
            ColumnType.newInstance(H2, "NUMBER", TYPE_NUMERIC, true, DECIMAL),
            ColumnType.newInstance(H2, "NUMERIC", TYPE_NUMERIC, true, DECIMAL),
            ColumnType.newInstance(H2, "DOUBLE", TYPE_REAL, false, DOUBLE),
            ColumnType.newInstance(H2, "FLOAT", TYPE_REAL, false, DOUBLE),
            ColumnType.newInstance(H2, "REAL", TYPE_REAL, false, REAL),
            ColumnType.newInstance(H2, "TIME", TYPE_TIME, false, TIME),
            ColumnType.newInstance(H2, "DATE", TYPE_DATE, false, DATE),
            ColumnType.newInstance(H2, "TIMESTAMP", TYPE_DATETIME, false, TIMESTAMP),
            ColumnType.newInstance(H2, "DATETIME", TYPE_DATETIME, false, TIMESTAMP),
            ColumnType.newInstance(H2, "BINATY", TYPE_BINARY, true, BINARY),
            ColumnType.newInstance(H2, "OBJECT", TYPE_OBJECT, false, OTHER),
            ColumnType.newInstance(H2, "VARCHAR", TYPE_STRING, true, VARCHAR),
            ColumnType.newInstance(H2, "VARCHAR_CASESENSITIVE", TYPE_STRING, true, VARCHAR),
            ColumnType.newInstance(H2, "VARCHAR_IGNORECASE", TYPE_STRING, true, VARCHAR),
            ColumnType.newInstance(H2, "CHAR", TYPE_CHAR, true, CHAR),
            ColumnType.newInstance(H2, "CHARACTER", TYPE_CHAR, true, CHAR),
            ColumnType.newInstance(H2, "BLOB", TYPE_BINARY, false, BLOB),
            ColumnType.newInstance(H2, "CLOB", TYPE_STRING, true, CLOB),
            ColumnType.newInstance(H2, "TEXT", TYPE_STRING, true, CLOB),
            ColumnType.newInstance(H2, "ARRAY", TYPE_STRING, true, ARRAY));

    public H2Dialect() {
        super(COLUMN_TYPES);
    }

    @Override
    public DialectProvider getDialectProvider() {
        return DialectProvider.H2;
    }

    @Override
    public String createColumnPart(ColumnModel column) {
        StringBuilder sb = new StringBuilder();
        sb.append(column.getPhysicalName());
        if (column.isAutoIncrement()) {
            sb.append(" IDENTITY");
        } else {
            sb.append(SPACE).append(column.getColumnType().getPhysicalName());
            if (column.getColumnType().isSizeSupported() && column.getColumnSize() != null) {
                sb.append(String.format("(%s)", column.getColumnSize()));
            }
        }
        if (StringUtils.isNotEmpty(column.getDefaultValue())) {
            sb.append(String.format(" DEFAULT %s", column.getDefaultValue()));
        }
        if (column.isNotNull()) {
            sb.append(" NOT NULL");
        }
        return sb.toString();
    }

    @Override
    public String getColumnMetadataSQL(String tableName) {
        return String.format("%s LIMIT 1", super.getColumnMetadataSQL(tableName));
    }

    @Override
    public boolean isAutoIncrement() {
        return true;
    }
}
