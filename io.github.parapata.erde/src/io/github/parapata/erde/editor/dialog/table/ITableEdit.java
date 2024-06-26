package io.github.parapata.erde.editor.dialog.table;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.github.parapata.erde.core.util.StringUtils;
import io.github.parapata.erde.dialect.IDialect;
import io.github.parapata.erde.editor.diagram.model.BaseConnectionModel;
import io.github.parapata.erde.editor.diagram.model.ColumnModel;
import io.github.parapata.erde.editor.diagram.model.IndexModel;
import io.github.parapata.erde.editor.diagram.model.RelationshipMappingModel;
import io.github.parapata.erde.editor.diagram.model.RelationshipModel;

/**
 * ITableEdit.
 *
 * @author modified by parapata
 */
public interface ITableEdit {
    String getPhysicalName();

    void setPhysicalName(String physicalName);

    String getLogicalName();

    void setLogicalName(String logicalName);

    String getDescription();

    void setDescription(String description);

    List<ColumnModel> getColumns();

    List<IndexModel> getIndices();

    default IDialect getDialect() {
        throw new UnsupportedClassVersionError();
    }

    List<RelationshipModel> getForeignKeyConnections();

    default boolean isForeignkey(String id) {
        for (RelationshipModel conn : getForeignKeyConnections()) {
            for (RelationshipMappingModel item : conn.getMappings()) {
                if (StringUtils.equals(id, item.getForeignKey().getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    List<RelationshipModel> getReferenceKeyConnections();

    default boolean isReferenceKey(String id) {
        for (RelationshipModel conn : getReferenceKeyConnections()) {
            for (RelationshipMappingModel item : conn.getMappings()) {
                if (StringUtils.equals(id, item.getReferenceKey().getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    default List<RelationshipModel> toRelationshipConnections(List<BaseConnectionModel> connections) {
        return connections.stream()
                .filter(conn -> (conn instanceof RelationshipModel))
                .map(item -> (RelationshipModel) item)
                .collect(Collectors.toList());
    }

    default void updateForeignkey(List<ColumnModel> foreignKeys) {

        List<BaseConnectionModel> connections = getForeignKeyConnections()
                .stream()
                .filter(conn -> (conn instanceof RelationshipModel))
                .collect(Collectors.toList());

        connections.forEach(conn -> {
            updateForeignkey(((RelationshipModel) conn),
                    getColumns().stream()
                            .filter(column -> isForeignkey(column.getId()))
                            .collect(Collectors.toList()));
        });
    }

    private void updateForeignkey(RelationshipModel relationship, List<ColumnModel> foreignKeyColumns) {
        List<RelationshipMappingModel> newMappings = new ArrayList<>();
        for (RelationshipMappingModel mapping : relationship.getMappings()) {
            for (ColumnModel column : foreignKeyColumns) {
                if (StringUtils.equals(column.getId(), mapping.getForeignKey().getId())) {
                    RelationshipMappingModel newMapping = new RelationshipMappingModel();
                    newMapping.setReferenceKey(mapping.getReferenceKey());
                    newMapping.setForeignKey(column);
                    newMappings.add(newMapping);
                    break;
                }
            }
        }
        if (!newMappings.isEmpty()) {
            relationship.setMappings(newMappings);
        }
    }

    default void updateReferenceKey(List<ColumnModel> referenceKey) {

        List<BaseConnectionModel> connections = getReferenceKeyConnections()
                .stream()
                .filter(conn -> (conn instanceof RelationshipModel))
                .collect(Collectors.toList());

        connections.forEach(conn -> {
            updateReferenceKey(((RelationshipModel) conn),
                    getColumns().stream()
                            .filter(column -> isReferenceKey(column.getId()))
                            .collect(Collectors.toList()));
        });
    }

    private void updateReferenceKey(RelationshipModel relationship, List<ColumnModel> referenceKeyColumns) {
        List<RelationshipMappingModel> newMappings = new ArrayList<>();
        for (RelationshipMappingModel mapping : relationship.getMappings()) {
            for (ColumnModel column : referenceKeyColumns) {
                if (StringUtils.equals(column.getId(), mapping.getReferenceKey().getId())) {
                    RelationshipMappingModel newMapping = new RelationshipMappingModel();
                    newMapping.setReferenceKey(column);
                    newMapping.setForeignKey(mapping.getForeignKey());
                    newMappings.add(newMapping);
                    break;
                }
            }
        }
        if (!newMappings.isEmpty()) {
            relationship.setMappings(newMappings);
        }
    }
}
