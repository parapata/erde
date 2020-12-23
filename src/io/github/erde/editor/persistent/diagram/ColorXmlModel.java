
package io.github.erde.editor.persistent.diagram;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
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
 *       &lt;attribute name="r" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="g" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="b" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "color")
public class ColorXmlModel
        implements Serializable {

    private final static long serialVersionUID = 1L;
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
