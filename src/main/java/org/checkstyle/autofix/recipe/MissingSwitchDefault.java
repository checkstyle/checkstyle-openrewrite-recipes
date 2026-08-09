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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.checkstyle.autofix.marker.checks.MissingSwitchDefaultMarker;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.Tree;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JContainer;
import org.openrewrite.java.tree.JRightPadded;
import org.openrewrite.java.tree.Space;
import org.openrewrite.java.tree.Statement;
import org.openrewrite.marker.Markers;

public class MissingSwitchDefault extends Recipe {

    private static final String NEW_LINE = "\n";
    private static final String SPACE_STRING = " ";
    private static final String DEFAULT_STRING = "default";

    private static final Set<Class<?>> TERMINAL_STATEMENTS =
            new HashSet<>(Arrays.asList(
                    J.Break.class, J.Return.class, J.Throw.class, J.Continue.class
            ));

    public MissingSwitchDefault() {
    }

    @Override
    public String getDisplayName() {
        return "MissingSwitchDefault recipe";
    }

    @Override
    public String getDescription() {
        return "Adds a default case to switch statements if it is missing.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new MissingSwitchDefaultVisitor();
    }

    private final class MissingSwitchDefaultVisitor extends JavaIsoVisitor<ExecutionContext> {

        private MissingSwitchDefaultVisitor() {
        }

        @Override
        public J.Switch visitSwitch(J.Switch switchStmt, ExecutionContext executionContext) {
            J.Switch visited = super.visitSwitch(switchStmt, executionContext);

            if (isViolationMarked(visited)) {
                final J.Block casesBlock = visited.getCases();
                List<Statement> cases = new ArrayList<>(casesBlock.getStatements());

                final boolean isLambdaStyle = isLambdaStyleSwitch(cases);

                if (!isLambdaStyle) {
                    cases = tryAddBreakToLastCase(cases);
                }

                final J.Identifier defaultLabel = new J.Identifier(
                        Tree.randomId(),
                        Space.EMPTY,
                        Markers.EMPTY,
                        null,
                        DEFAULT_STRING,
                        null,
                        null);

                final JRightPadded<J> paddedLabel;
                final JContainer<Statement> statements;
                final J.Case.Type caseType;

                if (isLambdaStyle) {
                    paddedLabel = JRightPadded.build((J) defaultLabel)
                            .withAfter(Space.build(SPACE_STRING, Collections.emptyList()));

                    final J.Block emptyBlock = J.Block.createEmptyBlock()
                            .withPrefix(Space.build(SPACE_STRING, Collections.emptyList()));
                    statements = JContainer.build(
                            Space.EMPTY,
                            Collections.singletonList(JRightPadded.build((Statement) emptyBlock)),
                            Markers.EMPTY);
                    caseType = J.Case.Type.Rule;
                }
                else {
                    paddedLabel = JRightPadded.build((J) defaultLabel);

                    final J.Break breakStmt = new J.Break(
                            Tree.randomId(),
                            Space.build(NEW_LINE, Collections.emptyList()),
                            Markers.EMPTY,
                            null);
                    statements = JContainer.build(
                            Space.EMPTY,
                            Collections.singletonList(JRightPadded.build((Statement) breakStmt)),
                            Markers.EMPTY);
                    caseType = J.Case.Type.Statement;
                }

                cases.add(new J.Case(
                        Tree.randomId(),
                        Space.build(NEW_LINE, Collections.emptyList()),
                        Markers.EMPTY,
                        caseType,
                        JContainer.build(Space.EMPTY, Collections.singletonList(paddedLabel),
                                Markers.EMPTY),
                        statements,
                        null,
                        null));

                visited = visited.withCases(casesBlock.withStatements(cases));
                visited = autoFormat(visited, executionContext);

                final Markers newMarkers = visited.getMarkers()
                        .removeByType(MissingSwitchDefaultMarker.class);
                visited = Optional.of(newMarkers).map(visited::withMarkers).get();
            }

            return visited;
        }

        private boolean isViolationMarked(J.Switch switchStmt) {
            return switchStmt.getMarkers().findFirst(MissingSwitchDefaultMarker.class).isPresent();
        }

        private List<Statement> tryAddBreakToLastCase(List<Statement> cases) {
            List<Statement> result = cases;
            if (!cases.isEmpty()) {
                final J.Case lastCase = (J.Case) cases.getLast();
                final List<Statement> lastCaseStatements =
                        new ArrayList<>(lastCase.getStatements());

                boolean needsBreak = true;
                if (!lastCaseStatements.isEmpty()) {
                    final Statement last = lastCaseStatements.getLast();
                    if (isTerminalStatement(last)) {
                        needsBreak = false;
                    }
                }

                if (needsBreak) {
                    final List<Statement> updatedCases = new ArrayList<>(cases);
                    final J.Break prevBreak = new J.Break(
                            Tree.randomId(),
                            Space.build(NEW_LINE, Collections.emptyList()),
                            Markers.EMPTY,
                            null);
                    lastCaseStatements.add(prevBreak);
                    final J.Case updatedLastCase =
                            lastCase.withStatements(lastCaseStatements);
                    updatedCases.set(updatedCases.size() - 1, updatedLastCase);
                    result = updatedCases;
                }
            }
            return result;
        }

        private boolean isLambdaStyleSwitch(List<Statement> cases) {
            boolean result = false;
            if (!cases.isEmpty()) {
                result = ((J.Case) cases.getFirst()).getType() == J.Case.Type.Rule;
            }
            return result;
        }

        private boolean isTerminalStatement(Statement statement) {
            return TERMINAL_STATEMENTS.contains(statement.getClass());
        }

    }

}
