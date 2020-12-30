package io.github.erde.editor.dialog.table;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;

import io.github.erde.IMessages;
import io.github.erde.core.exception.ValidateException;
import io.github.erde.core.util.UIUtils;
import io.github.erde.dialect.DialectProvider;
import io.github.erde.dialect.IDialect;
import io.github.erde.editor.diagram.model.BaseConnectionModel;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.DomainModel;
import io.github.erde.editor.diagram.model.IndexModel;
import io.github.erde.editor.diagram.model.RelationshipMappingModel;
import io.github.erde.editor.diagram.model.RelationshipModel;
import io.github.erde.editor.diagram.model.TableModel;
import io.github.erde.editor.dialog.table.tabs.AttributeTabCreator;
import io.github.erde.editor.dialog.table.tabs.DescriptionTabCreator;
import io.github.erde.editor.dialog.table.tabs.IndexTabCreator;

/**
 * The table dialog.
 *
 * @author modified by parapata
 */
public class TableEditDialog extends Dialog implements ITableEdit, IMessages {

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
        setShellStyle(getShellStyle() | SWT.RESIZE);
        this.dialect = DialectProvider.getDialect(dialectName);
        this.editTable = tableModel.clone();
        this.editColumnIndex = tableModel.getColumns().indexOf(editColumnModel);
        this.editIndexIndex = tableModel.getIndices().indexOf(editIndexModel);
        this.indexEditing = indexEditing;

        //
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
        getShell().setText(getResource("dialog.table.title"));

        TabFolder tabFolder = new TabFolder(parent, SWT.NULL);
        tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));

        // Attribute tab
        AttributeTabCreator attributeTabCreator = new AttributeTabCreator(this, editColumnIndex, domains);
        attributeTabCreator.create(tabFolder);

        // Table description tab
        DescriptionTabCreator description = new DescriptionTabCreator(this);
        description.create(tabFolder);

        // Index tab
        IndexTabCreator indexTabCreator = new IndexTabCreator(this, editIndexIndex, indexEditing);
        indexTabCreator.create(getShell(), tabFolder, editTable.getPhysicalName());

        return tabFolder;
    }

    @Override
    protected void okPressed() {
        try {
            validate();
            super.okPressed();
        } catch (ValidateException e) {
            UIUtils.openAlertDialog(e.getMessage());
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

        List<TableModel> tables = UIUtils.getRootModel().getTables();

        // Physical table name check
        if (StringUtils.isEmpty(editTable.getPhysicalName())) {
            throw new ValidateException(getResource("validation.error.physicalTableName.required"));
        }

        long count = tables.stream()
                .filter(table -> !StringUtils.equals(editTable.getId(), table.getId())
                        && StringUtils.equalsIgnoreCase(editTable.getPhysicalName(), table.getPhysicalName()))
                .count();
        if (count > 0) {
            throw new ValidateException(getResource("validation.error.physicalTableName.duplicated"));
        }

        // Logical table name check
        if (StringUtils.isEmpty(editTable.getLogicalName())) {
            throw new ValidateException(getResource("validation.error.logicalTableName.required"));
        }

        count = tables.stream()
                .filter(table -> !StringUtils.equals(editTable.getId(), table.getId())
                        && StringUtils.equalsIgnoreCase(editTable.getLogicalName(), table.getLogicalName()))
                .count();
        if (count > 0) {
            throw new ValidateException(getResource("validation.error.logicalTableName.duplicated"));
        }

        // Physical column name check
        count = editTable.getColumns()
                .stream()
                .map(column -> column.getPhysicalName())
                .filter(columnName -> StringUtils.isNotEmpty(columnName))
                .count();
        if (editTable.getColumns().size() != count) {
            throw new ValidateException(getResource("validation.error.physicalColumnName.required"));
        }

        count = editTable.getColumns()
                .stream()
                .map(column -> column.getPhysicalName())
                .distinct()
                .count();
        if (editTable.getColumns().size() != count) {
            throw new ValidateException(getResource("validation.error.physicalColumnName.duplicated"));
        }

        // Logical column name check
        count = editTable.getColumns()
                .stream()
                .map(column -> column.getLogicalName())
                .filter(columnName -> StringUtils.isNotEmpty(columnName))
                .count();
        if (editTable.getColumns().size() != count) {
            throw new ValidateException(getResource("validation.error.logicalColumnName.required"));
        }

        count = editTable.getColumns()
                .stream()
                .map(column -> column.getLogicalName())
                .distinct()
                .count();
        if (editTable.getColumns().size() != count) {
            throw new ValidateException(getResource("validation.error.logicalColumnName.duplicated"));
        }
    }
}
