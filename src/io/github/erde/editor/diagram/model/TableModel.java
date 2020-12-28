package io.github.erde.editor.diagram.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.views.properties.ColorPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import io.github.erde.IMessages;
import io.github.erde.dialect.type.ColumnType;

/**
 * TableModel.
 *
 * @author modified by parapata
 */
public class TableModel extends BaseEntityModel implements IMessages {

    private static final long serialVersionUID = 1L;

    public static final String P_ERROR = "p_error";
    public static final String P_TABLE_NAME = "p_table_name";
    public static final String P_LOGICAL_NAME = "p_logical_name";
    public static final String P_COLUMNS = "p_columns";
    public static final String P_INDICES = "p_indices";

    public static final String P_BACKGROUND_COLOR = "p_background_color";
    public static final String P_SCHEMA = "p_schema";

    private String physicalName;
    private String logicalName;
    private String description;
    private List<ColumnModel> columns = new ArrayList<>();
    private List<IndexModel> indices = new ArrayList<>();

    private String schema;
    private String error;
    private RGB backgroundColor;

    public TableModel() {
        super();
    }

    public TableModel(String id) {
        super(id);
    }

    @Override
    public boolean canSource(BaseConnectionModel conn) {
        if (conn instanceof NoteConnectionModel) {
            if (conn.getTarget() != null && conn.getTarget() instanceof TableModel) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canTarget(BaseConnectionModel conn) {
        if (conn instanceof NoteConnectionModel) {
            if (conn.getSource() instanceof TableModel) {
                return false;
            }
        }
        return true;
    }

    public ColumnModel[] getPrimaryKeyColumns() {
        List<ColumnModel> primaryKeyColumns = new ArrayList<>();
        for (ColumnModel columnModel : getColumns()) {
            if (columnModel.isPrimaryKey()) {
                primaryKeyColumns.add(columnModel);
            }
        }
        return primaryKeyColumns.toArray(new ColumnModel[primaryKeyColumns.size()]);
    }

    /**
     * Returns error message about this table.
     *
     * @return error messages
     */
    public String getError() {
        return StringUtils.defaultString(this.error);
    }

    /**
     * Sets error messages about this table.
     *
     * @param error
     *            error messages
     */
    public void setError(String error) {
        this.error = StringUtils.defaultString(error);
        firePropertyChange(P_ERROR, null, this.error);
    }

    public String getLogicalName() {
        return logicalName;
    }

    public void setLogicalName(String logicalName) {
        this.logicalName = StringUtils.defaultString(logicalName);
        firePropertyChange(P_LOGICAL_NAME, null, this.logicalName);
    }

    public void setPhysicalName(String tableName) {
        this.physicalName = StringUtils.defaultString(tableName);
        firePropertyChange(P_TABLE_NAME, null, this.physicalName);
    }

    public String getPhysicalName() {
        return StringUtils.defaultString(physicalName);
    }

    public void setDescription(String description) {
        this.description = StringUtils.defaultString(description);
    }

    public String getDescription() {
        return StringUtils.defaultString(this.description);
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
        firePropertyChange(P_COLUMNS, null, columns);
    }

    public List<ColumnModel> getColumns() {
        return this.columns;
    }

    public ColumnModel getColumn(String columnName) {
        for (ColumnModel column : columns) {
            if (column.getPhysicalName().equals(columnName)) {
                return column;
            }
        }
        return null;
    }

    public List<IndexModel> getIndices() {
        if (indices == null) {
            indices = new ArrayList<>();
        }
        return indices;
    }

    public void setIndices(List<IndexModel> indices) {
        this.indices = indices;
        firePropertyChange(P_INDICES, null, indices);
    }

    public String getSchema() {
        return StringUtils.defaultString(schema);
    }

    public void setSchema(String schema) {
        this.schema = StringUtils.defaultString(schema);
        firePropertyChange(P_SCHEMA, null, this.schema);
    }

    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        return new IPropertyDescriptor[] {
                new TextPropertyDescriptor(P_TABLE_NAME, getResource("property.physicalTableName")),
                new TextPropertyDescriptor(P_LOGICAL_NAME, getResource("property.logicalTableName")),
                new ColorPropertyDescriptor(P_BACKGROUND_COLOR, getResource("property.backgroundColor")) };
    }

    @Override
    public Object getPropertyValue(Object id) {
        if (id == P_TABLE_NAME) {
            return getPhysicalName();
        } else if (id == P_LOGICAL_NAME) {
            return getLogicalName();
        } else if (id == P_BACKGROUND_COLOR) {
            return getBackgroundColor();
        } else if (id == P_SCHEMA) {
            return getSchema();
        }
        return null;
    }

    @Override
    public boolean isPropertySet(Object id) {
        if (id == P_TABLE_NAME || id == P_LOGICAL_NAME || id == P_BACKGROUND_COLOR || id == P_SCHEMA) {
            return true;
        }
        return false;
    }

    @Override
    public void setPropertyValue(Object id, Object value) {
        if (id == P_TABLE_NAME) {
            setPhysicalName((String) value);
        } else if (id == P_LOGICAL_NAME) {
            setLogicalName((String) value);
        } else if (id == P_BACKGROUND_COLOR) {
            setBackgroundColor((RGB) value);
        } else if (id == P_SCHEMA) {
            setSchema((String) value);
        }
    }

    public RGB getBackgroundColor() {
        if (backgroundColor == null) {
            backgroundColor = new RGB(255, 255, 206);
        }
        return backgroundColor;
    }

    public void setBackgroundColor(RGB backgroundColor) {
        this.backgroundColor = backgroundColor;
        firePropertyChange(P_BACKGROUND_COLOR, null, backgroundColor);
    }

    @Override
    public TableModel clone() {

        TableModel newModel = SerializationUtils.clone(this);
        List<ColumnModel> columns = newModel.getColumns();

        getColumns().forEach(oldColumn -> {
            ColumnModel newColumn = SerializationUtils.clone(oldColumn);
            newColumn.setColumnType(SerializationUtils.clone((ColumnType) oldColumn.getColumnType()));
            columns.add(newColumn);
        });

        // TODO Copy Index...?
        // TODO Copy Connection...?

        return newModel;
    }
}
