package io.github.erde.dialect;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.github.erde.editor.diagram.model.BaseConnectionModel;
import io.github.erde.editor.diagram.model.RelationshipModel;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;

/**
 * TableDependencyCalculator.
 *
 * @author modified by parapata
 */
public class TableDependencyCalculator {

    public static List<TableModel> getSortedTable(RootModel root) {
        List<TableModel> result = new ArrayList<>();
        for (TableModel table : root.getTables()) {
            addTableModel(result, table, null);
        }
        return result;
    }

    private static void addTableModel(List<TableModel> result, TableModel table, Set<TableModel> dependentModels) {

        // Dependent models provides circular reference protection.
        // Contains all models up the recursion stack
        if (dependentModels == null) {
            dependentModels = new HashSet<>();
        }

        // We might have been already added as dependency by earlier tables
        if (result.contains(table)) {
            return;
        }

        // First add my dependencies
        Set<TableModel> innerDependentModels = new HashSet<>(dependentModels);
        dependentModels.add(table);

        for (BaseConnectionModel conn : table.getModelSourceConnections()) {
            if (conn instanceof RelationshipModel) {
                RelationshipModel fk = (RelationshipModel) conn;
                TableModel target = fk.getTarget();
                if (!dependentModels.contains(target)) {
                    addTableModel(result, target, innerDependentModels);
                }
            }
        }

        // Then add myself
        result.add(table);
    }

}
