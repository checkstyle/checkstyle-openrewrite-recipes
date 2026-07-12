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

public class ConstructorsDeclarationGroupingTest extends AbstractRecipeTestSupport {

    @Override
    protected String getSubpackage() {
        return "constructorsdeclarationgrouping";
    }

    @Test
    public void checkDescription() {
        final ConstructorsDeclarationGrouping recipe =
                new ConstructorsDeclarationGrouping(null);

        final String expectedDescription =
                "Groups all constructors together in a class.";

        assertWithMessage("Invalid description")
                .that(recipe.getDescription())
                .isEqualTo(expectedDescription);
    }

    @Test
    public void checkDisplayName() {
        final ConstructorsDeclarationGrouping recipe =
                new ConstructorsDeclarationGrouping(null);

        final String expectedDisplayName =
                "ConstructorsDeclarationGrouping recipe";

        assertWithMessage("Invalid display name")
                .that(recipe.getDisplayName())
                .isEqualTo(expectedDisplayName);
    }

    @RecipeTest
    void simple(ReportParser parser) throws Exception {
        verify(parser, "Simple");
    }

    @RecipeTest
    void separatedByField(ReportParser parser) throws Exception {
        verify(parser, "SeparatedByField");
    }

    @RecipeTest
    void separatedByMethod(ReportParser parser) throws Exception {
        verify(parser, "SeparatedByMethod");
    }

    @RecipeTest
    void separatedByInnerClass(ReportParser parser) throws Exception {
        verify(parser, "SeparatedByInnerClass");
    }

    @RecipeTest
    void separatedByComment(ReportParser parser) throws Exception {
        verify(parser, "SeparatedByComment");
    }

    @RecipeTest
    void multipleViolations(ReportParser parser) throws Exception {
        verify(parser, "MultipleViolations");
    }

    @RecipeTest
    void innerClass(ReportParser parser) throws Exception {
        verify(parser, "InnerClass");
    }

    @RecipeTest
    void innerEnum(ReportParser parser) throws Exception {
        verify(parser, "InnerEnum");
    }

    @RecipeTest
    void onlyOneViolation(ReportParser parser) throws Exception {
        verify(parser, "OnlyOneViolation");
    }

    @RecipeTest
    void noViolation(ReportParser parser) throws Exception {
        verify(parser, "NoViolation");
    }

    @RecipeTest
    void modifiers(ReportParser parser) throws Exception {
        verify(parser, "Modifiers");
    }

    @RecipeTest
    void annotations(ReportParser parser) throws Exception {
        verify(parser, "Annotations");
    }

    @RecipeTest
    void outerClassSeparated(ReportParser parser) throws Exception {
        verify(parser, "OuterClassSeparated");
    }

    @RecipeTest
    void multipleGroups(ReportParser parser) throws Exception {
        verify(parser, "MultipleGroups");
    }

    @RecipeTest
    void withJavadoc(ReportParser parser) throws Exception {
        verify(parser, "WithJavadoc");
    }

    @RecipeTest
    void withComments(ReportParser parser) throws Exception {
        verify(parser, "WithComments");
    }

    @RecipeTest
    void noConstructors(ReportParser parser) throws Exception {
        verify(parser, "NoConstructors");
    }

    @RecipeTest
    void suppressedViolations(ReportParser parser) throws Exception {
        verify(parser, "SuppressedViolations");
    }

    @RecipeTest
    void withPrefixWhitespaces(ReportParser parser) throws Exception {
        verify(parser, "WithPrefixWhitespaces");
    }

    @RecipeTest
    void sameLineDeclarations(ReportParser parser) throws Exception {
        verify(parser, "SameLineDeclarations");
    }

    @RecipeTest
    void multipleFiles(ReportParser parser) throws Exception {
        verify(parser, "MultipleFiles1", "MultipleFiles2");
    }

    @RecipeTest
    void idempotent(ReportParser parser) throws Exception {
        verify(parser, "Idempotent1");
        verify(parser, "Idempotent2");
    }

    @RecipeTest
    void classEndsWithConstructor(ReportParser parser) throws Exception {
        verify(parser, "ClassEndsWithConstructor");
    }

    @RecipeTest
    void orderByParameterCount(ReportParser parser) throws Exception {
        verify(parser, "OrderByParameterCount");
    }

    @RecipeTest
    void orderWithGrouping(ReportParser parser) throws Exception {
        verify(parser, "OrderWithGrouping");
    }

    @RecipeTest
    void orderStableTieBreak(ReportParser parser) throws Exception {
        verify(parser, "OrderStableTieBreak");
    }

    @RecipeTest
    void orderWithSuppressed(ReportParser parser) throws Exception {
        verify(parser, "OrderWithSuppressed");
    }

    @RecipeTest
    void orderNoViolation(ReportParser parser) throws Exception {
        verify(parser, "OrderNoViolation");
    }

    @RecipeTest
    void orderMultipleClasses(ReportParser parser) throws Exception {
        verify(parser, "OrderMultipleClasses");
    }

    @RecipeTest
    void orderWithFieldBeforeFirstConstructor(ReportParser parser) throws Exception {
        verify(parser, "OrderWithFieldBeforeFirstConstructor");
    }

    @RecipeTest
    void orderWithGroupingViolationSuppressed(ReportParser parser) throws Exception {
        verify(parser, "OrderWithGroupingViolationSuppressed");
    }

    @RecipeTest
    void orderNoViolationOutOfOrder(ReportParser parser) throws Exception {
        verify(parser, "OrderNoViolationOutOfOrder");
    }

    @RecipeTest
    void complex(ReportParser parser) throws Exception {
        verify(parser, "Complex");
    }

    @RecipeTest
    void orderWithGroupingOnly(ReportParser parser) throws Exception {
        verify(parser, "OrderWithGroupingOnly");
    }

    @RecipeTest
    void killMutation2(ReportParser parser) throws Exception {
        verify(parser, "KillMutation1");
    }
}
