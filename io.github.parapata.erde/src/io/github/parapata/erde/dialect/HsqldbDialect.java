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

/**
 * HsqldbDialect.
 *
 * @author modified by parapata
 */
public class HsqldbDialect extends AbstractDialect {

    private static final List<IColumnType> COLUMN_TYPES = Arrays.asList(
            ColumnType.newInstance(HSQLDB, "INT", TYPE_INTEGER, false, INTEGER),
            ColumnType.newInstance(HSQLDB, "INTEGER", TYPE_INTEGER, false, INTEGER),
            ColumnType.newInstance(HSQLDB, "DOUBLE", TYPE_REAL, false, DOUBLE),
            ColumnType.newInstance(HSQLDB, "FLOAT", TYPE_REAL, false, FLOAT),
            ColumnType.newInstance(HSQLDB, "VARCHAR", TYPE_STRING, true, VARCHAR),
            ColumnType.newInstance(HSQLDB, "VARCHAR_IGNORECASE", TYPE_STRING, true, VARCHAR),
            ColumnType.newInstance(HSQLDB, "CHAR", TYPE_CHAR, true, CHAR),
            ColumnType.newInstance(HSQLDB, "CHARACTER", TYPE_CHAR, true, CHAR),
            ColumnType.newInstance(HSQLDB, "DECIMAL", TYPE_REAL, true, DECIMAL),
            ColumnType.newInstance(HSQLDB, "NUMERIC", TYPE_NUMERIC, true, NUMERIC),
            ColumnType.newInstance(HSQLDB, "BOOLEAN", TYPE_BOOLEAN, true, BOOLEAN),
            ColumnType.newInstance(HSQLDB, "BIT", TYPE_BIT, true, BIT),
            ColumnType.newInstance(HSQLDB, "TINYINT", TYPE_INTEGER, true, TINYINT),
            ColumnType.newInstance(HSQLDB, "SMALLINT", TYPE_INTEGER, true, SMALLINT),
            ColumnType.newInstance(HSQLDB, "BIGINT", TYPE_INTEGER, true, BIGINT),
            ColumnType.newInstance(HSQLDB, "REAL", TYPE_REAL, true, REAL),
            ColumnType.newInstance(HSQLDB, "BINARY", TYPE_BINARY, true, BINARY),
            ColumnType.newInstance(HSQLDB, "VARBINARY", TYPE_BINARY, true, BINARY),
            ColumnType.newInstance(HSQLDB, "LONGVARBINARY", TYPE_BINARY, true, BINARY),
            ColumnType.newInstance(HSQLDB, "DATE", TYPE_DATE, false, DATE),
            ColumnType.newInstance(HSQLDB, "TIME", TYPE_TIME, false, TIME),
            ColumnType.newInstance(HSQLDB, "TIMESTAMP", TYPE_DATETIME, false, TIMESTAMP),
            ColumnType.newInstance(HSQLDB, "DATETIME", TYPE_DATETIME, false, TIMESTAMP),
            ColumnType.newInstance(HSQLDB, "OTHER", TYPE_OTHER, false, OTHER),
            ColumnType.newInstance(HSQLDB, "OBJECT", TYPE_OBJECT, false, OTHER));

    public HsqldbDialect() {
        super(COLUMN_TYPES);
    }

    @Override
    public DialectProvider getDialectProvider() {
        return DialectProvider.HSQLDB;
    }

    @Override
    public String createColumnPart(ColumnModel column) {
        StringBuilder sb = new StringBuilder();
        sb.append(column.getPhysicalName());
        sb.append(" ").append(column.getColumnType().getPhysicalName());
        if (column.getColumnType().isSizeSupported() && column.getColumnSize() != null) {
            sb.append("(").append(column.getColumnSize()).append(")");
        }
        if (StringUtils.isNotEmpty(column.getDefaultValue())) {
            sb.append(String.format(" DEFAULT %s", column.getDefaultValue()));
        }
        if (column.isNotNull()) {
            sb.append(" NOT NULL");
        }
        if (column.isAutoIncrement()) {
            sb.append(" IDENTITY");
        }
        return sb.toString();
    }

    @Override
    public String dropTableDDL(String tableName) {
        return String.format("DROP TABLE %s IF EXISTS", tableName);
    }

    @Override
    public String getColumnMetadataSQL(String tableName) {
        return super.getColumnMetadataSQL(tableName) + "  LIMIT 1";
    }

    @Override
    public boolean isComment() {
        return false;
    }
}
