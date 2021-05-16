package io.github.erde.editor.dialog.table.tabs;

import static io.github.erde.Resource.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import io.github.erde.core.util.swt.UIUtils;
import io.github.erde.editor.dialog.table.ITableEdit;

/**
 * DescriptionTabCreator.
 *
 * @author modified by parapata
 */
public class DescriptionTab extends Composite {

    private Text txtTableDescription;

    public DescriptionTab(ITableEdit tableEdit, TabFolder tabFolder) {
        super(tabFolder, SWT.NONE);
        setLayout(new GridLayout(1, false));
        setLayoutData(new GridData(GridData.FILL_BOTH));

        TabItem tab = new TabItem(tabFolder, SWT.NONE);
        tab.setText(LABEL_DESCRIPTION.getValue());
        tab.setControl(this);
        create(tableEdit);
    }

    private void create(ITableEdit tableEdit) {
        UIUtils.createLabel(this, LABEL_DESCRIPTION);
        txtTableDescription = new Text(this, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
        txtTableDescription.setLayoutData(new GridData(GridData.FILL_BOTH));
        txtTableDescription.setText(tableEdit.getDescription());

        txtTableDescription.addModifyListener(event -> tableEdit.setDescription(txtTableDescription.getText()));
    }
}
