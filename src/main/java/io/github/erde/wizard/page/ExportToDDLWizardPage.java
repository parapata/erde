package io.github.erde.wizard.page;

import static io.github.erde.Resource.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.SortedMap;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import io.github.erde.core.LineSeparatorCode;
import io.github.erde.core.util.swt.UIUtils;
import io.github.erde.wizard.ExportToDDLWizard;

/**
 * DDLWizardPage.
 *
 * @author modified by parapata
 */
public class ExportToDDLWizardPage extends ExportToFileWizardPage {

    private Button btnAlterTable;
    private Button btnComment;
    private Button btnDrop;
    private Button btnSchema;
    private Combo cmbEncoding;
    private Combo cmbLineSeparator;

    public ExportToDDLWizardPage(String fileName) {
        super(ExportToDDLWizardPage.class.getSimpleName(), fileName);
        setTitle(WIZARD_GENERATE_DDL_PAGE_TITLE.getValue());
        setDescription(WIZARD_GENERATE_DDL_PAGE_DESCRIPTION.getValue());
    }

    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);

        IDialogSettings setting = getDialogSettings();
        Composite composite = (Composite) getControl();

        // -------------
        UIUtils.createLabel(composite, LABEL_CHARACTER_ENCODING);
        cmbEncoding = new Combo(composite, SWT.READ_ONLY);
        SortedMap<String, Charset> charsets = Charset.availableCharsets();
        charsets.values().forEach(charset -> {
            cmbEncoding.add(charset.displayName());
        });
        cmbEncoding.setText(setting.get(ExportToDDLWizard.ENCODING));
        cmbEncoding.setLayoutData(UIUtils.createGridData(2));
        cmbEncoding.addModifyListener(event -> doValidate());

        // -------------
        UIUtils.createLabel(composite, LABEL_LINE_SEPARATOR);
        cmbLineSeparator = new Combo(composite, SWT.READ_ONLY);
        Arrays.asList(LineSeparatorCode.values()).forEach(code -> {
            cmbLineSeparator.add(code.name());
            cmbLineSeparator.setData(code.name(), code.getValue());
        });
        cmbLineSeparator.setText(setting.get(ExportToDDLWizard.LINE_SEPARATOR));
        cmbLineSeparator.setLayoutData(UIUtils.createGridData(2));

        // -------------
        Group group = new Group(composite, SWT.PUSH);
        group.setText(LABEL_OPTION.getValue());
        group.setLayoutData(UIUtils.createGridData(4));
        group.setLayout(new FillLayout(SWT.VIRTUAL));

        // -------------
        btnSchema = new Button(group, SWT.CHECK);
        btnSchema.setText(WIZARD_GENERATE_DDL_CHECK_SCHEMA.getValue());
        btnSchema.setLayoutData(UIUtils.createGridData(2));
        btnSchema.setSelection(setting.getBoolean(ExportToDDLWizard.SCHEMA));

        // -------------
        btnDrop = new Button(group, SWT.CHECK);
        btnDrop.setText(WIZARD_GENERATE_DDL_CHECK_DROP.getValue());
        btnDrop.setLayoutData(UIUtils.createGridData(2));
        btnDrop.setSelection(setting.getBoolean(ExportToDDLWizard.DROP));

        // -------------
        btnAlterTable = new Button(group, SWT.CHECK);
        btnAlterTable.setText(WIZARD_GENERATE_DDL_CHECK_ALTER_TABLE.getValue());
        btnAlterTable.setLayoutData(UIUtils.createGridData(2));
        btnAlterTable.setSelection(setting.getBoolean(ExportToDDLWizard.ALTER_TABLE));

        // -------------
        btnComment = new Button(group, SWT.CHECK);
        btnComment.setText(WIZARD_GENERATE_DDL_CHECK_COMMENT.getValue());
        btnComment.setLayoutData(UIUtils.createGridData(2));
        btnComment.setSelection(setting.getBoolean(ExportToDDLWizard.COMMENT));
    }

    @Override
    protected void doValidate() {
        super.doValidate();
        if (!isSupportedEncoding(cmbEncoding.getText())) {
            setErrorMessage(ERROR_WIZARD_GENERATE_DDL_ERROR_ENCODING.getValue());
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
