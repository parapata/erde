package io.github.parapata.erde.editor.diagram.editpart.command;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import io.github.parapata.erde.editor.diagram.model.BaseEntityModel;

/**
 * ChangeConstraintCommand.
 *
 * @author modified by parapata
 */
public class ChangeConstraintCommand extends Command {

    private BaseEntityModel model;
    private Rectangle constraint;
    private Rectangle oldConstraint;

    @Override
    public void execute() {
        model.setConstraint(constraint);
    }

    @Override
    public void undo() {
        model.setConstraint(oldConstraint);
    }

    public void setConstraint(Rectangle constraint) {
        this.constraint = constraint;
    }

    public void setModel(BaseEntityModel model) {
        this.model = model;
        oldConstraint = model.getConstraint();
    }
}
