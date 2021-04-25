package io.github.erde.editor.diagram.model;

import io.github.erde.core.util.StringUtils;
import io.github.erde.dialect.DialectProvider;
import io.github.erde.dialect.type.ColumnType;
import io.github.erde.dialect.type.IColumnType;

/**
 * DomainModel.
 *
 * @author modified by parapata
 */
public class DomainModel extends ColumnType implements IModel {

    /** . */
    private static final long serialVersionUID = 1L;

    public static DomainModel newInstance(DialectProvider dialectProvider, String id, String domainName,
            IColumnType columnType, Integer columnSize, Integer decimal, boolean unsigned) {
        DomainModel domain = new DomainModel(dialectProvider);
        if (StringUtils.isEmpty(id)) {
            domain.generateId();
        } else {
            domain.setId(id);
        }
        domain.domainName = domainName;

        if (columnType != null) {
            domain.setPhysicalName(columnType.getPhysicalName());
            domain.setLogicalName(columnType.getLogicalName());
            domain.setType(columnType.getType());
            domain.setSizeSupported(columnType.isSizeSupported());
            domain.columnSize = columnSize;
            domain.decimal = decimal;
            domain.unsigned = unsigned;
        }
        return domain;
    }

    private String id;
    private String domainName;
    private Integer columnSize;
    private Integer decimal;
    private boolean unsigned;

    public DomainModel(DialectProvider dialectProvider) {
        super(dialectProvider);
    }

    @Override
    public boolean isDomain() {
        return true;
    }

    public void setColumnType(IColumnType columnType) {
        setPhysicalName(columnType.getPhysicalName());
        setLogicalName(columnType.getLogicalName());
        setType(columnType.getType());
        setSizeSupported(columnType.isSizeSupported());
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String name) {
        this.domainName = name;
    }

    public Integer getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(Integer columnSize) {
        this.columnSize = columnSize;
    }

    public Integer getDecimal() {
        return decimal;
    }

    public void setDecimal(Integer decimal) {
        this.decimal = decimal;
    }

    public boolean isUnsigned() {
        return unsigned;
    }

    public void setUnsigned(boolean unsigned) {
        this.unsigned = unsigned;
    }

    private void generateId() {
        this.id = String.valueOf((System.nanoTime()));
    }

    @Override
    public DomainModel clone() {
        DomainModel newModel = DomainModel.newInstance(getDialectProvider(), id, domainName, this, columnSize, decimal,
                unsigned);
        return newModel;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDomainName());
        sb.append(" - ");
        sb.append(StringUtils.defaultString(getPhysicalName()));
        if (isSizeSupported() && getColumnSize() != null) {
            if (isDecimalSupported() && getDecimal() != null) {
                sb.append(String.format("(%d,%d)", getColumnSize(), getDecimal()));
            } else {
                sb.append(String.format("(%d)", getColumnSize()));
            }
        }
        return sb.toString();
    }
}
