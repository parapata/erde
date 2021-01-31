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
 * H2Dialect.
 *
 * @author modified by parapata
 */
public class H2Dialect extends AbstractDialect {

    private static final List<IColumnType> COLUMN_TYPES = Arrays.asList(
            ColumnType.newInstance(H2, "IDENTITY", "type.integer", false, INTEGER),
            ColumnType.newInstance(H2, "INT", "type.integer", false, INTEGER),
            ColumnType.newInstance(H2, "INTEGER", "type.integer", false, INTEGER),
            ColumnType.newInstance(H2, "BOOLEAN", "type.boolean", false, BOOLEAN),
            ColumnType.newInstance(H2, "BIT", "type.boolean", false, BOOLEAN),
            ColumnType.newInstance(H2, "BOOL", "type.boolean", false, BOOLEAN),
            ColumnType.newInstance(H2, "TINYINT", "type.integer", false, TINYINT),
            ColumnType.newInstance(H2, "BIGINT", "type.integer", false, BIGINT),
            ColumnType.newInstance(H2, "DECIMAL", "type.numeric", true, DECIMAL),
            ColumnType.newInstance(H2, "NUMBER", "type.numeric", true, DECIMAL),
            ColumnType.newInstance(H2, "NUMERIC", "type.numeric", true, DECIMAL),
            ColumnType.newInstance(H2, "DOUBLE", "type.real", false, DOUBLE),
            ColumnType.newInstance(H2, "FLOAT", "type.real", false, DOUBLE),
            ColumnType.newInstance(H2, "REAL", "type.real", false, REAL),
            ColumnType.newInstance(H2, "TIME", "type.time", false, TIME),
            ColumnType.newInstance(H2, "DATE", "type.date", false, DATE),
            ColumnType.newInstance(H2, "TIMESTAMP", "type.datetime", false, TIMESTAMP),
            ColumnType.newInstance(H2, "DATETIME", "type.datetime", false, TIMESTAMP),
            ColumnType.newInstance(H2, "BINATY", "type.binary", true, BINARY),
            ColumnType.newInstance(H2, "OBJECT", "type.object", false, OTHER),
            ColumnType.newInstance(H2, "VARCHAR", "type.string", true, VARCHAR),
            ColumnType.newInstance(H2, "VARCHAR_CASESENSITIVE", "type.string", true, VARCHAR),
            ColumnType.newInstance(H2, "VARCHAR_IGNORECASE", "type.string", true, VARCHAR),
            ColumnType.newInstance(H2, "CHAR", "type.char", true, CHAR),
            ColumnType.newInstance(H2, "CHARACTER", "type.char", true, CHAR),
            ColumnType.newInstance(H2, "BLOB", "type.binary", false, BLOB),
            ColumnType.newInstance(H2, "CLOB", "type.string", true, CLOB),
            ColumnType.newInstance(H2, "TEXT", "type.string", true, CLOB),
            ColumnType.newInstance(H2, "ARRAY", "type.string", true, ARRAY));

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
