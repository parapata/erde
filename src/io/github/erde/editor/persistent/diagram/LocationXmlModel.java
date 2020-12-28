
package io.github.erde.editor.persistent.diagram;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex typeのJavaクラス。
 * 
 * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="x" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="y" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="width" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="height" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "location")
public class LocationXmlModel
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "x", required = true)
    protected int x;
    @XmlAttribute(name = "y", required = true)
    protected int y;
    @XmlAttribute(name = "width", required = true)
    protected int width;
    @XmlAttribute(name = "height", required = true)
    protected int height;

    /**
     * xプロパティの値を取得します。
     * 
     */
    public int getX() {
        return x;
    }

    /**
     * xプロパティの値を設定します。
     * 
     */
    public void setX(int value) {
        this.x = value;
    }

    /**
     * yプロパティの値を取得します。
     * 
     */
    public int getY() {
        return y;
    }

    /**
     * yプロパティの値を設定します。
     * 
     */
    public void setY(int value) {
        this.y = value;
    }

    /**
     * widthプロパティの値を取得します。
     * 
     */
    public int getWidth() {
        return width;
    }

    /**
     * widthプロパティの値を設定します。
     * 
     */
    public void setWidth(int value) {
        this.width = value;
    }

    /**
     * heightプロパティの値を取得します。
     * 
     */
    public int getHeight() {
        return height;
    }

    /**
     * heightプロパティの値を設定します。
     * 
     */
    public void setHeight(int value) {
        this.height = value;
    }

}
