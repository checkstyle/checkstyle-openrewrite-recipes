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

public class AvoidStarImportTest extends AbstractRecipeTestSupport {

    @Override
    protected String getSubpackage() {
        return "avoidstarimport";
    }

    @Test
    public void checkDescription() {
        final AvoidStarImport recipe = new AvoidStarImport();

        final String expectedDescription =
                "Expands star imports into individual ones to avoid star imports.";

        assertEquals(expectedDescription, recipe.getDescription(), "Invalid description");
    }

    @Test
    public void checkDisplayName() {
        final AvoidStarImport recipe = new AvoidStarImport();

        final String expectedDisplayName =
                "Avoid Star Import recipe";

        assertEquals(expectedDisplayName, recipe.getDisplayName(), "Invalid display name");
    }

    @RecipeTest
    void starImportExpansion(ReportParser parser) throws Exception {
        verify(parser, "StarImportExpansion");
    }

    @RecipeTest
    void staticStarImportExpansion(ReportParser parser) throws Exception {
        verify(parser, "StaticStarImportExpansion");
    }

    @RecipeTest
    void multiFileExpansion(ReportParser parser) throws Exception {
        verify(parser, "MultiFileA", "MultiFileB", "MultiFile1", "MultiFile2");
    }

    @RecipeTest
    void multipleRequiredStarImports(ReportParser parser) throws Exception {
        verify(parser, "MultipleRequiredStarImports");
    }

    @RecipeTest
    void nonStaticFieldAndLocalVariableReferences(ReportParser parser) throws Exception {
        verify(parser, "NonStaticFieldAndLocalVariableReferences");
    }

    @RecipeTest
    void inheritedInstanceFieldReference(ReportParser parser) throws Exception {
        verify(parser, "InheritedInstanceFieldChild", "InheritedInstanceFieldParent");
    }

    @RecipeTest
    void proveMarkersAreGood(ReportParser parser) throws Exception {
        verify(parser, "MarkerCheck");
    }

    @RecipeTest
    void killMutation1(ReportParser parser) throws Exception {
        verify(parser, "KillMutation1");
    }

    @RecipeTest
    void primitiveClassAccess(ReportParser parser) throws Exception {
        verify(parser, "PrimitiveClassAccess");
    }

    @RecipeTest
    void killMutation2(ReportParser parser) throws Exception {
        verify(parser, "KillMutation2", "KillMutation2SupportClass");
    }
}
