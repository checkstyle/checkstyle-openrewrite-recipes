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
import org.junit.jupiter.api.Test;

public class HexLiteralCaseTest extends AbstractRecipeTestSupport {

    @Override
    protected String getSubpackage() {
        return "hexliterialcase";
    }

    @RecipeTest
    void hexLiteral(ReportParser parser) throws Exception {
        verify(parser, "HexLiteralCase");
    }

    @RecipeTest
    void hexLiteralTwo(ReportParser parser) throws Exception {
        verify(parser, "HexLiteralCaseTwo");
    }

    @RecipeTest
    void hexLiteralThree(ReportParser parser) throws Exception {
        verify(parser, "HexLiteralCaseThree");
    }

    @Test
    public void checkDisplayName() {
        final HexLiteralCase recipe = new HexLiteralCase();
        final String expectedDisplayName = "HexLiteralCase Recipe";

        assertWithMessage("Invalid display name")
                .that(recipe.getDisplayName())
                .isEqualTo(expectedDisplayName);
    }

    @Test
    public void checkDescription() {
        final HexLiteralCase recipe = new HexLiteralCase();
        final String expectedDescription =
                "Replace HexLiteral lowercase (a-f) with UpperCase (A-F) to improve readability.";

        assertWithMessage("Invalid description")
                .that(recipe.getDescription())
                .isEqualTo(expectedDescription);
    }

    @RecipeTest
    void multiFile(ReportParser parser) throws Exception {
        verify(parser, "MultiFileA", "MultiFileB");
    }
}
