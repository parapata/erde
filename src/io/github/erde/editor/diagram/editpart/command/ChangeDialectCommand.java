package io.github.erde.editor.diagram.editpart.command;

import org.eclipse.gef.commands.Command;

import io.github.erde.dialect.DialectProvider;
import io.github.erde.dialect.IDialect;
import io.github.erde.dialect.type.IColumnType;
import io.github.erde.editor.diagram.model.BaseEntityModel;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;

/**
 * ChangeDialectCommand.
 *
 * @author modified by parapata
 */
public class ChangeDialectCommand extends Command {

    private DialectProvider dialectProvider;
    private RootModel rootModel;

    public ChangeDialectCommand(DialectProvider dialectProvider, RootModel rootModel) {
        this.dialectProvider = dialectProvider;
        this.rootModel = rootModel;
    }

    @Override
    public void execute() {
        for (BaseEntityModel entity : rootModel.getChildren()) {
            if (entity instanceof TableModel) {
                TableModel table = (TableModel) entity;
                for (ColumnModel column : table.getColumns()) {
                    IColumnType type = getColumnType(dialectProvider.getDialect(), table, column);
                    column.setColumnType(type);
                }
                table.setColumns(table.getColumns());
            }
        }
        rootModel.setDialectProvider(dialectProvider);
    }

    private IColumnType getColumnType(IDialect dialect, TableModel table, ColumnModel columnModel) {
        IColumnType type = dialect.getColumnType(columnModel.getColumnType().getType());
        if (type == null) {
            type = dialect.getDefaultColumnType();
        }
        return type;
    }
}
