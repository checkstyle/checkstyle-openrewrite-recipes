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

public class RequireThisTest extends AbstractRecipeTestSupport {

    public RequireThisTest() {
    }

    @Override
    protected String getSubpackage() {
        return "requirethis";
    }

    @Test
    public void checkDescription() {
        final RequireThis recipe = new RequireThis();

        final String expectedDescription =
                "Adds 'this.' prefix to references of instance variables and methods"
                + " that require explicit 'this.' qualification.";

        assertWithMessage("Invalid description")
                .that(recipe.getDescription())
                .isEqualTo(expectedDescription);
    }

    @Test
    public void checkDisplayName() {
        final RequireThis recipe = new RequireThis();

        final String expectedDisplayName =
                "RequireThis recipe";

        assertWithMessage("Invalid display name")
                .that(recipe.getDisplayName())
                .isEqualTo(expectedDisplayName);
    }

    @RecipeTest
    void defaultOverlapping(ReportParser parser) throws Exception {
        verify(parser, "DefaultOverlapping");
    }

    @RecipeTest
    void allFields(ReportParser parser) throws Exception {
        verify(parser, "AllFields");
    }

    @RecipeTest
    void allMethods(ReportParser parser) throws Exception {
        verify(parser, "AllMethods");
    }

    @RecipeTest
    void fieldsAndMethods(ReportParser parser) throws Exception {
        verify(parser, "FieldsAndMethods");
    }

    @RecipeTest
    void innerClass(ReportParser parser) throws Exception {
        verify(parser, "InnerClass");
    }

    @RecipeTest
    void innerClassInheritance(ReportParser parser) throws Exception {
        verify(parser, "InnerClassInheritance");
    }

    @RecipeTest
    void anonymousClass(ReportParser parser) throws Exception {
        verify(parser, "AnonymousClass");
    }

    @RecipeTest
    void anonymousClassOuterField(ReportParser parser) throws Exception {
        verify(parser, "AnonymousClassOuterField");
    }

    @RecipeTest
    void anonymousInAnonymous(ReportParser parser) throws Exception {
        verify(parser, "AnonymousInAnonymous");
    }

    @RecipeTest
    void nestedClasses(ReportParser parser) throws Exception {
        verify(parser, "NestedClasses");
    }

    @RecipeTest
    void shadowedLocal(ReportParser parser) throws Exception {
        verify(parser, "ShadowedLocal");
    }

    @RecipeTest
    void constantVariable(ReportParser parser) throws Exception {
        verify(parser, "ConstantVariable");
    }

    @RecipeTest
    void kafka(ReportParser parser) throws Exception {
        verify(parser, "Kafka");
    }

    @RecipeTest
    void superclassMethodInvocation(ReportParser parser) throws Exception {
        verify(parser, "SuperclassMethodInvocation");
    }

}
