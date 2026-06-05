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

import java.util.List;

import org.checkstyle.autofix.parser.ReportParser;
import org.junit.jupiter.api.Test;

public class ArrayTrailingCommaTest extends AbstractRecipeTestSupport {

    @Override
    protected String getSubpackage() {
        return "arraytrailingcomma";
    }

    @Test
    public void checkDescription() {
        final ArrayTrailingComma recipe =
            new ArrayTrailingComma(List.of());

        final String expectedDescription =
            "Add trailing comma to array initializers"
                + " to satisfy the ArrayTrailingComma check.";

        assertWithMessage("Invalid description")
            .that(recipe.getDescription())
            .isEqualTo(expectedDescription);
    }

    @Test
    public void checkDisplayName() {
        final ArrayTrailingComma recipe =
            new ArrayTrailingComma(List.of());

        final String expectedDisplayName =
            "ArrayTrailingComma recipe";

        assertWithMessage("Invalid display name")
            .that(recipe.getDisplayName())
            .isEqualTo(expectedDisplayName);
    }

    @RecipeTest
    void simpleArrayTrailingComma(ReportParser parser) throws Exception {
        verify(parser, "SimpleArrayTrailingComma");
    }

    @RecipeTest
    void noViolationArrayTrailingComma(ReportParser parser) throws Exception {
        verify(parser, "NoViolationArrayTrailingComma");
    }

    @RecipeTest
    void differentLineArrayTrailingComma(ReportParser parser) throws Exception {
        verify(parser, "DifferentLineArrayTrailingComma");
    }

}
