
package io.github.erde.editor.persistent.diagram;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element ref="{}domainName"/&gt;
 *         &lt;element ref="{}type"/&gt;
 *         &lt;element ref="{}columnSize"/&gt;
 *         &lt;element ref="{}decimal"/&gt;
 *         &lt;element ref="{}unsigned"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "domainName",
        "type",
        "columnSize",
        "decimal",
        "unsigned"
})
@XmlRootElement(name = "domain")
public class DomainXmlModel
        implements Serializable {

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String domainName;
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
    @XmlAttribute(name = "id", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String id;

    /**
     * domainNameプロパティの値を取得します。
     * 
     * @return
     *         possible object is
     *         {@link String }
     */
    public String getDomainName() {
        return domainName;
    }

    /**
     * domainNameプロパティの値を設定します。
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setDomainName(String value) {
        this.domainName = value;
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
     * idプロパティの値を取得します。
     * 
     * @return
     *         possible object is
     *         {@link String }
     */
    public String getId() {
        return id;
    }

    /**
     * idプロパティの値を設定します。
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setId(String value) {
        this.id = value;
    }

}
