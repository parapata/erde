package io.github.erde.editor.dialog.relationship;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.erde.IMessages;
import io.github.erde.dialect.type.IndexType;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.IndexModel;
import io.github.erde.editor.diagram.model.RelationshipModel;
import io.github.erde.editor.diagram.model.TableModel;

/**
 * RelationshipHelper.
 *
 * @author modified by parapata
 */
public class RelationshipHelper implements IMessages {

    private Logger logger = LoggerFactory.getLogger(RelationshipHelper.class);

    private IRelationshipDialog dialog;

    public RelationshipHelper(IRelationshipDialog dialog) {
        this.dialog = dialog;
    }

    public String getColumnName(ColumnModel column) {
        if (dialog.isLogical()) {
            return StringUtils.defaultString(column.getLogicalName());
        } else {
            return StringUtils.defaultString(column.getPhysicalName());
        }
    }

    public String getReferrdName(RelationshipModel relationshipModel, Map<String, List<String>> referrdMap) {

        Optional<String> referrdName;

        if (isUpdate(relationshipModel)) {
            List<String> referenceKeys = relationshipModel.getMappings()
                    .stream()
                    .map(mapping -> getColumnName(mapping.getReferenceKey()))
                    .collect(Collectors.toList());

            referrdName = referrdMap.entrySet().stream()
                    .filter(predicate -> {
                        if (predicate.getValue().size() != referenceKeys.size()) {
                            return false;
                        }
                        return predicate.getValue()
                                .stream()
                                .allMatch(columnName -> referenceKeys.contains(columnName));
                    })
                    .map(map -> map.getKey())
                    .findFirst();
        } else {
            AtomicInteger index = new AtomicInteger();
            referrdName = referrdMap.entrySet()
                    .stream()
                    .filter(item -> {
                        return index.incrementAndGet() == 1;
                    })
                    .map(map -> map.getKey())
                    .findFirst();
        }
        logger.info("referrdMap : {}", referrdMap);
        return referrdName.orElse("");
    }

    public List<String> getForeignKeyCandidates(RelationshipModel relationshipModel, List<String> exclusions) {
        return relationshipModel.getTarget().getColumns()
                .stream()
                .filter(column -> {
                    // 外部キーとして設定済みのカラムは省く
                    return !exclusions.contains(getColumnName(column));
                }).map(column -> getColumnName(column))
                .collect(Collectors.toList());
    }

    public ColumnModel getSelectColumnModel(String columnName, List<ColumnModel> columns) {
        Predicate<ColumnModel> selectColumn = predicate -> getColumnName(predicate).equals(columnName);
        ColumnModel result = columns.stream()
                .filter(selectColumn)
                .findFirst()
                .orElseThrow(RuntimeException::new);
        return result;
    }

    public boolean isUpdate(RelationshipModel relationshipModel) {
        return !relationshipModel.getMappings().isEmpty();
    }

    public Map<String, List<String>> createReferrdMap(TableModel table) {
        Map<String, List<String>> map = new LinkedHashMap<>();
        List<String> columnNames = getPrimaryKeys(table).stream()
                .map(column -> getColumnName(column))
                .collect(Collectors.toList());
        map.put(getResource("dialog.mapping.primariyKey"), columnNames);

        getUniqueKeys(table).forEach(column -> {
            String columnName = getColumnName(column);
            map.put(columnName, Arrays.asList(columnName));
        });

        getUniqueIndexes(table.getIndices()).forEach(index -> {
            List<String> indexColumnNames = table.getColumns()
                    .stream()
                    .filter(predicate -> index.getColumns().contains(predicate.getPhysicalName()))
                    .map(column -> getColumnName(column))
                    .collect(Collectors.toList());
            map.put(index.getIndexName(), indexColumnNames);
        });
        return map;
    }

    public List<ColumnModel> getPrimaryKeys(TableModel table) {
        return table.getColumns()
                .stream()
                .filter(predicate -> predicate.isPrimaryKey())
                .collect(Collectors.toList());
    }

    public List<ColumnModel> getUniqueKeys(TableModel table) {
        return table.getColumns()
                .stream()
                .filter(predicate -> !predicate.isPrimaryKey() && predicate.isUniqueKey())
                .collect(Collectors.toList());
    }

    public List<IndexModel> getUniqueIndexes(List<IndexModel> indexes) {
        return indexes
                .stream()
                .filter(predicate -> predicate.getIndexType().equals(IndexType.UNIQUE))
                .collect(Collectors.toList());
    }
}
