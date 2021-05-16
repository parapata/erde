package io.github.erde.preference;

import static io.github.erde.Resource.*;
import static io.github.erde.preference.ERDPreferenceKey.*;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import io.github.erde.ERDPlugin;
import io.github.erde.core.util.SpinnerFieldEditor;

/**
 * ERDPreferencePage.
 *
 * @author modified by parapata
 */
public class ERDPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

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
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(1, false));
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        // for Layout (Grid)
        Group layoutGroup = new Group(composite, SWT.NONE);
        layoutGroup.setText(PREFERENCE_LAYOUT.getValue());
        layoutGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        showGrid = new BooleanFieldEditor(SHOW_GRID, PREFERENCE_LAYOUT_SHOW_GRID.getValue(), layoutGroup);
        showGrid.setPropertyChangeListener(event -> enabledGrid.setEnabled(showGrid.getBooleanValue(), layoutGroup));

        gridSize = new SpinnerFieldEditor(GRID_SIZE, PREFERENCE_LAYOUT_GRID_SIZE.getValue(), 1, 100, layoutGroup);

        enabledGrid = new BooleanFieldEditor(ENABLED_GRID, PREFERENCE_LAYOUT_ENABLED_GRID.getValue(), layoutGroup);

        snapToGeometry = new BooleanFieldEditor(SNAP_GEOMETRY, PREFERENCE_LAYOUT_SNAP_TO_GEOMETRY.getValue(),
                layoutGroup);
        layoutGroup.setLayout(new GridLayout(3, false));

        // for Diagram (Grid)
        Group diagramGroup = new Group(composite, SWT.NONE);
        diagramGroup.setText(PREFERENCE_DIAGRAM.getValue());
        diagramGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        showNotNull = new BooleanFieldEditor(SHOW_NOT_NULL, PREFERENCE_DIAGRAM_SHOW_NOT_NULL.getValue(), diagramGroup);
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
        IPreferenceStore store = ERDPlugin.getDefault().getPreferenceStore();

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
