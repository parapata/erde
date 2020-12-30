package io.github.erde.dialect;

import java.sql.Types;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import io.github.erde.dialect.type.ColumnType;
import io.github.erde.dialect.type.IColumnType;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;

/**
 * MySQLDialect.
 *
 * @author modified by parapata
 */
public class MySQLDialect extends AbstractDialect {

    private static final List<IColumnType> COLUMN_TYPES = Arrays.asList(
            ColumnType.newInstance("BOOL", resource.getString("type.boolean"), false, Types.BOOLEAN),
            ColumnType.newInstance("BOOLEAN", resource.getString("type.boolean"), false, Types.BOOLEAN),
            ColumnType.newInstance("BIT", resource.getString("type.bit"), true, Types.BIT),
            ColumnType.newInstance("TINYINT", resource.getString("type.integer"), true, Types.TINYINT),
            ColumnType.newInstance("SMALLINT", resource.getString("type.integer"), true, Types.SMALLINT),
            ColumnType.newInstance("MEDIUMINT", resource.getString("type.integer"), true, Types.INTEGER),
            ColumnType.newInstance("INT", resource.getString("type.integer"), true, Types.INTEGER),
            ColumnType.newInstance("INTEGER", resource.getString("type.integer"), true, Types.INTEGER),
            ColumnType.newInstance("BIGINT", resource.getString("type.integer"), true, Types.BIGINT),
            ColumnType.newInstance("FLOAT", resource.getString("type.real"), true, Types.FLOAT),
            ColumnType.newInstance("DOUBLE", resource.getString("type.real"), true, Types.DOUBLE),
            ColumnType.newInstance("DECIMAL", resource.getString("type.real"), true, Types.DECIMAL),
            ColumnType.newInstance("DEC", resource.getString("type.real"), true, Types.DECIMAL),
            ColumnType.newInstance("DATE", resource.getString("type.date"), false, Types.DATE),
            ColumnType.newInstance("DATETIME", resource.getString("type.datetime"), false, Types.DATE),
            ColumnType.newInstance("TIME", resource.getString("type.time"), false, Types.TIME),
            ColumnType.newInstance("TIMESTAMP", resource.getString("type.datetime"), false, Types.TIMESTAMP),
            ColumnType.newInstance("YEAR", resource.getString("type.year"), false, Types.INTEGER),
            ColumnType.newInstance("CHAR", resource.getString("type.char"), true, Types.CHAR),
            ColumnType.newInstance("CHARACTER", resource.getString("type.char"), true, Types.CHAR),
            ColumnType.newInstance("VARCHAR", resource.getString("type.string"), true, Types.VARCHAR),
            ColumnType.newInstance("BINARY", resource.getString("type.binary"), true, Types.BINARY),
            ColumnType.newInstance("VARBINARY", resource.getString("type.binary"), true, Types.VARBINARY),
            ColumnType.newInstance("BLOB", resource.getString("type.binary"), false, Types.BLOB),
            ColumnType.newInstance("TINYTEXT", resource.getString("type.string"), false, Types.VARCHAR),
            ColumnType.newInstance("TEXT", resource.getString("type.string"), false, Types.VARCHAR),
            ColumnType.newInstance("MEDIUMTEXT", resource.getString("type.string"), false, Types.VARCHAR),
            ColumnType.newInstance("LONGTEXT", resource.getString("type.string"), false, Types.VARCHAR));

    public MySQLDialect() {
        super(COLUMN_TYPES);
        setAutoIncrement(true);
    }

    @Override
    public String dropTableDDL(String tableName) {
        return String.format("DROP TABLE IF EXISTS %s;", tableName);
    }

    @Override
    public void createColumnDDL(RootModel root, TableModel tableModel, ColumnModel columnModel, StringBuilder ddl,
            StringBuilder additions) {
        super.createColumnDDL(root, tableModel, columnModel, ddl, additions);
        if (columnModel.isAutoIncrement()) {
            ddl.append(" AUTO_INCREMENT");
        }
        if (isComment() && StringUtils.isNotEmpty(columnModel.getLogicalName())) {
            ddl.append(String.format(" COMMENT '%s'", columnModel.getLogicalName()));
        }
    }

    @Override
    public void setupTableOption(RootModel root, TableModel model, StringBuilder ddl, StringBuilder additions) {
        if (isComment() && StringUtils.isNotEmpty(model.getLogicalName())) {
            ddl.append(String.format(" COMMENT = '%s'", model.getLogicalName()));
        }
        super.setupTableOption(root, model, ddl, additions);
    }

    @Override
    public String getColumnMetadataSQL(String tableName) {
        return String.format("SELECT * FROM %s LIMIT 0, 1", tableName);
    }

}
