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
import org.checkstyle.autofix.parser.CheckConfiguration;
import org.checkstyle.autofix.parser.CheckstyleReportParser;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.checkstyle.autofix.parser.ConfigurationLoader;
import org.openrewrite.Recipe;
import org.openrewrite.test.RewriteTest;

import com.puppycrawl.tools.checkstyle.AbstractXmlTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.XMLLogger;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.bdd.InlineConfigParser;
import com.puppycrawl.tools.checkstyle.bdd.TestInputConfiguration;

/**
 * Simple base class for recipe tests that extends Checkstyle's AbstractXmlTestSupport.
 */
public abstract class AbstractRecipeTest extends AbstractXmlTestSupport implements RewriteTest {

    /**
     * Returns the subpackage name for test resources.
     */
    protected abstract String getSubpackage();

    /**
     * Creates the recipe with violations and configs.
     */
    protected abstract Recipe createRecipe(List<CheckstyleViolation> violations,
                                           CheckConfiguration checkConfigs);

    @Override
    protected String getPackageLocation() {
        return "org/checkstyle/autofix/recipe/" + getSubpackage();
    }

    /**
     * Main verification method.
     */
    protected void verify(String testCaseName) throws Exception {
        final String inputFileName = "Input" + testCaseName + ".java";
        final String outputFileName = "Output" + testCaseName + ".java";
        final String inputPath =  testCaseName.toLowerCase() + "/" + inputFileName;
        final String outputPath = testCaseName.toLowerCase() + "/" + outputFileName;

        final List<CheckstyleViolation> violations = runCheckstyleAndGetViolations(inputPath);

        final CheckConfiguration checkConfigs = getAllCheckConfigurations(inputPath);

        final String beforeCode = readFile(getPath(inputPath));
        final String expectedAfterCode = readFile(getPath(outputPath));

        final Recipe preprocessingRecipe = new InputClassRenamer();
        final Recipe mainRecipe = createRecipe(violations, checkConfigs);

        testRecipe(beforeCode, expectedAfterCode, preprocessingRecipe, mainRecipe);
    }

    private List<CheckstyleViolation> runCheckstyleAndGetViolations(String inputPath)
            throws Exception {

        final String configFilePath = getPath(inputPath);
        final TestInputConfiguration testInputConfiguration =
                InlineConfigParser.parse(configFilePath);
        final Configuration parsedConfig = testInputConfiguration.createConfiguration();

        final Checker checker = createChecker(parsedConfig);
        final ByteArrayOutputStream xmlOutput = new ByteArrayOutputStream();
        final XMLLogger logger = new XMLLogger(xmlOutput, XMLLogger.OutputStreamOptions.CLOSE);
        checker.addListener(logger);

        final List<File> filesToCheck = Collections.singletonList(new File(configFilePath));
        checker.process(filesToCheck);

        final Path tempXmlPath = Files.createTempFile("checkstyle-report", ".xml");
        try {
            Files.write(tempXmlPath, xmlOutput.toByteArray());
            return CheckstyleReportParser.parse(tempXmlPath);
        } finally {
            Files.deleteIfExists(tempXmlPath);
        }
    }

    private CheckConfiguration getAllCheckConfigurations(String inputPath) throws Exception {
        final String configFilePath = getPath(inputPath);
        final TestInputConfiguration testInputConfiguration =
                InlineConfigParser.parse(configFilePath);
        return ConfigurationLoader.mapConfiguration(testInputConfiguration.createConfiguration());
    }

    /**
     * Helper method to test recipes using OpenRewrite testing framework.
     */
    private void testRecipe(String beforeCode, String expectedAfterCode,
                            Recipe... recipes) {
        assertDoesNotThrow(() -> {
            rewriteRun(
                    spec -> spec.recipes(recipes),
                    java(beforeCode, expectedAfterCode)
            );
        });
    }
}