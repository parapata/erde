package io.github.erde.editor.diagram.model;

import io.github.erde.IMessages;

/**
 * RelationshipMappingModel.
 *
 * @author modified by parapata
 */
public class RelationshipMappingModel implements IMessages {

    private ColumnModel referenceKey;
    private ColumnModel foreignKey;

    public RelationshipMappingModel() {
        return;
    }

    public RelationshipMappingModel(ColumnModel referenceKey, ColumnModel foreignKey) {
        this.referenceKey = referenceKey;
        this.foreignKey = foreignKey;
    }

    public ColumnModel getReferenceKey() {
        return referenceKey;
    }

    public void setReferenceKey(ColumnModel referenceKey) {
        this.referenceKey = referenceKey;
    }

    public ColumnModel getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(ColumnModel foreignKey) {
        this.foreignKey = foreignKey;
    }

    public String getDisplayString(boolean logicalMode) {
        StringBuilder sb = new StringBuilder();
        if (logicalMode) {
            if (getReferenceKey() == null) {
                sb.append(getResource("label.undef"));
            } else {
                sb.append(getReferenceKey().getLogicalName());
            }
            sb.append("=");
            if (getForeignKey() == null) {
                sb.append(getResource("label.undef"));
            } else {
                sb.append(getForeignKey().getLogicalName());
            }
        } else {
            if (getReferenceKey() == null) {
                sb.append(getResource("label.undef"));
            } else {
                sb.append(getReferenceKey().getPhysicalName());
            }
            sb.append("=");
            if (getForeignKey() == null) {
                sb.append(getResource("label.undef"));
            } else {
                sb.append(getForeignKey().getPhysicalName());
            }
        }
        return sb.toString();
    }
}
