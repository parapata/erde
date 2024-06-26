package io.github.parapata.erde.editor.dialog.table.composite;

import static io.github.parapata.erde.Resource.*;

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
import org.eclipse.swt.widgets.Text;

import io.github.parapata.erde.core.util.swt.UIUtils;
import io.github.parapata.erde.dialect.type.IIndexType;
import io.github.parapata.erde.dialect.type.IndexType;
import io.github.parapata.erde.editor.diagram.model.ColumnModel;
import io.github.parapata.erde.editor.diagram.model.IndexModel;
import io.github.parapata.erde.editor.dialog.table.ITableEdit;
import io.github.parapata.erde.editor.dialog.table.IndexColumnSelectDialog;

/**
 * IndexComposite.
 *
 * @author modified by parapata
 */
public class IndexComposite extends Composite {

    private ITableEdit tableEdit;

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

    public IndexComposite(Composite parent, ITableEdit tableEdit, int editIndexIndex) {
        super(parent, SWT.NONE);
        this.tableEdit = tableEdit;

        create(editIndexIndex);
    }

    private void create(int editIndexIndex) {
        setLayout(new GridLayout(2, false));
        setLayoutData(new GridData(GridData.FILL_BOTH));

        createIndexListArea(editIndexIndex);
        createIndexColumnListArea();

        if (editIndexIndex >= 0) {
            lstIndexs.select(editIndexIndex);
            indexSelectionChanged();
        } else {
            disableIndexForm();
        }
    }

