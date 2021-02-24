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

    public static final String DIALOG_NAME = "DDLWizard";
    public static final String SCHEMA = "schema";
    public static final String DROP = "drop";
    public static final String ALTER_TABLE = "alterTable";
    public static final String COMMENT = "comment";
    public static final String ENCODING = "encoding";
    public static final String LINE_SEPARATOR = "lineSeparator";

    private IFile ddlFile;
    private RootModel root;
    private DDLWizardPage page;

    public DDLWizard(IFile ddlFile, RootModel root, String generatorName) {

        this.ddlFile = ddlFile;
        this.root = root;
        setWindowTitle(generatorName);

        IDialogSettings settings = Activator.getDefault().getDialogSettings();
        IDialogSettings section = settings.getSection(DIALOG_NAME);
        if (section == null) {
            section = settings.addNewSection(DIALOG_NAME);
            section.put(SCHEMA, false);
            section.put(DROP, false);
            section.put(ALTER_TABLE, true);
            section.put(COMMENT, true);
            section.put(ENCODING, System.getProperty("file.encoding"));
            section.put(LINE_SEPARATOR, System.lineSeparator());
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

        IDialogSettings setting = getDialogSettings();
        setting.put(SCHEMA, page.getSchema());
        setting.put(DROP, page.getDrop());
        setting.put(ALTER_TABLE, page.getAlterTable());
        setting.put(COMMENT, page.getComment());
        setting.put(ENCODING, page.getEncoding());
        setting.put(LINE_SEPARATOR, page.getLineSeparator());

        IDialect dialect = root.getDialectProvider().getDialect();
        dialect.setSchema(page.getSchema());
        dialect.setDrop(page.getDrop());
        dialect.setAlterTable(page.getAlterTable());
        dialect.setComment(page.getComment());
        dialect.setLineSeparator(page.getLineSeparator());

        File file = Paths.get(page.getOutputFolderResource(), page.getFilename()).toFile();
        if (file.exists()) {
            String[] args = new String[] { page.getFilename() };
            if (!UIUtils.openConfirmDialog(Resource.WIZARD_GENERATE_DDL_CONFIRM_MESSAGE, args)) {
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
