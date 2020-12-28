package io.github.erde.editor.action;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.Window;

import io.github.erde.IMessages;
import io.github.erde.editor.diagram.editpart.command.DomainEditCommand;
import io.github.erde.editor.diagram.model.DomainModel;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.dialog.DomainEditDialog;

/**
 * Opens the DomainEditDialog.
 *
 * @author modified by parapata
 */
public class DomainEditAction extends Action implements IERDEAction {

    private DomainModel editDomain;

    public DomainEditAction() {
        super();
        setId(DOMAING_EDIT);
        setText(getResource("action.editDomain"));
    }

    public DomainEditAction(DomainModel editDomain) {
        super(IMessages.resource.getString("action.editDomain"));
        this.editDomain = editDomain;
    }

    @Override
    public void run() {
        GraphicalViewer viewer = getGraphicalViewer();
        RootModel root = (RootModel) viewer.getContents().getModel();
        DomainEditDialog dialog = new DomainEditDialog(viewer.getControl().getShell(), root, editDomain);
        if (dialog.open() == Window.OK) {
            CommandStack commandStack = viewer.getEditDomain().getCommandStack();
            commandStack.execute(new DomainEditCommand(root, dialog.getResult(), root.getDomains()));
        }
    }
}
