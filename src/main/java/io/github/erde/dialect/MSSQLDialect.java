package io.github.erde.dialect;

import static io.github.erde.Resource.*;
import static io.github.erde.dialect.DialectProvider.*;
import static java.sql.Types.*;

import java.util.Arrays;
import java.util.List;

import io.github.erde.dialect.type.ColumnType;
import io.github.erde.dialect.type.IColumnType;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.RootModel;

/**
 * MSSQLDialect.
 *
 * @author modified by parapata
 */
public class MSSQLDialect extends AbstractDialect {

    private static final List<IColumnType> COLUMN_TYPES = Arrays.asList(
            ColumnType.newInstance(MSSQL, "BIT", TYPE_BIT, false, BIT),
            ColumnType.newInstance(MSSQL, "INT", TYPE_INTEGER, false, INTEGER),
            ColumnType.newInstance(MSSQL, "BIGINT", TYPE_INTEGER, false, BIGINT),
            ColumnType.newInstance(MSSQL, "SMALLINT", TYPE_INTEGER, false, SMALLINT),
            ColumnType.newInstance(MSSQL, "TINYINT", TYPE_INTEGER, false, TINYINT),
            ColumnType.newInstance(MSSQL, "DECIMAL", TYPE_NUMERIC, false, DECIMAL),
            ColumnType.newInstance(MSSQL, "NUMERIC", TYPE_NUMERIC, false, NUMERIC),
            ColumnType.newInstance(MSSQL, "MONEY", TYPE_MONEY, false, DECIMAL),
            ColumnType.newInstance(MSSQL, "SMALLMONEY", TYPE_MONEY, false, DECIMAL),
            ColumnType.newInstance(MSSQL, "FLOAT", TYPE_REAL, true, FLOAT),
            ColumnType.newInstance(MSSQL, "REAL", TYPE_REAL, false, REAL),
            ColumnType.newInstance(MSSQL, "DATETIME", TYPE_DATE, false, DATE), // TIMESTAMPかも？
            ColumnType.newInstance(MSSQL, "SMALLDATETIME", TYPE_DATE, false, DATE), // TIMESTAMPかも？
            ColumnType.newInstance(MSSQL, "CHAR", TYPE_CHAR, true, CHAR),
            ColumnType.newInstance(MSSQL, "VARCHAR", TYPE_STRING, true, VARCHAR),
            ColumnType.newInstance(MSSQL, "TEXT", TYPE_TEXT, false, VARCHAR),
            ColumnType.newInstance(MSSQL, "NCHAR", TYPE_NCHAR, true, NCHAR),
            ColumnType.newInstance(MSSQL, "NTEXT", TYPE_NTEXT, false, VARCHAR),
            ColumnType.newInstance(MSSQL, "NVARCHAR", TYPE_NSTRING, true, NVARCHAR),
            ColumnType.newInstance(MSSQL, "BINARY", TYPE_BINARY, true, BLOB),
            ColumnType.newInstance(MSSQL, "VARBINARY", TYPE_BINARY, true, BLOB),
            ColumnType.newInstance(MSSQL, "TIMESTAMP", TYPE_TIMESTAMP, false, BINARY),
            ColumnType.newInstance(MSSQL, "SQL_VARIANT", TYPE_VARIANT, false, OTHER),
            ColumnType.newInstance(MSSQL, "UNIQUEIDENTIFIER", TYPE_GUID, false, OTHER),
            ColumnType.newInstance(MSSQL, "XML", TYPE_XML, false, OTHER));

    public MSSQLDialect() {
        super(COLUMN_TYPES);
        setSeparator(String.format("%s\n%s", getSeparator(), "go"));
    }

    @Override
    public DialectProvider getDialectProvider() {
        return DialectProvider.MSSQL;
    }

    @Override
    public String createColumnPart(ColumnModel column) {
        StringBuilder sb = new StringBuilder();
        sb.append(super.createColumnPart(column));
        if (column.isAutoIncrement()) {
            sb.append(" IDENTITY(1,1)");
        }
        return sb.toString();
    }

    @Override
    public void additions(RootModel root) {
        // TODO Add extended_properties
        return;
    }

    @Override
    public String getColumnMetadataSQL(String tableName) {
        return String.format("SELECT TOP 1 * FROM %s", tableName);
    }

    @Override
    public boolean isAutoIncrement() {
        return true;
    }
}
