package io.github.parapata.erde.dialect;

import static io.github.parapata.erde.Resource.*;
import static io.github.parapata.erde.dialect.DialectProvider.*;
import static java.sql.Types.*;

import java.util.Arrays;
import java.util.List;

import io.github.parapata.erde.ERDPlugin;
import io.github.parapata.erde.dialect.type.ColumnType;
import io.github.parapata.erde.dialect.type.IColumnType;
import io.github.parapata.erde.editor.diagram.model.BaseEntityModel;
import io.github.parapata.erde.editor.diagram.model.ColumnModel;
import io.github.parapata.erde.editor.diagram.model.IndexModel;
import io.github.parapata.erde.editor.diagram.model.RootModel;
import io.github.parapata.erde.editor.diagram.model.TableModel;
import io.github.parapata.erde.editor.validator.DiagramErrorManager;

/**
 * OracleDialect.
 *
 * @author modified by parapata
 */
public class OracleDialect extends AbstractDialect {

    private static final List<IColumnType> COLUMN_TYPES = Arrays.asList(
            ColumnType.newInstance(Oracle, "NUMBER", TYPE_NUMERIC, true, NUMERIC),
            ColumnType.newInstance(Oracle, "INTEGER", TYPE_INTEGER, false, INTEGER),
            ColumnType.newInstance(Oracle, "BINARY_FLOAT", TYPE_BIT, false, FLOAT),
            ColumnType.newInstance(Oracle, "DOUBLE PRECISION", TYPE_REAL, false, DOUBLE),
            ColumnType.newInstance(Oracle, "VARCHAR2", TYPE_STRING, true, VARCHAR),
            ColumnType.newInstance(Oracle, "CHAR", TYPE_CHAR, true, CHAR),
            ColumnType.newInstance(Oracle, "CLOB", TYPE_STRING, true, CLOB),
            ColumnType.newInstance(Oracle, "LONG", TYPE_STRING, false, CLOB),
            ColumnType.newInstance(Oracle, "DATE", TYPE_DATE, false, DATE),
            ColumnType.newInstance(Oracle, "TIMESTAMP", TYPE_DATE, false, TIMESTAMP),
            ColumnType.newInstance(Oracle, "RAW", TYPE_BINARY, false, BINARY),
            ColumnType.newInstance(Oracle, "BLOB", TYPE_BINARY, false, BLOB));

    public OracleDialect() {
        super(COLUMN_TYPES);
    }

    @Override
    public DialectProvider getDialectProvider() {
        return DialectProvider.Oracle;
    }

    @Override
    public String dropTableDDL(String tableName) {
        return String.format("DROP TABLE %s CASCADE CONSTRAINTS", tableName);
    }

    @Override
    public void additions(RootModel root) {

        // Table Comments
        super.additions(root);

        // TODO シーケンス機能を検討する
        TableDependencyCalculator.getSortedTable(root.getTables()).forEach(table -> {
            table.getColumns().forEach(column -> {
                if (column.isAutoIncrement()) {
                    String seqName = String.format("%s_%s_SEQ", table.getPhysicalName(), column.getPhysicalName());
                    String triggerName = String.format("%s_%s_TRG", table.getPhysicalName(), column.getPhysicalName());

                    if (isDrop()) {
                        print(String.format("DROP SEQUENCE %s", seqName));
                        println(getSeparator());
                    }

                    print(String.format("CREATE SEQUENCE %s NOMAXVALUE NOCACHE NOORDER NOCYCLE", seqName));
                    println(getSeparator());

                    println(String.format("CREATE TRIGGER %s", triggerName));
                    println(String.format("BEFORE INSERT ON %s", table.getPhysicalName()));
                    println(String.format("FOR EACH ROW"));
                    println(String.format("BEGIN"));
                    println(String.format("IF :NEW.%s IS NOT NULL THEN", column.getPhysicalName()));
                    print(String.format("    SELECT %s.NEXTVAL INTO :NEW.%s FROM DUAL",
                            seqName, column.getPhysicalName()));
                    println(getSeparator());
                    println(String.format("INTO :NEW.%s FROM DUAL", column.getPhysicalName()));
                    println(getSeparator());
                    print(String.format("END IF"));
                    println(getSeparator());
                    print(String.format("END"));
                    println(getSeparator());
                }
            });
        });
    }

    @Override
    public String getColumnMetadataSQL(String tableName) {
        return String.format("SELECT * FROM %s WHERE ROWNUM = 1", tableName);
    }

    // TODO Should Oracle validation levels be customizable?
    @Override
    public void validate(DiagramErrorManager deManager, RootModel root) {
        for (BaseEntityModel entity : root.getChildren()) {
            if (entity instanceof TableModel) {
                TableModel table = (TableModel) entity;
                String tableName = table.getPhysicalName();
                if (tableName.length() > 30) {
                    deManager.addError(ERDPlugin.LEVEL_ERROR,
                            table, VALIDATION_ERROR_ORACLE_TABLE_NAME_LENGTH.getValue());
                }

                for (ColumnModel column : table.getColumns()) {
                    String columnName = column.getPhysicalName();
                    if (columnName.length() > 30) {
                        deManager.addError(ERDPlugin.LEVEL_ERROR, table, column,
                                VALIDATION_ERROR_ORACLE_COLUMN_NAME_LENGTH.getValue());
                    }
                }

                for (IndexModel index : table.getIndices()) {
                    String indexName = index.getIndexName();
                    if (indexName.length() > 30) {
                        deManager.addError(ERDPlugin.LEVEL_ERROR, table, index,
                                VALIDATION_ERROR_ORACLE_INDEX_NAME_LENGTH.getValue());
                    }
                }
            }
        }
    }
}
