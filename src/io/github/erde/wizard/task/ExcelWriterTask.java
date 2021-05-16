package io.github.erde.wizard.task;

import static io.github.erde.Resource.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import io.github.erde.core.util.StringUtils;
import io.github.erde.editor.diagram.model.BaseConnectionModel;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.RelationshipMappingModel;
import io.github.erde.editor.diagram.model.RelationshipModel;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;
import io.github.erde.generate.excel.ColumnData;
import io.github.erde.generate.excel.ExcelGen;
import io.github.erde.generate.excel.TableData;

/**
 * ExcelWriterTask.
 *
 * @author parapata
 * @since 1.0.14
 */
public class ExcelWriterTask implements IRunnableWithProgress {

    private RootModel rootModel;
    private File outFile;

    public ExcelWriterTask(RootModel rootModel, File outFile) {
        this.rootModel = rootModel;
        this.outFile = outFile;
    }

    @Override
    public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
        try {
            monitor.beginTask("ファイル出力中...", IProgressMonitor.UNKNOWN);
            if (monitor.isCanceled()) {
                throw new InterruptedException();
            }

            List<TableData> data = getTableData(rootModel);
            new ExcelGen(outFile).export(data);
            monitor.worked(1);
        } finally {
            monitor.done();
        }
    }

    private List<TableData> getTableData(RootModel root) {
        List<TableData> tables = new ArrayList<>();

        for (TableModel table : root.getTables()) {
            TableData tableData = new TableData();
            tableData.setLogicalTableName(table.getLogicalName());
            tableData.setPhysicalTableName(table.getPhysicalName());
            tableData.setDescription(table.getDescription());

            List<ColumnData> columns = new ArrayList<>();
            for (ColumnModel column : table.getColumns()) {
                ColumnData columnData = new ColumnData();
                columnData.setLogicalColumnName(column.getLogicalName());
                columnData.setPhysicalColumnName(column.getPhysicalName());

                StringBuilder sb = new StringBuilder();
                sb.append(StringUtils.defaultString(column.getDescription()));
                if (!column.getEnumNames().isEmpty()) {
                    if (StringUtils.isNotEmpty(column.getDescription())) {
                        sb.append("\n");
                    }
                    sb.append(String.join(",", column.getEnumNames()));
                }
                columnData.setDescription(sb.toString());

                if (column.isUnsigned()) {
                    String columnTypeName = String.format("%s UNSIGNED", column.getColumnType().getPhysicalName());
                    columnData.setType(columnTypeName);
                } else {
                    columnData.setType(column.getColumnType().getPhysicalName());
                }
                columnData.setDefaultValue(column.getDefaultValue());
                if (column.getColumnType().isSizeSupported() && column.getColumnSize() != null) {
                    columnData.setColumnSize(String.valueOf(column.getColumnSize()));
                    if (column.getDecimal() != null) {
                        columnData.setDecimal(String.valueOf(column.getDecimal()));
                    }
                }
                if (column.isPrimaryKey()) {
                    columnData.setPrimaryKey(LABEL_O.getValue());
                }
                if (column.isNotNull()) {
                    columnData.setNullable(LABEL_O.getValue());
                }
                if (column.isUniqueKey()) {
                    columnData.setUnique(LABEL_O.getValue());
                }
                if (column.isAutoIncrement()) {
                    columnData.setAutoIncrement(LABEL_O.getValue());
                }

                LOOP: for (BaseConnectionModel conn : table.getModelSourceConnections()) {
                    if (conn instanceof RelationshipModel) {
                        RelationshipModel foreignKey = (RelationshipModel) conn;
                        for (RelationshipMappingModel mapping : foreignKey.getMappings()) {
                            if (mapping.getReferenceKey() == column) {
                                columnData.setForeignKey(LABEL_O.getValue());
                                columnData.setReference(foreignKey.getTarget().getPhysicalName() + "."
                                        + mapping.getForeignKey().getPhysicalName());
                                break LOOP;
                            }
                        }
                    }
                }

                columnData.setIndex(columns.size() + 1);
                columns.add(columnData);
            }
            tableData.setColumns(columns);
            tables.add(tableData);
        }

        return tables;
    }
}
