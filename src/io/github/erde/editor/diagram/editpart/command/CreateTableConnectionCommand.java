package io.github.erde.editor.diagram.editpart.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.RelationshipModel;
import io.github.erde.editor.diagram.model.TableModel;

/**
 * CreateTableConnectionCommand.
 *
 * @author modified by parapata
 */
public class CreateTableConnectionCommand extends CreateConnectionCommand {

    protected List<ColumnModel> oldColumns;

    @Override
    public void execute() {

        RelationshipModel model = getRelationshipModel();

        model.setForeignKeyName(createForeignKey());
        TableModel table = getTableModel();
        model.getMappings()
                .stream()
                .filter(predicate -> predicate.getReferenceKey() == null)
                .forEach(mapping -> {
                    ColumnModel targetColumn = mapping.getForeignKey();
                    ColumnModel referColumn = new ColumnModel();
                    referColumn.setPhysicalName(targetColumn.getPhysicalName());
                    referColumn.setLogicalName(targetColumn.getLogicalName());
                    referColumn.setColumnType(targetColumn.getColumnType());
                    referColumn.setColumnSize(targetColumn.getColumnSize());
                    referColumn.setDescription(targetColumn.getDescription());

                    oldColumns = table.getColumns();
                    List<ColumnModel> newArray = new ArrayList<>();
                    newArray.addAll(oldColumns);
                    newArray.add(referColumn);

                    table.setColumns(newArray);
                });
        super.execute();
    }

    @Override
    public void undo() {
        if (oldColumns != null) {
            TableModel table = (TableModel) getModel();
            table.setColumns(oldColumns);
        }
        super.undo();
    }

    private String createForeignKey() {
        long count = this.getConnection().getTarget().getModelTargetConnections().size();
        String tableName = StringUtils.upperCase(getTargetTableModel().getPhysicalName());
        return String.format("FK_%s_%d", tableName, count);
    }

    public TableModel getTableModel() {
        return (TableModel) super.getModel();
    }

    public TableModel getSourceTableModel() {
        return (TableModel) super.getSource();
    }

    public TableModel getTargetTableModel() {
        return (TableModel) super.getTarget();
    }

    private RelationshipModel getRelationshipModel() {
        return (RelationshipModel) super.getConnection();
    }
}
