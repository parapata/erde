package io.github.parapata.erde.editor.dialog;

import static io.github.parapata.erde.Resource.*;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.parapata.erde.ERDPlugin;
import io.github.parapata.erde.ICON;
import io.github.parapata.erde.core.util.StringUtils;
import io.github.parapata.erde.core.util.swt.UIUtils;
import io.github.parapata.erde.dialect.IDialect;
import io.github.parapata.erde.dialect.type.ColumnType;
import io.github.parapata.erde.dialect.type.IColumnType;
import io.github.parapata.erde.editor.diagram.model.BaseEntityModel;
import io.github.parapata.erde.editor.diagram.model.ColumnModel;
import io.github.parapata.erde.editor.diagram.model.DomainModel;
import io.github.parapata.erde.editor.diagram.model.RootModel;
import io.github.parapata.erde.editor.diagram.model.TableModel;

/**
 * DomainEditDialog.
 *
 * @author modified by parapata
 */
public class DomainEditDialog extends Dialog {

    private Logger logger = LoggerFactory.getLogger(DomainEditDialog.class);

    private TableViewer viewer;
    private RootModel rootModel;
    private IDialect dialect;
    private List<DomainModel> domainModels = new ArrayList<>();
    private DomainModel editingModel;

    private Text txtDomainName;
    private Combo cmbColumnType;
    private Text txtColumnSize;
    private Text txtDecimalSize;
    private Button btnChkUnsigned;

    private Button btnRemoveButton;
    private Button btnAddButton;

