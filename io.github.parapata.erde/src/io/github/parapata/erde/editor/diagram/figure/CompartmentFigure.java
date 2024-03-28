package io.github.parapata.erde.editor.diagram.figure;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.OrderedLayout;
import org.eclipse.draw2d.ToolbarLayout;

/**
 * CompartmentFigure.
 *
 * @author modified by parapata
 */
public class CompartmentFigure extends Figure {

    public CompartmentFigure() {
        ToolbarLayout layout = new ToolbarLayout();
        layout.setMinorAlignment(OrderedLayout.ALIGN_TOPLEFT);
        layout.setStretchMinorAxis(false);
        layout.setSpacing(0);
        setLayoutManager(layout);
    }
}
