package io.github.erde.editor.diagram.editpart.tree;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.erde.Activator;
import io.github.erde.editor.diagram.model.DomainModel;

/**
 * DomainTreeEditPart.
 *
 * @author modified by parapata
 */
public class DomainTreeEditPart extends DBTreeEditPart {

    private Logger logger = LoggerFactory.getLogger(DomainTreeEditPart.class);

    @Override
    protected void refreshVisuals() {
        DomainModel model = (DomainModel) getModel();

        List<String> args = new ArrayList<>();
        if (model.isSizeSupported() && model.getColumnSize() != null) {
            args.add(String.valueOf(model.getColumnSize()));
            if (model.isDecimalSupported() && model.getDecimal() != null) {
                args.add(String.valueOf(model.getDecimal()));
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s - %s", model.getDomainName(), model.getPhysicalName()));
        if (!args.isEmpty()) {
            sb.append(String.format("(%s)", String.join(",", args)));
        }

        setWidgetText(sb.toString());
        setWidgetImage(Activator.getImage(Activator.ICON_DOMAIN));
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        logger.info("更新処理イベント発生(TreeView) : {}", event.getPropertyName());
        super.propertyChange(event);
    }
}
