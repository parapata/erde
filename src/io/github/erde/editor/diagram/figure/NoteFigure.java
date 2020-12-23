/**
 *
 */
package io.github.erde.editor.diagram.figure;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * This class has been ported from AmaterasUML.
 *
 * @author modified by parapata
 */
public class NoteFigure extends Shape {

    private static final int RETURN_WIDTH = 15;

    private static final Color DEFAULT_BG_COLOR = new Color(Display.getCurrent(), 255, 255, 153);;

    private TextFlow textFlow;

    public NoteFigure() {
        super();
        setBorder(new MarginBorder(RETURN_WIDTH));
        setLayoutManager(new BorderLayout());
        textFlow = new TextFlow();
        ParagraphTextLayout layout = new ParagraphTextLayout(textFlow, ParagraphTextLayout.WORD_WRAP_SOFT);
        FlowPage page = new FlowPage();
        textFlow.setLayoutManager(layout);
        textFlow.setOpaque(false);
        page.add(textFlow);
        add(page, BorderLayout.CENTER);
        setMinimumSize(new Dimension(RETURN_WIDTH * 2, RETURN_WIDTH * 2));
    }

    @Override
    protected void fillShape(Graphics graphics) {
        graphics.setAlpha(200);
        Rectangle bounds = getBounds();
        Point topRight1 = bounds.getTopRight().translate(0, RETURN_WIDTH);
        Point topRight2 = bounds.getTopRight().translate(-RETURN_WIDTH, 0);
        PointList pointList = new PointList();
        pointList.addPoint(bounds.getTopLeft());
        pointList.addPoint(bounds.getBottomLeft());
        pointList.addPoint(bounds.getBottomRight());
        pointList.addPoint(topRight1);
        pointList.addPoint(topRight2);
        pointList.addPoint(bounds.getTopLeft());
        graphics.fillPolygon(pointList);
    }

    @Override
    protected void outlineShape(Graphics graphics) {
        Rectangle r = getBounds();
        int x = r.x + getLineWidth() / 2;
        int y = r.y + getLineWidth() / 2;
        int w = r.width - Math.max(1, getLineWidth());
        int h = r.height - Math.max(1, getLineWidth());
        Rectangle bounds = new Rectangle(x, y, w, h);
        Point topRight1 = bounds.getTopRight().translate(0, RETURN_WIDTH);
        Point topRight2 = bounds.getTopRight().translate(-RETURN_WIDTH, 0);
        Point topRight3 = bounds.getTopRight().translate(-RETURN_WIDTH, RETURN_WIDTH);
        graphics.drawLine(bounds.getTopLeft(), bounds.getBottomLeft());
        graphics.drawLine(bounds.getBottomLeft(), bounds.getBottomRight());
        graphics.drawLine(bounds.getBottomRight(), topRight1);
        graphics.drawLine(topRight1, topRight2);
        graphics.drawLine(topRight2, bounds.getTopLeft());
        graphics.drawLine(topRight2, topRight3);
        graphics.drawLine(topRight3, topRight1);
    }

    public void setText(String text) {
        setText(text, DEFAULT_BG_COLOR);
    }

    public void setText(String text, Color backgraundColor) {
        Color foregroundColor = decideColor(backgraundColor);
        setForegroundColor(foregroundColor);
        setBackgroundColor(DEFAULT_BG_COLOR);
        textFlow.setText(text);
    }

    private Color decideColor(Color color) {
        int sum = color.getRed() + color.getGreen() + color.getBlue();
        return sum > 255 ? ColorConstants.black : ColorConstants.white;
    }
}
