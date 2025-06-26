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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.checkstyle.autofix.InputClassRenamer;
import org.openrewrite.Recipe;
import org.openrewrite.test.RewriteTest;

/**
 * Base test class for recipe testing that provides common functionality
 * for reading test files and executing recipe tests with preprocessing.
 */
public abstract class AbstractRecipeTest implements RewriteTest {

    private static final String BASE_TEST_RESOURCES_PATH = "src/test/resources/org"
            + "/checkstyle/autofix/recipe/";

    /**
     * Creates a preprocessing recipe that normalizes class names from InputXxx to OutputXxx.
     * This allows test files to have descriptive names while maintaining consistent class names.
     *
     * @return the preprocessing recipe
     */
    private Recipe createPreprocessingRecipe() {
        return new InputClassRenamer();
    }

    /**
     * Creates the main recipe that should be tested.
     * Subclasses must implement this method to provide their specific recipe.
     *
     * @return the main recipe to test
     */
    protected abstract Recipe getRecipe();

    /**
     * Tests a recipe with the given recipe path and test case name.
     * Expects input and output files to follow the naming convention:
     * - Input: {recipePath}/{testCaseName}/Input{testCaseName}.java
     * - Output: {recipePath}/{testCaseName}/Output{testCaseName}.java
     * The method automatically applies preprocessing to normalize class names
     * before running the main recipe.
     *
     * @param recipePath the recipe-specific path.
     * @param testCaseName the name of the test case (should match directory and file names)
     * @throws IOException if files cannot be read
     */
    protected void testRecipe(String recipePath, String testCaseName)
            throws IOException {
        final String testCaseDir = testCaseName.toLowerCase();
        final String inputFileName = "Input" + testCaseName + ".java";
        final String outputFileName = "Output" + testCaseName + ".java";

        final String beforeCode = Files.readString(Paths.get(BASE_TEST_RESOURCES_PATH
                + recipePath + "/" + testCaseDir + "/" + inputFileName));

        final String afterCode = Files.readString(Paths.get(BASE_TEST_RESOURCES_PATH
                + recipePath + "/" + testCaseDir + "/" + outputFileName));

        final Recipe preprocessing = createPreprocessingRecipe();
        final Recipe mainRecipe = getRecipe();

        assertDoesNotThrow(() -> {
            rewriteRun(
                    spec -> spec.recipes(preprocessing, mainRecipe),
                    java(beforeCode, afterCode)
            );
        });
    }
}
