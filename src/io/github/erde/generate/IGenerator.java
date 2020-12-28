package io.github.erde.generate;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.gef.GraphicalViewer;

import io.github.erde.editor.diagram.model.RootModel;

/**
 * IGenerator.
 *
 * @author modified by parapata
 */
public interface IGenerator {

    public String getGeneratorName();

    public void execute(IFile erdFile, RootModel root, GraphicalViewer viewer);

    public default String getDefaultPath(IFile erdFile) {
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        IFile file = workspaceRoot.getFile(erdFile.getFullPath());
        return file.getRawLocation().toFile().getParent();
    }
}
