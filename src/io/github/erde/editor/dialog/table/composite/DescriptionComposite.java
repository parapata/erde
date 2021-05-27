package io.github.erde.editor.dialog.table.composite;

import static io.github.erde.Resource.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import io.github.erde.core.util.swt.UIUtils;
import io.github.erde.editor.dialog.table.ITableEdit;

/**
 * DescriptionComposite.
 *
 * @author modified by parapata
 */
public class DescriptionComposite extends Composite {

    private Text txtTableDescription;

    public DescriptionComposite(Composite parent, ITableEdit tableEdit) {
        super(parent, SWT.NONE);
        create(tableEdit);
    }

    private void create(ITableEdit tableEdit) {
        setLayout(new GridLayout(1, false));
        setLayoutData(new GridData(GridData.FILL_BOTH));

        UIUtils.createLabel(this, LABEL_DESCRIPTION);
        txtTableDescription = new Text(this, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
        txtTableDescription.setLayoutData(new GridData(GridData.FILL_BOTH));
        txtTableDescription.setText(tableEdit.getDescription());

        txtTableDescription.addModifyListener(event -> tableEdit.setDescription(txtTableDescription.getText()));
    }
}
