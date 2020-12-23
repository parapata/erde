
package io.github.erde.editor.persistent.diagram;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *       &lt;sequence&gt;
 *         &lt;element ref="{}jarFile"/&gt;
 *         &lt;element ref="{}jdbcDriver"/&gt;
 *         &lt;element ref="{}jdbcUrl"/&gt;
 *         &lt;element ref="{}jdbcCatalog"/&gt;
 *         &lt;element ref="{}jdbcSchema"/&gt;
 *         &lt;element ref="{}jdbcUser"/&gt;
 *         &lt;element ref="{}jdbcPassword"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "jarFile",
        "jdbcDriver",
        "jdbcUrl",
        "jdbcCatalog",
        "jdbcSchema",
        "jdbcUser",
        "jdbcPassword"
})
@XmlRootElement(name = "dbSettings")
public class DbSettingsXmlModel
        implements Serializable {

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String jarFile;
    @XmlElement(required = true)
    protected String jdbcDriver;
    @XmlElement(required = true)
    protected String jdbcUrl;
    @XmlElement(required = true)
    protected String jdbcCatalog;
    @XmlElement(required = true)
    protected String jdbcSchema;
    @XmlElement(required = true)
    protected String jdbcUser;
    @XmlElement(required = true)
    protected String jdbcPassword;

    /**
     * jarFileプロパティの値を取得します。
     * 
     * @return
     *         possible object is
     *         {@link String }
     */
    public String getJarFile() {
        return jarFile;
    }

    /**
     * jarFileプロパティの値を設定します。
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setJarFile(String value) {
        this.jarFile = value;
    }

    /**
     * jdbcDriverプロパティの値を取得します。
     * 
     * @return
     *         possible object is
     *         {@link String }
     */
    public String getJdbcDriver() {
        return jdbcDriver;
    }

    /**
     * jdbcDriverプロパティの値を設定します。
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setJdbcDriver(String value) {
        this.jdbcDriver = value;
    }

    /**
     * jdbcUrlプロパティの値を取得します。
     * 
     * @return
     *         possible object is
     *         {@link String }
     */
    public String getJdbcUrl() {
        return jdbcUrl;
    }

    /**
     * jdbcUrlプロパティの値を設定します。
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setJdbcUrl(String value) {
        this.jdbcUrl = value;
    }

    /**
     * jdbcCatalogプロパティの値を取得します。
     * 
     * @return
     *         possible object is
     *         {@link String }
     */
    public String getJdbcCatalog() {
        return jdbcCatalog;
    }

    /**
     * jdbcCatalogプロパティの値を設定します。
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setJdbcCatalog(String value) {
        this.jdbcCatalog = value;
    }

    /**
     * jdbcSchemaプロパティの値を取得します。
     * 
     * @return
     *         possible object is
     *         {@link String }
     */
    public String getJdbcSchema() {
        return jdbcSchema;
    }

    /**
     * jdbcSchemaプロパティの値を設定します。
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setJdbcSchema(String value) {
        this.jdbcSchema = value;
    }

    /**
     * jdbcUserプロパティの値を取得します。
     * 
     * @return
     *         possible object is
     *         {@link String }
     */
    public String getJdbcUser() {
        return jdbcUser;
    }

    /**
     * jdbcUserプロパティの値を設定します。
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setJdbcUser(String value) {
        this.jdbcUser = value;
    }

    /**
     * jdbcPasswordプロパティの値を取得します。
     * 
     * @return
     *         possible object is
     *         {@link String }
     */
    public String getJdbcPassword() {
        return jdbcPassword;
    }

    /**
     * jdbcPasswordプロパティの値を設定します。
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     */
    public void setJdbcPassword(String value) {
        this.jdbcPassword = value;
    }

}
