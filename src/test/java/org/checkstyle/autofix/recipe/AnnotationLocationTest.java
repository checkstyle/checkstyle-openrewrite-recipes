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

public class AnnotationLocationTest extends AbstractRecipeTestSupport {

    @Override
    protected String getSubpackage() {
        return "annotationlocation";
    }

    @Test
    public void checkDisplayName() {
        final AnnotationLocation recipe =
                new AnnotationLocation(java.util.Collections.emptyList());
        final String expectedDisplayName = "AnnotationLocation recipe";

        assertEquals(expectedDisplayName, recipe.getDisplayName(), "Invalid display name");
    }

    @Test
    public void checkDescription() {
        final AnnotationLocation recipe =
                new AnnotationLocation(java.util.Collections.emptyList());
        final String expectedDescription =
                "Move annotations to their own line with proper indentation.";

        assertEquals(expectedDescription, recipe.getDescription(), "Invalid description");
    }

    @RecipeTest
    void sameLineParameterizedAnnotation(ReportParser parser) throws Exception {
        verify(parser, "SameLineParameterizedAnnotation");
    }

    @RecipeTest
    void sameLineMultipleAnnotations(ReportParser parser) throws Exception {
        verify(parser, "SameLineMultipleAnnotations");
    }

    @RecipeTest
    void correctAnnotationLocation(ReportParser parser) throws Exception {
        verify(parser, "CorrectAnnotationLocation");
    }

    @RecipeTest
    void methodAnnotation(ReportParser parser) throws Exception {
        verify(parser, "MethodAnnotation");
    }

    @RecipeTest
    void fieldAnnotation(ReportParser parser) throws Exception {
        verify(parser, "FieldAnnotation");
    }

    @RecipeTest
    void classAnnotation(ReportParser parser) throws Exception {
        verify(parser, "ClassAnnotation");
    }
}
