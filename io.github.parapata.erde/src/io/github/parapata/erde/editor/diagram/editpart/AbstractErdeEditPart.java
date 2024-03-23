package io.github.parapata.erde.editor.diagram.editpart;

import java.beans.PropertyChangeListener;

import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import io.github.parapata.erde.editor.diagram.model.BaseModel;

/**
 * AbstractErdeEditPart.
 *
 * @author modified by parapata
 */
abstract class AbstractErdeEditPart extends AbstractGraphicalEditPart
        implements PropertyChangeListener, IDoubleClickSupport {

    @Override
    public void activate() {
        super.activate();
        ((BaseModel) getModel()).addPropertyChangeListener(this);
    }

    @Override
    public void deactivate() {
        super.deactivate();
        ((BaseModel) getModel()).removePropertyChangeListener(this);
    }

    @Override
    public void doubleClicked() {
        return;
    }
}
