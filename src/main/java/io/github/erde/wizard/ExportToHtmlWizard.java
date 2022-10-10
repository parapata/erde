package io.github.erde.wizard;

import static io.github.erde.Resource.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;

import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PlatformUI;

import io.github.erde.ERDPlugin;
import io.github.erde.core.util.swt.UIUtils;
import io.github.erde.wizard.page.FolderSelectWizardPage;
import io.github.erde.wizard.task.HtmlWriterTask;

/**
 * ExportToHtmlWizard.
 *
 * @author parapata
 * @since 1.0.14
 */
public class ExportToHtmlWizard extends Wizard {

    private File inFile;
    private FolderSelectWizardPage page;

    public ExportToHtmlWizard(File inFile) {
        setWindowTitle(WIZARD_GENERATE_HTML_DIALOG_TITLE.getValue());
        this.inFile = inFile;
    }

    @Override
    public void addPages() {
        page = new FolderSelectWizardPage("Html");
        page.setTitle(WIZARD_GENERATE_HTML_PAGE_TITLE.getValue());
        page.setDescription(WIZARD_GENERATE_HTML_PAGE_DESCRIPTION.getValue());

        addPage(page);
    }

    @Override
    public boolean performFinish() {

        File outPath = Paths.get(page.getOutputFolderResource()).toFile();

        ProgressMonitorDialog dialog = new ProgressMonitorDialog(
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
        try {
            HtmlWriterTask task = new HtmlWriterTask(inFile, outPath);
            dialog.run(true, true, task);
            UIUtils.projectRefresh();
            return true;
        } catch (InvocationTargetException e) {
            ERDPlugin.logException(e);
            UIUtils.openAlertDialog(ERROR_WIZARD_GENERATE_DDL_ERROR_OUTPUT);
            return false;
        } catch (InterruptedException e) {
            ERDPlugin.logException(e);
            UIUtils.openAlertDialog(ERROR_WIZARD_GENERATE_DDL_ERROR_OUTPUT);
            return false;
        }
    }
}
