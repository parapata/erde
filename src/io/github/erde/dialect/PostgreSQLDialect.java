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
 * PostgreSQLDialect.
 *
 * @author modified by parapata
 */
public class PostgreSQLDialect extends AbstractDialect {

    private static final List<IColumnType> COLUMN_TYPES = Arrays.asList(
            ColumnType.newInstance("SERIAL", resource.getString("type.integer"), false, Types.INTEGER),
            ColumnType.newInstance("BIGSERIAL", resource.getString("type.integer"), false, Types.BIGINT),
            ColumnType.newInstance("BIGINT", resource.getString("type.integer"), false, Types.BIGINT),
            ColumnType.newInstance("BIT", resource.getString("type.bit"), true, Types.BIT),
            ColumnType.newInstance("VARBIT", resource.getString("type.bit"), true, Types.BIT),
            ColumnType.newInstance("BOOLEAN", resource.getString("type.boolean"), false, Types.BOOLEAN),
            ColumnType.newInstance("BYTEA", resource.getString("type.binary"), false, Types.BINARY),
            ColumnType.newInstance("VARCHAR", resource.getString("type.string"), true, Types.VARCHAR),
            ColumnType.newInstance("CHARACTER", resource.getString("type.char"), true, Types.CHAR),
            ColumnType.newInstance("DATE", resource.getString("type.date"), false, Types.DATE),
            ColumnType.newInstance("INTEGER", resource.getString("type.integer"), false, Types.INTEGER),
            ColumnType.newInstance("NUMERIC", resource.getString("type.numeric"), true, Types.NUMERIC),
            ColumnType.newInstance("REAL", resource.getString("type.real"), false, Types.REAL),
            ColumnType.newInstance("SMALLINT", resource.getString("type.integer"), false, Types.SMALLINT),
            ColumnType.newInstance("TEXT", resource.getString("type.string"), false, Types.VARCHAR),
            ColumnType.newInstance("TIME", resource.getString("type.time"), false, Types.TIME),
            ColumnType.newInstance("TIMESTAMP", resource.getString("type.datetime"), false, Types.TIMESTAMP),
            ColumnType.newInstance("SERIAL", resource.getString("type.serial"), false, Types.INTEGER),
            ColumnType.newInstance("BIGSERIAL", resource.getString("type.serial"), false, Types.BIGINT),
            ColumnType.newInstance("XML", resource.getString("type.xml"), false, Types.SQLXML),
            ColumnType.newInstance("INTERVAL", resource.getString("type.interval"), false, Types.OTHER),
            ColumnType.newInstance("INET", resource.getString("type.networkaddress"), false, Types.OTHER),
            ColumnType.newInstance("CIDR", resource.getString("type.networkaddress"), false, Types.OTHER),
            ColumnType.newInstance("MACADDR", resource.getString("type.macaddress"), false, Types.OTHER));

    public PostgreSQLDialect() {
        super(COLUMN_TYPES);
        setAutoIncrement(false);
    }

    @Override
    public DialectProvider getDialectProvider() {
        return DialectProvider.PostgreSQL;
    }

    @Override
    public void createColumnDDL(RootModel root, TableModel tableModel, ColumnModel columnModel,
            StringBuilder ddl, StringBuilder additions) {

        ddl.append(columnModel.getPhysicalName());
        if (columnModel.isAutoIncrement()) {
            if (columnModel.getColumnType().getPhysicalName().equals("BIGINT")) {
                ddl.append(" BIGSERIAL");
            } else {
                ddl.append(" SERIAL");
            }
        } else {
            ddl.append(" ").append(columnModel.getColumnType().getPhysicalName());
            if (columnModel.getColumnType().isSizeSupported() && columnModel.getColumnSize() != null) {
                ddl.append("(").append(columnModel.getColumnSize()).append(")");
            }
            if (columnModel.isNotNull()) {
                ddl.append(" NOT NULL");
            }
        }
        if (columnModel.getDefaultValue().length() != 0) {
            ddl.append(" DEFAULT ").append(columnModel.getDefaultValue());
        }
    }

    @Override
    public String getColumnMetadataSQL(String tableName) {
        return super.getColumnMetadataSQL(tableName) + "  LIMIT 1";
    }
}
