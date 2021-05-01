package io.github.erde.editor.diagram.editpart.command;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;

import io.github.erde.core.util.JDBCConnection;
import io.github.erde.core.util.StringUtils;
import io.github.erde.dialect.IDialect;
import io.github.erde.dialect.type.IColumnType;
import io.github.erde.dialect.type.IndexType;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.IndexModel;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;

/**
 * ImportFromOracleCommand.
 *
 * @author modified by parapata
 */
public class ImportFromOracleCommand extends ImportFromJDBCCommand {

    public ImportFromOracleCommand(RootModel root, JDBCConnection jdbcConn, List<String> importTableNames) {
        super(root, jdbcConn, importTableNames);
    }

    @Override
    public TableModel createTableModel(Connection conn, String tableName, int i) throws SQLException {

        JDBCConnection jdbcConn = getJDBCConnection();
        String catalog = jdbcConn.getCatalog();
        String schema = jdbcConn.getSchema();

        TableModel table = new TableModel();
        table.setPhysicalName(tableName);

        if (jdbcConn.isAutoConvert()) {
            table.setLogicalName(table.getPhysicalName());
        } else {
            table.setLogicalName(getTableComment(conn, tableName));
        }

        List<ColumnModel> list = new ArrayList<>();

        DatabaseMetaData meta = conn.getMetaData();
        IDialect dialect = getDialect();
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(dialect.getColumnMetadataSQL(getTableName(schema, tableName)));
                ResultSet columns = meta.getColumns(catalog, schema, tableName, "%");) {

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
                    column.setLogicalName(column.getPhysicalName());
                } else {
                    String logicalName = getColumnComment(conn, jdbcConn.getSchema(), tableName,
                            columns.getString("COLUMN_NAME"));
                    column.setLogicalName(logicalName);
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

        logger.info("Import table : {}", table.getPhysicalName());

        if (table.getConstraint() == null) {
            table.setConstraint(new Rectangle(10 + i * 50, 10 + i * 50, -1, -1));
        }

        return table;
    }

    @Override
    public List<IndexModel> loadIndexModels(Connection conn, String tableName, List<ColumnModel> columns)
            throws SQLException {

        JDBCConnection jdbcConn = getJDBCConnection();
        List<IndexModel> result = getIndexInfo(conn, jdbcConn.getSchema(), tableName);
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
     * get Index Information
     *
     * @param conn
     * @param schema
     * @param tableName
     * @return
     * @throws SQLException
     */
    private List<IndexModel> getIndexInfo(Connection conn, String schemaName, String tableName) throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append("SELECT NULL                                     AS table_cat           ");
        query.append("    ,i.owner                                    AS table_schem         ");
        query.append("    ,i.table_name                               AS table_name          ");
        query.append("    ,decode(i.uniqueness ,'UNIQUE' ,0 ,1)       AS non_unique          ");
        query.append("    ,NULL                                       AS index_qualifier     ");
        query.append("    ,i.index_name                               AS index_name          ");
        query.append("    ,1                                          AS type                ");
        query.append("    ,c.column_position                          AS ordinal_position    ");
        query.append("    ,c.column_name                              AS column_name         ");
        query.append("    ,NULL                                       AS asc_or_desc         ");
        query.append("    ,i.distinct_keys                            AS cardinality         ");
        query.append("    ,i.leaf_blocks                              AS pages               ");
        query.append("    ,NULL                                       AS filter_condition    ");
        query.append("FROM all_indexes     i                                                 ");
        query.append("    ,all_ind_columns c                                                 ");
        query.append("WHERE i.table_name    = ?                                              ");
        if (StringUtils.isNotEmpty(schemaName)) {
            query.append("    AND i.owner         = ?                                            ");
        } else {
            query.append("    AND i.owner         = USER                                         ");
        }
        query.append("    AND i.index_name    = c.index_name                                 ");
        query.append("    AND i.table_owner   = c.table_owner                                ");
        query.append("    AND i.table_name    = c.table_name                                 ");
        query.append("    AND i.owner         = c.index_owner                                ");
        query.append("ORDER BY non_unique ,type ,index_name ,ordinal_position                ");

        List<IndexModel> result = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
            pstmt.setString(1, tableName.toUpperCase());
            if (StringUtils.isNotEmpty(schemaName)) {
                pstmt.setString(2, schemaName.toUpperCase());
            }

            ResultSet rs = pstmt.executeQuery();
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
            return result;
        }
    }

    private String getTableName(String schemaName, String tableName) {
        if (StringUtils.isNotEmpty(schemaName)) {
            return String.format("%s.%s", schemaName, tableName);
        } else {
            return tableName;
        }
    }

    private String getTableComment(Connection conn, String tableName) throws SQLException {

        String comment = tableName;
        JDBCConnection jdbcConn = getJDBCConnection();

        StringBuilder query = new StringBuilder();
        query.append("SELECT COMMENTS FROM ALL_TAB_COMMENTS WHERE TABLE_NAME = ? ");
        if (StringUtils.isNotEmpty(jdbcConn.getSchema())) {
            query.append("AND OWNER = ?");
        } else {
            query.append("AND OWNER = USER");
        }

        try (PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
            pstmt.setString(1, tableName.toUpperCase());
            if (StringUtils.isNotEmpty(jdbcConn.getSchema())) {
                pstmt.setString(2, jdbcConn.getSchema().toUpperCase());
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    comment = rs.getString(1);
                }
            }
            return StringUtils.isEmpty(comment) ? tableName : comment;
        }
    }

    private String getColumnComment(Connection conn, String schemaName, String tableName, String columnName)
            throws SQLException {
        String comment = columnName; // default

        StringBuilder query = new StringBuilder();
        query.append("SELECT COMMENTS FROM ALL_COL_COMMENTS WHERE TABLE_NAME = ? AND COLUMN_NAME = ? ");
        if (StringUtils.isEmpty(schemaName)) {
            query.append("AND OWNER = USER");
        } else {
            query.append("AND OWNER = ?");
        }

        try (PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
            pstmt.setString(1, tableName.toUpperCase());
            pstmt.setString(2, columnName.toUpperCase());
            if (StringUtils.isNotEmpty(schemaName)) {
                pstmt.setString(3, schemaName);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    comment = rs.getString(1);
                }
                return StringUtils.isEmpty(comment) ? columnName : comment;
            }
        }
    }

}
