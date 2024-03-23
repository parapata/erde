package io.github.parapata.erde.editor.diagram.figure.connection.decoration.ie;

import org.eclipse.draw2d.geometry.PointList;

import io.github.parapata.erde.editor.diagram.figure.connection.decoration.ErdeDecoration;

/**
 * IEOneDecoration.
 *
 * @author modified by parapata
 */
public class IEOneDecoration extends ErdeDecoration {

    public IEOneDecoration() {
        super();

        PointList pointList = new PointList();
        pointList.addPoint(-13, -12);
        pointList.addPoint(-13, 12);

        this.setTemplate(pointList);
        this.setScale(1, 1);
    }
}
