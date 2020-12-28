package io.github.erde.editor.validator;

import org.eclipse.core.resources.IMarker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.ui.ide.IGotoMarker;

import io.github.erde.core.util.UIUtils;
import io.github.erde.editor.diagram.editpart.RootEditPart;
import io.github.erde.editor.diagram.editpart.TableEditPart;
import io.github.erde.editor.diagram.model.TableModel;

/**
 * IERDGotoMarker.
 *
 * @author modified by parapata
 */
public interface IERDGotoMarker extends IGotoMarker {

    @Override
    default void gotoMarker(IMarker marker) {
        String id = marker.getAttribute(IMarker.SOURCE_ID, null);
        if (id == null) {
            return;
        }

        GraphicalViewer viewer = (GraphicalViewer) UIUtils.getActiveEditor().getAdapter(GraphicalViewer.class);
        EditPart editPart = viewer.getContents();
        EditPart found = findById(editPart, id);
        if (found == null) {
            return;
        }

        // 見つかった図形を選択
        viewer.select(found);
        viewer.reveal(found);
    }

    default EditPart findById(EditPart editPart, String id) {

        if (editPart instanceof RootEditPart) {
            for (Object obj : editPart.getChildren()) {
                EditPart child = (EditPart) obj;
                EditPart found = findById(child, id);
                if (found != null) {
                    return found;
                }
            }
        } else if (editPart instanceof TableEditPart) {
            TableModel model = (TableModel) ((TableEditPart) editPart).getModel();
            if (model.getId().equals(id)) {
                return editPart;
            }
        }
        return null;
    }
}
