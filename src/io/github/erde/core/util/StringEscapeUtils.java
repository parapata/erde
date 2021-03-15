package io.github.erde.core.util;

/**
 * StringEscapeUtils.
 *
 * @author modified by parapata
 */
public class StringEscapeUtils {

    public static String escapeHTML(String str) {
        if (str == null) {
            return "";
        }

        str = str.replaceAll("&", "&amp;");
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll("\"", "&quote;");
        str = str.replaceAll("\r\n", "\n");
        str = str.replaceAll("\r", "\n");
        str = str.replaceAll("\n", "<br>");

        return str;
    }

    public static String escapeHTML2(String str) {
        str = escapeHTML(str);
        if (str.length() == 0) {
            return "&nbsp;";
        }
        return str;
    }

    public static boolean isEmpty(String value) {
        return value == null || value.length() == 0;
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }
}
