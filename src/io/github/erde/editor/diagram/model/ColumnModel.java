package io.github.erde.editor.diagram.model;

import java.util.LinkedHashSet;
import java.util.Set;

import io.github.erde.core.util.SerializationUtils;
import io.github.erde.core.util.StringUtils;
import io.github.erde.dialect.type.ColumnType;
import io.github.erde.dialect.type.IColumnType;

/**
 * ColumnModel.
 *
 * @author modified by parapata
 */
public class ColumnModel implements IModel {

    private static final long serialVersionUID = 1L;

    private String physicalName;
    private String logicalName;
    private IColumnType columnType;
    private Set<String> enumNames = new LinkedHashSet<>();
    private Integer columnSize;
    private Integer decimal;
    private boolean unsigned;
    private boolean primaryKey;
    private boolean uniqueKey;
    private boolean notNull;
    private String description;
    private boolean autoIncrement;
    private String defaultValue;

    public String getLogicalName() {
        return this.logicalName;
    }

    public void setLogicalName(String logicalName) {
        this.logicalName = StringUtils.defaultString(logicalName);
    }

    public String getPhysicalName() {
        return physicalName;
    }

    public void setPhysicalName(String columnName) {
        this.physicalName = StringUtils.defaultString(columnName);
    }

    public IColumnType getColumnType() {
        return columnType;
    }

    public void setColumnType(IColumnType columnType) {
        this.columnType = columnType;
        if (this.columnType != null) {
            if (this.columnType instanceof DomainModel) {
                DomainModel domain = (DomainModel) columnType;
                this.columnSize = domain.getColumnSize();
                this.decimal = domain.getDecimal();
                this.unsigned = domain.isUnsigned();
            }
        }
    }

    public Set<String> getEnumNames() {
        return enumNames;
    }

    public void setEnumNames(Set<String> enumNames) {
        this.enumNames = enumNames;
    }

    public Integer getColumnSize() {
        if (columnType != null) {
            if (columnType instanceof DomainModel) {
                return ((DomainModel) columnType).getColumnSize();
            }
        }
        return columnSize;
    }

    public void setColumnSize(Integer columnSize) {
        if (columnType != null) {
            if (columnType instanceof DomainModel) {
                ((DomainModel) columnType).setColumnSize(columnSize);
            }
        }
        this.columnSize = columnSize;
    }

    public Integer getDecimal() {
        if (columnType != null) {
            if (columnType instanceof DomainModel) {
                return ((DomainModel) columnType).getDecimal();
            }
        }
        return decimal;
    }

    public void setDecimal(Integer decimal) {
        if (columnType != null) {
            if (columnType instanceof DomainModel) {
                ((DomainModel) columnType).setDecimal(decimal);
            }
        }
        this.decimal = decimal;
    }

    public boolean isUnsigned() {
        if (columnType != null) {
            if (columnType instanceof DomainModel) {
                return ((DomainModel) columnType).isUnsigned();
            }
        }
        return unsigned;
    }

    public void setUnsigned(boolean unsigned) {
        if (columnType != null) {
            if (columnType instanceof ColumnType) {
            } else if (columnType instanceof DomainModel) {
                ((DomainModel) columnType).setUnsigned(unsigned);
            }
        }
        this.unsigned = unsigned;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(boolean uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public String getDescription() {
        return StringUtils.defaultString(this.description);
    }

    public void setDescription(String description) {
        this.description = StringUtils.defaultString(description);
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public String getDefaultValue() {
        return StringUtils.defaultString(this.defaultValue);
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = StringUtils.defaultString(defaultValue);
    }

    @Override
    protected ColumnModel clone() throws CloneNotSupportedException {
        return SerializationUtils.clone(this);
    }

    @Override
    public String toString() {
        return getPhysicalName();
    }
}
