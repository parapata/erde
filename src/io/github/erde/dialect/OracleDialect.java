package io.github.erde.dialect;

import static io.github.erde.Resource.*;
import static io.github.erde.dialect.DialectProvider.*;
import static java.sql.Types.*;

import java.util.Arrays;
import java.util.List;

import io.github.erde.Activator;
import io.github.erde.core.util.JDBCConnection;
import io.github.erde.dialect.loader.ISchemaLoader;
import io.github.erde.dialect.loader.OracleSchemaLoader;
import io.github.erde.dialect.type.ColumnType;
import io.github.erde.dialect.type.IColumnType;
import io.github.erde.editor.diagram.model.BaseEntityModel;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.IndexModel;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;
import io.github.erde.editor.validator.DiagramErrorManager;

/**
 * OracleDialect.
 *
 * @author modified by parapata
 */
public class OracleDialect extends AbstractDialect {

    private static final List<IColumnType> COLUMN_TYPES = Arrays.asList(
            ColumnType.newInstance(Oracle, "NUMBER", "type.numeric", true, NUMERIC),
            ColumnType.newInstance(Oracle, "INTEGER", "type.integer", false, INTEGER),
            ColumnType.newInstance(Oracle, "BINARY_FLOAT", "type.bit", false, FLOAT),
            ColumnType.newInstance(Oracle, "DOUBLE PRECISION", "type.real", false, DOUBLE),
            ColumnType.newInstance(Oracle, "VARCHAR2", "type.string", true, VARCHAR),
            ColumnType.newInstance(Oracle, "CHAR", "type.char", true, CHAR),
            ColumnType.newInstance(Oracle, "CLOB", "type.string", true, CLOB),
            ColumnType.newInstance(Oracle, "LONG", "type.string", false, CLOB),
            ColumnType.newInstance(Oracle, "DATE", "type.date", false, DATE),
            ColumnType.newInstance(Oracle, "TIMESTAMP", "type.date", false, TIMESTAMP),
            ColumnType.newInstance(Oracle, "RAW", "type.binary", false, BINARY),
            ColumnType.newInstance(Oracle, "BLOB", "type.binary", false, BLOB));

    public OracleDialect() {
        super(COLUMN_TYPES);
        setAutoIncrement(false);
    }

    @Override
    public DialectProvider getDialectProvider() {
        return DialectProvider.Oracle;
    }

    @Override
    public ISchemaLoader getSchemaLoader(JDBCConnection jdbcConn) {
        return new OracleSchemaLoader(this, jdbcConn);
    }

    @Override
    public void createTableDDL(RootModel root, TableModel model, StringBuilder ddl, StringBuilder additions) {

        super.createTableDDL(root, model, ddl, additions);

        StringBuilder sbComment = new StringBuilder();

        // Oracle Table Comments
        if (isComment()) {
            sbComment.append("COMMENT ON TABLE ").append(model.getPhysicalName()).append(" IS ");
            sbComment.append("'");
            sbComment.append(model.getLogicalName());
            sbComment.append("'");
            sbComment.append(getSeparator());
            sbComment.append(LS);
        }
        for (ColumnModel column : model.getColumns()) {
            if (column.isAutoIncrement()) {
                String seqName = String.format("%s_%s_SEQ", model.getPhysicalName(), column.getPhysicalName());
                String triggerName = String.format("%s_%s_TRG", model.getPhysicalName(), column.getPhysicalName());

                if (isDrop()) {
                    ddl.append("DROP SEQUENCE ").append(seqName).append(getSeparator()).append(LS);
                    // sb.append("DROP TRIGGER ").append(triggerName).append(getSeparator()).append(LS);
                    ddl.append(LS);
                }

                ddl.append("CREATE SEQUENCE ");
                ddl.append(seqName);
                ddl.append(" NOMAXVALUE NOCACHE NOORDER NOCYCLE;");
                ddl.append(getSeparator());
                ddl.append(LS);

                ddl.append(LS);

                ddl.append("CREATE TRIGGER ");
                ddl.append(triggerName).append(LS);
                ddl.append("BEFORE INSERT ON ").append(model.getPhysicalName()).append(LS);
                ddl.append("FOR EACH ROW").append(LS);
                ddl.append("BEGIN").append(LS);
                ddl.append("IF :NEW.").append(column.getPhysicalName()).append(" IS NOT NULL THEN").append(LS);
                ddl.append("  SELECT ").append(seqName).append(".NEXTVAL ");
                ddl.append("INTO :NEW.").append(column.getPhysicalName()).append(" FROM DUAL;").append(LS);
                ddl.append("END IF;").append(LS);
                ddl.append("END;").append(LS);
            }

            if (isComment()) {
                if (column.getLogicalName() != null && column.getLogicalName().length() > 0) {
                    sbComment.append("COMMENT ON COLUMN ").append(model.getPhysicalName()).append(".");
                    sbComment.append(column.getPhysicalName()).append(" IS ");
                    sbComment.append("'").append(column.getLogicalName()).append("'");
                    sbComment.append(getSeparator());
                    sbComment.append(LS);
                }
            }
        }

        if (sbComment.length() > 0) {
            ddl.append(sbComment).append(LS);
        }
    }

    @Override
    public String dropTableDDL(String tableName) {
        return String.format("DROP TABLE %s  CASCADE CONSTRAINTS;", tableName);
    }

    @Override
    public String getColumnMetadataSQL(String tableName) {
        return String.format("SELECT * FROM %s WHERE ROWNUM = 1", tableName);
    }

    // TODO Should Oracle validation levels be customizable?
    @Override
    public void validate(DiagramErrorManager deManager, RootModel model) {
        for (BaseEntityModel entity : model.getChildren()) {
            if (entity instanceof TableModel) {
                TableModel table = (TableModel) entity;
                String tableName = table.getPhysicalName();
                if (tableName.length() > 30) {
                    deManager.addError(Activator.LEVEL_ERROR,
                            table, VALIDATION_ERROR_ORACLE_TABLE_NAME_LENGTH.getValue());
                }

                for (ColumnModel column : table.getColumns()) {
                    String columnName = column.getPhysicalName();
                    if (columnName.length() > 30) {
                        deManager.addError(Activator.LEVEL_ERROR, table, column,
                                VALIDATION_ERROR_ORACLE_COLUMN_NAME_LENGTH.getValue());
                    }
                }

                for (IndexModel index : table.getIndices()) {
                    String indexName = index.getIndexName();
                    if (indexName.length() > 30) {
                        deManager.addError(Activator.LEVEL_ERROR, table, index,
                                VALIDATION_ERROR_ORACLE_INDEX_NAME_LENGTH.getValue());
                    }
                }
            }
        }
    }
}
