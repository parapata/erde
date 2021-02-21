package io.github.erde.editor.dialog;
import static io.github.erde.Resource.*;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import io.github.erde.Activator;

/**
 * ERDMessageDialog.
 *
 * @author modified by parapata
 */
public class ERDMessageDialog {

    public static void openSystemError(String message, Throwable e) {
        IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.toString(), e);
        // Activator.error(e);
        Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        String title = DIALOG_ERROR_SYSTEM_TITLE.getValue();
        ErrorDialog.openError(shell, title, message, status);
    }

    public static void openAlert(String message) {
        Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        String title =  DIALOG_ALERT_TITLE.getValue();
        MessageDialog.openError(shell, title, message);
    }

    public static void openConfirm(String message) {
        Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        String title = DIALOG_CONFIRM_TITLE.getValue();
        MessageDialog.openConfirm(shell, title, message);
    }

    public static void openInformation(String message) {
        Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        String title = DIALOG_INFO_TITLE.getValue();
        MessageDialog.openInformation(shell, title, message);
    }
}
