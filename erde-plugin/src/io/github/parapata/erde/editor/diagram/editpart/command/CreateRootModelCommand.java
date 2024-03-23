package io.github.parapata.erde.editor.diagram.editpart.command;

import static io.github.parapata.erde.Resource.*;

import org.eclipse.gef.commands.Command;

import io.github.parapata.erde.editor.diagram.model.BaseEntityModel;
import io.github.parapata.erde.editor.diagram.model.RootModel;
import io.github.parapata.erde.editor.diagram.model.TableModel;

/**
 * CreateRootModelCommand.
 *
 * @author modified by parapata
 */
public class CreateRootModelCommand extends Command {

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
            ((TableModel) this.model).setLogicalName(LABEL_TABLE.getValue() + (root.getChildren().size() + 1));
        }
    }

    @Override
    public void undo() {
        root.removeChild(model);
    }
}
