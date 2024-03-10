package io.github.parapata.erde.editor.diagram.editpart.editpolicy;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.parapata.erde.editor.diagram.editpart.AbstractERDEntityEditPart;
import io.github.parapata.erde.editor.diagram.editpart.command.CreateConnectionCommand;
import io.github.parapata.erde.editor.diagram.editpart.command.ReconnectSourceCommand;
import io.github.parapata.erde.editor.diagram.editpart.command.ReconnectTargetCommand;
import io.github.parapata.erde.editor.diagram.model.BaseConnectionModel;
import io.github.parapata.erde.editor.diagram.model.BaseEntityModel;

/**
 * NodeEditPolicy.
 *
 * @author modified by parapata
 */
public class NodeEditPolicy extends GraphicalNodeEditPolicy {

    private Logger logger = LoggerFactory.getLogger(NodeEditPolicy.class);

    private NodeEditPolicyData data = new NodeEditPolicyData();

    public NodeEditPolicy(BaseEntityModel model) {
        this.data.setModel(model);
    }

    @Override
    protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
        BaseConnectionModel connection = (BaseConnectionModel) request.getNewObject();
        BaseEntityModel model = (BaseEntityModel) getHost().getModel();
        if (!model.canSource(connection)) {
            return null;
        }
        CreateConnectionCommand command = ((AbstractERDEntityEditPart) request.getTargetEditPart())
                .newCreateConnectionCommand();

        command.setModel(data.getModel());
        command.setConnection(connection);
        command.setSource(model);
        request.setStartCommand(command);
        return command;
    }

    @Override
    protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
        BaseConnectionModel connection = ((CreateConnectionCommand) request.getStartCommand()).getConnection();
        BaseEntityModel model = (BaseEntityModel) getHost().getModel();
        if (!model.canTarget(connection)) {
            return null;
        }
        CreateConnectionCommand command = (CreateConnectionCommand) request.getStartCommand();
        command.setTarget(model);

        if (!command.canExecute()) {
            return null;
        }

        return command;
    }

    @Override
    protected Command getReconnectSourceCommand(ReconnectRequest request) {
        BaseConnectionModel connection = (BaseConnectionModel) request.getConnectionEditPart().getModel();
        BaseEntityModel model = (BaseEntityModel) getHost().getModel();
        if (!model.canSource(connection)) {
            return null;
        }
        ReconnectSourceCommand command = new ReconnectSourceCommand();
        command.setConnection(connection);
        command.setSource(model);
        return command;
    }

    @Override
    protected Command getReconnectTargetCommand(ReconnectRequest request) {
        BaseConnectionModel connection = (BaseConnectionModel) request.getConnectionEditPart().getModel();
        BaseEntityModel model = (BaseEntityModel) getHost().getModel();
        if (!model.canTarget(connection)) {
            return null;
        }
        ReconnectTargetCommand command = new ReconnectTargetCommand();
        command.setConnection(connection);
        command.setTarget(model);
        return command;
    }
}
