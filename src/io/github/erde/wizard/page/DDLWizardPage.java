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
import io.github.erde.IMessages;
import io.github.erde.core.util.UIUtils;

/**
 * DDLWizardPage.
 *
 * @author modified by parapata
 */
public class DDLWizardPage extends FolderSelectWizardPage implements IMessages {

    private static final String PATTERN_EDITER_FILE_NAME = String.format("\\%s$", Activator.EXTENSION_ERDE);

    private Text filename;
    private Button comment;
    private Button drop;
    private Button schema;
    private Text encoding;

    public DDLWizardPage(IFile erdFile) {
        super(erdFile, resource.getString("wizard.generate.ddl.title"));
        setDescription(getResource("wizard.generate.ddl.description"));
    }

    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);

        IDialogSettings setting = getDialogSettings();
        Composite composite = (Composite) getControl();

        Label label = new Label(composite, SWT.NULL);
        label.setText(getResource("wizard.generate.ddl.filename"));

        filename = new Text(composite, SWT.BORDER);
        filename.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        filename.setText(erdFile.getName().replaceFirst(PATTERN_EDITER_FILE_NAME, Activator.EXTENSION_DDL));
        filename.addModifyListener(e -> doValidate());

        new Label(composite, SWT.NULL);

        new Label(composite, SWT.NULL).setText(getResource("wizard.generate.ddl.encoding"));
        encoding = new Text(composite, SWT.BORDER);
        encoding.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        encoding.setText(setting.get("encoding"));
        encoding.addModifyListener(e -> doValidate());

        schema = new Button(composite, SWT.CHECK);
        schema.setText(getResource("wizard.generate.ddl.schema"));
        schema.setLayoutData(UIUtils.createGridData(2));
        schema.setSelection(setting.getBoolean("schema"));

        drop = new Button(composite, SWT.CHECK);
        drop.setText(getResource("wizard.generate.ddl.dropTable"));
        drop.setLayoutData(UIUtils.createGridData(2));
        drop.setSelection(setting.getBoolean("drop"));

        comment = new Button(composite, SWT.CHECK);
        comment.setText(getResource("wizard.generate.ddl.comment"));
        comment.setLayoutData(UIUtils.createGridData(2));
        comment.setSelection(setting.getBoolean("comment"));
    }

    @Override
    protected void doValidate() {
        super.doValidate();
        if (!new File(getOutputFolderResource()).exists()) {
            setErrorMessage(getResource("wizard.generate.ddl.error.path"));
            setPageComplete(false);
        } else if (filename.getText().length() == 0) {
            setErrorMessage(getResource("wizard.generate.ddl.error.filename"));
            setPageComplete(false);
            return;
        } else if (!isSupportedEncoding(encoding.getText())) {
            setErrorMessage(getResource("wizard.generate.ddl.error.encoding"));
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

    public Text getFilename() {
        return filename;
    }

    public Button getComment() {
        return comment;
    }

    public Button getDrop() {
        return drop;
    }

    public Button getSchema() {
        return schema;
    }

    public Text getEncoding() {
        return encoding;
    }
}
