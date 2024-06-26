package io.github.parapata.erde.editor.diagram.editpart.editpolicy;

import static io.github.parapata.erde.Resource.*;

import java.util.function.Predicate;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.parapata.erde.core.util.StringUtils;
import io.github.parapata.erde.core.util.swt.UIUtils;
import io.github.parapata.erde.dialect.type.IndexType;
import io.github.parapata.erde.editor.diagram.editpart.command.CreateConnectionCommand;
import io.github.parapata.erde.editor.diagram.model.BaseConnectionModel;
import io.github.parapata.erde.editor.diagram.model.BaseEntityModel;
import io.github.parapata.erde.editor.diagram.model.ColumnModel;
import io.github.parapata.erde.editor.diagram.model.IndexModel;
import io.github.parapata.erde.editor.diagram.model.RelationshipModel;
import io.github.parapata.erde.editor.diagram.model.TableModel;

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
                UIUtils.openAlertDialog(ERROR_SELECT_REFERENCE_KEY);
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
