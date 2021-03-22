package io.github.erde.wizard;

import static io.github.erde.Resource.*;

import java.util.List;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.wizard.Wizard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.erde.core.util.JDBCConnection;
import io.github.erde.core.util.swt.UIUtils;
import io.github.erde.editor.diagram.editpart.command.ImportFromJDBCCommand;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.wizard.page.ImportFromJDBCWizardPage1;
import io.github.erde.wizard.page.ImportFromJDBCWizardPage2;

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
        this.viewer = viewer;
        setNeedsProgressMonitor(true);
        setWindowTitle(WIZARD_NEW_IMPORT_TITLE.getValue());
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
    public boolean performFinish() {
        try {
            JDBCConnection jdbcConn = page1.getJDBCConnection();
            List<String> tableNames = page2.getSelectionTables();

            CommandStack stack = viewer.getEditDomain().getCommandStack();
            RootModel root = (RootModel) viewer.getContents().getModel();
            stack.execute(new ImportFromJDBCCommand(root, jdbcConn, tableNames));
            page1.setJDBCSetting();
        } catch (Exception e) {
            UIUtils.openAlertDialog(ERROR_DB_IMPORT);
            return false;
        }
        return true;
    }
}
