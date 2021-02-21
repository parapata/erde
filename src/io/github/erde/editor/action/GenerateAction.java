package io.github.erde.editor.action;

import static io.github.erde.Resource.*;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IFileEditorInput;

import io.github.erde.editor.ERDiagramEditor;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.generate.IGenerator;

/**
 * GenerateAction.
 *
 * @author modified by parapata
 */
public class GenerateAction extends Action implements IERDEAction {

    private IGenerator generater;
    private ERDiagramEditor editor;

    public GenerateAction(IGenerator generater, ERDiagramEditor editor) {
        super(generater.getGeneratorName());
        setId(GENERATE);
        setText(generater.getGeneratorName());
        this.generater = generater;
        this.editor = editor;
    }

    @Override
    public void run() {
        // force save
        if (editor.isDirty()) {
            if (MessageDialog.openConfirm(editor.getSite().getShell(),
                    DIALOG_CONFIRM_TITLE.getValue(),
                    MESSAGE_SAVE_BEFORE_EXECUTE.getValue())) {
                editor.doSave(new NullProgressMonitor());
            } else {
                return;
            }
        }

        GraphicalViewer viewer = getGraphicalViewer();
        RootModel root = (RootModel) viewer.getContents().getModel();
        IFileEditorInput input = (IFileEditorInput) editor.getEditorInput();
        generater.execute(input.getFile(), root, viewer);
    }
}
