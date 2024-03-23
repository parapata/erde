package io.github.parapata.erde.wizard.page;

import static io.github.parapata.erde.Resource.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
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

import io.github.parapata.erde.ErdePlugin;
import io.github.parapata.erde.core.util.StringUtils;
import io.github.parapata.erde.editor.ErdeDiagramEditor;

/**
 * FolderSelectWizardPage.
 *
 * @author modified by parapata
 */
public class FolderSelectWizardPage extends WizardPage {

    private Text txtOutputFolder;
    private Path path;

    public FolderSelectWizardPage(String pageName) {
        super(FolderSelectWizardPage.class.getSimpleName());

        // TODO パスは外部から取得するように修正
        IFile file = ErdeDiagramEditor.getERDiagramEditorFile();
        IPath path = file.getParent().getLocation();
        this.path = Paths.get(path.toOSString());
    }

    public String getOutputFolderResource() {
        return txtOutputFolder.getText();
    }

    @Override
    public void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(3, false));
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        Label label = new Label(composite, SWT.NONE);
        label.setText(WIZARD_FOLDER_SELECT_FOLDER.getValue());
        txtOutputFolder = new Text(composite, SWT.BORDER);
        txtOutputFolder.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        txtOutputFolder.setText(getCurrentPath());
        txtOutputFolder.addModifyListener(event -> doValidate());

        Button button = new Button(composite, SWT.PUSH);
        button.setText(LABEL_BROWSE.getValue());
        button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                String folder = selectFolder();
                if (StringUtils.isNotEmpty(folder)) {
                    txtOutputFolder.setText(folder);
                }
            }
        });
        setControl(composite);
    }

    protected void doValidate() {
        setErrorMessage(null);
        if (StringUtils.isEmpty(txtOutputFolder.getText())) {
            setErrorMessage("Choose a folder");
            setPageComplete(false);
        } else {
            setPageComplete(true);
        }
    }

    private String selectFolder() {
        String outPath = null;
        try {
            DirectoryDialog dialog = new DirectoryDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
            dialog.setFilterPath(getCurrentPath());
            outPath = dialog.open();
        } catch (Exception e) {
            ErdePlugin.logException(e);
        }
        return outPath;
    }

    private String getCurrentPath() {
        return path.toString();
    }
}
