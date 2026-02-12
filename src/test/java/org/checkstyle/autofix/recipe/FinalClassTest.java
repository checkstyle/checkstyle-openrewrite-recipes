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

public class FinalClassTest extends AbstractRecipeTestSupport {

    @Override
    protected String getSubpackage() {
        return "finalclass";
    }

    @RecipeTest
    void privateConstructor(ReportParser parser) throws Exception {
        verify(parser, "PrivateConstructor");
    }

    @RecipeTest
    void innerClass(ReportParser parser) throws Exception {
        verify(parser, "InnerClass");
    }

    @RecipeTest
    void staticInnerClass(ReportParser parser) throws Exception {
        verify(parser, "StaticInnerClass");
    }

    @RecipeTest
    void interfaceInnerClass(ReportParser parser) throws Exception {
        verify(parser, "InterfaceInnerClass");
    }

    @RecipeTest
    void enumInnerClass(ReportParser parser) throws Exception {
        verify(parser, "EnumInnerClass");
    }

    @RecipeTest
    void annotationInnerClass(ReportParser parser) throws Exception {
        verify(parser, "AnnotationInnerClass");
    }

    @RecipeTest
    void publicConstructor(ReportParser parser) throws Exception {
        verify(parser, "PublicConstructor");
    }

    @RecipeTest
    void noModifierClass(ReportParser parser) throws Exception {
        verify(parser, "NoModifierClass");
    }

    @RecipeTest
    void privateConstructorWithMethod(ReportParser parser) throws Exception {
        verify(parser, "PrivateConstructorWithMethod");
    }

    @Test
    void metadataMethodsShouldReturnCorrectValues() {
        final FinalClass recipe = new FinalClass();
        assertEquals("FinalClass recipe", recipe.getDisplayName());
        assertEquals(
                "Add 'final' modifier to classes that have only private constructors.",
                recipe.getDescription());
    }

    @RecipeTest
    void protectedInnerClass(ReportParser parser) throws Exception {
        verify(parser, "ProtectedInnerClass");
    }

    @RecipeTest
    void modifierOrder(ReportParser parser) throws Exception {
        verify(parser, "ModifierOrder");
    }

}
