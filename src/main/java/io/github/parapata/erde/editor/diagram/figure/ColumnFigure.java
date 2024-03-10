package io.github.parapata.erde.editor.diagram.figure;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * ColumnFigure.
 *
 * @author modified by parapata
 */
public class ColumnFigure extends Label {

    private boolean underline;

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    @Override
    protected void paintFigure(Graphics graphics) {
        super.paintFigure(graphics);
        if (underline) {
            Rectangle bounds = getBounds();
            graphics.drawLine(bounds.x + 1, bounds.y + bounds.height - 1, bounds.x + bounds.width - 2,
                    bounds.y + bounds.height - 1);
        }
    }
}
