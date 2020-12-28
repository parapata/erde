package io.github.erde.dialect;

import java.sql.Types;
import java.util.Arrays;
import java.util.List;

import io.github.erde.dialect.type.ColumnType;
import io.github.erde.dialect.type.IColumnType;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;

/**
 * MSSQLDialect.
 *
 * @author modified by parapata
 */
public class MSSQLDialect extends AbstractDialect {

    private static final List<IColumnType> COLUMN_TYPES = Arrays.asList(
            ColumnType.newInstance("IDENTITY", resource.getString("type.numeric"), true, Types.NUMERIC),
            ColumnType.newInstance("BIT", resource.getString("type.bit"), false, Types.BIT),
            ColumnType.newInstance("INT", resource.getString("type.integer"), false, Types.INTEGER),
            ColumnType.newInstance("BIGINT", resource.getString("type.integer"), false, Types.BIGINT),
            ColumnType.newInstance("SMALLINT", resource.getString("type.integer"), false, Types.SMALLINT),
            ColumnType.newInstance("TINYINT", resource.getString("type.integer"), false, Types.TINYINT),
            ColumnType.newInstance("DECIMAL", resource.getString("type.numeric"), false, Types.DECIMAL),
            ColumnType.newInstance("NUMERIC", resource.getString("type.numeric"), false, Types.NUMERIC),
            ColumnType.newInstance("MONEY", resource.getString("type.money"), false, Types.DECIMAL),
            ColumnType.newInstance("SMALLMONEY", resource.getString("type.money"), false, Types.DECIMAL),
            ColumnType.newInstance("FLOAT", resource.getString("type.real"), true, Types.FLOAT),
            ColumnType.newInstance("REAL", resource.getString("type.real"), false, Types.REAL),
            ColumnType.newInstance("DATETIME", resource.getString("type.date"), false, Types.DATE), // TIMESTAMPかも？
            ColumnType.newInstance("SMALLDATETIME", resource.getString("type.date"), false, Types.DATE), // TIMESTAMPかも？
            ColumnType.newInstance("CHAR", resource.getString("type.char"), true, Types.CHAR),
            ColumnType.newInstance("TEXT", resource.getString("type.text"), false, Types.VARCHAR),
            ColumnType.newInstance("VARCHAR", resource.getString("type.string"), true, Types.VARCHAR),
            ColumnType.newInstance("NCHAR", resource.getString("type.nchar"), true, Types.NCHAR),
            ColumnType.newInstance("NTEXT", resource.getString("type.ntext"), false, Types.VARCHAR),
            ColumnType.newInstance("NVARCHAR", resource.getString("type.nstring"), true, Types.NVARCHAR),
            ColumnType.newInstance("BINARY", resource.getString("type.binary"), true, Types.BLOB),
            ColumnType.newInstance("VARBINARY", resource.getString("type.binary"), true, Types.BLOB),
            ColumnType.newInstance("TIMESTAMP", resource.getString("type.timestamp"), false, Types.BINARY),
            ColumnType.newInstance("SQL_VARIANT", resource.getString("type.variant"), false, Types.OTHER),
            ColumnType.newInstance("UNIQUEIDENTIFIER", resource.getString("type.guid"), false, Types.OTHER),
            ColumnType.newInstance("XML", resource.getString("type.xml"), false, Types.OTHER));

    public MSSQLDialect() {
        super(COLUMN_TYPES);
        setSeparator(LS + "go");
    }

    @Override
    public void createColumnDDL(RootModel root, TableModel tableModel, ColumnModel columnModel, StringBuilder ddl,
            StringBuilder additions) {
        super.createColumnDDL(root, tableModel, columnModel, ddl, additions);
        if (columnModel.isAutoIncrement()) {
            ddl.append(" IDENTITY");
        }
    }

    @Override
    public String getColumnMetadataSQL(String tableName) {
        return String.format("SELECT TOP 1 * FROM %s", tableName);
    }
}
