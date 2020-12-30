package io.github.erde.editor.dialog.table.tabs;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import io.github.erde.IMessages;
import io.github.erde.core.util.UIUtils;
import io.github.erde.dialect.type.IIndexType;
import io.github.erde.dialect.type.IndexType;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.IndexModel;
import io.github.erde.editor.dialog.table.ColumnSelectDialog;
import io.github.erde.editor.dialog.table.ITableEdit;

/**
 * IndexTabCreator.
 *
 * @author modified by parapata
 */
public class IndexTabCreator implements IMessages {

    private ITableEdit tableEdit;

    private boolean indexEditing;
    private int editIndexIndex;

    private org.eclipse.swt.widgets.List lstIndexs;
    private Text txtIndexName;
    private Combo cmbIndexType;
    private org.eclipse.swt.widgets.List lstIndexColumns;

    private Button btnAddIndex;
    private Button btnRemoveIndex;

    private Button btnAddColumn;
    private Button btnRemoveColumn;
    private Button btnUpColumn;
    private Button btnDownColumn;

    public IndexTabCreator(ITableEdit tableEdit, int editIndexIndex, boolean indexEditing) {
        this.tableEdit = tableEdit;
        this.editIndexIndex = editIndexIndex;
        this.indexEditing = indexEditing;
    }

    public void create(Shell shell, TabFolder tabFolder, String tablePyhgicalName) {
        Composite composite = new Composite(tabFolder, SWT.NULL);
        composite.setLayout(new GridLayout(2, false));
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        TabItem tab = new TabItem(tabFolder, SWT.NULL);
        tab.setText(getResource("label.index"));
        tab.setControl(composite);

        createIndexListArea(composite, tablePyhgicalName);
        createIndexColumnListArea(shell, composite);

        if (indexEditing) {
            tabFolder.setSelection(tab);
            if (editIndexIndex >= 0) {
                lstIndexs.select(editIndexIndex);
                indexSelectionChanged();
            } else {
                disableIndexForm();
            }
        } else {
            disableIndexForm();
        }
    }

