
package io.github.parapata.erde.editor.persistent.diagram;

import java.io.Serializable;
import io.github.parapata.erde.editor.persistent.adapter.BooleanAdapter;
import io.github.parapata.erde.editor.persistent.adapter.IntegerAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * <p>
 * anonymous complex typeのJavaクラス。
 * <p>
 * 次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 *
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{}domainName"/>
 *         <element ref="{}type"/>
 *         <element ref="{}columnSize"/>
 *         <element ref="{}decimal"/>
 *         <element ref="{}unsigned"/>
 *       </sequence>
 *       <attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
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

    private static final long serialVersionUID = 1L;
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
