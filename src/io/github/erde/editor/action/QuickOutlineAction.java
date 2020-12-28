package io.github.erde.editor.action;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.Action;

import io.github.erde.editor.ERDiagramInformationControl;

/**
 * QuickOutlineAction.
 *
 * @author modified by parapata
 */
public class QuickOutlineAction extends Action implements IERDEAction {

    public QuickOutlineAction() {
        super();
        setId(IERDEAction.QUICK_OUTLINE);
        setText(getResource("action.quickOutline"));
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
