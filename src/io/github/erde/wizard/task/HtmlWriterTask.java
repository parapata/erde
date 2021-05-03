package io.github.erde.wizard.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.w3c.dom.Document;

import io.github.erde.core.util.IOUtils;
import io.github.erde.generate.HtmlGenerator;

/**
 * HtmlWriterTask.
 *
 * @author parapata
 * @since 1.0.14
 */
public class HtmlWriterTask implements IRunnableWithProgress {

    private File inFile;
    private File outPath;

    public HtmlWriterTask(File inFile, File outPath) {
        this.inFile = inFile;
        this.outPath = outPath;
    }

    @Override
    public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
        try {
            monitor.beginTask("ファイル出力中...", IProgressMonitor.UNKNOWN);
            if (monitor.isCanceled()) {
                throw new InterruptedException();
            }
            export();
            monitor.worked(1);
        } finally {
            monitor.done();
        }
    }

    private void export() throws InvocationTargetException {
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
                OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {

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
