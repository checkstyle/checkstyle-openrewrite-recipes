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

import org.checkstyle.autofix.parser.ReportParser;

public class FinalLocalVariableTest extends AbstractRecipeTestSupport {

    @Override
    protected String getSubpackage() {
        return "finallocalvariable";
    }

    @RecipeTest
    void singleLocalTest(ReportParser parser) throws Exception {
        verify(parser, "SingleLocalTest");
    }

    @RecipeTest
    void classFieldTest(ReportParser parser) throws Exception {
        verify(parser, "ClassFieldTest");
    }

    @RecipeTest
    void edgeCaseTest(ReportParser parser) throws Exception {
        verify(parser, "EdgeCaseTest");
    }

    @RecipeTest
    void enhancedForLoop(ReportParser parser) throws Exception {
        verify(parser, "EnhancedForLoop");
    }

    @RecipeTest
    void annotationDeclaration(ReportParser parser) throws Exception {
        verify(parser, "AnnotationDeclaration");
    }

    @RecipeTest
    void multiple(ReportParser parser) throws Exception {
        verify(parser, "MultipleVariable");
    }

    @RecipeTest
    void localVariable(ReportParser parser) throws Exception {
        verify(parser, "LocalVariableOne");
    }

    @RecipeTest
    void localVariableTwo(ReportParser parser) throws Exception {
        verify(parser, "LocalVariableTwo");
    }

    @RecipeTest
    void localVariableThree(ReportParser parser) throws Exception {
        verify(parser, "LocalVariableThree");
    }

    @RecipeTest
    void localVariableFour(ReportParser parser) throws Exception {
        verify(parser, "LocalVariableFour");
    }

    @RecipeTest
    void localVariableFive(ReportParser parser) throws Exception {
        verify(parser, "LocalVariableFive");
    }

    @RecipeTest
    void localVariableCheckRecord(ReportParser parser) throws Exception {
        verify(parser, "LocalVariableCheckRecord");
    }

    @RecipeTest
    void finalLocalVariable2One(ReportParser parser) throws Exception {
        verify(parser, "FinalLocalVariable2One");
    }

    @RecipeTest
    void finalLocalForLoop(ReportParser parser) throws Exception {
        verify(parser, "VariableEnhancedForLoopVariable");
    }

    @RecipeTest
    void finalLocalForLoop2(ReportParser parser) throws Exception {
        verify(parser, "VariableEnhancedForLoopVariable2");
    }

    @RecipeTest
    void localVariableAssignedMultipleTimes(ReportParser parser) throws Exception {
        verify(parser, "LocalVariableAssignedMultipleTimes");
    }

    @RecipeTest
    void localVariableCheckSwitchExpressions(ReportParser parser) throws Exception {
        verify(parser, "LocalVariableCheckSwitchExpressions");
    }

    @RecipeTest
    void localVariableCheckSwitchAssignment(ReportParser parser) throws Exception {
        verify(parser, "LocalVariableCheckSwitchAssignment");
    }

}
