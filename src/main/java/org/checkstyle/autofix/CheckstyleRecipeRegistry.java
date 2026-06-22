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

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.checkstyle.autofix.marker.ViolationMarkerRecipe;
import org.checkstyle.autofix.parser.CheckConfiguration;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.checkstyle.autofix.recipe.AnnotationOnSameLine;
import org.checkstyle.autofix.recipe.AvoidStarImport;
import org.checkstyle.autofix.recipe.ConstructorsDeclarationGrouping;
import org.checkstyle.autofix.recipe.EmptyStatement;
import org.checkstyle.autofix.recipe.FinalClass;
import org.checkstyle.autofix.recipe.FinalLocalVariable;
import org.checkstyle.autofix.recipe.Header;
import org.checkstyle.autofix.recipe.HexLiteralCase;
import org.checkstyle.autofix.recipe.MissingDeprecated;
import org.checkstyle.autofix.recipe.MissingOverride;
import org.checkstyle.autofix.recipe.NewlineAtEndOfFile;
import org.checkstyle.autofix.recipe.NumericalPrefixesInfixesSuffixesCharacterCase;
import org.checkstyle.autofix.recipe.RedundantImport;
import org.checkstyle.autofix.recipe.UnusedImports;
import org.checkstyle.autofix.recipe.UnusedLocalVariable;
import org.checkstyle.autofix.recipe.UpperEll;
import org.checkstyle.autofix.recipe.UseEnhancedSwitch;
import org.openrewrite.Recipe;

public final class CheckstyleRecipeRegistry {

    private static final EnumMap<CheckFullName, Function<List<CheckstyleViolation>,
            Recipe>> RECIPE_MAP = new EnumMap<>(CheckFullName.class);

    private static final EnumMap<CheckFullName, BiFunction<List<CheckstyleViolation>,
            CheckConfiguration, Recipe>> RECIPE_MAP_WITH_CONFIG =
            new EnumMap<>(CheckFullName.class);

    private static final EnumMap<CheckFullName, Function<CheckConfiguration, Recipe>>
            RECIPE_MAP_WITH_CONFIG_NO_VIOLATIONS = new EnumMap<>(CheckFullName.class);

    private static final EnumMap<CheckFullName, Supplier<Recipe>>
            RECIPE_MAP_NO_VIOLATIONS = new EnumMap<>(CheckFullName.class);

    static {
        RECIPE_MAP.put(CheckFullName.CONSTRUCTORS_DECLARATION_GROUPING,
            ConstructorsDeclarationGrouping::new);
        RECIPE_MAP.put(CheckFullName.UPPER_ELL, UpperEll::new);
        RECIPE_MAP_WITH_CONFIG_NO_VIOLATIONS.put(CheckFullName.NEWLINE_AT_END_OF_FILE,
            NewlineAtEndOfFile::new);
        RECIPE_MAP_WITH_CONFIG_NO_VIOLATIONS.put(CheckFullName.HEADER, Header::new);
        RECIPE_MAP_NO_VIOLATIONS.put(CheckFullName.USE_ENHANCED_SWITCH, UseEnhancedSwitch::new);
        RECIPE_MAP_NO_VIOLATIONS.put(CheckFullName.ANNOTATION_ON_SAME_LINE,
            AnnotationOnSameLine::new);
        RECIPE_MAP_NO_VIOLATIONS.put(CheckFullName.UNUSED_IMPORT, UnusedImports::new);
        RECIPE_MAP_NO_VIOLATIONS.put(CheckFullName.FINAL_CLASS, FinalClass::new);
        RECIPE_MAP_NO_VIOLATIONS.put(CheckFullName.AVOID_STAR_IMPORT, AvoidStarImport::new);
        RECIPE_MAP_NO_VIOLATIONS.put(CheckFullName.FINAL_LOCAL_VARIABLE, FinalLocalVariable::new);
        RECIPE_MAP_NO_VIOLATIONS.put(CheckFullName.UNUSED_LOCAL_VARIABLE, UnusedLocalVariable::new);
        RECIPE_MAP_NO_VIOLATIONS.put(CheckFullName.EMPTY_STATEMENT, EmptyStatement::new);
        RECIPE_MAP_NO_VIOLATIONS.put(CheckFullName.HEX_LITERAL_CASE, HexLiteralCase::new);
        RECIPE_MAP_NO_VIOLATIONS.put(CheckFullName.NUMERICAL_PREFIXES_INF_SUF_CASE,
            NumericalPrefixesInfixesSuffixesCharacterCase::new);
        RECIPE_MAP_NO_VIOLATIONS.put(CheckFullName.MISSING_DEPRECATED, MissingDeprecated::new);
        RECIPE_MAP_NO_VIOLATIONS.put(CheckFullName.MISSING_OVERRIDE, MissingOverride::new);
        RECIPE_MAP_NO_VIOLATIONS.put(CheckFullName.REDUNDANT_IMPORT, RedundantImport::new);
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
        final List<Recipe> recipes = new ArrayList<>();

        recipes.add(new ViolationMarkerRecipe(violations));

        final Map<CheckstyleCheck, List<CheckstyleViolation>> groupedViolations =
                violations.stream()
                        .collect(Collectors.groupingBy(CheckstyleViolation::getSource));

        for (Map.Entry<CheckstyleCheck, List<CheckstyleViolation>> entry
                : groupedViolations.entrySet()) {
            final CheckstyleCheck check = entry.getKey();

            Optional.ofNullable(config.get(check))
                    .ifPresent(checkConfig -> addRecipe(recipes, checkConfig, entry.getValue()));
        }

        return recipes;
    }

    private static void addRecipe(List<Recipe> recipes, CheckConfiguration checkConfig,
                                  List<CheckstyleViolation> violations) {
        final CheckFullName checkName = checkConfig.getCheckName();

        Optional.ofNullable(RECIPE_MAP_NO_VIOLATIONS.get(checkName))
                .ifPresent(factory -> recipes.add(factory.get()));

        Optional.ofNullable(RECIPE_MAP_WITH_CONFIG_NO_VIOLATIONS.get(checkName))
                .ifPresent(factory -> recipes.add(factory.apply(checkConfig)));

        Optional.ofNullable(RECIPE_MAP_WITH_CONFIG.get(checkName))
                .ifPresent(factory -> {
                    recipes.add(factory.apply(violations, checkConfig));
                });

        Optional.ofNullable(RECIPE_MAP.get(checkName))
                .ifPresent(factory -> {
                    recipes.add(factory.apply(violations));
                });
    }

}
