package io.github.erde.generate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.w3c.dom.Document;

import io.github.erde.core.util.UIUtils;
import io.github.erde.editor.diagram.model.RootModel;

/**
 * HtmlGenerator.
 *
 * @author modified by parapata
 */
public class HtmlGenerator implements IGenerator {

    @Override
    public String getGeneratorName() {
        return "HTML";
    }

    @Override
    public void execute(IFile erdFile, RootModel root, GraphicalViewer viewer) {
        DirectoryDialog dialog = new DirectoryDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
        dialog.setFilterPath(getDefaultPath(erdFile));
        String outPath = dialog.open();

        if (outPath != null) {
            try (InputStream is = erdFile.getContents()) {
                execute(is, outPath);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CoreException e) {
                e.printStackTrace();
            } finally {
                UIUtils.projectRefresh();
            }
        }
    }

    public void execute(InputStream erde, String outPath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(erde);

            Path html = Paths.get(outPath, "index.html");
            convertXMLToHTMLWithXSL(doc, html);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void convertXMLToHTMLWithXSL(Document doc, Path html) throws Exception {
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
            throw e;
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }
}
