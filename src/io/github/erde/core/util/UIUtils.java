package io.github.erde.core.util;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import io.github.erde.IMessages;
import io.github.erde.editor.ERDiagramEditor;
import io.github.erde.editor.diagram.model.RootModel;

/**
 * UIUtils.
 *
 * @author modified by parapata
 */
public class UIUtils {

    private static IMessages messages = new IMessages() {
    };

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
    public static void createColumn(Table table, String key, int width) {
        createColumn(table, key, width, SWT.NULL);
    }

    /**
     * Creates a <code>ColumnInfo</code> with a given width.
     *
     * @param table the parent table
     * @param key the resource key of the column label
     * @param width the column width
     * @param style the column style
     */
    public static void createColumn(Table table, String key, int width, int style) {
        TableColumn column = new TableColumn(table, style);
        column.setText(messages.getResource(key));
        column.setWidth(width);
    }

    /**
     * Creates a <code>Label</code> with a given text.
     *
     * @param parent the parent
     * @param key the resource key of the label text
     * @return the created label
     */
    public static Label createLabel(Composite parent, String key) {
        Label label = new Label(parent, SWT.NULL);
        label.setText(messages.getResource(key));
        return label;
    }

    /**
     * Open the alert dialog.
     *
     * @param message message
     */
    public static void openAlertDialog(String messageKey) {
        String title = messages.getResource("dialog.alert.title");
        String message = messages.getResource(messageKey);
        MessageDialog.openError(Display.getCurrent().getActiveShell(), title, message);
    }

    /**
     * Open the confirm dialog.
     *
     * @param message message
     * @return
     */
    public static boolean openConfirmDialog(String messageKey, String... messageArgs) {
        String title = messages.getResource("dialog.confirm.title");
        String message = messages.createMessage(messageKey, messageArgs);
        return MessageDialog.openConfirm(Display.getCurrent().getActiveShell(), title, message);
    }

    /**
     * Open the infomation dialog.
     *
     * @param message message
     */
    public static void openInfoDialog(String messageKey) {
        String title = messages.getResource("dialog.info.title");
        String message = messages.getResource(messageKey);
        MessageDialog.openError(Display.getCurrent().getActiveShell(), title, message);
    }

    /**
     * Returns an active ERD editor in the workbench.
     *
     * @return an instance of active ERD editor
     */
    public static ERDiagramEditor getActiveEditor() {
        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
        IWorkbenchPage page = window.getActivePage();
        IEditorPart editorPart = page.getActiveEditor();
        if (editorPart instanceof ERDiagramEditor) {
            return (ERDiagramEditor) editorPart;
        }
        return null;
    }

    /**
     * Returns an GraphicalViewer in the ERDiagramEditor.
     *
     * @return an instance of GraphicalViewer
     */
    public static GraphicalViewer getGraphicalViewer() {
        ERDiagramEditor editor = getActiveEditor();
        if (editor != null) {
            return (GraphicalViewer) editor.getAdapter(GraphicalViewer.class);
        }
        return null;
    }

    /**
     * Returns an root model in the GraphicalViewer.
     *
     * @return an instance of RootModel
     */
    public static RootModel getRootModel() {
        GraphicalViewer viewer = getGraphicalViewer();
        if (viewer != null) {
            return (RootModel) viewer.getContents().getModel();
        }
        return null;
    }
}
