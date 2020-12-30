package io.github.erde.generate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.eclipse.core.resources.IFile;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;

import io.github.erde.Activator;
import io.github.erde.core.util.IOUtils;
import io.github.erde.core.util.UIUtils;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;
import io.github.erde.generate.html.HTMLGen;

/**
 * HTMLGenerator.
 *
 * @author modified by parapata
 */
public class HTMLGenerator implements IGenerator {

    private static ResourceBundle bundle = ResourceBundle.getBundle(HTMLGen.class.getName());
    private static Map<String, String> messages = new HashMap<>();
    static {
        for (Enumeration<String> elements = bundle.getKeys(); elements.hasMoreElements();) {
            String key = elements.nextElement();
            messages.put(key, bundle.getString(key));
        }
    }

    static {
        // kills Velocity logging
        // Velocity.addProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, NullLogChute.class.getName());
        Velocity.addProperty(RuntimeConstants.RUNTIME_LOG_NAME, "mylog");
    }

    @Override
    public String getGeneratorName() {
        return "HTML";
    }

    @Override
    public void execute(IFile erdFile, RootModel root, GraphicalViewer viewer) {
        try {
            DirectoryDialog dialog = new DirectoryDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
            dialog.setFilterPath(getDefaultPath(erdFile));
            String outPath = dialog.open();

            if (outPath != null) {
                generate(outPath, root);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            UIUtils.projectRefresh();
        }
    }

    public void generate(String outPath, RootModel root) throws Exception {

        try (InputStream in = HTMLGenerator.class.getResourceAsStream("html/stylesheet.css");
                OutputStream out = new FileOutputStream(new File(outPath, "stylesheet.css"))) {
            IOUtils.copyStream(in, out);
        }

        Velocity.init();
        VelocityContext context = new VelocityContext();
        context.put("model", root);
        context.put("util", new VelocityUtils());
        context.put("msg", messages);

        processTemplate("index.html", new File(outPath, "index.html"), context);
        processTemplate("list.html", new File(outPath, "list.html"), context);
        processTemplate("summary.html", new File(outPath, "summary.html"), context);

        File imageDir = new File(outPath, "images");
        imageDir.mkdir();

        try (InputStream in = HTMLGenerator.class.getResourceAsStream("html/primarykey.gif");
                OutputStream out = new FileOutputStream(new File(imageDir, "primarykey.gif"))) {
            IOUtils.copyStream(in, out);
        }

        File tableDir = new File(outPath, "tables");
        tableDir.mkdir();

        for (TableModel table : root.getTables()) {
            context.put("table", table);
            processTemplate("table.html", new File(tableDir,
                    table.getPhysicalName() + ".html"), context);
        }
    }

    private void processTemplate(String templateName, File output, VelocityContext context) throws Exception {
        String charset = Activator.getCharset();
        try (StringWriter writer = new StringWriter();
                InputStream is = HTMLGen.class.getResourceAsStream(templateName);
                InputStreamReader reader = new InputStreamReader(is, charset);
                FileOutputStream out = new FileOutputStream(output);) {
            Velocity.evaluate(context, writer, "", reader);
            out.write(writer.getBuffer().toString().getBytes(charset));
        } catch (Exception e) {
            throw e;
        }
    }
}
