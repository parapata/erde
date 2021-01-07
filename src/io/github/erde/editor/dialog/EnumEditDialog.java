package io.github.erde.editor.dialog;

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

    public EnumEditDialog(Shell parentShell) {
        super(parentShell);
    }

    @Override
    protected Point getInitialSize() {
        Point point = super.getInitialSize();
        point.y = 450;
        return point;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        // return super.createDialogArea(parent);

        Shell shell = super.getShell();
        shell.setText("Enum設定");
        shell.setLayout(new FillLayout());

        TableViewer viewer = new TableViewer(parent,
                SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
        Table table = viewer.getTable();
        table.setLayout(new GridLayout(1, false));
        table.setLayoutData(new GridData(GridData.FILL_BOTH));

        // Tableウィジェットを生成
        // Table table = new Table(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        // カラムを設定
        String[] cols = { "名前" };
        for (int i = 0; i < cols.length; i++) {
            TableColumn col = new TableColumn(table, SWT.LEFT);
            col.setText(cols[i]);
            col.setWidth (350);
        }

        // 行データを追加
        TableItem item = new TableItem(table, SWT.NULL);
        item.setText(0, "Name1");
        item.setText(1, "Name2");

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
}
