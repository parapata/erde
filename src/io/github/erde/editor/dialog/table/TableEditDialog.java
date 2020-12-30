package io.github.erde.editor.dialog.table;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;

import io.github.erde.IMessages;
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

    private TableModel editTableModel;

    private int editColumnIndex = -1;
    private int editIndexIndex = -1;

    private boolean indexEditing = false;

    private List<BaseConnectionModel> referenceKeyModels;
    private List<BaseConnectionModel> foreignKeyModels;

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
        this.editTableModel = tableModel.clone();
        this.editColumnIndex = tableModel.getColumns().indexOf(editColumnModel);
        this.editIndexIndex = tableModel.getIndices().indexOf(editIndexModel);
        this.indexEditing = indexEditing;

        //
        this.referenceKeyModels = tableModel.getModelSourceConnections();
        this.foreignKeyModels = tableModel.getModelTargetConnections();

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
        indexTabCreator.create(getShell(), tabFolder, editTableModel.getPhysicalName());

        return tabFolder;
    }

    @Override
    protected void okPressed() {
        super.okPressed();
    }

    @Override
    public IDialect getDialect() {
        return dialect;
    }

    @Override
    public List<ColumnModel> getColumns() {
        return editTableModel.getColumns();
    }

    @Override
    public List<IndexModel> getIndices() {
        return editTableModel.getIndices();
    }

    @Override
    public String getPhysicalName() {
        return editTableModel.getPhysicalName();
    }

    @Override
    public void setPhysicalName(String physicalName) {
        editTableModel.setPhysicalName(physicalName);
    }

    @Override
    public String getLogicalName() {
        return editTableModel.getLogicalName();
    }

    @Override
    public void setLogicalName(String logicalName) {
        editTableModel.setLogicalName(logicalName);
    }

    @Override
    public String getDescription() {
        return editTableModel.getDescription();
    }

    @Override
    public void setDescription(String description) {
        editTableModel.setDescription(description);
    }

    @Override
    public boolean isForeignkey(String physicalName) {
        for (BaseConnectionModel conn : foreignKeyModels) {
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
        for (BaseConnectionModel conn : referenceKeyModels) {
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
}