    public DomainEditDialog(Shell parentShell, RootModel rootModel, DomainModel editDomain) {
        super(parentShell);
        setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX | SWT.MIN);
        for (DomainModel model : rootModel.getDomains()) {
            DomainModel clonedModel = model.clone();
            if (editDomain != null && StringUtils.equals(model.getId(), editDomain.getId())) {
                editingModel = clonedModel;
            }
            domainModels.add(clonedModel);
        }
        this.rootModel = rootModel;
        this.dialect = rootModel.getDialectProvider().getDialect();
    }

    @Override
    protected Point getInitialSize() {
        Point point = super.getInitialSize();
        point.y = 450;
        return point;
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(DIALOG_DOMAIN_TITLE.getValue());
        newShell.setImage(ERDPlugin.getImage(ICON.TABLE.getPath()));
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(2, false));
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        createTableArea(composite);
        if (editingModel != null) {
            int i = 0;
            for (DomainModel model : domainModels) {
                if (StringUtils.equals(model.getId(), editingModel.getId())) {
                    viewer.getTable().setSelection(i);
                    break;
                }
                i++;
            }
        }
        createEditArea(composite);

        return composite;
    }

    private void createTableArea(Composite composite) {
        viewer = new TableViewer(composite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
        Table table = viewer.getTable();
        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        table.setLayoutData(new GridData(GridData.FILL_BOTH));

        UIUtils.createColumn(table, LABEL_ID, 0);
        UIUtils.createColumn(table, LABEL_DOMAIN_NAME, 250);
        UIUtils.createColumn(table, LABEL_TYPE, 200);
        UIUtils.createColumn(table, LABEL_UNSIGNED, 100);

        viewer.setContentProvider(new ArrayContentProvider());
        viewer.setLabelProvider(new ITableLabelProvider() {
            @Override
            public Image getColumnImage(Object element, int columnIndex) {
                return null;
            }

            @Override
            public String getColumnText(Object element, int columnIndex) {
                DomainModel model = (DomainModel) element;
                String value = null;
                switch (columnIndex) {
                    case 0:
                        value = model.getId();
                        break;
                    case 1:
                        value = model.getDomainName();
                        break;
                    case 2:
                        value = model.getPhysicalName();
                        List<String> args = new ArrayList<>();
                        if (model.isSizeSupported() && model.getColumnSize() != null) {
                            args.add(String.valueOf(model.getColumnSize()));
                            if (model.isDecimalSupported() && model.getDecimal() != null) {
                                args.add(String.valueOf(model.getDecimal()));
                            }
                        }
                        if (!args.isEmpty()) {
                            value = String.format("%s(%s)", value, String.join(",", args));
                        }
                        break;
                    case 3:
                        value = model.isUnsigned() ? Boolean.TRUE.toString() : "";
                        break;
                    default:
                }
                return value;
            }

            @Override
            public void addListener(ILabelProviderListener listener) {
            }

            @Override
            public void dispose() {
            }

            @Override
            public boolean isLabelProperty(Object element, String property) {
                return false;
            }

            @Override
            public void removeListener(ILabelProviderListener listener) {
            }
        });
        viewer.setInput(domainModels);

        viewer.addSelectionChangedListener(event -> {
            editAreaSelectionChanged();
        });

        // ----------
        Composite buttons = new Composite(composite, SWT.NONE);
        GridLayout buttonsLayout = new GridLayout(1, false);
        buttonsLayout.horizontalSpacing = 0;
        buttonsLayout.verticalSpacing = 0;
        buttonsLayout.marginHeight = 0;
        buttonsLayout.marginWidth = 2;
        buttons.setLayout(buttonsLayout);
        buttons.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

        btnAddButton = new Button(buttons, SWT.PUSH);
        btnAddButton.setText(LABEL_ADD.getValue());
        btnAddButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnAddButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                IColumnType defaultType = dialect.getDefaultColumnType();
                String domainName = String.format("%s_%d", LABEL_DOMAIN_NAME.getValue(), domainModels.size() + 1);

                DomainModel domain = DomainModel.newInstance(rootModel.getDialectProvider(), null, domainName,
                        defaultType, null, null, false);
                domainModels.add(domain);
                viewer.refresh();
            }
        });

        // ----------
        btnRemoveButton = new Button(buttons, SWT.PUSH);
        btnRemoveButton.setText(LABEL_DELETE.getValue());
        btnRemoveButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnRemoveButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                IStructuredSelection sel = (IStructuredSelection) viewer.getSelection();

                @SuppressWarnings("unchecked")
                List<DomainModel> domains = sel.toList();
                for (DomainModel domain : domains) {
                    for (BaseEntityModel entity : rootModel.getChildren()) {
                        if (entity instanceof TableModel) {
                            TableModel table = (TableModel) entity;
                            for (ColumnModel column : table.getColumns()) {
                                if (column.getColumnType().isDomain()
                                        && domain.getId().equals(((DomainModel) column.getColumnType()).getId())) {
                                    UIUtils.openAlertDialog(ERROR_DIALOG_ALERT_DOMAIN_DELETE_ERROR);
                                    return;
                                }
                            }
                        }
                    }
                }
                domainModels.removeAll(domains);

                int index = table.getSelectionIndex() - 1;
                if (index > -1) {
                    table.select(index);
                }
                viewer.refresh();
            }
        });
        btnRemoveButton.setEnabled(table.getItemCount() > 0);
    }

    private void createEditArea(Composite composite) {
        Composite editArea = new Composite(composite, SWT.NONE);
        editArea.setLayout(new GridLayout(7, false));
        editArea.setLayoutData(UIUtils.createGridData(2));

        // ----------
        new Label(editArea, SWT.NONE).setText(LABEL_DOMAIN_NAME.getValue());
        txtDomainName = new Text(editArea, SWT.BORDER);
        txtDomainName.setLayoutData(UIUtils.createGridData(6));
        txtDomainName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent event) {
                updateDomainModel();
            }
        });

        // ----------
        new Label(editArea, SWT.NONE).setText(LABEL_TYPE.getValue());
        cmbColumnType = new Combo(editArea, SWT.READ_ONLY);
        for (int i = 0; i < dialect.getColumnTypes().size(); i++) {
            cmbColumnType.add(dialect.getColumnTypes().get(i).toString());
            cmbColumnType.setData(String.valueOf(i), dialect.getColumnTypes().get(i));
        }
        cmbColumnType.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                updateDomainModel();
                editAreaSelectionChanged();
            }
        });
        cmbColumnType.setLayoutData(UIUtils.createGridData(1));

        // ----------
        new Label(editArea, SWT.NONE).setText(LABEL_SIZE.getValue());
        txtColumnSize = new Text(editArea, SWT.BORDER);
        txtColumnSize.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent event) {
                updateDomainModel();
            }
        });
        GridData columnSizeGrid = new GridData();
        columnSizeGrid.widthHint = 50;
        txtColumnSize.setLayoutData(columnSizeGrid);

        // ----------
        new Label(editArea, SWT.NONE).setText(LABEL_DECIMAL.getValue());
        txtDecimalSize = new Text(editArea, SWT.BORDER);
        txtDecimalSize.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent event) {
                updateDomainModel();
            }
        });
        GridData decimalSizeGrid = new GridData();
        decimalSizeGrid.widthHint = 50;
        txtDecimalSize.setLayoutData(decimalSizeGrid);

        // ----------
        btnChkUnsigned = new Button(editArea, SWT.CHECK);
        GridData unsignedGrid = new GridData();
        btnChkUnsigned.setLayoutData(unsignedGrid);
        btnChkUnsigned.setText(LABEL_UNSIGNED.getValue());
        btnChkUnsigned.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                updateDomainModel();
            }
        });

        editAreaSelectionChanged();
    }

    private void editAreaSelectionChanged() {
        logger.info("Call editAreaSelectionChanged");
        IStructuredSelection sel = (IStructuredSelection) viewer.getSelection();
        btnRemoveButton.setEnabled(!sel.isEmpty());
        if (sel.isEmpty()) {
            editingModel = null;
            txtDomainName.setText("");
            txtDomainName.setEnabled(false);
            cmbColumnType.setText("");
            cmbColumnType.setEnabled(false);
            txtColumnSize.setText("");
            txtColumnSize.setEnabled(false);
            txtDecimalSize.setText("");
            txtDecimalSize.setEnabled(false);
            btnChkUnsigned.setSelection(false);
            btnChkUnsigned.setEnabled(false);
        } else {
            editingModel = (DomainModel) sel.getFirstElement();
            txtDomainName.setEnabled(true);
            txtDomainName.setText(editingModel.getDomainName());
            cmbColumnType.setEnabled(true);
            cmbColumnType.setText(editingModel.toString());

            txtColumnSize.setEnabled(editingModel.isSizeSupported());
            if (editingModel.isSizeSupported()) {
                if (editingModel.getColumnSize() == null) {
                    txtColumnSize.setText("");
                } else {
                    txtColumnSize.setText(String.valueOf(editingModel.getColumnSize()));
                }
            }

            txtDecimalSize.setEnabled(editingModel.isDecimalSupported());
            if (editingModel.isDecimalSupported()) {
                if (editingModel.getDecimal() == null) {
                    txtDecimalSize.setText("");
                } else {
                    txtDecimalSize.setText(String.valueOf(editingModel.getDecimal()));
                }
            }

            if (editingModel.isUnsignedSupported()) {
                btnChkUnsigned.setSelection(editingModel.isUnsigned());
            } else {
                btnChkUnsigned.setSelection(false);
            }
            btnChkUnsigned.setEnabled(editingModel.isUnsignedSupported());

            // ドロップダウンの設定
            for (int i = 0; i < cmbColumnType.getItemCount(); i++) {
                ColumnType data = (ColumnType) cmbColumnType.getData(String.valueOf(i));
                if (data.getPhysicalName().equals(editingModel.getPhysicalName())) {
                    cmbColumnType.select(i);
                    break;
                }
            }
        }
    }

    private void updateDomainModel() {
        logger.info("Call updateDomainModel");
        editingModel.setDomainName(txtDomainName.getText());
        int selectedIndex = cmbColumnType.getSelectionIndex();
        if (selectedIndex > -1) {
            ColumnType selectedColumnType = (ColumnType) dialect.getColumnTypes().get(selectedIndex);
            editingModel.setColumnType(selectedColumnType);
        }

        // -----
        String columnSize = txtColumnSize.getText();
        if (StringUtils.isNotEmpty(columnSize)) {
            editingModel.setColumnSize(Integer.valueOf(columnSize));
        } else {
            editingModel.setColumnSize(null);
        }
        txtColumnSize.setEnabled(editingModel.isSizeSupported());
        if (!editingModel.isSizeSupported()) {
            txtColumnSize.setText("");
            editingModel.setColumnSize(null);
        }

        // -----
        String decimalSize = txtDecimalSize.getText();
        if (StringUtils.isNotEmpty(decimalSize)) {
            editingModel.setDecimal(Integer.valueOf(txtDecimalSize.getText()));
        } else {
            editingModel.setDecimal(null);
        }
        txtDecimalSize.setEnabled(editingModel.isDecimalSupported());
        if (!editingModel.isDecimalSupported()) {
            txtDecimalSize.setText("");
            editingModel.setDecimal(null);
        }

        // -----
        editingModel.setUnsigned(btnChkUnsigned.getSelection());

        viewer.refresh();
    }

    public List<DomainModel> getResult() {
        return this.domainModels;
    }

}
