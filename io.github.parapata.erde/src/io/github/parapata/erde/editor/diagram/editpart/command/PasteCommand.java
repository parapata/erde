package io.github.parapata.erde.editor.diagram.editpart.command;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.Clipboard;

import io.github.parapata.erde.editor.diagram.model.BaseEntityModel;
import io.github.parapata.erde.editor.diagram.model.RootModel;

/**
 * PasteCommand.
 *
 * @author modified by parapata
 */
public class PasteCommand extends Command {

    private GraphicalViewer viewer;
    private List<BaseEntityModel> targets;

    public PasteCommand(GraphicalViewer viewer, List<BaseEntityModel> targets) {
        this.viewer = viewer;
        this.targets = targets;
    }

    @Override
    public void execute() {
        List<BaseEntityModel> copies = new ArrayList<>();
        for (BaseEntityModel model : targets) {
            model.setId(model.generateId());
            model.getModelTargetConnections().clear();
            model.getModelSourceConnections().clear();

            model.setConstraint(getNewRectangle(model.getConstraint()));
            addBaseEntityModel(model);
            copies.add(model.clone());
        }
        Clipboard.getDefault().setContents(copies);
        // focus();
    }

    @Override
    public void undo() {
        for (BaseEntityModel model : targets) {
            removeBaseEntityModel(model);
        }
        Clipboard.getDefault().setContents(targets);
    }

    private Rectangle getNewRectangle(Rectangle rect) {
        Rectangle newRect = new Rectangle();
        newRect.x = rect.x + 20;
        newRect.y = rect.y + 20;
        newRect.width = rect.width;
        newRect.height = rect.height;
        return newRect;
    }

    private void addBaseEntityModel(BaseEntityModel baseEntityModel) {
        RootModel rootModel = (RootModel) viewer.getRootEditPart().getContents().getModel();
        rootModel.addChild(baseEntityModel);
    }

    private void removeBaseEntityModel(BaseEntityModel baseEntityModel) {
        RootModel rootModel = (RootModel) viewer.getRootEditPart().getContents().getModel();
        rootModel.removeChild(baseEntityModel);
    }
}
