package io.github.erde.generate.html;

import io.github.erde.Resource;

public class HtmlGenUtils {

    public static String getResource(String key) {
        return Resource.toResource(key).getValue();
    }
}
