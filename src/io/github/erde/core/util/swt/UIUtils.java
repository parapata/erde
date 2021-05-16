package io.github.erde.core.util.swt;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import io.github.erde.ERDPlugin;
import io.github.erde.Resource;

/**
 * UIUtils.
 *
 * @author modified by parapata
 */
public class UIUtils {

    private UIUtils() {
        throw new AssertionError();
    }

    /**
     * Creates the <code>GridData</code> with a given colspan.
     *
     * @param colspan
     *            the colspan
     * @return the created GridData
     */
    public static GridData createGridData(int colspan) {
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = colspan;
        return gd;
    }

    public static GridData createGridData(int colspan, int option) {
        GridData gd = new GridData(option);
        gd.horizontalSpan = colspan;
        return gd;
    }

    public static GridData createGridDataWithWidth(int width) {
        GridData gd = new GridData();
        gd.widthHint = width;
        return gd;
    }

    public static GridData createGridDataWithColspan(int colspan, int height) {
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = colspan;
        gd.heightHint = height;
        return gd;
    }

    public static GridData createGridDataWithRowspan(int rowspan, int width) {
        GridData gd = new GridData(GridData.FILL_VERTICAL);
        gd.verticalSpan = rowspan;
        gd.widthHint = width;
        return gd;
    }

    /**
     * Creates a <code>ColumnInfo</code> with a given width.
     *
     * @param table the parent table
     * @param key the resource key of the column label
     * @param width the column width
     */
    public static void createColumn(Table table, Resource key, int width) {
        createColumn(table, key, width, SWT.NONE);
    }

    /**
     * Creates a <code>ColumnInfo</code> with a given width.
     *
     * @param table the parent table
     * @param key the resource key of the column label
     * @param width the column width
     * @param style the column style
     */
    public static void createColumn(Table table, Resource key, int width, int style) {
        TableColumn column = new TableColumn(table, style);
        column.setText(key.getValue());
        column.setWidth(width);
    }

    /**
     * Creates a <code>Label</code> with a given text.
     *
     * @param parent the parent
     * @param key the resource key of the label text
     * @return the created label
     */
    public static Label createLabel(Composite parent, Resource key) {
        Label label = new Label(parent, SWT.NONE);
        label.setText(key.getValue());
        return label;
    }

    /**
     * Open the alert dialog.
     *
     * @param message message
     */
    public static void openAlertDialog(String message) {
        String title = Resource.DIALOG_ALERT_TITLE.getValue();
        MessageDialog.openError(Display.getCurrent().getActiveShell(), title, message);
    }

    /**
     * Open the alert dialog.
     *
     * @param message message
     */
    public static void openAlertDialog(Resource message) {
        openAlertDialog(message.getValue());
    }

    /**
     * Open the alert dialog.
     *
     * @param message message
     */
    public static void openAlertDialog(Resource message, String... messageArgs) {
        openAlertDialog(message.createMessage(messageArgs));
    }

    /**
     * Open the confirm dialog.
     *
     * @param message message
     * @return
     */
    public static boolean openConfirmDialog(Resource message, String... messageArgs) {
        String title = Resource.DIALOG_CONFIRM_TITLE.getValue();
        return MessageDialog.openConfirm(Display.getCurrent().getActiveShell(),
                title,
                message.createMessage(messageArgs));
    }

    /**
     * Open the infomation dialog.
     *
     * @param message message
     */
    public static void openInfoDialog(Resource message) {
        String title = Resource.DIALOG_INFO_TITLE.getValue();
        MessageDialog.openError(Display.getCurrent().getActiveShell(), title, message.getValue());
    }

    public static void projectRefresh() {
        try {
            for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
                project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
            }
            IWorkspaceRoot wsroot = ResourcesPlugin.getWorkspace().getRoot();
            wsroot.refreshLocal(IResource.DEPTH_ONE, new NullProgressMonitor());
        } catch (CoreException e) {
            ERDPlugin.logException(e);
        }
    }
}
