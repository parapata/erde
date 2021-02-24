package io.github.erde.wizard.page;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.SortedMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import io.github.erde.Activator;
import io.github.erde.Resource;
import io.github.erde.core.LineSeparatorCode;
import io.github.erde.core.util.UIUtils;
import io.github.erde.wizard.DDLWizard;

/**
 * DDLWizardPage.
 *
 * @author modified by parapata
 */
public class DDLWizardPage extends FolderSelectWizardPage {

    private static final String PATTERN_EDITER_FILE_NAME = String.format("\\%s$", Activator.EXTENSION_ERDE);

    private Text txtFilename;
    private Button btnAlterTable;
    private Button btnComment;
    private Button btnDrop;
    private Button btnSchema;
    private Combo cmbEncoding;
    private Combo cmbLineSeparator;

    public DDLWizardPage(IFile erdFile) {
        super(erdFile, Resource.WIZARD_GENERATE_DDL_TITLE.getValue());
        setDescription(Resource.WIZARD_GENERATE_DDL_DESCRIPTION.getValue());
    }

    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);

        IDialogSettings setting = getDialogSettings();
        Composite composite = (Composite) getControl();

        Label label = new Label(composite, SWT.NULL);
        label.setText(Resource.WIZARD_GENERATE_DDL_FILENAME.getValue());

        txtFilename = new Text(composite, SWT.BORDER);
        txtFilename.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        txtFilename.setText(erdFile.getName().replaceFirst(PATTERN_EDITER_FILE_NAME, Activator.EXTENSION_DDL));
        txtFilename.addModifyListener(event -> doValidate());

        new Label(composite, SWT.NULL);
        new Label(composite, SWT.NULL).setText(Resource.WIZARD_GENERATE_DDL_ENCODING.getValue());
        cmbEncoding = new Combo(composite, SWT.READ_ONLY);
        SortedMap<String, Charset> charsets = Charset.availableCharsets();
        charsets.values().forEach(charset -> {
            cmbEncoding.add(charset.displayName());
        });
        cmbEncoding.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        cmbEncoding.setText(setting.get(DDLWizard.ENCODING));
        cmbEncoding.addModifyListener(event -> doValidate());

        new Label(composite, SWT.NULL);
        new Label(composite, SWT.NULL).setText(Resource.WIZARD_GENERATE_DDL_LINE_SEPARATOR.getValue());
        cmbLineSeparator = new Combo(composite, SWT.READ_ONLY);

        Arrays.asList(LineSeparatorCode.values()).forEach(code -> {
            cmbLineSeparator.add(code.name());
            cmbLineSeparator.setData(code.name(), code.getValue());
        });
        cmbLineSeparator.setText(setting.get(DDLWizard.LINE_SEPARATOR));
        cmbLineSeparator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        btnSchema = new Button(composite, SWT.CHECK);
        btnSchema.setText(Resource.WIZARD_GENERATE_DDL_SCHEMA.getValue());
        btnSchema.setLayoutData(UIUtils.createGridData(2));
        btnSchema.setSelection(setting.getBoolean(DDLWizard.SCHEMA));

        btnDrop = new Button(composite, SWT.CHECK);
        btnDrop.setText(Resource.WIZARD_GENERATE_DDL_DROP.getValue());
        btnDrop.setLayoutData(UIUtils.createGridData(2));
        btnDrop.setSelection(setting.getBoolean(DDLWizard.DROP));

        btnAlterTable = new Button(composite, SWT.CHECK);
        btnAlterTable.setText(Resource.WIZARD_GENERATE_DDL_ALTER_TABLE.getValue());
        btnAlterTable.setLayoutData(UIUtils.createGridData(2));
        btnAlterTable.setSelection(setting.getBoolean(DDLWizard.ALTER_TABLE));

        btnComment = new Button(composite, SWT.CHECK);
        btnComment.setText(Resource.WIZARD_GENERATE_DDL_COMMENT.getValue());
        btnComment.setLayoutData(UIUtils.createGridData(2));
        btnComment.setSelection(setting.getBoolean(DDLWizard.COMMENT));
    }

    @Override
    protected void doValidate() {
        super.doValidate();
        if (!new File(getOutputFolderResource()).exists()) {
            setErrorMessage(Resource.WIZARD_GENERATE_DDL_ERROR_PATH.getValue());
            setPageComplete(false);
        } else if (txtFilename.getText().isEmpty()) {
            setErrorMessage(Resource.WIZARD_GENERATE_DDL_ERROR_FILENAME.getValue());
            setPageComplete(false);
            return;
        } else if (!isSupportedEncoding(cmbEncoding.getText())) {
            setErrorMessage(Resource.WIZARD_GENERATE_DDL_ERROR_ENCODING.getValue());
            setPageComplete(false);
            return;
        }
    }

    private boolean isSupportedEncoding(String encoding) {
        try {
            new String(new byte[0], encoding);
        } catch (UnsupportedEncodingException e) {
            return false;
        }
        return true;
    }

    public String getFilename() {
        return txtFilename.getText();
    }

    public boolean getAlterTable() {
        return btnAlterTable.getSelection();
    }

    public boolean getComment() {
        return btnComment.getSelection();
    }

    public boolean getDrop() {
        return btnDrop.getSelection();
    }

    public boolean getSchema() {
        return btnSchema.getSelection();
    }

    public String getEncoding() {
        return cmbEncoding.getText();
    }

    public String getLineSeparator() {
        return cmbLineSeparator.getText();
    }
}
