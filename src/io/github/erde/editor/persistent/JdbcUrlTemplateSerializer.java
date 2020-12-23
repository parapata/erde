package io.github.erde.editor.persistent;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXB;

import io.github.erde.editor.persistent.db.DriverXmlModel;
import io.github.erde.editor.persistent.db.JdbcUrlTemplateXmlModel;
import io.github.erde.editor.persistent.db.ObjectFactory;
import io.github.erde.editor.persistent.db.ProductXmlModel;

/**
 * JdbcUrlTemplateSerializer.
 *
 * @author modified by parapata
 */
public class JdbcUrlTemplateSerializer {

    private static final String URL_TEMPLATE_XML = "/io/github/erde/wizard/jdbcUrlTemplate.xml";

    private static JdbcUrlTemplateXmlModel ROOT_MODEL;

    static {
        try (InputStream is = JdbcUrlTemplateSerializer.class.getResourceAsStream(URL_TEMPLATE_XML)) {
            ROOT_MODEL = JAXB.unmarshal(is, JdbcUrlTemplateXmlModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        ObjectFactory factory = new ObjectFactory();

        JdbcUrlTemplateXmlModel rootModel = factory.createJdbcUrlTemplateXmlModel();
        ProductXmlModel productModel = factory.createProductXmlModel();
        productModel.setName("MySQL");

        DriverXmlModel driverModel = factory.createDriverXmlModel();
        driverModel.setName("dddddd");
        driverModel.setTemplate("ddddddddd");
        productModel.getDrivers().add(driverModel);

        rootModel.getProducts().add(productModel);

        JAXB.marshal(rootModel, System.out);

        new JdbcUrlTemplateSerializer().getUrl("MySQL", "");

    }

    public String getUrl(String product, String driver) {
        try (InputStream is = getClass().getResourceAsStream(URL_TEMPLATE_XML)) {
            JdbcUrlTemplateXmlModel res = JAXB.unmarshal(is, JdbcUrlTemplateXmlModel.class);
            for (ProductXmlModel model : res.getProducts()) {
                System.out.println(model.getName());
                model.getDrivers().forEach(item -> {
                    System.out.println(item.getName());
                    System.out.println(item.getTemplate());
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
