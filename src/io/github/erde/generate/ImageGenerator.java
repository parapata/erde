package io.github.erde.generate;

import org.eclipse.core.resources.IFile;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

import io.github.erde.editor.diagram.model.RootModel;

/**
 * ImageGenerator.
 *
 * @author modified by parapata
 */
public class ImageGenerator implements IGenerator {

    @Override
    public String getGeneratorName() {
        return "Image";
    }

    @Override
    public void execute(IFile erdFile, RootModel root, GraphicalViewer viewer) {
        ScalableRootEditPart rootEditPart = (ScalableRootEditPart) viewer.getRootEditPart();
        double zoom = rootEditPart.getZoomManager().getZoom();

        try {
            FileDialog dialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
            dialog.setFilterPath(getDefaultPath(erdFile));
            dialog.setFilterExtensions(new String[] { "*.png" });
            dialog.setFileName("newfile.png");

            String saveFile = dialog.open();
            if (saveFile != null) {
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
                    saveFile = saveFile + ".bmp";
                    loader.save(saveFile, SWT.IMAGE_BMP);
                }

                image.dispose();
                gc.dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rootEditPart.getZoomManager().setZoom(zoom);
        }
    }
}
