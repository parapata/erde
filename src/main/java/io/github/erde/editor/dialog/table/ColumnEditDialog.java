package io.github.erde.editor.dialog.table;

import static io.github.erde.Resource.*;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.erde.ERDPlugin;
import io.github.erde.ICON;
import io.github.erde.core.util.StringUtils;
import io.github.erde.core.util.swt.UIUtils;
import io.github.erde.dialect.IDialect;
import io.github.erde.dialect.type.IColumnType;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.DomainModel;
import io.github.erde.editor.dialog.enumeration.EnumEditDialog;
import io.github.erde.editor.dialog.parts.NumericVerifyListener;

/**
 * ColumnEditDialog.
 *
 * @author parapata
 * @since 1.0.17
 */
public class ColumnEditDialog extends Dialog {
    private Logger logger = LoggerFactory.getLogger(ColumnEditDialog.class);

    private IDialect dialect;
    private ColumnModel columnModel;
    private List<DomainModel> domains = new ArrayList<>();

    private Text txtColumnPhysicalName;
    private Text txtColumnLogicalName;
    private Combo cmbColumnType;
    private Text txtColumnSize;
    private Text txtDecimal;
    private Combo cmbEnum;
    private Button btnEnumEdit;
    private Button chkUnsigned;
    private Button chkNotNull;
    private Button chkIsPK;
    private Button chkIsUnique;
    private Button chkAutoIncrement;
    private Text txtDefaultValue;
    private Text txtColumnDescription;

    public ColumnEditDialog(Shell parentShell, IDialect dialect, ColumnModel columnModel) {
        super(parentShell);
        this.dialect = dialect;
        this.columnModel = columnModel;
    }

    @Override
    protected Point getInitialSize() {
        return super.getInitialSize();
        // return new Point(400, 300);
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(DIALOG_COLUMN_TITLE.getValue());
        newShell.setImage(ERDPlugin.getImage(ICON.TABLE.getPath()));
    }

    @Override
    protected void constrainShellSize() {
        Shell shell = getShell();
        shell.pack();
        shell.setSize(shell.getSize().x, shell.getSize().y);
    }

    @Override
    protected int getShellStyle() {
        return (super.getShellStyle() | SWT.RESIZE | SWT.MAX);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);

        // ------------------------------------------------------------
        // column infomation area
        // ------------------------------------------------------------
        Group group = new Group(composite, SWT.NONE);
        group.setText(LABEL_EDIT_COLUMN.getValue());
        group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        group.setLayout(new GridLayout(7, false));

        // ------------------------------------------------------------
        // check box area(pkey, autoIncrement etc...)
        // ------------------------------------------------------------
        // ----- line_1
        Composite checks = new Composite(group, SWT.NONE);
        checks.setLayout(new GridLayout(6, false));
        checks.setLayoutData(UIUtils.createGridData(7));

