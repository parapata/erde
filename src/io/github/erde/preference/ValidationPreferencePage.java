package io.github.erde.preference;

import static io.github.erde.Resource.*;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import io.github.erde.Activator;
import io.github.erde.Resource;

/**
 * ValidationPreferencePage.
 *
 * @author modified by parapata
 */
public class ValidationPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

    private static final String[][] VALIDATION_LEVELS = new String[][] {
            { PREF_VALIDATION_LEVEL_WARN.getValue(), Activator.LEVEL_WARNING },
            { PREF_VALIDATION_LEVEL_ERR.getValue(), Activator.LEVEL_ERROR },
            { PREF_VALIDATION_LEVEL_IGNORE.getValue(), Activator.LEVEL_IGNORE }, };

    public ValidationPreferencePage() {
        super(GRID);
        setPreferenceStore(Activator.getDefault().getPreferenceStore());
    }

    @Override
    public void init(IWorkbench workbench) {
    }

    @Override
    protected void createFieldEditors() {
        // TODO 設定ファイルから読込たい
        setTitle("ERDEditor");

        Composite parent = getFieldEditorParent();

        addField(new BooleanFieldEditor(ERDPreferenceKey.VALIDATE_ON_SAVE, Resource.PREF_VALIDATION.getValue(),
                parent));

        addField(new ComboFieldEditor(ERDPreferenceKey.VALIDATE_PHYSICAL_TABLE_NAME_REQUIRED,
                PREF_VALIDATION_LOGICAL_TABLE_NAME_REQUIRED.getValue(), VALIDATION_LEVELS, parent));
        addField(new ComboFieldEditor(ERDPreferenceKey.VALIDATE_PHYSICAL_TABLE_NAME_DUPLICATED,
                PREF_VALIDATION_TABLE_NAME_DUPLICATED.getValue(), VALIDATION_LEVELS, parent));
        addField(new ComboFieldEditor(ERDPreferenceKey.VALIDATE_LOGICAL_TABLE_NAME_REQUIRED,
                PREF_VALIDATION_LOGICAL_TABLE_NAME_REQUIRED.getValue(), VALIDATION_LEVELS, parent));
        addField(new ComboFieldEditor(ERDPreferenceKey.VALIDATE_LOGICAL_TABLE_NAME_DUPLICATED,
                PREF_VALIDATION_LOGICAL_TABLE_NAME_DUPLICATED.getValue(), VALIDATION_LEVELS, parent));

        addField(new ComboFieldEditor(ERDPreferenceKey.VALIDATE_PHYSICAL_COLUMN_NAME_REQUIRED,
                PREF_VALIDATION_LOGICAL_COLUMN_NAME_REQUIRED.getValue(), VALIDATION_LEVELS, parent));
        addField(new ComboFieldEditor(ERDPreferenceKey.VALIDATE_PHYSICAL_COLUMN_NAME_DUPLICATED,
                PREF_VALIDATION_LOGICAL_COLUMN_NAME_DUPLICATED.getValue(), VALIDATION_LEVELS, parent));
        addField(new ComboFieldEditor(ERDPreferenceKey.VALIDATE_LOGICAL_COLUMN_NAME_REQUIRED,
                PREF_VALIDATION_LOGICAL_COLUMN_NAME_REQUIRED.getValue(), VALIDATION_LEVELS, parent));
        addField(new ComboFieldEditor(ERDPreferenceKey.VALIDATE_LOGICAL_COLUMN_NAME_DUPLICATED,
                PREF_VALIDATION_LOGICAL_COLUMN_NAME_DUPLICATED.getValue(), VALIDATION_LEVELS, parent));

        addField(new ComboFieldEditor(ERDPreferenceKey.VALIDATE_NO_COLUMNS,
                PREF_VALIDATION_NO_COLUMNS.getValue(), VALIDATION_LEVELS, parent));
        addField(new ComboFieldEditor(ERDPreferenceKey.VALIDATE_PRIMARY_KEY,
                PREF_VALIDATION_PRIMARY_KEY.getValue(), VALIDATION_LEVELS, parent));

        addField(new ComboFieldEditor(ERDPreferenceKey.VALIDATE_FOREIGN_KEY_COLUMN_TYPE,
                PREF_VALIDATION_FOREIGN_KEY_COLUMN_TYPE.getValue(), VALIDATION_LEVELS, parent));
        addField(new ComboFieldEditor(ERDPreferenceKey.VALIDATE_FOREIGN_KEY_COLUMN_SIZE,
                PREF_VALIDATION_FOREIGN_KEY_COLUMN_SIZE.getValue(), VALIDATION_LEVELS, parent));
    }
}
