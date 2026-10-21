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
                final String defaultIndentation = getIndentation(visited.getPrefix(),
                        DEFAULT_INDENTATION);
                result = visited.withStatements(splitStatements(statements,
                        getLineBreakAndIndentation(statements, defaultIndentation)));
            }
            return result;
        }

        @Override
        public J.Import visitImport(J.Import importStatement,
                                    ExecutionContext executionContext) {
            final J.Import visited = super.visitImport(importStatement, executionContext);
            J.Import result = visited;
            if (isAtViolationLocation(visited) && !hasLineBreak(visited.getPrefix())) {
                result = visited.withPrefix(visited.getPrefix().withWhitespace(NEW_LINE));
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
                        DEFAULT_INDENTATION);
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
                if (resource.getMarkers().findFirst(OneStatementPerLineMarker.class).isPresent()
                        && !hasLineBreak(resource.getPrefix())) {
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
