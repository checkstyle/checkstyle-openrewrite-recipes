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

public class AnnotationOnSameLineTest extends AbstractRecipeTestSupport {

    @Test
    void metadata() {
        final AnnotationOnSameLine recipe = new AnnotationOnSameLine();
        assertEquals("Annotation on same line",
                recipe.getDisplayName());
        assertEquals("Checks whether annotation is on the same line "
                + "with its target element.",
                recipe.getDescription());
    }

    @Override
    protected String getSubpackage() {
        return "annotationonsameline";
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
    void testFieldAnnotation(ReportParser parser) throws Exception {
        verify(parser, "FieldAnnotation");
    }

    @RecipeTest
    void testEdgeCases(ReportParser parser) throws Exception {
        verify(parser, "EdgeCases");
    }

    @RecipeTest
    void testMultiAnnotation(ReportParser parser) throws Exception {
        verify(parser, "MultiAnnotation");
    }

    @RecipeTest
    void testClassNoModifiers(ReportParser parser) throws Exception {
        verify(parser, "ClassNoModifiers");
    }

    @RecipeTest
    void testMethodNoModifier(ReportParser parser) throws Exception {
        verify(parser, "MethodNoModifier");
    }

    @RecipeTest
    void testVariableWithModifier(ReportParser parser) throws Exception {
        verify(parser, "VariableWithModifier");
    }

    @RecipeTest
    void testNoChange(ReportParser parser) throws Exception {
        verify(parser, "NoChange", "EdgeCases");
    }

    @RecipeTest
    void testCommentNewline(ReportParser parser) throws Exception {
        verify(parser, "CommentNewline");
    }

    @RecipeTest
    void testMultiAnnotationSparse(ReportParser parser) throws Exception {
        verify(parser, "MultiAnnotationSparse");
    }

    @RecipeTest
    void testTripleAnnotation(ReportParser parser) throws Exception {
        verify(parser, "TripleAnnotation");
    }

    @RecipeTest
    void testFieldNoModifiers(ReportParser parser) throws Exception {
        verify(parser, "FieldNoModifiers");
    }

    @RecipeTest
    void testVariableWithVar(ReportParser parser) throws Exception {
        verify(parser, "VariableWithVar");
    }

    @RecipeTest
    void testNestedVariable(ReportParser parser) throws Exception {
        verify(parser, "NestedVariable");
    }

    @RecipeTest
    void testNoChangeMultiSpacing(ReportParser parser) throws Exception {
        verify(parser, "NoChangeMultiSpacing", "FieldNoModifiers");
    }

    @RecipeTest
    void testSingleLineComment(ReportParser parser) throws Exception {
        verify(parser, "SingleLineComment");
    }
}