    private void createIndexListArea(Composite composite, String tablePyhgicalName) {
        Composite indexArea = new Composite(composite, SWT.NULL);
        GridLayout layout = new GridLayout(2, false);
        indexArea.setLayout(layout);
        indexArea.setLayoutData(UIUtils.createGridData(2, GridData.FILL_BOTH));

        lstIndexs = new org.eclipse.swt.widgets.List(indexArea, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        lstIndexs.setLayoutData(new GridData(GridData.FILL_BOTH));
        for (IndexModel index : tableEdit.getIndices()) {
            lstIndexs.add(index.toString());
        }
        lstIndexs.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                indexSelectionChanged();
            }
        });

        Composite indexButtons = new Composite(indexArea, SWT.NULL);
        GridLayout buttonsLayout = new GridLayout(1, false);
        indexButtons.setLayout(buttonsLayout);
        indexButtons.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

        btnAddIndex = new Button(indexButtons, SWT.PUSH);
        btnAddIndex.setText(getResource("dialog.table.addIndex"));
        btnAddIndex.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnAddIndex.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                IndexModel indexModel = new IndexModel();
                indexModel.setIndexType(IndexType.UNIQUE);
                String indexName = String.format("IDX_%s_%d", tablePyhgicalName,
                        (tableEdit.getIndices().size() + 1));
                indexModel.setIndexName(indexName.toUpperCase());
                tableEdit.getIndices().add(indexModel);
                lstIndexs.add(indexModel.toString());
            }
        });

        btnRemoveIndex = new Button(indexButtons, SWT.PUSH);
        btnRemoveIndex.setText(getResource("dialog.table.removeIndex"));
        btnRemoveIndex.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnRemoveIndex.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                int index = lstIndexs.getSelectionIndex();
                lstIndexs.remove(index);
                tableEdit.getIndices().remove(index);
                lstIndexs.select(index - 1);
                indexSelectionChanged();
            }
        });

        new Label(composite, SWT.NULL).setText(getResource("dialog.table.editIndex.indexType"));
        cmbIndexType = new Combo(composite, SWT.READ_ONLY);
        for (IIndexType type : tableEdit.getDialect().getIndexTypes()) {
            cmbIndexType.add(type.getName());
        }

        cmbIndexType.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                IndexModel model = tableEdit.getIndices().get(editIndexIndex);
                model.setIndexType(IndexType.toIndexType(cmbIndexType.getText()));
                lstIndexs.setItem(editIndexIndex, model.toString());
            }
        });

        new Label(composite, SWT.NULL).setText(getResource("dialog.table.editIndex.indexName"));
        txtIndexName = new Text(composite, SWT.BORDER);
        txtIndexName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        txtIndexName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent event) {
                IndexModel model = tableEdit.getIndices().get(editIndexIndex);
                model.setIndexName(txtIndexName.getText().toUpperCase());
                lstIndexs.setItem(editIndexIndex, model.toString());
            }
        });
    }

    private void createIndexColumnListArea(Shell shell, Composite composite) {
        Group indexColumnGroup = new Group(composite, SWT.NULL);
        indexColumnGroup.setText(getResource("dialog.table.editIndex.indexColumns"));
        indexColumnGroup.setLayout(new GridLayout(2, false));
        indexColumnGroup.setLayoutData(UIUtils.createGridData(3, GridData.FILL_BOTH));

        lstIndexColumns = new org.eclipse.swt.widgets.List(indexColumnGroup,
                SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        lstIndexColumns.setLayoutData(UIUtils.createGridData(1, GridData.FILL_BOTH));
        lstIndexColumns.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                updateIndexColumnButtons();
            }
        });

        Composite indexColumnButtons = new Composite(indexColumnGroup, SWT.NULL);
        indexColumnButtons.setLayout(new GridLayout(1, false));
        indexColumnButtons.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

        btnAddColumn = new Button(indexColumnButtons, SWT.PUSH);
        btnAddColumn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnAddColumn.setText(getResource("dialog.table.addColumn"));
        btnAddColumn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                List<String> selectedList = Arrays.asList(lstIndexColumns.getItems());

                List<ColumnModel> items = tableEdit.getColumns()
                        .stream()
                        .filter(predicate -> !selectedList.contains(predicate.getPhysicalName()))
                        .collect(Collectors.toList());

                ColumnSelectDialog dialog = new ColumnSelectDialog(shell, items);
                if (dialog.open() == Window.OK) {
                    dialog.getSelectedColumns().forEach(column -> {
                        String columnName = column.getPhysicalName();
                        IndexModel model = tableEdit.getIndices().get(editIndexIndex);
                        model.getColumns().add(columnName);
                        lstIndexColumns.add(columnName);
                        lstIndexs.setItem(editIndexIndex, model.toString());
                        updateIndexColumnButtons();
                    });
                }
            }
        });

        btnRemoveColumn = new Button(indexColumnButtons, SWT.PUSH);
        btnRemoveColumn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnRemoveColumn.setText(getResource("dialog.table.removeColumn"));
        btnRemoveColumn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                int index = lstIndexColumns.getSelectionIndex();
                if (index >= 0) {
                    IndexModel model = tableEdit.getIndices().get(editIndexIndex);
                    model.getColumns().remove(index);
                    lstIndexColumns.remove(index);
                    lstIndexs.setItem(editIndexIndex, model.toString());
                    lstIndexColumns.select(index - 1);
                    updateIndexColumnButtons();
                }
            }
        });

        btnUpColumn = new Button(indexColumnButtons, SWT.PUSH);
        btnUpColumn.setText(getResource("dialog.table.upColumn"));
        btnUpColumn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnUpColumn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                int index = lstIndexColumns.getSelectionIndex();
                if (index > 0) {
                    IndexModel model = tableEdit.getIndices().get(editIndexIndex);
                    String columnName = model.getColumns().get(index);
                    model.getColumns().remove(index);
                    model.getColumns().add(index - 1, columnName);
                    lstIndexColumns.remove(index);
                    lstIndexColumns.add(columnName, index - 1);
                    lstIndexColumns.select(index - 1);
                    lstIndexs.setItem(editIndexIndex, model.toString());
                    updateIndexColumnButtons();
                }
            }
        });

        btnDownColumn = new Button(indexColumnButtons, SWT.PUSH);
        btnDownColumn.setText(getResource("dialog.table.downColumn"));
        btnDownColumn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnDownColumn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                int index = lstIndexColumns.getSelectionIndex();
                if (index < tableEdit.getIndices().get(editIndexIndex).getColumns().size() - 1) {
                    IndexModel model = tableEdit.getIndices().get(editIndexIndex);
                    String columnName = model.getColumns().get(index);
                    model.getColumns().remove(index);
                    model.getColumns().add(index + 1, columnName);
                    lstIndexColumns.remove(index);
                    lstIndexColumns.add(columnName, index + 1);
                    lstIndexColumns.select(index + 1);
                    lstIndexs.setItem(editIndexIndex, model.toString());
                    updateIndexColumnButtons();
                }
            }
        });
    }

    private void indexSelectionChanged() {
        editIndexIndex = lstIndexs.getSelectionIndex();
        if (editIndexIndex >= 0) {
            IndexModel model = tableEdit.getIndices().get(editIndexIndex);
            txtIndexName.setEnabled(true);
            cmbIndexType.setEnabled(true);
            lstIndexColumns.setEnabled(true);
            lstIndexColumns.removeAll();

            txtIndexName.setText(model.getIndexName());
            cmbIndexType.setText(model.getIndexType().getName());

            btnAddColumn.setEnabled(true);
            btnRemoveIndex.setEnabled(true);

            for (String columnName : model.getColumns()) {
                lstIndexColumns.add(columnName);
            }
        } else {
            disableIndexForm();
        }
    }

    private void updateIndexColumnButtons() {
        btnRemoveColumn.setEnabled(false);
        btnUpColumn.setEnabled(false);
        btnDownColumn.setEnabled(false);

        int index = lstIndexColumns.getSelectionIndex();
        if (index >= 0) {
            btnRemoveColumn.setEnabled(true);
            if (index > 0) {
                btnUpColumn.setEnabled(true);
            } else if (index < lstIndexColumns.getItemCount() - 1) {
                btnDownColumn.setEnabled(true);
            }
        }
    }

    private void disableIndexForm() {
        editIndexIndex = -1;
        txtIndexName.setText("");
        cmbIndexType.setText("");
        lstIndexColumns.removeAll();

        txtIndexName.setEnabled(false);
        cmbIndexType.setEnabled(false);
        lstIndexColumns.setEnabled(false);

        btnRemoveIndex.setEnabled(false);
        btnAddColumn.setEnabled(false);
        btnRemoveColumn.setEnabled(false);
        btnUpColumn.setEnabled(false);
        btnDownColumn.setEnabled(false);
    }
}
