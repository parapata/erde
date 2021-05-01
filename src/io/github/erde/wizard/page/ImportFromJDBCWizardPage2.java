package io.github.erde.wizard.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import io.github.erde.Resource;
import io.github.erde.wizard.ImportFromJDBCWizard;
import io.github.erde.wizard.task.TableLoaderTask;

/**
 * ImportFromJDBCWizardPage2.
 *
 * @author modified by parapata
 */
public class ImportFromJDBCWizardPage2 extends WizardPage {

    private Tree tree;

    public ImportFromJDBCWizardPage2() {
        super(Resource.WIZARD_NEW_IMPORT_TITLE.getValue());
        setTitle(Resource.WIZARD_NEW_IMPORT_TITLE.getValue());
        setMessage(Resource.WIZARD_NEW_IMPORT_MESSAGE.getValue());
    }

    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NULL);
        container.setLayout(new GridLayout(4, false));
        container.setLayoutData(new GridData(GridData.FILL_BOTH));

        createTree(container);

        doValidate();
        setControl(container);
    }

    // 前ページから値を得て現在のページに反映させたい場合は、setVisibleをオーバーライド
    @Override
    public void setVisible(boolean visible) {
        ImportFromJDBCWizard wizard = (ImportFromJDBCWizard) getWizard();
        ImportFromJDBCWizardPage1 page1 = (ImportFromJDBCWizardPage1) wizard.getPreviousPage(this);

        TreeItem table = tree.getItem(0);
        table.removeAll();

        if (visible) {
            try {
                TableLoaderTask task = new TableLoaderTask(page1.getJDBCConnection());
                IWizardContainer container = getContainer();
                container.run(true, true, task);

                task.getTableNames().forEach(tableName -> {
                    TreeItem item = new TreeItem(table, SWT.NULL);
                    item.setText(tableName);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        table.setExpanded(true);
        setPageComplete(tree.getItem(0).getChecked());
        super.setVisible(visible);
    }

    public List<String> getSelectionTables() {
        List<String> selections = new ArrayList<>();
        for (TreeItem root : tree.getSelection()) {
            for (TreeItem leaf : root.getItems()) {
                if (leaf.getChecked()) {
                    selections.add(leaf.getText());
                }
            }
        }
        return selections;
    }

    private void createTree(Composite parent) {
        tree = new Tree(parent, SWT.BORDER | SWT.CHECK | SWT.OPEN);
        tree.setLayout(new GridLayout());
        tree.setLayoutData(new GridData(GridData.FILL_BOTH));
        tree.addListener(SWT.Selection, event -> {
            TreeItem item = (TreeItem) event.item;
            for (TreeItem childItem : item.getItems()) {
                childItem.setChecked(item.getChecked());
            }
            TreeItem parrentItem = item.getParentItem();
            if (parrentItem != null) {
                if (item.getChecked()) {
                    parrentItem.setChecked(true);
                } else {
                    long count = Arrays.asList(parrentItem.getItems())
                            .stream()
                            .filter(ddd -> ddd.getChecked())
                            .count();
                    if (count == 0) {
                        parrentItem.setChecked(false);
                    }
                }
            }
            setPageComplete(tree.getItem(0).getChecked());
        });
        // ルートとなる要素を追加
        TreeItem table = new TreeItem(tree, SWT.NULL);
        table.setText("Table");
    }

    private void doValidate() {
        if (tree.getSelection() == null || tree.getSelection().length == 0) {
            setPageComplete(false);
            return;
        }
        setPageComplete(true);
    }
}
