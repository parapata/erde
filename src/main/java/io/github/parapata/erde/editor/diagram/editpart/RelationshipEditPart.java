package io.github.parapata.erde.editor.diagram.editpart;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.parapata.erde.core.util.StringUtils;
import io.github.parapata.erde.core.util.swt.FontDataWrapper;
import io.github.parapata.erde.editor.diagram.editpart.command.DeleteConnectionCommand;
import io.github.parapata.erde.editor.diagram.editpart.command.TableEditCommand;
import io.github.parapata.erde.editor.diagram.figure.connection.RelationshipConnection;
import io.github.parapata.erde.editor.diagram.figure.connection.decoration.Decoration;
import io.github.parapata.erde.editor.diagram.figure.connection.decoration.DecorationFactory;
import io.github.parapata.erde.editor.diagram.model.BaseEntityModel;
import io.github.parapata.erde.editor.diagram.model.RelationshipMappingModel;
import io.github.parapata.erde.editor.diagram.model.RelationshipModel;
import io.github.parapata.erde.editor.diagram.model.RootModel;
import io.github.parapata.erde.editor.diagram.model.TableModel;
import io.github.parapata.erde.editor.dialog.relationship.RelationshipDialog;

/**
 * RelationshipEditPart.
 *
 * @author modified by parapata
 */
public class RelationshipEditPart extends AbstractERDConnectionEditPart implements IDoubleClickSupport {

    private Logger logger = LoggerFactory.getLogger(RelationshipEditPart.class);

    private Label label;
    private Font font;

    @Override
    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.COMPONENT_ROLE, new EntityComponentEditPolicy());
        installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());
        // installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE, new BendpointEditPolicy());
    }

    @Override
    protected IFigure createFigure() {
        PolylineConnection connection = new RelationshipConnection();
        label = new Label();
        label.setLabelAlignment(PositionConstants.CENTER);
        label.setOpaque(true);
        label.setBackgroundColor(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
        updateConnection(connection);
        connection.add(label, new ConnectionLocator(connection, ConnectionLocator.MIDDLE));
        connection.setLineWidth(10);
        return connection;
    }

    @Override
    protected void refreshVisuals() {
        super.refreshVisuals();

        if (font != null) {
            font.dispose();
        }

        if (getRoot() != null) {
            RootModel root = (RootModel) getRoot().getContents().getModel();
            FontData[] fontData = FontDataWrapper.toFontData(root.getFontData());
            font = new Font(Display.getDefault(), fontData);
            figure.setFont(font);
            updateConnection((PolylineConnection) getFigure());
        }
    }

    @Override
    public void deactivate() {
        super.deactivate();
        if (font != null) {
            font.dispose();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (BaseEntityModel.P_TARGET_CONNECTION.equals(event.getPropertyName())
                && event.getNewValue() != null
                && event.getNewValue() instanceof RelationshipModel
                && isOpenDialog((RelationshipModel) event.getNewValue())) {

            RelationshipModel relationshipModel = (RelationshipModel) getModel();
            RelationshipDialog dialog = new RelationshipDialog(
                    getViewer().getControl().getShell(),
                    relationshipModel,
                    ((RootModel) getRoot().getContents().getModel()).isLogicalMode());

            CommandStack commandStack = getViewer().getEditDomain().getCommandStack();
            if (dialog.open() == Window.OK) {
                TableModel model = relationshipModel.getTarget();
                commandStack.execute(new TableEditCommand(model, model));
            } else {
                DeleteConnectionCommand command = new DeleteConnectionCommand(relationshipModel);
                commandStack.execute(command);
            }
        }
        super.propertyChange(event);
    }

    @Override
    protected void refreshTargetConnections() {
        super.refreshTargetConnections();
    }

    @Override
    public void doubleClicked() {
        getForeignKeyColumns();
        RelationshipModel relationshipModel = (RelationshipModel) getModel();
        RelationshipDialog dialog = new RelationshipDialog(
                getViewer().getControl().getShell(),
                relationshipModel,
                ((RootModel) getRoot().getContents().getModel()).isLogicalMode());

        if (dialog.open() == Window.OK) {
            EditPartViewer viewer = getViewer();
            TableModel model = relationshipModel.getTarget();
            CommandStack commandStack = viewer.getEditDomain().getCommandStack();
            commandStack.execute(new TableEditCommand(model, model));
        }
    }

    private boolean isOpenDialog(RelationshipModel newRelationship) {
        RelationshipModel relationship = (RelationshipModel) getModel();
        return StringUtils.equals(newRelationship.getSource().getId(), relationship.getSource().getId());
    }

    private void updateConnection(PolylineConnection connection) {
        RootModel root = (RootModel) getRoot().getContents().getModel();
        RelationshipModel model = (RelationshipModel) getModel();
        StringBuilder sb = new StringBuilder();
        for (RelationshipMappingModel mapping : model.getMappings()) {
            try {
                if (sb.length() != 0) {
                    sb.append("\n");
                }
                sb.append(mapping.getDisplayString(root.isLogicalMode()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        label.setText(sb.toString());

        // notation(表記) : IDEF1X ro IE
        Decoration decoration = DecorationFactory.getDecoration(
                root.getNotation(),
                model.getSourceCardinality(),
                model.getTargetCardinality());

        connection.setSourceDecoration(decoration.getSourceDecoration());
        connection.setTargetDecoration(decoration.getTargetDecoration());
        connection.setLineStyle(Graphics.LINE_DASH);

        for (RelationshipMappingModel mapping : model.getMappings()) {
            if (mapping.getReferenceKey() != null && mapping.getReferenceKey().isPrimaryKey()) {
                connection.setLineStyle(Graphics.LINE_SOLID);
                break;
            }
        }
    }

    public List<String> getForeignKeyColumns() {
        TableModel table = (TableModel) getTarget().getModel();
        List<RelationshipModel> list = table.getModelTargetConnections().stream()
                .filter(predicate -> (predicate instanceof RelationshipModel))
                .map(relationship -> ((RelationshipModel) relationship))
                .collect(Collectors.toList());

        List<String> result = new ArrayList<>();
        list.forEach(relationship -> {
            relationship.getMappings().forEach(model -> {
                result.add(model.getForeignKey().getPhysicalName());
            });
        });
        return result;
    }
}
