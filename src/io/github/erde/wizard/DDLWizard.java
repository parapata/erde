package io.github.erde.wizard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Paths;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.ui.wizards.datatransfer.FileSystemExportWizard;

import io.github.erde.Activator;
import io.github.erde.Resource;
import io.github.erde.core.util.UIUtils;
import io.github.erde.dialect.IDialect;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.wizard.page.DDLWizardPage;

/**
 * DDLWizard.
 *
 * @author modified by parapata
 */
public class DDLWizard extends FileSystemExportWizard {

    private IFile ddlFile;
    private RootModel root;
    private DDLWizardPage page;

    public DDLWizard(IFile ddlFile, RootModel root, String generatorName) {

        this.ddlFile = ddlFile;
        this.root = root;
        setWindowTitle(generatorName);

        IDialogSettings settings = Activator.getDefault().getDialogSettings();
        IDialogSettings section = settings.getSection("DDLWizard");
        if (section == null) {
            section = settings.addNewSection("DDLWizard");
            section.put("schema", false);
            section.put("drop", false);
            section.put("alterTable", true);
            section.put("comment", true);
            section.put("encoding", System.getProperty("file.encoding"));
        }
        this.setDialogSettings(section);
    }

    @Override
    public void addPages() {
        this.page = new DDLWizardPage(ddlFile);
        addPage(page);
    }

    @Override
    public boolean performFinish() {
        IDialect dialect = root.getDialectProvider().getDialect();
        dialect.setSchema(page.getSchema());
        dialect.setDrop(page.getDrop());
        dialect.setAlterTable(page.getAlterTable());
        dialect.setComment(page.getComment());

        IDialogSettings setting = getDialogSettings();
        setting.put("schema", page.getSchema());
        setting.put("drop", page.getDrop());
        setting.put("alterTable", page.getAlterTable());
        setting.put("comment", page.getComment());
        setting.put("encoding", page.getEncoding());

        File file = Paths.get(page.getOutputFolderResource(), page.getFilename()).toFile();

        if (file.exists()) {
            String[] messageArgs = new String[] { page.getFilename() };
            if (!UIUtils.openConfirmDialog(Resource.WIZARD_GENERATE_DDL_CONFIRM_MESSAGE, messageArgs)) {
                return false;
            }
        }

        try (FileOutputStream fos = new FileOutputStream(file);
                OutputStreamWriter osw = new OutputStreamWriter(fos, page.getEncoding());
                BufferedWriter bw = new BufferedWriter(osw);
                PrintWriter pw = new PrintWriter(bw)) {
            dialect.createDDL(root, pw);
            pw.flush();
            return true;
        } catch (IOException e) {
            Activator.logException(e);
            UIUtils.openAlertDialog(Resource.WIZARD_GENERATE_DDL_ERROR_OUTPUT);
            return false;
        } finally {
            UIUtils.projectRefresh();
        }
    }
}
