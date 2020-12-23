package io.github.erde.editor.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import io.github.erde.IMessages;
import io.github.erde.core.util.NameConverter.DictionaryEntry;
import io.github.erde.core.util.UIUtils;

/**
 * EntryEditDialog.
 *
 * @author modified by parapata
 */
public class EntryEditDialog extends Dialog implements IMessages {

    private Text txtLogicalName;
    private Text txtPhysicalName;
    private Button btnPartMatch;
    private DictionaryEntry element;

    public EntryEditDialog(Shell parentShell) {
        super(parentShell);
        setShellStyle(getShellStyle() | SWT.RESIZE);
    }

    public EntryEditDialog(Shell parentShell, DictionaryEntry element) {
        super(parentShell);
        this.element = element;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        getShell().setText(getResource("dialog.dictionary.title"));

        Composite composite = new Composite(parent, SWT.NULL);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        composite.setLayout(new GridLayout(2, false));

        UIUtils.createLabel(composite, "label.physicalName");
        txtPhysicalName = new Text(composite, SWT.BORDER);
        txtPhysicalName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        if (element != null) {
            txtPhysicalName.setText(element.physicalName);
        }

        UIUtils.createLabel(composite, "label.logicalName");
        txtLogicalName = new Text(composite, SWT.BORDER);
        if (element != null) {
            txtLogicalName.setText(element.logicalName);
        }
        txtLogicalName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        UIUtils.createLabel(composite, "label.partialMatch");
        btnPartMatch = new Button(composite, SWT.CHECK);
        if (element != null) {
            btnPartMatch.setSelection(element.partialMatch);
        }

        return composite;
    }

    @Override
    protected void okPressed() {
        if (txtPhysicalName.getText().length() == 0) {
            UIUtils.openAlertDialog(createMessage(getResource("error.required"),
                    getResource("label.physicalName")));
            return;
        }
        if (txtLogicalName.getText().length() == 0) {
            UIUtils.openAlertDialog(createMessage(getResource("error.required"),
                    getResource("label.logicalName")));
            return;
        }
        element = new DictionaryEntry(txtPhysicalName.getText(), txtLogicalName.getText(), btnPartMatch.getSelection());
        super.okPressed();
    }

    public DictionaryEntry getEntry() {
        return element;
    }
}
