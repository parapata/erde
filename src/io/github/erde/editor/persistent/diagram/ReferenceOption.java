
package io.github.erde.editor.persistent.diagram;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * referenceOptionのJavaクラス。
 * <p>
 * 次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="referenceOption"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="NO_ACTION"/&gt;
 *     &lt;enumeration value="RESTRICT"/&gt;
 *     &lt;enumeration value="CASCADE"/&gt;
 *     &lt;enumeration value="SET_NULL"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "referenceOption")
@XmlEnum
public enum ReferenceOption {

    NO_ACTION, RESTRICT, CASCADE, SET_NULL;

    public String value() {
        return name();
    }

    public static ReferenceOption fromValue(String v) {
        return valueOf(v);
    }

}
