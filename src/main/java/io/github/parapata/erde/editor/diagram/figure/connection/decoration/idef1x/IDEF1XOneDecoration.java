package io.github.parapata.erde.editor.diagram.figure.connection.decoration.idef1x;

import org.eclipse.draw2d.geometry.PointList;

import io.github.parapata.erde.editor.diagram.figure.connection.decoration.ERDDecoration;

/**
 * IDEF1XOneDecoration.
 *
 * @author modified by parapata
 */
public class IDEF1XOneDecoration extends ERDDecoration {

    public IDEF1XOneDecoration() {
        super();

        PointList pointList = new PointList();
        this.setTemplate(pointList);
        this.setScale(1, 1);
    }
}
