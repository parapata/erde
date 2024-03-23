package io.github.parapata.erde.editor.diagram.editpart;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.SWT;

/**
 * This class has been ported from AmaterasUML.
 *
 * @author modified by parapata
 */
public class NoteConnectionEditPart extends AbstractErdeConnectionEditPart {

    @Override
    protected void createEditPolicies() {
        super.createEditPolicies();
        removeEditPolicy(EditPolicy.DIRECT_EDIT_ROLE);
    }

    @Override
    protected IFigure createFigure() {
        PolylineConnection connection = new PolylineConnection();
        connection.setLineStyle(SWT.LINE_DASH);
        return connection;
    }
}
