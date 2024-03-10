package io.github.parapata.erde.editor.diagram.model;

import java.util.ArrayList;
import java.util.List;

import io.github.parapata.erde.core.util.SerializationUtils;
import io.github.parapata.erde.dialect.type.IIndexType;

/**
 * IndexModel.
 *
 * @author modified by parapata
 */
public class IndexModel implements IModel {

    private static final long serialVersionUID = 1L;

    private String indexName;
    private IIndexType indexType;
    private List<String> columns = new ArrayList<>();

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    /**
     * Returns the index type.
     *
     * @return "INDEX" or "UNIQUE"
     */
    public IIndexType getIndexType() {
        return indexType;
    }

    /**
     * Sets the index type.
     *
     * @param indexType "INDEX" or "UNIQUE"
     */
    public void setIndexType(IIndexType indexType) {
        this.indexType = indexType;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        if (columns == null) {
            this.columns.clear();
        } else {
            this.columns = columns;
        }
    }

    @Override
    protected IndexModel clone() throws CloneNotSupportedException {
        IndexModel newModel = SerializationUtils.clone(this);
        List<String> newColumns = SerializationUtils.clone((ArrayList<String>) newModel.getColumns());
        newModel.columns = newColumns;
        return newModel;
    }

    @Override
    public String toString() {
        String indexTypeName = indexType == null ? "" : indexType.getName();
        return String.format("%s - %s(%s)", indexName, indexTypeName, String.join(", ", columns));
    }
}
