package io.github.erde.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import io.github.erde.core.exception.SystemException;

/**
 * PropertyLoader.
 *
 * @author modified by parapata
 */
public class PropertyLoader {

    private Properties properties;

    public PropertyLoader(String fineName) {
        properties = getInstance(fineName);
    }

    public PropertyLoader(String fineName, String charSet) {
        properties = getInstance(fineName, charSet);
    }

    public String getString(String key) {
        return properties.getProperty(key);
    }

    public String getString(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        String value = properties.getProperty(key);
        return StringUtils.isEmpty(value) ? false : Boolean.parseBoolean(value);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        return StringUtils.isEmpty(value) ? defaultValue : Boolean.parseBoolean(value);
    }

    public int getInt(String key) {
        return Integer.parseInt(getString(key));
    }

    public int getInt(String key, int defaultValue) {
        String value = properties.getProperty(key);
        return StringUtils.isEmpty(value) ? defaultValue : Integer.parseInt(value);
    }

    public long getLong(String key) {
        return Long.parseLong(getString(key));
    }

    public long getLong(String key, long defaultValue) {
        String value = properties.getProperty(key);
        return StringUtils.isEmpty(value) ? defaultValue : Long.parseLong(value);
    }

    public static Properties getInstance(String fineName) {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fineName)) {
            Properties properties = new Properties();
            properties.load(is);
            return properties;
        } catch (IOException e) {
            throw new SystemException(e);
        }
    }

    public static Properties getInstance(String fineName, String charSet) {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fineName);
                InputStreamReader isr = new InputStreamReader(is, charSet)) {
            Properties properties = new Properties();
            properties.load(isr);
            return properties;
        } catch (IOException e) {
            throw new SystemException(e);
        }
    }

}
