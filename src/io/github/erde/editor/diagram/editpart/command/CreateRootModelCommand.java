package io.github.erde.editor.diagram.editpart.command;

import org.eclipse.gef.commands.Command;

import io.github.erde.IMessages;
import io.github.erde.editor.diagram.model.BaseEntityModel;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;

/**
 * CreateRootModelCommand.
 *
 * @author modified by parapata
 */
public class CreateRootModelCommand extends Command implements IMessages {

    private RootModel root;
    private BaseEntityModel model;

    @Override
    public void execute() {
        root.addChild(model);
    }

    public void setRootModel(Object root) {
        this.root = (RootModel) root;
    }

    public void setModel(Object model) {
        this.model = (BaseEntityModel) model;
        if (this.model instanceof TableModel) {
            ((TableModel) this.model).setPhysicalName("TABLE_" + (root.getChildren().size() + 1));
            ((TableModel) this.model).setLogicalName(getResource("label.table") + (root.getChildren().size() + 1));
        }
    }

    @Override
    public void undo() {
        root.removeChild(model);
    }
}
