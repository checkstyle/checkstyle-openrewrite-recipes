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

public class UnusedLocalVariableTest extends AbstractRecipeTestSupport {

    @Override
    protected String getSubpackage() {
        return "unusedlocalvariable";
    }

    @Test
    public void checkDisplayName() {
        final UnusedLocalVariable recipe = new UnusedLocalVariable();
        assertWithMessage("Invalid display name")
                .that(recipe.getDisplayName())
                .isEqualTo("UnusedLocalVariable recipe");
    }

    @Test
    public void checkDescription() {
        final UnusedLocalVariable recipe = new UnusedLocalVariable();
        assertWithMessage("Invalid description")
                .that(recipe.getDescription())
                .isEqualTo("Removes pure unused variables while keeping statements with "
                        + "side effects unchanged to ensure maximum code safety.");
    }

    @RecipeTest
    void simpleUnused(ReportParser parser) throws Exception {
        verify(parser, "SimpleUnused");
    }

    @RecipeTest
    void multipleUnused(ReportParser parser) throws Exception {
        verify(parser, "MultipleUnused");
    }

    @RecipeTest
    void classFields(ReportParser parser) throws Exception {
        verify(parser, "ClassFields");
    }

    @RecipeTest
    void classFieldsWithPathFilter(ReportParser parser) throws Exception {
        verify(parser, "PathFilterClean", "PathFilter");
    }

    @RecipeTest
    void multiVarDeclaration(ReportParser parser) throws Exception {
        verify(parser, "MultiVarDeclaration");
    }

    @RecipeTest
    void nestedBlocks(ReportParser parser) throws Exception {
        verify(parser, "NestedBlocks");
    }

    @RecipeTest
    void sideEffect(ReportParser parser) throws Exception {
        verify(parser, "SideEffect");
    }

    @RecipeTest
    void pathMatch(ReportParser parser) throws Exception {
        verify(parser, "PathMatchA", "PathMatchB");
    }

    @RecipeTest
    void assignmentWithMethods(ReportParser parser) throws Exception {
        verify(parser, "AssignmentWithMethods");
    }

    @RecipeTest
    void unsafeInitializer(ReportParser parser) throws Exception {
        verify(parser, "UnsafeInitializer");
    }

    @RecipeTest
    void recipeEnhancements(ReportParser parser) throws Exception {
        verify(parser, "RecipeEnhancements");
    }

    @RecipeTest
    void simpleExpressions(ReportParser parser) throws Exception {
        verify(parser, "SimpleExpressions");
    }

    @RecipeTest
    void partialRemoval(ReportParser parser) throws Exception {
        verify(parser, "PartialRemoval");
    }

    @RecipeTest
    void methodScope(ReportParser parser) throws Exception {
        verify(parser, "MethodScope");
    }

    @RecipeTest
    void records(ReportParser parser) throws Exception {
        verify(parser, "Records");
    }

    @RecipeTest
    void switchExpressions(ReportParser parser) throws Exception {
        verify(parser, "SwitchExpressions");
    }

    @RecipeTest
    void compoundAssignment(ReportParser parser) throws Exception {
        verify(parser, "CompoundAssignment");
    }

    @RecipeTest
    void killMutations(ReportParser parser) throws Exception {
        verify(parser, "KillMutations");
    }

    @RecipeTest
    void killMutations2(ReportParser parser) throws Exception {
        verify(parser, "KillMutations2");
    }

    @RecipeTest
    void killMutations3(ReportParser parser) throws Exception {
        verify(parser, "KillMutations3");
    }

    @RecipeTest
    void killMutations4(ReportParser parser) throws Exception {
        verify(parser, "KillMutations4");
    }

    @RecipeTest
    void killMutations5(ReportParser parser) throws Exception {
        verify(parser, "KillMutations5");
    }

    @RecipeTest
    void staticInitializer(ReportParser parser) throws Exception {
        verify(parser, "StaticInitializer");
    }

    @RecipeTest
    void nonIncrementUnary(ReportParser parser) throws Exception {
        verify(parser, "NonIncrementUnary");
    }

    @RecipeTest
    void multipleFiles(ReportParser parser) throws Exception {
        verify(parser, "MultipleFilesA", "MultipleFilesB");
    }

    @RecipeTest
    void orphanedAssignments(ReportParser parser) throws Exception {
        verify(parser, "OrphanedAssignments");
    }

    @RecipeTest
    void killRemainingMutations(ReportParser parser) throws Exception {
        verify(parser, "KillRemainingMutations");
    }

    @RecipeTest
    void killRemainingMutations2(ReportParser parser) throws Exception {
        verify(parser, "KillRemainingMutations2");
    }

    @RecipeTest
    void killRemainingMutations3(ReportParser parser) throws Exception {
        verify(parser, "KillRemainingMutations3");
    }

    @RecipeTest
    void killRemainingMutations4(ReportParser parser) throws Exception {
        verify(parser, "KillRemainingMutations4");
    }

    @RecipeTest
    void killRemainingMutations5(ReportParser parser) throws Exception {
        verify(parser, "KillRemainingMutations5");
    }

    @RecipeTest
    void killRemainingMutations6(ReportParser parser) throws Exception {
        verify(parser, "KillRemainingMutations6");
    }

    @RecipeTest
    void killTransferCommentMutations(ReportParser parser) throws Exception {
        verify(parser, "KillTransferCommentMutations");
    }

    @RecipeTest
    void staleAlignment(ReportParser parser) throws Exception {
        verify(parser, "StaleAlignment");
    }

    @RecipeTest
    void methodNewAssignmentSideEffects(ReportParser parser) throws Exception {
        verify(parser, "MethodNewAssignmentSideEffects");
    }

    @RecipeTest
    void multipleSideEffects(ReportParser parser) throws Exception {
        verify(parser, "MultipleSideEffects");
    }

    @RecipeTest
    void unarySideEffect(ReportParser parser) throws Exception {
        verify(parser, "UnarySideEffect");
    }

    @RecipeTest
    void orphanedSideEffect(ReportParser parser) throws Exception {
        verify(parser, "OrphanedSideEffect");
    }

    @RecipeTest
    void orphanedSideEffectWithComments(ReportParser parser) throws Exception {
        verify(parser, "OrphanedSideEffectWithComments");
    }

}
