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
import java.util.stream.Collectors;

import org.checkstyle.autofix.CheckFullName;
import org.checkstyle.autofix.marker.CheckstyleViolationMarker;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.Tree;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JRightPadded;
import org.openrewrite.java.tree.Space;
import org.openrewrite.java.tree.Statement;
import org.openrewrite.marker.Markers;

/**
 * Fixes Checkstyle EmptyStatement violations by removing empty statements
 * (standalone semicolons) from the code.
 */
public class EmptyStatement extends Recipe {

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
            return result.withThenPart(removeEmptyStatement(
                    result.getThenPart(), executionContext
            ));
        }

        @Override
        public J.ForLoop visitForLoop(J.ForLoop forLoop,
                                      ExecutionContext executionContext) {
            final J.ForLoop result = super.visitForLoop(forLoop, executionContext);
            return result.withBody(removeEmptyStatement(result.getBody(), executionContext));
        }

        @Override
        public J.ForEachLoop visitForEachLoop(
                J.ForEachLoop forEachLoop, ExecutionContext executionContext) {
            final J.ForEachLoop result = super.visitForEachLoop(forEachLoop, executionContext);
            return result.withBody(removeEmptyStatement(result.getBody(), executionContext));
        }

        @Override
        public J.WhileLoop visitWhileLoop(J.WhileLoop whileLoop,
                                          ExecutionContext executionContext) {
            final J.WhileLoop result = super.visitWhileLoop(whileLoop, executionContext);
            return result.withBody(removeEmptyStatement(result.getBody(), executionContext));
        }

        @Override
        public J.DoWhileLoop visitDoWhileLoop(
                J.DoWhileLoop doWhileLoop, ExecutionContext executionContext) {
            final J.DoWhileLoop result = super.visitDoWhileLoop(doWhileLoop,
                    executionContext);
            return result.withBody(removeEmptyStatement(result.getBody(), executionContext));
        }

        @Override
        public J.Case visitCase(J.Case caseStatement,
                                ExecutionContext executionContext) {
            final J.Case visited = super.visitCase(caseStatement, executionContext);
            return visited.withStatements(filterStatements(visited.getStatements()));
        }

        private List<Statement> filterStatements(List<Statement> statements) {
            return statements.stream()
                    .filter(statement -> {
                        return !(statement instanceof J.Empty
                                && isAtViolationLocation((J.Empty) statement));
                    })
                    .collect(Collectors.toList());
        }

        private Statement removeEmptyStatement(Statement statement,
                                               ExecutionContext executionContext) {
            Statement result = statement;

            if (statement instanceof J.Empty
                    && isAtViolationLocation((J.Empty) statement)) {
                final J.Block emptyBlock = new J.Block(
                        Tree.randomId(),
                        Space.EMPTY,
                        Markers.EMPTY,
                        new JRightPadded<>(false, Space.EMPTY, Markers.EMPTY),
                        List.of(),
                        Space.EMPTY
                ).withPrefix(statement.getPrefix());
                result = maybeAutoFormat(statement, emptyBlock, executionContext);
            }
            return result;
        }

        private boolean isAtViolationLocation(J.Empty empty) {
            return empty.getMarkers()
                    .findAll(CheckstyleViolationMarker.class)
                    .stream()
                    .anyMatch(marker -> marker.isFor(CheckFullName.EMPTY_STATEMENT));
        }

    }
}
