package io.github.parapata.erde.core.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;

import io.github.parapata.erde.ErdePlugin;
import io.github.parapata.erde.preference.ErdePreferenceKey;

/**
 * NameConverter.
 *
 * @author modified by parapata
 */
public class NameConverter {

    /**
     * Loads a default dictionary.
     */
    public static List<DictionaryEntry> loadDefaultDictionary() {
        List<DictionaryEntry> entries = new ArrayList<>();

        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(ErdePlugin.DICTIONARY_TXT);

        String str = IOUtils.loadStream(in, ErdePlugin.getCharset());
        str = str.replaceAll("\r\n", "\n");
        str = str.replaceAll("\r", "\n");

        String[] lines = str.split("\n");
        for (String line : lines) {
            String key = line.substring(0, line.indexOf('='));
            String value = line.substring(line.indexOf('=') + 1);
            boolean partMatch = false;
            if (key.startsWith("_")) {
                partMatch = true;
                key = key.substring(1);
            }
            entries.add(new DictionaryEntry(key, value, partMatch));
        }

        return entries;
    }

    public static void saveToPreferenceStore(IPreferenceStore store, List<DictionaryEntry> entries) {
        StringBuilder sb = new StringBuilder();
        for (DictionaryEntry entry : entries) {
            sb.append(entry.toString()).append("\n");
        }
        store.setValue(ErdePreferenceKey.DICTIONALY, sb.toString());
    }

    public static List<DictionaryEntry> loadFromPreferenceStore(IPreferenceStore store) {
        List<DictionaryEntry> entries = new ArrayList<>();
        String value = store.getString(ErdePreferenceKey.DICTIONALY);
        for (String line : value.split("\n")) {
            String[] dim = line.split(",");
            DictionaryEntry entry = new DictionaryEntry(dim[0], dim[1], Boolean.parseBoolean(dim[2]));
            entries.add(entry);
        }
        return entries;
    }

    /**
     * Converts the logical name to the physical name.
     *
     * @param logical ths logical name
     * @return the physical name
     */
    public static String logical2physical(String logical) {
        logical = logical.toUpperCase();

        for (DictionaryEntry entry : loadFromPreferenceStore(ErdePlugin.getDefault().getPreferenceStore())) {
            logical = logical.replace(entry.logicalName, "_" + entry.physicalName + "_");
        }
        logical = logical.replaceAll("_+", "_");
        logical = logical.replaceAll("^_|_$", "");
        return logical;
    }

    /**
     * Converts the physical name to the logical name.
     *
     * @param physical ths physical name
     * @return the logical name
     */
    public static String physical2logical(String physical) {
        physical = physical.toUpperCase();

        List<DictionaryEntry> entries = new ArrayList<>(
                loadFromPreferenceStore(ErdePlugin.getDefault().getPreferenceStore()));
        Collections.sort(entries, (o1, o2) -> o1.physicalName.length() < o1.physicalName.length() ? 1
                : o1.physicalName.length() > o2.physicalName.length() ? -1 : 0);

        for (DictionaryEntry entry : entries) {
            String physicalName = entry.physicalName;
            if (entry.partialMatch) {
                physicalName = "_" + physicalName;
            }
            physical = physical.replace(physicalName, entry.logicalName);
        }
        physical = physical.replace("_", "");
        return physical;
    }
}
