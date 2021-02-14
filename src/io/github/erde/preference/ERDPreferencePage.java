package io.github.erde.preference;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import io.github.erde.Activator;
import io.github.erde.IMessages;
import io.github.erde.core.util.SpinnerFieldEditor;

/**
 * ERDPreferencePage.
 *
 * @author modified by parapata
 */
public class ERDPreferencePage extends PreferencePage implements IMessages, IWorkbenchPreferencePage {

    private BooleanFieldEditor showGrid;
    private SpinnerFieldEditor gridSize;
    private BooleanFieldEditor enabledGrid;
    private BooleanFieldEditor snapToGeometry;
    private BooleanFieldEditor showNotNull;

    @Override
    public void init(IWorkbench workbench) {
    }

    @Override
    protected Control createContents(Composite parent) {
        Composite composite = new Composite(parent, SWT.NULL);
        composite.setLayout(new GridLayout(1, false));
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        // for Layout (Grid)
        Group layoutGroup = new Group(composite, SWT.NULL);
        layoutGroup.setText(getResource("preference.layout"));
        layoutGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        showGrid = new BooleanFieldEditor(Activator.PREF_SHOW_GRID,
                getResource("preference.layout.showGrid"), layoutGroup);
        showGrid.setPropertyChangeListener(new IPropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent event) {
                enabledGrid.setEnabled(showGrid.getBooleanValue(), layoutGroup);
            }
        });

        gridSize = new SpinnerFieldEditor(Activator.PREF_GRID_SIZE, getResource("preference.layout.gridSize"), 1, 100,
                layoutGroup);

        enabledGrid = new BooleanFieldEditor(Activator.PREF_ENABLED_GRID, getResource("preference.layout.enabledGrid"),
                layoutGroup);

        snapToGeometry = new BooleanFieldEditor(Activator.PREF_SNAP_GEOMETRY,
                getResource("preference.layout.snapToGeometry"), layoutGroup);
        layoutGroup.setLayout(new GridLayout(3, false));

        // for Diagram (Grid)
        Group diagramGroup = new Group(composite, SWT.NULL);
        diagramGroup.setText(getResource("preference.diagram"));
        diagramGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        showNotNull = new BooleanFieldEditor(Activator.PREF_SHOW_NOT_NULL,
                getResource("preference.diagram.showNotNull"), diagramGroup);
        diagramGroup.setLayout(new GridLayout(1, false));

        // Initializes values
        fillInitialValues();
        enabledGrid.setEnabled(showGrid.getBooleanValue(), layoutGroup);

        return composite;
    }

    @Override
    protected void performDefaults() {
        super.performDefaults();
        showGrid.loadDefault();
        gridSize.loadDefault();
        enabledGrid.loadDefault();
        snapToGeometry.loadDefault();
        showNotNull.loadDefault();
    }

    @Override
    public boolean performOk() {
        showGrid.store();
        gridSize.store();
        enabledGrid.store();
        snapToGeometry.store();
        showNotNull.store();
        return true;
    }

    private void fillInitialValues() {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();

        showGrid.setPreferenceStore(store);
        showGrid.load();

        gridSize.setPreferenceStore(store);
        gridSize.load();

        enabledGrid.setPreferenceStore(store);
        enabledGrid.load();

        snapToGeometry.setPreferenceStore(store);
        snapToGeometry.load();

        showNotNull.setPreferenceStore(store);
        showNotNull.load();
    }
}
