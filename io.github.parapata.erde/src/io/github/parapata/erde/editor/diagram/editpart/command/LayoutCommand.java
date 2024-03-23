package io.github.parapata.erde.editor.diagram.editpart.command;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import io.github.parapata.erde.editor.diagram.model.BaseEntityModel;

/**
 * LayoutCommand.
 *
 * @author modified by parapata
 */
public class LayoutCommand extends Command {

    private BaseEntityModel target;
    private int x;
    private int y;
    private int oldX;
    private int oldY;

    public LayoutCommand(BaseEntityModel target, int x, int y) {
        this.target = target;
        this.x = x;
        this.y = y;
        this.oldX = target.getConstraint().x;
        this.oldY = target.getConstraint().y;
    }

    @Override
    public void execute() {
        this.target.setConstraint(new Rectangle(this.x, this.y, -1, -1));
    }

    @Override
    public void undo() {
        this.target.setConstraint(new Rectangle(this.oldX, this.oldY, -1, -1));
    }
}
