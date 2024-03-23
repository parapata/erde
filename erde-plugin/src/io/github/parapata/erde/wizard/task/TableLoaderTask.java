package io.github.parapata.erde.wizard.task;

import static io.github.parapata.erde.Resource.*;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import io.github.parapata.erde.core.util.JDBCConnection;
import io.github.parapata.erde.core.util.StringUtils;
import io.github.parapata.erde.dialect.DialectProvider;
import io.github.parapata.erde.dialect.IDialect;

/**
 * TableLoaderTask.
 *
 * @author parapata
 * @since 1.0.14
 */
public class TableLoaderTask implements IRunnableWithProgress {

    private JDBCConnection jdbcConn;
    private List<String> tableNames;

    public TableLoaderTask(JDBCConnection jdbcConn) {
        this.jdbcConn = jdbcConn;
    }

    @Override
    public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
        monitor.beginTask(INFO_PROCESSING_NOW.getValue(), IProgressMonitor.UNKNOWN);
        tableNames = new ArrayList<>();
        try (Connection conn = jdbcConn.connect()) {

            IDialect dialect = DialectProvider.getDialect(jdbcConn.getDialectProvider());
            DialectProvider dialectProvider = dialect.getDialectProvider();

            String catalog;
            if (DialectProvider.MySQL.equals(dialectProvider)
                    && StringUtils.isEmpty(jdbcConn.getCatalog())) {
                catalog = "%";
            } else {
                catalog = jdbcConn.getCatalog();
            }

            String schema = jdbcConn.getSchema();

            String[] types;
            if (DialectProvider.Oracle.equals(dialectProvider)) {
                types = new String[] { "TABLE", "VIEW", "SYNONYM" };
            } else {
                types = null;
            }

            DatabaseMetaData meta = conn.getMetaData();
            // String productName = meta.getDatabaseProductName();

            try (ResultSet rs = meta.getTables(catalog, schema, "%", types)) {
                while (rs.next()) {
                    if (monitor.isCanceled()) {
                        throw new InterruptedException();
                    }

                    String tableType = rs.getString("TABLE_TYPE");
                    if (StringUtils.equalsIgnoreCase("TABLE", tableType)
                            || StringUtils.equalsIgnoreCase("VIEW", tableType) && jdbcConn.isEnableView()
                            || DialectProvider.Oracle.equals(dialectProvider)
                                    && StringUtils.equalsIgnoreCase("SYNONYM", tableType)) {

                        String tableName = rs.getString("table_name");
                        tableNames.add(tableName);
                        monitor.setTaskName(tableName);
                        monitor.worked(1);
                    }
                }
            }

            if ("org.hsqldb.jdbcDriver".equals(jdbcConn.getDriverName())
                    && jdbcConn.getURI().indexOf("jdbc:hsqldb:hsql://") != 0) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate("SHUTDOWN;");
                }
            }
        } catch (SQLException e) {
            throw new InvocationTargetException(e);
        } finally {
            monitor.done();
        }
    }

    public List<String> getTableNames() {
        return tableNames == null ? new ArrayList<>() : tableNames;
    }
}
