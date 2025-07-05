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
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.checkstyle.autofix.InputClassRenamer;
import org.openrewrite.Recipe;
import org.openrewrite.test.RewriteTest;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public abstract class AbstractRecipeTest implements RewriteTest {

    private static final String BASE_TEST_RESOURCES_PATH = "src/test/resources/org"
            + "/checkstyle/autofix/recipe/";

    private Recipe createPreprocessingRecipe() {
        return new InputClassRenamer();
    }

    protected abstract Recipe getRecipe() throws CheckstyleException;

    protected void testRecipe(String recipePath, String testCaseName) throws IOException,
            CheckstyleException {
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

    protected Configuration extractCheckConfiguration(Configuration config, String checkName) {

        return Arrays.stream(config.getChildren())
                .filter(child -> checkName.equals(child.getName()))
                .findFirst()
                .orElseThrow(() -> {
                    return new IllegalArgumentException(checkName + "configuration not "
                            + "found");
                });
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
