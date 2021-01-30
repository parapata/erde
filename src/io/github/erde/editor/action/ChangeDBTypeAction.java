package io.github.erde.editor.action;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;

import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.wizard.ChangeDialectWizard;

/**
 * Changes a database dialect and convert column types from the old dialect to the new dialect.
 *
 * @author modified by parapata
 */
public class ChangeDBTypeAction extends Action implements IERDEAction {

    public ChangeDBTypeAction() {
        super();
        setId(CHANGE_DB_TYPE);
        setText(getResource("action.changeDatabaseType"));
    }

    @Override
    public void run() {

        GraphicalViewer viewer = getGraphicalViewer();
        RootModel root = (RootModel) viewer.getContents().getModel();
        WizardDialog dialog = new WizardDialog(
                viewer.getControl().getShell(),
                new ChangeDialectWizard(getCommandStack2(), root));
        dialog.open();
    }
}
