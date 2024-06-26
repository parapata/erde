package io.github.parapata.erde.wizard.page;

import static io.github.parapata.erde.Resource.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

import io.github.parapata.erde.ErdePlugin;
import io.github.parapata.erde.dialect.DialectProvider;
import io.github.parapata.erde.editor.diagram.figure.connection.decoration.DecorationFactory;
import io.github.parapata.erde.editor.persistent.diagram.ErdeXmlModel;
import io.github.parapata.erde.editor.persistent.diagram.ObjectFactory;
import jakarta.xml.bind.JAXB;

/**
 * NewDiagramWizardPage.
 *
 * @author modified by parapata
 */
public class NewDiagramWizardPage extends WizardNewFileCreationPage {

    private static final String NEW_FILE_NAME = String.format("newfile%s", ErdePlugin.EXTENSION_ERDE);

    private Combo products;
    private Text schema;

    public NewDiagramWizardPage(IStructuredSelection selection) {
        super(ImportFromJDBCWizardPage2.class.getSimpleName(), selection);
        setTitle(WIZARD_NEW_ERDE_PAGE_TITLE.getValue());
        setDescription(WIZARD_NEW_ERDE_PAGE_DESCRIPTION.getValue());

        setFileName(NEW_FILE_NAME);
    }

    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);
        GridLayout layout = new GridLayout(2, false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;

        Composite composite = new Composite((Composite) getControl(), SWT.NONE);
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Label productLabel = new Label(composite, SWT.NONE);
        productLabel.setText(LABEL_DATABASE.getValue());

        products = new Combo(composite, SWT.READ_ONLY);
        List<String> dialectNames = DialectProvider.getDialectNames();

        dialectNames.forEach(dialectName -> {
            products.add(dialectName);
        });

        products.setText(dialectNames.get(0));

        composite.setLayout(layout);
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        Label schemaLabel = new Label(composite, SWT.NONE);
        schemaLabel.setText(LABEL_SCHEMA.getValue());

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
            if (!fileName.endsWith(ErdePlugin.EXTENSION_ERDE)) {
                setErrorMessage(ERROR_ERDE_EXTENSION.getValue());
                valid = false;
            }
        }
        if (valid) {
            setMessage("エラーメッセージ");
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
        erde.setDiagram(factory.createDiagramXmlModel());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JAXB.marshal(erde, out);
        return new ByteArrayInputStream(out.toByteArray());
    }
}
