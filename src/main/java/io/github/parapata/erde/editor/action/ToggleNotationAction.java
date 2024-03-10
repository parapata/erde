package io.github.parapata.erde.editor.action;

import static io.github.parapata.erde.Resource.*;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.Action;

import io.github.parapata.erde.core.util.StringUtils;
import io.github.parapata.erde.editor.diagram.figure.connection.decoration.DecorationFactory;
import io.github.parapata.erde.editor.diagram.model.RootModel;

/**
 * ToggleNotationAction.
 *
 * @author modified by parapata
 */
public class ToggleNotationAction extends Action implements IERDAction {

    public ToggleNotationAction() {
        super();
        setId(TOGGLE_NOTATION);
        setText(ACTION_TOGGLE_NOTATION.getValue());
    }

    @Override
    public void run() {
        CommandStack stack = getERDCommandStack();

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
