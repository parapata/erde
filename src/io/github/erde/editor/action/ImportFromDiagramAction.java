package io.github.erde.editor.action;

import static io.github.erde.Resource.*;

import org.eclipse.core.resources.IFile;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IFileEditorInput;

import io.github.erde.core.util.swt.UIUtils;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.wizard.ImportFromDiagramWizard;

/**
 * Imports table models from other diagram.
 *
 * @author modified by parapata
 */
public class ImportFromDiagramAction extends Action implements IERDEAction {

    public ImportFromDiagramAction() {
        super();
        setId(IMPORT_FROM_DIAGRAM);
        setText(ACTION_IMPORT_FROM_DIAGRAM.getValue());
    }

    @Override
    public void run() {

        GraphicalViewer viewer = getGraphicalViewer();

        IFileEditorInput input = (IFileEditorInput) UIUtils.getActiveEditor().getEditorInput();
        IFile file = input.getFile();
        RootModel root = (RootModel) viewer.getContents().getModel();

        CommandStack commandStack = viewer.getEditDomain().getCommandStack();
        ImportFromDiagramWizard wizard = new ImportFromDiagramWizard(root, file, commandStack);
        WizardDialog dialog = new WizardDialog(viewer.getControl().getShell(), wizard);
        dialog.open();
    }
}
