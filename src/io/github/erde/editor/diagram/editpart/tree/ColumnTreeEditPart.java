package io.github.erde.editor.diagram.editpart.tree;

import io.github.erde.Activator;
import io.github.erde.editor.diagram.model.ColumnModel;

/**
 * ColumnTreeEditPart.
 *
 * @author modified by parapata
 */
public class ColumnTreeEditPart extends DBTreeEditPart {

    @Override
    protected void refreshVisuals() {
        ColumnModel model = (ColumnModel) getModel();

        StringBuilder sb = new StringBuilder();
        sb.append(model.getPhysicalName()).append("(").append(model.getLogicalName()).append(")");
        sb.append(" - ");
        sb.append(model.getColumnType().getPhysicalName());
        if (model.getColumnType().isSizeSupported()) {
            sb.append("(").append(model.getColumnSize()).append(")");
        }

        setWidgetText(sb.toString());

        if (model.isPrimaryKey()) {
            setWidgetImage(Activator.getImage(Activator.ICON_PK_COLUMN));
        } else {
            setWidgetImage(Activator.getImage(Activator.ICON_COLUMN));
        }
        // TODO changes an image for foreign key columns
    }
}