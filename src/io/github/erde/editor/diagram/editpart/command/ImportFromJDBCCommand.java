package io.github.erde.editor.diagram.editpart.command;

import static io.github.erde.Resource.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;

import io.github.erde.Activator;
import io.github.erde.core.exception.SystemException;
import io.github.erde.core.util.JDBCConnection;
import io.github.erde.core.util.swt.UIUtils;
import io.github.erde.dialect.IDialect;
import io.github.erde.dialect.loader.ISchemaLoader;
import io.github.erde.editor.diagram.model.RootModel;

/**
 * ImportFromJDBCCommand.
 *
 * @author modified by parapata
 */
public class ImportFromJDBCCommand extends Command {

    private RootModel root;
    private JDBCConnection jdbcConn;
    private List<String> tableNames;

    public ImportFromJDBCCommand(RootModel root, JDBCConnection jdbcConn, List<String> tableNames) {
        this.root = root;
        this.jdbcConn = jdbcConn;
        this.tableNames = tableNames;
    }

    @Override
    public void execute() {
        if (tableNames.isEmpty()) {
            return;
        }

        ProgressMonitorDialog dialog = new ProgressMonitorDialog(Display.getCurrent().getActiveShell());
        try {
            IRunnableWithProgress task = monitor -> {
                IDialect dialect = root.getDialectProvider().getDialect();
                try {
                    ISchemaLoader loader = dialect.getSchemaLoader(jdbcConn);
                    loader.loadSchema(tableNames, root, monitor);
                } catch (Exception e) {
                    throw new SystemException(e);
                }
            };

            // arg1: 別スレッドとして(3)の処理を実行するかどうか
            // arg2: 処理の取り消しを有効とするかどうか
            // arg3: 実行したい処理をカプセル化したオブジェクト
            dialog.run(false, true, task);
        } catch (InvocationTargetException e) {
            UIUtils.openAlertDialog(ERROR_DB_IMPORT);
            Activator.logException(e);
        } catch (InterruptedException e) {
            UIUtils.openInfoDialog(INFO_CANCEL);
            Activator.logException(e);
        }
    }

    @Override
    public boolean canUndo() {
        return false;
    }
}
