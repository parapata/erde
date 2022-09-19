package io.github.erde.editor.diagram.figure;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.OrderedLayout;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Insets;

/**
 * ColumnLayoutFigure.
 *
 * @author modified by parapata
 */
public class ColumnLayoutFigure extends Figure {

    public ColumnLayoutFigure() {
        ToolbarLayout layout = new ToolbarLayout(true);
        layout.setMinorAlignment(OrderedLayout.ALIGN_TOPLEFT);
        layout.setStretchMinorAxis(false);
        layout.setSpacing(2);
        setLayoutManager(layout);
        setBorder(getBorderInstance());
    }

    private Border getBorderInstance() {
        return new AbstractBorder() {
            @Override
            public Insets getInsets(IFigure figure) {
                return new Insets(1, 0, 2, 0);
            }

            @Override
            public void paint(IFigure figure, Graphics graphics, Insets insets) {
                graphics.drawLine(getPaintRectangle(figure, insets).getTopLeft(), tempRect.getTopRight());
            }
        };
    }
}
