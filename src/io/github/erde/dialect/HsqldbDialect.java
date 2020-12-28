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
 * HsqldbDialect.
 *
 * @author modified by parapata
 */
public class HsqldbDialect extends AbstractDialect {

    private static final List<IColumnType> COLUMN_TYPES = Arrays.asList(
            ColumnType.newInstance("IDENTITY", resource.getString("type.integer"), false, Types.INTEGER),
            ColumnType.newInstance("INT", resource.getString("type.integer"), false, Types.INTEGER),
            ColumnType.newInstance("INTEGER", resource.getString("type.integer"), false, Types.INTEGER),
            ColumnType.newInstance("DOUBLE", resource.getString("type.real"), false, Types.DOUBLE),
            ColumnType.newInstance("FLOAT", resource.getString("type.real"), false, Types.FLOAT),
            ColumnType.newInstance("VARCHAR", resource.getString("type.string"), true, Types.VARCHAR),
            ColumnType.newInstance("VARCHAR_IGNORECASE", resource.getString("type.string"), true, Types.VARCHAR),
            ColumnType.newInstance("CHAR", resource.getString("type.char"), true, Types.CHAR),
            ColumnType.newInstance("CHARACTER", resource.getString("type.char"), true, Types.CHAR),
            ColumnType.newInstance("DECIMAL", resource.getString("type.real"), true, Types.DECIMAL),
            ColumnType.newInstance("NUMERIC", resource.getString("type.numeric"), true, Types.NUMERIC),
            ColumnType.newInstance("BOOLEAN", resource.getString("type.boolean"), true, Types.BOOLEAN),
            ColumnType.newInstance("BIT", resource.getString("type.bit"), true, Types.BIT),
            ColumnType.newInstance("TINYINT", resource.getString("type.integer"), true, Types.TINYINT),
            ColumnType.newInstance("SMALLINT", resource.getString("type.integer"), true, Types.SMALLINT),
            ColumnType.newInstance("BIGINT", resource.getString("type.integer"), true, Types.BIGINT),
            ColumnType.newInstance("REAL", resource.getString("type.real"), true, Types.REAL),
            ColumnType.newInstance("BINARY", resource.getString("type.binary"), true, Types.BINARY),
            ColumnType.newInstance("VARBINARY", resource.getString("type.binary"), true, Types.BINARY),
            ColumnType.newInstance("LONGVARBINARY", resource.getString("type.binary"), true, Types.BINARY),
            ColumnType.newInstance("DATE", resource.getString("type.date"), false, Types.DATE),
            ColumnType.newInstance("TIME", resource.getString("type.time"), false, Types.TIME),
            ColumnType.newInstance("TIMESTAMP", resource.getString("type.datetime"), false, Types.TIMESTAMP),
            ColumnType.newInstance("DATETIME", resource.getString("type.datetime"), false, Types.TIMESTAMP),
            ColumnType.newInstance("OTHER", resource.getString("type.other"), false, Types.OTHER),
            ColumnType.newInstance("OBJECT", resource.getString("type.object"), false, Types.OTHER));

    public HsqldbDialect() {
        super(COLUMN_TYPES);
        setAutoIncrement(false);
    }

    @Override
    public void createColumnDDL(RootModel root, TableModel tableModel, ColumnModel columnModel, StringBuilder ddl,
            StringBuilder additions) {
        ddl.append(columnModel.getPhysicalName());
        ddl.append(" ").append(columnModel.getColumnType().getPhysicalName());
        if (columnModel.getColumnType().isSizeSupported() && columnModel.getColumnSize() != null) {
            ddl.append("(").append(columnModel.getColumnSize()).append(")");
        }
        if (columnModel.getDefaultValue().length() != 0) {
            ddl.append(" DEFAULT ").append(columnModel.getDefaultValue());
        }
        if (columnModel.isNotNull()) {
            ddl.append(" NOT NULL");
        }
        if (columnModel.isAutoIncrement()) {
            ddl.append(" IDENTITY");
        }
    }

    @Override
    public String dropTableDDL(String tableName) {
        return String.format("DROP TABLE %s IF EXISTS;", tableName);
    }

    @Override
    public String getColumnMetadataSQL(String tableName) {
        return super.getColumnMetadataSQL(tableName) + "  LIMIT 1";
    }
}
