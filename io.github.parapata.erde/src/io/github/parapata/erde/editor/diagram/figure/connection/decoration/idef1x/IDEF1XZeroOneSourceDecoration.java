package io.github.parapata.erde.editor.diagram.figure.connection.decoration.idef1x;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.PointList;

import io.github.parapata.erde.editor.diagram.figure.connection.decoration.ErdeDecoration;

/**
 * IDEF1XZeroOneSourceDecoration.
 *
 * @author modified by parapata
 */
public class IDEF1XZeroOneSourceDecoration extends ErdeDecoration {

    public IDEF1XZeroOneSourceDecoration() {
        super();

        PointList pointList = new PointList();
        pointList.addPoint(-1, 0);
        pointList.addPoint(-8, -7);
        pointList.addPoint(-15, 0);
        pointList.addPoint(-8, 7);
        pointList.addPoint(-1, 0);

        this.setTemplate(pointList);
        this.setScale(1, 1);

        Label label = new Label();
        label.setText("Z");

        this.add(label);
    }
}
