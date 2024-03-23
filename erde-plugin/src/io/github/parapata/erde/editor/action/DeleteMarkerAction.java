package io.github.parapata.erde.editor.action;

import static io.github.parapata.erde.Resource.*;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.Action;

import io.github.parapata.erde.ErdePlugin;
import io.github.parapata.erde.editor.ErdeDiagramEditor;
import io.github.parapata.erde.editor.diagram.model.BaseEntityModel;
import io.github.parapata.erde.editor.diagram.model.RootModel;
import io.github.parapata.erde.editor.diagram.model.TableModel;

/**
 * DeleteMarkerAction.
 *
 * @author modified by parapata
 */
public class DeleteMarkerAction extends Action implements IErdeAction {

    public DeleteMarkerAction() {
        super();
        setId(DELETE_MARKER);
        setText(ACTION_VALIDATION_DELETE_MARKERS.getValue());
    }

    @Override
    public void run() {

        GraphicalViewer viewer = getGraphicalViewer();

        CommandStack stack = getErdeCommandStack();
        stack.execute(new Command("Delete markers") {
            @Override
            public void execute() {
                RootModel model = (RootModel) viewer.getRootEditPart().getContents().getModel();
                for (BaseEntityModel entity : model.getChildren()) {
                    if (entity instanceof TableModel) {
                        ((TableModel) entity).setError("");
                    }
                }
            }

            @Override
            public boolean canUndo() {
                return false;
            }
        });
        IFile file = ErdeDiagramEditor.getERDiagramEditorFile();
        try {
            file.deleteMarkers(IMarker.PROBLEM, false, 0);
        } catch (CoreException e) {
            ErdePlugin.logException(e);
        }
    }
}
