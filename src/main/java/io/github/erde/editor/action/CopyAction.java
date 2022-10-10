package io.github.erde.editor.action;

import static io.github.erde.Resource.*;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import io.github.erde.editor.ERDiagramEditor;
import io.github.erde.editor.diagram.model.BaseEntityModel;

/**
 * Copy selected entity models in the diagram editor.
 *
 * @author modified by parapata
 */
public class CopyAction extends SelectionAction implements IERDAction {

    private PasteAction pasteAction;

    public CopyAction(ERDiagramEditor editor, PasteAction pasteAction) {
        super(editor);
        setId(ActionFactory.COPY.getId());
        setText(ACTION_COPY.getValue());
        ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
        setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
        setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
        this.pasteAction = pasteAction;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        List<EditPart> selection = getSelectedObjects();
        List<BaseEntityModel> copies = new ArrayList<>();
        for (EditPart editPart : selection) {
            BaseEntityModel model = (BaseEntityModel) editPart.getModel();
            BaseEntityModel clone = model.clone();
            clone.generateId();
            copies.add(clone);
        }
        Clipboard.getDefault().setContents(copies);
        pasteAction.update();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected boolean calculateEnabled() {
        List<Object> selected = getSelectedObjects();
        if (selected.isEmpty()) {
            return true;
        }
        for (Object obj : selected) {
            if (!(obj instanceof EditPart)) {
                return false;
            }
            EditPart editPart = (EditPart) obj;
            if (BaseEntityModel.class.isAssignableFrom(editPart.getModel().getClass())) {
                return true;
            }
        }
        return false;
    }
}
