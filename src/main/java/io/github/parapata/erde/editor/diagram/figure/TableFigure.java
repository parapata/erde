package io.github.parapata.erde.editor.diagram.figure;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

import io.github.parapata.erde.core.util.StringUtils;
import io.github.parapata.erde.editor.diagram.editpart.editpolicy.ERDiagramFrameBorder;

/**
 * TableFigure.
 *
 * @author modified by parapata
 */
// public class TableFigure extends RoundedRectangle {
// public class TableFigure extends RectangleFigure {
public class TableFigure extends Figure {

    private static final int COLUMN_NAME = 0;
    private static final int COLUMN_TYPE = 1;
    private static final int NN = 2;

    private ERDiagramFrameBorder border;
    private ColumnLayoutFigure columnFigure;
    private CompartmentFigure columnNameFigure;
    private CompartmentFigure columnTypeFigure;
    private CompartmentFigure notNullFigure;
    private int activeColumn = 0;

    public TableFigure() {
        super();
        this.columnNameFigure = new CompartmentFigure();

        this.columnTypeFigure = new CompartmentFigure();
        this.columnTypeFigure.setBorder(new MarginBorder(0, 5, 0, 0));

        this.notNullFigure = new CompartmentFigure();
        this.notNullFigure.setBorder(new MarginBorder(0, 5, 0, 7));

        this.columnFigure = new ColumnLayoutFigure();
        this.columnFigure.setBorder(new MarginBorder(2, 8, 5, 0));
        this.columnFigure.add(columnNameFigure);
        this.columnFigure.add(columnTypeFigure);
        this.columnFigure.add(notNullFigure);

        ToolbarLayout layout = new ToolbarLayout(false);
        // layout.setStretchMinorAxis(true);
        // layout.setSpacing(0);
        setLayoutManager(layout);

        border = new ERDiagramFrameBorder();
        setBorder(border);
        setOpaque(true);
        Font font;
        try {
            if (Display.getCurrent() == null) {
                font = new Font(new Display(), "ＭＳ 明朝", 20, SWT.NORMAL);
            } else {
                font = Display.getCurrent().getSystemFont();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        FontData[] fd = font.getFontData();
        fd[0].height = fd[0].height + 2;
        fd[0].setStyle(SWT.BOLD);
        border.setFont(new Font(font.getDevice(), fd));

        add(this.columnFigure);
        repaint();
    }

    @Override
    public void add(IFigure figure, Object constraint, int index) {
        if (figure instanceof ColumnFigure) {
            if (activeColumn == COLUMN_NAME) {
                columnNameFigure.add(figure);
                activeColumn = COLUMN_TYPE;
            } else if (activeColumn == COLUMN_TYPE) {
                columnTypeFigure.add(figure);
                activeColumn = NN;
            } else {
                notNullFigure.add(figure);
                activeColumn = COLUMN_NAME;
            }
        } else {
            super.add(figure, constraint, index);
        }
    }

    @Override
    public void remove(IFigure figure) {
        if (figure instanceof ColumnFigure) {
            columnNameFigure.remove(figure);
            columnTypeFigure.remove(figure);
            notNullFigure.remove(figure);
        } else {
            super.remove(figure);
        }
    }

    public void setTableName(String tableName) {
        // this.name.setText(tableName);
        border.setLabel(tableName);
    }

    public void setErrorMessage(String message) {
        if (StringUtils.isEmpty(message)) {
            // this.name.setIcon(null);
            // this.name.setToolTip(null);
        } else {
            // this.name.setIcon(Activator.getImage(Activator.ICON_WARNING));
            // this.name.setToolTip(new Label(message));
        }
    }

    public void setLinkedTable(boolean linked) {
        if (linked) {
            setForegroundColor(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
            // ((LineBorder) getBorder()).setColor(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
        } else {
            setForegroundColor(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
            // ((LineBorder) getBorder()).setColor(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
        }
    }

    @Override
    public Dimension getPreferredSize(int wHint, int hHint) {
        Dimension dimension = super.getPreferredSize(wHint, hHint);
        int w = dimension.width < 110 ? 110 : dimension.width;
        int h = columnNameFigure.getChildren().isEmpty() ? 60 : dimension.height;
        return new Dimension(w, h); // 固定サイズを返す
    }

    public void clearColumns() {
        columnNameFigure.removeAll();
        columnTypeFigure.removeAll();
        notNullFigure.removeAll();
    }

    public String getLabel() {
        return border.getLabel();
    }
}
