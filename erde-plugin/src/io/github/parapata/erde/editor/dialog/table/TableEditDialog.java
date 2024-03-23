package io.github.parapata.erde.editor.dialog.table;

import static io.github.parapata.erde.Resource.*;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import io.github.parapata.erde.ErdePlugin;
import io.github.parapata.erde.ICON;
import io.github.parapata.erde.core.exception.ValidateException;
import io.github.parapata.erde.core.util.StringUtils;
import io.github.parapata.erde.core.util.swt.UIUtils;
import io.github.parapata.erde.dialect.DialectProvider;
import io.github.parapata.erde.dialect.IDialect;
import io.github.parapata.erde.editor.ErdeDiagramEditor;
import io.github.parapata.erde.editor.diagram.model.ColumnModel;
import io.github.parapata.erde.editor.diagram.model.IndexModel;
import io.github.parapata.erde.editor.diagram.model.RelationshipMappingModel;
import io.github.parapata.erde.editor.diagram.model.RelationshipModel;
import io.github.parapata.erde.editor.diagram.model.TableModel;
import io.github.parapata.erde.editor.dialog.table.composite.AttributeComposite;
import io.github.parapata.erde.editor.dialog.table.composite.DescriptionComposite;
import io.github.parapata.erde.editor.dialog.table.composite.IndexComposite;

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

    private List<RelationshipModel> referenceKeys;
    private List<RelationshipModel> foreignKeys;

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
            IndexModel editIndexModel) {

        super(parentShell);
        setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX | SWT.MIN);
        this.dialect = DialectProvider.getDialect(dialectName);
        this.editTable = tableModel.clone();
        this.editColumnIndex = tableModel.getColumns().indexOf(editColumnModel);
        this.editIndexIndex = tableModel.getIndices().indexOf(editIndexModel);

        this.referenceKeys = toRelationshipConnections(tableModel.getModelSourceConnections());
        this.foreignKeys = toRelationshipConnections(tableModel.getModelTargetConnections());
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(DIALOG_TABLE_TITLE.getValue());
        newShell.setImage(ErdePlugin.getImage(ICON.TABLE.getPath()));
    }

    @Override
    protected void constrainShellSize() {
        Shell shell = getShell();
        shell.pack();
        // shell.setSize(shell.getSize().x, 450);
        shell.setSize(860, 600);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        TabFolder tabFolder = new TabFolder(parent, SWT.NONE);
        tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));

        // Attribute tab
        Composite attribute = new AttributeComposite(tabFolder, this, editColumnIndex);
        TabItem attributeTab = new TabItem(tabFolder, SWT.NONE);
        attributeTab.setText(LABEL_ATTRIBUTE.getValue());
        attributeTab.setControl(attribute);

        // Table description tab
        Composite description = new DescriptionComposite(tabFolder, this);
        TabItem descriptionTab = new TabItem(tabFolder, SWT.NONE);
        descriptionTab.setText(LABEL_DESCRIPTION.getValue());
        descriptionTab.setControl(description);

        // Index tab
        // new IndexTabCreator(this, tabFolder, editIndexIndex, indexEditing, editTable.getPhysicalName());
        Composite index = new IndexComposite(tabFolder, this, editIndexIndex);
        TabItem indexTab = new TabItem(tabFolder, SWT.NONE);
        indexTab.setText(LABEL_INDEX.getValue());
        indexTab.setControl(index);

        if (editIndexIndex > -1) {
            tabFolder.setSelection(indexTab);
        } else {
            tabFolder.setSelection(attributeTab);
        }

        return tabFolder;
    }

    @Override
    protected void okPressed() {
        try {
            validate();
            super.okPressed();
        } catch (ValidateException e) {
            UIUtils.openAlertDialog(e);
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
    public List<RelationshipModel> getForeignKeyConnections() {
        return foreignKeys;
    }

    @Override
    public boolean isForeignkey(String id) {
        for (RelationshipModel conn : foreignKeys) {
            for (RelationshipMappingModel item : conn.getMappings()) {
                if (StringUtils.equals(id, item.getForeignKey().getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<RelationshipModel> getReferenceKeyConnections() {
        return referenceKeys;
    }

    @Override
    public boolean isReferenceKey(String id) {
        for (RelationshipModel conn : referenceKeys) {
            for (RelationshipMappingModel item : conn.getMappings()) {
                if (StringUtils.equals(id, item.getReferenceKey().getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void validate() throws ValidateException {

        List<TableModel> tables = ErdeDiagramEditor.getERDiagramRootModel().getTables();

        // -----
        // Physical table name check
        String tablePhysicalName = editTable.getPhysicalName();
        if (StringUtils.isEmpty(tablePhysicalName)) {
            throw new ValidateException(VALIDATION_ERROR_PHYSICAL_TABLE_NAME_REQUIRED);
        }

        if (tables.stream().anyMatch(table -> !StringUtils.equals(editTable.getId(), table.getId())
                && StringUtils.equalsIgnoreCase(tablePhysicalName, table.getPhysicalName()))) {
            throw new ValidateException(VALIDATION_ERROR_PHYSICAL_TABLE_NAME_DUPLICATED);
        }

        // -----
        // Logical table name check
        String tableLogicalName = editTable.getLogicalName();
        if (StringUtils.isEmpty(tableLogicalName)) {
            throw new ValidateException(VALIDATION_ERROR_LOGICAL_TABLE_NAME_REQUIRED);
        }

        if (tables.stream().anyMatch(table -> !StringUtils.equals(editTable.getId(), table.getId())
                && StringUtils.equals(tableLogicalName, table.getLogicalName()))) {
            throw new ValidateException(VALIDATION_ERROR_LOGICAL_TABLE_NAME_DUPLICATED);
        }

        // -----
        // Physical column name check
        if (editTable.getColumns()
                .stream()
                .map(column -> column.getPhysicalName())
                .anyMatch(columnName -> StringUtils.isEmpty(columnName))) {
            throw new ValidateException(VALIDATION_ERROR_PHYSICAL_TABLE_NAME_REQUIRED);
        }

        long count = editTable.getColumns()
                .stream()
                .map(column -> column.getPhysicalName())
                .distinct()
                .count();
        if (editTable.getColumns().size() != count) {
            throw new ValidateException(VALIDATION_ERROR_PHYSICAL_TABLE_NAME_DUPLICATED);
        }

        // -----
        // Logical column name check
        if (editTable.getColumns()
                .stream()
                .map(column -> column.getLogicalName())
                .anyMatch(columnName -> StringUtils.isEmpty(columnName))) {
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
