package io.github.erde.dialect.loader;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.ResultSet;
import java.util.Properties;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class JdbcMetaData4MySQLTest {

    private Logger logger = LoggerFactory.getLogger(JdbcMetaData4MySQLTest.class);

    private static Driver driver;
    private static Properties prop = new Properties();

    private static String JAR_FILE = "D:/pleiades/lib/mysql-connector-java-8.0.22.jar";
    private static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String JDBC_URL = "jdbc:mysql://172.26.133.74:3306/demo";
    private static String JDBC_CATALOG = "demo";
    private static String JDBC_SCHEM = "demo";
    private static String JDBC_USER = "demo";
    private static String JDBC_PASSWD = "demo";

    @BeforeAll
    static void beforeAll() throws Exception {
        URL jarURL = new File(JAR_FILE).toURI().toURL();
        URLClassLoader loader = URLClassLoader.newInstance(new URL[] { jarURL });
        @SuppressWarnings("unchecked")
        Class<Driver> driverClass = (Class<Driver>) loader.loadClass(JDBC_DRIVER);
        driver = (Driver) driverClass.getDeclaredConstructor().newInstance();
        prop.setProperty("catalog", JDBC_CATALOG);
        prop.setProperty("schem", JDBC_SCHEM);
        prop.setProperty("user", JDBC_USER);
        prop.setProperty("password", JDBC_PASSWD);
    }

    @AfterAll
    static void afterAll() {
    }

    @BeforeEach
    void beforeEach() {
    }

    @AfterEach
    void afterEach() {
    }

    @Test
    void test1() throws Exception {

        try (Connection con = driver.connect(JDBC_URL, prop)) {

            // データベースメタデータの取得
            DatabaseMetaData dbmd = con.getMetaData();

            logger.info("----- DatabaseMetaData -----");
            logger.info("Database product name   :{}", dbmd.getDatabaseProductName());
            logger.info("Database product version:{}", dbmd.getDatabaseProductVersion());
            logger.info("Database major version  :{}", dbmd.getDatabaseMajorVersion());
            logger.info("Database minor version  :{}", dbmd.getDatabaseMinorVersion());
            logger.info("Driver name             :{}", dbmd.getDriverName());
            logger.info("Driver version          :{}", dbmd.getDriverVersion());
            logger.info("Driver major version    :{}", dbmd.getDriverMajorVersion());
            logger.info("Driver minor version    :{}", dbmd.getDriverMinorVersion());
        }
    }

    @Test
    void test2() throws Exception {

        try (Connection con = driver.connect(JDBC_URL, prop)) {

            // データベースメタデータの取得
            DatabaseMetaData dbmd = con.getMetaData();
            logger.info("----- DatabaseMetaData -----");

            try (ResultSet rs = dbmd.getColumns(JDBC_CATALOG, JDBC_SCHEM, "%", "%")) {
                while (rs.next()) {
                    logger.info("----- Table:{}, Column:{} -----",
                            rs.getString("TABLE_NAME"),
                            rs.getString("COLUMN_NAME"));
                    logger.info("TABLE_CAT         :{}", rs.getString("TABLE_CAT"));
                    logger.info("TABLE_SCHEM       :{}", rs.getString("TABLE_SCHEM"));
                    logger.info("TABLE_NAME        :{}", rs.getString("TABLE_NAME"));
                    logger.info("COLUMN_NAME       :{}", rs.getString("COLUMN_NAME"));
                    logger.info("DATA_TYPE         :{}", rs.getString("DATA_TYPE"));
                    logger.info("TYPE_NAME         :{}", rs.getString("TYPE_NAME"));
                    logger.info("COLUMN_SIZE       :{}", rs.getString("COLUMN_SIZE"));
                    logger.info("BUFFER_LENGTH     :{}", rs.getString("BUFFER_LENGTH"));
                    logger.info("DECIMAL_DIGITS    :{}", rs.getString("DECIMAL_DIGITS"));
                    logger.info("NUM_PREC_RADIX    :{}", rs.getString("NUM_PREC_RADIX"));
                    logger.info("NULLABLE          :{}", rs.getString("NULLABLE"));
                    logger.info("CHAR_OCTET_LENGTH :{}", rs.getString("CHAR_OCTET_LENGTH"));
                    logger.info("ORDINAL_POSITION  :{}", rs.getString("ORDINAL_POSITION"));
                    logger.info("REMARKS           :{}", rs.getString("REMARKS"));
                    logger.info("SQL_DATA_TYPE     :{}", rs.getString("SQL_DATA_TYPE"));
                    logger.info("SQL_DATETIME_SUB  :{}", rs.getString("SQL_DATETIME_SUB"));
                    logger.info("COLUMN_DEF        :{}", rs.getString("COLUMN_DEF"));
                    logger.info("ORDINAL_POSITION  :{}", rs.getString("ORDINAL_POSITION"));
                    logger.info("IS_AUTOINCREMENT  :{}", rs.getString("IS_AUTOINCREMENT"));
                    logger.info("IS_GENERATEDCOLUMN:{}", rs.getString("IS_GENERATEDCOLUMN"));
                    logger.info("IS_NULLABLE       :{}", rs.getString("IS_NULLABLE"));
                }
            }
        }
    }
}
