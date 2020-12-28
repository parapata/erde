package io.github.erde.editor.action;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IFileEditorInput;

import io.github.erde.Activator;
import io.github.erde.core.util.UIUtils;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.validator.DiagramError;
import io.github.erde.editor.validator.DiagramErrorManager;
import io.github.erde.editor.validator.DiagramValidator;

/**
 * ValidateAction.
 *
 * @author modified by parapata
 */
public class ValidateAction extends Action implements IERDEAction {

    public ValidateAction() {
        super();
        setId(VALIDATE);
        setText(getResource("action.validation.executeValidation"));
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
                    IFile file = ((IFileEditorInput) UIUtils.getActiveEditor().getEditorInput()).getFile();

                    file.deleteMarkers(IMarker.PROBLEM, false, 0);
                    DiagramErrorManager deManager = new DiagramValidator(model).doValidate();
                    for (DiagramError error : deManager.getErrors()) {
                        error.addMarker(file);
                    }
                } catch (CoreException e) {
                    Activator.logException(e);
                }
            }
        });
    }
}
