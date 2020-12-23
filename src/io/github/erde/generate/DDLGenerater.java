package io.github.erde.generate;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.wizard.WizardDialog;

import io.github.erde.Activator;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.wizard.DDLWizard;

/**
 * DDLGenerater.
 *
 * @author modified by parapata
 */
public class DDLGenerater implements IGenerator {

    private static final String NEW_FILE_NAME = "newfile" + Activator.EXTENSION_DDL;

    @Override
    public String getGeneratorName() {
        return "DDL";
    }

    @Override
    public void execute(IFile erdFile, RootModel root, GraphicalViewer viewer) {

        IProject project = erdFile.getProject();
        IWorkspaceRoot workspace = ResourcesPlugin.getWorkspace().getRoot();
        IPath path = project.getFullPath().append(NEW_FILE_NAME);
        IFile ddlFile = workspace.getFile(path);

        WizardDialog dialog = new WizardDialog(null, new DDLWizard(ddlFile, root, getGeneratorName()));
        dialog.open();
    }
}
