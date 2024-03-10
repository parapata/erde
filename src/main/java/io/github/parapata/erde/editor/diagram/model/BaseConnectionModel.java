package io.github.parapata.erde.editor.diagram.model;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * BaseConnectionModel.
 *
 * @author modified by parapata
 */
public abstract class BaseConnectionModel extends BaseModel implements IPropertySource {

    private static final long serialVersionUID = 1L;

    private BaseEntityModel source;
    private BaseEntityModel target;

    public void attachSource() {
        if (!source.getModelSourceConnections().contains(this)) {
            source.addSourceConnection(this);
        }
    }

    public void attachTarget() {
        if (!target.getModelTargetConnections().contains(this)) {
            target.addTargetConnection(this);
        }
    }

    public void detachSource() {
        if (source != null) {
            source.removeSourceConnection(this);
        }
    }

    public void detachTarget() {
        if (target != null) {
            target.removeTargetConnection(this);
        }
    }

    public BaseEntityModel getSource() {
        return source;
    }

    public BaseEntityModel getTarget() {
        return target;
    }

    public void setSource(BaseEntityModel model) {
        source = model;
    }

    public void setTarget(BaseEntityModel model) {
        target = model;
    }

    @Override
    public Object getEditableValue() {
        return this;
    }

    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        return new IPropertyDescriptor[0];
    }

    @Override
    public Object getPropertyValue(Object id) {
        return null;
    }

    @Override
    public boolean isPropertySet(Object id) {
        return false;
    }

    @Override
    public void resetPropertyValue(Object id) {
    }

    @Override
    public void setPropertyValue(Object id, Object value) {
    }
}
