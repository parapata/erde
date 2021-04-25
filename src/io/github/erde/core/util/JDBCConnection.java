package io.github.erde.core.util;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * JDBCConnection.
 *
 * @author modified by parapata
 */
public class JDBCConnection {

    private String uri;
    private String user;
    private String password;
    private String catalog;
    private String schema;
    private Driver driver = null;
    private boolean enableView = false;
    private String productName;
    private boolean autoConvert = false;

    final public String POSTGRESQL = "PostgreSQL";
    final public String MYSQL = "MySQL";
    final public String HSQLDB = "HSQL Database Engine";

    public JDBCConnection(Class<?> driverClass) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        driver = (Driver) driverClass.getDeclaredConstructor().newInstance();
    }

    public void setURI(String uri) {
        if (StringUtils.isNotEmpty(uri)) {
            this.uri = uri;
        }
    }

    public String getURI() {
        return this.uri;
    }

    public void setCatalog(String catalog) {
        if (StringUtils.isNotEmpty(catalog)) {
            this.catalog = catalog;
        }
    }

    public String getCatalog() {
        return this.catalog;
    }

    public void setPassword(String password) {
        if (StringUtils.isNotEmpty(password)) {
            this.password = password;
        }
    }

    public String getPassword() {
        return this.password;
    }

    public void setSchema(String schema) {
        if (StringUtils.isNotEmpty(schema)) {
            this.schema = schema;
        }
    }

    public String getSchema() {
        return this.schema;
    }

    public void setUser(String user) {
        if (StringUtils.isNotEmpty(user)) {
            this.user = user;
        }
    }

    public String getUser() {
        return this.user;
    }

    public void setEnableView(boolean flag) {
        enableView = flag;
    }

    public boolean isEnableView() {
        return this.enableView;
    }

    public boolean isAutoConvert() {
        return autoConvert;
    }

    public void setAutoConvert(boolean autoConvert) {
        this.autoConvert = autoConvert;
    }

    /**
     * Connect to the database and return the connection.
     *
     * @return the JDBC connection
     * @throws SQLException
     *             Connect error
     */
    public Connection connect() throws SQLException {
        Properties prop = new Properties();
        prop.setProperty("user", user);
        prop.setProperty("password", password);
        return driver.connect(uri, prop);
    }

    public String getDriverName() {
        return driver.getClass().getName();
    }

    public List<String> getTableNames() throws SQLException {

        List<String> list = new ArrayList<>();
        try (Connection conn = connect();) {
            DatabaseMetaData meta = conn.getMetaData();
            productName = meta.getDatabaseProductName();

            if (isMSSQL()) {
                if (StringUtils.isEmpty(catalog)) {
                    catalog = "%";
                }
            }

            try (ResultSet tables = meta.getTables(catalog, schema, "%",
                    isOracle() ? new String[] { "TABLE", "VIEW", "SYNONYM" } : null);) {
                while (tables.next()) {
                    String tableType = tables.getString("TABLE_TYPE");
                    if ("TABLE".equals(tableType)
                            || ("VIEW".equals(tableType) && enableView)
                            || (isOracle() && "SYNONYM".equals(tableType))) {
                        list.add(tables.getString("table_name"));
                    }
                }
            }

            if ("org.hsqldb.jdbcDriver".equals(getDriverName())
                    && uri.indexOf("jdbc:hsqldb:hsql://") != 0) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate("SHUTDOWN;");
                }
            }
        }
        return list;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public boolean isPostgreSQL() {
        return POSTGRESQL.equals(productName);
    }

    public boolean isMySQL() {
        return MYSQL.equals(productName);
    }

    public boolean isHSQLDB() {
        return HSQLDB.equals(productName);
    }

    public boolean isMSSQL() {
        if (productName.toLowerCase().indexOf("microsoft") != -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isOracle() {
        if (productName.toLowerCase().indexOf("oracl") != -1) {
            return true;
        } else {
            return false;
        }
    }
}
