package io.github.parapata.erde.core.util;

import io.github.parapata.erde.core.util.swt.ColumnInfo;

/**
 * DictionaryEntry.
 *
 * @author modified by parapata
 */
public class DictionaryEntry {

    @ColumnInfo(index = 0, width = 150, label = ColumnInfo.PHYSICAL_NAME)
    public String physicalName;

    @ColumnInfo(index = 1, width = 150, label = ColumnInfo.LOGICAL_NAME)
    public String logicalName;

    @ColumnInfo(index = 2, width = 100, label = ColumnInfo.PARTIAL_MATCH)
    public boolean partialMatch;

    public DictionaryEntry(String physicalName, String logicalName, boolean partialMatch) {
        this.physicalName = physicalName;
        this.logicalName = logicalName;
        this.partialMatch = partialMatch;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s", physicalName, logicalName, partialMatch);
    }
}
