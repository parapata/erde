package io.github.erde.dialect;

import static io.github.erde.dialect.DialectProvider.*;
import static java.sql.Types.*;

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
            ColumnType.newInstance(HSQLDB, "IDENTITY", "type.integer", false, INTEGER),
            ColumnType.newInstance(HSQLDB, "INT", "type.integer", false, INTEGER),
            ColumnType.newInstance(HSQLDB, "INTEGER", "type.integer", false, INTEGER),
            ColumnType.newInstance(HSQLDB, "DOUBLE", "type.real", false, DOUBLE),
            ColumnType.newInstance(HSQLDB, "FLOAT", "type.real", false, FLOAT),
            ColumnType.newInstance(HSQLDB, "VARCHAR", "type.string", true, VARCHAR),
            ColumnType.newInstance(HSQLDB, "VARCHAR_IGNORECASE", "type.string", true, VARCHAR),
            ColumnType.newInstance(HSQLDB, "CHAR", "type.char", true, CHAR),
            ColumnType.newInstance(HSQLDB, "CHARACTER", "type.char", true, CHAR),
            ColumnType.newInstance(HSQLDB, "DECIMAL", "type.real", true, DECIMAL),
            ColumnType.newInstance(HSQLDB, "NUMERIC", "type.numeric", true, NUMERIC),
            ColumnType.newInstance(HSQLDB, "BOOLEAN", "type.boolean", true, BOOLEAN),
            ColumnType.newInstance(HSQLDB, "BIT", "type.bit", true, BIT),
            ColumnType.newInstance(HSQLDB, "TINYINT", "type.integer", true, TINYINT),
            ColumnType.newInstance(HSQLDB, "SMALLINT", "type.integer", true, SMALLINT),
            ColumnType.newInstance(HSQLDB, "BIGINT", "type.integer", true, BIGINT),
            ColumnType.newInstance(HSQLDB, "REAL", "type.real", true, REAL),
            ColumnType.newInstance(HSQLDB, "BINARY", "type.binary", true, BINARY),
            ColumnType.newInstance(HSQLDB, "VARBINARY", "type.binary", true, BINARY),
            ColumnType.newInstance(HSQLDB, "LONGVARBINARY", "type.binary", true, BINARY),
            ColumnType.newInstance(HSQLDB, "DATE", "type.date", false, DATE),
            ColumnType.newInstance(HSQLDB, "TIME", "type.time", false, TIME),
            ColumnType.newInstance(HSQLDB, "TIMESTAMP", "type.datetime", false, TIMESTAMP),
            ColumnType.newInstance(HSQLDB, "DATETIME", "type.datetime", false, TIMESTAMP),
            ColumnType.newInstance(HSQLDB, "OTHER", "type.other", false, OTHER),
            ColumnType.newInstance(HSQLDB, "OBJECT", "type.object", false, OTHER));

    public HsqldbDialect() {
        super(COLUMN_TYPES);
        setAutoIncrement(false);
    }

    @Override
    public DialectProvider getDialectProvider() {
        return DialectProvider.HSQLDB;
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
