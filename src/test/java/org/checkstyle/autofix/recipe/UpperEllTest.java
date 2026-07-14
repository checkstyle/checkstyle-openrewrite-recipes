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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.checkstyle.autofix.parser.ReportParser;
import org.junit.jupiter.api.Test;

public class UpperEllTest extends AbstractRecipeTestSupport {

    @Override
    protected String getSubpackage() {
        return "upperell";
    }

    @Test
    public void checkDescription() {
        final UpperEll recipe = new UpperEll();

        final String expectedDescription =
                "Replace lowercase 'l' suffix in long literals with uppercase 'L' "
                        + "to improve readability.";

        assertEquals(expectedDescription, recipe.getDescription(),
                "Invalid description");
    }

    @Test
    public void checkDisplayName() {
        final UpperEll recipe = new UpperEll();

        final String expectedDisplayName = "UpperEll recipe";

        assertEquals(expectedDisplayName, recipe.getDisplayName(),
                "Invalid display name");
    }

    @RecipeTest
    void hexOctalLiteral(ReportParser parser) throws Exception {
        verify(parser, "HexOctalLiteral");
    }

    @RecipeTest
    void complexLongLiterals(ReportParser parser) throws Exception {
        verify(parser, "ComplexLongLiterals");
    }

    @RecipeTest
    void stringAndComments(ReportParser parser) throws Exception {
        verify(parser, "StringAndComments");
    }

    @RecipeTest
    void symbolicLiteral(ReportParser parser) throws Exception {
        verify(parser, "SymbolicLiterals");
    }

    @RecipeTest
    void multiFile(ReportParser parser) throws Exception {
        verify(parser, "MultiFileA", "MultiFileB");
    }

    @RecipeTest
    void killMutation(ReportParser parser) throws Exception {
        verify(parser, "KillMutation");
    }

}
