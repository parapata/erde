package io.github.erde.editor.diagram.editpart.tree;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.editparts.AbstractEditPart;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.swt.graphics.ImageDataProvider;
import org.eclipse.swt.graphics.Point;

import io.github.erde.Activator;
import io.github.erde.IMessages;
import io.github.erde.editor.diagram.editpart.TableEditPart;
import io.github.erde.editor.diagram.editpart.tree.FolderTreeEditPart.FolderModel;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.IndexModel;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;

/**
 * TableTreeEditPart.
 *
 * @author modified by parapata
 */
public class TableTreeEditPart extends DBTreeEditPart implements IMessages {

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
            ImageDataProvider baseImageProvider = arg -> Activator.getImage(baseImageKey).getImageData();
            drawImage(baseImageProvider, 0, 0);

            // Image overlayImage = Activator.getImage(overlayImageKey);
            // drawImage(overlayImage.getImageData(), 0, 8);
            ImageDataProvider overlayImageProvider = arg -> Activator.getImage(overlayImageKey).getImageData();
            drawImage(overlayImageProvider, 0, 0);
        }

        @Override
        protected Point getSize() {
            return new Point(16, 16);
        }
    }

    // Register overlay icons to ImageRegistery of DBPlugin.
    static {
        Activator.getDefault().getImageRegistry().put(IMAGE_TABLE_ERROR,
                new OverlayImageDescriptor(Activator.ICON_TABLE, Activator.ICON_OVERLAY_ERROR));

        Activator.getDefault().getImageRegistry().put(IMAGE_TABLE_WARNING,
                new OverlayImageDescriptor(Activator.ICON_TABLE, Activator.ICON_OVERLAY_WARNING));
    }

    @Override
    protected List<Object> getModelChildren() {
        List<Object> list = new ArrayList<>();
        List<ColumnModel> columns = ((TableModel) getModel()).getColumns();
        for (ColumnModel column : columns) {
            list.add(column);
        }
        list.add(new FolderModel(getResource("label.index"), null) {
            @Override
            public void doEdit() {
                TableModel table = (TableModel) getModel();
                TableEditPart.openTableEditDialog(getViewer(), (RootModel) getRoot().getContents().getModel(), table,
                        (IndexModel) null);
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
        setWidgetText(String.format("%s - %s", model.getPhysicalName(), model.getLogicalName()));

        if (model.getError().isEmpty()) {
            setWidgetImage(Activator.getImage(Activator.ICON_TABLE));
        } else {
            setWidgetImage(Activator.getImage(IMAGE_TABLE_WARNING));
        }

        @SuppressWarnings("unchecked")
        List<AbstractEditPart> children = getChildren();
        for (AbstractEditPart child : children) {
            child.refresh();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        super.propertyChange(event);
        String propName = event.getPropertyName();
        TableModel model = (TableModel) getModel();

        if (TableModel.P_LOGICAL_NAME.equals(propName) || TableModel.P_TABLE_NAME.equals(propName)) {
            setWidgetText(String.format("%s - %s", model.getPhysicalName(), model.getLogicalName()));
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
