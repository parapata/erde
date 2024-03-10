package io.github.parapata.erde.preference;

import static io.github.parapata.erde.preference.ERDPreferenceKey.*;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.RGB;

import io.github.parapata.erde.ERDPlugin;
import io.github.parapata.erde.core.util.DictionaryEntry;
import io.github.parapata.erde.core.util.NameConverter;

/**
 * ERDPreferenceInitializer.
 *
 * @author modified by parapata
 */
public class ERDPreferenceInitializer extends AbstractPreferenceInitializer {

    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = ERDPlugin.getDefault().getPreferenceStore();
        store.setDefault(VALIDATE_ON_SAVE, false);
        store.setDefault(VALIDATE_PHYSICAL_TABLE_NAME_REQUIRED, ERDPlugin.LEVEL_ERROR);
        store.setDefault(VALIDATE_PHYSICAL_TABLE_NAME_DUPLICATED, ERDPlugin.LEVEL_ERROR);
        store.setDefault(VALIDATE_LOGICAL_TABLE_NAME_REQUIRED, ERDPlugin.LEVEL_WARNING);
        store.setDefault(VALIDATE_LOGICAL_TABLE_NAME_DUPLICATED, ERDPlugin.LEVEL_WARNING);
        store.setDefault(VALIDATE_PHYSICAL_COLUMN_NAME_REQUIRED, ERDPlugin.LEVEL_ERROR);
        store.setDefault(VALIDATE_PHYSICAL_COLUMN_NAME_DUPLICATED, ERDPlugin.LEVEL_ERROR);
        store.setDefault(VALIDATE_LOGICAL_COLUMN_NAME_REQUIRED, ERDPlugin.LEVEL_WARNING);
        store.setDefault(VALIDATE_LOGICAL_COLUMN_NAME_DUPLICATED, ERDPlugin.LEVEL_WARNING);
        store.setDefault(VALIDATE_NO_COLUMNS, ERDPlugin.LEVEL_ERROR);
        store.setDefault(VALIDATE_PRIMARY_KEY, ERDPlugin.LEVEL_WARNING);
        store.setDefault(VALIDATE_FOREIGN_KEY_COLUMN_TYPE, ERDPlugin.LEVEL_ERROR);
        store.setDefault(VALIDATE_FOREIGN_KEY_COLUMN_SIZE, ERDPlugin.LEVEL_ERROR);
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
