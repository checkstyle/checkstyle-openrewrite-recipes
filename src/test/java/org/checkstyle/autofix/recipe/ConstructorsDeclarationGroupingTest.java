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
                new ConstructorsDeclarationGrouping();

        final String expectedDescription =
                "Groups all constructors together in a class.";

        assertWithMessage("Invalid description")
                .that(recipe.getDescription())
                .isEqualTo(expectedDescription);
    }

    @Test
    public void checkDisplayName() {
        final ConstructorsDeclarationGrouping recipe =
                new ConstructorsDeclarationGrouping();

        final String expectedDisplayName =
                "ConstructorsDeclarationGrouping recipe";

        assertWithMessage("Invalid display name")
                .that(recipe.getDisplayName())
                .isEqualTo(expectedDisplayName);
    }

    /**
     * Baseline test: two constructors separated by a method and a field.
     * Verifies that all violating constructors are moved to follow the initial group.
     */
    @RecipeTest
    void simple(ReportParser parser) throws Exception {
        verify(parser, "Simple");
    }

    /**
     * A single constructor is separated from the first constructor by a field declaration.
     * Verifies the minimal field-separation scenario.
     */
    @RecipeTest
    void separatedByField(ReportParser parser) throws Exception {
        verify(parser, "SeparatedByField");
    }

    /**
     * A single constructor is separated from the initial group by a method.
     * Verifies the minimal method-separation scenario.
     */
    @RecipeTest
    void separatedByMethod(ReportParser parser) throws Exception {
        verify(parser, "SeparatedByMethod");
    }

    /**
     * A single constructor is separated from the initial group by an inner class declaration.
     * Inner class declarations are non-constructor members and therefore trigger a violation.
     * Verifies the inner-class-separation scenario.
     */
    @RecipeTest
    void separatedByInnerClass(ReportParser parser) throws Exception {
        verify(parser, "SeparatedByInnerClass");
    }

    /**
     * Constructors are separated only by comments (single-line and block).
     * According to the rule, comments between constructors are allowed.
     * Verifies that no modification is made.
     */
    @RecipeTest
    void separatedByComment(ReportParser parser) throws Exception {
        verify(parser, "SeparatedByComment");
    }

    /**
     * Three constructors are each separated by a different member (field, field, method).
     * Verifies that all violating constructors are collected and inserted in their
     * original relative order after the initial constructor group.
     */
    @RecipeTest
    void multipleViolations(ReportParser parser) throws Exception {
        verify(parser, "MultipleViolations");
    }

    /**
     * An inner static class has a constructor separated from its group by a field.
     * Verifies that the fix is correctly scoped to the inner class body without
     * affecting the outer class.
     */
    @RecipeTest
    void innerClass(ReportParser parser) throws Exception {
        verify(parser, "InnerClass");
    }

    /**
     * A private enum nested in a class has a constructor separated by a field.
     * Verifies that the fix works correctly inside enum bodies.
     */
    @RecipeTest
    void innerEnum(ReportParser parser) throws Exception {
        verify(parser, "InnerEnum");
    }

    /**
     * Exactly one constructor is separated from the sole initial constructor.
     * Verifies the simplest possible violation: a single isolated constructor.
     */
    @RecipeTest
    void onlyOneViolation(ReportParser parser) throws Exception {
        verify(parser, "OnlyOneViolation");
    }

    /**
     * All constructors are already grouped together with no violations.
     * Verifies that the Recipe makes no changes when the code is already compliant.
     */
    @RecipeTest
    void noViolation(ReportParser parser) throws Exception {
        verify(parser, "NoViolation");
    }

    /**
     * Constructors with different access modifiers (public, protected, private) are
     * separated by a field. Verifies that modifiers are preserved correctly after moving.
     */
    @RecipeTest
    void modifiers(ReportParser parser) throws Exception {
        verify(parser, "Modifiers");
    }

    /**
     * Constructors carrying annotations (@SuppressWarnings, @Deprecated) are separated
     * by a method. Verifies that annotations are preserved correctly after moving.
     */
    @RecipeTest
    void annotations(ReportParser parser) throws Exception {
        verify(parser, "Annotations");
    }

    /**
     * The first constructor is preceded by fields and methods; a second constructor
     * appears after another method. Verifies that the fix works when the constructor
     * group does not start at the top of the class body.
     */
    @RecipeTest
    void outerClassSeparated(ReportParser parser) throws Exception {
        verify(parser, "OuterClassSeparated");
    }

    /**
     * Both the outer class and a nested static inner class independently have
     * ungrouped constructors. Verifies that the fix is applied correctly and
     * independently in each class scope.
     */
    @RecipeTest
    void multipleGroups(ReportParser parser) throws Exception {
        verify(parser, "MultipleGroups");
    }

    /**
     * All constructors and methods have Javadoc. Verifies that Javadoc of moved constructors
     * are moved together.
     */
    @RecipeTest
    void withJavadoc(ReportParser parser) throws Exception {
        verify(parser, "WithJavadoc");
    }

    /**
     * All constructors and methods have Javadoc. Verifies that Javadoc of moved constructors
     * are moved together.
     */
    @RecipeTest
    void withComments(ReportParser parser) throws Exception {
        verify(parser, "WithComments");
    }

    /**
     * There are no constructors at all. Verifies that no modification is made.
     */
    @RecipeTest
    void noConstructors(ReportParser parser) throws Exception {
        verify(parser, "NoConstructors");
    }

    /**
     * Not all constructors are grouped together, but violations are suppressed.
     * Verifies that no modification is made.
     */
    @RecipeTest
    void suppressedViolations(ReportParser parser) throws Exception {
        verify(parser, "SuppressedViolations");
    }

    /**
     * Contains ungrouped constructors with irregular leading whitespaces.
     * Verifies that leading whitespaces are carried along.
     */
    @RecipeTest
    void withPrefixWhitespaces(ReportParser parser) throws Exception {
        verify(parser, "WithPrefixWhitespaces");
    }

    /**
     * Multiple constructors or methods are declared on the same line.
     * Verifies that ungrouped constructors are moved correctly.
     */
    @RecipeTest
    void sameLineDeclarations(ReportParser parser) throws Exception {
        verify(parser, "SameLineDeclarations");
    }

    /**
     * Multiple files.
     * Verifies that violating constructors from the correct files are moved.
     */
    @RecipeTest
    void multipleFiles(ReportParser parser) throws Exception {
        verify(parser, "MultipleFiles1", "MultipleFiles2");
    }

    /**
     * Constructors that end up at violation locations after the first cycle of reordering
     * must not be wrongly moved again in a second cycle. Idempotent1 tests that
     * XPath-suppressed constructors at violation positions are correctly ignored;
     * Idempotent2 tests that already-moved constructors stay put.
     */
    @RecipeTest
    void idempotent(ReportParser parser) throws Exception {
        verify(parser, "Idempotent1");
        verify(parser, "Idempotent2");
    }

    /**
     * A class whose last declaration is a constructor, and an inner class whose last
     * declaration is also a constructor. Verifies that no IndexOutOfBoundsException occurs
     * when the constructor group extends to the end of the class body.
     */
    @RecipeTest
    void classEndsWithConstructor(ReportParser parser) throws Exception {
        verify(parser, "ClassEndsWithConstructor");
    }
}
