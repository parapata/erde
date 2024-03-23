package io.github.parapata.erde.wizard;

import static io.github.parapata.erde.Resource.*;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.wizard.Wizard;

import io.github.parapata.erde.dialect.DialectProvider;
import io.github.parapata.erde.editor.diagram.editpart.command.ChangeDialectCommand;
import io.github.parapata.erde.editor.diagram.model.RootModel;
import io.github.parapata.erde.wizard.page.ChangeDialectWizardPage;

/**
 * ChangeDialectWizard.
 *
 * @author modified by parapata
 */
public class ChangeDialectWizard extends Wizard {

    private CommandStack commandStack;
    private RootModel rootModel;
    private ChangeDialectWizardPage page;

    public ChangeDialectWizard(CommandStack commandStack, RootModel rootModel) {
        setWindowTitle(WIZARD_CHANGEDB_DIALOG_TITLE.getValue());
        this.commandStack = commandStack;
        this.rootModel = rootModel;
    }

    @Override
    public void addPages() {
        page = new ChangeDialectWizardPage(rootModel.getDialectProvider().name());
        addPage(page);
    }

    @Override
    public boolean performFinish() {
        DialectProvider newProvider = DialectProvider.valueOf(page.getDialectName());
        if (!rootModel.getDialectProvider().equals(newProvider)) {
            commandStack.execute(new ChangeDialectCommand(newProvider, rootModel));
        }
        return true;
    }
}
