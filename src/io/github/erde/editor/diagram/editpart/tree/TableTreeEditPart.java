package io.github.erde.editor.diagram.editpart.tree;

import static io.github.erde.Resource.*;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.editparts.AbstractEditPart;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.ImageDataProvider;
import org.eclipse.swt.graphics.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.erde.ERDPlugin;
import io.github.erde.ICON;
import io.github.erde.editor.diagram.editpart.TableEditPart;
import io.github.erde.editor.diagram.editpart.tree.FolderTreeEditPart.FolderModel;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.IndexModel;
import io.github.erde.editor.diagram.model.TableModel;

/**
 * TableTreeEditPart.
 *
 * @author modified by parapata
 */
public class TableTreeEditPart extends DBTreeEditPart {

    private Logger logger = LoggerFactory.getLogger(TableTreeEditPart.class);

    private static final String IMAGE_TABLE_ERROR = "image_table_error";
    private static final String IMAGE_TABLE_WARNING = "image_table_warning";

    /**
     * A <code>CompositeImageDescriptor</code> implementation to create overlay icon.
     */
    private static class OverlayImageDescriptor extends CompositeImageDescriptor {

        private String baseImageKey;
        private String overlayImageKey;

        public OverlayImageDescriptor(String baseImageKey, String overlayImageKey) {
            this.baseImageKey = baseImageKey;
            this.overlayImageKey = overlayImageKey;
        }

        @Override
        protected void drawCompositeImage(int var1, int var2) {

            // Image baseImage = Activator.getImage(baseImageKey);
            // drawImage(baseImage.getImageData(), 0, 0);
            ImageDataProvider baseImageProvider = arg -> ERDPlugin.getImage(baseImageKey).getImageData();
            drawImage(baseImageProvider, 0, 0);

            // Image overlayImage = Activator.getImage(overlayImageKey);
            // drawImage(overlayImage.getImageData(), 0, 8);
            ImageDataProvider overlayImageProvider = arg -> ERDPlugin.getImage(overlayImageKey).getImageData();
            drawImage(overlayImageProvider, 0, 0);
        }

        @Override
        protected Point getSize() {
            return new Point(16, 16);
        }
    }

    // Register overlay icons to ImageRegistery of DBPlugin.
    static {
        ImageRegistry imageRegistry = ERDPlugin.getDefault().getImageRegistry();
        imageRegistry.put(IMAGE_TABLE_ERROR,
                new OverlayImageDescriptor(ICON.TABLE.getPath(), ICON.OVERLAY_ERROR.getPath()));
        imageRegistry.put(IMAGE_TABLE_WARNING,
                new OverlayImageDescriptor(ICON.TABLE.getPath(), ICON.OVERLAY_WARNING.getPath()));
    }

    @Override
    protected List<Object> getModelChildren() {
        List<Object> list = new ArrayList<>();
        List<ColumnModel> columns = ((TableModel) getModel()).getColumns();
        for (ColumnModel column : columns) {
            list.add(column);
        }
        list.add(new FolderModel(LABEL_INDEX.getValue(), null) {
            @Override
            public void doEdit() {
                TableModel table = (TableModel) getModel();
                TableEditPart.openTableEditDialog(getViewer(), table, (IndexModel) null);
            }

            @Override
            public List<?> getChildren() {
                List<IndexModel> indices = ((TableModel) getModel()).getIndices();
                List<IndexModel> list = new ArrayList<>();
                for (IndexModel indexModel : indices) {
                    list.add(indexModel);
                }
                return list;
            }
        });
        return list;
    }

    @Override
    protected void refreshVisuals() {
        TableModel model = (TableModel) getModel();
        setWidgetText(String.format("%s(%s)", model.getPhysicalName(), model.getLogicalName()));

        if (model.getError().isEmpty()) {
            setWidgetImage(ERDPlugin.getImage(ICON.TABLE.getPath()));
        } else {
            setWidgetImage(ERDPlugin.getImage(IMAGE_TABLE_WARNING));
        }

        @SuppressWarnings("unchecked")
        List<AbstractEditPart> children = getChildren();
        for (AbstractEditPart child : children) {
            child.refresh();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        logger.info("更新処理イベント発生(TreeView) : {}", event.getPropertyName());
        super.propertyChange(event);

        String propName = event.getPropertyName();
        TableModel model = (TableModel) getModel();

        if (TableModel.P_LOGICAL_NAME.equals(propName) || TableModel.P_TABLE_NAME.equals(propName)) {
            setWidgetText(String.format("%s(%s)", model.getPhysicalName(), model.getLogicalName()));
        } else if (TableModel.P_ERROR.equals(propName)) {
            refreshVisuals();

        }
        if (TableModel.P_COLUMNS.equals(propName) || TableModel.P_INDICES.equals(propName)) {
            refreshChildren();
            @SuppressWarnings("unchecked")
            List<AbstractEditPart> children = getChildren();
            for (AbstractEditPart child : children) {
                child.refresh();
            }
        }
    }
}
