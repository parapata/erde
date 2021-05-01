package io.github.erde.dialect;

import static io.github.erde.Resource.*;
import static io.github.erde.dialect.DialectProvider.*;
import static java.sql.Types.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.github.erde.Activator;
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

        AtomicInteger count = new AtomicInteger();

        // Oracle Table Comments
        if (isComment()) {
            TableDependencyCalculator.getSortedTable(root).forEach(table -> {
                if (count.get() > 0) {
                    println();
                }
                print(String.format("COMMENT ON TABLE %s IS '%s'",
                        table.getPhysicalName(),
                        table.getLogicalName()));
                println(getSeparator());

                table.getColumns().forEach(column -> {
                    print(String.format("COMMENT ON COLUMN %s.%s IS '%s'",
                            table.getPhysicalName(),
                            column.getPhysicalName(),
                            column.getLogicalName()));
                    println(getSeparator());
                });
            });
        }

        // TODO シーケンス機能を検討する
        TableDependencyCalculator.getSortedTable(root).forEach(table -> {
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
