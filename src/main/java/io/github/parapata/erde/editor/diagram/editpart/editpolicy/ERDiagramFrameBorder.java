package io.github.parapata.erde.editor.diagram.editpart.editpolicy;

import org.eclipse.draw2d.FrameBorder;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.TitleBarBorder;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * ERDiagramFrameBorder.
 *
 * @author modified by parapata
 */
public class ERDiagramFrameBorder extends FrameBorder {

    private static final int DELTA = 255 / 10;

    private Color color;

    public ERDiagramFrameBorder() {
        this(new Color(Display.getCurrent(), 0, 76, 153));
    }

    public ERDiagramFrameBorder(Color color) {
        super();
        this.color = color;
        ((TitleBarBorder) super.inner).setBackgroundColor(color);
    }

    public ERDiagramFrameBorder(String label, Color color) {
        super(label);
        this.color = color;
        ((TitleBarBorder) super.inner).setBackgroundColor(color);
    }

    @Override
    protected void createBorders() {
        inner = new TitleBarBorder();
        outer = new LineBorder();
    }

    @Override
    public void paint(IFigure figure, Graphics graphics, Insets insets) {
        tempRect.setBounds(getPaintRectangle(figure, insets));

        int r = 9 * DELTA;
        int g = 9 * DELTA;
        int b = 9 * DELTA;

        // for (int i = 0; i <= 5; i++) {
        for (int i = 0; i <= 2; i++) {
            // Color color = new Color(Display.getCurrent(), 255, g, b);
            paint1(i, color, tempRect, graphics);
            g -= DELTA;
            b -= DELTA;
        }
        super.paint(figure, graphics, insets);
    }

    public void setTitleBackgroundColor(Color color) {
        this.color = color;
        ((TitleBarBorder) super.inner).setBackgroundColor(color);
    }

    private void paint1(int i, Color color, Rectangle tempRect, Graphics graphics) {
        tempRect.x++;
        tempRect.y++;
        tempRect.width -= 2;
        tempRect.height -= 2;

        graphics.setForegroundColor(color);
        graphics.drawRectangle(tempRect);
    }
}
