package io.github.erde.core;

import io.github.erde.core.exception.SystemException;

/**
 * LineSeparator enum.
 *
 * @author parapata
 * @since 1.0.8
 */
public enum LineSeparatorCode {

    /** CR+LF(\r\n). */
    CRLF("\r\n"),

    /** LF(\n). */
    FL("\n"),

    /** CR(\r). */
    CR("\r");

    private String lineSeparator;

    LineSeparatorCode(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }

    public String getValue() {
        return lineSeparator;
    }

    public static LineSeparatorCode getDefault() {
        LineSeparatorCode result = findByValue(System.lineSeparator());
        if (result == null) {
            throw new SystemException();
        } else {
            return result;
        }
    }

    public static LineSeparatorCode findByValue(String value) {
        for (LineSeparatorCode code : values()) {
            if (code.getValue().equals(value)) {
                return code;
            }
        }
        throw null;
    }

    public static LineSeparatorCode findByName(String name) {
        for (LineSeparatorCode code : values()) {
            if (code.name().equals(name)) {
                return code;
            }
        }
        throw null;
    }
}
