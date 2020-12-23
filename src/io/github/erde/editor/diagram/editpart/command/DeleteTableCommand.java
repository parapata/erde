package io.github.erde.editor.diagram.editpart.command;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;

import io.github.erde.editor.diagram.model.BaseConnectionModel;
import io.github.erde.editor.diagram.model.BaseEntityModel;
import io.github.erde.editor.diagram.model.RootModel;

/**
 * DeleteTableCommand.
 *
 * @author modified by parapata
 */
public class DeleteTableCommand extends Command {

    private RootModel root;
    private BaseEntityModel model;

    private List<BaseConnectionModel> sourceConnections = new ArrayList<>();
    private List<BaseConnectionModel> targetConnections = new ArrayList<>();

    @Override
    public void execute() {
        sourceConnections.addAll(model.getModelSourceConnections());
        targetConnections.addAll(model.getModelTargetConnections());
        for (BaseConnectionModel model : sourceConnections) {
            model.detachSource();
            model.detachTarget();
        }
        for (BaseConnectionModel model : targetConnections) {
            model.detachSource();
            model.detachTarget();
        }
        root.removeChild(model);
    }

    public void setRootModel(Object root) {
        this.root = (RootModel) root;
    }

    public void setTargetModel(Object model) {
        this.model = (BaseEntityModel) model;
    }

    @Override
    public void undo() {
        root.addChild(model);
        for (BaseConnectionModel model : sourceConnections) {
            model.attachSource();
            model.attachTarget();
        }
        for (BaseConnectionModel model : targetConnections) {
            model.attachSource();
            model.attachTarget();
        }
        sourceConnections.clear();
        targetConnections.clear();
    }
}
