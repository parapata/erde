package io.github.erde.editor.diagram.editpart.tree;

import io.github.erde.Activator;
import io.github.erde.editor.diagram.model.DomainModel;

/**
 * DomainTreeEditPart.
 *
 * @author modified by parapata
 */
public class DomainTreeEditPart extends DBTreeEditPart {

    @Override
    protected void refreshVisuals() {
        DomainModel model = (DomainModel) getModel();

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s - %s", model.getDomainName(), model.getPhysicalName()));
        if (model.isSizeSupported()) {
            sb.append(String.format("(%d)", model.getColumnSize()));
        }

        setWidgetText(sb.toString());
        setWidgetImage(Activator.getImage(Activator.ICON_DOMAIN));
    }
}
