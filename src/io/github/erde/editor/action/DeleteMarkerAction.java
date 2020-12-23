package io.github.erde.editor.action;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;

import io.github.erde.Activator;
import io.github.erde.core.util.UIUtils;
import io.github.erde.editor.diagram.model.BaseEntityModel;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;

/**
 * DeleteMarkerAction.
 * 
 * @author modified by parapata
 */
public class DeleteMarkerAction extends Action implements IERDEAction {

    public DeleteMarkerAction() {
        super();
        setId(DELETE_MARKER);
        setText(getResource("action.validation.deleteMarkers"));
    }

    @Override
    public void run() {

        GraphicalViewer viewer = getGraphicalViewer();

        CommandStack stack = getCommandStack2();
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

        IEditorInput input = UIUtils.getActiveEditor().getEditorInput();
        if (input instanceof IFileEditorInput) {
            IFile file = ((IFileEditorInput) input).getFile();
            try {
                file.deleteMarkers(IMarker.PROBLEM, false, 0);
            } catch (CoreException e) {
                Activator.logException(e);
            }
        }
    }
}
