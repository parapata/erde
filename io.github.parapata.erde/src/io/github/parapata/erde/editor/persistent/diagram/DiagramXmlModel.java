
package io.github.parapata.erde.editor.persistent.diagram;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

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
 *         <group ref="{}tables"/>
 *         <group ref="{}notes"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "tables",
        "notes"
})
@XmlRootElement(name = "diagram")
public class DiagramXmlModel
        implements Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement(name = "table")
    protected List<TableXmlModel> tables;
    @XmlElement(name = "note")
    protected List<NoteXmlModel> notes;

    /**
     * Gets the value of the tables property.
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tables property.
     * </p>
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     *
     * <pre>
     * getTables().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TableXmlModel }
     * </p>
     *
     * @return
     *         The value of the tables property.
     */
    public List<TableXmlModel> getTables() {
        if (tables == null) {
            tables = new ArrayList<>();
        }
        return this.tables;
    }

    /**
     * Gets the value of the notes property.
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the notes property.
     * </p>
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     *
     * <pre>
     * getNotes().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NoteXmlModel }
     * </p>
     *
     * @return
     *         The value of the notes property.
     */
    public List<NoteXmlModel> getNotes() {
        if (notes == null) {
            notes = new ArrayList<>();
        }
        return this.notes;
    }

}
