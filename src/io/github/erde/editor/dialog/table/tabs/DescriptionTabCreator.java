package io.github.erde.editor.dialog.table.tabs;

import static io.github.erde.Resource.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import io.github.erde.core.util.UIUtils;
import io.github.erde.editor.dialog.table.ITableEdit;

/**
 * DescriptionTabCreator.
 *
 * @author modified by parapata
 */
public class DescriptionTabCreator {

    private ITableEdit tableEdit;
    private Text txtTableDescription;

    public DescriptionTabCreator(ITableEdit tableEdit) {
        this.tableEdit = tableEdit;
    }

    public void create(TabFolder tabFolder) {
        Composite composite = new Composite(tabFolder, SWT.NULL);
        composite.setLayout(new GridLayout(1, false));
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        TabItem tab = new TabItem(tabFolder, SWT.NULL);
        tab.setText(LABEL_DESCRIPTION.getValue());
        tab.setControl(composite);

        UIUtils.createLabel(composite, DIALOG_TABLE_DESCRIPTION);
        txtTableDescription = new Text(composite, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
        txtTableDescription.setLayoutData(new GridData(GridData.FILL_BOTH));
        txtTableDescription.setText(tableEdit.getDescription());

        txtTableDescription.addModifyListener(event -> tableEdit.setDescription(txtTableDescription.getText()));
    }
}
