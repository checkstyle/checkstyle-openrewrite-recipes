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

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.checkstyle.autofix.parser.CheckConfiguration;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.checkstyle.autofix.recipe.FinalLocalVariable;
import org.checkstyle.autofix.recipe.Header;
import org.checkstyle.autofix.recipe.HexLiteralCase;
import org.checkstyle.autofix.recipe.RedundantImport;
import org.checkstyle.autofix.recipe.UpperEll;
import org.openrewrite.Recipe;

public final class CheckstyleRecipeRegistry {

    private static final EnumMap<CheckstyleCheck, Function<List<CheckstyleViolation>,
            Recipe>> RECIPE_MAP = new EnumMap<>(CheckstyleCheck.class);

    private static final EnumMap<CheckstyleCheck, BiFunction<List<CheckstyleViolation>,
            CheckConfiguration, Recipe>> RECIPE_MAP_WITH_CONFIG =
            new EnumMap<>(CheckstyleCheck.class);

    static {
        RECIPE_MAP.put(CheckstyleCheck.UPPER_ELL, UpperEll::new);
        RECIPE_MAP.put(CheckstyleCheck.HEX_LITERAL_CASE, HexLiteralCase::new);
        RECIPE_MAP.put(CheckstyleCheck.FINAL_LOCAL_VARIABLE, FinalLocalVariable::new);
        RECIPE_MAP_WITH_CONFIG.put(CheckstyleCheck.HEADER, Header::new);
        RECIPE_MAP.put(CheckstyleCheck.REDUNDANT_IMPORT, RedundantImport::new);
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
                                          Map<CheckstyleCheck, CheckConfiguration> config) {
        return violations.stream()
                .collect(Collectors.groupingBy(CheckstyleViolation::getSource))
                .entrySet()
                .stream()
                .map(entry -> {
                    return createRecipe(entry.getValue(), config.get(entry.getKey()));
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static Recipe createRecipe(List<CheckstyleViolation> violations,
                                       CheckConfiguration checkConfig) {
        Recipe result = null;
        if (checkConfig != null) {

            final CheckstyleCheck check = checkConfig.getCheck();

            final BiFunction<List<CheckstyleViolation>, CheckConfiguration,
                    Recipe> configRecipeFactory = RECIPE_MAP_WITH_CONFIG.get(check);

            if (configRecipeFactory != null) {
                result = configRecipeFactory.apply(violations, checkConfig);
            }
            else {
                result = RECIPE_MAP.get(check).apply(violations);
            }
        }
        return result;
    }

}
