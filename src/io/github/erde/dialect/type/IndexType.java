package io.github.erde.dialect.type;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * IndexType.
 *
 * @author modified by parapata
 */
public enum IndexType implements IIndexType, Serializable {

    /** INDEX. */
    INDEX,

    /** UNIQUE. */
    UNIQUE;

    @Override
    public String getName() {
        return this.name();
    }

    public static List<String> getNames() {
        List<String> result = new LinkedList<>();
        for (IndexType item : IndexType.values()) {
            result.add(item.name());
        }
        return result;
    }

    public static IndexType toIndexType(String value) {
        for (IndexType indexType : values()) {
            if (indexType.name().equals(value)) {
                return indexType;
            }
        }
        throw new IllegalArgumentException();
    }

}
