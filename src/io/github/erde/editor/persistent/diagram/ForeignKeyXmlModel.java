
package io.github.erde.editor.persistent.diagram;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex typeのJavaクラス。
 * 
 * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 * 
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{}onUpdateOption"/>
 *         <element ref="{}onDeleteOption"/>
 *         <element ref="{}sourceCardinality"/>
 *         <element ref="{}targetCardinality"/>
 *         <element ref="{}foreignKeyMapping" maxOccurs="unbounded"/>
 *       </sequence>
 *       <attribute name="sourceId" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       <attribute name="foreignKeyName" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "onUpdateOption",
    "onDeleteOption",
    "sourceCardinality",
    "targetCardinality",
    "foreignKeyMappings"
})
@XmlRootElement(name = "foreignKey")
public class ForeignKeyXmlModel
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected ReferenceOption onUpdateOption;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected ReferenceOption onDeleteOption;
    @XmlElement(required = true)
    protected String sourceCardinality;
    @XmlElement(required = true)
    protected String targetCardinality;
    @XmlElement(name = "foreignKeyMapping", required = true)
    protected List<ForeignKeyMappingXmlModel> foreignKeyMappings;
    @XmlAttribute(name = "sourceId", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String sourceId;
    @XmlAttribute(name = "foreignKeyName", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String foreignKeyName;

    /**
     * onUpdateOptionプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link ReferenceOption }
     *     
     */
    public ReferenceOption getOnUpdateOption() {
        return onUpdateOption;
    }

    /**
     * onUpdateOptionプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceOption }
     *     
     */
    public void setOnUpdateOption(ReferenceOption value) {
        this.onUpdateOption = value;
    }

    /**
     * onDeleteOptionプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link ReferenceOption }
     *     
     */
    public ReferenceOption getOnDeleteOption() {
        return onDeleteOption;
    }

    /**
     * onDeleteOptionプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceOption }
     *     
     */
    public void setOnDeleteOption(ReferenceOption value) {
        this.onDeleteOption = value;
    }

    /**
     * sourceCardinalityプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceCardinality() {
        return sourceCardinality;
    }

    /**
     * sourceCardinalityプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceCardinality(String value) {
        this.sourceCardinality = value;
    }

    /**
     * targetCardinalityプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetCardinality() {
        return targetCardinality;
    }

    /**
     * targetCardinalityプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetCardinality(String value) {
        this.targetCardinality = value;
    }

    /**
     * Gets the value of the foreignKeyMappings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the foreignKeyMappings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getForeignKeyMappings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ForeignKeyMappingXmlModel }
     * 
     * 
     * @return
     *     The value of the foreignKeyMappings property.
     */
    public List<ForeignKeyMappingXmlModel> getForeignKeyMappings() {
        if (foreignKeyMappings == null) {
            foreignKeyMappings = new ArrayList<>();
        }
        return this.foreignKeyMappings;
    }

    /**
     * sourceIdプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * sourceIdプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceId(String value) {
        this.sourceId = value;
    }

    /**
     * foreignKeyNameプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForeignKeyName() {
        return foreignKeyName;
    }

    /**
     * foreignKeyNameプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForeignKeyName(String value) {
        this.foreignKeyName = value;
    }

}
