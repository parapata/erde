package io.github.erde.dialect.type;

/**
 * IColumnType.
 *
 * @author modified by parapata
 */
public interface IColumnType {
    String getPhysicalName();

    String getLogicalName();

    boolean isSizeSupported();

    boolean isDecimalSupported();

    boolean isUnsignedSupported();

    boolean isEnum();

    int getType();

    boolean isDomain();
}
