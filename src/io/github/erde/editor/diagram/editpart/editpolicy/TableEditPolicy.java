package io.github.erde.editor.diagram.editpart.editpolicy;

import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.erde.dialect.type.IndexType;
import io.github.erde.editor.diagram.editpart.command.CreateConnectionCommand;
import io.github.erde.editor.diagram.model.BaseConnectionModel;
import io.github.erde.editor.diagram.model.BaseEntityModel;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.IndexModel;
import io.github.erde.editor.diagram.model.RelationshipModel;
import io.github.erde.editor.diagram.model.TableModel;
import io.github.erde.editor.dialog.ERDMessageDialog;

/**
 * TableEditPolicy.
 *
 * @author modified by parapata
 */
public class TableEditPolicy extends NodeEditPolicy {

    private Logger logger = LoggerFactory.getLogger(TableEditPolicy.class);

    public TableEditPolicy(BaseEntityModel model) {
        super(model);
    }

    @Override
    protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
        BaseConnectionModel connection = ((CreateConnectionCommand) request.getStartCommand()).getConnection();
        BaseEntityModel model = (BaseEntityModel) getHost().getModel();
        if (!model.canTarget(connection)) {
            return null;
        }

        if (connection instanceof RelationshipModel
                && connection.getSource() instanceof TableModel
                && connection.getTarget() instanceof TableModel) {
            TableModel table = (TableModel) request.getSourceEditPart().getModel();
            if (!validate(table)) {
                ERDMessageDialog.openAlert("参照キーとして利用できるカラムがありません");
                return null;
            }
        }
        CreateConnectionCommand command = (CreateConnectionCommand) request.getStartCommand();
        command.setTarget(model);

        if (!command.canExecute()) {
            return null;
        }

        if (isDuplicate(request)) {
            return null;
        }

        return command;
    }

    @SuppressWarnings("unchecked")
    private boolean isDuplicate(CreateConnectionRequest request) {
        GraphicalEditPart editPart = (GraphicalEditPart) request.getTargetEditPart();
        BaseConnectionModel connection = ((CreateConnectionCommand) request.getStartCommand()).getConnection();

        Predicate<GraphicalEditPart> duplicate = predicate -> {
            return ((BaseConnectionModel) predicate.getModel()).getTarget().getModelTargetConnections().stream()
                    .anyMatch(source -> equals(source, connection));
        };

        return editPart.getTargetConnections().stream().anyMatch(duplicate);
    }

    private boolean equals(BaseConnectionModel source, BaseConnectionModel target) {
        BaseEntityModel refSourceElement = source.getSource();
        BaseEntityModel refTargetElement = target.getSource();
        BaseEntityModel sourceElement = source.getTarget();
        BaseEntityModel targetElement = target.getTarget();

        return StringUtils.equals(refSourceElement.getId(), refTargetElement.getId())
                && StringUtils.equals(sourceElement.getId(), targetElement.getId());
    }

    private boolean validate(TableModel table) {
        Predicate<ColumnModel> column = predicate -> {
            return predicate.isPrimaryKey() || predicate.isUniqueKey();
        };
        Predicate<IndexModel> index = predicate -> {
            return IndexType.UNIQUE.equals(predicate.getIndexType());
        };

        if (table.getColumns().stream().anyMatch(column) || table.getIndices().stream().anyMatch(index)) {
            return true;
        } else {
            return false;
        }
    }
}
