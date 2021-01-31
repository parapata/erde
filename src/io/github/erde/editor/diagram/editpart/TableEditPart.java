package io.github.erde.editor.diagram.editpart;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.erde.IMessages;
import io.github.erde.core.util.FontPropertyDescriptor;
import io.github.erde.editor.diagram.editpart.command.CreateConnectionCommand;
import io.github.erde.editor.diagram.editpart.command.CreateTableConnectionCommand;
import io.github.erde.editor.diagram.editpart.command.TableEditCommand;
import io.github.erde.editor.diagram.editpart.editpolicy.EntityLayoutEditPolicy;
import io.github.erde.editor.diagram.editpart.editpolicy.TableComponentEditPolicy;
import io.github.erde.editor.diagram.editpart.editpolicy.TableEditPolicy;
import io.github.erde.editor.diagram.editpart.editpolicy.TableSelectionEditPolicy;
import io.github.erde.editor.diagram.figure.ColumnFigure;
import io.github.erde.editor.diagram.figure.TableFigure;
import io.github.erde.editor.diagram.model.BaseConnectionModel;
import io.github.erde.editor.diagram.model.BaseEntityModel;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.DomainModel;
import io.github.erde.editor.diagram.model.IndexModel;
import io.github.erde.editor.diagram.model.RelationshipMappingModel;
import io.github.erde.editor.diagram.model.RelationshipModel;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;
import io.github.erde.editor.dialog.table.TableEditDialog;

/**
 * TableEditPart.
 *
 * @author modified by parapata
 */
public class TableEditPart extends AbstractERDEntityEditPart implements IMessages {

    private Logger logger = LoggerFactory.getLogger(TableEditPart.class);

    private Font font;

    /**
     * Creates a {@link CreateTableConnectionCommand} instance as the connection creation command.
     */
    @Override
    public CreateConnectionCommand newCreateConnectionCommand() {
        return new CreateTableConnectionCommand();
    }

