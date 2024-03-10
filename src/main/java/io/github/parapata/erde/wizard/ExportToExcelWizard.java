package io.github.parapata.erde.wizard;

import static io.github.parapata.erde.Resource.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;

import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PlatformUI;

import io.github.parapata.erde.ERDPlugin;
import io.github.parapata.erde.core.util.swt.UIUtils;
import io.github.parapata.erde.editor.diagram.model.RootModel;
import io.github.parapata.erde.wizard.page.ExportToFileWizardPage;
import io.github.parapata.erde.wizard.task.ExcelWriterTask;

/**
 * ExportToExcelWizard.
 *
 * @author parapata
 * @since 1.0.14
 */
public class ExportToExcelWizard extends Wizard {

    private String fileName;
    private RootModel root;
    private ExportToFileWizardPage page;

    public ExportToExcelWizard(String fileName, RootModel root) {
        setWindowTitle(WIZARD_GENERATE_EXCEL_DIALOG_TITLE.getValue());
        this.fileName = fileName;
        this.root = root;
    }

    @Override
    public void addPages() {
        page = new ExportToFileWizardPage(fileName);
        page.setTitle(WIZARD_GENERATE_EXCEL_PAGE_TITLE.getValue());
        page.setDescription(WIZARD_GENERATE_EXCEL_PAGE_DESCRIPTION.getValue());

        addPage(page);
    }

    @Override
    public boolean performFinish() {
        File file = Paths.get(page.getOutputFolderResource(), page.getFileName()).toFile();
        if (file.exists()) {
            String[] args = new String[] { page.getFileName() };
            if (!UIUtils.openConfirmDialog(INFO_ALREADY_EXISTS_OVER_WRITE, args)) {
                return false;
            }
        }

        ProgressMonitorDialog dialog = new ProgressMonitorDialog(
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
        try {
            ExcelWriterTask task = new ExcelWriterTask(root, file);
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
