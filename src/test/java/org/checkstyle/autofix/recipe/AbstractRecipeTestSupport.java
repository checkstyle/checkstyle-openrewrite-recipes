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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import org.checkstyle.autofix.CheckstyleAutoFix;
import org.checkstyle.autofix.InputClassRenamer;
import org.checkstyle.autofix.RemoveViolationComments;
import org.checkstyle.autofix.parser.CheckConfiguration;
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

    protected abstract Recipe createRecipe(List<CheckstyleViolation> violations,
                                           CheckConfiguration configuration);

    @Override
    protected String getPackageLocation() {
        return "org/checkstyle/autofix/recipe/" + getSubpackage();
    }

    protected void verify(String testCaseName) throws Exception {

        final String inputPath = getInputFilePath(testCaseName);
        final String outputPath = getOutputFilePath(testCaseName);
        final Configuration config = getCheckConfigurations(inputPath);
        verifyOutputFile(outputPath, config);

        final Path violationPath = runCheckstyle(inputPath, config);
        List<String> lines = Files.readAllLines(Path.of(getPath(inputPath)));
        List<String> inlineConfig = getInlineConfig(lines);
        final Path configPath = Files.createTempFile("checkstyle-config", ".xml");
        Files.write(configPath, inlineConfig);

        final String beforeCode = readFile(getPath(inputPath));
        final String expectedAfterCode = readFile(getPath(outputPath));

        final Recipe recipe = new CheckstyleAutoFix(violationPath.toString(), configPath.toString());

        testRecipe(beforeCode, expectedAfterCode,
                getPath(inputPath), new InputClassRenamer(), recipe, new RemoveViolationComments());
    }

    protected void verify(Configuration config, String testCaseName) throws Exception {

        final String inputPath = getInputFilePath(testCaseName);
        final String outputPath = getOutputFilePath(testCaseName);

        verifyOutputFile(outputPath, config);

        final Path violationPath = runCheckstyle(inputPath, config);



        final String beforeCode = readFile(getPath(inputPath));
        final String expectedAfterCode = readFile(getPath(outputPath));
        final Path configPath = Files.createTempFile("checkstyle-config", ".xml");
        writeConfigurationToXml(config, configPath.toString());

        List<String> lines = Files.readAllLines(configPath);
        for (String line : lines) {
            System.out.println(line);
        }

        final Recipe recipe = new CheckstyleAutoFix(violationPath.toString(), configPath.toString());
        testRecipe(beforeCode, expectedAfterCode,
                getPath(inputPath), new InputClassRenamer(), recipe,
                new RemoveViolationComments());
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

    private void verifyOutputFile(String outputPath, Configuration config) throws Exception {
        verify(config, getPath(outputPath), new String[0]);
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

    public static void writeConfigurationToXml(Configuration config, String filePath) throws IOException, CheckstyleException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writeModule(config, writer);
        }
    }

    private static void writeModule(Configuration config, BufferedWriter writer) throws IOException, CheckstyleException {
        writer.write("<module name=\"" + config.getName() + "\">\n");
        for (String propName : config.getPropertyNames())
            writer.write("    <property name=\"" + propName + "\" value=\""
                    + config.getProperty(propName).replace("&", "&amp;")
                    .replace("<", "&lt;").replace(">", "&gt;")
                    .replace("\"", "&quot;").replace("'", "&apos;")
                    + "\"/>\n");
        for (Configuration child : config.getChildren())
            writeModule(child, writer);
        writer.write("</module>\n");
    }

    private List<String> getInlineConfig(List<String> lines) {
        return lines.stream()
                .skip(1L)
                .takeWhile((line) -> !line.startsWith("*/"))
                .toList();
    }

    private String getInputFilePath(String testCaseName) {
        final String inputFileName = "Input" + testCaseName + ".java";
        return testCaseName.toLowerCase() + "/" + inputFileName;
    }

    private String getOutputFilePath(String testCaseName) {
        final String inputFileName = "Output" + testCaseName + ".java";
        return testCaseName.toLowerCase() + "/" + inputFileName;
    }

}
