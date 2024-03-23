package io.github.parapata.erde.editor.diagram.editpart;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import io.github.parapata.erde.editor.diagram.model.NoteConnectionModel;
import io.github.parapata.erde.editor.diagram.model.NoteModel;
import io.github.parapata.erde.editor.diagram.model.RelationshipModel;
import io.github.parapata.erde.editor.diagram.model.RootModel;
import io.github.parapata.erde.editor.diagram.model.TableModel;

/**
 * ErdeEditPartFactory.
 *
 * @author modified by parapata
 */
public class ErdeEditPartFactory implements EditPartFactory {

    @Override
    public EditPart createEditPart(EditPart context, Object model) {
        EditPart editPart = null;

        if (model instanceof RootModel) {
            editPart = new RootEditPart();
        } else if (model instanceof TableModel) {
            editPart = new TableEditPart();
        } else if (model instanceof NoteModel) {
            editPart = new NoteEditPart();
        } else if (model instanceof RelationshipModel) {
            editPart = new RelationshipEditPart();
        } else if (model instanceof NoteConnectionModel) {
            editPart = new NoteConnectionEditPart();
        }

        if (editPart != null) {
            editPart.setModel(model);
        }

        return editPart;
    }

}
