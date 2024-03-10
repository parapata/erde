package io.github.parapata.erde.generate.html;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;

import io.github.parapata.erde.ERDPlugin;
import io.github.parapata.erde.core.util.IOUtils;
import io.github.parapata.erde.generate.HtmlGenerator;

/**
 * HtmlGen.
 *
 * @author modified by parapata
 */
public class HtmlGen {

    private File inFile;
    private File outPath;

    public HtmlGen(File inFile, File outPath) {
        this.inFile = inFile;
        this.outPath = outPath;
    }

    public void export() throws InvocationTargetException {
        try (InputStream is = new FileInputStream(inFile)) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);

            String path = outPath.getPath();

            Path html = Paths.get(path, "index.html");
            convertXMLToHTMLWithXSL(doc, html);

            try (InputStream icon = HtmlGenerator.class.getResourceAsStream("html/check.svg")) {
                OutputStream fos = new FileOutputStream(Paths.get(path, "check.svg").toFile());
                IOUtils.copyStream(icon, fos);
            }
        } catch (Exception e) {
            throw new InvocationTargetException(e);
        }
    }

    private void convertXMLToHTMLWithXSL(Document doc, Path html) throws InvocationTargetException {
        try (InputStream xsl = HtmlGenerator.class.getResourceAsStream("html/html-frames.xsl");
                FileOutputStream fos = new FileOutputStream(html.toFile(), false);
                OutputStreamWriter osw = new OutputStreamWriter(fos, ERDPlugin.getCharset())) {

            StreamSource source = new StreamSource(xsl);
            StreamResult result = new StreamResult(osw);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(source);
            transformer.setOutputProperty("indent", "yes");
            transformer.setParameter("output.dir", html.getParent().toFile());
            transformer.transform(new DOMSource(doc), result);

        } catch (TransformerException e) {
            throw new InvocationTargetException(e);
        } catch (FileNotFoundException e) {
            throw new InvocationTargetException(e);
        } catch (IOException e) {
            throw new InvocationTargetException(e);
        }
    }
}
