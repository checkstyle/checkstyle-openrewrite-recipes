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

public class EmptyStatementTest extends AbstractRecipeTestSupport {

    @Override
    protected String getSubpackage() {
        return "emptystatement";
    }

    @RecipeTest
    void simpleEmptyStatement(ReportParser parser) throws Exception {
        verify(parser, "SimpleEmptyStatement");
    }

    @Test
    public void checkDescription() {
        final EmptyStatement recipe =
                new EmptyStatement(List.of());

        final String expectedDescription =
                "Remove empty statements (standalone semicolons) from code.";

        assertWithMessage("Invalid description")
                .that(recipe.getDescription())
                .isEqualTo(expectedDescription);
    }

    @Test
    public void checkDisplayName() {
        final EmptyStatement recipe =
                new EmptyStatement(List.of());

        final String expectedDisplayName =
                "EmptyStatement recipe";

        assertWithMessage("Invalid display name")
                .that(recipe.getDisplayName())
                .isEqualTo(expectedDisplayName);
    }

    @RecipeTest
    void switchMixedCase(ReportParser parser) throws Exception {
        verify(parser, "SwitchMixedCase");
    }

    @RecipeTest
    void loopMixedCase(ReportParser parser) throws Exception {
        verify(parser, "LoopMixedCase");
    }

    @RecipeTest
    void blockMixedCase(ReportParser parser) throws Exception {
        verify(parser, "BlockMixedCase");
    }

    @RecipeTest
    void forEachEmptyStatement(ReportParser parser) throws Exception {
        verify(parser, "ForEachEmptyStatement");
    }

    @RecipeTest
    void forEachMixedCase(ReportParser parser) throws Exception {
        verify(parser, "ForEachMixedCase");
    }

    @RecipeTest
    void nonViolationEmptyStatement(ReportParser parser) throws Exception {
        verify(parser, "NoViolationEmptyStatement");
    }

    @RecipeTest
    void switchCaseNoViolation(ReportParser parser) throws Exception {
        verify(parser, "SwitchCaseNoViolation");
    }

    @RecipeTest
    void ifEmptyStatement(ReportParser parser) throws Exception {
        verify(parser, "IfEmptyStatement");
    }

    @RecipeTest
    void whileEmptyStatement(ReportParser parser) throws Exception {
        verify(parser, "WhileEmptyStatement");
    }

    @RecipeTest
    void switchCaseNestedViolation(ReportParser parser) throws Exception {
        verify(parser, "SwitchCaseNestedViolation");
    }

    @RecipeTest
    void multiFileEmptyStatement(ReportParser parser) throws Exception {
        verify(parser, "MultiFileEmptyStatement1", "MultiFileEmptyStatement2");
    }
}
