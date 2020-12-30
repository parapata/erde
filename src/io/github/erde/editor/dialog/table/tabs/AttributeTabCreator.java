package io.github.erde.editor.dialog.table.tabs;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import io.github.erde.IMessages;
import io.github.erde.core.util.UIUtils;
import io.github.erde.dialect.type.IColumnType;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.DomainModel;
import io.github.erde.editor.dialog.parts.NumericVerifyListener;
import io.github.erde.editor.dialog.table.ITableEdit;

/**
 * AttributeTabCreator.
 *
 * @author modified by parapata
 */
public class AttributeTabCreator implements IMessages {

    private static final int PK_CULUMN = 0;
    private static final int FK_CULUMN = 1;
    private static final int PHYSICAL_NAME_CULUMN = 2;
    private static final int LOGICAL_NAME_CULUMN = 3;
    private static final int SQL_TYPE_CULUMN = 4;
    private static final int NOT_NULL_CULUMN = 5;
    private static final int UNIQUE_CULUMN = 6;

    private ITableEdit tableEdit;
    private int editColumnIndex;
    private List<DomainModel> domains;

    private Text txtTablePyhgicalName;
    private Text txtTableLogicalName;
    private Table tblColumns;
    private Text txtColumnName;
    private Text txtColumnLogicalName;
    private Combo cmbColumnType;
    private Text txtColumnSize;
    private Text txtDecimal;
    private Button btnChkUnsigned;
    private Button btnChkNotNull;
    private Button btnChkIsPK;
    private Button btnChkIsUnique;
    private Button btnAutoIncrement;
    private Text txtDefaultValue;
    private Text txtColumnDescription;

    private Button btnAddColumn;
    private Button btnRemoveColumn;
    private Button btnUpColumn;
    private Button btnDownColumn;

    public AttributeTabCreator(ITableEdit tableEdit, int editColumnIndex, List<DomainModel> domains) {
        this.tableEdit = tableEdit;
        this.editColumnIndex = editColumnIndex;
        this.domains = domains;
    }

