package io.github.erde.editor.action;

import static io.github.erde.Resource.*;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.Action;

import io.github.erde.editor.ERDiagramInformationControl;

/**
 * QuickOutlineAction.
 *
 * @author modified by parapata
 */
public class QuickOutlineAction extends Action implements IERDAction {

    public QuickOutlineAction() {
        super();
        setId(IERDAction.QUICK_OUTLINE);
        setText(ACTION_QUICK_OUTLINE.getValue());
    }

    @Override
    public void run() {
        GraphicalViewer viewer = getGraphicalViewer();
        ERDiagramInformationControl quickOutline = new ERDiagramInformationControl(
                viewer.getControl().getShell(),
                viewer);
        quickOutline.setVisible(true);
    }
}
