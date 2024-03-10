package io.github.parapata.erde.generate.excel;

/**
 * ColumnData.
 *
 * @author modified by parapata
 */
public class ColumnData {

    private String logicalColumnName = "";
    private String physicalColumnName = "";
    private String primaryKey = "";
    private String foreignKey = "";
    private String type = "";
    private String columnSize = "";
    private String decimal = "";
    private String reference = "";
    private String description = "";
    private String nullable = "";
    private String unique = "";
    private String autoIncrement = "";
    private String defaultValue = "";
    private int index;

    public String getLogicalColumnName() {
        return logicalColumnName;
    }

    public void setLogicalColumnName(String logicalColumnName) {
        this.logicalColumnName = logicalColumnName;
    }

    public String getPhysicalColumnName() {
        return physicalColumnName;
    }

    public void setPhysicalColumnName(String physicalColumnName) {
        this.physicalColumnName = physicalColumnName;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(String columnSize) {
        this.columnSize = columnSize;
    }

    public String getDecimal() {
        return decimal;
    }

    public void setDecimal(String decimal) {
        this.decimal = decimal;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    public String getAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(String autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
