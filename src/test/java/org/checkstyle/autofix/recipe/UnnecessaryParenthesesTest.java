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

import static com.google.common.truth.Truth.assertWithMessage;

import org.checkstyle.autofix.parser.ReportParser;

public class UnnecessaryParenthesesTest extends AbstractRecipeTestSupport {

    @Override
    protected String getSubpackage() {
        return "unnecessaryparentheses";
    }

    @RecipeTest
    public void checkDescription(ReportParser parser) {
        final UnnecessaryParentheses recipe = new UnnecessaryParentheses(null);
        final String expectedDescription =
            "Removes unnecessary parentheses at Checkstyle violation locations.";
        assertWithMessage("Invalid description")
                .that(recipe.getDescription())
                .isEqualTo(expectedDescription);
    }

    @RecipeTest
    public void checkDisplayName(ReportParser parser) {
        final UnnecessaryParentheses recipe = new UnnecessaryParentheses(null);
        final String expectedDisplayName = "UnnecessaryParentheses Recipe";
        assertWithMessage("Invalid display name")
                .that(recipe.getDisplayName())
                .isEqualTo(expectedDisplayName);
    }

    @RecipeTest
    void simpleUnnecessaryParentheses(ReportParser parser) throws Exception {
        verify(parser, "SimpleUnnecessaryParentheses");
    }

    @RecipeTest
    void multiFile(ReportParser parser) throws Exception {
        verify(parser, "MultiFile1", "MultiFile2");
    }
}
