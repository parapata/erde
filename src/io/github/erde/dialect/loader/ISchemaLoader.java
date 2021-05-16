package io.github.erde.dialect.loader;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.erde.core.util.JDBCConnection;
import io.github.erde.core.util.NameConverter;
import io.github.erde.core.util.StringUtils;
import io.github.erde.dialect.IDialect;
import io.github.erde.dialect.type.IColumnType;
import io.github.erde.dialect.type.IndexType;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.IndexModel;
import io.github.erde.editor.diagram.model.RelationshipMappingModel;
import io.github.erde.editor.diagram.model.RelationshipModel;
import io.github.erde.editor.diagram.model.TableModel;

/**
 * ISchemaLoader.
 *
 * @author modified by parapata
 */
public interface ISchemaLoader {

    Logger logger = LoggerFactory.getLogger(ISchemaLoader.class);

    String METAKEY_PKTABLE_NAME = "PKTABLE_NAME";
    String METAKEY_PKCOLUMN_NAME = "PKCOLUMN_NAME";
    String METAKEY_FKTABLE_NAME = "FKTABLE_NAME";
    String METAKEY_FKCOLUMN_NAME = "FKCOLUMN_NAME";
    String METAKEY_FK_NAME = "FK_NAME";
    String KEY_RELATIONSHIP = "RELATION_MAPPING";

