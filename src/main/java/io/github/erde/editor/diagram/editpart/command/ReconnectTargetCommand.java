package io.github.erde.editor.diagram.editpart.command;

import org.eclipse.gef.commands.Command;

import io.github.erde.editor.diagram.model.BaseConnectionModel;
import io.github.erde.editor.diagram.model.BaseEntityModel;

/**
 * ReconnectTargetCommand.
 *
 * @author modified by parapata
 */
public class ReconnectTargetCommand extends Command {

    private BaseEntityModel target;
    private BaseEntityModel oldTarget;
    private BaseConnectionModel connection;

    @Override
    public void execute() {
        connection.detachTarget();
        connection.setTarget(target);
        connection.attachTarget();
    }

    public void setConnection(Object model) {
        connection = (BaseConnectionModel) model;
        oldTarget = connection.getTarget();
    }

    public void setTarget(Object model) {
        target = (BaseEntityModel) model;
    }

    @Override
    public boolean canExecute() {
        if (connection.getSource() == null || target == null) {
            return false;
        }
        if (connection.getSource().equals(target)) {
            return false;
        }
        return true;
    }

    @Override
    public void undo() {
        connection.detachTarget();
        connection.setTarget(oldTarget);
        connection.attachTarget();
    }
}
