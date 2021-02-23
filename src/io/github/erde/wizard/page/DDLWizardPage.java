package io.github.erde.wizard.page;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import io.github.erde.Activator;
import io.github.erde.Resource;
import io.github.erde.core.util.UIUtils;

/**
 * DDLWizardPage.
 *
 * @author modified by parapata
 */
public class DDLWizardPage extends FolderSelectWizardPage {

    private static final String PATTERN_EDITER_FILE_NAME = String.format("\\%s$", Activator.EXTENSION_ERDE);

    private Text filename;
    private Button alterTable;
    private Button comment;
    private Button drop;
    private Button schema;
    private Text encoding;

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

        filename = new Text(composite, SWT.BORDER);
        filename.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        filename.setText(erdFile.getName().replaceFirst(PATTERN_EDITER_FILE_NAME, Activator.EXTENSION_DDL));
        filename.addModifyListener(e -> doValidate());

        new Label(composite, SWT.NULL);

        new Label(composite, SWT.NULL).setText(Resource.WIZARD_GENERATE_DDL_ENCODING.getValue());
        encoding = new Text(composite, SWT.BORDER);
        encoding.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        encoding.setText(setting.get("encoding"));
        encoding.addModifyListener(e -> doValidate());

        schema = new Button(composite, SWT.CHECK);
        schema.setText(Resource.WIZARD_GENERATE_DDL_SCHEMA.getValue());
        schema.setLayoutData(UIUtils.createGridData(2));
        schema.setSelection(setting.getBoolean("schema"));

        drop = new Button(composite, SWT.CHECK);
        drop.setText(Resource.WIZARD_GENERATE_DDL_DROP.getValue());
        drop.setLayoutData(UIUtils.createGridData(2));
        drop.setSelection(setting.getBoolean("drop"));

        alterTable = new Button(composite, SWT.CHECK);
        alterTable.setText(Resource.WIZARD_GENERATE_DDL_ALTER_TABLE.getValue());
        alterTable.setLayoutData(UIUtils.createGridData(2));
        alterTable.setSelection(setting.getBoolean("alterTable"));

        comment = new Button(composite, SWT.CHECK);
        comment.setText(Resource.WIZARD_GENERATE_DDL_COMMENT.getValue());
        comment.setLayoutData(UIUtils.createGridData(2));
        comment.setSelection(setting.getBoolean("comment"));
    }

    @Override
    protected void doValidate() {
        super.doValidate();
        if (!new File(getOutputFolderResource()).exists()) {
            setErrorMessage(Resource.WIZARD_GENERATE_DDL_ERROR_PATH.getValue());
            setPageComplete(false);
        } else if (filename.getText().isEmpty()) {
            setErrorMessage(Resource.WIZARD_GENERATE_DDL_ERROR_FILENAME.getValue());
            setPageComplete(false);
            return;
        } else if (!isSupportedEncoding(encoding.getText())) {
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
        return filename.getText();
    }

    public boolean getAlterTable() {
        return alterTable.getSelection();
    }

    public boolean getComment() {
        return comment.getSelection();
    }

    public boolean getDrop() {
        return drop.getSelection();
    }

    public boolean getSchema() {
        return schema.getSelection();
    }

    public String getEncoding() {
        return encoding.getText();
    }
}
