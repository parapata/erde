package io.github.erde.preference;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import io.github.erde.Activator;
import io.github.erde.IMessages;

/**
 * ValidationPreferencePage.
 *
 * @author modified by parapata
 */
public class ValidationPreferencePage extends FieldEditorPreferencePage implements IMessages, IWorkbenchPreferencePage {

    private static final String[][] VALIDATION_LEVELS = new String[][] {
            { resource.getString("preference.validation.level.warning"), Activator.LEVEL_WARNING },
            { resource.getString("preference.validation.level.error"), Activator.LEVEL_ERROR },
            { resource.getString("preference.validation.level.ignore"), Activator.LEVEL_IGNORE }, };

    public ValidationPreferencePage() {
        super(GRID); // $NON-NLS-1$
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

        addField(new BooleanFieldEditor(Activator.PREF_VALIDATE_ON_SAVE, getResource("preference.validation"), parent));

        addField(new ComboFieldEditor(Activator.PREF_VALIDATE_PHYSICAL_TABLE_NAME_REQUIRED,
                getResource("preference.validation.tableName.required"), VALIDATION_LEVELS, parent));
        addField(new ComboFieldEditor(Activator.PREF_VALIDATE_PHYSICAL_TABLE_NAME_DUPLICATED,
                getResource("preference.validation.tableName.duplicated"), VALIDATION_LEVELS, parent));
        addField(new ComboFieldEditor(Activator.PREF_VALIDATE_LOGICAL_TABLE_NAME_REQUIRED,
                getResource("preference.validation.logicalTableName.required"), VALIDATION_LEVELS, parent));
        addField(new ComboFieldEditor(Activator.PREF_VALIDATE_LOGICAL_TABLE_NAME_DUPLICATED,
                getResource("preference.validation.logicalTableName.duplicated"), VALIDATION_LEVELS, parent));

        addField(new ComboFieldEditor(Activator.PREF_VALIDATE_PHYSICAL_COLUMN_NAME_REQUIRED,
                getResource("preference.validation.columnName.required"), VALIDATION_LEVELS, parent));
        addField(new ComboFieldEditor(Activator.PREF_VALIDATE_PHYSICAL_COLUMN_NAME_DUPLICATED,
                getResource("preference.validation.columnName.duplicated"), VALIDATION_LEVELS, parent));
        addField(new ComboFieldEditor(Activator.PREF_VALIDATE_LOGICAL_COLUMN_NAME_REQUIRED,
                getResource("preference.validation.logicalColumnName.required"), VALIDATION_LEVELS, parent));
        addField(new ComboFieldEditor(Activator.PREF_VALIDATE_LOGICAL_COLUMN_NAME_DUPLICATED,
                getResource("preference.validation.logicalColumnName.duplicated"), VALIDATION_LEVELS, parent));

        addField(new ComboFieldEditor(Activator.PREF_VALIDATE_NO_COLUMNS,
                getResource("preference.validation.noColumns"), VALIDATION_LEVELS, parent));
        addField(new ComboFieldEditor(Activator.PREF_VALIDATE_PRIMARY_KEY,
                getResource("preference.validation.primaryKey"), VALIDATION_LEVELS, parent));

        addField(new ComboFieldEditor(Activator.PREF_VALIDATE_FOREIGN_KEY_COLUMN_TYPE,
                getResource("preference.validation.foreignKey.columnType"), VALIDATION_LEVELS, parent));
        addField(new ComboFieldEditor(Activator.PREF_VALIDATE_FOREIGN_KEY_COLUMN_SIZE,
                getResource("preference.validation.foreignKey.columnSize"), VALIDATION_LEVELS, parent));
    }
}
