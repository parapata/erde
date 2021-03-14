package io.github.erde.generate.html;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import io.github.erde.Resource;

public class HtmlGenUtils {

    public static String getResource(String key) {
        return Resource.toResource(key).getValue();
    }

    public static String getType(String type, String columnSize, String decimal, String unsigned) {

        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotEmpty(type)) {
            sb.append(type);
            if (StringUtils.isNotEmpty(columnSize)) {
                if (StringUtils.isEmpty(decimal)) {
                    sb.append(String.format("(%s)", columnSize));
                } else {
                    sb.append(String.format("(%s,%s)", columnSize, decimal));
                }
            }
            if (BooleanUtils.toBoolean(unsigned)) {
                sb.append(" UNSIGNED");
            }
        }
        return sb.toString();
    }

    public static boolean toBoolean(String value) {
        return BooleanUtils.toBoolean(value);
    }

    public static String srcIdToTableName() {
        return null;
    }
}
