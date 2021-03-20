package io.github.erde.core.util;

/**
 * StringUtils.
 *
 * @author parapata
 * @since 1.0.12
 */
public class StringUtils {

    public static final String EMPTY = "";

    private StringUtils() {
        throw new AssertionError();
    }

    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty() ? true : false;
    }

    public static boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty() ? true : false;
    }

    public static boolean isNoneEmpty(String value) {
        return value == null || value.trim().isEmpty() ? true : false;
    }

    public static String defaultString(String value) {
        return isEmpty(value) ? EMPTY : value;
    }

    public static String defaultString(String value, String defaultValue) {
        return isEmpty(value) ? defaultValue : value;
    }

    public static boolean equals(String value1, String value2) {
        if (value1 == null) {
            return value2 == null;
        } else {
            return value1.equals(value2);
        }
    }

    public static boolean equalsIgnoreCase(String value1, String value2) {
        if (value1 == null) {
            return value2 == null;
        } else {
            return value1.equalsIgnoreCase(value2);
        }
    }

    public static String trim(String value) {
        return value == null ? null : value.trim();
    }

    public static String upperCase(String value) {
        return value == null ? null : value.toUpperCase();
    }

    public static String lowerCase(String value) {
        return value == null ? null : value.toLowerCase();
    }

    public static boolean isNumeric(String value) {
        if (value == null) {
            return false;
        } else {
            for (char ch : value.toCharArray()) {
                if (!Character.isDigit(ch)) {
                    return false;
                }
            }
        }
        return true;
    }
}
