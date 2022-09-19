package io.github.erde.editor;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;

import io.github.erde.editor.action.DomainEditAction;
import io.github.erde.editor.diagram.editpart.TableEditPart;
import io.github.erde.editor.diagram.editpart.tree.FolderTreeEditPart.FolderModel;
import io.github.erde.editor.diagram.model.BaseEntityModel;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.DomainModel;
import io.github.erde.editor.diagram.model.IModel;
import io.github.erde.editor.diagram.model.IndexModel;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;

/**
 * ModelEditor.
 *
 * @author modified by parapata
 */
public class ModelEditor {

    private GraphicalViewer viewer;
    private boolean editTable;

    public ModelEditor(GraphicalViewer viewer, boolean editTable) {
        this.viewer = viewer;
        this.editTable = editTable;
    }

    public void editModel(IModel model) {
        EditPart rootEditPart = viewer.getContents();
        RootModel rootModel = (RootModel) rootEditPart.getModel();

        if (model instanceof TableModel) {
            if (editTable) {
                TableModel tableModel = (TableModel) model;
                TableEditPart.openTableEditDialog(viewer, tableModel);
            } else {
                EditPart select = getSelectEditPart(rootEditPart, model);
                if (select != null) {
                    viewer.select(select);
                    viewer.reveal(select);
                }
            }

        } else if (model instanceof DomainModel) {
            new DomainEditAction((DomainModel) model).run();

        } else if (model instanceof ColumnModel) {
            TableModel parent = null;
            LOOP: for (BaseEntityModel entity : rootModel.getChildren()) {
                if (entity instanceof TableModel) {
                    TableModel table = (TableModel) entity;
                    for (ColumnModel column : table.getColumns()) {
                        if (column == model) {
                            parent = table;
                            break LOOP;
                        }
                    }
                }
            }
            if (parent != null) {
                TableEditPart.openTableEditDialog(viewer, parent, (ColumnModel) model);
            }

        } else if (model instanceof FolderModel) {
            ((FolderModel) model).doEdit();

        } else if (model instanceof IndexModel) {
            TableModel parent = null;
            LOOP: for (BaseEntityModel entity : rootModel.getChildren()) {
                if (entity instanceof TableModel) {
                    TableModel table = (TableModel) entity;
                    for (IndexModel index : table.getIndices()) {
                        if (index == model) {
                            parent = table;
                            break LOOP;
                        }
                    }
                }
            }
            if (parent != null) {
                TableEditPart.openTableEditDialog(viewer, parent, (IndexModel) model);
            }
        }

    }

    private EditPart getSelectEditPart(EditPart part, IModel model) {
        if (part.getModel() == model) {
            return part;
        }
        for (Object child : part.getChildren()) {
            EditPart result = getSelectEditPart((EditPart) child, model);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

}
