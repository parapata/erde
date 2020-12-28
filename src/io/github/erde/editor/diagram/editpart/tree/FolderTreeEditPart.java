package io.github.erde.editor.diagram.editpart.tree;

import java.util.List;

import io.github.erde.Activator;
import io.github.erde.editor.diagram.model.RootModel;

/**
 * FolderTreeEditPart.
 *
 * @author modified by parapata
 */
public class FolderTreeEditPart extends DBTreeEditPart {

    @Override
    protected List<?> getModelChildren() {
        FolderModel model = (FolderModel) getModel();
        return model.getChildren();
    }

    @Override
    protected void refreshVisuals() {
        FolderModel model = (FolderModel) getModel();
        setWidgetText(model.name);
        setWidgetImage(Activator.getImage(Activator.ICON_FOLDER));
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