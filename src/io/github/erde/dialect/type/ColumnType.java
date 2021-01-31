package io.github.erde.dialect.type;

import java.sql.Types;

import org.apache.commons.lang3.SerializationUtils;

import io.github.erde.IMessages;
import io.github.erde.dialect.DialectProvider;
import io.github.erde.editor.diagram.model.IModel;

/**
 * ColumnType.
 *
 * @author modified by parapata
 */
public class ColumnType implements IColumnType, IModel, IMessages {

    private static final long serialVersionUID = 1L;

    public static ColumnType newInstance(DialectProvider dialectProvider, String physicaNameKey, String logicalName,
            boolean sizeSupported, int type) {
        ColumnType columnType = new ColumnType(dialectProvider);
        columnType.physicalName = physicaNameKey;
        columnType.logicalName = resource.getString(logicalName);
        columnType.sizeSupported = sizeSupported;
        columnType.type = type;
        return columnType;
    }

    private DialectProvider dialectProvider;
    private String physicalName;
    private String logicalName;
    private boolean sizeSupported;
    private int type;

    public ColumnType(DialectProvider dialectProvider) {
        this.dialectProvider = dialectProvider;
    }

    @Override
    public DialectProvider getDialectProvider() {
        return dialectProvider;
    }

    @Override
    public String getPhysicalName() {
        return physicalName;
    }

    protected void setPhysicalName(String physicalName) {
        this.physicalName = physicalName;
    }

    @Override
    public String getLogicalName() {
        return logicalName;
    }

    protected void setLogicalName(String logicalName) {
        this.logicalName = logicalName;
    }

    @Override
    public boolean isSizeSupported() {
        return sizeSupported;
    }

    protected void setSizeSupported(boolean sizeSupported) {
        this.sizeSupported = sizeSupported;
    }

    @Override
    public int getType() {
        return type;
    }

    protected void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean isDomain() {
        return false;
    }

    @Override
    public boolean isDecimalSupported() {
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

    @Override
    public boolean isUnsignedSupported() {
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

    @Override
    public boolean isEnum() {
        return type == Types.OTHER && "ENUM".equals(physicalName);
    }

    @Override
    protected ColumnType clone() throws CloneNotSupportedException {
        return SerializationUtils.clone(this);
    }

    @Override
    public String toString() {
        return String.format("%s - %s", getPhysicalName(), getLogicalName());
    }
}
