///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle-openrewrite-recipes: Automatically fix Checkstyle violations with OpenRewrite.
// Copyright (C) 2026 The Checkstyle OpenRewrite Recipes Authors
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

package org.checkstyle.autofix;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.openrewrite.Recipe;

class CheckstyleAutoFixTest {

    CheckstyleAutoFixTest() {
    }

    @Test
    void testUnsupportedReportFormat() {
        final CheckstyleAutoFix autoFix = new CheckstyleAutoFix("report.txt", "config.xml");
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                autoFix::getRecipeList);
        assertEquals("Unsupported report format: report.txt", exception.getMessage());
    }

    @Test
    void testSarifReportFormat() {
        final CheckstyleAutoFix autoFix = new CheckstyleAutoFix("report.sarif", "config.xml");
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                autoFix::getRecipeList);
        assertEquals("Failed to parse report: report.sarif", exception.getMessage());
    }

    @Test
    void testSarifJsonReportFormat() {
        final CheckstyleAutoFix autoFix = new CheckstyleAutoFix("report.sarif.json", "config.xml");
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                autoFix::getRecipeList);
        assertEquals("Failed to parse report: report.sarif.json", exception.getMessage());
    }

    @Test
    void testValidPropertiesPath(@TempDir Path tempDir) throws Exception {
        final Path propsPath = tempDir.resolve("checkstyle.properties");
        Files.writeString(propsPath, "my.severity=warning");

        final Path configPath = tempDir.resolve("checkstyle.xml");
        final String configXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<!DOCTYPE module PUBLIC \"-//Checkstyle//DTD Checkstyle Configuration 1.3//EN\" "
                + "\"https://checkstyle.org/dtds/configuration_1_3.dtd\">\n"
                + "<module name=\"Checker\">\n"
                + "  <property name=\"severity\" value=\"${my.severity}\"/>\n"
                + "</module>";
        Files.writeString(configPath, configXml);

        final String reportPath = "src/test/resources/org/checkstyle/autofix/parser/"
                + "checkstyle-report.xml";

        final CheckstyleAutoFix autoFix = new CheckstyleAutoFix(
                reportPath, configPath.toString(), propsPath.toString());

        final List<Recipe> recipes = autoFix.getRecipeList();
        Assertions.assertNotNull(recipes);

        final List<Recipe> cachedRecipes = autoFix.getRecipeList();
        Assertions.assertSame(recipes, cachedRecipes, "Recipe list should be cached");
    }

}
