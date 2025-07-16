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
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.checkstyle.autofix.InputClassRenamer;
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

public abstract class AbstractRecipeTest extends AbstractXmlTestSupport implements RewriteTest {

    protected abstract String getSubpackage();

    protected abstract String getCheckName();

    protected abstract Recipe createRecipe(List<CheckstyleViolation> violations,
                                           Configuration configuration);

    @Override
    protected String getPackageLocation() {
        return "org/checkstyle/autofix/recipe/" + getSubpackage();
    }

    private Recipe createPreprocessingRecipe() {
        return new InputClassRenamer();
    }

    protected void verify(String testCaseName) throws Exception {
        final String inputFileName = "Input" + testCaseName + ".java";
        final String outputFileName = "Output" + testCaseName + ".java";
        final String inputPath = testCaseName.toLowerCase() + "/" + inputFileName;
        final String outputPath = testCaseName.toLowerCase() + "/" + outputFileName;

        final Configuration config = getAllCheckConfigurations(inputPath);
        final List<CheckstyleViolation> violations = runCheckstyle(inputPath, config);
        final Configuration checkConfig = extractCheckConfiguration(config, getCheckName());

        final String beforeCode = readFile(getPath(inputPath));
        final String expectedAfterCode = readFile(getPath(outputPath));
        final Recipe mainRecipe = createRecipe(violations, checkConfig);

        testRecipe(beforeCode, expectedAfterCode,
                getPath(inputPath), createPreprocessingRecipe(), mainRecipe);
    }

    protected void verify(Configuration checkConfig, String testCaseName) throws Exception {
        final String inputFileName = "Input" + testCaseName + ".java";
        final String outputFileName = "Output" + testCaseName + ".java";
        final String inputPath = testCaseName.toLowerCase() + "/" + inputFileName;
        final String outputPath = testCaseName.toLowerCase() + "/" + outputFileName;

        final List<CheckstyleViolation> violations = runCheckstyle(inputPath, checkConfig);

        final String beforeCode = readFile(getPath(inputPath));
        final String expectedAfterCode = readFile(getPath(outputPath));

        final Recipe mainRecipe = createRecipe(violations, checkConfig);

        testRecipe(beforeCode, expectedAfterCode,
                getPath(inputPath), createPreprocessingRecipe(), mainRecipe);
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

    private Configuration getAllCheckConfigurations(String inputPath) throws Exception {
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

    protected Configuration extractCheckConfiguration(Configuration config, String checkName) {
        return Optional.of(config)
                .filter(child -> checkName.equals(child.getName()))
                .orElse(Arrays.stream(config.getChildren())
                        .filter(child -> {
                            return "com.puppycrawl.tools.checkstyle.TreeWalker"
                                    .equals(child.getName());
                        })
                        .flatMap(treeWalker -> Arrays.stream(treeWalker.getChildren()))
                        .filter(child -> checkName.equals(child.getName()))
                        .findFirst()
                        .orElseThrow(() -> {
                            return new IllegalArgumentException(checkName
                                    + " configuration not found");
                        }));
    }

    protected Charset getCharset(Configuration config) {
        try {
            final String charsetName;

            if (Arrays.asList(config.getPropertyNames()).contains("charset")) {
                charsetName = config.getProperty("charset");
            }
            else {
                charsetName = Charset.defaultCharset().name();
            }

            return Charset.forName(charsetName);
        }
        catch (CheckstyleException exception) {
            throw new IllegalArgumentException("Failed to extract charset from config.", exception);
        }
    }

}
