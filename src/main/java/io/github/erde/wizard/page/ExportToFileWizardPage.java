package io.github.erde.wizard.page;

import static io.github.erde.Resource.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import io.github.erde.core.util.swt.UIUtils;

/**
 * ExportToExcelWizardPage.
 *
 * @author parapata
 */
public class ExportToFileWizardPage extends FolderSelectWizardPage {

    private String fileName;
    private Text txtFileName;

    public ExportToFileWizardPage(String fileName) {
        this(ExportToFileWizardPage.class.getSimpleName(), fileName);
    }

    public ExportToFileWizardPage(String pageName, String fileName) {
        super(pageName);
        this.fileName = fileName;
    }

    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);

        Composite composite = (Composite) getControl();

        // -------------
        UIUtils.createLabel(composite, LABEL_FILENAME);
        txtFileName = new Text(composite, SWT.BORDER);
        txtFileName.setLayoutData(UIUtils.createGridData(2));
        txtFileName.setText(fileName);
        txtFileName.addModifyListener(event -> doValidate());
    }

    @Override
    protected void doValidate() {
        super.doValidate();
        if (txtFileName.getText().isEmpty()) {
            setErrorMessage(ERROR_WIZARD_GENERATE_DDL_ERROR_FILENAME.getValue());
            setPageComplete(false);
            return;
        }
    }

    public String getFileName() {
        return txtFileName.getText();
    }
}
