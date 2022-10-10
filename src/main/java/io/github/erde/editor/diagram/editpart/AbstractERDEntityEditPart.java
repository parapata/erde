package io.github.erde.editor.diagram.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;

import io.github.erde.editor.diagram.editpart.command.CreateConnectionCommand;
import io.github.erde.editor.diagram.editpart.editpolicy.EntityLayoutEditPolicy;
import io.github.erde.editor.diagram.editpart.editpolicy.NodeEditPolicy;
import io.github.erde.editor.diagram.editpart.editpolicy.TableComponentEditPolicy;
import io.github.erde.editor.diagram.model.BaseConnectionModel;
import io.github.erde.editor.diagram.model.BaseEntityModel;

/**
 * AbstractERDEntityEditPart.
 *
 * @author modified by parapata
 */
public abstract class AbstractERDEntityEditPart extends AbstractERDEditPart implements NodeEditPart {

    /**
     * Creats a {@link CreateConnectionCommand} instance.
     * Override to return an instance of extended class to customize connection creation.
     *
     * @return the connection creation command
     */
    public CreateConnectionCommand newCreateConnectionCommand() {
        return new CreateConnectionCommand();
    }

    @Override
    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.COMPONENT_ROLE, new TableComponentEditPolicy());
        BaseEntityModel model = (BaseEntityModel) getModel();
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new NodeEditPolicy(model));
        installEditPolicy(EditPolicy.LAYOUT_ROLE, new EntityLayoutEditPolicy());
    }

    @Override
    protected void refreshVisuals() {
        BaseEntityModel model = (BaseEntityModel) getModel();
        if (model instanceof BaseEntityModel) {
            Rectangle constraint = model.getConstraint();
            ((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), constraint);
        }
    }

    @Override
    protected List<BaseConnectionModel> getModelSourceConnections() {
        return ((BaseEntityModel) getModel()).getModelSourceConnections();
    }

    @Override
    protected List<BaseConnectionModel> getModelTargetConnections() {
        return ((BaseEntityModel) getModel()).getModelTargetConnections();
    }

    @Override
    public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
        return new ChopboxAnchor(getFigure());
    }

    @Override
    public ConnectionAnchor getSourceConnectionAnchor(Request request) {
        return new ChopboxAnchor(getFigure());
    }

    @Override
    public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
        return new ChopboxAnchor(getFigure());
    }

    @Override
    public ConnectionAnchor getTargetConnectionAnchor(Request request) {
        return new ChopboxAnchor(getFigure());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void propertyChange(PropertyChangeEvent event) {
        refreshVisuals();
        refreshSourceConnections();
        refreshTargetConnections();

        invokePropertyChangeListener(event, getSourceConnections());
        invokePropertyChangeListener(event, getTargetConnections());
    }

    private void invokePropertyChangeListener(PropertyChangeEvent event, List<PropertyChangeListener> listeners) {
        for (PropertyChangeListener listener : new CopyOnWriteArrayList<>(listeners)) {
            listener.propertyChange(event);
        }
    }
}
