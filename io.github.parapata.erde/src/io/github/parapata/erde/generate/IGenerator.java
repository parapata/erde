package io.github.parapata.erde.generate;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.gef.GraphicalViewer;

import io.github.parapata.erde.editor.diagram.model.RootModel;

/**
 * IGenerator.
 *
 * @author modified by parapata
 */
public interface IGenerator {

    String getGeneratorName();

    void execute(IFile erdFile, RootModel root, GraphicalViewer viewer);

    default String getDefaultPath(IFile erdFile) {
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        IFile file = workspaceRoot.getFile(erdFile.getFullPath());
        return file.getRawLocation().toFile().getParent();
    }
}
