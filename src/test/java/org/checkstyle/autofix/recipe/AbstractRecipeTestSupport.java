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

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import org.checkstyle.autofix.CheckstyleAutoFix;
import org.checkstyle.autofix.InputClassRenamer;
import org.checkstyle.autofix.RemoveViolationComments;
import org.checkstyle.autofix.parser.CheckstyleReportParser;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.Recipe;
import org.openrewrite.test.RewriteTest;

import com.puppycrawl.tools.checkstyle.AbstractXmlTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.XMLLogger;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.bdd.InlineConfigParser;
import com.puppycrawl.tools.checkstyle.bdd.TestInputConfiguration;

public abstract class AbstractRecipeTestSupport extends AbstractXmlTestSupport
        implements RewriteTest {

    protected abstract String getSubpackage();

    @Override
    protected String getPackageLocation() {
        return "org/checkstyle/autofix/recipe/" + getSubpackage();
    }

    protected void verify(String testCaseName) throws Exception {

        final String inputPath = getInputFilePath(testCaseName);
        final String outputPath = getOutputFilePath(testCaseName);
        final Configuration config = getCheckConfigurations(inputPath);
        verifyOutputFile(outputPath, config);

        final Path violations = runCheckstyle(inputPath, config);
        final Path configPath = createConfigFile(config);

        verifyWithInlineConfigParser(getPath(inputPath),
                convertToExpectedMessages(CheckstyleReportParser.parse(violations)));

        final String beforeCode = readFile(getPath(inputPath));
        final String expectedAfterCode = readFile(getPath(outputPath));

        try {
            final Recipe mainRecipe = new CheckstyleAutoFix(violations.toString(),
                    configPath.toString());
            testRecipe(beforeCode, expectedAfterCode,
                    getPath(inputPath), new InputClassRenamer(),
                    mainRecipe, new RemoveViolationComments());
        }
        finally {
            Files.deleteIfExists(configPath);
            Files.deleteIfExists(violations);
        }
    }

    protected void verify(Configuration config, String testCaseName) throws Exception {
        final String inputPath = getInputFilePath(testCaseName);
        final String outputPath = getOutputFilePath(testCaseName);

        verifyOutputFile(outputPath, config);

        final Path violationReportPath = runCheckstyle(inputPath, config);

        final String beforeCode = readFile(getPath(inputPath));
        final String expectedAfterCode = readFile(getPath(outputPath));

        final Path configPath = createConfigFile(config);

        try {
            final Recipe mainRecipe = new CheckstyleAutoFix(violationReportPath.toString(),
                    configPath.toString());
            testRecipe(beforeCode, expectedAfterCode,
                    getPath(inputPath), new InputClassRenamer(), mainRecipe);
        }
        finally {
            Files.deleteIfExists(configPath);
            Files.deleteIfExists(violationReportPath);
        }
    }

    private Path runCheckstyle(String inputPath,
                               Configuration config) throws Exception {

        final Checker checker = createChecker(config);
        final ByteArrayOutputStream xmlOutput = new ByteArrayOutputStream();
        final XMLLogger logger = new XMLLogger(xmlOutput, XMLLogger.OutputStreamOptions.CLOSE);
        checker.addListener(logger);

        final List<File> filesToCheck = Collections.singletonList(new File(getPath(inputPath)));
        checker.process(filesToCheck);

        final Path tempXmlPath = Files.createTempFile("checkstyle-report", ".xml");

        Files.write(tempXmlPath, xmlOutput.toByteArray());
        return tempXmlPath;
    }

    private Path createConfigFile(Configuration config) throws Exception {
        final Path configPath = Files.createTempFile("checkstyle-config", ".xml");
        writeConfigurationToXml(config, configPath.toString());
        return configPath;
    }

    public static void writeConfigurationToXml(Configuration config, String filePath)
            throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<!DOCTYPE module PUBLIC \"-//Checkstyle//DTD Checkstyle "
                    + "Configuration 1.3//EN\" \"configuration_1_3.dtd\">\n");
            writeModule(config, writer);
        }
    }

    private static void writeModule(Configuration config, BufferedWriter writer)
            throws IOException {

        writer.write("<module name=\"" + (config.getName()) + "\">\n");

        for (String propName : config.getPropertyNames()) {
            try {
                final String propValue = config.getProperty(propName);
                writer.write("  <property name=\"" + propName + "\" value=\""
                        + propValue + "\"/>\n");
            }
            catch (CheckstyleException exception) {
                throw new IOException("Failed to get property: " + propName, exception);
            }
        }
        for (Configuration child : config.getChildren()) {
            writeModule(child, writer);
        }

        writer.write("</module>\n");
    }

    private Configuration getCheckConfigurations(String inputPath) throws Exception {
        final String configFilePath = getPath(inputPath);
        final TestInputConfiguration testInputConfiguration =
                InlineConfigParser.parse(configFilePath);
        return testInputConfiguration.createConfiguration();
    }

    private String[] convertToExpectedMessages(List<CheckstyleViolation> violations) {
        return violations.stream()
                .map(violation -> {
                    return violation.getLine() + ":"
                                + violation.getColumn() + ": " + violation.getMessage();
                })
                .toArray(String[]::new);
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

    private String getInputFilePath(String testCaseName) {
        final String inputFileName = "Input" + testCaseName + ".java";
        return testCaseName.toLowerCase() + "/" + inputFileName;
    }

    private String getOutputFilePath(String testCaseName) {
        final String inputFileName = "Output" + testCaseName + ".java";
        return testCaseName.toLowerCase() + "/" + inputFileName;
    }

    private void verifyOutputFile(String outputPath, Configuration config) throws Exception {
        verify(config, getPath(outputPath), new String[0]);
    }

}
