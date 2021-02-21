package io.github.erde.editor.dialog.enumeration;

import static io.github.erde.Resource.*;

import java.util.Arrays;
import java.util.HashSet;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.erde.core.util.UIUtils;

public class EnumEditDialog extends Dialog {

    private Logger logger = LoggerFactory.getLogger(EnumEditDialog.class);

    private static final int EDIT_COLUMN = 0;

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

        // Tableで行がクリックされた場合の処理
        table.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                int index = table.getSelectionIndex();
                if (index < 0) {
                    return;
                }
                // Tableの選択範囲を解除
                table.setSelection(new int[0]);

                TableEditor tableEditor = new TableEditor(table);
                tableEditor.grabHorizontal = true;

                // 選択された行のTableItemを取得
                TableItem item = table.getItem(index);

                // セルエディタを設定
                Text text = new Text(table, SWT.NONE);
                text.setText(item.getText(EDIT_COLUMN));

                // フォーカスが外れた場合、重複チェックを行う
                text.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusLost(FocusEvent event) {
                        Text sourceText = (Text) event.getSource();
                        logger.info("フォーカスロスト:{}", sourceText.getText());

                        // 編集内容反映をテーブルに反映
                        TableItem editItem = table.getItem(index);
                        editItem.setText(EDIT_COLUMN, sourceText.getText());

                        // 重複チェック
                        if (StringUtils.isNotEmpty(text.getText())) {
                            long count = Arrays.asList(table.getItems())
                                    .stream()
                                    .filter(predicate -> predicate.getText().equals(text.getText()))
                                    .count();
                            if (count > 1) {
                                logger.error("重複エラー");
                            }
                        }
                        logger.info("編集モード終了:index={}, text={}", index, sourceText.getText());
                        text.dispose();
                    }
                });
                logger.info("編集モード開始:index={}, text={}", index, text.getText());
                tableEditor.setEditor(text, item, EDIT_COLUMN);
            }
        });
        return parent;
    }

    @Override
    protected void buttonPressed(int buttonId) {
        logger.info("buttonId:{}", buttonId);
        if (0 == buttonId) {
            items = Arrays.asList(viewer.getTable().getItems())
                    .stream()
                    .map(tableItem -> tableItem.getText(0))
                    .filter(predicate -> StringUtils.isNoneEmpty(predicate) && !StringUtils.trim(predicate).isEmpty())
                    .collect(Collectors.toList());
            if (items.size() != new HashSet<>(items).size()) {
                UIUtils.openAlertDialog(ERROR_KEY_DUPLICATE);
                return;
            }
        }
        super.buttonPressed(buttonId);
    }

    public String[] getItems() {
        return items.toArray(new String[0]);
    }
}
