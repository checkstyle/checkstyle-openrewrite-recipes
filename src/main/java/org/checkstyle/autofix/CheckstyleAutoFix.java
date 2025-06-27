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

import java.util.Collections;
import java.util.List;

import org.checkstyle.autofix.recipe.UpperEll;
import org.openrewrite.Recipe;

/**
 * Main recipe that automatically fixes all supported Checkstyle violations.
 */
public class CheckstyleAutoFix extends Recipe {

    @Override
    public String getDisplayName() {
        return "Checkstyle autoFix";
    }

    @Override
    public String getDescription() {
        return "Automatically fixes Checkstyle violations.";
    }

    @Override
    public List<Recipe> getRecipeList() {
        return Collections.singletonList(

                new UpperEll()
        );
    }
}
