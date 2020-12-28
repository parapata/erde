package io.github.erde.editor.diagram.figure.connection.decoration;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import io.github.erde.editor.diagram.figure.connection.RelationshipConnection;

/**
 * ERDecoration.
 *
 * @author modified by parapata
 */
public class ERDecoration extends PolygonDecoration {
    @Override
    public void paintFigure(Graphics graphics) {

        RelationshipConnection connection = (RelationshipConnection) this.getParent();
        graphics.setAntialias(SWT.ON);
        Color color = connection.getColor();

        if (color != null) {
            graphics.setForegroundColor(color);
            graphics.setBackgroundColor(color);
        }
        super.paintFigure(graphics);
    }
}
