package io.github.erde.editor.diagram.editpart.tree;

import java.util.ArrayList;
import java.util.List;

import io.github.erde.Activator;
import io.github.erde.dialect.type.IColumnType;
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
        IColumnType columnType = model.getColumnType();

        List<String> args = new ArrayList<>();
        if (columnType.isSizeSupported() && model.getColumnSize() != null) {
            args.add(String.valueOf(model.getColumnSize()));
            if (columnType.isDecimalSupported() && model.getDecimal() != null) {
                args.add(String.valueOf(model.getDecimal()));
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s(%s) - %s",
                model.getPhysicalName(),
                model.getLogicalName(),
                columnType.getPhysicalName()));
        if (!args.isEmpty()) {
            sb.append(String.format("(%s)", String.join(",", args)));
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
