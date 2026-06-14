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

import java.util.List;

import org.checkstyle.autofix.PositionHelper;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;

/**
 * Fixes Checkstyle AvoidNoArgumentSuperConstructorCall violations
 * by removing unnecessary no-argument super constructor calls.
 */
public class AvoidNoArgumentSuperConstructorCall extends Recipe {

    private final List<CheckstyleViolation> violations;

    public AvoidNoArgumentSuperConstructorCall(List<CheckstyleViolation> violations) {
        this.violations = violations;
    }

    @Override
    public String getDisplayName() {
        return "AvoidNoArgumentSuperConstructorCall recipe";
    }

    @Override
    public String getDescription() {
        return "Removes no-argument super constructor calls.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new AvoidNoArgumentSuperConstructorCallVisitor();
    }

    private final class AvoidNoArgumentSuperConstructorCallVisitor
            extends JavaIsoVisitor<ExecutionContext> {

        private J.CompilationUnit compilationUnit;

        @Override
        public J.CompilationUnit visitCompilationUnit(J.CompilationUnit compUnit,
                                                      ExecutionContext executionContext) {
            compilationUnit = compUnit;
            return super.visitCompilationUnit(compUnit, executionContext);
        }

        @Override
        public J.MethodInvocation visitMethodInvocation(J.MethodInvocation method,
                                                        ExecutionContext executionContext) {
            J.MethodInvocation result = method;

            if ("super".equals(method.getSimpleName())) {
                final int line = PositionHelper.computeLinePosition(
                        compilationUnit, method, getCursor());

                for (CheckstyleViolation violation : violations) {
                    if (violation.getLine() == line
                            & violation.getFilePath().endsWith(
                                    compilationUnit.getSourcePath().toString())) {
                        result = null;
                        break;
                    }
                }
            }

            return result;
        }
    }
}
