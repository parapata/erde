package io.github.erde.generate;

import org.eclipse.core.resources.IFile;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;

import io.github.erde.ERDPlugin;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.wizard.ExportToDDLWizard;

/**
 * DDLGenerater.
 *
 * @author modified by parapata
 */
public class DDLGenerater implements IGenerator {

    private static final String NEW_FILE_NAME = "newfile" + ERDPlugin.EXTENSION_DDL;

    @Override
    public String getGeneratorName() {
        return "DDL";
    }

    @Override
    public void execute(IFile erdFile, RootModel root, GraphicalViewer viewer) {
        Wizard wizard = new ExportToDDLWizard(NEW_FILE_NAME, root);
        WizardDialog dialog = new WizardDialog(null, wizard);
        dialog.open();
    }
}
