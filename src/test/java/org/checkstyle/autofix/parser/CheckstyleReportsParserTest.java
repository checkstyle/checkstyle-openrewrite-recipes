///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle-openrewrite-recipes: Automatically fix Checkstyle violations with OpenRewrite.
// Copyright (C) 2025 The Checkstyle OpenRewrite Recipes Authors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
///////////////////////////////////////////////////////////////////////////////////////////////

package org.checkstyle.autofix.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.checkstyle.autofix.CheckFullName;
import org.checkstyle.autofix.CheckstyleCheck;
import org.junit.jupiter.api.Test;

public class CheckstyleReportsParserTest {

    private final ReportParser reportParser = new XmlReportParser();

    private static String getPath(String path) {
        return "src/test/resources/org/checkstyle/autofix/parser/" + path;
    }

    @Test
    public void testParseFromResource() throws Exception {
        final Path xmlPath = Path.of(getPath("checkstyle-report.xml"));
        final List<CheckstyleViolation> records = reportParser.parse(xmlPath);

        assertNotNull(records);
        assertEquals(1, records.size());

        final CheckstyleViolation record = records.get(0);
        assertEquals(42, record.getLine());
        assertEquals(13, record.getColumn());
        assertEquals("error", record.getSeverity());
        assertEquals("Example message", record.getMessage());
        assertEquals(new CheckstyleCheck(CheckFullName.UPPER_ELL, null),
                record.getSource());
        assertEquals(Path.of("Example.java"), record.getFilePath());
    }

    @Test
    public void testParseMultipleFilesReport() throws Exception {
        final Path xmlPath = Path.of(getPath("checkstyle-multiple-files.xml"));
        final List<CheckstyleViolation> records = reportParser.parse(xmlPath);

        assertNotNull(records);
        assertEquals(3, records.size());

        final Map<Path, List<CheckstyleViolation>> grouped = records.stream()
                .collect(Collectors.groupingBy(CheckstyleViolation::getFilePath));

        assertEquals(2, grouped.size());

        assertEquals(2, grouped.get(Path.of("Main.java")).size());
        assertEquals(1, grouped.get(Path.of("Utils.java")).size());

        CheckstyleViolation record = grouped.get(Path.of("Main.java")).get(0);
        assertEquals("error", record.getSeverity());

        record = grouped.get(Path.of("Utils.java")).get(0);
        assertEquals("warning", record.getSeverity());
    }
}
