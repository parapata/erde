package io.github.parapata.erde.editor.action;

import static io.github.parapata.erde.Resource.*;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IFileEditorInput;

import io.github.parapata.erde.editor.ErdeDiagramEditor;
import io.github.parapata.erde.editor.diagram.model.RootModel;
import io.github.parapata.erde.generate.IGenerator;

/**
 * GenerateAction.
 *
 * @author modified by parapata
 */
public class GenerateAction extends Action implements IErdeAction {

    private IGenerator generater;
    private ErdeDiagramEditor editor;

    public GenerateAction(IGenerator generater, ErdeDiagramEditor editor) {
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
                    INFO_SAVE_BEFORE_EXECUTE.getValue())) {
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
