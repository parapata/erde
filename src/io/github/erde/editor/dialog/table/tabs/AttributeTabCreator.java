package io.github.erde.editor.dialog.table.tabs;

import java.util.ArrayList;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private Logger logger = LoggerFactory.getLogger(AttributeTabCreator.class);

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
    private Combo cmbEnum;
    private Button btnEnumEdit;
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

    /** テーブル一覧からカラムが指定された場合の処理. */
    private SelectionAdapter columnListSelectionChanged = new SelectionAdapter() {
        @Override
        public void widgetSelected(SelectionEvent event) {
            logger.info("テーブル一覧からカラム情報が選択されました");
            columnSelectionChanged();
        }
    };

    /** 型が変更された場合の処理. */
    private SelectionListener columnTypeSelectionChanged = new SelectionAdapter() {
        @Override
        public void widgetSelected(SelectionEvent event) {
            logger.info("カラムタイムが変更されました");
            updateColumnInfo();
        }
    };

    /** カラム情報(TEXT)が更新された場合の処理. */
    private FocusListener updateColumnInfoChanged = new FocusAdapter() {
        @Override
        public void focusLost(FocusEvent event) {
            logger.info("カラム情報(TEXT)が変更されました");
            updateColumnInfo();
        }
    };

    /** カラム情報(CheckBox)が更新された場合の処理. */
    private SelectionListener columnInfoSelectionChanged = new SelectionAdapter() {
        @Override
        public void widgetSelected(SelectionEvent event) {
            logger.info("カラム情報(Checkbox)が変更されました");
            updateColumnInfo();
        }
    };

    public AttributeTabCreator(ITableEdit tableEdit, int editColumnIndex, List<DomainModel> domains) {
        this.tableEdit = tableEdit;
        this.editColumnIndex = editColumnIndex;
        this.domains = domains;
    }

    /**
     * Create tab area.
     *
     * @param tabFolder parent component
     */
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

        // ------------------------------------------------------------
        // tabale area
        // ------------------------------------------------------------
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
        tblColumns.addSelectionListener(columnListSelectionChanged);

        UIUtils.createColumn(tblColumns, "dialog.table.columnPK", 55);
        UIUtils.createColumn(tblColumns, "dialog.table.columnFK", 55);
        UIUtils.createColumn(tblColumns, "dialog.table.columnPyhgicalName", 160);
        UIUtils.createColumn(tblColumns, "dialog.table.columnLogicalName", 160);
        UIUtils.createColumn(tblColumns, "dialog.table.columnType", 160);
        UIUtils.createColumn(tblColumns, "dialog.table.columnNotNull", 55);
        UIUtils.createColumn(tblColumns, "dialog.table.columnUnique", 55);

        for (ColumnModel model : tableEdit.getColumns()) {
            TableItem item = new TableItem(tblColumns, SWT.NULL);
            setTableItem(item, model);
        }

        Composite buttons = new Composite(tableArea, SWT.NULL);
        GridLayout buttonsLayout = new GridLayout(1, false);
        buttonsLayout.horizontalSpacing = 0;
        buttonsLayout.verticalSpacing = 0;
        buttonsLayout.marginHeight = 0;
        buttonsLayout.marginWidth = 2;
        buttons.setLayout(buttonsLayout);
        buttons.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

        // -----
        btnAddColumn = new Button(buttons, SWT.PUSH);
        btnAddColumn.setText(getResource("dialog.table.addColumn"));
        btnAddColumn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnAddColumn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                int num = tableEdit.getColumns().size() + 1;
                ColumnModel column = new ColumnModel();
                column.setPhysicalName(String.format("COLUMN_%d", num));
                column.setLogicalName(String.format("%s_%d", getResource("label.column"), num));
                column.setColumnType(tableEdit.getDialect().getDefaultColumnType());

                int index = tblColumns.getSelectionIndex();
                if (index == -1) {
                    tableEdit.getColumns().add(column);
                    TableItem item = new TableItem(tblColumns, SWT.NULL);
                    setTableItem(item, column);
                    tblColumns.setSelection(index - 1);
                } else {
                    tableEdit.getColumns().add(index + 1, column);
                    syncColumnModelsToTable();
                    tblColumns.setSelection(index + 1);
                }
                columnSelectionChanged();
            }
        });

        // -----
        btnRemoveColumn = new Button(buttons, SWT.PUSH);
        btnRemoveColumn.setText(getResource("dialog.table.removeColumn"));
        btnRemoveColumn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnRemoveColumn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                int index = tblColumns.getSelectionIndex();
                tableEdit.getColumns().remove(index);
                tblColumns.remove(index);
                int row = tblColumns.getItemCount();
                if (index == 0 && row > 0) {
                    tblColumns.select(0);
                } else if (index == row) {
                    tblColumns.select(index - 1);
                } else if (index < row) {
                    tblColumns.select(index);
                } else {
                    tblColumns.select(-1);
                }
                columnSelectionChanged();
            }
        });

        // -----
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
                columnSelectionChanged();
            }
        });

        // -----
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
                columnSelectionChanged();
            }
        });

        // ------------------------------------------------------------
        // column infomation area
        // ------------------------------------------------------------
        Group group = new Group(composite, SWT.NULL);
        group.setText(getResource("dialog.table.editColumn"));
        group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        group.setLayout(new GridLayout(7, false));

        // -----
        UIUtils.createLabel(group, "dialog.table.editColumn.name");
        txtColumnName = new Text(group, SWT.BORDER);
        GridData pyhgicalNameGridData = new GridData(GridData.FILL_HORIZONTAL);
        pyhgicalNameGridData.horizontalSpan = 6;
        txtColumnName.setLayoutData(pyhgicalNameGridData);
        txtColumnName.addFocusListener(updateColumnInfoChanged);

        // -----
        UIUtils.createLabel(group, "dialog.table.editColumn.logicalName");
        txtColumnLogicalName = new Text(group, SWT.BORDER);
        GridData logicalNameGridData = new GridData(GridData.FILL_HORIZONTAL);
        logicalNameGridData.horizontalSpan = 6;
        txtColumnLogicalName.setLayoutData(logicalNameGridData);
        txtColumnLogicalName.addFocusListener(updateColumnInfoChanged);

        // -----
        UIUtils.createLabel(group, "dialog.table.editColumn.type");
        cmbColumnType = new Combo(group, SWT.READ_ONLY);
        cmbColumnType.addSelectionListener(columnTypeSelectionChanged);
        for (DomainModel doman : domains) {
            cmbColumnType.add(doman.toString());
        }
        for (IColumnType type : tableEdit.getDialect().getColumnTypes()) {
            cmbColumnType.add(type.toString());
        }

        // -----
        UIUtils.createLabel(group, "dialog.table.editColumn.size");
        txtColumnSize = new Text(group, SWT.BORDER);
        txtColumnSize.setLayoutData(UIUtils.createGridDataWithWidth(60));
        txtColumnSize.addFocusListener(updateColumnInfoChanged);
        txtColumnSize.addVerifyListener(new NumericVerifyListener());

        // -----
        UIUtils.createLabel(group, "dialog.table.editColumn.decimal");
        txtDecimal = new Text(group, SWT.BORDER);
        txtDecimal.setLayoutData(UIUtils.createGridDataWithWidth(60));
        txtDecimal.addFocusListener(updateColumnInfoChanged);
        txtDecimal.addVerifyListener(new NumericVerifyListener());

        // -----
        btnChkUnsigned = new Button(group, SWT.CHECK);
        btnChkUnsigned.setText(getResource("dialog.table.editColumn.unsigned"));
        btnChkUnsigned.addSelectionListener(columnInfoSelectionChanged);

        // -----
        UIUtils.createLabel(group, "");
        Composite test = new Composite(group, SWT.NULL);
        test.setLayout(new GridLayout(5, false));
        test.setLayoutData(UIUtils.createGridData(6));
        UIUtils.createLabel(test, getResource("Enum/Set:"));
        cmbEnum = new Combo(test, SWT.READ_ONLY);
        btnEnumEdit = new Button(test, SWT.PUSH);
        btnEnumEdit.setText(getResource("編集"));
        btnEnumEdit.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
            }
        });

        // -----
        UIUtils.createLabel(group, "dialog.table.editColumn.defaultValue");
        txtDefaultValue = new Text(group, SWT.BORDER);
        GridData defaultValueGridData = new GridData(GridData.FILL_HORIZONTAL);
        defaultValueGridData.horizontalSpan = 6;
        txtDefaultValue.setLayoutData(defaultValueGridData);
        txtDefaultValue.addFocusListener(updateColumnInfoChanged);

        // -----
        UIUtils.createLabel(group, "dialog.table.description");
        txtColumnDescription = new Text(group, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
        txtColumnDescription.setLayoutData(UIUtils.createGridDataWithColspan(6, 90));
        txtColumnDescription.addFocusListener(updateColumnInfoChanged);

        // -----
        Composite checks = new Composite(group, SWT.NULL);
        checks.setLayout(new GridLayout(6, false));
        checks.setLayoutData(UIUtils.createGridData(7));

        // -----
        btnChkIsPK = new Button(checks, SWT.CHECK);
        btnChkIsPK.setText(getResource("dialog.table.editColumn.PK"));
        btnChkIsPK.addSelectionListener(columnInfoSelectionChanged);

        // -----
        btnChkNotNull = new Button(checks, SWT.CHECK);
        btnChkNotNull.setText(getResource("dialog.table.editColumn.notNull"));
        btnChkNotNull.addSelectionListener(columnInfoSelectionChanged);

        // -----
        btnChkIsUnique = new Button(checks, SWT.CHECK);
        btnChkIsUnique.setText(getResource("dialog.table.editColumn.UniqueKey"));
        btnChkIsUnique.addSelectionListener(columnInfoSelectionChanged);

        // -----
        btnAutoIncrement = new Button(checks, SWT.CHECK);
        btnAutoIncrement.setText(getResource("dialog.table.editColumn.autoIncrement"));
        btnAutoIncrement.addSelectionListener(columnInfoSelectionChanged);

        if (editColumnIndex > -1) {
            tabFolder.setSelection(tab);
            tblColumns.select(editColumnIndex);
            columnSelectionChanged();
        } else {
            disableColumnForm();
        }
        updateButtons();
    }

    /**
     * Display detailed information for the selected column.
     */
    private void columnSelectionChanged() {
        logger.info("Call columnSelectionChanged");

        int selectedIndex = tblColumns.getSelectionIndex();
        if (selectedIndex > -1) {
            ColumnModel column = tableEdit.getColumns().get(selectedIndex);
            IColumnType columnType = column.getColumnType();

            // -----
            txtColumnName.setText(column.getPhysicalName());
            txtColumnName.setEnabled(true);

            // -----
            txtColumnLogicalName.setText(column.getLogicalName());
            txtColumnLogicalName.setEnabled(true);

            // -----
            cmbColumnType.setText(columnType.toString());
            cmbColumnType.setEnabled(true);

            // -----
            if (columnType.isSizeSupported()) {
                if (column.getColumnSize() == null) {
                    txtColumnSize.setText("");
                } else {
                    txtColumnSize.setText(String.valueOf(column.getColumnSize()));
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
                if (column.getDecimal() == null) {
                    txtDecimal.setText("");
                } else {
                    txtDecimal.setText(String.valueOf(column.getDecimal()));
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
                btnChkUnsigned.setSelection(column.isUnsigned());
            } else {
                btnChkUnsigned.setSelection(false);
            }
            if (columnType.isDomain()) {
                btnChkUnsigned.setEnabled(false);
            } else {
                btnChkUnsigned.setEnabled((columnType.isUnsignedSupported()));
            }

            // -----
            txtColumnDescription.setText(column.getDescription());
            txtColumnDescription.setEnabled(true);

            // -----
            txtDefaultValue.setText(column.getDefaultValue());
            txtDefaultValue.setEnabled(true);

            // -----
            btnChkIsPK.setSelection(column.isPrimaryKey());
            btnChkIsPK.setEnabled(true);

            // -----
            btnChkIsUnique.setSelection(column.isUniqueKey());
            btnChkIsUnique.setEnabled(!column.isPrimaryKey());

            // -----
            if (column.isPrimaryKey()) {
                btnChkNotNull.setSelection(true);
                btnChkNotNull.setEnabled(false);
            } else {
                btnChkNotNull.setSelection(column.isNotNull());
                btnChkNotNull.setEnabled(true);
            }

            // -----
            if (tableEdit.getDialect().isAutoIncrement()) {
                if (column.isPrimaryKey() && columnType.isSizeSupported()) {
                    btnAutoIncrement.setEnabled(true);
                    btnAutoIncrement.setSelection(column.isAutoIncrement());
                } else {
                    btnAutoIncrement.setEnabled(false);
                    btnAutoIncrement.setSelection(false);
                }
            } else {
                btnAutoIncrement.setSelection(false);
                btnAutoIncrement.setEnabled(false);
            }

            editColumnIndex = selectedIndex;
        } else {
            disableColumnForm();
        }
        updateButtons();
    }

    /**
     * Update column information.
     */
    private void updateColumnInfo() {
        logger.info("Call updateColumnInfo");

        if (cmbColumnType.getSelectionIndex() < 0) {
            return;
        }

        ColumnModel column = tableEdit.getColumns().get(editColumnIndex);
        column.setPhysicalName(txtColumnName.getText());
        column.setLogicalName(txtColumnLogicalName.getText());

        int columnTypeIndex = cmbColumnType.getSelectionIndex();
        int domainCount = domains.size();

        IColumnType columnType;

        if (columnTypeIndex < domainCount) {
            // domain column selected

            DomainModel domain = domains.get(columnTypeIndex);
            column.setColumnType(domain);
            column.setColumnSize(domain.getColumnSize());
            column.setDecimal(domain.getDecimal());
            columnType = domain;

        } else {
            // column type selected

            List<IColumnType> columnTypes = tableEdit.getDialect().getColumnTypes();
            columnType = columnTypes.get(columnTypeIndex - domainCount);
            column.setColumnType(columnType);
        }

        if (txtColumnSize.isEnabled() && StringUtils.isNotEmpty(txtColumnSize.getText())) {
            column.setColumnSize(Integer.valueOf(txtColumnSize.getText()));
        } else {
            column.setColumnSize(null);
        }
        if (txtDecimal.isEnabled() && StringUtils.isNotEmpty(txtDecimal.getText())) {
            column.setDecimal(Integer.valueOf(txtDecimal.getText()));
        } else {
            column.setDecimal(null);
        }
        if (columnType.isUnsignedSupported()) {
            column.setUnsigned(btnChkUnsigned.getSelection());
        } else {
            column.setUnsigned(false);
            btnChkUnsigned.setSelection(false);
        }

        if (columnType.isSizeSupported()) {
            Integer columnSize = column.getColumnSize();
            if (columnSize == null) {
                txtColumnSize.setText("");
            } else {
                txtColumnSize.setText(String.valueOf(columnSize));
            }

            Integer decimal = column.getDecimal();
            if (decimal == null) {
                txtDecimal.setText("");
            } else {
                txtDecimal.setText(String.valueOf(decimal));
            }
        } else {
            txtColumnSize.setText("");
            txtDecimal.setText("");
        }

        txtColumnSize.setEnabled(columnType.isSizeSupported());
        txtDecimal.setEnabled(columnType.isDecimalSupported());
        btnChkUnsigned.setEnabled(columnType.isUnsignedSupported());

        if (btnChkIsPK.getSelection()) {
            btnChkNotNull.setSelection(true);
            btnChkNotNull.setEnabled(false);
            btnChkIsUnique.setSelection(false);
            btnChkIsUnique.setEnabled(false);

            // TODO 整数の場合の判定分を追加する
            if (tableEdit.getDialect().isAutoIncrement()) {
                if (btnChkIsPK.getSelection()) {
                    btnAutoIncrement.setEnabled(true);
                }
            } else {
                btnAutoIncrement.setSelection(false);
                btnAutoIncrement.setEnabled(false);
            }

        } else {
            if (btnChkIsPK.getSelection() != column.isPrimaryKey()) {
                btnChkNotNull.setSelection(false);
                btnAutoIncrement.setSelection(false);
            }
            btnChkNotNull.setEnabled(true);
            btnChkIsUnique.setEnabled(true);
            btnAutoIncrement.setEnabled(false);
        }

        column.setDefaultValue(txtDefaultValue.getText());
        column.setDescription(txtColumnDescription.getText());

        column.setPrimaryKey(btnChkIsPK.getSelection());
        column.setNotNull(btnChkNotNull.getSelection());
        column.setUniqueKey(btnChkIsUnique.getSelection());
        column.setAutoIncrement(btnAutoIncrement.getSelection());

        TableItem tblItem = tblColumns.getItem(editColumnIndex);
        setTableItem(tblItem, column);
    }

    /**
     * Change the order of the column information displayed in the table.
     */
    private void syncColumnModelsToTable() {
        logger.info("Call syncColumnModelsToTable");

        tblColumns.removeAll();
        for (ColumnModel column : tableEdit.getColumns()) {
            TableItem item = new TableItem(tblColumns, SWT.NULL);
            setTableItem(item, column);
        }
    }

    /**
     * Updates status of column control buttons.
     */
    private void updateButtons() {
        logger.info("Call updateButtons");

        btnUpColumn.setEnabled(false);
        btnDownColumn.setEnabled(false);
        int index = tblColumns.getSelectionIndex();
        if (index > -1) {
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
        logger.info("Call disableColumnForm");

        txtColumnName.setText("");
        txtColumnName.setEnabled(false);

        txtColumnLogicalName.setText("");
        txtColumnLogicalName.setEnabled(false);

        cmbColumnType.select(-1);
        cmbColumnType.setEnabled(false);

        txtColumnSize.setText("");
        txtColumnSize.setEnabled(false);

        txtDecimal.setText("");
        txtDecimal.setEnabled(false);

        cmbEnum.select(-1);
        cmbEnum.setEnabled(false);

        btnEnumEdit.setEnabled(false);

        btnChkUnsigned.setSelection(false);
        btnChkUnsigned.setEnabled(false);

        txtDefaultValue.setText("");
        txtDefaultValue.setEnabled(false);

        txtColumnDescription.setText("");
        txtColumnDescription.setEnabled(false);

        btnChkIsPK.setSelection(false);
        btnChkIsPK.setEnabled(false);

        btnChkIsUnique.setSelection(false);
        btnChkIsUnique.setEnabled(false);

        btnChkNotNull.setSelection(false);
        btnChkNotNull.setEnabled(false);

        btnAutoIncrement.setSelection(false);
        btnAutoIncrement.setEnabled(false);
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
        item.setText(FK_CULUMN, tableEdit.isForeignkey(model.getPhysicalName()) ? "FK" : "");
        item.setText(PHYSICAL_NAME_CULUMN, model.getPhysicalName());
        item.setText(LOGICAL_NAME_CULUMN, model.getLogicalName());
        item.setText(SQL_TYPE_CULUMN, column);
        item.setText(NOT_NULL_CULUMN, Boolean.toString(model.isNotNull()));
        item.setText(UNIQUE_CULUMN, Boolean.toString(model.isUniqueKey()));
    }
}
