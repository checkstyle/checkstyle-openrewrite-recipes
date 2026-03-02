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

import java.util.Collections;

import org.checkstyle.autofix.parser.ReportParser;
import org.junit.jupiter.api.Test;

public class UseEnhancedSwitchTest extends AbstractRecipeTestSupport {

    @Override
    protected String getSubpackage() {
        return "useenhancedswitch";
    }

    @Test
    public void checkDisplayName() {
        final UseEnhancedSwitch recipe = new UseEnhancedSwitch(Collections.emptyList());
        final String expectedDisplayName = "UseEnhancedSwitch recipe";

        assertWithMessage("Invalid display name")
                .that(recipe.getDisplayName()).isEqualTo(expectedDisplayName);
    }

    @Test
    public void checkDescription() {
        final UseEnhancedSwitch recipe = new UseEnhancedSwitch(Collections.emptyList());
        final String expectedDescription = "Convert switch statements using "
                + "colon syntax to enhanced switch using arrow syntax.";

        assertWithMessage("Invalid description")
                .that(recipe.getDescription()).isEqualTo(expectedDescription);
    }

    @RecipeTest
    void basicSwitchStatement(ReportParser parser) throws Exception {
        verify(parser, "BasicSwitchStatement");
    }

    @RecipeTest
    void switchExpression(ReportParser parser) throws Exception {
        verify(parser, "SwitchExpression");
    }

    @RecipeTest
    void noViolation(ReportParser parser) throws Exception {
        verify(parser, "NoViolation");
    }

    @RecipeTest
    void labeledBreak(ReportParser parser) throws Exception {
        verify(parser, "LabeledBreak");
    }

    @RecipeTest
    void selectiveSwitch(ReportParser parser) throws Exception {
        verify(parser, "SelectiveSwitch");
    }

    @RecipeTest
    void switchExpressionBlock(ReportParser parser) throws Exception {
        verify(parser, "SwitchExpressionBlock");
    }

    @RecipeTest
    void survivingMutations(ReportParser parser) throws Exception {
        verify(parser, "SurvivingMutations", "SurvivingMutations2");
    }

    @RecipeTest
    void mutationTests(ReportParser parser) throws Exception {
        verify(parser, "MutationTests");
    }

    @RecipeTest
    void blockWithBreak(ReportParser parser) throws Exception {
        verify(parser, "BlockWithBreak");
    }

    @RecipeTest
    void genericSwitchExpressionsTest(ReportParser parser) throws Exception {
        verify(parser, "GenericSwitchExpressions");
    }

    @RecipeTest
    void genericSwitchStatementsTest(ReportParser parser) throws Exception {
        verify(parser, "GenericSwitchStatements");
    }

    @RecipeTest
    void genericEnumExhaustivenessTest(ReportParser parser) throws Exception {
        verify(parser, "GenericEnumExhaustiveness");
    }

    @RecipeTest
    void mutationTests3(ReportParser parser) throws Exception {
        verify(parser, "MutationTests3");
    }

    @RecipeTest
    void mutationTests4(ReportParser parser) throws Exception {
        verify(parser, "MutationTests4");
    }

    @RecipeTest
    void mutationTests5(ReportParser parser) throws Exception {
        verify(parser, "MutationTests5");
    }

    @RecipeTest
    void mutationTests6(ReportParser parser) throws Exception {
        verify(parser, "MutationTests6");
    }

    @RecipeTest
    void mutationTests7(ReportParser parser) throws Exception {
        verify(parser, "MutationTests7");
    }

    @RecipeTest
    void mutationTests8(ReportParser parser) throws Exception {
        verify(parser, "MutationTests8");
    }

    @RecipeTest
    void mutationTests9(ReportParser parser) throws Exception {
        verify(parser, "MutationTests9");
    }

    @RecipeTest
    void mutationTests10(ReportParser parser) throws Exception {
        verify(parser, "MutationTests10");
    }

    @RecipeTest
    void mutationTests11(ReportParser parser) throws Exception {
        verify(parser, "MutationTests11");
    }

    @RecipeTest
    void mutationTests12(ReportParser parser) throws Exception {
        verify(parser, "MutationTests12");
    }

    @RecipeTest
    void mutationTests13(ReportParser parser) throws Exception {
        verify(parser, "MutationTests13");
    }

    @RecipeTest
    void mutationTests14(ReportParser parser) throws Exception {
        verify(parser, "MutationTests14");
    }

    @RecipeTest
    void mutationTests15(ReportParser parser) throws Exception {
        verify(parser, "MutationTests15");
    }

    @RecipeTest
    void mutationTests16(ReportParser parser) throws Exception {
        verify(parser, "MutationTests16");
    }

    @RecipeTest
    void mutationTests17(ReportParser parser) throws Exception {
        verify(parser, "MutationTests17");
    }

    @RecipeTest
    void mutationTests18(ReportParser parser) throws Exception {
        verify(parser, "MutationTests18");
    }

    @RecipeTest
    void mutationTests19(ReportParser parser) throws Exception {
        verify(parser, "MutationTests19");
    }

}
