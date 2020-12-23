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
 * ChangeDBTypeWizardPage.
 *
 * @author modified by parapata
 */
public class ChangeDBTypeWizardPage extends WizardPage implements IMessages {

    private Combo dbType;
    private String dbName;

    public ChangeDBTypeWizardPage(String dbName) {
        super("ChangeDBTypeWizardPage");
        setTitle(getResource("wizard.changedb.title"));
        setDescription(getResource("wizard.changedb.description"));
        this.dbName = dbName;
    }

    @Override
    public void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NULL);
        composite.setLayout(new GridLayout(2, false));
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        UIUtils.createLabel(composite, "wizard.changedb.databaseType");

        dbType = new Combo(composite, SWT.READ_ONLY);
        for (String dbName : DialectProvider.getDialectNames()) {
            dbType.add(dbName);
        }

        dbType.setText(dbName);

        setControl(composite);
    }

    public String getDbType() {
        return this.dbType.getText();
    }
}
