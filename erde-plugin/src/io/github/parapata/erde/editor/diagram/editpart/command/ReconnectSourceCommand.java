package io.github.parapata.erde.editor.diagram.editpart.command;

import org.eclipse.gef.commands.Command;

import io.github.parapata.erde.editor.diagram.model.BaseConnectionModel;
import io.github.parapata.erde.editor.diagram.model.BaseEntityModel;

/**
 * ReconnectSourceCommand.
 *
 * @author modified by parapata
 */
public class ReconnectSourceCommand extends Command {

    private BaseEntityModel source;
    private BaseEntityModel oldSource;
    private BaseConnectionModel connection;

    @Override
    public void execute() {
        connection.detachSource();
        connection.setSource(source);
        connection.attachSource();
    }

    public void setConnection(Object model) {
        connection = (BaseConnectionModel) model;
        oldSource = connection.getSource();
    }

    public void setSource(Object model) {
        source = (BaseEntityModel) model;
    }

    @Override
    public boolean canExecute() {
        if (connection.getTarget() == null || source == null) {
            return false;
        }
        if (connection.getTarget().equals(source)) {
            return false;
        }
        return true;
    }

    @Override
    public void undo() {
        connection.detachSource();
        connection.setSource(oldSource);
        connection.attachSource();
    }
}
