
package io.github.parapata.erde.editor.persistent.diagram;

import java.io.Serializable;
import io.github.parapata.erde.editor.persistent.adapter.BooleanAdapter;
import io.github.parapata.erde.editor.persistent.adapter.IntegerAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
 *         <element ref="{}schema"/>
 *         <choice>
 *           <sequence>
 *             <element ref="{}characterSet"/>
 *             <element ref="{}collation"/>
 *             <element ref="{}storageEngine"/>
 *             <element ref="{}primaryKeyLengthOfText"/>
 *           </sequence>
 *           <element ref="{}withoutOIDs"/>
 *         </choice>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "schema",
        "withoutOIDs",
        "characterSet",
        "collation",
        "storageEngine",
        "primaryKeyLengthOfText"
})
@XmlRootElement(name = "tableProperties")
public class TablePropertiesXmlModel
        implements Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String schema;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean withoutOIDs;
    protected String characterSet;
    protected String collation;
    protected String storageEngine;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    @XmlSchemaType(name = "integer")
    protected Integer primaryKeyLengthOfText;

    /**
     * schemaプロパティの値を取得します。
     *
     * @return
     *         possible object is
     *         {@link String }
     */
    public String getSchema() {
        return schema;
    }

    /**
     * schemaプロパティの値を設定します。
     *
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setSchema(String value) {
        this.schema = value;
    }

    /**
     * withoutOIDsプロパティの値を取得します。
     *
     * @return
     *         possible object is
     *         {@link String }
     */
    public Boolean isWithoutOIDs() {
        return withoutOIDs;
    }

    /**
     * withoutOIDsプロパティの値を設定します。
     *
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setWithoutOIDs(Boolean value) {
        this.withoutOIDs = value;
    }

    /**
     * characterSetプロパティの値を取得します。
     *
     * @return
     *         possible object is
     *         {@link String }
     */
    public String getCharacterSet() {
        return characterSet;
    }

    /**
     * characterSetプロパティの値を設定します。
     *
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setCharacterSet(String value) {
        this.characterSet = value;
    }

    /**
     * collationプロパティの値を取得します。
     *
     * @return
     *         possible object is
     *         {@link String }
     */
    public String getCollation() {
        return collation;
    }

    /**
     * collationプロパティの値を設定します。
     *
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setCollation(String value) {
        this.collation = value;
    }

    /**
     * storageEngineプロパティの値を取得します。
     *
     * @return
     *         possible object is
     *         {@link String }
     */
    public String getStorageEngine() {
        return storageEngine;
    }

    /**
     * storageEngineプロパティの値を設定します。
     *
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setStorageEngine(String value) {
        this.storageEngine = value;
    }

    /**
     * primaryKeyLengthOfTextプロパティの値を取得します。
     *
     * @return
     *         possible object is
     *         {@link String }
     */
    public Integer getPrimaryKeyLengthOfText() {
        return primaryKeyLengthOfText;
    }

    /**
     * primaryKeyLengthOfTextプロパティの値を設定します。
     *
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setPrimaryKeyLengthOfText(Integer value) {
        this.primaryKeyLengthOfText = value;
    }

}
