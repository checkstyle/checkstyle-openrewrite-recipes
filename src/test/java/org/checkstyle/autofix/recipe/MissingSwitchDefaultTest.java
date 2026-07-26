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

public class MissingSwitchDefaultTest extends AbstractRecipeTestSupport {

    public MissingSwitchDefaultTest() {
    }

    @Override
    protected String getSubpackage() {
        return "missingswitchdefault";
    }

    @Test
    public void checkDisplayName() {
        final MissingSwitchDefault recipe = new MissingSwitchDefault();
        final String expectedDisplayName = "MissingSwitchDefault recipe";

        assertWithMessage("Invalid display name")
                .that(recipe.getDisplayName())
                .isEqualTo(expectedDisplayName);
    }

    @Test
    public void checkDescription() {
        final MissingSwitchDefault recipe = new MissingSwitchDefault();
        final String expectedDescription = "Adds a default case to switch statements "
                + "if it is missing.";

        assertWithMessage("Invalid description")
                .that(recipe.getDescription())
                .isEqualTo(expectedDescription);
    }

    @RecipeTest
    void simple(ReportParser parser) throws Exception {
        verify(parser, "Simple");
    }

    @RecipeTest
    void nested(ReportParser parser) throws Exception {
        verify(parser, "Nested");
    }

    @RecipeTest
    void hasDefault(ReportParser parser) throws Exception {
        verify(parser, "HasDefault");
    }

    @RecipeTest
    void missingBreak(ReportParser parser) throws Exception {
        verify(parser, "MissingBreak");
    }

    @RecipeTest
    void arrowStyle(ReportParser parser) throws Exception {
        verify(parser, "ArrowStyle");
    }

    @RecipeTest
    void emptySwitch(ReportParser parser) throws Exception {
        verify(parser, "EmptySwitch");
    }

    @RecipeTest
    void emptyCase(ReportParser parser) throws Exception {
        verify(parser, "EmptyCase");
    }

    @RecipeTest
    void terminalReturn(ReportParser parser) throws Exception {
        verify(parser, "TerminalReturn");
    }

    @RecipeTest
    void killMutation(ReportParser parser) throws Exception {
        verify(parser, "KillMutation");
    }

}