        chkIsPK = new Button(checks, SWT.CHECK);
        chkIsPK.setText(LABEL_COLUMN_PK.getValue());
        chkIsPK.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                super.widgetSelected(event);
                updatePkChkBtn();
            }
        });

        // -----
        chkNotNull = new Button(checks, SWT.CHECK);
        chkNotNull.setText(LABEL_NOT_NULL.getValue());
        // chkNotNull.addSelectionListener(columnInfoSelectionChanged);

        // -----
        chkIsUnique = new Button(checks, SWT.CHECK);
        chkIsUnique.setText(LABEL_UNIQUE_KEY.getValue());
        // chkIsUnique.addSelectionListener(columnInfoSelectionChanged);

        // -----
        chkAutoIncrement = new Button(checks, SWT.CHECK);
        chkAutoIncrement.setText(LABEL_AUTO_INCREMENT.getValue());
        chkAutoIncrement.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                updateAutoIncrement();
            }
        });

        // ----- line_2
        UIUtils.createLabel(group, LABEL_PYHGICAL_COLUMN_NAME);
        txtColumnPhysicalName = new Text(group, SWT.BORDER);
        GridData pyhgicalNameGridData = new GridData(GridData.FILL_HORIZONTAL);
        pyhgicalNameGridData.horizontalSpan = 6;
        txtColumnPhysicalName.setLayoutData(pyhgicalNameGridData);
        // txtColumnName.addFocusListener(updateColumnInfoChanged);

        // ----- line_3
        UIUtils.createLabel(group, LABEL_LOGICAL_COLUMN_NAME);
        txtColumnLogicalName = new Text(group, SWT.BORDER);
        GridData logicalNameGridData = new GridData(GridData.FILL_HORIZONTAL);
        logicalNameGridData.horizontalSpan = 6;
        txtColumnLogicalName.setLayoutData(logicalNameGridData);
        // txtColumnLogicalName.addFocusListener(updateColumnInfoChanged);

        // ----- line_4
        UIUtils.createLabel(group, LABEL_TYPE);
        cmbColumnType = new Combo(group, SWT.READ_ONLY);
        // cmbColumnType.addSelectionListener(columnTypeSelectionChanged);
        domains.forEach(domain -> {
            cmbColumnType.add(domain.toString());
            cmbColumnType.setData(domain.toString(), domain);
        });
        dialect.getColumnTypes().forEach(columnType -> {
            cmbColumnType.add(columnType.toString());
            cmbColumnType.setData(columnType.toString(), columnType);
        });
        cmbColumnType.setText(columnModel.getColumnType().toString());
        cmbColumnType.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                updateColumnTypeCmb();
            }
        });

        // -----
        UIUtils.createLabel(group, LABEL_SIZE);
        txtColumnSize = new Text(group, SWT.BORDER);
        txtColumnSize.setLayoutData(UIUtils.createGridDataWithWidth(60));
        txtColumnSize.addVerifyListener(new NumericVerifyListener());

        // -----
        UIUtils.createLabel(group, LABEL_DECIMAL);
        txtDecimal = new Text(group, SWT.BORDER);
        txtDecimal.setLayoutData(UIUtils.createGridDataWithWidth(60));
        txtDecimal.addVerifyListener(new NumericVerifyListener());

        // -----
        chkUnsigned = new Button(group, SWT.CHECK);
        chkUnsigned.setText(LABEL_UNSIGNED.getValue());
        // chkUnsigned.addSelectionListener(columnInfoSelectionChanged);

        // ----- line_5
        UIUtils.createLabel(group, NONE);
        Composite enumArea = new Composite(group, SWT.NONE);
        enumArea.setLayout(new GridLayout(5, false));
        enumArea.setLayoutData(UIUtils.createGridData(6));
        UIUtils.createLabel(enumArea, LABEL_ENUM_SET);

        GridData cmbEnumGridData = new GridData();
        cmbEnumGridData.widthHint = 160;
        cmbEnum = new Combo(enumArea, SWT.DROP_DOWN | SWT.READ_ONLY);
        cmbEnum.setLayoutData(cmbEnumGridData);

        btnEnumEdit = new Button(enumArea, SWT.PUSH);
        btnEnumEdit.setText(LABEL_EDIT.getValue());
        btnEnumEdit.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
                EnumEditDialog dialog = new EnumEditDialog(shell, cmbEnum.getItems());
                if (dialog.open() == Window.OK) {
                    cmbEnum.removeAll();
                    cmbEnum.setItems(dialog.getItems());
                    cmbEnum.select(0);
                    // updateColumnInfo();
                }
            }
        });

        // ----- line_6
        UIUtils.createLabel(group, LABEL_DEFAULT_VALUE);
        txtDefaultValue = new Text(group, SWT.BORDER);
        GridData defaultValueGridData = new GridData(GridData.FILL_HORIZONTAL);
        defaultValueGridData.horizontalSpan = 6;
        txtDefaultValue.setLayoutData(defaultValueGridData);
        // txtDefaultValue.addFocusListener(updateColumnInfoChanged);

        // ----- line_7
        UIUtils.createLabel(group, LABEL_DESCRIPTION);
        txtColumnDescription = new Text(group, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
        txtColumnDescription.setLayoutData(UIUtils.createGridDataWithColspan(6, 90));
        // txtColumnDescription.addFocusListener(updateColumnInfoChanged);

        initialize();
        return composite;
    }

    @Override
    protected void okPressed() {
        columnModel.setPhysicalName(txtColumnPhysicalName.getText());
        columnModel.setLogicalName(txtColumnLogicalName.getText());
        IColumnType columnType = (IColumnType) cmbColumnType.getData(cmbColumnType.getText());
        columnModel.setColumnType(columnType);
        if (columnType.isSizeSupported() && StringUtils.isNotEmpty(txtColumnSize.getText())) {
            columnModel.setColumnSize(Integer.valueOf(txtColumnSize.getText()));
            if (columnType.isDecimalSupported() && StringUtils.isNotEmpty(txtDecimal.getText())) {
                columnModel.setDecimal(Integer.valueOf(txtDecimal.getText()));
            }
        }
        if (columnModel.getColumnType().isEnum()) {
            for (String item : cmbEnum.getItems()) {
                columnModel.getEnumNames().add(item);
            }
        }
        columnModel.setPrimaryKey(chkIsPK.getSelection());
        if (chkIsPK.getSelection()) {
            columnModel.setNotNull(true);
            columnModel.setUniqueKey(false);
            columnModel.setAutoIncrement(chkAutoIncrement.getSelection());
        } else {
            columnModel.setNotNull(chkNotNull.getSelection());
            columnModel.setUniqueKey(chkIsUnique.getSelection());
        }
        if (columnType.isUnsignedSupported()) {
            columnModel.setUnsigned(chkUnsigned.getSelection());
        } else {
            columnModel.setUnsigned(false);
        }
        columnModel.setDefaultValue(txtDefaultValue.getText());
        columnModel.setDescription(txtColumnDescription.getText());
        super.okPressed();
    }

    public ColumnModel getColumn() {
        return columnModel;
    }

    private void initialize() {
        IColumnType columnType = columnModel.getColumnType();

        txtColumnPhysicalName.setText(columnModel.getPhysicalName());
        txtColumnLogicalName.setText(columnModel.getLogicalName());
        cmbColumnType.setText(columnType.getPhysicalName());

        if (columnType.isSizeSupported() && columnModel.getColumnSize() != null) {
            txtColumnSize.setText(String.valueOf(columnModel.getColumnSize()));
            if (columnType.isDecimalSupported() && columnModel.getDecimal() != null) {
                txtDecimal.setText(String.valueOf(columnModel.getDecimal()));
            }
        }
        txtColumnSize.setEnabled(columnType.isSizeSupported());
        txtDecimal.setEditable(columnType.isDecimalSupported());

        columnModel.getEnumNames().forEach(name -> {
            cmbEnum.setText(name);
        });
        cmbEnum.setEnabled(columnType.isEnum());

        chkIsPK.setSelection(columnModel.isPrimaryKey());
        if (chkIsPK.getSelection()) {
            chkNotNull.setSelection(true);
            chkIsUnique.setSelection(false);
            chkAutoIncrement.setSelection(columnModel.isAutoIncrement());
        } else {
            chkNotNull.setSelection(columnModel.isNotNull());
            chkIsUnique.setSelection(columnModel.isUniqueKey());
        }

        if (columnType.isUnsignedSupported()) {
            chkUnsigned.setSelection(columnModel.isUnsigned());
        }
        chkUnsigned.setEnabled(columnType.isUnsignedSupported());

        if (chkAutoIncrement.getSelection()) {
            txtDefaultValue.setEnabled(false);
        } else {
            txtDefaultValue.setEnabled(true);
            txtDefaultValue.setText(columnModel.getDefaultValue());
        }

        txtColumnDescription.setText(columnModel.getDescription());
        updatePkChkBtn();
    }

    private void updatePkChkBtn() {
        if (chkIsPK.getSelection()) {
            chkNotNull.setSelection(chkIsPK.getSelection());
        }
        chkNotNull.setEnabled(!chkIsPK.getSelection());
        chkIsUnique.setEnabled(!chkIsPK.getSelection());
        chkAutoIncrement.setEnabled(chkIsPK.getSelection());
    }

    private void updateColumnTypeCmb() {
        if (cmbColumnType.getSelectionIndex() < 0) {
            return;
        }

        int index = cmbColumnType.getSelectionIndex();
        int domainCount = domains.size();

        IColumnType newColumnType;

        if (index < domainCount) {
            // domain column selected
            DomainModel domain = domains.get(index);
            columnModel.setColumnType(domain);
            columnModel.setColumnSize(domain.getColumnSize());
            columnModel.setDecimal(domain.getDecimal());
            newColumnType = domain;

        } else {
            // column type selected
            List<IColumnType> columnTypes = dialect.getColumnTypes();
            newColumnType = columnTypes.get(index - domainCount);
            columnModel.setColumnType(newColumnType);
        }

        txtColumnSize.setEnabled(newColumnType.isSizeSupported());
        txtDecimal.setEditable((newColumnType.isDecimalSupported()));
        chkUnsigned.setEnabled(newColumnType.isUnsignedSupported());
        cmbEnum.setEnabled(newColumnType.isEnum());
        btnEnumEdit.setEnabled(newColumnType.isEnum());
    }

    private void updateAutoIncrement() {
        txtDefaultValue.setEnabled(!chkAutoIncrement.getSelection());
        if (chkAutoIncrement.getSelection()) {
            txtDefaultValue.setText("");
        }
    }
}
