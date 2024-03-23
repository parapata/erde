package io.github.parapata.erde.editor.diagram.editpart.editpolicy;

import org.eclipse.gef.editpolicies.SelectionEditPolicy;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * TableSelectionEditPolicy.
 *
 * @author modified by parapata
 */
public class TableSelectionEditPolicy extends SelectionEditPolicy {

    private static final Color HIDE_COLOR = new Color(Display.getCurrent(), 0, 76, 153);
    private static final Color SHOW_COLOR = new Color(Display.getCurrent(), 255, 0, 0);

    @Override
    protected void hideSelection() {
        DiagramFrameBorder border = (DiagramFrameBorder) getHostFigure().getBorder();
        border.setTitleBackgroundColor(HIDE_COLOR);
    }

    @Override
    protected void showSelection() {
        DiagramFrameBorder border = (DiagramFrameBorder) getHostFigure().getBorder();
        border.setTitleBackgroundColor(SHOW_COLOR);
    }
}
