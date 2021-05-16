package io.github.erde.editor.dialog.table;

import static io.github.erde.Resource.*;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;

import io.github.erde.core.exception.ValidateException;
import io.github.erde.core.util.StringUtils;
import io.github.erde.core.util.swt.UIUtils;
import io.github.erde.dialect.DialectProvider;
import io.github.erde.dialect.IDialect;
import io.github.erde.editor.ERDiagramEditor;
import io.github.erde.editor.diagram.model.BaseConnectionModel;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.DomainModel;
import io.github.erde.editor.diagram.model.IndexModel;
import io.github.erde.editor.diagram.model.RelationshipMappingModel;
import io.github.erde.editor.diagram.model.RelationshipModel;
import io.github.erde.editor.diagram.model.TableModel;
import io.github.erde.editor.dialog.table.tabs.AttributeTab;
import io.github.erde.editor.dialog.table.tabs.DescriptionTab;
import io.github.erde.editor.dialog.table.tabs.IndexTab;

/**
 * The table dialog.
 *
 * @author modified by parapata
 */
public class TableEditDialog extends Dialog implements ITableEdit {

    private IDialect dialect;

    private TableModel editTable;

    private int editColumnIndex = -1;
    private int editIndexIndex = -1;

    private boolean indexEditing = false;

    private List<BaseConnectionModel> referenceKeys;
    private List<BaseConnectionModel> foreignKeys;

    private List<DomainModel> domains;

    /**
     * The constructor.
     *
     * @param parentShell the parent shell
     * @param dialectName the dialect name
     * @param tableModel the table model
     * @param editColumnModel the editing target column model
     * @param indexEditing the index editing
     * @param editIndexModel the index models
     */
    public TableEditDialog(Shell parentShell, String dialectName, TableModel tableModel, ColumnModel editColumnModel,
            boolean indexEditing, IndexModel editIndexModel, List<DomainModel> domains) {

        super(parentShell);
        setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX | SWT.MIN);
        this.dialect = DialectProvider.getDialect(dialectName);
        this.editTable = tableModel.clone();
        this.editColumnIndex = tableModel.getColumns().indexOf(editColumnModel);
        this.editIndexIndex = tableModel.getIndices().indexOf(editIndexModel);
        this.indexEditing = indexEditing;

        this.referenceKeys = tableModel.getModelSourceConnections();
        this.foreignKeys = tableModel.getModelTargetConnections();

        this.domains = domains;
    }

    @Override
    protected void constrainShellSize() {
        Shell shell = getShell();
        shell.pack();
        // shell.setSize(shell.getSize().x, 450);
        shell.setSize(860, 760);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        getShell().setText(DIALOG_TABLE_TITLE.getValue());

        TabFolder tabFolder = new TabFolder(parent, SWT.NULL);
        tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));

        // Attribute tab
        new AttributeTab(this, tabFolder, editColumnIndex, domains);

        // Table description tab
        new DescriptionTab(this, tabFolder);

        // Index tab
        // new IndexTabCreator(this, tabFolder, editIndexIndex, indexEditing, editTable.getPhysicalName());
        new IndexTab(this, tabFolder, editIndexIndex, indexEditing);

        return tabFolder;
    }

    @Override
    protected void okPressed() {
        try {
            validate();
            super.okPressed();
        } catch (ValidateException e) {
            UIUtils.openAlertDialog(ERROR_VALIDATION);
        }
    }

    @Override
    public IDialect getDialect() {
        return dialect;
    }

    @Override
    public List<ColumnModel> getColumns() {
        return editTable.getColumns();
    }

    @Override
    public List<IndexModel> getIndices() {
        return editTable.getIndices();
    }

    @Override
    public String getPhysicalName() {
        return editTable.getPhysicalName();
    }

    @Override
    public void setPhysicalName(String physicalName) {
        editTable.setPhysicalName(physicalName);
    }

    @Override
    public String getLogicalName() {
        return editTable.getLogicalName();
    }

    @Override
    public void setLogicalName(String logicalName) {
        editTable.setLogicalName(logicalName);
    }

    @Override
    public String getDescription() {
        return editTable.getDescription();
    }

    @Override
    public void setDescription(String description) {
        editTable.setDescription(description);
    }

    @Override
    public boolean isForeignkey(String physicalName) {
        for (BaseConnectionModel conn : foreignKeys) {
            if (conn instanceof RelationshipModel) {
                for (RelationshipMappingModel item : ((RelationshipModel) conn).getMappings()) {
                    if (physicalName.equals(item.getForeignKey().getPhysicalName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean isReferenceKey(String physicalName) {
        for (BaseConnectionModel conn : referenceKeys) {
            if (conn instanceof RelationshipModel) {
                for (RelationshipMappingModel item : ((RelationshipModel) conn).getMappings()) {
                    if (physicalName.equals(item.getReferenceKey().getPhysicalName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void validate() throws ValidateException {

        List<TableModel> tables = ERDiagramEditor.getERDiagramRootModel().getTables();

        // Physical table name check
        if (StringUtils.isEmpty(editTable.getPhysicalName())) {
            throw new ValidateException(VALIDATION_ERROR_PHYSICAL_TABLE_NAME_REQUIRED);
        }

        long count = tables.stream()
                .filter(table -> !StringUtils.equals(editTable.getId(), table.getId())
                        && StringUtils.equalsIgnoreCase(editTable.getPhysicalName(), table.getPhysicalName()))
                .count();
        if (count > 0) {
            throw new ValidateException(VALIDATION_ERROR_PHYSICAL_TABLE_NAME_DUPLICATED);
        }

        // Logical table name check
        if (StringUtils.isEmpty(editTable.getLogicalName())) {
            throw new ValidateException(VALIDATION_ERROR_LOGICAL_TABLE_NAME_REQUIRED);
        }

        count = tables.stream()
                .filter(table -> !StringUtils.equals(editTable.getId(), table.getId())
                        && StringUtils.equalsIgnoreCase(editTable.getLogicalName(), table.getLogicalName()))
                .count();
        if (count > 0) {
            throw new ValidateException(VALIDATION_ERROR_LOGICAL_TABLE_NAME_DUPLICATED);
        }

        // Physical column name check
        count = editTable.getColumns()
                .stream()
                .map(column -> column.getPhysicalName())
                .filter(columnName -> StringUtils.isNotEmpty(columnName))
                .count();
        if (editTable.getColumns().size() != count) {
            throw new ValidateException(VALIDATION_ERROR_PHYSICAL_TABLE_NAME_REQUIRED);
        }

        count = editTable.getColumns()
                .stream()
                .map(column -> column.getPhysicalName())
                .distinct()
                .count();
        if (editTable.getColumns().size() != count) {
            throw new ValidateException(VALIDATION_ERROR_PHYSICAL_TABLE_NAME_DUPLICATED);
        }

        // Logical column name check
        count = editTable.getColumns()
                .stream()
                .map(column -> column.getLogicalName())
                .filter(columnName -> StringUtils.isNotEmpty(columnName))
                .count();
        if (editTable.getColumns().size() != count) {
            throw new ValidateException(VALIDATION_ERROR_LOGICAL_COLUMN_NAME_REQUIRED);
        }

        count = editTable.getColumns()
                .stream()
                .map(column -> column.getLogicalName())
                .distinct()
                .count();
        if (editTable.getColumns().size() != count) {
            throw new ValidateException(VALIDATION_ERROR_LOGICAL_COLUMN_NAME_DUPLICATED);
        }
    }
}
