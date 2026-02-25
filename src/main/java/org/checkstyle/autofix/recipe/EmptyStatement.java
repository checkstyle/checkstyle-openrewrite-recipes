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
import org.openrewrite.java.tree.Space;
import org.openrewrite.java.tree.Statement;

/**
 * Fixes Checkstyle EmptyStatement violations by removing empty statements
 * (standalone semicolons) from the code.
 */
public class EmptyStatement extends Recipe {

    private final List<CheckstyleViolation> violations;

    public EmptyStatement(List<CheckstyleViolation> violations) {
        this.violations = violations;
    }

    @Override
    public String getDisplayName() {
        return "EmptyStatement recipe";
    }

    @Override
    public String getDescription() {
        return "Remove empty statements (standalone semicolons) from code.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new EmptyStatementVisitor();
    }

    private final class EmptyStatementVisitor extends JavaIsoVisitor<ExecutionContext> {

        @Override
        public J.Block visitBlock(J.Block block, ExecutionContext executionContext) {
            final J.Block result = super.visitBlock(block, executionContext);
            return result.withStatements(filterStatements(result.getStatements()));
        }

        @Override
        public J.If visitIf(J.If iff, ExecutionContext executionContext) {
            final J.If result = super.visitIf(iff, executionContext);
            return result.withThenPart(removeEmptyStatement(result.getThenPart()));
        }

        @Override
        public J.ForLoop visitForLoop(J.ForLoop forLoop,
                ExecutionContext executionContext) {
            final J.ForLoop result = super.visitForLoop(forLoop, executionContext);
            return result.withBody(removeEmptyStatement(result.getBody()));
        }

        @Override
        public J.ForEachLoop visitForEachLoop(
                J.ForEachLoop forEachLoop, ExecutionContext executionContext) {
            final J.ForEachLoop result = super.visitForEachLoop(forEachLoop,
                    executionContext);
            return result.withBody(removeEmptyStatement(result.getBody()));
        }

        @Override
        public J.WhileLoop visitWhileLoop(J.WhileLoop whileLoop,
                ExecutionContext executionContext) {
            final J.WhileLoop result = super.visitWhileLoop(whileLoop,
                    executionContext);
            return result.withBody(removeEmptyStatement(result.getBody()));
        }

        @Override
        public J.DoWhileLoop visitDoWhileLoop(
                J.DoWhileLoop doWhileLoop, ExecutionContext executionContext) {
            final J.DoWhileLoop result = super.visitDoWhileLoop(doWhileLoop,
                    executionContext);
            return result.withBody(removeEmptyStatement(result.getBody()));
        }

        @Override
        public J.Case visitCase(J.Case caseStatement,
                                ExecutionContext executionContext) {

            final J.Case visited = super.visitCase(caseStatement, executionContext);
            return visited.withStatements(filterStatements(visited.getStatements()));
        }

        private List<Statement> filterStatements(List<Statement> statements) {
            final List<Statement> filteredStatements = new ArrayList<>();
            for (Statement statement : statements) {
                if (statement instanceof J.Empty
                        && isAtViolationLocation((J.Empty) statement)) {
                    continue;
                }
                filteredStatements.add(statement);
            }
            return filteredStatements;
        }

        private Statement removeEmptyStatement(Statement statement) {
            Statement result = statement;

            if (statement instanceof J.Empty
                    && isAtViolationLocation((J.Empty) statement)) {
                result = new J.Block(
                        org.openrewrite.Tree.randomId(),
                        Space.SINGLE_SPACE,
                        org.openrewrite.marker.Markers.EMPTY,
                        new org.openrewrite.java.tree.JRightPadded<>(
                                false,
                                Space.EMPTY,
                                org.openrewrite.marker.Markers.EMPTY
                        ),
                        List.of(),
                        Space.format("\n        ")
                );
            }

            return result;
        }

        private boolean isAtViolationLocation(J.Empty empty) {

            final J.CompilationUnit compilationUnit =
                    getCursor().firstEnclosing(J.CompilationUnit.class);

            final int line =
                    PositionHelper.computeLinePosition(compilationUnit, empty, getCursor());

            final int column =
                    PositionHelper.computeColumnPosition(compilationUnit, empty, getCursor());
            boolean found = false;

            for (CheckstyleViolation violation : violations) {
                if (matchesViolation(violation, compilationUnit, line, column)) {
                    found = true;
                    break;
                }
            }

            return found;
        }

        private boolean matchesViolation(CheckstyleViolation violation,
                                         J.CompilationUnit compilationUnit,
                                         int line,
                                         int column) {

            return violation.getFilePath().endsWith(compilationUnit.getSourcePath())
                    && violation.getLine() == line
                    && violation.getColumn() == column;
        }

    }
}
