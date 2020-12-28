package io.github.erde.editor.action;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;

import io.github.erde.wizard.ImportFromJDBCWizard;

/**
 * ImportFromJDBCAction.
 * 
 * @author modified by parapata
 */
public class ImportFromJDBCAction extends Action implements IERDEAction {

    public ImportFromJDBCAction() {
        super();
        setId(IMPORT_FROM_JDBC);
        setText(getResource("action.importFromDB"));
    }

    @Override
    public void run() {
        GraphicalViewer viewer = getGraphicalViewer();
        ImportFromJDBCWizard wizard = new ImportFromJDBCWizard(viewer);
        WizardDialog dialog = new WizardDialog(viewer.getControl().getShell(), wizard);
        dialog.open();
    }
}
