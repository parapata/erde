package io.github.parapata.erde.core.util;

import java.util.List;

import io.github.parapata.erde.editor.diagram.model.BaseConnectionModel;
import io.github.parapata.erde.editor.diagram.model.ColumnModel;
import io.github.parapata.erde.editor.diagram.model.RelationshipMappingModel;
import io.github.parapata.erde.editor.diagram.model.RelationshipModel;
import io.github.parapata.erde.editor.diagram.model.RootModel;
import io.github.parapata.erde.editor.diagram.model.TableModel;

/**
 * Provides utilities for model operating.
 *
 * @author modified by parapata
 */
public class ModelUtils {

    public static void importOrReplaceTable(RootModel rootModel, TableModel oldTable, TableModel newTable) {
        stripConnections(newTable.getModelSourceConnections());
        stripConnections(newTable.getModelTargetConnections());

        if (oldTable != null) {
            rootModel.removeChild(oldTable);
            for (BaseConnectionModel conn : oldTable.getModelSourceConnections()) {
                if (conn instanceof RelationshipModel) {
                    RelationshipModel fk = (RelationshipModel) conn;
                    List<RelationshipMappingModel> mappings = fk.getMappings();
                    for (RelationshipMappingModel mapping : mappings) {
                        ColumnModel oldColumn = mapping.getReferenceKey();
                        ColumnModel newColumn = newTable.getColumn(oldColumn.getPhysicalName());
                        mapping.setReferenceKey(newColumn);
                    }
                    fk.setMappings(mappings);
                }
                conn.setSource(newTable);
                newTable.addSourceConnection(conn);
            }

            for (BaseConnectionModel conn : oldTable.getModelTargetConnections()) {
                if (conn instanceof RelationshipModel) {
                    RelationshipModel fk = (RelationshipModel) conn;
                    List<RelationshipMappingModel> mappings = fk.getMappings();
                    for (RelationshipMappingModel mapping : mappings) {
                        ColumnModel oldColumn = mapping.getForeignKey();
                        ColumnModel newColumn = newTable.getColumn(oldColumn.getPhysicalName());
                        mapping.setForeignKey(newColumn);
                    }
                    fk.setMappings(mappings);
                }
                conn.setTarget(newTable);
                newTable.addTargetConnection(conn);
            }
            newTable.setConstraint(oldTable.getConstraint());
        }

        rootModel.addChild(newTable);
    }

    public static void stripConnections(List<BaseConnectionModel> conns) {
        for (BaseConnectionModel conn : conns.toArray(new BaseConnectionModel[conns.size()])) {
            conn.getSource().removeSourceConnection(conn);
            conn.getTarget().removeTargetConnection(conn);
        }
    }

}
