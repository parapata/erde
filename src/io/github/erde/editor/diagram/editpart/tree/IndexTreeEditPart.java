package io.github.erde.editor.diagram.editpart.tree;

import java.beans.PropertyChangeEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.erde.ERDPlugin;
import io.github.erde.ICON;
import io.github.erde.editor.diagram.model.IndexModel;

/**
 * IndexTreeEditPart.
 *
 * @author modified by parapata
 */
public class IndexTreeEditPart extends DBTreeEditPart {

    private Logger logger = LoggerFactory.getLogger(IndexTreeEditPart.class);

    @Override
    protected void refreshVisuals() {
        IndexModel model = (IndexModel) getModel();
        setWidgetText(model.toString());
        setWidgetImage(ERDPlugin.getImage(ICON.INDEX.getPath()));
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        logger.info("更新処理イベント発生(TreeView) : {}", event.getPropertyName());
        super.propertyChange(event);
    }
}
