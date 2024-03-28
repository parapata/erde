package io.github.parapata.erde.editor.diagram.editpart.editpolicy;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import io.github.parapata.erde.editor.diagram.editpart.command.ChangeConstraintCommand;
import io.github.parapata.erde.editor.diagram.editpart.command.CreateRootModelCommand;
import io.github.parapata.erde.editor.diagram.model.BaseEntityModel;
import io.github.parapata.erde.editor.diagram.model.NoteModel;

/**
 * RootEditPolicy.
 *
 * @author modified by parapata
 */
public class RootEditPolicy extends XYLayoutEditPolicy {

    public Set<Class<? extends BaseEntityModel>> RESIZABLE = null;
    {
        RESIZABLE = new HashSet<>();
        RESIZABLE.add(NoteModel.class);
    }

    @Override
    protected EditPolicy createChildEditPolicy(EditPart child) {
        if (RESIZABLE.contains(child.getModel().getClass())) {
            return new ResizableEditPolicy();
        } else {
            return new NonResizableEditPolicy();
        }
    }

    @Override
    protected Command createAddCommand(EditPart child, Object constraint) {
        return null;
    }

    @Override
    protected Command createChangeConstraintCommand(EditPart child, Object constraint) {
        ChangeConstraintCommand command = new ChangeConstraintCommand();
        command.setModel((BaseEntityModel) child.getModel());
        command.setConstraint((Rectangle) constraint);
        return command;
    }

    @Override
    protected Command getCreateCommand(CreateRequest request) {
        CreateRootModelCommand command = new CreateRootModelCommand();
        Rectangle constraint = (Rectangle) getConstraintFor(request);
        BaseEntityModel model = (BaseEntityModel) request.getNewObject();
        if (!RESIZABLE.contains(model.getClass())) {
            constraint.width = -1;
            constraint.height = -1;
        }
        model.setConstraint(constraint);

        command.setRootModel(getHost().getModel());
        command.setModel(model);
        return command;
    }

    @Override
    protected Command getDeleteDependantCommand(Request request) {
        return null;
    }
}
