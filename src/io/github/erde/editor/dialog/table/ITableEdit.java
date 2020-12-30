package io.github.erde.editor.dialog.table;

import java.util.List;

import io.github.erde.dialect.IDialect;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.IndexModel;

/**
 * ITableEdit.
 *
 * @author modified by parapata
 */
public interface ITableEdit {
    IDialect getDialect();

    String getPhysicalName();

    void setPhysicalName(String physicalName);

    String getLogicalName();

    void setLogicalName(String logicalName);

    String getDescription();

    void setDescription(String description);

    List<ColumnModel> getColumns();

    List<IndexModel> getIndices();

    boolean isForeignkey(String physicalName);

    boolean isReferenceKey(String physicalName);
}
