package io.github.parapata.erde.editor.action;

import static io.github.parapata.erde.Resource.*;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.Action;

import io.github.parapata.erde.ERDPlugin;
import io.github.parapata.erde.editor.ERDiagramEditor;
import io.github.parapata.erde.editor.diagram.model.RootModel;
import io.github.parapata.erde.editor.validator.DiagramError;
import io.github.parapata.erde.editor.validator.DiagramErrorManager;
import io.github.parapata.erde.editor.validator.DiagramValidator;

/**
 * ValidateAction.
 *
 * @author modified by parapata
 */
public class ValidateAction extends Action implements IERDAction {

    public ValidateAction() {
        super();
        setId(VALIDATE);
        setText(ACTION_VALIDATION_EXECUTE_VALIDATION.getValue());
    }

    @Override
    public void run() {

        GraphicalViewer viewer = getGraphicalViewer();

        // Validate models
        CommandStack stack = viewer.getEditDomain().getCommandStack();
        stack.execute(new Command("Validation") {
            @Override
            public boolean canUndo() {
                return false;
            }

            @Override
            public void execute() {
                try {
                    RootModel model = (RootModel) viewer.getContents().getModel();
                    IFile file = ERDiagramEditor.getERDiagramEditorFile();

                    file.deleteMarkers(IMarker.PROBLEM, false, 0);
                    DiagramErrorManager deManager = new DiagramValidator(model).doValidate();
                    for (DiagramError error : deManager.getErrors()) {
                        error.addMarker(file);
                    }
                } catch (CoreException e) {
                    ERDPlugin.logException(e);
                }
            }
        });
    }
}
