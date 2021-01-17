package io.github.erde.editor.dialog;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import io.github.erde.IMessages;

public class EnumEditDialog extends Dialog implements IMessages {

    private List<String> items;

    private TableViewer viewer;

    public EnumEditDialog(Shell parentShell, String[] items) {
        super(parentShell);
        this.items = Arrays.asList(items);
    }

    @Override
    protected Point getInitialSize() {
        Point point = super.getInitialSize();
        point.y = 450;
        return point;
    }

    @Override
    protected Control createDialogArea(Composite parent) {

        Shell shell = super.getShell();
        shell.setText("Enum設定");
        shell.setLayout(new FillLayout());

        viewer = new TableViewer(parent,
                SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);

        Table table = viewer.getTable();
        table.setLayout(new GridLayout(1, false));
        table.setLayoutData(new GridData(GridData.FILL_BOTH));

        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        // カラムを設定
        TableColumn column = new TableColumn(table, SWT.LEFT);
        column.setText("名前");
        column.setWidth(360);

        // 行データを追加
        for (int i = 0; i < 64; i++) {
            TableItem item = new TableItem(table, SWT.NULL | SWT.FILL);
            if (i < items.size()) {
                item.setText(0, items.get(i));
            } else {
                item.setText(0, "");
            }
        }

        // TableEditorを生成
        TableEditor tableEditor = new TableEditor(table);
        tableEditor.grabHorizontal = true;

        // Tableで行がクリックされた場合の処理
        table.addSelectionListener(new SelectionAdapter() {
            // インデックス=1のカラムを編集する
            private static final int EDIT_COLUMN = 0;

            public void widgetSelected(SelectionEvent event) {
                int index = table.getSelectionIndex();
                if (index == -1) {
                    return;
                }
                // Tableの選択範囲を解除
                table.setSelection(new int[0]);

                // 選択された行のTableItemを取得
                TableItem item = table.getItem(index);

                // セルエディタを設定
                Text text = new Text(table, SWT.NONE);
                text.setText(item.getText(EDIT_COLUMN));

                // フォーカスが外れたときの処理
                text.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent e) {
                        TableItem item = tableEditor.getItem();
                        item.setText(EDIT_COLUMN, text.getText());
                        text.dispose();
                    }
                });
                tableEditor.setEditor(text, item, EDIT_COLUMN);
                text.setFocus();
                text.selectAll();
            }
        });
        return parent;
    }

    @Override
    protected void buttonPressed(int buttonId) {
        items = Arrays.asList(viewer.getTable().getItems())
                .stream()
                .map(tableItem -> tableItem.getText(0))
                .filter(predicate -> StringUtils.isNoneEmpty(predicate) && !StringUtils.trim(predicate).isEmpty())
                .collect(Collectors.toList());
        super.buttonPressed(buttonId);
    }

    public String[] getItems() {
        return items.toArray(new String[0]);
    }
}