package io.github.erde.wizard.page;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import io.github.erde.IMessages;
import io.github.erde.core.util.UIUtils;
import io.github.erde.dialect.DialectProvider;

/**
 * ChangeDialectWizardPage.
 *
 * @author modified by parapata
 */
public class ChangeDialectWizardPage extends WizardPage implements IMessages {

    private Combo cmbDialectName;
    private String dialectName;

    public ChangeDialectWizardPage(String dialectName) {
        super("ChangeDialectWizardPage");
        setTitle(getResource("wizard.changedb.title"));
        setDescription(getResource("wizard.changedb.description"));
        this.dialectName = dialectName;
    }

    @Override
    public void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NULL);
        composite.setLayout(new GridLayout(2, false));
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        UIUtils.createLabel(composite, "wizard.changedb.databaseType");

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