
package io.github.erde.editor.persistent.diagram;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import io.github.erde.editor.persistent.adapter.BooleanAdapter;
import io.github.erde.editor.persistent.adapter.IntegerAdapter;

/**
 * <p>
 * anonymous complex typeのJavaクラス。
 * <p>
 * 次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{}physicalName"/&gt;
 *         &lt;element ref="{}logicalName"/&gt;
 *         &lt;element ref="{}domainId"/&gt;
 *         &lt;element ref="{}type"/&gt;
 *         &lt;element ref="{}columnSize"/&gt;
 *         &lt;element ref="{}decimal"/&gt;
 *         &lt;element ref="{}unsigned"/&gt;
 *         &lt;element ref="{}description"/&gt;
 *         &lt;element ref="{}autoIncrement"/&gt;
 *         &lt;element ref="{}notNull"/&gt;
 *         &lt;element ref="{}primaryKey"/&gt;
 *         &lt;element ref="{}uniqueKey"/&gt;
 *         &lt;element ref="{}defaultValue"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "physicalName",
        "logicalName",
        "domainId",
        "type",
        "columnSize",
        "decimal",
        "unsigned",
        "description",
        "autoIncrement",
        "notNull",
        "primaryKey",
        "uniqueKey",
        "defaultValue"
})
@XmlRootElement(name = "column")
public class ColumnXmlModel
        implements Serializable {

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String physicalName;
    @XmlElement(required = true)
    protected String logicalName;
    @XmlElement(required = true)
    protected String domainId;
    @XmlElement(required = true)
    protected String type;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    @XmlSchemaType(name = "integer")
    protected Integer columnSize;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    @XmlSchemaType(name = "integer")
    protected Integer decimal;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean unsigned;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean autoIncrement;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean notNull;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean primaryKey;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean uniqueKey;
    @XmlElement(required = true)
    protected String defaultValue;

    /**
     * physicalNameプロパティの値を取得します。
     * 
     * @return
     *         possible object is
     *         {@link String }
     */
    public String getPhysicalName() {
        return physicalName;
    }

    /**
     * physicalNameプロパティの値を設定します。
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setPhysicalName(String value) {
        this.physicalName = value;
    }

    /**
     * logicalNameプロパティの値を取得します。
     * 
     * @return
     *         possible object is
     *         {@link String }
     */
    public String getLogicalName() {
        return logicalName;
    }

    /**
     * logicalNameプロパティの値を設定します。
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setLogicalName(String value) {
        this.logicalName = value;
    }

    /**
     * domainIdプロパティの値を取得します。
     * 
     * @return
     *         possible object is
     *         {@link String }
     */
    public String getDomainId() {
        return domainId;
    }

    /**
     * domainIdプロパティの値を設定します。
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setDomainId(String value) {
        this.domainId = value;
    }

    /**
     * typeプロパティの値を取得します。
     * 
     * @return
     *         possible object is
     *         {@link String }
     */
    public String getType() {
        return type;
    }

    /**
     * typeプロパティの値を設定します。
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * columnSizeプロパティの値を取得します。
     * 
     * @return
     *         possible object is
     *         {@link String }
     */
    public Integer getColumnSize() {
        return columnSize;
    }

    /**
     * columnSizeプロパティの値を設定します。
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setColumnSize(Integer value) {
        this.columnSize = value;
    }

    /**
     * decimalプロパティの値を取得します。
     * 
     * @return
     *         possible object is
     *         {@link String }
     */
    public Integer getDecimal() {
        return decimal;
    }

    /**
     * decimalプロパティの値を設定します。
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setDecimal(Integer value) {
        this.decimal = value;
    }

    /**
     * unsignedプロパティの値を取得します。
     * 
     * @return
     *         possible object is
     *         {@link String }
     */
    public Boolean isUnsigned() {
        return unsigned;
    }

    /**
     * unsignedプロパティの値を設定します。
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setUnsigned(Boolean value) {
        this.unsigned = value;
    }

    /**
     * descriptionプロパティの値を取得します。
     * 
     * @return
     *         possible object is
     *         {@link String }
     */
    public String getDescription() {
        return description;
    }

    /**
     * descriptionプロパティの値を設定します。
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * autoIncrementプロパティの値を取得します。
     * 
     * @return
     *         possible object is
     *         {@link String }
     */
    public Boolean isAutoIncrement() {
        return autoIncrement;
    }

    /**
     * autoIncrementプロパティの値を設定します。
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setAutoIncrement(Boolean value) {
        this.autoIncrement = value;
    }

    /**
     * notNullプロパティの値を取得します。
     * 
     * @return
     *         possible object is
     *         {@link String }
     */
    public Boolean isNotNull() {
        return notNull;
    }

    /**
     * notNullプロパティの値を設定します。
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setNotNull(Boolean value) {
        this.notNull = value;
    }

    /**
     * primaryKeyプロパティの値を取得します。
     * 
     * @return
     *         possible object is
     *         {@link String }
     */
    public Boolean isPrimaryKey() {
        return primaryKey;
    }

    /**
     * primaryKeyプロパティの値を設定します。
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setPrimaryKey(Boolean value) {
        this.primaryKey = value;
    }

    /**
     * uniqueKeyプロパティの値を取得します。
     * 
     * @return
     *         possible object is
     *         {@link String }
     */
    public Boolean isUniqueKey() {
        return uniqueKey;
    }

    /**
     * uniqueKeyプロパティの値を設定します。
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setUniqueKey(Boolean value) {
        this.uniqueKey = value;
    }

    /**
     * defaultValueプロパティの値を取得します。
     * 
     * @return
     *         possible object is
     *         {@link String }
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * defaultValueプロパティの値を設定します。
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setDefaultValue(String value) {
        this.defaultValue = value;
    }

}
