package io.github.erde.wizard.page;

import static io.github.erde.Resource.*;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import io.github.erde.core.util.swt.UIUtils;
import io.github.erde.dialect.DialectProvider;

/**
 * ChangeDialectWizardPage.
 *
 * @author modified by parapata
 */
public class ChangeDialectWizardPage extends WizardPage {

    private Combo cmbDialectName;
    private String dialectName;

    public ChangeDialectWizardPage(String dialectName) {
        super(ChangeDialectWizardPage.class.getSimpleName());
        setTitle(WIZARD_CHANGEDB_PAGE_TITLE.getValue());
        setDescription(WIZARD_CHANGEDB_PAGE_DESCRIPTION.getValue());

        this.dialectName = dialectName;
    }

    @Override
    public void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NULL);
        composite.setLayout(new GridLayout(2, false));
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        UIUtils.createLabel(composite, LABEL_DATABASE);

        cmbDialectName = new Combo(composite, SWT.READ_ONLY);
        for (String dialectName : DialectProvider.getDialectNames()) {
            cmbDialectName.add(dialectName);
        }

        cmbDialectName.setText(dialectName);

        setControl(composite);
    }

    public String getDialectName() {
        return this.cmbDialectName.getText();
    }
}
