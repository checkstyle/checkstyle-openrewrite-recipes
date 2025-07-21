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

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.checkstyle.autofix.parser.CheckConfiguration;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.checkstyle.autofix.recipe.Header;
import org.checkstyle.autofix.recipe.UpperEll;
import org.openrewrite.Recipe;

public final class CheckstyleRecipeRegistry {

    private static final Map<String, Function<List<CheckstyleViolation>, Recipe>> RECIPE_MAP =
            new HashMap<>();
    private static final Map<String, BiFunction<List<CheckstyleViolation>, CheckConfiguration,
                    Recipe>> RECIPE_MAP_WITH_CONFIG =
            new HashMap<>();

    static {
        RECIPE_MAP.put("UPPERELL", UpperEll::new);
        RECIPE_MAP_WITH_CONFIG.put("HEADER", Header::new);
    }

    private CheckstyleRecipeRegistry() {
        // utility class
    }

    /**
     * Returns a list of Recipe objects based on the given list of Checkstyle violations.
     * The method groups violations by their check name, finds the matching recipe factory
     * using the simple name of the check, and applies the factory to generate Recipe instances.
     *
     * @param violations the list of Checkstyle violations
     * @param config the checkstyle configuration
     * @return a list of generated Recipe objects
     */
    public static List<Recipe> getRecipes(List<CheckstyleViolation> violations,
                                          CheckConfiguration config) {
        return violations.stream()
                .collect(Collectors.groupingBy(CheckstyleViolation::getSource))
                .entrySet()
                .stream()
                .map(entry -> createRecipe(entry, config))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static Recipe createRecipe(Map.Entry<String, List<CheckstyleViolation>> entry,
                                       CheckConfiguration config) {

        final String simpleCheckName = normalizeCheckName(entry.getKey());
        final List<CheckstyleViolation> violations = entry.getValue();

        return Optional.ofNullable(RECIPE_MAP_WITH_CONFIG.get(simpleCheckName))
                .map(factory -> {
                    return factory.apply(violations,
                            extractCheckConfiguration(config, simpleCheckName));
                }).orElseGet(() -> {
                    return Optional.ofNullable(RECIPE_MAP.get(simpleCheckName))
                            .map(factory -> factory.apply(violations))
                            .orElse(null);
                });
    }

    private static CheckConfiguration extractCheckConfiguration(CheckConfiguration config,
                                                                String checkName) {
        return config.getChildConfig(checkName);
    }

    private static String normalizeCheckName(String checkName) {
        String normalizedCheckName = checkName.substring(checkName.lastIndexOf('.') + 1);
        final int checkLength = 5;
        if (normalizedCheckName.toLowerCase().endsWith("check")) {
            normalizedCheckName = normalizedCheckName
                    .substring(0, normalizedCheckName.length() - checkLength);
        }
        return normalizedCheckName.toUpperCase(Locale.ROOT);
    }
}
