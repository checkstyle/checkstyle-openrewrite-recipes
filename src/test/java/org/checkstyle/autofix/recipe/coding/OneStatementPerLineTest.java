///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle-openrewrite-recipes: Automatically fix Checkstyle violations with OpenRewrite.
// Copyright (C) 2026 The Checkstyle OpenRewrite Recipes Authors
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

package org.checkstyle.autofix.recipe.coding;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.checkstyle.autofix.CheckFullName;
import org.checkstyle.autofix.parser.CheckConfiguration;
import org.checkstyle.autofix.parser.ReportParser;
import org.checkstyle.autofix.recipe.AbstractRecipeTestSupport;
import org.checkstyle.autofix.recipe.RecipeTest;
import org.junit.jupiter.api.Test;

public class OneStatementPerLineTest extends AbstractRecipeTestSupport {

    public OneStatementPerLineTest() {
    }

    @Override
    protected String getSubpackage() {
        return "onestatementperline";
    }

    @Test
    public void checkDisplayName() {
        final OneStatementPerLine recipe = new OneStatementPerLine(
                new CheckConfiguration(CheckFullName.ONE_STATEMENT_PER_LINE,
                        Map.of(), Map.of()));
        assertEquals("OneStatementPerLine recipe", recipe.getDisplayName(),
                "Invalid display name");
    }

    @RecipeTest
    void statementsOnOneLine(ReportParser parser) throws Exception {
        verify(parser, "StatementsOnOneLine");
    }

}
