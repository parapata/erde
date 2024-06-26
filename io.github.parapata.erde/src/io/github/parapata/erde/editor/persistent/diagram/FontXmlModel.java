
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
 *       <attribute name="fontName" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       <attribute name="fontSize" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "font")
public class FontXmlModel
        implements Serializable {

    private static final long serialVersionUID = 1L;
    @XmlAttribute(name = "fontName", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String fontName;
    @XmlAttribute(name = "fontSize", required = true)
    protected int fontSize;

    /**
     * fontNameプロパティの値を取得します。
     *
     * @return
     *         possible object is
     *         {@link String }
     */
    public String getFontName() {
        return fontName;
    }

    /**
     * fontNameプロパティの値を設定します。
     *
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setFontName(String value) {
        this.fontName = value;
    }

    /**
     * fontSizeプロパティの値を取得します。
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * fontSizeプロパティの値を設定します。
     */
    public void setFontSize(int value) {
        this.fontSize = value;
    }

}
