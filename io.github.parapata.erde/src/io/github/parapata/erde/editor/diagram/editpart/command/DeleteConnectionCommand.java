package io.github.parapata.erde.editor.diagram.editpart.command;

import org.eclipse.gef.commands.Command;

import io.github.parapata.erde.editor.diagram.model.BaseConnectionModel;

/**
 * DeleteConnectionCommand.
 *
 * @author modified by parapata
 */
public class DeleteConnectionCommand extends Command {

    private BaseConnectionModel model;

    public DeleteConnectionCommand(BaseConnectionModel model) {
        super();
        this.model = model;
    }

    public void setModel(BaseConnectionModel model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.detachSource();
        model.detachTarget();
    }
}
