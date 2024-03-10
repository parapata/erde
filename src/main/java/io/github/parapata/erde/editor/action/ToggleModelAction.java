package io.github.parapata.erde.editor.action;

import static io.github.parapata.erde.Resource.*;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.Action;

import io.github.parapata.erde.editor.diagram.model.RootModel;

/**
 * The action to toggle diagram mode (logical / physical).
 *
 * @author modified by parapata
 */
public class ToggleModelAction extends Action implements IERDAction {

    public ToggleModelAction() {
        super();
        setId(TOGGLE_MODEL);
        setText(ACTION_TOGGLE_MODE.getValue());
    }

    @Override
    public void run() {
        // CommandStack stack = editor.getCommandStack();
        CommandStack stack = getERDCommandStack();
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
