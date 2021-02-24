package io.github.erde.dialect;

import static io.github.erde.dialect.DialectProvider.*;
import static java.sql.Types.*;

import java.util.Arrays;
import java.util.List;

import io.github.erde.dialect.type.ColumnType;
import io.github.erde.dialect.type.IColumnType;
import io.github.erde.editor.diagram.model.ColumnModel;

/**
 * MSSQLDialect.
 *
 * @author modified by parapata
 */
public class MSSQLDialect extends AbstractDialect {

    private static final List<IColumnType> COLUMN_TYPES = Arrays.asList(
            ColumnType.newInstance(MSSQL, "IDENTITY", "type.numeric", true, NUMERIC),
            ColumnType.newInstance(MSSQL, "BIT", "type.bit", false, BIT),
            ColumnType.newInstance(MSSQL, "INT", "type.integer", false, INTEGER),
            ColumnType.newInstance(MSSQL, "BIGINT", "type.integer", false, BIGINT),
            ColumnType.newInstance(MSSQL, "SMALLINT", "type.integer", false, SMALLINT),
            ColumnType.newInstance(MSSQL, "TINYINT", "type.integer", false, TINYINT),
            ColumnType.newInstance(MSSQL, "DECIMAL", "type.numeric", false, DECIMAL),
            ColumnType.newInstance(MSSQL, "NUMERIC", "type.numeric", false, NUMERIC),
            ColumnType.newInstance(MSSQL, "MONEY", "type.money", false, DECIMAL),
            ColumnType.newInstance(MSSQL, "SMALLMONEY", "type.money", false, DECIMAL),
            ColumnType.newInstance(MSSQL, "FLOAT", "type.real", true, FLOAT),
            ColumnType.newInstance(MSSQL, "REAL", "type.real", false, REAL),
            ColumnType.newInstance(MSSQL, "DATETIME", "type.date", false, DATE), // TIMESTAMPかも？
            ColumnType.newInstance(MSSQL, "SMALLDATETIME", "type.date", false, DATE), // TIMESTAMPかも？
            ColumnType.newInstance(MSSQL, "CHAR", "type.char", true, CHAR),
            ColumnType.newInstance(MSSQL, "TEXT", "type.text", false, VARCHAR),
            ColumnType.newInstance(MSSQL, "VARCHAR", "type.string", true, VARCHAR),
            ColumnType.newInstance(MSSQL, "NCHAR", "type.nchar", true, NCHAR),
            ColumnType.newInstance(MSSQL, "NTEXT", "type.ntext", false, VARCHAR),
            ColumnType.newInstance(MSSQL, "NVARCHAR", "type.nstring", true, NVARCHAR),
            ColumnType.newInstance(MSSQL, "BINARY", "type.binary", true, BLOB),
            ColumnType.newInstance(MSSQL, "VARBINARY", "type.binary", true, BLOB),
            ColumnType.newInstance(MSSQL, "TIMESTAMP", "type.timestamp", false, BINARY),
            ColumnType.newInstance(MSSQL, "SQL_VARIANT", "type.variant", false, OTHER),
            ColumnType.newInstance(MSSQL, "UNIQUEIDENTIFIER", "type.guid", false, OTHER),
            ColumnType.newInstance(MSSQL, "XML", "type.xml", false, OTHER));

    public MSSQLDialect() {
        super(COLUMN_TYPES);
        setSeparator(getSeparator() + "go");
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
            sb.append(" IDENTITY");
        }
        return sb.toString();
    }

    @Override
    public String getColumnMetadataSQL(String tableName) {
        return String.format("SELECT TOP 1 * FROM %s", tableName);
    }
}
