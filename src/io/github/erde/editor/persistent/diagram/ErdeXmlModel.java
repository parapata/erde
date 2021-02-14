
package io.github.erde.editor.persistent.diagram;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import io.github.erde.editor.persistent.adapter.BooleanAdapter;


/**
 * <p>anonymous complex typeのJavaクラス。
 * 
 * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{}color"/&gt;
 *         &lt;element ref="{}font"/&gt;
 *         &lt;element ref="{}dialectName"/&gt;
 *         &lt;element ref="{}schemaName"/&gt;
 *         &lt;element ref="{}lowerCase"/&gt;
 *         &lt;element ref="{}logicalMode"/&gt;
 *         &lt;element ref="{}includeView"/&gt;
 *         &lt;element ref="{}notation"/&gt;
 *         &lt;element ref="{}zoom"/&gt;
 *         &lt;element ref="{}dbSettings"/&gt;
 *         &lt;element ref="{}diagram"/&gt;
 *         &lt;group ref="{}domains"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "color",
    "font",
    "dialectName",
    "schemaName",
    "lowerCase",
    "logicalMode",
    "includeView",
    "notation",
    "zoom",
    "dbSettings",
    "diagram",
    "domains"
})
@XmlRootElement(name = "erde")
public class ErdeXmlModel
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected ColorXmlModel color;
    @XmlElement(required = true)
    protected FontXmlModel font;
    @XmlElement(required = true)
    protected String dialectName;
    @XmlElement(required = true)
    protected String schemaName;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean lowerCase;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean logicalMode;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean includeView;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String notation;
    protected double zoom;
    @XmlElement(required = true)
    protected DbSettingsXmlModel dbSettings;
    @XmlElement(required = true)
    protected DiagramXmlModel diagram;
    @XmlElement(name = "domain")
    protected List<DomainXmlModel> domains;

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
     * dialectNameプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDialectName() {
        return dialectName;
    }

    /**
     * dialectNameプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDialectName(String value) {
        this.dialectName = value;
    }

    /**
     * schemaNameプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchemaName() {
        return schemaName;
    }

    /**
     * schemaNameプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchemaName(String value) {
        this.schemaName = value;
    }

    /**
     * lowerCaseプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean isLowerCase() {
        return lowerCase;
    }

    /**
     * lowerCaseプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLowerCase(Boolean value) {
        this.lowerCase = value;
    }

    /**
     * logicalModeプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean isLogicalMode() {
        return logicalMode;
    }

    /**
     * logicalModeプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogicalMode(Boolean value) {
        this.logicalMode = value;
    }

    /**
     * includeViewプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean isIncludeView() {
        return includeView;
    }

    /**
     * includeViewプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncludeView(Boolean value) {
        this.includeView = value;
    }

    /**
     * notationプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotation() {
        return notation;
    }

    /**
     * notationプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotation(String value) {
        this.notation = value;
    }

    /**
     * zoomプロパティの値を取得します。
     * 
     */
    public double getZoom() {
        return zoom;
    }

    /**
     * zoomプロパティの値を設定します。
     * 
     */
    public void setZoom(double value) {
        this.zoom = value;
    }

    /**
     * dbSettingsプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link DbSettingsXmlModel }
     *     
     */
    public DbSettingsXmlModel getDbSettings() {
        return dbSettings;
    }

    /**
     * dbSettingsプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link DbSettingsXmlModel }
     *     
     */
    public void setDbSettings(DbSettingsXmlModel value) {
        this.dbSettings = value;
    }

    /**
     * diagramプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link DiagramXmlModel }
     *     
     */
    public DiagramXmlModel getDiagram() {
        return diagram;
    }

    /**
     * diagramプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link DiagramXmlModel }
     *     
     */
    public void setDiagram(DiagramXmlModel value) {
        this.diagram = value;
    }

    /**
     * Gets the value of the domains property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the domains property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDomains().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DomainXmlModel }
     * 
     * 
     */
    public List<DomainXmlModel> getDomains() {
        if (domains == null) {
            domains = new ArrayList<DomainXmlModel>();
        }
        return this.domains;
    }

}
