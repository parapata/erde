package io.github.erde.editor.diagram.editpart.tree;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import io.github.erde.editor.diagram.editpart.tree.FolderTreeEditPart.FolderModel;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.DomainModel;
import io.github.erde.editor.diagram.model.IndexModel;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;

/**
 * DBTreeEditPartFactory.
 *
 * @author modified by parapata
 */
public class DBTreeEditPartFactory implements EditPartFactory {

    @Override
    public EditPart createEditPart(EditPart context, Object model) {
        EditPart part = null;
        if (model instanceof RootModel) {
            part = new RootTreeEditPart();
        } else if (model instanceof TableModel) {
            part = new TableTreeEditPart();
        } else if (model instanceof ColumnModel) {
            part = new ColumnTreeEditPart();
        } else if (model instanceof FolderModel) {
            part = new FolderTreeEditPart();
        } else if (model instanceof DomainModel) {
            part = new DomainTreeEditPart();
        } else if (model instanceof IndexModel) {
            part = new IndexTreeEditPart();
        }
        if (part != null) {
            part.setModel(model);
        }
        return part;
    }
}
