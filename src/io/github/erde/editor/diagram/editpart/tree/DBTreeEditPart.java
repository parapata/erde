package io.github.erde.editor.diagram.editpart.tree;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.gef.editparts.AbstractTreeEditPart;

import io.github.erde.editor.diagram.model.BaseEntityModel;
import io.github.erde.editor.diagram.model.BaseModel;

/**
 * DBTreeEditPart.
 *
 * @author modified by parapata
 */
public class DBTreeEditPart extends AbstractTreeEditPart implements PropertyChangeListener {

    @Override
    public void activate() {
        super.activate();
        if (getModel() instanceof BaseModel) {
            ((BaseModel) getModel()).addPropertyChangeListener(this);
        }
    }

    @Override
    public void deactivate() {
        super.deactivate();
        if (getModel() instanceof BaseModel) {
            ((BaseModel) getModel()).removePropertyChangeListener(this);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        String propName = event.getPropertyName();
        if (BaseEntityModel.P_SOURCE_CONNECTION.equals(propName)) {
            refreshChildren();
        }
    }
}
