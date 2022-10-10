package io.github.erde.editor.diagram.figure.connection.decoration.ie;

import org.eclipse.draw2d.geometry.PointList;

import io.github.erde.editor.diagram.figure.connection.decoration.ERDDecoration;

/**
 * IEOptionalTargetDecoration.
 *
 * @author modified by parapata
 */
public class IEOptionalTargetDecoration extends ERDDecoration {

    public IEOptionalTargetDecoration() {
        super();

        PointList pointList = new PointList();
        pointList.addPoint(-16, 0);
        pointList.addPoint(-13, 0);
        pointList.addPoint(-1, -12);
        pointList.addPoint(-13, 0);
        pointList.addPoint(-1, 12);
        pointList.addPoint(-13, 0);
        pointList.addPoint(-16, 0);

        // circle
        int x = -23;
        int y = 0;
        int n = 28; // プロット数
        int diameter = 6; // 直径？
        for (int i = 0; i < n; i++) {
            Double px = x + diameter * Math.cos(i * Math.PI / (n / 2));
            Double py = y + diameter * Math.sin(i * Math.PI / (n / 2));
            pointList.addPoint(px.intValue(), py.intValue());
        }

        this.setTemplate(pointList);
        this.setScale(1, 1);
    }
}
