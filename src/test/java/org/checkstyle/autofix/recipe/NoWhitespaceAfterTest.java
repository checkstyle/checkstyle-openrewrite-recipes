///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle-openrewrite-recipes: Automatically fix Checkstyle violations with OpenRewrite.
// Copyright (C) 2026 The Checkstyle OpenRewrite Recipes Authors
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
import org.junit.jupiter.api.Test;

public class NoWhitespaceAfterTest extends AbstractRecipeTestSupport {

    public NoWhitespaceAfterTest() {
    }

    @Override
    protected String getSubpackage() {
        return "nowhitespaceafter";
    }

    @Test
    public void checkDescription() {
        final NoWhitespaceAfter recipe = new NoWhitespaceAfter();

        final String expectedDescription =
                "Removes whitespace after specific tokens where forbidden by Checkstyle.";

        assertWithMessage("Invalid description")
                .that(recipe.getDescription())
                .isEqualTo(expectedDescription);
    }

    @Test
    public void checkDisplayName() {
        final NoWhitespaceAfter recipe = new NoWhitespaceAfter();

        final String expectedDisplayName =
                "NoWhitespaceAfter recipe";

        assertWithMessage("Invalid display name")
                .that(recipe.getDisplayName())
                .isEqualTo(expectedDisplayName);
    }

    @RecipeTest
    void testTokens(ReportParser parser) throws Exception {
        verify(parser, "Tokens");
    }

    @RecipeTest
    void testAllTokens(ReportParser parser) throws Exception {
        verify(parser, "AllTokens");
    }

    @RecipeTest
    void testLineBreaks(ReportParser parser) throws Exception {
        verify(parser, "LineBreaks");
    }

}
