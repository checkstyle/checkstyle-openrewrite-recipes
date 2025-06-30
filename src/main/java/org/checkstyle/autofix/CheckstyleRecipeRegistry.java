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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.checkstyle.autofix.recipe.UpperEll;
import org.openrewrite.Recipe;

public final class CheckstyleRecipeRegistry {

    private static final Map<String, Function<List<CheckstyleViolation>, Recipe>> RECIPE_MAP =
            new HashMap<>();

    static {
        RECIPE_MAP.put("com.puppycrawl.tools.checkstyle.checks.UpperEllCheck",
                UpperEll::new);
    }

    private CheckstyleRecipeRegistry() {
        // utility class
    }

    public static List<Recipe> getRecipes(List<CheckstyleViolation> violations) {

        final Map<String, List<CheckstyleViolation>> violationsByCheck = violations.stream()
                .collect(Collectors.groupingBy(CheckstyleViolation::getSource));

        final List<Recipe> recipes = new ArrayList<>();

        for (Map.Entry<String, List<CheckstyleViolation>> entry : violationsByCheck.entrySet()) {
            final String checkName = entry.getKey();
            final List<CheckstyleViolation> records = entry.getValue();

            final Function<List<CheckstyleViolation>, Recipe> recipeFactory =
                    RECIPE_MAP.get(checkName);
            if (recipeFactory != null) {
                recipes.add(recipeFactory.apply(records));
            }
        }

        return recipes;
    }
}
