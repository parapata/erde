package io.github.erde.dialect.loader;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.erde.core.util.JDBCConnection;
import io.github.erde.core.util.NameConverter;
import io.github.erde.dialect.IDialect;
import io.github.erde.dialect.type.IColumnType;
import io.github.erde.dialect.type.IndexType;
import io.github.erde.editor.diagram.model.BaseConnectionModel;
import io.github.erde.editor.diagram.model.BaseEntityModel;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.IndexModel;
import io.github.erde.editor.diagram.model.RelationshipMappingModel;
import io.github.erde.editor.diagram.model.RelationshipModel;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;

/**
 * A default implementation of {@link ISchemaLoader}.
 *
 * @author modified by parapata
 */
public class DefaultSchemaLoader implements ISchemaLoader {

    private Logger logger = LoggerFactory.getLogger(DefaultSchemaLoader.class);

    private static final String METAKEY_PKTABLE_NAME = "PKTABLE_NAME";
    private static final String METAKEY_PKCOLUMN_NAME = "PKCOLUMN_NAME";
    private static final String METAKEY_FKTABLE_NAME = "FKTABLE_NAME";
    private static final String METAKEY_FKCOLUMN_NAME = "FKCOLUMN_NAME";
    private static final String METAKEY_FK_NAME = "FK_NAME";
    private static final String KEY_RELATIONSHIP = "RELATION_MAPPING";

    private IDialect dialect;
    private JDBCConnection jdbcConn;

    public DefaultSchemaLoader(IDialect dialect, JDBCConnection jdbcConn) {
        this.dialect = dialect;
        this.jdbcConn = jdbcConn;
    }

    @Override
    public void loadSchema(List<String> tableNames, RootModel root, IProgressMonitor monitor)
            throws SQLException, InterruptedException {

        try (Connection conn = jdbcConn.connect()) {
            monitor.beginTask("インポート", tableNames.size());
            for (int i = 0; i < tableNames.size(); i++) {
                if (monitor.isCanceled()) {
                    throw new InterruptedException();
                }

                TableModel table = getTableInfo(conn, tableNames.get(i));

                // merge
                for (BaseEntityModel obj : root.getChildren()) {
                    if (monitor.isCanceled()) {
                        throw new InterruptedException();
                    }
                    if (obj instanceof TableModel) {
                        TableModel tableModel = (TableModel) obj;
                        if (tableModel.getPhysicalName().equals(table.getPhysicalName())) {
                            table.setLogicalName(tableModel.getLogicalName());
                            table.setDescription(tableModel.getDescription());
                            table.setConstraint(tableModel.getConstraint());
                            List<ColumnModel> columns = table.getColumns();
                            for (ColumnModel column : columns) {
                                ColumnModel columnModel = tableModel.getColumn(column.getPhysicalName());
                                if (columnModel != null) {
                                    column.setLogicalName(columnModel.getLogicalName());
                                    column.setDescription(columnModel.getDescription());
                                }
                            }
                            root.removeChild(tableModel);
                            break;
                        }
                    }
                }
                if (table.getConstraint() == null) {
                    table.setConstraint(new Rectangle(10 + i * 50, 10 + i * 50, -1, -1));
                }
                root.addChild(table);
                monitor.worked(i);
            }
            setForeignKeys(conn, root);
            monitor.worked(tableNames.size());

        } finally {
            monitor.done();
        }
    }

