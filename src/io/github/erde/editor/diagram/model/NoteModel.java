package io.github.erde.editor.diagram.model;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import io.github.erde.IMessages;

/**
 * This class has been ported from AmaterasUML.
 *
 * @author modified by parapata
 */
public class NoteModel extends BaseEntityModel implements IMessages {

    private static final long serialVersionUID = 1L;

    public static final String P_CONTENT = "p_content";

    private static final int DEFAULT_WIDTH = 120;
    private static final int DEFAULT_HEIGHT = 65;

    private String content;

    public NoteModel() {
        super();
    }

    public NoteModel(String id) {
        super(id);
    }

    @Override
    public boolean canSource(BaseConnectionModel conn) {
        if (conn instanceof RelationshipModel) {
            return false;
        }
        return true;
    }

    @Override
    public boolean canTarget(BaseConnectionModel conn) {
        if (conn instanceof RelationshipModel) {
            return false;
        }
        return true;
    }

    @Override
    public void setConstraint(Rectangle constraint) {
        if (constraint.width < DEFAULT_WIDTH) {
            constraint.width = DEFAULT_WIDTH;
        }
        if (constraint.height < DEFAULT_HEIGHT) {
            constraint.height = DEFAULT_HEIGHT;
        }
        super.setConstraint(constraint);
    }

    public String getContent() {
        return StringUtils.defaultString(content);
    }

    public void setContent(String content) {
        this.content = StringUtils.defaultString(content);
        firePropertyChange(P_CONTENT, null, this.content);
    }

    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        return new IPropertyDescriptor[] { new TextPropertyDescriptor(P_CONTENT, getResource("property.text")), };
    }

    @Override
    public Object getPropertyValue(Object id) {
        if (id.equals(P_CONTENT)) {
            return getContent();
        }
        return null;
    }

    @Override
    public boolean isPropertySet(Object id) {
        if (id.equals(P_CONTENT)) {
            return true;
        }
        return false;
    }

    @Override
    public void setPropertyValue(Object id, Object value) {
        if (id.equals(P_CONTENT)) {
            setContent((String) value);
        }
    }

    @Override
    public NoteModel clone() {
        return SerializationUtils.clone(this);
    }
}
