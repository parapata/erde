package io.github.erde.editor.diagram.editpart.tree;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.erde.ERDPlugin;
import io.github.erde.ICON;
import io.github.erde.dialect.type.IColumnType;
import io.github.erde.editor.diagram.model.ColumnModel;

/**
 * ColumnTreeEditPart.
 *
 * @author modified by parapata
 */
public class ColumnTreeEditPart extends DBTreeEditPart {

    private Logger logger = LoggerFactory.getLogger(ColumnTreeEditPart.class);

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
            setWidgetImage(ERDPlugin.getImage(ICON.PK_COLUMN.getPath()));
        } else {
            setWidgetImage(ERDPlugin.getImage(ICON.COLUMN.getPath()));
        }
        // TODO changes an image for foreign key columns
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        logger.info("更新処理イベント発生(TreeView) : {}", event.getPropertyName());
        super.propertyChange(event);
    }
}
