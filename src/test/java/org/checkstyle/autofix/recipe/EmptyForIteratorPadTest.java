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

import org.checkstyle.autofix.parser.ReportParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmptyForIteratorPadTest extends AbstractRecipeTestSupport {

    public EmptyForIteratorPadTest() {
    }

    @Override
    protected String getSubpackage() {
        return "emptyforiteratorpad";
    }

    @Test
    public void checkDescription() {
        final EmptyForIteratorPad recipe = new EmptyForIteratorPad(null);

        final String expectedDescription = "Fixes Checkstyle EmptyForIteratorPad violations.";

        Assertions.assertEquals(expectedDescription, recipe.getDescription(),
                "Invalid description");
    }

    @Test
    public void checkDisplayName() {
        final EmptyForIteratorPad recipe = new EmptyForIteratorPad(null);

        final String expectedDisplayName = "EmptyForIteratorPad recipe";

        Assertions.assertEquals(expectedDisplayName, recipe.getDisplayName(),
                "Invalid display name");
    }

    @RecipeTest
    void defaultNoSpace(ReportParser parser) throws Exception {
        verify(parser, "DefaultNoSpace");
    }

    @RecipeTest
    void optionSpace(ReportParser parser) throws Exception {
        verify(parser, "OptionSpace");
    }

}
