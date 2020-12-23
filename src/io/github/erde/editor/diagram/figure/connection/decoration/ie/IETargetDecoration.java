package io.github.erde.editor.diagram.figure.connection.decoration.ie;

import org.eclipse.draw2d.geometry.PointList;

import io.github.erde.editor.diagram.figure.connection.decoration.ERDecoration;

/**
 * IETargetDecoration.
 *
 * @author modified by parapata
 */
public class IETargetDecoration extends ERDecoration {

    public IETargetDecoration() {
        super();

        PointList pointList = new PointList();
        pointList.addPoint(-13, -12);
        pointList.addPoint(-13, 0);
        pointList.addPoint(-1, -12);
        pointList.addPoint(-13, 0);
        pointList.addPoint(-1, 12);
        pointList.addPoint(-13, 0);
        pointList.addPoint(-13, 12);

        this.setTemplate(pointList);
        this.setScale(1, 1);
    }
}
