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
        final UnusedLocalVariable recipe = new UnusedLocalVariable(null);
        assertWithMessage("Invalid display name")
                .that(recipe.getDisplayName())
                .isEqualTo("UnusedLocalVariable recipe");
    }

    @Test
    public void checkDescription() {
        final UnusedLocalVariable recipe = new UnusedLocalVariable(null);
        assertWithMessage("Invalid description")
                .that(recipe.getDescription())
                .isEqualTo("Removes local variable declarations that are never used.");
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
    void pathMatch(ReportParser parser) throws Exception {
        verify(parser, "PathMatchA", "PathMatchB");
    }

}
