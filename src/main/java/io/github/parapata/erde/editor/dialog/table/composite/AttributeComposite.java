package io.github.parapata.erde.editor.dialog.table.composite;

import static io.github.parapata.erde.Resource.*;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.parapata.erde.core.util.swt.UIUtils;
import io.github.parapata.erde.editor.diagram.model.ColumnModel;
import io.github.parapata.erde.editor.dialog.table.ColumnEditDialog;
import io.github.parapata.erde.editor.dialog.table.ITableEdit;

/**
 * AttributeComposite.
 *
 * @author modified by parapata
 */
public class AttributeComposite extends Composite {
    private Logger logger = LoggerFactory.getLogger(AttributeComposite.class);

    private static final int PK_CULUMN = 0;
    private static final int FK_CULUMN = 1;
    private static final int PHYSICAL_NAME_CULUMN = 2;
    private static final int LOGICAL_NAME_CULUMN = 3;
    private static final int SQL_TYPE_CULUMN = 4;
    private static final int NOT_NULL_CULUMN = 5;
    private static final int UNIQUE_CULUMN = 6;

    private ITableEdit tableEdit;

    private Text txtTablePyhgicalName;
    private Text txtTableLogicalName;

    private TableViewer viewer;

    private Button btnAddColumn;
    private Button btnRemoveColumn;
    private Button btnUpColumn;
    private Button btnDownColumn;

    // テーブル一覧からカラムが指定された場合の処理
    private IDoubleClickListener columnDoubleClick = new IDoubleClickListener() {
        public void doubleClick(DoubleClickEvent event) {
            logger.info("テーブル一覧からカラム情報が選択されました");

            int index = viewer.getTable().getSelectionIndex();

            ColumnModel columnModel = tableEdit.getColumns().get(index);
            ColumnEditDialog dialog = new ColumnEditDialog(getShell(), tableEdit.getDialect(), columnModel);
            if (dialog.open() == Window.OK) {
                columnModel = dialog.getColumn();
                TableItem[] items = viewer.getTable().getSelection();
                setTableItem(items[0], columnModel);
            }
        }
    };

    public AttributeComposite(Composite parent, ITableEdit tableEdit, int editColumnIndex) {
        super(parent, SWT.NONE);
        this.tableEdit = tableEdit;
        create(editColumnIndex);
    }

