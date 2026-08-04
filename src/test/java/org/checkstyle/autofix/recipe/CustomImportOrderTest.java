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

public class CustomImportOrderTest extends AbstractRecipeTestSupport {

    public CustomImportOrderTest() {
    }

    @Override
    protected String getSubpackage() {
        return "customimportorder";
    }

    @Test
    public void checkDescription() {
        final CustomImportOrder recipe = new CustomImportOrder();
        final String expectedDescription = "Orders imports to fix CustomImportOrder "
                + "violations using OpenRewrite's OrderImports.";
        assertWithMessage("Invalid description")
                .that(recipe.getDescription())
                .isEqualTo(expectedDescription);
    }

    @Test
    public void checkDisplayName() {
        final CustomImportOrder recipe = new CustomImportOrder();
        final String expectedDisplayName = "CustomImportOrder Recipe";
        assertWithMessage("Invalid display name")
                .that(recipe.getDisplayName())
                .isEqualTo(expectedDisplayName);
    }

    @RecipeTest
    void customCaseOne(ReportParser parser) throws Exception {
        verify(parser, "CustomCaseOne");
    }

    @RecipeTest
    void customCaseTwo(ReportParser parser) throws Exception {
        verify(parser, "CustomCaseTwo");
    }

    @RecipeTest
    void customCaseThree(ReportParser parser) throws Exception {
        verify(parser, "CustomCaseThree");
    }

}
