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

package org.checkstyle.autofix.recipe;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.openrewrite.java.Assertions.java;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import org.checkstyle.autofix.InputClassRenamer;
import org.checkstyle.autofix.RemoveViolationComments;
import org.checkstyle.autofix.parser.CheckstyleReportParser;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.Recipe;
import org.openrewrite.test.RewriteTest;

import com.puppycrawl.tools.checkstyle.AbstractXmlTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.XMLLogger;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.bdd.InlineConfigParser;
import com.puppycrawl.tools.checkstyle.bdd.TestInputConfiguration;

public abstract class AbstractRecipeTestSupport extends AbstractXmlTestSupport
        implements RewriteTest {

    protected abstract String getSubpackage();

    protected abstract Recipe createRecipe(List<CheckstyleViolation> violations);

    @Override
    protected String getPackageLocation() {
        return "org/checkstyle/autofix/recipe/" + getSubpackage();
    }

    protected void verify(String testCaseName) throws Exception {
        final String inputFileName = "Input" + testCaseName + ".java";
        final String outputFileName = "Output" + testCaseName + ".java";
        final String inputPath = testCaseName.toLowerCase() + "/" + inputFileName;
        final String outputPath = testCaseName.toLowerCase() + "/" + outputFileName;

        verifyOutputFile(outputPath);

        final Configuration config = getCheckConfigurations(inputPath);
        final List<CheckstyleViolation> violations = runCheckstyle(inputPath, config);

        String[] expectedMessages = convertToExpectedMessages(violations);
        verifyWithInlineConfigParser(getPath(inputPath), expectedMessages);

        final String beforeCode = readFile(getPath(inputPath));
        final String expectedAfterCode = readFile(getPath(outputPath));

        final Recipe mainRecipe = createRecipe(violations);

        testRecipe(beforeCode, expectedAfterCode,
                getPath(inputPath), new InputClassRenamer(), mainRecipe, new RemoveViolationComments());
    }

    private List<CheckstyleViolation> runCheckstyle(String inputPath,
                                                    Configuration config) throws Exception {

        final Checker checker = createChecker(config);
        final ByteArrayOutputStream xmlOutput = new ByteArrayOutputStream();
        final XMLLogger logger = new XMLLogger(xmlOutput, XMLLogger.OutputStreamOptions.CLOSE);
        checker.addListener(logger);

        final List<File> filesToCheck = Collections.singletonList(new File(getPath(inputPath)));
        checker.process(filesToCheck);

        final Path tempXmlPath = Files.createTempFile("checkstyle-report", ".xml");
        try {
            Files.write(tempXmlPath, xmlOutput.toByteArray());
            return CheckstyleReportParser.parse(tempXmlPath);
        }
        finally {
            Files.deleteIfExists(tempXmlPath);
        }
    }

    private void verifyOutputFile(String outputPath) throws Exception {

        final Configuration config = getCheckConfigurations(outputPath);
        final List<CheckstyleViolation> violations = runCheckstyle(outputPath, config);
        if (!violations.isEmpty()) {
            final StringBuilder violationMessage =
                    new StringBuilder("Checkstyle violations found in the output file:\n");

            violationMessage.append("outputFile: ").append(getPath(outputPath)).append("\n");

            for (CheckstyleViolation violation : violations) {
                violationMessage
                        .append("line: ").append(violation.getLine())
                        .append(", col: ").append(violation.getColumn())
                        .append(", message: ").append(violation.getMessage())
                        .append("\n");
            }

            throw new IllegalStateException(violationMessage.toString());
        }
    }

    private String[] convertToExpectedMessages(List<CheckstyleViolation> violations) {
        return violations.stream()
                .map(v -> {
                    if (v.getColumn() > 0) {
                        return v.getLine() + ":" + v.getColumn() + ": " + v.getMessage();
                    } else {
                        return v.getLine() + ": " + v.getMessage();
                    }
                })
                .toArray(String[]::new);
    }

    private Configuration getCheckConfigurations(String inputPath) throws Exception {
        final String configFilePath = getPath(inputPath);
        final TestInputConfiguration testInputConfiguration =
                InlineConfigParser.parse(configFilePath);
        return testInputConfiguration.createConfiguration();
    }

    private void testRecipe(String beforeCode, String expectedAfterCode,
                            String filePath, Recipe... recipes) {
        assertDoesNotThrow(() -> {
            rewriteRun(
                    spec -> spec.recipes(recipes),
                    java(beforeCode, expectedAfterCode, spec -> spec.path(filePath))
            );
        });
    }
}
