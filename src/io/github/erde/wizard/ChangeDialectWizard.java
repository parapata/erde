package io.github.erde.wizard;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.wizard.Wizard;

import io.github.erde.IMessages;
import io.github.erde.dialect.DialectProvider;
import io.github.erde.dialect.IDialect;
import io.github.erde.dialect.type.IColumnType;
import io.github.erde.editor.diagram.editpart.command.ChangeDBTypeCommand;
import io.github.erde.editor.diagram.model.BaseEntityModel;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;
import io.github.erde.wizard.page.ChangeDialectWizardPage;

/**
 * ChangeDialectWizard.
 *
 * @author modified by parapata
 */
public class ChangeDialectWizard extends Wizard implements IMessages {

    private CommandStack commandStack;
    private RootModel root;
    private ChangeDialectWizardPage page;

    public ChangeDialectWizard(CommandStack commandStack, RootModel rootModel) {
        setWindowTitle(getResource("wizard.changedb.title"));
        this.commandStack = commandStack;
        this.root = rootModel;
    }

    @Override
    public void addPages() {
        page = new ChangeDialectWizardPage(root.getDialectName());
        addPage(page);
    }

    @Override
    public boolean performFinish() {
        String dialectName = page.getDialectName();
        if (!dialectName.equals(root.getDialectName())) {
            IDialect dialect = DialectProvider.getDialect(dialectName);
            for (BaseEntityModel entity : root.getChildren()) {
                if (entity instanceof TableModel) {
                    TableModel table = (TableModel) entity;
                    for (ColumnModel column : table.getColumns()) {
                        IColumnType type = getColumnType(dialect, table, column);
                        column.setColumnType(type);
                    }
                    table.setColumns(table.getColumns());
                }
            }
            root.setDialectName(dialectName);
            commandStack.execute(new ChangeDBTypeCommand());
        }
        return true;
    }

    private IColumnType getColumnType(IDialect dialect, TableModel table, ColumnModel column) {
        IColumnType type = dialect.getColumnType(column.getColumnType().getType());
        if (type == null) {
            type = dialect.getDefaultColumnType();
        }
        return type;
    }
}
