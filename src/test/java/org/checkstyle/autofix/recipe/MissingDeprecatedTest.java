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

public class MissingDeprecatedTest extends AbstractRecipeTestSupport {

    @Override
    protected String getSubpackage() {
        return "missingdeprecated";
    }

    @Test
    public void checkDescription() {
        final MissingDeprecated recipe =
            new MissingDeprecated();
        final String expectedDescription = "Add @Deprecated annotation or {@code @deprecated} "
                + "Javadoc tag when either is missing.";
        assertWithMessage("Invalid description")
                .that(recipe.getDescription())
                .isEqualTo(expectedDescription);
    }

    @Test
    public void checkDisplayName() {
        final MissingDeprecated recipe = new MissingDeprecated();
        final String expectedDisplayName = "Missing Deprecated annotation or Javadoc tag";
        assertWithMessage("Invalid display name")
                .that(recipe.getDisplayName())
                .isEqualTo(expectedDisplayName);
    }

    @RecipeTest
    void testMissingDeprecatedAddJavadocTag(ReportParser parser) throws Exception {
        verify(parser, "AddJavadocTag");
    }

    @RecipeTest
    void testMissingDeprecatedAddAnnotation(ReportParser parser) throws Exception {
        verify(parser, "AddAnnotation");
    }

    @RecipeTest
    void testMissingDeprecatedMultipleDeclarations(ReportParser parser) throws Exception {
        verify(parser, "MultipleDeclarations");
    }

    @RecipeTest
    void testMissingDeprecatedMultiFile(ReportParser parser) throws Exception {
        verify(parser, "MultiFile1", "MultiFile2");
    }
}
