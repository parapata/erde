package io.github.erde.editor.action;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.Action;

import io.github.erde.editor.diagram.model.RootModel;

/**
 * The action to toggle diagram mode (logical / physical).
 *
 * @author modified by parapata
 */
public class ToggleModelAction extends Action implements IERDEAction {

    public ToggleModelAction() {
        super();
        setId(TOGGLE_MODEL);
        setText(getResource("action.toggleMode"));
    }

    @Override
    public void run() {
        // CommandStack stack = editor.getCommandStack();
        CommandStack stack = getCommandStack2();
        stack.execute(new Command("Toggle display mode") {
            @Override
            public void execute() {
                toggleLogical();
            }

            @Override
            public void undo() {
                toggleLogical();
            }
        });
    }

    private void toggleLogical() {
        GraphicalViewer viewer = getGraphicalViewer();
        RootModel root = (RootModel) viewer.getContents().getModel();
        root.setLogicalMode(!root.isLogicalMode());
    }
}
