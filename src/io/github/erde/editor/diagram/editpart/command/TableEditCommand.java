package io.github.erde.editor.diagram.editpart.command;

import org.eclipse.gef.commands.Command;

import io.github.erde.editor.diagram.model.TableModel;
import io.github.erde.editor.dialog.table.ITableEdit;

/**
 * TableEditCommand.
 *
 * @author modified by parapata
 */
public class TableEditCommand extends Command {

    private TableModel newTable;
    private TableModel oldTable;
    private ITableEdit tableEdit;

    public TableEditCommand(TableModel table, ITableEdit tableEdit) {
        this.oldTable = table.clone();
        this.newTable = table;
        this.tableEdit = tableEdit;
    }

    @Override
    public void execute() {

        this.newTable.setPhysicalName(tableEdit.getPhysicalName());
        this.newTable.setLogicalName(tableEdit.getLogicalName());
        this.newTable.setDescription(tableEdit.getDescription());
        this.newTable.setColumns(tableEdit.getColumns());
        this.newTable.setIndices(tableEdit.getIndices());
        super.execute();
    }

    @Override
    public void undo() {
        this.newTable = this.oldTable;
        this.oldTable = null;
        super.undo();
    }
}
