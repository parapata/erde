package io.github.parapata.erde.editor.action;

import static io.github.parapata.erde.Resource.*;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.Action;

import io.github.parapata.erde.editor.ErdeDiagramInformationControl;

/**
 * QuickOutlineAction.
 *
 * @author modified by parapata
 */
public class QuickOutlineAction extends Action implements IErdeAction {

    public QuickOutlineAction() {
        super();
        setId(IErdeAction.QUICK_OUTLINE);
        setText(ACTION_QUICK_OUTLINE.getValue());
    }

    @Override
    public void run() {
        GraphicalViewer viewer = getGraphicalViewer();
        ErdeDiagramInformationControl quickOutline = new ErdeDiagramInformationControl(
                viewer.getControl().getShell(),
                viewer);
        quickOutline.setVisible(true);
    }
}