    @Override
    protected void createEditPolicies() {
        BaseEntityModel model = (BaseEntityModel) getModel();
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new TableEditPolicy(model));
        installEditPolicy(EditPolicy.COMPONENT_ROLE, new TableComponentEditPolicy());
        installEditPolicy(EditPolicy.LAYOUT_ROLE, new EntityLayoutEditPolicy());
        installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new TableSelectionEditPolicy());
    }

    @Override
    protected IFigure createFigure() {
        TableFigure figure = new TableFigure();
        updateFigure(figure);
        return figure;
    }

    @Override
    public void deactivate() {
        super.deactivate();
        if (font != null) {
            font.dispose();
        }
    }

    @Override
    protected void refreshVisuals() {
        super.refreshVisuals();
        updateFigure((TableFigure) getFigure());
        refreshChildren();
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (BaseEntityModel.P_TARGET_CONNECTION.equals(event.getPropertyName())) {
        }
        super.propertyChange(event);
    }

    @Override
    public void doubleClicked() {
        TableModel model = (TableModel) getModel();
        openTableEditDialog(getViewer(), model);
    }

    private void updateFigure(TableFigure figure) {

        if (font != null) {
            font.dispose();
        }

        RootModel root = (RootModel) getParent().getModel();
        FontData[] fontData = FontPropertyDescriptor.toFontData(root.getFontData());
        font = new Font(Display.getDefault(), fontData);
        figure.setFont(font);

        TableModel table = (TableModel) getModel();
        if (root.isLogicalMode()) {
            figure.setTableName(table.getLogicalName());
        } else {
            figure.setTableName(table.getPhysicalName());
        }
        figure.setErrorMessage(table.getError());
        figure.clearColumns();
        // figure.setBackgroundColor(Activator.getDefault().getColor(table.getBackgroundColor()));

        List<ColumnModel> columns = table.getColumns();
        for (ColumnModel column : columns) {
            ColumnFigure[] figures = createColumnFigure(root, table, column);
            figure.add(figures[0]);
            figure.add(figures[1]);
            figure.add(figures[2]);
        }
    }

    private ColumnFigure[] createColumnFigure(RootModel root, TableModel table, ColumnModel model) {
        ColumnFigure lblColumnName = new ColumnFigure();
        ColumnFigure lblColumnType = new ColumnFigure();
        ColumnFigure lblNotNull = new ColumnFigure();

        lblColumnName.setUnderline(model.isPrimaryKey());
        lblColumnType.setUnderline(model.isPrimaryKey());
        lblNotNull.setUnderline(model.isPrimaryKey());

        boolean foreignKey = isForeignkey(table.getModelTargetConnections(), model.getPhysicalName());

        String columnName = root.isLogicalMode() ? model.getLogicalName() : model.getPhysicalName();
        if (model.isPrimaryKey() && foreignKey) {
            columnName = String.format("%s(PK+FK)", columnName);
        } else if (model.isPrimaryKey()) {
            columnName = String.format("%s(PK)", columnName);
        } else if (foreignKey) {
            columnName = String.format("%s(FK)", columnName);
        }

        if (model.isPrimaryKey()) {
            lblColumnName.setForegroundColor(ColorConstants.red);
            lblColumnType.setForegroundColor(ColorConstants.red);
            lblNotNull.setForegroundColor(ColorConstants.red);
        } else if (foreignKey) {
            lblColumnName.setForegroundColor(ColorConstants.blue);
            lblColumnType.setForegroundColor(ColorConstants.blue);
            lblNotNull.setForegroundColor(ColorConstants.blue);
        }
        lblColumnName.setText(columnName);

        String columnType;
        if (root.isLogicalMode()) {
            columnType = model.getColumnType().getLogicalName();
        } else {
            columnType = model.getColumnType().getPhysicalName();
        }

        if (model.getColumnType().isSizeSupported()) {
            if (model.getColumnSize() != null && model.getDecimal() != null) {
                columnType = String.format("%s(%d,%d)", columnType, model.getColumnSize(), model.getDecimal());
            } else if (model.getColumnSize() != null && model.getDecimal() == null) {
                columnType = String.format("%s(%d)", columnType, model.getColumnSize(), model.getDecimal());
            }
        }
        lblColumnType.setText(columnType);

        LOOP: for (IndexModel index : table.getIndices()) {
            if (index.getIndexType().getName().equals("UNIQUE")) {
                for (String name : index.getColumns()) {
                    if (name.equals(model.getPhysicalName())) {
                        lblColumnName.setText(String.format("%s(UQ+)", lblColumnName.getText()));
                        if (!model.isPrimaryKey()) {
                            lblColumnName.setForegroundColor(ColorConstants.darkGreen);
                            lblColumnType.setForegroundColor(ColorConstants.darkGreen);
                            lblNotNull.setForegroundColor(ColorConstants.darkGreen);
                        }
                        break LOOP;
                    }
                }
            }
        }

        if (model.isNotNull()) {
            lblNotNull.setText("(NN)");
        }

        return new ColumnFigure[] { lblColumnName, lblColumnType, lblNotNull };
    }

    private boolean isForeignkey(List<BaseConnectionModel> list, String columnName) {
        for (BaseConnectionModel conn : list) {
            if (conn instanceof RelationshipModel) {
                for (RelationshipMappingModel relationship : ((RelationshipModel) conn).getMappings()) {
                    if (columnName.equals(relationship.getForeignKey().getPhysicalName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Opens the {@link TableEditDialog}.
     *
     * @param viewer the viewer
     * @param tableModel the table model
     */
    public static void openTableEditDialog(EditPartViewer viewer, TableModel tableModel) {
        openTableEditDialog(viewer, tableModel, (ColumnModel) null);
    }

    /**
     * Opens the {@link TableEditDialog} to edit a given column.
     *
     * @param viewer the viewer
     * @param table the table model
     * @param editColumn the editing target column model
     */
    public static void openTableEditDialog(EditPartViewer viewer, TableModel table, ColumnModel editColumn) {

        Shell shell = viewer.getControl().getShell();
        RootModel root = getRootModel(viewer);
        String dialectName = root.getDialectProvider().name();
        List<DomainModel> domains = root.getDomains();

        TableEditDialog dialog = new TableEditDialog(shell, dialectName, table, editColumn, false, null, domains);

        if (dialog.open() == Window.OK) {
            Command command = new TableEditCommand(table, dialog);

            CommandStack commandStack = viewer.getEditDomain().getCommandStack();
            commandStack.execute(command);
        }
    }

    /**
     * Opens the {@link TableEditDialog} to edit a given index.
     *
     * @param viewer the viewer
     * @param table the table model
     * @param editIndex the editing target index model
     */
    public static void openTableEditDialog(EditPartViewer viewer, TableModel table, IndexModel editIndex) {

        Shell shell = viewer.getControl().getShell();
        RootModel root = getRootModel(viewer);
        String dialectName = root.getDialectProvider().name();
        List<DomainModel> domains = root.getDomains();

        TableEditDialog dialog = new TableEditDialog(shell, dialectName, table, null, true, editIndex, domains);

        if (dialog.open() == Window.OK) {
            Command command = new TableEditCommand(table, dialog);

            CommandStack commandStack = viewer.getEditDomain().getCommandStack();
            commandStack.execute(command);
        }
    }

    private static RootModel getRootModel(EditPartViewer viewer) {
        EditPart rootEditPart = viewer.getContents();
        return (RootModel) rootEditPart.getModel();
    }
}
