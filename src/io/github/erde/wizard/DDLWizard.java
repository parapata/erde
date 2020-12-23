package io.github.erde.wizard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Paths;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.ui.wizards.datatransfer.FileSystemExportWizard;

import io.github.erde.Activator;
import io.github.erde.IMessages;
import io.github.erde.core.util.UIUtils;
import io.github.erde.dialect.DialectProvider;
import io.github.erde.dialect.IDialect;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.wizard.page.DDLWizardPage;

/**
 * DDLWizard.
 *
 * @author modified by parapata
 */
public class DDLWizard extends FileSystemExportWizard implements IMessages {

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
        try {
            IDialect dialect = DialectProvider.getDialect(root.getDialectName());
            dialect.setSchema(page.getSchema().getSelection());
            dialect.setDrop(page.getDrop().getSelection());
            dialect.setComment(page.getComment().getSelection());

            StringBuilder ddl = new StringBuilder();
            dialect.createDDL(root, ddl);

            IDialogSettings setting = getDialogSettings();
            setting.put("schema", page.getSchema().getSelection());
            setting.put("drop", page.getDrop().getSelection());
            setting.put("comment", page.getComment().getSelection());
            setting.put("encoding", page.getEncoding().getText());

            File file = Paths.get(page.getOutputFolderResource(), page.getFilename().getText()).toFile();
            if (file.exists()) {
                String messageKey = "wizard.generate.ddl.confirm.message";
                String[] messageArgs = new String[] { page.getFilename().getText() };
                if (!UIUtils.openConfirmDialog(messageKey, messageArgs)) {
                    return false;
                }
            }

            // TODO リフレッシュの効果なし
            for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
                project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
            }
            IWorkspaceRoot wsroot = ResourcesPlugin.getWorkspace().getRoot();
            wsroot.refreshLocal(IResource.DEPTH_ONE, new NullProgressMonitor());

            return output(ddl.toString(), file, page.getEncoding().getText());

        } catch (Exception e) {
            Activator.logException(e);
            return false;
        }
    }

    private boolean output(String ddl, File file, String encode) {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), encode)) {
            writer.write(ddl);
        } catch (UnsupportedEncodingException e) {
            Activator.logException(e);
            UIUtils.openAlertDialog("wizard.generate.ddl.error.encoding");
            return false;
        } catch (IOException e) {
            Activator.logException(e);
            UIUtils.openAlertDialog("wizard.generate.ddl.error.output");
            return false;
        }
        return true;
    }
}
