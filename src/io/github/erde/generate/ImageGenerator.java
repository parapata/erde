package io.github.erde.generate;

import org.eclipse.core.resources.IFile;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;

import io.github.erde.ERDPlugin;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.wizard.ExportToImageWizard;

/**
 * ImageGenerator.
 *
 * @author modified by parapata
 */
public class ImageGenerator implements IGenerator {

    private static final String NEW_FILE_NAME = "newfile" + ERDPlugin.EXTENSION_PNG;

    @Override
    public String getGeneratorName() {
        return "Image";
    }

    @Override
    public void execute(IFile erdFile, RootModel root, GraphicalViewer viewer) {
        Wizard wizard = new ExportToImageWizard(viewer, NEW_FILE_NAME);
        WizardDialog dialog = new WizardDialog(null, wizard);
        dialog.open();
    }
}