    private void create(int editColumnIndex) {
        setLayout(new GridLayout(2, false));
        setLayoutData(UIUtils.createGridData(2));

        // ------------------------------------------------------------
        // table name area
        // ------------------------------------------------------------
        Composite tableNameArea = new Composite(this, SWT.NONE);
        tableNameArea.setLayout(new GridLayout(2, false));
        tableNameArea.setLayoutData(UIUtils.createGridData(2));

        UIUtils.createLabel(tableNameArea, LABEL_PHYSICAL_TABLE_NAME);
        txtTablePyhgicalName = new Text(tableNameArea, SWT.BORDER);
        txtTablePyhgicalName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        txtTablePyhgicalName.setText(tableEdit.getPhysicalName());
        txtTablePyhgicalName.addModifyListener(event -> tableEdit.setPhysicalName(txtTablePyhgicalName.getText()));

        UIUtils.createLabel(tableNameArea, LABEL_LOGICAL_TABLE_NAME);
        txtTableLogicalName = new Text(tableNameArea, SWT.BORDER);
        txtTableLogicalName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        txtTableLogicalName.setText(tableEdit.getLogicalName());
        txtTableLogicalName.addModifyListener(event -> tableEdit.setLogicalName(txtTableLogicalName.getText()));

        // ------------------------------------------------------------
        // tabale area
        // ------------------------------------------------------------
        Composite tableArea = new Composite(this, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        layout.horizontalSpacing = 0;
        layout.verticalSpacing = 0;
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        tableArea.setLayout(layout);
        tableArea.setLayoutData(new GridData(GridData.FILL_BOTH));

        viewer = new TableViewer(tableArea, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
        Table table = viewer.getTable();
        table.setLayoutData(new GridData(GridData.FILL_BOTH));
        table.setHeaderVisible(true);
        // tblColumns.addSelectionListener(columnListSelectionChanged);
        viewer.addDoubleClickListener(columnDoubleClick);
        viewer.addSelectionChangedListener(new ISelectionChangedListener() {
            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                updateButtons();
            }
        });

        UIUtils.createColumn(table, LABEL_COLUMN_PK, 55);
        UIUtils.createColumn(table, LABEL_COLUMN_FK, 55);
        UIUtils.createColumn(table, LABEL_PYHGICAL_COLUMN_NAME, 160);
        UIUtils.createColumn(table, LABEL_LOGICAL_COLUMN_NAME, 160);
        UIUtils.createColumn(table, LABEL_TYPE, 160);
        UIUtils.createColumn(table, LABEL_NOT_NULL, 55);
        UIUtils.createColumn(table, LABEL_UNIQUE, 55);

        for (ColumnModel model : tableEdit.getColumns()) {
            TableItem item = new TableItem(table, SWT.NONE | SWT.FILL);
            setTableItem(item, model);
        }

        Composite buttons = new Composite(tableArea, SWT.NONE);
        GridLayout buttonsLayout = new GridLayout(1, false);
        buttonsLayout.horizontalSpacing = 0;
        buttonsLayout.verticalSpacing = 0;
        buttonsLayout.marginHeight = 0;
        buttonsLayout.marginWidth = 2;
        buttons.setLayout(buttonsLayout);
        buttons.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

        // -----
        btnAddColumn = new Button(buttons, SWT.PUSH);
        btnAddColumn.setText(LABEL_ADD.getValue());
        btnAddColumn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnAddColumn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                int num = tableEdit.getColumns().size() + 1;
                ColumnModel column = new ColumnModel();
                column.setPhysicalName(String.format("COLUMN_%d", num));
                column.setLogicalName(String.format("%s_%d", LABEL_COLUMN.getValue(), num));
                column.setColumnType(tableEdit.getDialect().getDefaultColumnType());

                int index = table.getSelectionIndex();
                if (index == -1) {
                    tableEdit.getColumns().add(column);
                    TableItem item = new TableItem(table, SWT.NONE);
                    setTableItem(item, column);
                    table.setSelection(0);
                } else {
                    tableEdit.getColumns().add(index + 1, column);
                    syncColumnModelsToTable();
                    table.setSelection(index + 1);
                }
                updateButtons();
            }
        });

        // -----
        btnRemoveColumn = new Button(buttons, SWT.PUSH);
        btnRemoveColumn.setText(LABEL_DELETE.getValue());
        btnRemoveColumn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnRemoveColumn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                int index = table.getSelectionIndex();
                tableEdit.getColumns().remove(index);
                table.remove(index);

                int size = table.getItemCount();
                if (size > 0) {
                    if (index >= size) {
                        table.setSelection(index - 1);
                    } else {
                        table.setSelection(index);
                    }
                } else {
                    table.setSelection(-1);
                }
                updateButtons();
            }
        });

        // -----
        btnUpColumn = new Button(buttons, SWT.PUSH);
        btnUpColumn.setText(LABEL_UP_COLUMN.getValue());
        btnUpColumn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnUpColumn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                int index = table.getSelectionIndex();
                ColumnModel column = tableEdit.getColumns().get(index);
                tableEdit.getColumns().remove(index);
                tableEdit.getColumns().add(index - 1, column);
                syncColumnModelsToTable();
                table.setSelection(index - 1);
                updateButtons();
            }
        });

        // -----
        btnDownColumn = new Button(buttons, SWT.PUSH);
        btnDownColumn.setText(LABEL_DOWN_COLUMN.getValue());
        btnDownColumn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnDownColumn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                int index = table.getSelectionIndex();
                ColumnModel column = tableEdit.getColumns().get(index);
                tableEdit.getColumns().remove(index);
                tableEdit.getColumns().add(index + 1, column);
                syncColumnModelsToTable();
                table.setSelection(index + 1);
                updateButtons();
            }
        });

        if (editColumnIndex > -1) {
            table.select(editColumnIndex);
        }
        updateButtons();
    }

    /**
     * Change the order of the column information displayed in the table.
     */
    private void syncColumnModelsToTable() {
        logger.info("Call syncColumnModelsToTable");

        Table table = viewer.getTable();
        table.removeAll();
        for (ColumnModel column : tableEdit.getColumns()) {
            TableItem item = new TableItem(table, SWT.NONE);
            setTableItem(item, column);
        }
    }

    /**
     * Updates status of column control buttons.
     */
    private void updateButtons() {
        logger.info("Call updateButtons");

        Table table = viewer.getTable();
        int index = table.getSelectionIndex();
        int size = table.getItemCount();

        boolean res = false;
        if (index > -1) {
            String id = tableEdit.getColumns().get(index).getId();
            res = !(tableEdit.isReferenceKey(id) || tableEdit.isForeignkey(id));
        }

        btnAddColumn.setEnabled(true);
        btnRemoveColumn.setEnabled(res);
        btnUpColumn.setEnabled(index > 0);
        btnDownColumn.setEnabled(index > -1 && index < size - 1);
    }

    private void setTableItem(TableItem item, ColumnModel model) {
        logger.info("Call setTableItem");

        List<String> args = new ArrayList<>();
        String column;
        if (model.getColumnType().isSizeSupported() && model.getColumnSize() != null) {
            args.add(String.valueOf(model.getColumnSize()));
            if (model.getColumnType().isDecimalSupported() && model.getDecimal() != null) {
                args.add(String.valueOf(model.getDecimal()));
            }
            column = String.format("%s(%s)",
                    model.getColumnType().getPhysicalName(),
                    String.join(",", args));
        } else {
            column = model.getColumnType().getPhysicalName();
        }

        item.setText(PK_CULUMN, model.isPrimaryKey() ? "PK" : "");
        item.setText(FK_CULUMN, tableEdit.isForeignkey(model.getId()) ? "FK" : "");
        item.setText(PHYSICAL_NAME_CULUMN, model.getPhysicalName());
        item.setText(LOGICAL_NAME_CULUMN, model.getLogicalName());
        if (column != null) {
            item.setText(SQL_TYPE_CULUMN, column);
        }
        item.setText(NOT_NULL_CULUMN, Boolean.toString(model.isNotNull()));
        item.setText(UNIQUE_CULUMN, Boolean.toString(model.isUniqueKey()));
    }
}
