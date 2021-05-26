package io.github.erde.wizard.task;

import static io.github.erde.Resource.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;

import io.github.erde.core.util.swt.UIUtils;

/**
 * ImageWriterTask.
 *
 * @author parapata
 * @since 1.0.14
 */
public class ImageWriterTask implements IRunnableWithProgress {

    private GraphicalViewer viewer;
    private File outFile;

    public ImageWriterTask(GraphicalViewer viewer, File outFile) {
        this.viewer = viewer;
        this.outFile = outFile;
    }

    @Override
    public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
        try {
            monitor.beginTask(INFO_PROCESSING_NOW.getValue(), IProgressMonitor.UNKNOWN);
            if (monitor.isCanceled()) {
                throw new InterruptedException();
            }
            export();
            monitor.worked(1);
        } finally {
            monitor.done();
        }
    }

    private void export() {
        ScalableRootEditPart rootEditPart = (ScalableRootEditPart) viewer.getRootEditPart();
        double zoom = rootEditPart.getZoomManager().getZoom();

        try {
            String saveFile = outFile.getAbsolutePath();
            IFigure figure = rootEditPart.getLayer(LayerConstants.PRINTABLE_LAYERS);

            Rectangle rectangle = figure.getBounds();

            Image image = new Image(Display.getDefault(), rectangle.width + 50, rectangle.height + 50);
            GC gc = new GC(image);
            SWTGraphics graphics = new SWTGraphics(gc);
            figure.paint(graphics);

            ImageLoader loader = new ImageLoader();
            loader.data = new ImageData[] { image.getImageData() };

            if (saveFile.endsWith(".bmp")) {
                loader.save(saveFile, SWT.IMAGE_BMP);
            } else if (saveFile.endsWith(".gif")) {
                loader.save(saveFile, SWT.IMAGE_GIF);
            } else if (saveFile.endsWith(".jpg") || saveFile.endsWith(".jpeg")) {
                loader.save(saveFile, SWT.IMAGE_JPEG);
            } else if (saveFile.endsWith(".png")) {
                loader.save(saveFile, SWT.IMAGE_PNG);
            } else if (saveFile.endsWith(".tiff")) {
                loader.save(saveFile, SWT.IMAGE_TIFF);
            } else {
                saveFile = saveFile + ".png";
                loader.save(saveFile, SWT.IMAGE_PNG);
            }

            image.dispose();
            gc.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rootEditPart.getZoomManager().setZoom(zoom);
            UIUtils.projectRefresh();
        }
    }
}
