package io.github.erde.wizard;

import static io.github.erde.Resource.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PlatformUI;

import io.github.erde.ERDPlugin;
import io.github.erde.core.util.swt.UIUtils;
import io.github.erde.wizard.page.ExportToFileWizardPage;
import io.github.erde.wizard.task.ImageWriterTask;

/**
 * ExportToImageWizard.
 *
 * @author parapata
 * @since 1.0.14
 */
public class ExportToImageWizard extends Wizard {

    private GraphicalViewer viewer;
    private String fileName;
    private ExportToFileWizardPage page;

    public ExportToImageWizard(GraphicalViewer viewer, String fileName) {
        setWindowTitle(WIZARD_GENERATE_IMAGE_DIALOG_TITLE.getValue());
        this.viewer = viewer;
        this.fileName = fileName;
    }

    @Override
    public void addPages() {
        page = new ExportToFileWizardPage(fileName);
        page.setTitle(WIZARD_GENERATE_IMAGE_PAGE_TITLE.getValue());
        page.setDescription(WIZARD_GENERATE_IMAGE_PAGE_DESCRIPTION.getValue());

        addPage(page);
    }

    @Override
    public boolean performFinish() {

        ProgressMonitorDialog dialog = new ProgressMonitorDialog(
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());

        File file = Paths.get(page.getOutputFolderResource(), page.getFileName()).toFile();
        try {
            ImageWriterTask task = new ImageWriterTask(viewer, file);
            dialog.run(false, true, task);
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
