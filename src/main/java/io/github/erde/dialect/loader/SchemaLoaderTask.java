package io.github.erde.dialect.loader;

import static io.github.erde.Resource.*;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.erde.editor.diagram.model.TableModel;

/**
 * SchemaLoaderTask.
 *
 * @author parapata
 * @since 1.0.14
 */
public class SchemaLoaderTask implements IRunnableWithProgress {

    private Logger logger = LoggerFactory.getLogger(SchemaLoaderTask.class);

    private ISchemaLoader schemaLoader;

    private List<TableModel> tableModels;

    public SchemaLoaderTask(ISchemaLoader schemaLoader) {
        this.schemaLoader = schemaLoader;
    }

    @Override
    public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
        tableModels = new ArrayList<>();
        try (Connection conn = schemaLoader.getJDBCConnection().connect()) {
            List<String> tableNames = schemaLoader.getImportTableNames();
            monitor.beginTask(INFO_PROCESSING_NOW.getValue(), tableNames.size());

            AtomicInteger i = new AtomicInteger(0);
            for (String tableName : tableNames) {
                if (monitor.isCanceled()) {
                    throw new InterruptedException();
                }

                tableModels.add(schemaLoader.createTableModel(conn, tableName, i.get()));
                monitor.setTaskName(tableName);
                monitor.worked(1);
                i.incrementAndGet();
            }

            schemaLoader.setForeignKeys(conn, tableModels);
            monitor.worked(tableNames.size());

        } catch (SQLException e) {
            throw new InvocationTargetException(e);
        } finally {
            monitor.done();
        }
    }

    public List<TableModel> getImportTableModels() {
        return tableModels;
    }
}
