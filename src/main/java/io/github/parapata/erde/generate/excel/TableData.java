package io.github.parapata.erde.generate.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * TableData.
 *
 * @author modified by parapata
 */
public class TableData {

    private String logicalTableName = "";
    private String physicalTableName = "";
    private String description;
    private List<ColumnData> columns = new ArrayList<>();

    public String getLogicalTableName() {
        return logicalTableName;
    }

    public void setLogicalTableName(String logicalTableName) {
        this.logicalTableName = logicalTableName;
    }

    public String getPhysicalTableName() {
        return physicalTableName;
    }

    public void setPhysicalTableName(String physicalTableName) {
        this.physicalTableName = physicalTableName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ColumnData> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnData> columns) {
        this.columns = columns;
    }
}
