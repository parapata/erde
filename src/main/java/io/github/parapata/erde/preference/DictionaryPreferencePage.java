package io.github.parapata.erde.preference;

import java.util.List;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import io.github.parapata.erde.ERDPlugin;
import io.github.parapata.erde.core.util.DictionaryEntry;
import io.github.parapata.erde.core.util.NameConverter;
import io.github.parapata.erde.core.util.swt.TableViewerSupport;
import io.github.parapata.erde.editor.dialog.EntryEditDialog;

/**
 * DictionaryPreferencePage.
 *
 * @author modified by parapata
 */
public class DictionaryPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

    private TableViewer viewer;
    private List<DictionaryEntry> models;

    public DictionaryPreferencePage() {
        super("DictionaryPreferencePage");
    }

    @Override
    public void init(IWorkbench workbench) {
    }

    @Override
    public Point computeSize() {
        Point point = super.computeSize();
        return new Point(point.x, 0);
    }

    @Override
    protected Control createContents(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(1, false));

        models = NameConverter.loadFromPreferenceStore(ERDPlugin.getDefault().getPreferenceStore());

        TableViewerSupport<DictionaryEntry> support = new TableViewerSupport<>(models, composite) {
            @Override
            protected DictionaryEntry doAdd() {
                EntryEditDialog dialog = new EntryEditDialog(getShell());
                if (dialog.open() == Window.OK) {
                    return dialog.getEntry();
                }
                return null;
            }

            @Override
            protected void doEdit(DictionaryEntry entry) {
                EntryEditDialog dialog = new EntryEditDialog(getShell(), entry);
                if (dialog.open() == Window.OK) {
                    DictionaryEntry newEntry = dialog.getEntry();
                    entry.logicalName = newEntry.logicalName;
                    entry.physicalName = newEntry.physicalName;
                    entry.partialMatch = newEntry.partialMatch;
                }
            }
        };

        support.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
        this.viewer = support.getTableViewer();

        return composite;
    }

    @Override
    public boolean performCancel() {
        models.clear();
        models.addAll(NameConverter.loadDefaultDictionary());
        viewer.refresh();
        return true;
    }

    @Override
    public boolean performOk() {
        NameConverter.saveToPreferenceStore(ERDPlugin.getDefault().getPreferenceStore(), models);
        return true;
    }

    @Override
    protected void performDefaults() {
        List<DictionaryEntry> defaultEntries = NameConverter.loadDefaultDictionary();
        models.clear();
        models.addAll(defaultEntries);
        this.viewer.refresh();
    }
}
