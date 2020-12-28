
package io.github.erde.editor.persistent.diagram;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

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
 *         &lt;element ref="{}location"/&gt;
 *         &lt;element ref="{}text"/&gt;
 *         &lt;element ref="{}noteConnection" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "location",
        "text",
        "noteConnections"
})
@XmlRootElement(name = "note")
public class NoteXmlModel
        implements Serializable {

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected LocationXmlModel location;
    @XmlElement(required = true)
    protected String text;
    @XmlElement(name = "noteConnection")
    protected List<NoteConnectionXmlModel> noteConnections;
    @XmlAttribute(name = "id", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String id;

    /**
     * locationプロパティの値を取得します。
     * 
     * @return
     *         possible object is
     *         {@link LocationXmlModel }
     */
    public LocationXmlModel getLocation() {
        return location;
    }

    /**
     * locationプロパティの値を設定します。
     * 
     * @param value
     *            allowed object is
     *            {@link LocationXmlModel }
     */
    public void setLocation(LocationXmlModel value) {
        this.location = value;
    }

    /**
     * textプロパティの値を取得します。
     * 
     * @return
     *         possible object is
     *         {@link String }
     */
    public String getText() {
        return text;
    }

    /**
     * textプロパティの値を設定します。
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setText(String value) {
        this.text = value;
    }

    /**
     * Gets the value of the noteConnections property.
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the noteConnections property.
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getNoteConnections().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NoteConnectionXmlModel }
     */
    public List<NoteConnectionXmlModel> getNoteConnections() {
        if (noteConnections == null) {
            noteConnections = new ArrayList<NoteConnectionXmlModel>();
        }
        return this.noteConnections;
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
