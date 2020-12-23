/*
 * thanks
 */

package io.github.erde.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.ide.IDE;

import io.github.erde.wizard.page.NewDiagramWizardPage;

/**
 * The new diagram wizard.
 *
 * @author modified by parapata
 */
public class NewDiagramWizard extends Wizard implements INewWizard {

    private NewDiagramWizardPage page1;
    private IStructuredSelection selection;
    private IWorkbench workbench;

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.selection = selection;
        this.workbench = workbench;
    }

    @Override
    public void addPages() {
        this.page1 = new NewDiagramWizardPage(selection);
        addPage(page1);
    }

    @Override
    public boolean performFinish() {
        try {
            IFile file = page1.createNewFile();
            if (file == null) {
                return false;
            }
            IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
            IDE.openEditor(page, file, true);
        } catch (Exception e) {
            // Activator.showExceptionDialog(e);
        }
        return true;
    }
}
