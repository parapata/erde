package io.github.erde.generate;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IFileEditorInput;

import io.github.erde.core.util.swt.UIUtils;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.wizard.ExportToHtmlWizard;

/**
 * HtmlGenerator.
 *
 * @author modified by parapata
 */
public class HtmlGenerator implements IGenerator {

    @Override
    public String getGeneratorName() {
        return "HTML";
    }

    @Override
    public void execute(IFile erdFile, RootModel root, GraphicalViewer viewer) {

        IFile inFile = ((IFileEditorInput) UIUtils.getActiveEditor().getEditorInput()).getFile();
        File file = inFile.getLocation().toFile();

        Wizard wizard = new ExportToHtmlWizard(file);
        WizardDialog dialog = new WizardDialog(null, wizard);
        dialog.open();
    }

}
