
package io.github.erde.editor.persistent.sqltype;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the io.github.erde.editor.persistent.sqltype package.
 * <p>
 * An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups. Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _JavaClass_QNAME = new QName("", "javaClass");
    private final static QName _NeedArgs_QNAME = new QName("", "needArgs");
    private final static QName _FullTextIndexable_QNAME = new QName("", "fullTextIndexable");
    private final static QName _Value_QNAME = new QName("", "value");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package:
     * io.github.erde.editor.persistent.sqltype
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SqlTypesXmlModel }
     */
    public SqlTypesXmlModel createSqlTypesXmlModel() {
        return new SqlTypesXmlModel();
    }

    /**
     * Create an instance of {@link ProductXmlModel }
     */
    public ProductXmlModel createProductXmlModel() {
        return new ProductXmlModel();
    }

    /**
     * Create an instance of {@link SqlTypeXmlModel }
     */
    public SqlTypeXmlModel createSqlTypeXmlModel() {
        return new SqlTypeXmlModel();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "javaClass")
    public JAXBElement<String> createJavaClass(String value) {
        return new JAXBElement<String>(_JavaClass_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * 
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "needArgs")
    public JAXBElement<Boolean> createNeedArgs(Boolean value) {
        return new JAXBElement<Boolean>(_NeedArgs_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * 
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "fullTextIndexable")
    public JAXBElement<Boolean> createFullTextIndexable(Boolean value) {
        return new JAXBElement<Boolean>(_FullTextIndexable_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *            Java instance representing xml element's value.
     * @return
     *         the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "value")
    public JAXBElement<String> createValue(String value) {
        return new JAXBElement<String>(_Value_QNAME, String.class, null, value);
    }

}
