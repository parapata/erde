package io.github.erde.preference;

import static io.github.erde.preference.ERDPreferenceKey.*;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.RGB;

import io.github.erde.Activator;
import io.github.erde.core.util.NameConverter;
import io.github.erde.core.util.NameConverter.DictionaryEntry;

/**
 * ERDPreferenceInitializer.
 *
 * @author modified by parapata
 */
public class ERDPreferenceInitializer extends AbstractPreferenceInitializer {

    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        store.setDefault(VALIDATE_ON_SAVE, false);
        store.setDefault(VALIDATE_PHYSICAL_TABLE_NAME_REQUIRED, Activator.LEVEL_ERROR);
        store.setDefault(VALIDATE_PHYSICAL_TABLE_NAME_DUPLICATED, Activator.LEVEL_ERROR);
        store.setDefault(VALIDATE_LOGICAL_TABLE_NAME_REQUIRED, Activator.LEVEL_WARNING);
        store.setDefault(VALIDATE_LOGICAL_TABLE_NAME_DUPLICATED, Activator.LEVEL_WARNING);
        store.setDefault(VALIDATE_PHYSICAL_COLUMN_NAME_REQUIRED, Activator.LEVEL_ERROR);
        store.setDefault(VALIDATE_PHYSICAL_COLUMN_NAME_DUPLICATED, Activator.LEVEL_ERROR);
        store.setDefault(VALIDATE_LOGICAL_COLUMN_NAME_REQUIRED, Activator.LEVEL_WARNING);
        store.setDefault(VALIDATE_LOGICAL_COLUMN_NAME_DUPLICATED, Activator.LEVEL_WARNING);
        store.setDefault(VALIDATE_NO_COLUMNS, Activator.LEVEL_ERROR);
        store.setDefault(VALIDATE_PRIMARY_KEY, Activator.LEVEL_WARNING);
        store.setDefault(VALIDATE_FOREIGN_KEY_COLUMN_TYPE, Activator.LEVEL_ERROR);
        store.setDefault(VALIDATE_FOREIGN_KEY_COLUMN_SIZE, Activator.LEVEL_ERROR);
        store.setDefault(SHOW_GRID, true);
        store.setDefault(ENABLED_GRID, true);
        store.setDefault(GRID_SIZE, 15);
        store.setDefault(SNAP_GEOMETRY, false);
        store.setDefault(SHOW_NOT_NULL, false);

        StringBuilder sb = new StringBuilder();
        for (DictionaryEntry entry : NameConverter.loadDefaultDictionary()) {
            sb.append(entry.toString()).append("\n");
        }
        store.setDefault(DICTIONALY, sb.toString());

        // for SQL editor
        store.setDefault(COLOR_DEFAULT, StringConverter.asString(new RGB(0, 0, 0)));
        store.setDefault(COLOR_COMMENT, StringConverter.asString(new RGB(0, 128, 0)));
        store.setDefault(COLOR_STRING, StringConverter.asString(new RGB(0, 0, 255)));
        store.setDefault(COLOR_KEYWORD, StringConverter.asString(new RGB(128, 0, 128)));
    }
}
