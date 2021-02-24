package io.github.erde.core.util;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

/**
 * Properties.
 *
 * @author parapata
 * @since 1.0.8
 */
public class PropertiesEx extends Properties {

    /** . */
    private static final long serialVersionUID = 1L;
    private boolean recurse;

    @Override
    public synchronized Set<Map.Entry<Object, Object>> entrySet() {
        if (this.recurse) {
            return super.entrySet();
        } else {
            this.recurse = true;
            TreeMap<Object, Object> treeMap = new TreeMap<>(this);
            Set<Map.Entry<Object, Object>> entries = treeMap.entrySet();
            this.recurse = false;
            return entries;
        }
    }
}
