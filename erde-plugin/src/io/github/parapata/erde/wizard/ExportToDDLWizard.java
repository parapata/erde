package io.github.parapata.erde.wizard;

import static io.github.parapata.erde.Resource.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PlatformUI;

import io.github.parapata.erde.ErdePlugin;
import io.github.parapata.erde.core.LineSeparatorCode;
import io.github.parapata.erde.core.util.swt.UIUtils;
import io.github.parapata.erde.dialect.IDialect;
import io.github.parapata.erde.editor.diagram.model.RootModel;
import io.github.parapata.erde.wizard.page.ExportToDDLWizardPage;
import io.github.parapata.erde.wizard.task.DDLWriterTask;

/**
 * ExportToDDLWizard.
 *
 * @author modified by parapata
 */
public class ExportToDDLWizard extends Wizard {

    public static final String DIALOG_NAME = "DDLWizard";
    public static final String SCHEMA = "schema";
    public static final String DROP = "drop";
    public static final String ALTER_TABLE = "alterTable";
    public static final String COMMENT = "comment";
    public static final String ENCODING = "encoding";
    public static final String LINE_SEPARATOR = "lineSeparator";

    private String fileName;
    private RootModel root;
    private ExportToDDLWizardPage page;

    public ExportToDDLWizard(String fileName, RootModel root) {
        setWindowTitle(WIZARD_GENERATE_DDL_DIALOG_TITLE.getValue());
        this.fileName = fileName;
        this.root = root;

        IDialogSettings settings = ErdePlugin.getDefault().getDialogSettings();
        IDialogSettings section = settings.getSection(DIALOG_NAME);
        if (section == null) {
            section = settings.addNewSection(DIALOG_NAME);
        }
        if (section.get(SCHEMA) == null) {
            section.put(SCHEMA, false);
        }
        if (section.get(DROP) == null) {
            section.put(DROP, false);
        }
        if (section.get(ALTER_TABLE) == null) {
            section.put(ALTER_TABLE, true);
        }
        if (section.get(COMMENT) == null) {
            section.put(COMMENT, true);
        }
        if (section.get(ENCODING) == null) {
            section.put(ENCODING, System.getProperty("file.encoding"));
        }
        if (section.get(LINE_SEPARATOR) == null) {
            section.put(LINE_SEPARATOR, LineSeparatorCode.findByValue(System.lineSeparator()).name());
        }
        this.setDialogSettings(section);
    }

    @Override
    public void addPages() {
        page = new ExportToDDLWizardPage(fileName);
        addPage(page);
    }

    @Override
    public boolean performFinish() {

        LineSeparatorCode ls = LineSeparatorCode.findByName(page.getLineSeparator());
        if (ls == null) {
            ls = LineSeparatorCode.getDefault();
        }

        IDialogSettings setting = getDialogSettings();
        setting.put(SCHEMA, page.getSchema());
        setting.put(DROP, page.getDrop());
        setting.put(ALTER_TABLE, page.getAlterTable());
        setting.put(COMMENT, page.getComment());
        setting.put(ENCODING, page.getEncoding());
        setting.put(LINE_SEPARATOR, ls.name());

        IDialect dialect = root.getDialectProvider().getDialect();
        dialect.setSchema(page.getSchema());
        dialect.setDrop(page.getDrop());
        dialect.setAlterTable(page.getAlterTable());
        dialect.setComment(page.getComment());
        dialect.setLineSeparator(ls.getValue());

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
            DDLWriterTask task = new DDLWriterTask(dialect, root, file, page.getEncoding());
            dialog.run(true, true, task);
            UIUtils.projectRefresh();
            return true;
        } catch (InvocationTargetException e) {
            ErdePlugin.logException(e);
            UIUtils.openAlertDialog(ERROR_WIZARD_GENERATE_DDL_ERROR_OUTPUT);
            return false;
        } catch (InterruptedException e) {
            ErdePlugin.logException(e);
            UIUtils.openAlertDialog(ERROR_WIZARD_GENERATE_DDL_ERROR_OUTPUT);
            return false;
        }
    }
}
