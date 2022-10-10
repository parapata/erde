package io.github.erde.editor.dialog.table;

import static io.github.erde.Resource.*;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import io.github.erde.ERDPlugin;
import io.github.erde.ICON;
import io.github.erde.core.util.swt.UIUtils;
import io.github.erde.editor.diagram.model.ColumnModel;

/**
 * ColumnSelectDialog.
 *
 * @author modified by parapata
 */
public class IndexColumnSelectDialog extends Dialog {

    private List<ColumnModel> columns;
    private List<ColumnModel> selectedColumns;
    private TableViewer viewer;

    public IndexColumnSelectDialog(Shell parentShell, List<ColumnModel> columns) {
        super(parentShell);
        this.columns = columns;
    }

    @Override
    protected Point getInitialSize() {
        return new Point(500, 400);
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(DIALOG_INDEX_COLUMN_SELECT_TITLE.getValue());
        newShell.setImage(ERDPlugin.getImage(ICON.TABLE.getPath()));
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        viewer = new TableViewer(parent, SWT.FULL_SELECTION | SWT.MULTI | SWT.BORDER);
        Table table = viewer.getTable();
        table.setLinesVisible(true);
        table.setHeaderVisible(true);

        UIUtils.createColumn(table, LABEL_PYHGICAL_COLUMN_NAME, 200);
        UIUtils.createColumn(table, LABEL_TYPE, 200);

        viewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
        viewer.setContentProvider(new ArrayContentProvider());
        viewer.setLabelProvider(new ITableLabelProvider() {

            @Override
            public Image getColumnImage(Object element, int columnIndex) {
                return null;
            }

            @Override
            public String getColumnText(Object element, int columnIndex) {
                ColumnModel column = (ColumnModel) element;
                if (columnIndex == 0) {
                    return column.getPhysicalName();
                } else if (columnIndex == 1) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(column.getColumnType().getPhysicalName());
                    if (column.getColumnType().isSizeSupported()) {
                        sb.append("(").append(column).append(")");
                    }
                    return sb.toString();
                }
                return null;
            }

            @Override
            public void addListener(ILabelProviderListener listener) {
            }

            @Override
            public void dispose() {
            }

            @Override
            public boolean isLabelProperty(Object element, String property) {
                return false;
            }

            @Override
            public void removeListener(ILabelProviderListener listener) {
            }
        });
        viewer.setInput(columns);

        return viewer.getControl();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void okPressed() {
        selectedColumns = ((IStructuredSelection) viewer.getSelection()).toList();
        super.okPressed();
    }

    public List<ColumnModel> getSelectedColumns() {
        return selectedColumns;
    }
}
