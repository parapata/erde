package io.github.erde.editor.diagram.figure.connection.decoration.idef1x;

import org.eclipse.draw2d.geometry.PointList;

import io.github.erde.editor.diagram.figure.connection.decoration.ERDDecoration;

/**
 * IDEF1XTargetDecoration.
 *
 * @author modified by parapata
 */
public class IDEF1XTargetDecoration extends ERDDecoration {

    public IDEF1XTargetDecoration() {
        super();

        PointList pointList = new PointList();
        pointList.addPoint(-15, 2);
        pointList.addPoint(-15, -2);
        pointList.addPoint(-14, -3);
        pointList.addPoint(-14, -4);
        pointList.addPoint(-12, -6);
        pointList.addPoint(-11, -6);
        pointList.addPoint(-10, -7);
        pointList.addPoint(-6, -7);
        pointList.addPoint(-5, -6);
        pointList.addPoint(-4, -6);
        pointList.addPoint(-2, -4);
        pointList.addPoint(-2, -3);
        pointList.addPoint(-1, -2);
        pointList.addPoint(-1, 2);
        pointList.addPoint(-2, 3);
        pointList.addPoint(-2, 4);
        pointList.addPoint(-4, 6);
        pointList.addPoint(-5, 6);
        pointList.addPoint(-6, 7);
        pointList.addPoint(-10, 7);
        pointList.addPoint(-11, 6);
        pointList.addPoint(-12, 6);
        pointList.addPoint(-14, 4);
        pointList.addPoint(-14, 3);
        pointList.addPoint(-15, 2);
        pointList.addPoint(-15, -2);

        this.setTemplate(pointList);
        this.setScale(1, 1);
    }
}
