
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
 *         <element ref="{}location"/>
 *         <element ref="{}font"/>
 *         <element ref="{}color"/>
 *         <element ref="{}physicalName"/>
 *         <element ref="{}logicalName"/>
 *         <element ref="{}description"/>
 *         <element ref="{}column" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{}index" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{}foreignKey" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{}tableProperties"/>
 *       </sequence>
 *       <attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "location",
    "font",
    "color",
    "physicalName",
    "logicalName",
    "description",
    "columns",
    "indices",
    "foreignKeies",
    "tableProperties"
})
@XmlRootElement(name = "table")
public class TableXmlModel
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected LocationXmlModel location;
    @XmlElement(required = true)
    protected FontXmlModel font;
    @XmlElement(required = true)
    protected ColorXmlModel color;
    @XmlElement(required = true)
    protected String physicalName;
    @XmlElement(required = true)
    protected String logicalName;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(name = "column")
    protected List<ColumnXmlModel> columns;
    @XmlElement(name = "index")
    protected List<IndexXmlModel> indices;
    @XmlElement(name = "foreignKey")
    protected List<ForeignKeyXmlModel> foreignKeies;
    @XmlElement(required = true)
    protected TablePropertiesXmlModel tableProperties;
    @XmlAttribute(name = "id", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String id;

    /**
     * locationプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link LocationXmlModel }
     *     
     */
    public LocationXmlModel getLocation() {
        return location;
    }

    /**
     * locationプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link LocationXmlModel }
     *     
     */
    public void setLocation(LocationXmlModel value) {
        this.location = value;
    }

    /**
     * fontプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link FontXmlModel }
     *     
     */
    public FontXmlModel getFont() {
        return font;
    }

    /**
     * fontプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link FontXmlModel }
     *     
     */
    public void setFont(FontXmlModel value) {
        this.font = value;
    }

    /**
     * colorプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link ColorXmlModel }
     *     
     */
    public ColorXmlModel getColor() {
        return color;
    }

    /**
     * colorプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link ColorXmlModel }
     *     
     */
    public void setColor(ColorXmlModel value) {
        this.color = value;
    }

    /**
     * physicalNameプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhysicalName() {
        return physicalName;
    }

    /**
     * physicalNameプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhysicalName(String value) {
        this.physicalName = value;
    }

    /**
     * logicalNameプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogicalName() {
        return logicalName;
    }

    /**
     * logicalNameプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogicalName(String value) {
        this.logicalName = value;
    }

    /**
     * descriptionプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * descriptionプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the columns property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the columns property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getColumns().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ColumnXmlModel }
     * 
     * 
     * @return
     *     The value of the columns property.
     */
    public List<ColumnXmlModel> getColumns() {
        if (columns == null) {
            columns = new ArrayList<>();
        }
        return this.columns;
    }

    /**
     * Gets the value of the indices property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the indices property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndices().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IndexXmlModel }
     * 
     * 
     * @return
     *     The value of the indices property.
     */
    public List<IndexXmlModel> getIndices() {
        if (indices == null) {
            indices = new ArrayList<>();
        }
        return this.indices;
    }

    /**
     * Gets the value of the foreignKeies property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the foreignKeies property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getForeignKeies().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ForeignKeyXmlModel }
     * 
     * 
     * @return
     *     The value of the foreignKeies property.
     */
    public List<ForeignKeyXmlModel> getForeignKeies() {
        if (foreignKeies == null) {
            foreignKeies = new ArrayList<>();
        }
        return this.foreignKeies;
    }

    /**
     * tablePropertiesプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link TablePropertiesXmlModel }
     *     
     */
    public TablePropertiesXmlModel getTableProperties() {
        return tableProperties;
    }

    /**
     * tablePropertiesプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link TablePropertiesXmlModel }
     *     
     */
    public void setTableProperties(TablePropertiesXmlModel value) {
        this.tableProperties = value;
    }

    /**
     * idプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * idプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
