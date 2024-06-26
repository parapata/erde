package io.github.parapata.erde.editor.action;

import static io.github.parapata.erde.Resource.*;

import java.util.List;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import io.github.parapata.erde.editor.ErdeDiagramEditor;
import io.github.parapata.erde.editor.diagram.editpart.command.PasteCommand;
import io.github.parapata.erde.editor.diagram.model.BaseEntityModel;

/**
 * Paste copied entity models in the diagram editor.
 *
 * @author modified by parapata
 */
public class PasteAction extends SelectionAction implements IErdeAction {

    public PasteAction(ErdeDiagramEditor editor) {
        super(editor);
        setId(ActionFactory.PASTE.getId());
        setText(ACTION_PASTE.getValue());
        ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
        setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
        setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        List<BaseEntityModel> models = (List<BaseEntityModel>) Clipboard.getDefault().getContents();
        if (models == null) {
            return;
        }
        CommandStack stack = getCommandStack();
        stack.execute(new PasteCommand(getGraphicalViewer(), models));
    }

    @Override
    @SuppressWarnings("unchecked")
    protected boolean calculateEnabled() {
        Object obj = Clipboard.getDefault().getContents();
        if (obj == null) {
            return false;
        }
        if (obj instanceof List) {
            List<Object> list = (List<Object>) obj;
            for (Object element : list) {
                if (BaseEntityModel.class.isAssignableFrom(element.getClass())) {
                    return true;
                }
            }
        }
        return true;
    }
}
