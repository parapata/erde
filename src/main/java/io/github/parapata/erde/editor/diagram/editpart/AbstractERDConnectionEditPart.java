package io.github.parapata.erde.editor.diagram.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import io.github.parapata.erde.editor.diagram.editpart.command.DeleteConnectionCommand;
import io.github.parapata.erde.editor.diagram.model.BaseConnectionModel;
import io.github.parapata.erde.editor.diagram.model.BaseModel;

/**
 * AbstractERDConnectionEditPart.
 *
 * @author modified by parapata
 */
abstract class AbstractERDConnectionEditPart extends AbstractConnectionEditPart implements PropertyChangeListener {

    @Override
    protected IFigure createFigure() {
        return super.createFigure();
    }

    @Override
    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.COMPONENT_ROLE, new EntityComponentEditPolicy());
        installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());
    }

    @Override
    public void activate() {
        super.activate();
        getModel().addPropertyChangeListener(this);
    }

    @Override
    public void deactivate() {
        super.deactivate();
        getModel().removePropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        refreshVisuals();
    }

    @Override
    public BaseModel getModel() {
        return (BaseModel) super.getModel();
    }

    /** EditPolicy for Entity */
    protected class EntityComponentEditPolicy extends ComponentEditPolicy {
        @Override
        protected Command createDeleteCommand(GroupRequest deleteRequest) {
            BaseConnectionModel model = (BaseConnectionModel) getModel();
            DeleteConnectionCommand command = new DeleteConnectionCommand(model);
            return command;
        }
    }

}
