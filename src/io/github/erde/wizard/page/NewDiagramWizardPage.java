package io.github.erde.wizard.page;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXB;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

import io.github.erde.Activator;
import io.github.erde.Resource;
import io.github.erde.dialect.DialectProvider;
import io.github.erde.editor.diagram.figure.connection.decoration.DecorationFactory;
import io.github.erde.editor.persistent.diagram.ErdeXmlModel;
import io.github.erde.editor.persistent.diagram.ObjectFactory;

/**
 * NewDiagramWizardPage.
 *
 * @author modified by parapata
 */
public class NewDiagramWizardPage extends WizardNewFileCreationPage {

    private static final String NEW_FILE_NAME = String.format("newfile%s", Activator.EXTENSION_ERDE);

    private Combo products;
    private Text schema;

    public NewDiagramWizardPage(IStructuredSelection selection) {

        super(Resource.WIZARD_NEW_ERD_TITLE.getValue(), selection);
        setTitle(Resource.WIZARD_NEW_ERD_TITLE.getValue());
        setFileName(NEW_FILE_NAME);
    }

    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);
        GridLayout layout = new GridLayout(2, false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;

        Composite composite = new Composite((Composite) getControl(), SWT.NULL);
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Label productLabel = new Label(composite, SWT.NULL);
        productLabel.setText(Resource.WIZARD_NEW_ERD_PRODUCT.getValue());

        products = new Combo(composite, SWT.READ_ONLY);
        List<String> dialectNames = DialectProvider.getDialectNames();

        dialectNames.forEach(dialectName -> {
            products.add(dialectName);
        });

        products.setText(dialectNames.get(0));

        composite.setLayout(layout);
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        Label schemaLabel = new Label(composite, SWT.NULL);
        schemaLabel.setText(Resource.WIZARD_NEW_ERD_SCHEMA.getValue());

        schema = new Text(composite, SWT.BORDER);
        schema.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        validatePage();
    }

    @Override
    protected void createLinkTarget() {
    }

    @Override
    protected boolean validatePage() {
        boolean valid = super.validatePage();
        if (valid) {
            String fileName = getFileName();
            if (!fileName.endsWith(Activator.EXTENSION_ERDE)) {
                setErrorMessage(Resource.ERR_ERD_EXTENSION.getValue());
                valid = false;
            }
        }
        if (valid) {
            setMessage(Resource.WIZARD_NEW_ERD_MESSAGE.getValue());
        }
        return valid;
    }

    @Override
    protected InputStream getInitialContents() {
        ObjectFactory factory = new ObjectFactory();
        ErdeXmlModel erde = factory.createErdeXmlModel();
        erde.setDialectName(products.getText());
        erde.setSchemaName(schema.getText());
        erde.setLowerCase(false);
        erde.setLogicalMode(false);
        erde.setIncludeView(false);
        erde.setNotation(DecorationFactory.NOTATION_IE);
        erde.setDbSettings(factory.createDbSettingsXmlModel());
        erde.setDiagram(factory.createDiagramXmlModel());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JAXB.marshal(erde, out);
        return new ByteArrayInputStream(out.toByteArray());
    }
}
