package org.checkstyle.autofix.data;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Contains diff from parsed data, expunges all abundant information
 * immediately when there is an opportunity to do so,
 * thus keeping memory usage as minimal as possible.
 *
 */
public final class CheckReport {

    /**
     * Container for parsed data,
     * note it is a TreeMap for memory keeping purposes.
     */
    private Map<String, List<CheckstyleRecord>> records =
            new TreeMap<>();

    /**
     * Getter for data container.
     *
     * @return map containing parsed data.
     */
    public Map<String, List<CheckstyleRecord>> getRecords() {
        return records;
    }

    /**
     * Adds new records to the diff report,
     * when there are records with this filename, comparison
     * between them and new record is performed and only difference is saved.
     *
     * @param newRecords
     *        a new records list.
     * @param filename
     *        name of a file which is a cause of records generation.
     */
    public void addRecords(List<CheckstyleRecord> newRecords,
                           String filename) {
        if (!newRecords.isEmpty()) {
            records.put(filename, newRecords);
        }
    }
}
