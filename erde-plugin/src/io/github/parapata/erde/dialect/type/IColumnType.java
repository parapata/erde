package io.github.parapata.erde.dialect.type;

import java.sql.Types;

import io.github.parapata.erde.dialect.DialectProvider;

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

    int getType();

    default boolean isAutoIncrementSupported() {
        if (getType() == Types.TINYINT
                || getType() == Types.SMALLINT
                || getType() == Types.INTEGER
                || getType() == Types.BIGINT) {
            return true;
        } else {
            return false;
        }
    }

    default boolean isDomain() {
        return false;
    }

    default boolean isDecimalSupported() {
        int type = getType();
        if (isSizeSupported() &&
                (type == Types.DECIMAL
                        || type == Types.FLOAT
                        || type == Types.DOUBLE
                        || type == Types.NUMERIC)) {
            return true;
        } else {
            return false;
        }
    }

    default boolean isUnsignedSupported() {
        int type = getType();
        DialectProvider dialectProvider = getDialectProvider();
        if (!DialectProvider.PostgreSQL.equals(dialectProvider)
                && (type == Types.TINYINT
                        || type == Types.SMALLINT
                        || type == Types.INTEGER
                        || type == Types.BIGINT)) {
            return true;
        } else {
            return false;
        }
    }

    default boolean isEnum() {
        // TODO 判定見直し
        return getType() == Types.OTHER && "ENUM".equals(getPhysicalName());
    }

}
