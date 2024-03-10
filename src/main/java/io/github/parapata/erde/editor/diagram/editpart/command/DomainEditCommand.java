package io.github.parapata.erde.editor.diagram.editpart.command;

import java.util.List;

import org.eclipse.gef.commands.Command;

import io.github.parapata.erde.editor.diagram.model.DomainModel;
import io.github.parapata.erde.editor.diagram.model.RootModel;

/**
 * DomainEditCommand.
 *
 * @author modified by parapata
 */
public class DomainEditCommand extends Command {
    private RootModel rootModel;
    private List<DomainModel> newDomains;
    private List<DomainModel> oldDomains;

    public DomainEditCommand(RootModel rootModel, List<DomainModel> newDomains, List<DomainModel> oldDomains) {
        this.rootModel = rootModel;
        this.newDomains = newDomains;
        this.oldDomains = oldDomains;
    }

    @Override
    public void execute() {
        this.rootModel.setDomains(newDomains);
    }

    @Override
    public void undo() {
        this.rootModel.setDomains(oldDomains);
    }
}
