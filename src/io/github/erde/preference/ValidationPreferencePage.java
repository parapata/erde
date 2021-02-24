package io.github.erde.preference;

import static io.github.erde.Resource.*;
import static io.github.erde.preference.ERDPreferenceKey.*;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import io.github.erde.Activator;

/**
 * ValidationPreferencePage.
 *
 * @author modified by parapata
 */
public class ValidationPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

    private static final String[][] VALIDATION_LEVELS = new String[][] {
            { PREFERENCE_VALIDATION_LEVEL_WARNING.getValue(), Activator.LEVEL_WARNING },
            { PREFERENCE_VALIDATION_LEVEL_ERROR.getValue(), Activator.LEVEL_ERROR },
            { PREFERENCE_VALIDATION_LEVEL_IGNORE.getValue(), Activator.LEVEL_IGNORE }, };

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

        addField(new BooleanFieldEditor(VALIDATE_ON_SAVE,
                PREFERENCE_VALIDATION.getValue(),
                parent));

        addField(new ComboFieldEditor(VALIDATE_PHYSICAL_TABLE_NAME_REQUIRED,
                PREFERENCE_VALIDATION_LOGICAL_TABLE_NAME_REQUIRED.getValue(),
                VALIDATION_LEVELS,
                parent));

        addField(new ComboFieldEditor(VALIDATE_PHYSICAL_TABLE_NAME_DUPLICATED,
                PREFERENCE_VALIDATION_TABLE_NAME_DUPLICATED.getValue(),
                VALIDATION_LEVELS,
                parent));

        addField(new ComboFieldEditor(VALIDATE_LOGICAL_TABLE_NAME_REQUIRED,
                PREFERENCE_VALIDATION_LOGICAL_TABLE_NAME_REQUIRED.getValue(),
                VALIDATION_LEVELS,
                parent));

        addField(new ComboFieldEditor(VALIDATE_LOGICAL_TABLE_NAME_DUPLICATED,
                PREFERENCE_VALIDATION_LOGICAL_TABLE_NAME_DUPLICATED.getValue(),
                VALIDATION_LEVELS,
                parent));

        addField(new ComboFieldEditor(VALIDATE_PHYSICAL_COLUMN_NAME_REQUIRED,
                PREFERENCE_VALIDATION_LOGICAL_COLUMN_NAME_REQUIRED.getValue(),
                VALIDATION_LEVELS,
                parent));

        addField(new ComboFieldEditor(VALIDATE_PHYSICAL_COLUMN_NAME_DUPLICATED,
                PREFERENCE_VALIDATION_LOGICAL_COLUMN_NAME_DUPLICATED.getValue(),
                VALIDATION_LEVELS,
                parent));

        addField(new ComboFieldEditor(VALIDATE_LOGICAL_COLUMN_NAME_REQUIRED,
                PREFERENCE_VALIDATION_LOGICAL_COLUMN_NAME_REQUIRED.getValue(),
                VALIDATION_LEVELS,
                parent));

        addField(new ComboFieldEditor(VALIDATE_LOGICAL_COLUMN_NAME_DUPLICATED,
                PREFERENCE_VALIDATION_LOGICAL_COLUMN_NAME_DUPLICATED.getValue(),
                VALIDATION_LEVELS,
                parent));

        addField(new ComboFieldEditor(VALIDATE_NO_COLUMNS,
                PREFERENCE_VALIDATION_NO_COLUMNS.getValue(),
                VALIDATION_LEVELS,
                parent));

        addField(new ComboFieldEditor(VALIDATE_PRIMARY_KEY,
                PREFERENCE_VALIDATION_PRIMARY_KEY.getValue(),
                VALIDATION_LEVELS,
                parent));

        addField(new ComboFieldEditor(VALIDATE_FOREIGN_KEY_COLUMN_TYPE,
                PREFERENCE_VALIDATION_FOREIGN_KEY_COLUMN_TYPE.getValue(),
                VALIDATION_LEVELS,
                parent));

        addField(new ComboFieldEditor(VALIDATE_FOREIGN_KEY_COLUMN_SIZE,
                PREFERENCE_VALIDATION_FOREIGN_KEY_COLUMN_SIZE.getValue(),
                VALIDATION_LEVELS,
                parent));
    }
}
