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

package org.checkstyle.autofix;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.google.common.truth.Truth;

public class KafkaConfigTest {

    private void assertRecipesAreInConfig(String configPath) throws Exception {
        final Path recipesDir = Paths.get("src/main/java/org/checkstyle/autofix/recipe");
        final Set<String> recipes = new TreeSet<>();

        try (Stream<Path> paths = Files.walk(recipesDir)) {
            final Set<String> recipeNames = paths
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".java"))
                .filter(path -> !"package-info.java".equals(path.getFileName().toString()))
                .map(path -> path.getFileName().toString().replace(".java", ""))
                .collect(Collectors.toSet());
            recipes.addAll(recipeNames);
        }

        final String configContent = new String(
            Files.readAllBytes(Paths.get(configPath)));

        final Set<String> modulesInConfig = new TreeSet<>();
        final Pattern pattern = Pattern.compile("<module name=\"([^\"]+)\"");
        final Matcher matcher = pattern.matcher(configContent);
        while (matcher.find()) {
            modulesInConfig.add(matcher.group(1));
        }

        final Set<String> missingInConfig = new TreeSet<>(recipes);
        missingInConfig.removeAll(modulesInConfig);

        Truth.assertWithMessage(
            "The following recipes are missing in " + configPath + ":")
                .that(missingInConfig)
                .isEmpty();
    }

    @Test
    public void testAllRecipesAreInKafkaConfig() throws Exception {
        assertRecipesAreInConfig(".ci/checkstyle-config-with-all-autofixed-checks-kafka.xml");
    }

    @Test
    public void testAllRecipesAreInAllAutofixedChecksConfig() throws Exception {
        assertRecipesAreInConfig(".ci/checkstyle-config-with-all-autofixed-checks.xml");
    }
}
