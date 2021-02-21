package io.github.erde.editor.diagram.editpart.tree;

import static io.github.erde.Resource.*;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.erde.Activator;
import io.github.erde.ICON;
import io.github.erde.editor.diagram.model.RootModel;

/**
 * FolderTreeEditPart.
 *
 * @author modified by parapata
 */
public class FolderTreeEditPart extends DBTreeEditPart {

    private Logger logger = LoggerFactory.getLogger(FolderTreeEditPart.class);

    @Override
    protected List<?> getModelChildren() {
        FolderModel model = (FolderModel) getModel();
        return model.getChildren();
    }

    @Override
    protected void refreshVisuals() {
        FolderModel model = (FolderModel) getModel();
        setWidgetText(model.name);
        setWidgetImage(Activator.getImage(ICON.FOLDER.getPath()));
    }

    public boolean isTable() {
        FolderModel model = (FolderModel) getModel();
        return model.name.equals(LABEL_TABLE.getValue());
    }

    public boolean isDomain() {
        FolderModel model = (FolderModel) getModel();
        return model.name.equals(LABEL_DOMAIN.getValue());
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        logger.info("更新処理イベント発生(TreeView) : {}", event.getPropertyName());
        super.propertyChange(event);
    }

    public static abstract class FolderModel {
        public String name;
        public RootModel root;

        public FolderModel(String name, RootModel root) {
            this.name = name;
            this.root = root;
        }

        public abstract List<?> getChildren();

        public abstract void doEdit();
    }
}
