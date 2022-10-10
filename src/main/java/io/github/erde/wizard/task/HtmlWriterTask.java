package io.github.erde.wizard.task;

import static io.github.erde.Resource.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import io.github.erde.generate.html.HtmlGen;

/**
 * HtmlWriterTask.
 *
 * @author parapata
 * @since 1.0.14
 */
public class HtmlWriterTask implements IRunnableWithProgress {

    private HtmlGen gen;

    public HtmlWriterTask(File inFile, File outPath) {
        gen = new HtmlGen(inFile, outPath);
    }

    @Override
    public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
        try {
            monitor.beginTask(INFO_PROCESSING_NOW.getValue(), IProgressMonitor.UNKNOWN);
            if (monitor.isCanceled()) {
                throw new InterruptedException();
            }
            gen.export();
            monitor.worked(1);
        } finally {
            monitor.done();
        }
    }
}
