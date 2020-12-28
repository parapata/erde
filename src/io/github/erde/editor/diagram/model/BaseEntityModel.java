package io.github.erde.editor.diagram.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * BaseEntityModel.
 *
 * @author modified by parapata
 */
public abstract class BaseEntityModel extends BaseModel implements IPropertySource {

    private static final long serialVersionUID = 1L;

    public static final String P_CONSTRAINT = "p_constraint";
    public static final String P_SOURCE_CONNECTION = "p_source_connection";
    public static final String P_TARGET_CONNECTION = "p_target_connection";

    private String id;
    private Rectangle constraint;
    private List<BaseConnectionModel> sourceConnections = new ArrayList<>();
    private List<BaseConnectionModel> targetConnections = new ArrayList<>();

    public BaseEntityModel() {
        generateId();
    }

    public BaseEntityModel(String id) {
        this.id = id;
    }

    public Rectangle getConstraint() {
        return constraint;
    }

    public void setConstraint(Rectangle constraint) {
        this.constraint = constraint;
        firePropertyChange(P_CONSTRAINT, null, constraint);
    }

    public boolean canSource(BaseConnectionModel conn) {
        return true;
    }

    public boolean canTarget(BaseConnectionModel conn) {
        return true;
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

    public void addSourceConnection(BaseConnectionModel connection) {
        sourceConnections.add(connection);
        firePropertyChange(P_SOURCE_CONNECTION, null, connection);
    }

    public void addTargetConnection(BaseConnectionModel connection) {
        targetConnections.add(connection);
        firePropertyChange(P_TARGET_CONNECTION, null, connection);
    }

    public List<BaseConnectionModel> getModelSourceConnections() {
        return sourceConnections;
    }

    public List<BaseConnectionModel> getModelTargetConnections() {
        return targetConnections;
    }

    public void removeSourceConnection(Object connection) {
        sourceConnections.remove(connection);
        firePropertyChange(P_SOURCE_CONNECTION, connection, null);
    }

    public void removeTargetConnection(Object connection) {
        targetConnections.remove(connection);
        firePropertyChange(P_TARGET_CONNECTION, connection, null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void generateId() {
        this.id = String.valueOf((System.nanoTime()));
    }

    @Override
    public BaseEntityModel clone() {
        return SerializationUtils.clone(this);
    }
}
