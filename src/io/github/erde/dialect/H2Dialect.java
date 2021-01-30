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
 * H2Dialect.
 *
 * @author modified by parapata
 */
public class H2Dialect extends AbstractDialect {

    private static final List<IColumnType> COLUMN_TYPES = Arrays.asList(
            ColumnType.newInstance("IDENTITY", resource.getString("type.integer"), false, Types.INTEGER),
            ColumnType.newInstance("INT", resource.getString("type.integer"), false, Types.INTEGER),
            ColumnType.newInstance("INTEGER", resource.getString("type.integer"), false, Types.INTEGER),
            ColumnType.newInstance("BOOLEAN", resource.getString("type.boolean"), false, Types.BOOLEAN),
            ColumnType.newInstance("BIT", resource.getString("type.boolean"), false, Types.BOOLEAN),
            ColumnType.newInstance("BOOL", resource.getString("type.boolean"), false, Types.BOOLEAN),
            ColumnType.newInstance("TINYINT", resource.getString("type.integer"), false, Types.TINYINT),
            ColumnType.newInstance("BIGINT", resource.getString("type.integer"), false, Types.BIGINT),
            ColumnType.newInstance("DECIMAL", resource.getString("type.numeric"), true, Types.DECIMAL),
            ColumnType.newInstance("NUMBER", resource.getString("type.numeric"), true, Types.DECIMAL),
            ColumnType.newInstance("NUMERIC", resource.getString("type.numeric"), true, Types.DECIMAL),
            ColumnType.newInstance("DOUBLE", resource.getString("type.real"), false, Types.DOUBLE),
            ColumnType.newInstance("FLOAT", resource.getString("type.real"), false, Types.DOUBLE),
            ColumnType.newInstance("REAL", resource.getString("type.real"), false, Types.REAL),
            ColumnType.newInstance("TIME", resource.getString("type.time"), false, Types.TIME),
            ColumnType.newInstance("DATE", resource.getString("type.date"), false, Types.DATE),
            ColumnType.newInstance("TIMESTAMP", resource.getString("type.datetime"), false, Types.TIMESTAMP),
            ColumnType.newInstance("DATETIME", resource.getString("type.datetime"), false, Types.TIMESTAMP),
            ColumnType.newInstance("BINATY", resource.getString("type.binary"), true, Types.BINARY),
            ColumnType.newInstance("OBJECT", resource.getString("type.object"), false, Types.OTHER),
            ColumnType.newInstance("VARCHAR", resource.getString("type.string"), true, Types.VARCHAR),
            ColumnType.newInstance("VARCHAR_CASESENSITIVE", resource.getString("type.string"), true, Types.VARCHAR),
            ColumnType.newInstance("VARCHAR_IGNORECASE", resource.getString("type.string"), true, Types.VARCHAR),
            ColumnType.newInstance("CHAR", resource.getString("type.char"), true, Types.CHAR),
            ColumnType.newInstance("CHARACTER", resource.getString("type.char"), true, Types.CHAR),
            ColumnType.newInstance("BLOB", resource.getString("type.binary"), false, Types.BLOB),
            ColumnType.newInstance("CLOB", resource.getString("type.string"), true, Types.CLOB),
            ColumnType.newInstance("TEXT", resource.getString("type.string"), true, Types.CLOB),
            ColumnType.newInstance("ARRAY", resource.getString("type.string"), true, Types.ARRAY));

    public H2Dialect() {
        super(COLUMN_TYPES);
        setAutoIncrement(false);
    }

    @Override
    public DialectProvider getDialectProvider() {
        return DialectProvider.H2;
    }

    @Override
    public void createColumnDDL(RootModel root, TableModel tableModel, ColumnModel columnModel, StringBuilder sb,
            StringBuilder additions) {
        sb.append(columnModel.getPhysicalName());
        if (columnModel.isAutoIncrement()) {
            sb.append(" IDENTITY");
        } else {
            sb.append(" ").append(columnModel.getColumnType().getPhysicalName());
            if (columnModel.getColumnType().isSizeSupported() && columnModel.getColumnSize() != null) {
                sb.append("(").append(columnModel.getColumnSize()).append(")");
            }
        }
        if (columnModel.getDefaultValue().length() != 0) {
            sb.append(" DEFAULT ").append(columnModel.getDefaultValue());
        }
        if (columnModel.isNotNull()) {
            sb.append(" NOT NULL");
        }
    }

    @Override
    public String getColumnMetadataSQL(String tableName) {
        return super.getColumnMetadataSQL(tableName) + "  LIMIT 1";
    }
}
