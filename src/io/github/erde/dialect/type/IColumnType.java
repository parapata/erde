package io.github.erde.dialect.type;

import io.github.erde.dialect.DialectProvider;

/**
 * IColumnType.
 *
 * @author modified by parapata
 */
public interface IColumnType {
    DialectProvider getDialectProvider();

    String getPhysicalName();

    String getLogicalName();

    boolean isSizeSupported();

    boolean isDecimalSupported();

    boolean isUnsignedSupported();

    boolean isEnum();

    int getType();

    boolean isDomain();
}
