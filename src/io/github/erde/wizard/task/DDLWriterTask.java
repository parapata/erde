package io.github.erde.wizard.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import io.github.erde.dialect.IDialect;
import io.github.erde.editor.diagram.model.RootModel;

/**
 * DDLWriterTask.
 *
 * @author parapata
 * @since 1.0.14
 */
public class DDLWriterTask implements IRunnableWithProgress {

    private IDialect dialect;
    private RootModel rootModel;
    private File outFile;
    private String charsetName;

    public DDLWriterTask(IDialect dialect, RootModel rootModel, File outFile, String charsetName) {
        this.dialect = dialect;
        this.rootModel = rootModel;
        this.outFile = outFile;
        this.charsetName = charsetName;
    }

    @Override
    public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
        try (FileOutputStream fos = new FileOutputStream(outFile);
                OutputStreamWriter osw = new OutputStreamWriter(fos, charsetName);
                BufferedWriter bw = new BufferedWriter(osw);
                PrintWriter pw = new PrintWriter(bw)) {
            monitor.beginTask("DDL出力中...", IProgressMonitor.UNKNOWN);
            if (monitor.isCanceled()) {
                throw new InterruptedException();
            }
            dialect.createDDL(rootModel, pw);
            pw.flush();
            monitor.worked(1);
        } catch (IOException e) {
            throw new InvocationTargetException(e);
        } finally {
            monitor.done();
        }
    }
}