    default TableModel createTableModel(Connection conn, String tableName, int i) throws SQLException {

        TableModel table = new TableModel();
        table.setPhysicalName(tableName);

        JDBCConnection jdbcConn = getJDBCConnection();

        if (jdbcConn.isAutoConvert()) {
            table.setLogicalName(NameConverter.physical2logical(table.getPhysicalName()));
        } else {
            // TODO Camelize?
            table.setLogicalName(table.getPhysicalName());
        }

        List<ColumnModel> list = new ArrayList<>();
        IDialect dialect = getDialect();
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(dialect.getColumnMetadataSQL(tableName));) {

            DatabaseMetaData meta = conn.getMetaData();
            String catalog = jdbcConn.getCatalog();
            String schema = jdbcConn.getSchema();

            try (ResultSet columns = meta.getColumns(catalog, schema, tableName, "%")) {
                while (columns.next()) {

                    logger.info("Table column infomation : {}", tableName);
                    logger.info("TABLE_CAT         :{}", columns.getString("TABLE_CAT"));
                    logger.info("TABLE_SCHEM       :{}", columns.getString("TABLE_SCHEM"));
                    logger.info("TABLE_NAME        :{}", columns.getString("TABLE_NAME"));
                    logger.info("COLUMN_NAME       :{}", columns.getString("COLUMN_NAME"));
                    logger.info("DATA_TYPE         :{}", columns.getString("DATA_TYPE"));
                    logger.info("TYPE_NAME         :{}", columns.getString("TYPE_NAME"));
                    logger.info("COLUMN_SIZE       :{}", columns.getString("COLUMN_SIZE"));
                    logger.info("BUFFER_LENGTH     :{}", columns.getString("BUFFER_LENGTH"));
                    logger.info("DECIMAL_DIGITS    :{}", columns.getString("DECIMAL_DIGITS"));
                    logger.info("NUM_PREC_RADIX    :{}", columns.getString("NUM_PREC_RADIX"));
                    logger.info("NULLABLE          :{}", columns.getString("NULLABLE"));
                    logger.info("CHAR_OCTET_LENGTH :{}", columns.getString("CHAR_OCTET_LENGTH"));
                    logger.info("ORDINAL_POSITION  :{}", columns.getString("ORDINAL_POSITION"));
                    logger.info("REMARKS           :{}", columns.getString("REMARKS"));
                    logger.info("SQL_DATA_TYPE     :{}", columns.getString("SQL_DATA_TYPE"));
                    logger.info("SQL_DATETIME_SUB  :{}", columns.getString("SQL_DATETIME_SUB"));
                    logger.info("COLUMN_DEF        :{}", columns.getString("COLUMN_DEF"));
                    logger.info("ORDINAL_POSITION  :{}", columns.getString("ORDINAL_POSITION"));
                    logger.info("IS_AUTOINCREMENT  :{}", columns.getString("IS_AUTOINCREMENT"));
                    logger.info("IS_GENERATEDCOLUMN:{}", columns.getString("IS_GENERATEDCOLUMN"));
                    logger.info("IS_NULLABLE       :{}", columns.getString("IS_NULLABLE"));

                    IColumnType type = dialect.getColumnType(columns.getString("TYPE_NAME"));
                    if (type == null) {
                        type = dialect.getColumnType(columns.getInt("DATA_TYPE"));
                        if (type == null) {
                            type = dialect.getDefaultColumnType();
                        }
                    }

                    ColumnModel column = new ColumnModel();
                    String columnName = columns.getString("COLUMN_NAME");
                    column.setPhysicalName(columnName);

                    // get enum data
                    if (StringUtils.equalsIgnoreCase("ENUM", type.getPhysicalName())) {
                        String query = dialect.getEnumMetadataSQL();
                        if (StringUtils.isNotEmpty(query)) {
                            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                                pstmt.setString(1, tableName);
                                pstmt.setString(2, columnName);
                                try (ResultSet enumRs = pstmt.executeQuery()) {
                                    if (enumRs != null && enumRs.next()) {
                                        for (String str : StringUtils.split(enumRs.getString(1), ",")) {
                                            column.getEnumNames().add(str.substring(1, str.length() - 1));
                                        }
                                    }
                                }
                            }
                        } else {
                            logger.warn("Importing enum data is not supported");
                        }
                    }

                    if (jdbcConn.isAutoConvert()) {
                        column.setLogicalName(NameConverter.physical2logical(columnName));
                    } else {
                        column.setLogicalName(columnName);
                    }
                    column.setColumnType(type);
                    column.setColumnSize(columns.getInt("COLUMN_SIZE"));
                    column.setNotNull(columns.getString("IS_NULLABLE").equals("NO"));
                    column.setAutoIncrement(StringUtils.toBoolean(columns.getString("IS_AUTOINCREMENT")));

                    ResultSetMetaData rm = rs.getMetaData();
                    int rmIndex = getResultSetMetaDataIndex(rm, columnName);
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

        logger.info("Import table : {}", table.getPhysicalName());

        if (table.getConstraint() == null) {
            table.setConstraint(new Rectangle(10 + i * 50, 10 + i * 50, -1, -1));
        }

        return table;
    }

    default List<IndexModel> loadIndexModels(Connection conn, String tableName, List<ColumnModel> columns)
            throws SQLException {

        List<IndexModel> result = new ArrayList<>();

        DatabaseMetaData meta = conn.getMetaData();

        JDBCConnection jdbcConn = getJDBCConnection();
        String catalog = jdbcConn.getCatalog();
        String schema = jdbcConn.getSchema();

        try (ResultSet rs = meta.getIndexInfo(catalog, schema, tableName, false, true)) {
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
    default int getResultSetMetaDataIndex(ResultSetMetaData rsmd, String columnName) throws SQLException {
        for (int i = 1; i < rsmd.getColumnCount(); i++) {
            if (StringUtils.equalsIgnoreCase(rsmd.getColumnName(i), columnName)) {
                return i;
            }
        }
        return 0;
    }

    default void setForeignKeys(Connection conn, List<TableModel> tables) throws SQLException {

        DatabaseMetaData meta = conn.getMetaData();
        for (TableModel table : tables) {

            Map<String, Map<String, Object>> map = new HashMap<>();

            JDBCConnection jdbcConn = getJDBCConnection();
            try (ResultSet rs = meta.getImportedKeys(jdbcConn.getCatalog(), jdbcConn.getSchema(),
                    table.getPhysicalName())) {
                while (rs.next()) {
                    String pkTable = rs.getString(METAKEY_PKTABLE_NAME);
                    String pkColumn = rs.getString(METAKEY_PKCOLUMN_NAME);
                    String fkTable = rs.getString(METAKEY_FKTABLE_NAME);
                    String fkColumn = rs.getString(METAKEY_FKCOLUMN_NAME);
                    String keyName = rs.getString(METAKEY_FK_NAME);

                    logger.info("{} : {}.{} = {}.{}", keyName, pkTable, pkColumn, fkTable, fkColumn);

                    TableModel sourceTableModel = getTable(pkTable, tables);
                    TableModel targetTableModel = getTable(fkTable, tables);

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

    private TableModel getTable(String tableName, List<TableModel> tableModels) {
        return tableModels.stream()
                .filter(predicate -> StringUtils.equalsIgnoreCase(tableName, predicate.getPhysicalName()))
                .findFirst()
                .get();
    }

    IDialect getDialect();

    JDBCConnection getJDBCConnection();

    List<String> getImportTableNames();

    void addTableModel(TableModel tableModel);
}
