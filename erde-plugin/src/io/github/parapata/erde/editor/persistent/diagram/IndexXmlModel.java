
package io.github.parapata.erde.editor.persistent.diagram;

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
 *         <element ref="{}columnName" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *       <attribute name="indexName" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       <attribute name="indexType" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "columnNames"
})
@XmlRootElement(name = "index")
public class IndexXmlModel
    implements Serializable
{

    private static final long serialVersionUID = 1L;
    @XmlElement(name = "columnName")
    protected List<String> columnNames;
    @XmlAttribute(name = "indexName", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String indexName;
    @XmlAttribute(name = "indexType", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String indexType;

    /**
     * Gets the value of the columnNames property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the columnNames property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getColumnNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * </p>
     * 
     * 
     * @return
     *     The value of the columnNames property.
     */
    public List<String> getColumnNames() {
        if (columnNames == null) {
            columnNames = new ArrayList<>();
        }
        return this.columnNames;
    }

    /**
     * indexNameプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndexName() {
        return indexName;
    }

    /**
     * indexNameプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndexName(String value) {
        this.indexName = value;
    }

    /**
     * indexTypeプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndexType() {
        return indexType;
    }

    /**
     * indexTypeプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndexType(String value) {
        this.indexType = value;
    }

}