    private void createIndexListArea(int editIndexIndex) {
        Composite indexArea = new Composite(this, SWT.NONE);
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

        Composite indexButtons = new Composite(indexArea, SWT.NONE);
        GridLayout buttonsLayout = new GridLayout(1, false);
        indexButtons.setLayout(buttonsLayout);
        indexButtons.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

        btnAddIndex = new Button(indexButtons, SWT.PUSH);
        btnAddIndex.setText(LABEL_ADD.getValue());
        btnAddIndex.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnAddIndex.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                IndexModel indexModel = new IndexModel();
                indexModel.setIndexType(IndexType.UNIQUE);
                String indexName = String.format("IDX_%s_%d", tableEdit.getPhysicalName(),
                        (tableEdit.getIndices().size() + 1));
                indexModel.setIndexName(indexName.toUpperCase());
                tableEdit.getIndices().add(indexModel);
                lstIndexs.add(indexModel.toString());
            }
        });

        btnRemoveIndex = new Button(indexButtons, SWT.PUSH);
        btnRemoveIndex.setText(LABEL_DELETE.getValue());
        btnRemoveIndex.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnRemoveIndex.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                int index = lstIndexs.getSelectionIndex();
                lstIndexs.remove(index);
                tableEdit.getIndices().remove(index);

                int size = lstIndexs.getItemCount();
                if (size > 0) {
                    if (index >= size) {
                        lstIndexs.setSelection(index - 1);
                    } else {
                        lstIndexs.setSelection(index);
                    }
                } else {
                    lstIndexs.setSelection(-1);
                }

                indexSelectionChanged();
            }
        });

        new Label(this, SWT.NONE).setText(LABEL_INDEX_TYPE.getValue());
        cmbIndexType = new Combo(this, SWT.READ_ONLY);
        for (IIndexType type : tableEdit.getDialect().getIndexTypes()) {
            cmbIndexType.add(type.getName());
        }

        cmbIndexType.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                int index = lstIndexs.getSelectionIndex();
                IndexModel model = tableEdit.getIndices().get(index);
                model.setIndexType(IndexType.toIndexType(cmbIndexType.getText()));
                lstIndexs.setItem(index, model.toString());
            }
        });

        new Label(this, SWT.NONE).setText(LABEL_INDEX_NAME.getValue());
        txtIndexName = new Text(this, SWT.BORDER);
        txtIndexName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        txtIndexName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent event) {
                int index = lstIndexs.getSelectionIndex();
                IndexModel model = tableEdit.getIndices().get(index);
                model.setIndexName(txtIndexName.getText().toUpperCase());
                lstIndexs.setItem(index, model.toString());
            }
        });
    }

    private void createIndexColumnListArea() {
        Group indexColumnGroup = new Group(this, SWT.NONE);
        indexColumnGroup.setText(LABEL_INDEX_COLUMNS.getValue());
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

        Composite indexColumnButtons = new Composite(indexColumnGroup, SWT.NONE);
        indexColumnButtons.setLayout(new GridLayout(1, false));
        indexColumnButtons.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

        btnAddColumn = new Button(indexColumnButtons, SWT.PUSH);
        btnAddColumn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnAddColumn.setText(LABEL_ADD.getValue());
        btnAddColumn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                List<String> selectedList = Arrays.asList(lstIndexColumns.getItems());

                List<ColumnModel> items = tableEdit.getColumns()
                        .stream()
                        .filter(predicate -> !selectedList.contains(predicate.getPhysicalName()))
                        .collect(Collectors.toList());

                IndexColumnSelectDialog dialog = new IndexColumnSelectDialog(getShell(), items);
                if (dialog.open() == Window.OK) {
                    dialog.getSelectedColumns().forEach(column -> {
                        int index = lstIndexs.getSelectionIndex();
                        String columnName = column.getPhysicalName();
                        IndexModel model = tableEdit.getIndices().get(index);
                        model.getColumns().add(columnName);
                        lstIndexColumns.add(columnName);
                        lstIndexs.setItem(index, model.toString());
                        updateIndexColumnButtons();
                    });
                }
            }
        });

        btnRemoveColumn = new Button(indexColumnButtons, SWT.PUSH);
        btnRemoveColumn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnRemoveColumn.setText(LABEL_DELETE.getValue());
        btnRemoveColumn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                int index = lstIndexColumns.getSelectionIndex();
                if (index > -1) {
                    int i = lstIndexs.getSelectionIndex();
                    IndexModel model = tableEdit.getIndices().get(i);
                    model.getColumns().remove(index);
                    lstIndexColumns.remove(index);
                    lstIndexs.setItem(i, model.toString());

                    int size = lstIndexColumns.getItemCount();
                    if (size > 0) {
                        if (index >= size) {
                            lstIndexColumns.setSelection(index - 1);
                        } else {
                            lstIndexColumns.setSelection(index);
                        }
                    } else {
                        lstIndexColumns.setSelection(-1);
                    }
                    updateIndexColumnButtons();
                }
            }
        });

        btnUpColumn = new Button(indexColumnButtons, SWT.PUSH);
        btnUpColumn.setText(LABEL_UP_COLUMN.getValue());
        btnUpColumn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnUpColumn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                int index = lstIndexColumns.getSelectionIndex();
                if (index > 0) {
                    int i = lstIndexs.getSelectionIndex();
                    IndexModel model = tableEdit.getIndices().get(i);
                    String columnName = model.getColumns().get(index);
                    model.getColumns().remove(index);
                    model.getColumns().add(index - 1, columnName);
                    lstIndexColumns.remove(index);
                    lstIndexColumns.add(columnName, index - 1);
                    lstIndexColumns.select(index - 1);
                    lstIndexs.setItem(i, model.toString());
                    updateIndexColumnButtons();
                }
            }
        });

        btnDownColumn = new Button(indexColumnButtons, SWT.PUSH);
        btnDownColumn.setText(LABEL_DOWN_COLUMN.getValue());
        btnDownColumn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnDownColumn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                int index = lstIndexColumns.getSelectionIndex();
                int i = lstIndexs.getSelectionIndex();
                if (index < tableEdit.getIndices().get(i).getColumns().size() - 1) {
                    IndexModel model = tableEdit.getIndices().get(i);
                    String columnName = model.getColumns().get(index);
                    model.getColumns().remove(index);
                    model.getColumns().add(index + 1, columnName);
                    lstIndexColumns.remove(index);
                    lstIndexColumns.add(columnName, index + 1);
                    lstIndexColumns.select(index + 1);
                    lstIndexs.setItem(i, model.toString());
                    updateIndexColumnButtons();
                }
            }
        });
    }

    private void indexSelectionChanged() {
        int index = lstIndexs.getSelectionIndex();
        if (index >= 0) {
            IndexModel model = tableEdit.getIndices().get(index);
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
        int index = lstIndexColumns.getSelectionIndex();
        int size = lstIndexColumns.getItemCount();

        btnAddColumn.setEnabled(true);
        btnRemoveColumn.setEnabled(size > -1);
        btnUpColumn.setEnabled(index > 0);
        btnDownColumn.setEnabled(index > -1 && index < size - 1);
    }

    private void disableIndexForm() {
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
