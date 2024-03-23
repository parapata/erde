package io.github.parapata.erde.editor.action;

import static io.github.parapata.erde.Resource.*;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;

import io.github.parapata.erde.editor.diagram.model.RootModel;
import io.github.parapata.erde.wizard.ChangeDialectWizard;

/**
 * Changes a database dialect and convert column types from the old dialect to the new dialect.
 *
 * @author modified by parapata
 */
public class ChangeDialectAction extends Action implements IErdeAction {

    public ChangeDialectAction() {
        super();
        setId(CHANGE_DB_TYPE);
        setText(ACTION_CHANGE_DATABASE_TYPE.getValue());
    }

    @Override
    public void run() {
        GraphicalViewer viewer = getGraphicalViewer();
        RootModel root = (RootModel) viewer.getContents().getModel();
        WizardDialog dialog = new WizardDialog(
                viewer.getControl().getShell(),
                new ChangeDialectWizard(getErdeCommandStack(), root));
        dialog.open();
    }
}
