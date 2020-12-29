
package io.github.erde.editor.persistent.sqltype;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>productNameのJavaクラス。
 * 
 * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 * <p>
 * <pre>
 * &lt;simpleType name="productName"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="HSQLDB"/&gt;
 *     &lt;enumeration value="DB2"/&gt;
 *     &lt;enumeration value="PostgreSQL"/&gt;
 *     &lt;enumeration value="MySQL"/&gt;
 *     &lt;enumeration value="H2"/&gt;
 *     &lt;enumeration value="MSSQL"/&gt;
 *     &lt;enumeration value="Oracle"/&gt;
 *     &lt;enumeration value="Other"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "productName")
@XmlEnum
public enum ProductName {

    HSQLDB("HSQLDB"),
    @XmlEnumValue("DB2")
    DB_2("DB2"),
    @XmlEnumValue("PostgreSQL")
    POSTGRE_SQL("PostgreSQL"),
    @XmlEnumValue("MySQL")
    MY_SQL("MySQL"),
    @XmlEnumValue("H2")
    H_2("H2"),
    MSSQL("MSSQL"),
    @XmlEnumValue("Oracle")
    ORACLE("Oracle"),
    @XmlEnumValue("Other")
    OTHER("Other");
    private final String value;

    ProductName(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProductName fromValue(String v) {
        for (ProductName c: ProductName.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
