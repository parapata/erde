package io.github.erde.wizard.page;

import java.util.ArrayList;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.internal.ui.wizards.TypedElementSelectionValidator;
import org.eclipse.jdt.internal.ui.wizards.buildpaths.FolderSelectionDialog;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import io.github.erde.Activator;
import io.github.erde.IMessages;
import io.github.erde.core.util.UIUtils;
import io.github.erde.editor.diagram.model.BaseEntityModel;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;
import io.github.erde.editor.persistent.ERDiagramSerializer;

/**
 * ImportFromDiagramWizardPage.
 *
 * @author modified by parapata
 */
public class ImportFromDiagramWizardPage extends WizardPage implements IMessages {

    private IFile self;

    private RootModel root;
    private RootModel selectedRootModel;
    private Text file;
    private List list;

    public ImportFromDiagramWizardPage(IFile self, RootModel root) {
        super(resource.getString("wizard.importFromDiagram.title"));
        setTitle(resource.getString("wizard.importFromDiagram.title"));
        setMessage(resource.getString("wizard.importFromDiagram.message"));
        this.self = self;
        this.root = root;
    }

    @Override
    public void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NULL);
        composite.setLayout(new GridLayout(3, false));
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        UIUtils.createLabel(composite, "wizard.importFromDiagram.erdFile");
        file = new Text(composite, SWT.BORDER);
        file.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        file.setEditable(false);

        Button browse = new Button(composite, SWT.PUSH);
        browse.setText(resource.getString("button.browse"));
        browse.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                selectFile();
            }
        });

        UIUtils.createLabel(composite, "wizard.new.import.tables");
        list = new List(composite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
        list.setLayoutData(new GridData(GridData.FILL_BOTH));

        list.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String[] selectedTableNames = list.getSelection();
                for (String tableName : selectedTableNames) {
                    setErrorMessage(null);
                    setPageComplete(true);
                }
            }
        });

        setControl(composite);
    }

    private void selectFile() {
        try {
            IResource init = null;
            if (!file.getText().equals("")) {
                init = getSelectedFile();
            }
            Class<?>[] acceptedClasses = new Class<?>[] { IFile.class };
            ISelectionStatusValidator validator = new TypedElementSelectionValidator(acceptedClasses, false);

            IWorkspaceRoot wsroot = ResourcesPlugin.getWorkspace().getRoot();
            FolderSelectionDialog dialog = new FolderSelectionDialog(getShell(), new WorkbenchLabelProvider(),
                    new WorkbenchContentProvider());

            ViewerFilter filter = new ViewerFilter() {
                @Override
                public boolean select(Viewer viewer, Object parentElement, Object element) {
                    if (element instanceof IContainer) {
                        return true;
                    }
                    return element instanceof IFile && !element.equals(self)
                            && ((IFile) element).getName().endsWith(Activator.EXTENSION_ERDE);
                }
            };

            dialog.setTitle(resource.getString("wizard.generate.browse.title"));
            dialog.setMessage(resource.getString("wizard.generate.browse.message"));
            dialog.addFilter(filter);
            dialog.setInput(wsroot);
            dialog.setValidator(validator);
            dialog.setInitialSelection(init);
            if (dialog.open() == Window.OK) {
                IFile selectedFile = (IFile) dialog.getFirstResult();
                file.setText(selectedFile.getFullPath().toString());

                // TODO
                selectedRootModel = new ERDiagramSerializer().read(selectedFile.getContents());
                list.removeAll();
                for (BaseEntityModel entity : selectedRootModel.getChildren()) {
                    if (entity instanceof TableModel) {
                        list.add(((TableModel) entity).getPhysicalName());
                    }
                }
            }

        } catch (Exception e) {
            Activator.logException(e);
        }
    }

    public IFile getSelectedFile() {
        String outputDir = file.getText();
        IWorkspaceRoot wsroot = ResourcesPlugin.getWorkspace().getRoot();
        return (IFile) wsroot.findMember(outputDir);
    }

    public TableModel[] getSelectedTableModel() {
        if (selectedRootModel == null) {
            return new TableModel[0];
        }
        java.util.List<TableModel> result = new ArrayList<>();
        for (String tableName : list.getSelection()) {
            TableModel table = selectedRootModel.getTable(tableName);
            table.setSchema(selectedRootModel.getJdbcSchema());
            result.add(selectedRootModel.getTable(tableName));
        }
        return result.toArray(new TableModel[result.size()]);
    }
}
