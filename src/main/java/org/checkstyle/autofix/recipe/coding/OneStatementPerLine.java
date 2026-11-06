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

package org.checkstyle.autofix.recipe.coding;

import java.util.ArrayList;
import java.util.List;

import org.checkstyle.autofix.marker.checks.OneStatementPerLineMarker;
import org.checkstyle.autofix.parser.CheckConfiguration;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.Space;
import org.openrewrite.java.tree.Statement;

/**
 * Places statements reported by Checkstyle on separate lines.
 */
public class OneStatementPerLine extends Recipe {

    private static final String TREAT_TRY_RESOURCES_AS_STATEMENT =
            "treatTryResourcesAsStatement";
    private static final String DEFAULT_INDENTATION = "    ";
    private static final String RESOURCE_INDENTATION = "     ";
    private static final String NEW_LINE = "\n";

    private final boolean treatTryResourcesAsStatement;

    public OneStatementPerLine(CheckConfiguration config) {
        treatTryResourcesAsStatement = Boolean.parseBoolean(
                config.getPropertyOrDefault(TREAT_TRY_RESOURCES_AS_STATEMENT, "false"));
    }

    @Override
    public String getDisplayName() {
        return "OneStatementPerLine recipe";
    }

    @Override
    public String getDescription() {
        return "Place each Java statement on its own line.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new OneStatementPerLineVisitor();
    }

    private final class OneStatementPerLineVisitor extends JavaIsoVisitor<ExecutionContext> {

        private OneStatementPerLineVisitor() {
        }

        @Override
        public J.Block visitBlock(J.Block block, ExecutionContext executionContext) {
            final J.Block visited = super.visitBlock(block, executionContext);
            J.Block result = visited;
            if (isAtViolationLocation(visited)) {
                final List<Statement> statements = visited.getStatements();
                final String defaultIndentation = getParentIndentation(visited);
                final String statementIndentation = getStatementIndentation(visited,
                        defaultIndentation);
                result = visited.withStatements(splitStatements(statements,
                        getLineBreakAndIndentation(statements, statementIndentation)));
                if (!hasLineBreak(visited.getPrefix())) {
                    final List<Statement> formattedStatements = new ArrayList<>(
                            result.getStatements());
                    final Statement first = formattedStatements.get(0);
                    formattedStatements.set(0, first.withPrefix(first.getPrefix()
                            .withWhitespace(NEW_LINE + statementIndentation)));
                    result = result.withStatements(splitStatements(formattedStatements,
                            NEW_LINE + statementIndentation));
                    result = result.withEnd(result.getEnd().withWhitespace(
                            NEW_LINE + defaultIndentation));
                }
            }
            return result;
        }

        @Override
        public J.CompilationUnit visitCompilationUnit(J.CompilationUnit compilationUnit,
                                                       ExecutionContext executionContext) {
            final J.CompilationUnit visited = super.visitCompilationUnit(
                    compilationUnit, executionContext);
            J.CompilationUnit result = visited;
            if (visited.getImports().stream().anyMatch(this::isAtViolationLocation)) {
                final List<J.Import> imports = new ArrayList<>(visited.getImports());
                for (int index = 1; index < imports.size(); index++) {
                    final J.Import importStatement = imports.get(index);
                    if (!hasLineBreak(importStatement.getPrefix())) {
                        imports.set(index, importStatement.withPrefix(
                                importStatement.getPrefix().withWhitespace(
                                        NEW_LINE + getIndentation(importStatement.getPrefix(),
                                                ""))));
                    }
                }
                result = visited.withImports(imports);
            }
            return result;
        }

        @Override
        public J.Try visitTry(J.Try tryStatement, ExecutionContext executionContext) {
            final J.Try visited = super.visitTry(tryStatement, executionContext);
            J.Try result = visited;
            if (treatTryResourcesAsStatement) {
                final List<J.Try.Resource> resources = visited.getResources();
                final String defaultIndentation = getIndentation(visited.getPrefix(),
                        DEFAULT_INDENTATION) + RESOURCE_INDENTATION;
                result = visited.withResources(splitResources(resources,
                        getLineBreakAndIndentation(resources, defaultIndentation)));
            }
            return result;
        }

        private List<Statement> splitStatements(List<Statement> statements,
                                                String lineBreakAndIndentation) {
            final List<Statement> result = new ArrayList<>(statements);
            for (int index = 1; index < result.size(); index++) {
                final Statement statement = result.get(index);
                if (!hasLineBreak(statement.getPrefix())) {
                    result.set(index, statement.withPrefix(statement.getPrefix()
                            .withWhitespace(lineBreakAndIndentation)));
                }
            }
            return result;
        }

        private List<J.Try.Resource> splitResources(List<J.Try.Resource> resources,
                                                    String lineBreakAndIndentation) {
            final List<J.Try.Resource> result = new ArrayList<>(resources);
            for (int index = 1; index < result.size(); index++) {
                final J.Try.Resource resource = result.get(index);
                if (!hasLineBreak(resource.getPrefix())) {
                    result.set(index, resource.withPrefix(resource.getPrefix()
                            .withWhitespace(lineBreakAndIndentation)));
                }
            }
            return result;
        }

        private String getIndentation(Space prefix, String defaultIndentation) {
            final String whitespace = prefix.getWhitespace();
            final int lastNewLine = whitespace.lastIndexOf(NEW_LINE);
            final String result;
            if (lastNewLine >= 0) {
                result = whitespace.substring(lastNewLine + 1);
            }
            else {
                result = defaultIndentation;
            }
            return result;
        }

        private String getParentIndentation(J.Block block) {
            final Object parent = getCursor().getParentTreeCursor().getValue();
            final String result;
            if (parent instanceof final J parentTree) {
                result = getIndentation(parentTree.getPrefix(), "");
            }
            else {
                result = "";
            }
            return result;
        }

        private String getStatementIndentation(J.Block block, String parentIndentation) {
            final String result;
            if (!block.getStatements().isEmpty()
                    && hasLineBreak(block.getStatements().get(0).getPrefix())) {
                result = getIndentation(block.getStatements().get(0).getPrefix(),
                        parentIndentation + DEFAULT_INDENTATION);
            }
            else {
                result = parentIndentation + DEFAULT_INDENTATION;
            }
            return result;
        }

        private String getLineBreakAndIndentation(List<? extends J> nodes,
                                                  String defaultIndentation) {
            final String whitespace = nodes.get(0).getPrefix().getWhitespace();
            final int lastNewLine = whitespace.lastIndexOf(NEW_LINE);
            final String indentation;
            if (lastNewLine >= 0) {
                indentation = whitespace.substring(lastNewLine + 1);
            }
            else {
                indentation = defaultIndentation;
            }
            return NEW_LINE + indentation;
        }

        private boolean hasLineBreak(Space prefix) {
            return prefix.getWhitespace().contains(NEW_LINE)
                    || prefix.getComments().stream().anyMatch(comment -> {
                        return comment.getSuffix().contains(NEW_LINE);
                    });
        }

        private boolean isAtViolationLocation(J.Block block) {
            return block.getMarkers().findFirst(OneStatementPerLineMarker.class).isPresent();
        }

        private boolean isAtViolationLocation(J.Import importStatement) {
            return importStatement.getMarkers()
                    .findFirst(OneStatementPerLineMarker.class).isPresent();
        }

    }

}
