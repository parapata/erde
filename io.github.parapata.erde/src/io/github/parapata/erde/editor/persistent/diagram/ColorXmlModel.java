
package io.github.parapata.erde.editor.persistent.diagram;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
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
 *       <attribute name="r" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       <attribute name="g" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       <attribute name="b" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "color")
public class ColorXmlModel
        implements Serializable {

    private static final long serialVersionUID = 1L;
    @XmlAttribute(name = "r", required = true)
    protected int r;
    @XmlAttribute(name = "g", required = true)
    protected int g;
    @XmlAttribute(name = "b", required = true)
    protected int b;

    /**
     * rプロパティの値を取得します。
     */
    public int getR() {
        return r;
    }

    /**
     * rプロパティの値を設定します。
     */
    public void setR(int value) {
        this.r = value;
    }

    /**
     * gプロパティの値を取得します。
     */
    public int getG() {
        return g;
    }

    /**
     * gプロパティの値を設定します。
     */
    public void setG(int value) {
        this.g = value;
    }

    /**
     * bプロパティの値を取得します。
     */
    public int getB() {
        return b;
    }

    /**
     * bプロパティの値を設定します。
     */
    public void setB(int value) {
        this.b = value;
    }

}
