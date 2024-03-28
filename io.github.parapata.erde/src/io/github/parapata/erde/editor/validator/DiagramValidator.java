package io.github.parapata.erde.editor.validator;

import static io.github.parapata.erde.Resource.*;
import static io.github.parapata.erde.preference.ErdePreferenceKey.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.preference.IPreferenceStore;

import io.github.parapata.erde.ErdePlugin;
import io.github.parapata.erde.core.util.StringUtils;
import io.github.parapata.erde.dialect.DialectProvider;
import io.github.parapata.erde.dialect.IDialect;
import io.github.parapata.erde.dialect.type.IColumnType;
import io.github.parapata.erde.editor.diagram.model.BaseConnectionModel;
import io.github.parapata.erde.editor.diagram.model.BaseEntityModel;
import io.github.parapata.erde.editor.diagram.model.ColumnModel;
import io.github.parapata.erde.editor.diagram.model.RelationshipMappingModel;
import io.github.parapata.erde.editor.diagram.model.RelationshipModel;
import io.github.parapata.erde.editor.diagram.model.RootModel;
import io.github.parapata.erde.editor.diagram.model.TableModel;

/**
 * The ER-Diagram validator.
 *
 * @author modified by parapata
 */
public class DiagramValidator {

    private RootModel model;

    private Set<String> tableNames = new HashSet<>();
    private Set<String> logicalNames = new HashSet<>();

    /**
     * The constructor.
     *
     * @param model
     *            the model for validation
     */
    public DiagramValidator(RootModel model) {
        this.model = model;
    }

    /**
     * Executes validation.
     *
     * @return validation errors
     */
    public DiagramErrorManager doValidate() {
        DiagramErrorManager deManager = new DiagramErrorManager();

        for (BaseEntityModel entity : model.getChildren()) {
            if (entity instanceof TableModel) {
                TableModel table = (TableModel) entity;
                table.setError("");
                validateTable(deManager, model, table);
            }
        }
        String dialectName = model.getDialectProvider().name();
        IDialect dialect = DialectProvider.getDialect(dialectName);
        dialect.validate(deManager, model);

        return deManager;
    }

    private void validateTable(DiagramErrorManager deManager, RootModel root, TableModel table) {

        IPreferenceStore store = ErdePlugin.getDefault().getPreferenceStore();

        // Validates TableModel
        String tableName = table.getPhysicalName();
        if (StringUtils.isEmpty(tableName)) {
            deManager.addError(
                    store.getString(VALIDATE_PHYSICAL_TABLE_NAME_REQUIRED),
                    table,
                    VALIDATION_ERROR_PHYSICAL_COLUMN_NAME_REQUIRED.getValue());

        } else if (tableNames.contains(tableName)) {
            deManager.addError(
                    store.getString(VALIDATE_PHYSICAL_TABLE_NAME_DUPLICATED),
                    table,
                    VALIDATION_ERROR_PHYSICAL_COLUMN_NAME_DUPLICATED.getValue());
        } else {
            tableNames.add(tableName);
        }

        String logicalName = table.getLogicalName();
        if (StringUtils.isEmpty(logicalName)) {
            deManager.addError(
                    store.getString(VALIDATE_LOGICAL_TABLE_NAME_REQUIRED),
                    table,
                    VALIDATION_ERROR_LOGICAL_TABLE_NAME_REQUIRED.getValue());

        } else if (logicalNames.contains(logicalName)) {
            deManager.addError(
                    store.getString(VALIDATE_LOGICAL_TABLE_NAME_DUPLICATED),
                    table,
                    VALIDATION_ERROR_LOGICAL_TABLE_NAME_DUPLICATED.getValue());
        } else {
            logicalNames.add(logicalName);
        }

        // Validates ColumnModels
        List<ColumnModel> columns = table.getColumns();
        if (columns.isEmpty()) {
            deManager.addError(
                    store.getString(VALIDATE_NO_COLUMNS),
                    table,
                    VALIDATION_ERROR_NO_COLUMNS.getValue());
        } else {
            Set<String> columnNames = new HashSet<>();
            Set<String> logicalColumnNames = new HashSet<>();
            boolean findPk = false;
            for (ColumnModel column : columns) {
                if (column.isPrimaryKey()) {
                    findPk = true;
                }
                String columnName = column.getPhysicalName();
                if (StringUtils.isEmpty(columnName)) {
                    deManager.addError(
                            store.getString(VALIDATE_PHYSICAL_COLUMN_NAME_REQUIRED),
                            table,
                            column,
                            VALIDATION_ERROR_PHYSICAL_COLUMN_NAME_REQUIRED.getValue());

                } else if (columnNames.contains(columnName)) {
                    deManager.addError(
                            store.getString(VALIDATE_PHYSICAL_COLUMN_NAME_DUPLICATED),
                            table,
                            column,
                            VALIDATION_ERROR_PHYSICAL_COLUMN_NAME_DUPLICATED.getValue());
                } else {
                    columnNames.add(columnName);
                }

                String logicalColumnName = column.getLogicalName();
                if (logicalColumnName == null || logicalColumnName.length() == 0) {
                    deManager.addError(
                            store.getString(VALIDATE_LOGICAL_COLUMN_NAME_REQUIRED),
                            table,
                            column,
                            VALIDATION_ERROR_LOGICAL_COLUMN_NAME_REQUIRED.getValue());

                } else if (logicalColumnNames.contains(logicalColumnName)) {
                    deManager.addError(
                            store.getString(VALIDATE_LOGICAL_COLUMN_NAME_DUPLICATED),
                            table,
                            column,
                            VALIDATION_ERROR_LOGICAL_COLUMN_NAME_DUPLICATED.getValue());
                } else {
                    logicalColumnNames.add(logicalColumnName);
                }
            }

            if (!findPk) {
                deManager.addError(
                        store.getString(VALIDATE_PRIMARY_KEY),
                        table,
                        VALIDATION_ERROR_NO_PRIMARY_KEY.getValue());
            }
        }

        // Validates Relations
        for (BaseConnectionModel conn : table.getModelSourceConnections()) {
            if (conn instanceof RelationshipModel) {
                RelationshipModel fk = (RelationshipModel) conn;
                for (RelationshipMappingModel mapping : fk.getMappings()) {
                    ColumnModel referer = mapping.getReferenceKey();
                    ColumnModel target = mapping.getForeignKey();

                    IColumnType refererType = referer.getColumnType();
                    IColumnType targetType = target.getColumnType();

                    if (!refererType.getPhysicalName().equals(targetType.getPhysicalName())) {
                        deManager.addError(store.getString(VALIDATE_FOREIGN_KEY_COLUMN_TYPE),
                                table,
                                referer,
                                VALIDATION_ERROR_FOREIGN_KEY_COLUMN_TYPE.getValue());
                        continue;
                    }
                    if (refererType.isSizeSupported() && !referer.getColumnSize().equals(target.getColumnSize())) {
                        deManager.addError(
                                store.getString(VALIDATE_FOREIGN_KEY_COLUMN_SIZE),
                                table,
                                referer,
                                VALIDATION_ERROR_FOREIGN_KEY_COLUMN_SIZE.getValue());
                        continue;
                    }
                }
            }
        }
    }
}
