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

package org.checkstyle.autofix.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.checkstyle.autofix.parser.ReportParser;
import org.junit.jupiter.api.Test;

public class AnnotationLocationTest extends AbstractRecipeTestSupport {

    public AnnotationLocationTest() {
    }

    @Test
    public void checkDisplayName() {
        final AnnotationLocation recipe = new AnnotationLocation();
        assertEquals("Annotation location", recipe.getDisplayName());
    }

    @Test
    public void checkDescription() {
        final AnnotationLocation recipe = new AnnotationLocation();
        assertEquals("Checks whether annotation is on a separate line.", recipe.getDescription());
    }

    @Override
    protected String getSubpackage() {
        return "annotationlocation";
    }

    @RecipeTest
    void testBasicClassAnnotation(ReportParser parser) throws Exception {
        verify(parser, "BasicClassAnnotation");
    }

    @RecipeTest
    void testMethodAnnotation(ReportParser parser) throws Exception {
        verify(parser, "MethodAnnotation");
    }

    @RecipeTest
    void testVariableAnnotation(ReportParser parser) throws Exception {
        verify(parser, "VariableAnnotation");
    }

    @RecipeTest
    void testComplexAnnotationLocation(ReportParser parser) throws Exception {
        verify(parser, "ComplexAnnotationLocation");
    }

    @RecipeTest
    void testIntegrationWithAnnotationOnSameLine(ReportParser parser) throws Exception {
        verify(parser, "IntegrationWithAnnotationOnSameLine");
    }

}
