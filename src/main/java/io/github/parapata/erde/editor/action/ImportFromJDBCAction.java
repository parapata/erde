package io.github.parapata.erde.editor.action;

import static io.github.parapata.erde.Resource.*;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;

import io.github.parapata.erde.wizard.ImportFromJDBCWizard;

/**
 * ImportFromJDBCAction.
 *
 * @author modified by parapata
 */
public class ImportFromJDBCAction extends Action implements IERDAction {

    public ImportFromJDBCAction() {
        super();
        setId(IMPORT_FROM_JDBC);
        setText(ACTION_IMPORT_FROM_DB.getValue());
    }

    @Override
    public void run() {
        GraphicalViewer viewer = getGraphicalViewer();
        ImportFromJDBCWizard wizard = new ImportFromJDBCWizard(viewer);
        WizardDialog dialog = new WizardDialog(viewer.getControl().getShell(), wizard);
        dialog.open();
    }
}
