package io.github.erde.editor.diagram.editpart.command;

import static io.github.erde.Resource.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.ui.PlatformUI;

import io.github.erde.ERDPlugin;
import io.github.erde.core.util.JDBCConnection;
import io.github.erde.core.util.swt.UIUtils;
import io.github.erde.dialect.DialectProvider;
import io.github.erde.dialect.IDialect;
import io.github.erde.dialect.loader.ISchemaLoader;
import io.github.erde.dialect.loader.SchemaLoaderTask;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;

/**
 * ImportFromJDBCCommand.
 *
 * @author modified by parapata
 */
public class ImportFromJDBCCommand extends Command implements ISchemaLoader {

    private RootModel root;
    private JDBCConnection jdbcConn;
    private List<String> importTableNames;

    public ImportFromJDBCCommand(RootModel root, JDBCConnection jdbcConn, List<String> importTableNames) {
        this.root = root;
        this.jdbcConn = jdbcConn;
        this.importTableNames = importTableNames;
    }

    @Override
    public void execute() {
        if (tableNames.isEmpty()) {
            return;
        }

        // ProgressMonitorDialog dialog = new ProgressMonitorDialog(Display.getCurrent().getActiveShell());
        ProgressMonitorDialog dialog = new ProgressMonitorDialog(
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
        try {
            DialectProvider provider = DialectProvider.valueOf(jdbcConn.getDialectProvider());
            // IDialect dialect = provider.getDialect();
            root.setDialectProvider(provider);

            // IRunnableWithProgress task = monitor -> {
            // try {
            // ISchemaLoader loader = dialect.getSchemaLoader(jdbcConn);
            // loader.loadSchema(tableNames, root, monitor);
            // } catch (Exception e) {
            // throw new SystemException(e);
            // }
            // };

            SchemaLoaderTask task = new SchemaLoaderTask(this);

            // arg1: 別スレッドとして(3)の処理を実行するかどうか
            // arg2: 処理の取り消しを有効とするかどうか
            // arg3: 実行したい処理をカプセル化したオブジェクト
            dialog.run(true, true, task);

            for (TableModel table : task.getImportTableModels()) {
                addTableModel(table);
            }

        } catch (InvocationTargetException e) {
            UIUtils.openAlertDialog(ERROR_DB_IMPORT);
            ERDPlugin.logException(e);
        } catch (InterruptedException e) {
            UIUtils.openInfoDialog(INFO_PROCESS_ABORTED);
            ERDPlugin.logException(e);
        }
    }

    @Override
    public boolean canUndo() {
        return false;
    }

    @Override
    public void addTableModel(TableModel tableModel) {
        root.addChild(tableModel);
    }

    @Override
    public List<String> getImportTableNames() {
        return importTableNames;
    }

    @Override
    public IDialect getDialect() {
        DialectProvider provider = DialectProvider.valueOf(jdbcConn.getDialectProvider());
        return provider.getDialect();
    }

    @Override
    public JDBCConnection getJDBCConnection() {
        return jdbcConn;
    }
}
