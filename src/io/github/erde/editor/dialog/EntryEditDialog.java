package io.github.erde.editor.dialog;

import static io.github.erde.Resource.*;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import io.github.erde.ERDPlugin;
import io.github.erde.ICON;
import io.github.erde.Resource;
import io.github.erde.core.util.DictionaryEntry;
import io.github.erde.core.util.swt.UIUtils;

/**
 * EntryEditDialog.
 *
 * @author modified by parapata
 */
public class EntryEditDialog extends Dialog {

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
        Shell shell = getShell();
        shell.setText(DIALOG_DICTIONARY_TITLE.getValue());
        shell.setImage(ERDPlugin.getImage(ICON.TABLE.getPath()));

        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        composite.setLayout(new GridLayout(2, false));

        UIUtils.createLabel(composite, Resource.LABEL_PHYSICAL_NAME);
        txtPhysicalName = new Text(composite, SWT.BORDER);
        txtPhysicalName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        if (element != null) {
            txtPhysicalName.setText(element.physicalName);
        }

        UIUtils.createLabel(composite, LABEL_LOGICAL_TABLE_NAME);
        txtLogicalName = new Text(composite, SWT.BORDER);
        if (element != null) {
            txtLogicalName.setText(element.logicalName);
        }
        txtLogicalName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        UIUtils.createLabel(composite, LABEL_PARTIAL_MATCH);
        btnPartMatch = new Button(composite, SWT.CHECK);
        if (element != null) {
            btnPartMatch.setSelection(element.partialMatch);
        }

        return composite;
    }

    @Override
    protected void okPressed() {
        if (txtPhysicalName.getText().isEmpty()) {
            UIUtils.openAlertDialog(ERROR_REQUIRED, LABEL_PHYSICAL_NAME.getValue());
            return;
        }
        if (txtLogicalName.getText().isEmpty()) {
            UIUtils.openAlertDialog(ERROR_REQUIRED, LABEL_LOGICAL_TABLE_NAME.getValue());
            return;
        }
        element = new DictionaryEntry(txtPhysicalName.getText(), txtLogicalName.getText(), btnPartMatch.getSelection());
        super.okPressed();
    }

    public DictionaryEntry getEntry() {
        return element;
    }
}
