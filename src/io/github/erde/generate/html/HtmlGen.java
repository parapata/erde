package io.github.erde.generate.html;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;

/**
 * HTMLGen.
 *
 * @author modified by parapata
 */
public class HtmlGen {

    private static final File INPUT_XML_FILE = new File("D:\\repos\\erde\\logs\\newfile.ere");
    private static final File INPUT_XSL_FILE = new File(
            "D:\\repos\\erde\\resources\\io\\github\\erde\\generate\\html\\HtmlGen.xsl");
    private static final File OUTPUT_HTML_FILE = new File("D:\\repos\\erde\\logs\\HtmlGen.html");

    public static void main(String[] args) {
        new HtmlGen().execute();
    }

    private void execute() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(INPUT_XML_FILE);
            if (OUTPUT_HTML_FILE.exists()) {
                OUTPUT_HTML_FILE.delete();
            }
            convertXMLToHTMLWithXSL(doc, OUTPUT_HTML_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void convertXMLToHTMLWithXSL(Document doc, File file) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(file, false);
                OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {

            StreamResult sr = new StreamResult(osw);

            TransformerFactory factory = TransformerFactory.newInstance();
            StreamSource source = new StreamSource(INPUT_XSL_FILE);
            Transformer transformer = factory.newTransformer(source);
            transformer.setOutputProperty("indent", "yes");
            transformer.transform(new DOMSource(doc), sr);

        } catch (TransformerException e) {
            throw e;
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }
}
