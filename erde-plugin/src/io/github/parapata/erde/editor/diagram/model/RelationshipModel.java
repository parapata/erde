package io.github.parapata.erde.editor.diagram.model;

import static io.github.parapata.erde.Resource.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import io.github.parapata.erde.core.util.StringUtils;

/**
 * RelationshipModel.
 *
 * @author modified by parapata
 */
public class RelationshipModel extends BaseConnectionModel {

    private static final long serialVersionUID = 1L;

    public static final String P_FOREIGN_KEY_NAME = "p_foreign_key_name";
    public static final String P_FOREIGN_KEY_MAPPING = "p_foreign_key_mapping";

    private String foreignKeyName;
    private Map<ColumnModel, ColumnModel> relationMap = new HashMap<>();

    private String onUpdateOption;
    private String onDeleteOption;
    private String sourceCardinality;
    private String targetCardinality;

    public void setForeignKeyName(String foreignKeyName) {
        this.foreignKeyName = StringUtils.defaultString(foreignKeyName);
        firePropertyChange(P_FOREIGN_KEY_NAME, null, foreignKeyName);
    }

    public String getForeignKeyName() {
        return StringUtils.defaultString(this.foreignKeyName);
    }

    public void setMappings(List<RelationshipMappingModel> mapping) {
        relationMap.clear();
        for (RelationshipMappingModel entry : mapping) {
            relationMap.put(entry.getReferenceKey(), entry.getForeignKey());
        }
        firePropertyChange(P_FOREIGN_KEY_MAPPING, null, mapping);
    }

    public List<RelationshipMappingModel> getMappings() {
        List<RelationshipMappingModel> list = new ArrayList<>();
        relationMap.forEach((key, value) -> {
            list.add(new RelationshipMappingModel(key, value));
        });
        return list;
    }

    public String getOnUpdateOption() {
        return onUpdateOption;
    }

    public void setOnUpdateOption(String onUpdateOption) {
        this.onUpdateOption = onUpdateOption;
    }

    public String getOnDeleteOption() {
        return onDeleteOption;
    }

    public void setOnDeleteOption(String onDeleteOption) {
        this.onDeleteOption = onDeleteOption;
    }

    public String getSourceCardinality() {
        return sourceCardinality;
    }

    public void setSourceCardinality(String sourceCardinality) {
        this.sourceCardinality = sourceCardinality;
    }

    public String getTargetCardinality() {
        return targetCardinality;
    }

    public void setTargetCardinality(String targetCardinality) {
        this.targetCardinality = targetCardinality;
    }

    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        List<IPropertyDescriptor> descriptoes = new ArrayList<>();
        descriptoes.add(new TextPropertyDescriptor(P_FOREIGN_KEY_NAME, LABEL_FOREIGN_KEY_NAME.getValue()));
        return descriptoes.toArray(new IPropertyDescriptor[descriptoes.size()]);
    }

    @Override
    public Object getPropertyValue(Object id) {
        if (id == P_FOREIGN_KEY_NAME) {
            return getForeignKeyName();
        }
        return null;
    }

    @Override
    public boolean isPropertySet(Object id) {
        if (id == P_FOREIGN_KEY_NAME) {
            return true;
        }
        return false;
    }

    @Override
    public void setPropertyValue(Object id, Object value) {
        if (id == P_FOREIGN_KEY_NAME) {
            setForeignKeyName((String) value);
        }
    }

    @Override
    public TableModel getSource() {
        return (TableModel) super.getSource();
    }

    @Override
    public TableModel getTarget() {
        return (TableModel) super.getTarget();
    }
}
