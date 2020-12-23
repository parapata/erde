package io.github.erde.editor.diagram.editpart.editpolicy;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import io.github.erde.editor.diagram.editpart.command.DeleteTableCommand;

/**
 * TableComponentEditPolicy.
 *
 * @author modified by parapata
 */
public class TableComponentEditPolicy extends ComponentEditPolicy {

    @Override
    protected Command createDeleteCommand(GroupRequest deleteRequest) {
        DeleteTableCommand command = new DeleteTableCommand();
        command.setRootModel(getHost().getParent().getModel());
        command.setTargetModel(getHost().getModel());
        return command;
    }
}
