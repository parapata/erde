package io.github.parapata.erde.dialect.loader;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.ResultSet;
import java.util.Properties;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class JdbcMetaDataTest {

    private static Logger logger = LoggerFactory.getLogger(JdbcMetaDataTest.class);

    private static Driver driver;
    private static Properties prop;

    static void init(String resource) throws Exception {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream is = loader.getResourceAsStream(resource)) {
            prop = new Properties();
            prop.load(is);
            URL jarURL = new File(prop.getProperty("jarfile")).toURI().toURL();
            URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[] { jarURL });
            @SuppressWarnings("unchecked")
            Class<Driver> driverClass = (Class<Driver>) urlClassLoader.loadClass(prop.getProperty("driver"));
            driver = driverClass.getDeclaredConstructor().newInstance();

            logger.info("jarfile :{}", prop.getProperty("jarfile"));
            logger.info("driver  :{}", prop.getProperty("driver"));
            logger.info("url     :{}", prop.getProperty("url"));
            logger.info("catalog :{}", prop.getProperty("catalog"));
            logger.info("schem   :{}", prop.getProperty("schem"));
            logger.info("user    :{}", prop.getProperty("user"));
            logger.info("password:{}", prop.getProperty("password"));

        } catch (Exception e) {
            logger.error("", e);
            throw e;
        }
    }

    @Disabled
    @Test
    void test1() throws Exception {
        try (Connection con = driver.connect(prop.getProperty("url"), prop)) {

            // データベースメタデータの取得
            DatabaseMetaData dbmd = con.getMetaData();

            logger.info("----- DatabaseMetaData -----");
            logger.info("Database product name :{}", dbmd.getDatabaseProductName());
            logger.info("Database product version:{}", dbmd.getDatabaseProductVersion());
            logger.info("Database major version :{}", dbmd.getDatabaseMajorVersion());
            logger.info("Database minor version :{}", dbmd.getDatabaseMinorVersion());
            logger.info("Driver name :{}", dbmd.getDriverName());
            logger.info("Driver version :{}", dbmd.getDriverVersion());
            logger.info("Driver major version :{}", dbmd.getDriverMajorVersion());
            logger.info("Driver minor version :{}", dbmd.getDriverMinorVersion());
        } catch (Exception e) {
            logger.error("", e);
            throw e;
        }
    }

    @Disabled
    @Test
    void test2() throws Exception {

        try (Connection con = driver.connect(prop.getProperty("url"), prop)) {

            // データベースメタデータの取得
            DatabaseMetaData dbmd = con.getMetaData();
            logger.info("----- DatabaseMetaData -----");

            String catalog = prop.getProperty("catalog");
            String schem = prop.getProperty("schem");

            try (ResultSet rs = dbmd.getColumns(catalog, schem, "%", "%")) {
                while (rs.next()) {
                    logger.info("----- Table:{}, Column:{} -----",
                            rs.getString("TABLE_NAME"),
                            rs.getString("COLUMN_NAME"));
                    logger.info("TABLE_CAT :{}", rs.getString("TABLE_CAT"));
                    logger.info("TABLE_SCHEM :{}", rs.getString("TABLE_SCHEM"));
                    logger.info("TABLE_NAME :{}", rs.getString("TABLE_NAME"));
                    logger.info("COLUMN_NAME :{}", rs.getString("COLUMN_NAME"));
                    logger.info("DATA_TYPE :{}", rs.getString("DATA_TYPE"));
                    logger.info("TYPE_NAME :{}", rs.getString("TYPE_NAME"));
                    logger.info("COLUMN_SIZE :{}", rs.getString("COLUMN_SIZE"));
                    logger.info("BUFFER_LENGTH :{}", rs.getString("BUFFER_LENGTH"));
                    logger.info("DECIMAL_DIGITS :{}", rs.getString("DECIMAL_DIGITS"));
                    logger.info("NUM_PREC_RADIX :{}", rs.getString("NUM_PREC_RADIX"));
                    logger.info("NULLABLE :{}", rs.getString("NULLABLE"));
                    logger.info("CHAR_OCTET_LENGTH :{}", rs.getString("CHAR_OCTET_LENGTH"));
                    logger.info("ORDINAL_POSITION :{}", rs.getString("ORDINAL_POSITION"));
                    logger.info("REMARKS :{}", rs.getString("REMARKS"));
                    logger.info("SQL_DATA_TYPE :{}", rs.getString("SQL_DATA_TYPE"));
                    logger.info("SQL_DATETIME_SUB :{}", rs.getString("SQL_DATETIME_SUB"));
                    logger.info("COLUMN_DEF :{}", rs.getString("COLUMN_DEF"));
                    logger.info("ORDINAL_POSITION :{}", rs.getString("ORDINAL_POSITION"));
                    logger.info("IS_AUTOINCREMENT :{}", rs.getString("IS_AUTOINCREMENT"));
                    logger.info("IS_GENERATEDCOLUMN:{}", rs.getString("IS_GENERATEDCOLUMN"));
                    logger.info("IS_NULLABLE :{}", rs.getString("IS_NULLABLE"));
                }
            }
        } catch (Exception e) {
            logger.error("", e);
            throw e;
        }
    }
}