    protected TableModel getTableInfo(Connection conn, String tableName) throws SQLException {

        TableModel table = new TableModel();
        table.setPhysicalName(tableName);

        if (jdbcConn.isAutoConvert()) {
            table.setLogicalName(NameConverter.physical2logical(table.getPhysicalName()));
        } else {
            // TODO Camelize?
            table.setLogicalName(table.getPhysicalName());
        }

        List<ColumnModel> list = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(dialect.getColumnMetadataSQL(tableName));) {

            DatabaseMetaData meta = conn.getMetaData();
            String catalog = jdbcConn.getCatalog();
            String schema = jdbcConn.getSchema();

            try (ResultSet columns = meta.getColumns(catalog, schema, tableName, "%")) {
                ResultSetMetaData rm = rs.getMetaData();
                while (columns.next()) {
                    IColumnType type = dialect.getColumnType(columns.getString("TYPE_NAME"));
                    if (type == null) {
                        type = dialect.getColumnType(columns.getInt("DATA_TYPE"));
                        if (type == null) {
                            type = dialect.getDefaultColumnType();
                        }
                    }

                    ColumnModel column = new ColumnModel();
                    column.setPhysicalName(columns.getString("COLUMN_NAME"));
                    if (jdbcConn.isAutoConvert()) {
                        column.setLogicalName(NameConverter.physical2logical(column.getPhysicalName()));
                    } else {
                        column.setLogicalName(column.getPhysicalName());
                    }
                    column.setColumnType(type);
                    column.setColumnSize(columns.getInt("COLUMN_SIZE"));
                    column.setNotNull(columns.getString("IS_NULLABLE").equals("NO"));

                    int rmIndex = getResultSetMetaDataIndex(rm, column.getPhysicalName());
                    if (rmIndex > 0) {
                        column.setAutoIncrement(rm.isAutoIncrement(rmIndex));
                    }
                    list.add(column);
                }
            }

            try (ResultSet keys = meta.getPrimaryKeys(catalog, schema, tableName)) {
                while (keys.next()) {
                    String columnName = keys.getString("COLUMN_NAME");
                    for (ColumnModel column : list) {
                        if (column.getPhysicalName().equals(columnName)) {
                            column.setPrimaryKey(true);
                        }
                    }
                }
            }
        }

        table.setColumns(list);
        List<IndexModel> indices = loadIndexModels(conn, tableName, list);
        table.setIndices(indices);

