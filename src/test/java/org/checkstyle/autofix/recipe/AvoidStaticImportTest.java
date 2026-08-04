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

public class AvoidStaticImportTest extends AbstractRecipeTestSupport {

    public AvoidStaticImportTest() {
    }

    @Override
    protected String getSubpackage() {
        return "avoidstaticimport";
    }

    @Test
    public void checkDescription() {
        final AvoidStaticImport recipe = new AvoidStaticImport();

        final String expectedDescription =
                "Removes static imports and fully qualifies their usages.";

        assertEquals(expectedDescription, recipe.getDescription(), "Invalid description");
    }

    @Test
    public void checkDisplayName() {
        final AvoidStaticImport recipe = new AvoidStaticImport();

        final String expectedDisplayName =
                "Avoid static import recipe";

        assertEquals(expectedDisplayName, recipe.getDisplayName(), "Invalid display name");
    }

    @RecipeTest
    void methodStaticImport(ReportParser parser) throws Exception {
        verify(parser, "MethodStaticImport");
    }

    @RecipeTest
    void fieldStaticImport(ReportParser parser) throws Exception {
        verify(parser, "FieldStaticImport");
    }

    @RecipeTest
    void starStaticImport(ReportParser parser) throws Exception {
        verify(parser, "StarStaticImport");
    }

    @RecipeTest
    void variableDeclarationStaticImport(ReportParser parser) throws Exception {
        verify(parser, "VariableDeclarationStaticImport");
    }

    @RecipeTest
    void qualifiedMethodStaticImport(ReportParser parser) throws Exception {
        verify(parser, "QualifiedMethodStaticImport");
    }

    @RecipeTest
    void nestedClassStaticImport(ReportParser parser) throws Exception {
        verify(parser, "NestedClassStaticImport");
    }

    @RecipeTest
    void fieldClassStaticImport(ReportParser parser) throws Exception {
        verify(parser, "FieldClassStaticImport");
    }

    @RecipeTest
    void deeplyNestedClassStaticImport(ReportParser parser) throws Exception {
        verify(parser, "DeeplyNestedClassStaticImport");
    }

}
