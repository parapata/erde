
package io.github.erde.editor.persistent.db;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the io.github.erde.editor.persistent.db package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Template_QNAME = new QName("", "template");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: io.github.erde.editor.persistent.db
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JdbcUrlTemplateXmlModel }
     * 
     */
    public JdbcUrlTemplateXmlModel createJdbcUrlTemplateXmlModel() {
        return new JdbcUrlTemplateXmlModel();
    }

    /**
     * Create an instance of {@link ProductXmlModel }
     * 
     */
    public ProductXmlModel createProductXmlModel() {
        return new ProductXmlModel();
    }

    /**
     * Create an instance of {@link DriverXmlModel }
     * 
     */
    public DriverXmlModel createDriverXmlModel() {
        return new DriverXmlModel();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "template")
    public JAXBElement<String> createTemplate(String value) {
        return new JAXBElement<String>(_Template_QNAME, String.class, null, value);
    }

}
