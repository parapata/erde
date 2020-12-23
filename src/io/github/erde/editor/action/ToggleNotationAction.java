package io.github.erde.editor.action;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.Action;

import io.github.erde.editor.diagram.figure.connection.decoration.DecorationFactory;
import io.github.erde.editor.diagram.model.RootModel;

/**
 * ToggleNotationAction.
 *
 * @author modified by parapata
 */
public class ToggleNotationAction extends Action implements IERDEAction {

    public ToggleNotationAction() {
        super();
        setId(TOGGLE_NOTATION);
        setText(getResource("action.toggleNotation"));
    }

    @Override
    public void run() {
        CommandStack stack = getCommandStack2();

        stack.execute(new Command("Toggle display notation") {
            @Override
            public void execute() {
                toggleNotation();
            }

            @Override
            public void undo() {
                toggleNotation();
            }
        });
    }

    private void toggleNotation() {
        GraphicalViewer viewer = getGraphicalViewer();
        RootModel root = (RootModel) viewer.getContents().getModel();
        String notation = StringUtils.defaultString(root.getNotation(), DecorationFactory.NOTATION_IE);
        if (DecorationFactory.NOTATION_IE.equals(notation)) {
            root.setNotation(DecorationFactory.NOTATION_IDEF1X);
        } else {
            root.setNotation(DecorationFactory.NOTATION_IE);
        }
    }
}