    public void create(TabFolder tabFolder) {
        Composite composite = new Composite(tabFolder, SWT.NULL);
        composite.setLayout(new GridLayout());
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        TabItem tab = new TabItem(tabFolder, SWT.NULL);
        tab.setText(getResource("label.attribute"));
        tab.setControl(composite);

        Composite table = new Composite(composite, SWT.NULL);
        table.setLayout(new GridLayout(2, false));
        table.setLayoutData(UIUtils.createGridData(2));

        UIUtils.createLabel(table, "dialog.table.tablePyhgicalName");
        txtTablePyhgicalName = new Text(table, SWT.BORDER);
        txtTablePyhgicalName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        txtTablePyhgicalName.setText(tableEdit.getPhysicalName());
        txtTablePyhgicalName.addModifyListener(event -> tableEdit.setPhysicalName(txtTablePyhgicalName.getText()));

        UIUtils.createLabel(table, "dialog.table.tableLogicalName");
        txtTableLogicalName = new Text(table, SWT.BORDER);
        txtTableLogicalName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        txtTableLogicalName.setText(tableEdit.getLogicalName());
        txtTableLogicalName.addModifyListener(event -> tableEdit.setLogicalName(txtTableLogicalName.getText()));

        Composite tableArea = new Composite(composite, SWT.NULL);
        GridLayout layout = new GridLayout(2, false);
        layout.horizontalSpacing = 0;
        layout.verticalSpacing = 0;
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        tableArea.setLayout(layout);
        tableArea.setLayoutData(new GridData(GridData.FILL_BOTH));

        tblColumns = new Table(tableArea, SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION);
        tblColumns.setLayoutData(new GridData(GridData.FILL_BOTH));
        tblColumns.setHeaderVisible(true);

        UIUtils.createColumn(tblColumns, "dialog.table.columnPK", 55);
        UIUtils.createColumn(tblColumns, "dialog.table.columnFK", 55);
        UIUtils.createColumn(tblColumns, "dialog.table.columnPyhgicalName", 160);
        UIUtils.createColumn(tblColumns, "dialog.table.columnLogicalName", 160);
        UIUtils.createColumn(tblColumns, "dialog.table.columnType", 160);
        UIUtils.createColumn(tblColumns, "dialog.table.columnNotNull", 55);
        UIUtils.createColumn(tblColumns, "dialog.table.columnUnique", 55);

        for (ColumnModel model : tableEdit.getColumns()) {
            TableItem item = new TableItem(tblColumns, SWT.NULL);
            updateTableItem(item, model);
        }

        tblColumns.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                tableSelectionChanged();
            }
        });

        Composite buttons = new Composite(tableArea, SWT.NULL);

        GridLayout buttonsLayout = new GridLayout(1, false);
        buttonsLayout.horizontalSpacing = 0;
        buttonsLayout.verticalSpacing = 0;
        buttonsLayout.marginHeight = 0;
        buttonsLayout.marginWidth = 2;
        buttons.setLayout(buttonsLayout);
        buttons.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

        btnAddColumn = new Button(buttons, SWT.PUSH);
        btnAddColumn.setText(getResource("dialog.table.addColumn"));
        btnAddColumn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnAddColumn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                ColumnModel column = new ColumnModel();
                column.setPhysicalName("COLUMN_" + (tableEdit.getColumns().size() + 1));
                column.setLogicalName(getResource("label.column") + (tableEdit.getColumns().size() + 1));
                column.setColumnType(tableEdit.getDialect().getDefaultColumnType());
                int index = tblColumns.getSelectionIndex();
                if (index == -1) {
                    tableEdit.getColumns().add(column);
                    TableItem item = new TableItem(tblColumns, SWT.NULL);
                    updateTableItem(item, column);
                    tblColumns.setSelection(tableEdit.getColumns().size() - 1);
                    tableSelectionChanged();
                } else {
                    tableEdit.getColumns().add(index + 1, column);
                    syncColumnModelsToTable();
                    tblColumns.setSelection(index + 1);
                    tableSelectionChanged();
                }
            }
        });

        btnRemoveColumn = new Button(buttons, SWT.PUSH);
        btnRemoveColumn.setText(getResource("dialog.table.removeColumn"));
        btnRemoveColumn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnRemoveColumn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                int index = tblColumns.getSelectionIndex();
                tableEdit.getColumns().remove(index);
                tblColumns.remove(index);
                tblColumns.select(index - 1);
                tableSelectionChanged();
            }
        });

        btnUpColumn = new Button(buttons, SWT.PUSH);
        btnUpColumn.setText(getResource("dialog.table.upColumn"));
        btnUpColumn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnUpColumn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                int index = tblColumns.getSelectionIndex();
                ColumnModel column = tableEdit.getColumns().get(index);
                tableEdit.getColumns().remove(index);
                tableEdit.getColumns().add(index - 1, column);
                syncColumnModelsToTable();
                tblColumns.setSelection(index - 1);
                tableSelectionChanged();
            }
        });

        btnDownColumn = new Button(buttons, SWT.PUSH);
        btnDownColumn.setText(getResource("dialog.table.downColumn"));
        btnDownColumn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnDownColumn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                int index = tblColumns.getSelectionIndex();
                ColumnModel column = tableEdit.getColumns().get(index);
                tableEdit.getColumns().remove(index);
                tableEdit.getColumns().add(index + 1, column);
                syncColumnModelsToTable();

                tblColumns.setSelection(index + 1);
                tableSelectionChanged();
            }
        });

        Group group = new Group(composite, SWT.NULL);
        group.setText(getResource("dialog.table.editColumn"));
        group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        group.setLayout(new GridLayout(7, false));

        // -------------
        UIUtils.createLabel(group, "dialog.table.editColumn.name");
        txtColumnName = new Text(group, SWT.BORDER);
        GridData pyhgicalNameGridData = new GridData(GridData.FILL_HORIZONTAL);
        pyhgicalNameGridData.horizontalSpan = 6;
        txtColumnName.setLayoutData(pyhgicalNameGridData);
        txtColumnName.addFocusListener(updateColumnListener);

        // -------------
        UIUtils.createLabel(group, "dialog.table.editColumn.logicalName");
        txtColumnLogicalName = new Text(group, SWT.BORDER);
        GridData logicalNameGridData = new GridData(GridData.FILL_HORIZONTAL);
        logicalNameGridData.horizontalSpan = 6;
        txtColumnLogicalName.setLayoutData(logicalNameGridData);
        txtColumnLogicalName.addFocusListener(updateColumnListener);

        // -------------
        UIUtils.createLabel(group, "dialog.table.editColumn.type");
        cmbColumnType = new Combo(group, SWT.READ_ONLY);
        cmbColumnType.addSelectionListener(columnTypeSelectionChanged);
        for (DomainModel doman : domains) {
            cmbColumnType.add(doman.toString());
        }
        for (IColumnType type : tableEdit.getDialect().getColumnTypes()) {
            cmbColumnType.add(type.toString());
        }

        UIUtils.createLabel(group, "dialog.table.editColumn.size");
        txtColumnSize = new Text(group, SWT.BORDER);
        txtColumnSize.setLayoutData(UIUtils.createGridDataWithWidth(60));
        txtColumnSize.addFocusListener(updateColumnListener);
        txtColumnSize.addVerifyListener(new NumericVerifyListener());

        UIUtils.createLabel(group, "dialog.table.editColumn.decimal");
        txtDecimal = new Text(group, SWT.BORDER);
        txtDecimal.setLayoutData(UIUtils.createGridDataWithWidth(60));
        txtDecimal.addFocusListener(updateColumnListener);
        txtDecimal.addVerifyListener(new NumericVerifyListener());

        btnChkUnsigned = new Button(group, SWT.CHECK);
        btnChkUnsigned.setText(getResource("dialog.table.editColumn.unsigned"));
        btnChkUnsigned.addSelectionListener(columnTypeSelectionChanged);

        // -------------
        UIUtils.createLabel(group, "dialog.table.editColumn.defaultValue");
        txtDefaultValue = new Text(group, SWT.BORDER);
        GridData defaultValueGridData = new GridData(GridData.FILL_HORIZONTAL);
        defaultValueGridData.horizontalSpan = 6;
        txtDefaultValue.setLayoutData(defaultValueGridData);
        txtDefaultValue.addFocusListener(updateColumnListener);

        // -------------
        UIUtils.createLabel(group, "dialog.table.description");
        txtColumnDescription = new Text(group, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
        txtColumnDescription.setLayoutData(UIUtils.createGridDataWithColspan(6, 90));
        txtColumnDescription.addFocusListener(updateColumnListener);

        // -------------
        Composite checks = new Composite(group, SWT.NULL);
        checks.setLayout(new GridLayout(6, false));
        checks.setLayoutData(UIUtils.createGridData(7));

        btnChkIsPK = new Button(checks, SWT.CHECK);
        btnChkIsPK.setText(getResource("dialog.table.editColumn.PK"));
        btnChkIsPK.addSelectionListener(columnTypeSelectionChanged);

        btnChkNotNull = new Button(checks, SWT.CHECK);
        btnChkNotNull.setText(getResource("dialog.table.editColumn.notNull"));
        btnChkNotNull.addSelectionListener(columnTypeSelectionChanged);

        btnChkIsUnique = new Button(checks, SWT.CHECK);
        btnChkIsUnique.setText(getResource("dialog.table.editColumn.UniqueKey"));
        btnChkIsUnique.addSelectionListener(columnTypeSelectionChanged);

        btnAutoIncrement = new Button(checks, SWT.CHECK);
        btnAutoIncrement.setText(getResource("dialog.table.editColumn.autoIncrement"));
        btnAutoIncrement.addSelectionListener(columnTypeSelectionChanged);

        if (editColumnIndex >= 0) {
            tabFolder.setSelection(tab);
            tblColumns.select(editColumnIndex);
            tableSelectionChanged();
        } else {
            disableColumnForm();
        }
        updateButtons();
    }

    private FocusListener updateColumnListener = new FocusAdapter() {
        @Override
        public void focusLost(FocusEvent event) {
            updateColumn();
        }
    };

    private SelectionListener columnTypeSelectionChanged = new SelectionAdapter() {
        @Override
        public void widgetSelected(SelectionEvent event) {
            updateColumn();
        }
    };

    private void updateColumn() {
        if (editColumnIndex != -1 && cmbColumnType.getSelectionIndex() != -1) {
            ColumnModel model = tableEdit.getColumns().get(editColumnIndex);
            model.setPhysicalName(txtColumnName.getText());
            model.setLogicalName(txtColumnLogicalName.getText());

            int selectionIndex = cmbColumnType.getSelectionIndex();
            IColumnType columnType = tableEdit.getDialect().getColumnTypes().get(selectionIndex);

            if (selectionIndex < domains.size()) {
                DomainModel domain = domains.get(selectionIndex);
                model.setColumnType(domain);
                model.setColumnSize(domain.getColumnSize());
                model.setDecimal(domain.getDecimal());

                if (domain.getColumnSize() != null) {
                    txtColumnSize.setText(String.valueOf(domain.getColumnSize()));
                }
                txtColumnSize.setEnabled(false);

                if (domain.getDecimal() != null) {
                    txtDecimal.setText(String.valueOf(domain.getDecimal()));
                }
                txtDecimal.setEnabled(false);

                btnChkUnsigned.setEnabled(false);

            } else {
                List<IColumnType> columnTypes = tableEdit.getDialect().getColumnTypes();

                model.setColumnType(columnTypes.get(cmbColumnType.getSelectionIndex() - domains.size()));
                if (txtColumnSize.isEnabled() && StringUtils.isNotEmpty(txtColumnSize.getText())) {
                    model.setColumnSize(Integer.valueOf(txtColumnSize.getText()));
                } else {
                    model.setColumnSize(null);
                }
                if (txtDecimal.isEnabled() && StringUtils.isNotEmpty(txtDecimal.getText())) {
                    model.setDecimal(Integer.valueOf(txtDecimal.getText()));
                } else {
                    model.setDecimal(null);
                }
                if (columnType.isUnsignedSupported()) {
                    model.setUnsigned(btnChkUnsigned.getSelection());
                } else {
                    btnChkUnsigned.setSelection(false);
                }

                model.setUnsigned(columnType.isUnsignedSupported());

                if (model.getColumnType().isSizeSupported()) {
                    Integer columnSize = model.getColumnSize();
                    if (columnSize == null) {
                        txtColumnSize.setText("");
                    } else {
                        txtColumnSize.setText(String.valueOf(columnSize));
                    }

                    Integer decimal = model.getDecimal();
                    if (decimal == null) {
                        txtDecimal.setText("");
                    } else {
                        txtDecimal.setText(String.valueOf(decimal));
                    }
                } else {
                    txtColumnSize.setText("");
                    txtDecimal.setText("");
                }

                txtColumnSize.setEnabled(model.getColumnType().isSizeSupported());
                txtDecimal.setEnabled(model.getColumnType().isDecimalSupported());
                btnChkUnsigned.setEnabled(columnType.isUnsignedSupported());
            }

            if (model.isPrimaryKey()) {
                btnChkNotNull.setSelection(true);
                btnChkNotNull.setEnabled(false);
                btnChkIsUnique.setSelection(false);
                btnChkIsUnique.setEnabled(false);
            } else {
                btnChkNotNull.setEnabled(true);
                btnChkIsUnique.setEnabled(true);
            }

            model.setPrimaryKey(btnChkIsPK.getSelection());
            model.setUniqueKey(btnChkIsUnique.getSelection());
            model.setNotNull(btnChkNotNull.getSelection());
            model.setDescription(txtColumnDescription.getText());
            model.setAutoIncrement(btnAutoIncrement.getSelection());
            model.setDefaultValue(txtDefaultValue.getText());

            TableItem item = tblColumns.getItem(editColumnIndex);
            updateTableItem(item, model);

        }
    }

    private void syncColumnModelsToTable() {
        tblColumns.removeAll();
        for (ColumnModel model : tableEdit.getColumns()) {
            TableItem item = new TableItem(tblColumns, SWT.NULL);
            updateTableItem(item, model);
        }
    }

    private void tableSelectionChanged() {
        int selectedIndex = tblColumns.getSelectionIndex();
        if (selectedIndex > -1) {
            ColumnModel model = tableEdit.getColumns().get(selectedIndex);
            IColumnType columnType = model.getColumnType();

            // -----
            txtColumnName.setText(model.getPhysicalName());
            txtColumnName.setEnabled(true);

            // -----
            txtColumnLogicalName.setText(model.getLogicalName());
            txtColumnLogicalName.setEnabled(true);

            // -----
            cmbColumnType.setText(columnType.toString());
            cmbColumnType.setEnabled(true);

            // -----
            if (columnType.isSizeSupported()) {
                if (model.getColumnSize() == null) {
                    txtColumnSize.setText("");
                } else {
                    txtColumnSize.setText(String.valueOf(model.getColumnSize()));
                }
            } else {
                txtColumnSize.setText("");
            }
            if (columnType.isDomain()) {
                txtColumnSize.setEnabled(false);
            } else {
                txtColumnSize.setEnabled(columnType.isSizeSupported());
            }

            // -----
            if (columnType.isSizeSupported()) {
                if (model.getDecimal() == null) {
                    txtDecimal.setText("");
                } else {
                    txtDecimal.setText(String.valueOf(model.getDecimal()));
                }
            } else {
                txtDecimal.setText("");
            }
            if (columnType.isDomain()) {
                txtDecimal.setEnabled(false);
            } else {
                txtDecimal.setEnabled(columnType.isDecimalSupported());
            }

            // -----
            if (columnType.isUnsignedSupported()) {
                btnChkUnsigned.setSelection(model.isUnsigned());
            } else {
                btnChkUnsigned.setSelection(false);
            }
            if (columnType.isDomain()) {
                btnChkUnsigned.setEnabled(false);
            } else {
                btnChkUnsigned.setEnabled((columnType.isUnsignedSupported()));
            }

            // -----
            txtColumnDescription.setText(model.getDescription());
            txtColumnDescription.setEnabled(true);

            // -----
            btnChkIsPK.setSelection(model.isPrimaryKey());
            btnChkIsPK.setEnabled(true);

            // -----
            btnChkIsUnique.setSelection(model.isUniqueKey());
            btnChkIsUnique.setSelection(!model.isPrimaryKey());
            btnChkIsUnique.setEnabled(!model.isPrimaryKey());

            // -----
            btnChkNotNull.setSelection(model.isNotNull());
            btnChkNotNull.setSelection(model.isPrimaryKey());
            btnChkNotNull.setEnabled(!model.isPrimaryKey());

            // -----
            btnAutoIncrement.setSelection(model.isAutoIncrement());
            if (tableEdit.getDialect().isAutoIncrement()) {
                btnAutoIncrement.setEnabled(true);
            } else {
                btnAutoIncrement.setSelection(false);
            }

            // -----
            txtDefaultValue.setText(model.getDefaultValue());
            txtDefaultValue.setEnabled(true);

            editColumnIndex = selectedIndex;
        } else {
            disableColumnForm();
        }
        updateButtons();
    }

    /**
     * Updates status of column control buttons.
     */
    private void updateButtons() {
        btnUpColumn.setEnabled(false);
        btnDownColumn.setEnabled(false);
        int index = tblColumns.getSelectionIndex();
        if (index >= 0) {
            String physicalName = tableEdit.getColumns().get(index).getPhysicalName();
            if (tableEdit.isReferenceKey(physicalName) || tableEdit.isForeignkey(physicalName)) {
                btnRemoveColumn.setEnabled(false);
            } else {
                btnRemoveColumn.setEnabled(true);
            }

            if (index > 0) {
                btnUpColumn.setEnabled(true);
            }
            if (index < tableEdit.getColumns().size() - 1) {
                btnDownColumn.setEnabled(true);
            }
        }
    }

    /**
     * Clears and disables the column editing form.
     */
    private void disableColumnForm() {
        editColumnIndex = -1;

        txtColumnName.setText("");
        txtColumnLogicalName.setText("");
        cmbColumnType.setText("");
        txtColumnSize.setText("");
        txtDecimal.setText("");
        btnChkUnsigned.setSelection(false);
        txtDefaultValue.setText("");
        btnChkIsPK.setSelection(false);
        btnChkIsUnique.setSelection(false);
        btnChkNotNull.setSelection(false);
        btnAutoIncrement.setSelection(false);

        txtColumnName.setEnabled(false);
        txtColumnLogicalName.setEnabled(false);
        cmbColumnType.setEnabled(false);
        txtColumnSize.setEnabled(false);
        txtDecimal.setEnabled(false);
        btnChkUnsigned.setEnabled(false);
        txtDefaultValue.setEnabled(false);
        txtColumnDescription.setEnabled(false);
        btnChkIsPK.setEnabled(false);
        btnChkIsUnique.setEnabled(false);
        btnChkNotNull.setEnabled(false);
        btnAutoIncrement.setEnabled(false);
    }

    private void updateTableItem(TableItem item, ColumnModel model) {
        StringBuilder sb = new StringBuilder();
        sb.append(model.getColumnType().getPhysicalName());
        if (model.getColumnType().isSizeSupported() && model.getColumnSize() != null) {
            sb.append("(").append(model.getColumnSize());
            if (model.getColumnType().isDecimalSupported() && model.getDecimal() != null) {
                sb.append(",").append(model.getDecimal());
            }
            sb.append(")");
        }

        item.setText(PK_CULUMN, model.isPrimaryKey() ? "PK" : "");
        item.setText(FK_CULUMN, tableEdit.isForeignkey(model.getPhysicalName()) ? "FK" : "");
        item.setText(PHYSICAL_NAME_CULUMN, model.getPhysicalName());
        item.setText(LOGICAL_NAME_CULUMN, model.getLogicalName());
        item.setText(SQL_TYPE_CULUMN, sb.toString());
        item.setText(NOT_NULL_CULUMN, Boolean.toString(model.isNotNull()));
        item.setText(UNIQUE_CULUMN, Boolean.toString(model.isUniqueKey()));
    }
}
