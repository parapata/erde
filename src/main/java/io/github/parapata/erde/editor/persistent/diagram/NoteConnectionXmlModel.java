
package io.github.parapata.erde.editor.persistent.diagram;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
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
 *       <attribute name="targetId" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "noteConnection")
public class NoteConnectionXmlModel
        implements Serializable {

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "targetId", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String targetId;

    /**
     * targetIdプロパティの値を取得します。
     *
     * @return
     *         possible object is
     *         {@link String }
     */
    public String getTargetId() {
        return targetId;
    }

    /**
     * targetIdプロパティの値を設定します。
     *
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setTargetId(String value) {
        this.targetId = value;
    }

}
