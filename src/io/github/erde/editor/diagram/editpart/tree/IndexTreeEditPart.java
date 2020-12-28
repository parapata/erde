package io.github.erde.editor.diagram.editpart.tree;

import io.github.erde.Activator;
import io.github.erde.editor.diagram.model.IndexModel;

/**
 * IndexTreeEditPart.
 *
 * @author modified by parapata
 */
public class IndexTreeEditPart extends DBTreeEditPart {

    @Override
    protected void refreshVisuals() {
        IndexModel model = (IndexModel) getModel();
        setWidgetText(model.toString());
        setWidgetImage(Activator.getImage(Activator.ICON_INDEX));
    }
}