        return table;
    }

    protected List<IndexModel> loadIndexModels(Connection conn, String tableName, List<ColumnModel> columns)
            throws SQLException {

        List<IndexModel> result = new ArrayList<>();

        DatabaseMetaData meta = conn.getMetaData();
        try (ResultSet rs = meta.getIndexInfo(jdbcConn.getCatalog(), jdbcConn.getSchema(), tableName, false, true)) {
            while (rs.next()) {
                String indexName = rs.getString("INDEX_NAME");
                if (indexName != null) {
                    IndexModel indexModel = null;
                    for (IndexModel index : result) {
                        if (index.getIndexName().equals(indexName)) {
                            indexModel = index;
                            break;
                        }
                    }
                    if (indexModel == null) {
                        indexModel = new IndexModel();
                        indexModel.setIndexName(indexName);
                        indexModel.setIndexName(rs.getString("INDEX_NAME"));
                        if (rs.getBoolean("NON_UNIQUE")) {
                            indexModel.setIndexType(IndexType.INDEX);
                        } else {
                            indexModel.setIndexType(IndexType.UNIQUE);
                        }
                        result.add(indexModel);
                    }
                    indexModel.getColumns().add(rs.getString("COLUMN_NAME"));
                }
            }
        }

        List<IndexModel> removeIndexModels = new ArrayList<>();
        for (IndexModel indexModel : result) {
            List<String> pkColumns = new ArrayList<>();
            for (ColumnModel columnModel : columns) {
                if (columnModel.isPrimaryKey()) {
                    pkColumns.add(columnModel.getPhysicalName());
                }
            }
            if (indexModel.getColumns().size() == pkColumns.size()) {
                boolean isNotPk = false;
                for (int i = 0; i < indexModel.getColumns().size(); i++) {
                    if (!indexModel.getColumns().get(i).equals(pkColumns.get(i))) {
                        isNotPk = true;
                        break;
                    }
                }
                if (!isNotPk) {
                    removeIndexModels.add(indexModel);
                }
            }
        }
        result.removeAll(removeIndexModels);
        return result;
    }

    /**
     * Return the index in the <code>ResultSetMetaData</code> for the given column.
     */
    protected int getResultSetMetaDataIndex(ResultSetMetaData rm, String columnName) throws SQLException {
        for (int i = 1; i < rm.getColumnCount(); i++) {
            if (rm.getColumnName(i).equals(columnName)) {
                return i;
            }
        }
        return 0;
    }

    protected void setForeignKeys(Connection conn, RootModel root) throws SQLException {

        for (BaseEntityModel element : root.getChildren()) {
            TableModel table = (TableModel) element;
            for (BaseConnectionModel connModel : table.getModelSourceConnections()
                    .toArray(new BaseConnectionModel[table.getModelSourceConnections().size()])) {
                if (connModel instanceof RelationshipModel) {
                    connModel.detachSource();
                    connModel.detachTarget();
                }
            }
        }

        DatabaseMetaData meta = conn.getMetaData();
        for (BaseEntityModel element : root.getChildren()) {

            TableModel table = (TableModel) element;
            Map<String, Map<String, Object>> map = new HashMap<>();

            try (ResultSet rs = meta.getImportedKeys(jdbcConn.getCatalog(), jdbcConn.getSchema(),
                    table.getPhysicalName())) {
                while (rs.next()) {
                    String pkTable = rs.getString(METAKEY_PKTABLE_NAME);
                    String pkColumn = rs.getString(METAKEY_PKCOLUMN_NAME);
                    String fkTable = rs.getString(METAKEY_FKTABLE_NAME);
                    String fkColumn = rs.getString(METAKEY_FKCOLUMN_NAME);
                    String keyName = rs.getString(METAKEY_FK_NAME);

                    logger.info("{} : {}.{} = {}.{}", keyName, pkTable, pkColumn, fkTable, fkColumn);

                    TableModel sourceTableModel = root.getTable(pkTable);
                    TableModel targetTableModel = root.getTable(fkTable);

                    if (sourceTableModel != null && targetTableModel != null) {

                        RelationshipModel foreignKeyModel = new RelationshipModel();
                        foreignKeyModel.setForeignKeyName(keyName);

                        foreignKeyModel.setSource(sourceTableModel);
                        sourceTableModel.getModelSourceConnections().add(foreignKeyModel);

                        foreignKeyModel.setTarget(targetTableModel);
                        targetTableModel.getModelTargetConnections().add(foreignKeyModel);

                        if (map.get(keyName) == null) {
                            Map<String, Object> entry = new HashMap<>();
                            entry.put(KEY_RELATIONSHIP, new ArrayList<RelationshipMappingModel>());
                            map.put(keyName, entry);
                        }

                        @SuppressWarnings("unchecked")
                        List<RelationshipMappingModel> mappings = (List<RelationshipMappingModel>) map.get(keyName)
                                .get(KEY_RELATIONSHIP);
                        RelationshipMappingModel mapping = new RelationshipMappingModel();

                        logger.info("{}", sourceTableModel.getColumn(fkColumn).getPhysicalName());
                        logger.info("{}", targetTableModel.getColumn(pkColumn).getPhysicalName());

                        mapping.setReferenceKey(sourceTableModel.getColumn(pkColumn));
                        mapping.setForeignKey(targetTableModel.getColumn(fkColumn));
                        mappings.add(mapping);

                        logger.info("★ADD FK - {}:{}.{}={}.{}", keyName,
                                sourceTableModel.getPhysicalName(), fkColumn,
                                targetTableModel.getPhysicalName(), fkColumn);

                        foreignKeyModel.setMappings(mappings);
                        foreignKeyModel.attachSource();
                        foreignKeyModel.attachTarget();
                    }
                }
            }
        }
    }

    protected boolean isAutoConvert() {
        return jdbcConn.isAutoConvert();
    }

    protected String getCatalog() {
        return jdbcConn.getCatalog();
    }

    protected String getSchema() {
        return jdbcConn.getSchema();
    }

    protected IDialect getDialect() {
        return dialect;
    }
}
