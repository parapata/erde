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

    List<ColumnModel> getColumnModels();

    List<IndexModel> getIndexModels();

    String getTablePyhgicalName();

    void setTablePyhgicalName(String tablePyhgicalName);

    String getTableLogicalName();

    void setTableLogicalName(String tableLogicalName);

    String getTableDescription();

    void setTableDescription(String tableDescription);

    boolean isForeignkey(String physicalName);

    boolean isReferenceKey(String physicalName);
}
