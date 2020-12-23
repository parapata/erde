package io.github.erde.editor.dialog.table.tabs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import io.github.erde.IMessages;
import io.github.erde.core.util.UIUtils;
import io.github.erde.editor.dialog.table.ITableEdit;

/**
 * DescriptionTabCreator.
 *
 * @author modified by parapata
 */
public class DescriptionTabCreator implements IMessages {

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
        tab.setText(getResource("label.description"));
        tab.setControl(composite);

        UIUtils.createLabel(composite, "dialog.table.description");
        txtTableDescription = new Text(composite, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
        txtTableDescription.setLayoutData(new GridData(GridData.FILL_BOTH));
        txtTableDescription.setText(tableEdit.getTableDescription());

        txtTableDescription.addModifyListener(event -> tableEdit.setTableDescription(txtTableDescription.getText()));
    }
}
