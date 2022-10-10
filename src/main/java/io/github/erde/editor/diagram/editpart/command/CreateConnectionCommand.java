package io.github.erde.editor.diagram.editpart.command;

import org.eclipse.gef.commands.Command;

import io.github.erde.editor.diagram.model.BaseConnectionModel;
import io.github.erde.editor.diagram.model.BaseEntityModel;

/**
 * CreateConnectionCommand.
 *
 * @author modified by parapata
 */
public class CreateConnectionCommand extends Command {

    private BaseEntityModel model;
    private BaseEntityModel source;
    private BaseEntityModel target;
    private BaseConnectionModel connection;

    @Override
    public boolean canExecute() {
        if (source == null || target == null) {
            return false;
        } else if (source == target) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void execute() {
        connection.attachSource();
        connection.attachTarget();
    }

    @Override
    public void undo() {
        connection.detachSource();
        connection.detachTarget();
    }

    public void setModel(BaseEntityModel model) {
        this.model = model;
    }

    public Object getModel() {
        return this.model;
    }

    public BaseConnectionModel getConnection() {
        return connection;
    }

    public void setConnection(Object model) {
        connection = (BaseConnectionModel) model;
    }

    public BaseEntityModel getSource() {
        return source;
    }

    public void setSource(Object model) {
        source = (BaseEntityModel) model;
        connection.setSource(source);
    }

    public BaseEntityModel getTarget() {
        return target;
    }

    public void setTarget(Object model) {
        target = (BaseEntityModel) model;
        connection.setTarget(target);
    }

}
