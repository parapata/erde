package io.github.erde.editor.diagram.editpart.command;

import java.util.List;

import org.eclipse.gef.commands.Command;

import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.IndexModel;
import io.github.erde.editor.diagram.model.TableModel;

/**
 * TableEditCommand.
 *
 * @author modified by parapata
 */
public class TableEditCommand extends Command {

    private TableModel model;

    private String oldTableName;
    private String oldTableLogicalName;
    private String oldTableDescription;
    private List<ColumnModel> oldColumns;
    private List<IndexModel> oldIndices;

    private String newTableName;
    private String newTableLogicalName;
    private String newTableDescription;
    private List<ColumnModel> newColumns;
    private List<IndexModel> newIndices;

    public TableEditCommand(TableModel model, String newTableName, String newTableLogicalName,
            String newTableDescription, List<ColumnModel> newColumns, List<IndexModel> newIndices) {

        this.model = model;
        this.oldTableName = model.getPhysicalName();
        this.newTableName = newTableName;
        this.oldTableLogicalName = model.getLogicalName();
        this.newTableLogicalName = newTableLogicalName;
        this.oldTableDescription = model.getDescription();
        this.newTableDescription = newTableDescription;
        this.oldColumns = model.getColumns();
        this.newColumns = newColumns;
        this.oldIndices = model.getIndices();
        this.newIndices = newIndices;
    }

    @Override
    public void execute() {
        this.model.setPhysicalName(newTableName);
        this.model.setLogicalName(newTableLogicalName);
        this.model.setDescription(newTableDescription);
        this.model.setColumns(newColumns);
        this.model.setIndices(newIndices);
    }

    @Override
    public void undo() {
        this.model.setPhysicalName(oldTableName);
        this.model.setLogicalName(oldTableLogicalName);
        this.model.setDescription(oldTableDescription);
        this.model.setColumns(oldColumns);
        this.model.setIndices(oldIndices);
    }
}
