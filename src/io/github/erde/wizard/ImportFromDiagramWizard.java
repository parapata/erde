package io.github.erde.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.wizard.Wizard;

import io.github.erde.Resource;
import io.github.erde.core.util.ModelUtils;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;
import io.github.erde.wizard.page.ImportFromDiagramWizardPage;

/**
 * ImportFromDiagramWizard.
 *
 * @author modified by parapata
 */
public class ImportFromDiagramWizard extends Wizard {

    private ImportFromDiagramWizardPage page;
    private RootModel model;
    private IFile file;
    private CommandStack stack;

    public ImportFromDiagramWizard(RootModel model, IFile file, CommandStack stack) {
        super();
        setNeedsProgressMonitor(true);
        setWindowTitle(Resource.WIZARD_NEW_IMPORT_TITLE.getValue());
        this.model = model;
        this.file = file;
        this.stack = stack;
    }

    @Override
    public void addPages() {
        page = new ImportFromDiagramWizardPage(file, model);
        addPage(page);
    }

    @Override
    public boolean performFinish() {
        TableModel[] selectedTables = page.getSelectedTableModel();
        //IFile file = page.getSelectedFile();

        stack.execute(new Command() {
            @Override
            public void execute() {
                for (TableModel newTable : selectedTables) {
                    TableModel oldTable = model.getTable(newTable.getPhysicalName());
                    ModelUtils.importOrReplaceTable(model, oldTable, newTable);
                }
            }

            @Override
            public boolean canUndo() {
                return false;
            }
        });

        return true;
    }
}
