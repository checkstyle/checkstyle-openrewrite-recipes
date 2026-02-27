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

import java.util.ArrayList;
import java.util.List;

import org.checkstyle.autofix.PositionHelper;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.Statement;

/**
 * Fixes Checkstyle UnusedLocalVariable violations by removing local variable declarations
 * that are never used.
 */
public class UnusedLocalVariable extends Recipe {

    private final List<CheckstyleViolation> violations;

    public UnusedLocalVariable(List<CheckstyleViolation> violations) {
        this.violations = violations;
    }

    @Override
    public String getDisplayName() {
        return "UnusedLocalVariable recipe";
    }

    @Override
    public String getDescription() {
        return "Removes local variable declarations that are never used.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new RemoveUnusedVisitor();
    }

    private final class RemoveUnusedVisitor extends JavaIsoVisitor<ExecutionContext> {

        private static final String QUOTE = "'";

        private String sourcePath;
        private J.CompilationUnit currentCompilationUnit;

        @Override
        public J.CompilationUnit visitCompilationUnit(J.CompilationUnit cu,
                                                      ExecutionContext executionContext) {
            this.sourcePath = cu.getSourcePath().toString();
            this.currentCompilationUnit = cu;
            return super.visitCompilationUnit(cu, executionContext);
        }

        @Override
        public J.Block visitBlock(J.Block block, ExecutionContext executionContext) {
            final J.Block visited = super.visitBlock(block, executionContext);

            final List<Statement> newStatements = new ArrayList<>();

            for (Statement stmt : visited.getStatements()) {
                if (stmt instanceof J.VariableDeclarations varDecl) {
                    handleVariableDeclaration(varDecl, newStatements);
                }
                else {
                    newStatements.add(stmt);
                }
            }

            return visited.withStatements(newStatements);
        }

        private void handleVariableDeclaration(J.VariableDeclarations varDecl,
                                               List<Statement> newStatements) {
            final List<J.VariableDeclarations.NamedVariable> remaining = new ArrayList<>();
            for (J.VariableDeclarations.NamedVariable variable : varDecl.getVariables()) {
                if (!isAtViolationLocation(variable)) {
                    remaining.add(variable);
                }
            }
            if (!remaining.isEmpty()) {
                newStatements.add(varDecl.withVariables(remaining));
            }
        }

        private boolean isAtViolationLocation(J.VariableDeclarations.NamedVariable variable) {
            final int line = PositionHelper
                    .computeLinePosition(currentCompilationUnit, variable, getCursor());

            return violations.removeIf(violation -> {
                return violation.getLine() == line
                        && violation.getFilePath().toString().endsWith(sourcePath)
                        && violation.getMessage()
                        .contains(QUOTE + variable.getSimpleName() + QUOTE);
            });
        }
    }
}
