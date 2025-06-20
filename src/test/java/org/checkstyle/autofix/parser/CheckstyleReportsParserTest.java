package org.checkstyle.autofix.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.checkstyle.autofix.data.CheckReport;
import org.checkstyle.autofix.data.CheckstyleRecord;
import org.junit.Test;

public class CheckstyleReportsParserTest {

    private static String getPath(String path) {
        return "src/test/resources/org/checkstyle/autofix/parser/" + path;
    }

    @Test
    public void testParseFromResource() throws Exception {
        final Path xmlPath = Path.of(getPath("checkstyle-report.xml"));
        final CheckReport report = CheckstyleReportsParser.parse(xmlPath);

        assertNotNull(report);
        final Map<String, List<CheckstyleRecord>> allRecords = report.getRecords();
        assertEquals(1, allRecords.size());

        final List<CheckstyleRecord> records = allRecords.get("Example.java");
        assertNotNull(records);
        assertEquals(1, records.size());

        final CheckstyleRecord record = records.get(0);
        assertEquals(42, record.getLine());
        assertEquals(13, record.getColumn());
        assertEquals("error", record.getSeverity());
        assertEquals("Example message", record.getMessage());
        assertEquals("com.puppycrawl.example.Check", record.getSource());
        assertEquals("Example.java", record.getXref());
    }

    @Test
    public void testParseMultipleFilesReport() throws Exception {
        final Path xmlPath = Path.of(getPath("checkstyle-multiple-files.xml"));
        final CheckReport report = CheckstyleReportsParser.parse(xmlPath);

        assertNotNull(report);
        final Map<String, List<CheckstyleRecord>> allRecords = report.getRecords();
        assertEquals(2, allRecords.size());

        assertEquals(2, allRecords.get("Main.java").size());
        assertEquals(1, allRecords.get("Utils.java").size());

        CheckstyleRecord record = allRecords.get("Main.java").get(0);
        assertEquals("error", record.getSeverity());

        record = allRecords.get("Utils.java").get(0);
        assertEquals("warning", record.getSeverity());
    }

}
