package io.github.parapata.erde.wizard;

import static io.github.parapata.erde.Resource.*;

import java.util.List;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.wizard.Wizard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.parapata.erde.core.util.JDBCConnection;
import io.github.parapata.erde.core.util.swt.UIUtils;
import io.github.parapata.erde.dialect.DialectProvider;
import io.github.parapata.erde.editor.diagram.editpart.command.ImportFromJDBCCommand;
import io.github.parapata.erde.editor.diagram.editpart.command.ImportFromOracleCommand;
import io.github.parapata.erde.editor.diagram.model.RootModel;
import io.github.parapata.erde.wizard.page.ImportFromJDBCWizardPage1;
import io.github.parapata.erde.wizard.page.ImportFromJDBCWizardPage2;

/**
 * ImportFromJDBCWizard.
 *
 * @author modified by parapata
 */
public class ImportFromJDBCWizard extends Wizard {

    private Logger logger = LoggerFactory.getLogger(ImportFromJDBCWizard.class);

    private GraphicalViewer viewer;
    private ImportFromJDBCWizardPage1 page1;
    private ImportFromJDBCWizardPage2 page2;

    public ImportFromJDBCWizard(GraphicalViewer viewer) {
        super();
        setWindowTitle(WIZARD_IMPORT_FROM_JDBC_DIALOG_TITLE.getValue());
        this.viewer = viewer;
        setNeedsProgressMonitor(true);
    }

    @Override
    public void addPages() {
        RootModel root = (RootModel) viewer.getContents().getModel();
        page1 = new ImportFromJDBCWizardPage1(root);
        addPage(page1);
        page2 = new ImportFromJDBCWizardPage2();
        addPage(page2);
    }

    @Override
    public boolean canFinish() {
        return page2.isPageComplete();
    }

    @Override
    public boolean performFinish() {
        try {
            JDBCConnection jdbcConn = page1.getJDBCConnection();
            List<String> tableNames = page2.getSelectionTables();

            CommandStack stack = viewer.getEditDomain().getCommandStack();
            RootModel root = (RootModel) viewer.getContents().getModel();

            DialectProvider provider = DialectProvider.valueOf(jdbcConn.getDialectProvider());
            if (DialectProvider.Oracle.equals(provider)) {
                stack.execute(new ImportFromOracleCommand(root, jdbcConn, tableNames));
            } else {
                stack.execute(new ImportFromJDBCCommand(root, jdbcConn, tableNames));
            }
            page1.setJDBCSetting();
        } catch (Exception e) {
            UIUtils.openAlertDialog(ERROR_DB_IMPORT);
            return false;
        }
        return true;
    }
}
