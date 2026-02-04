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

/**
 * Test cases for EmptyStatement recipe.
 */
public class EmptyStatementTest extends AbstractRecipeTestSupport {

    @Test
    void metadata() {
        final EmptyStatement recipe = new EmptyStatement();

        assertEquals("EmptyStatement - Remove empty statements",
                recipe.getDisplayName());
        assertEquals("Detects and removes empty statements (standalone semicolons).",
                recipe.getDescription());
    }

    @Override
    protected String getSubpackage() {
        return "emptystatement";
    }

    @RecipeTest
    void removeDoubleSermicolon(ReportParser parser) throws Exception {
        verify(parser, "DoubleSermicolon");
    }

    @RecipeTest
    void removeEmptyStatementInBlock(ReportParser parser) throws Exception {
        verify(parser, "EmptyStatementInBlock");
    }

    @RecipeTest
    void multipleEmptyStatements(ReportParser parser) throws Exception {
        verify(parser, "MultipleEmptyStatements");
    }

    @RecipeTest
    void nestedEmptyStatements(ReportParser parser) throws Exception {
        verify(parser, "NestedEmptyStatements");
    }

    @RecipeTest
    void emptyIfStatement(ReportParser parser) throws Exception {
        verify(parser, "EmptyIfStatement");
    }

    @RecipeTest
    void emptyWhileLoop(ReportParser parser) throws Exception {
        verify(parser, "EmptyWhileLoop");
    }

    @RecipeTest
    void emptyForLoop(ReportParser parser) throws Exception {
        verify(parser, "EmptyForLoop");
    }

    @RecipeTest
    void emptyDoWhileLoop(ReportParser parser) throws Exception {
        verify(parser, "EmptyDoWhileLoop");
    }

    @RecipeTest
    void nestedWhileLoop(ReportParser parser) throws Exception {
        verify(parser, "NestedWhileLoop");
    }

    @RecipeTest
    void nestedForLoop(ReportParser parser) throws Exception {
        verify(parser, "NestedForLoop");
    }

    @RecipeTest
    void nestedDoWhileLoop(ReportParser parser) throws Exception {
        verify(parser, "NestedDoWhileLoop");
    }
}
