package io.github.parapata.erde.editor.diagram.editpart.editpolicy;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

/**
 * EntityLayoutEditPolicy.
 *
 * @author modified by parapata
 */
public class EntityLayoutEditPolicy extends LayoutEditPolicy {

    @Override
    protected Command getMoveChildrenCommand(Request request) {
        return null;
    }

    @Override
    protected EditPolicy createChildEditPolicy(EditPart child) {
        return new NonResizableEditPolicy();
    }

    @Override
    protected Command getCreateCommand(CreateRequest request) {
        return null;
    }

    @Override
    protected Command getDeleteDependantCommand(Request request) {
        return null;
    }
}
