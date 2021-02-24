package io.github.erde.wizard.page;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import io.github.erde.Activator;
import io.github.erde.Resource;

/**
 * FolderSelectWizardPage.
 *
 * @author modified by parapata
 */
public class FolderSelectWizardPage extends WizardPage {

    private Text txtOutputFolder;
    protected IFile erdFile;

    public FolderSelectWizardPage(IFile erdFile, String pageName) {
        super(pageName);
        setTitle(pageName);
        this.erdFile = erdFile;
    }

    public String getOutputFolderResource() {
        return txtOutputFolder.getText();
    }

    @Override
    public void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NULL);
        composite.setLayout(new GridLayout(3, false));
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        Label label = new Label(composite, SWT.NULL);
        label.setText(Resource.WIZARD_GENERATE_FOLDER.getValue());
        txtOutputFolder = new Text(composite, SWT.BORDER);
        txtOutputFolder.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        txtOutputFolder.setText(getCurrentPath());
        txtOutputFolder.addModifyListener(e -> doValidate());

        Button button = new Button(composite, SWT.PUSH);
        button.setText(Resource.BUTTON_BROWSE.getValue());
        button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                txtOutputFolder.setText(selectFolder());
            }
        });

        setControl(composite);
    }

    protected void doValidate() {
        setErrorMessage(null);
        setPageComplete(true);

        // if(txtOutputFolder.getText().length() == 0){
        // setErrorMessage("Choose a folder");
        // setPageComplete(false);
        // }
    }

    private String selectFolder() {
        String outPath = null;
        try {
            DirectoryDialog dialog = new DirectoryDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
            dialog.setFilterPath(getCurrentPath());
            outPath = dialog.open();
        } catch (Exception e) {
            Activator.logException(e);
        }
        return outPath;
    }

    private String getCurrentPath() {
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        IFile file = workspaceRoot.getFile(erdFile.getFullPath());
        return file.getRawLocation().toFile().getParent();
    }
}
