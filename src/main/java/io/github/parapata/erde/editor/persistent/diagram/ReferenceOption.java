
package io.github.parapata.erde.editor.persistent.diagram;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

/**
 * <p>
 * referenceOptionのJavaクラス。
 * <p>
 * 次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 *
 * <pre>{@code
 * <simpleType name="referenceOption">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="NO_ACTION"/>
 *     <enumeration value="RESTRICT"/>
 *     <enumeration value="CASCADE"/>
 *     <enumeration value="SET_NULL"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
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
