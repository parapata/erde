package io.github.erde;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

/**
 * IMessages.
 *
 * @author modified by parapata
 */
public interface IMessages {

    static final ResourceBundle resource = ResourceBundle.getBundle("io.github.erde.messages");

    default String getResource(String key) {
        try {
            return resource.getString(key);
        } catch (MissingResourceException e) {
            return key;
        }
    }

    default String getResource(String key, String defaultValue) {
        try {
            String result = resource.getString(key);
            return StringUtils.isEmpty(result) ? defaultValue : result;
        } catch (MissingResourceException e) {
            return key;
        }
    }

    default String createMessage(String key, String... args) {
        String message = resource.getString(key);
        for (int i = 0; i < args.length; i++) {
            message = message.replaceAll(String.format("\\{%d\\}", i), args[i]);
        }
        return message;
    }
}
