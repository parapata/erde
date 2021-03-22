/**
 * Oracle Schema Loader
 */
package io.github.erde.dialect.loader;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.erde.core.util.JDBCConnection;
import io.github.erde.core.util.StringUtils;
import io.github.erde.dialect.IDialect;
import io.github.erde.dialect.type.IColumnType;
import io.github.erde.dialect.type.IndexType;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.IndexModel;
import io.github.erde.editor.diagram.model.TableModel;

/**
 * OracleSchemaLoader.
 *
 * @author modified by parapata
 */
public class OracleSchemaLoader extends DefaultSchemaLoader {

    private Logger logger = LoggerFactory.getLogger(OracleSchemaLoader.class);

    public OracleSchemaLoader(IDialect dialect, JDBCConnection jdbcConn) {
        super(dialect, jdbcConn);
    }

    @Override
    public TableModel getTableInfo(Connection conn, String tableName) throws SQLException {

        String catalog = super.getCatalog();
        String schema = super.getSchema();

        TableModel table = new TableModel();
        table.setPhysicalName(tableName);

        if (isAutoConvert()) {
            table.setLogicalName(table.getPhysicalName());
        } else {
            table.setLogicalName(getTableComment(conn, tableName));
        }

        List<ColumnModel> list = new ArrayList<>();

        DatabaseMetaData meta = conn.getMetaData();
        IDialect dialect = getDialect();
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(dialect.getColumnMetadataSQL(getTableName(tableName, schema)));
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
                if (isAutoConvert()) {
                    column.setLogicalName(column.getPhysicalName());
                } else {
                    column.setLogicalName(getColumnComment(conn, tableName, columns.getString("COLUMN_NAME")));
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

        return table;
    }

    @Override
    protected List<IndexModel> loadIndexModels(Connection conn, String tableName, List<ColumnModel> columns)
            throws SQLException {

        List<IndexModel> result = getIndexInfo(conn, tableName);
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
    private List<IndexModel> getIndexInfo(Connection conn, String tableName) throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append("SELECT NULL                                     AS table_cat				");
        query.append("	    ,i.owner                                  AS table_schem            ");
        query.append("	    ,i.table_name                             AS table_name             ");
        query.append("	    ,decode(i.uniqueness ,'UNIQUE' ,0 ,1)     AS non_unique             ");
        query.append("	    ,NULL                                     AS index_qualifier        ");
        query.append("	    ,i.index_name                             AS index_name             ");
        query.append("	    ,1                                        AS type                   ");
        query.append("	    ,c.column_position                        AS ordinal_position       ");
        query.append("	    ,c.column_name                            AS column_name            ");
        query.append("	    ,NULL                                     AS asc_or_desc            ");
        query.append("	    ,i.distinct_keys                          AS cardinality            ");
        query.append("	    ,i.leaf_blocks                            AS pages                  ");
        query.append("	    ,NULL                                     AS filter_condition       ");
        query.append("  FROM all_indexes     i                                                  ");
        query.append("	    ,all_ind_columns c                                                  ");
        query.append(" WHERE i.table_name    = ?                                                ");
        if (StringUtils.isNotEmpty(getSchema())) {
            query.append("   AND i.owner         = ?                                                ");
        } else {
            query.append("   AND i.owner         = USER                                             ");
        }
        query.append("   AND i.index_name    = c.index_name                                     ");
        query.append("   AND i.table_owner   = c.table_owner                                    ");
        query.append("   AND i.table_name    = c.table_name                                     ");
        query.append("   AND i.owner         = c.index_owner                                    ");
        query.append(" ORDER BY non_unique ,type ,index_name ,ordinal_position                  ");

        List<IndexModel> result = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
            pstmt.setString(1, tableName.toUpperCase());
            if (StringUtils.isNotEmpty(getSchema())) {
                pstmt.setString(2, getSchema().toUpperCase());
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

    /**
     * get Table's Name
     *
     * @param tabName
     * @param schema
     * @return
     */
    private String getTableName(String tabName, String schema) {
        if (StringUtils.isNotEmpty(schema)) {
            return schema + "." + tabName;
        } else {
            return tabName;
        }
    }

    /**
     * get Table's Comments
     */
    protected String getTableComment(Connection conn, String tableName)
            throws SQLException {

        String comment = tableName;

        StringBuilder query = new StringBuilder();
        query.append("SELECT COMMENTS FROM ALL_TAB_COMMENTS WHERE TABLE_NAME = ? ");
        if (StringUtils.isNotEmpty(getSchema())) {
            query.append("AND OWNER = ?");
        } else {
            query.append("AND OWNER = USER");
        }

        try (PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
            pstmt.setString(1, tableName.toUpperCase());
            if (StringUtils.isNotEmpty(getSchema())) {
                pstmt.setString(2, getSchema().toUpperCase());
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    comment = rs.getString(1);
                }
            }
            return StringUtils.isEmpty(comment) ? tableName : comment;
        }
    }

    /**
     * get Column's Comments
     */
    protected String getColumnComment(Connection conn, String tableName, String columnName) throws SQLException {
        String comment = columnName; // default

        StringBuilder query = new StringBuilder();
        query.append("SELECT COMMENTS FROM ALL_COL_COMMENTS WHERE TABLE_NAME = ? AND COLUMN_NAME = ? ");
        if (StringUtils.isNotEmpty(getSchema())) {
            query.append("AND OWNER = ?");
        } else {
            query.append("AND OWNER = USER");
        }

        try (PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
            pstmt.setString(1, tableName.toUpperCase());
            pstmt.setString(2, columnName.toUpperCase());
            if (StringUtils.isNotEmpty(getSchema())) {
                pstmt.setString(3, getSchema());
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
