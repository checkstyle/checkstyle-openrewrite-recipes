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

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.checkstyle.autofix.PositionHelper;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.Tree;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaVisitor;
import org.openrewrite.java.tree.Expression;
import org.openrewrite.java.tree.Flag;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JContainer;
import org.openrewrite.java.tree.JLeftPadded;
import org.openrewrite.java.tree.JavaType;
import org.openrewrite.java.tree.JavaType.FullyQualified;
import org.openrewrite.java.tree.Space;
import org.openrewrite.java.tree.Statement;
import org.openrewrite.marker.Markers;

/**
 * Fixes Checkstyle UseEnhancedSwitch violations by converting traditional switch
 * statements using colon syntax to enhanced switch using arrow syntax.
 */
public class UseEnhancedSwitch extends Recipe {

    private final List<CheckstyleViolation> violations;

    public UseEnhancedSwitch(List<CheckstyleViolation> violations) {
        this.violations = violations;
    }

    @Override
    public String getDisplayName() {
        return "UseEnhancedSwitch recipe";
    }

    @Override
    public String getDescription() {
        return "Convert switch statements using colon syntax to enhanced switch "
                + "using arrow syntax.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new UseEnhancedSwitchVisitor();
    }

    private final class UseEnhancedSwitchVisitor extends JavaVisitor<ExecutionContext> {

        private static final String DEFAULT_LABEL = "default";
        private Path sourcePath;
        private J.CompilationUnit compUnit;

        @Override
        public J.CompilationUnit visitCompilationUnit(
                J.CompilationUnit cu, ExecutionContext executionContext) {
            this.sourcePath = cu.getSourcePath();
            this.compUnit = cu;
            return (J.CompilationUnit) super.visitCompilationUnit(cu, executionContext);
        }

        @Override
        public Statement visitSwitch(J.Switch switchNode, ExecutionContext executionContext) {
            final J.Switch visited = (J.Switch) super.visitSwitch(switchNode, executionContext);
            Statement result = visited;

            if (isTraditional(visited.getCases().getStatements())
                    && isAtViolationLocation(visited)) {
                final List<Statement> mergedCases = combineCasesWithSameStatements(
                        visited.getCases().getStatements());
                final boolean isExpr = canConvertToReturnSwitchExpression(
                        mergedCases, isEnumExhaustive(visited.getSelector(), mergedCases));

                final J.Switch updatedSwitch = visited.withCases(
                        convertCases(visited.getCases(), visited.getSelector(), isExpr));

                if (isExpr) {
                    final J.SwitchExpression switchExpr = new J.SwitchExpression(
                            Tree.randomId(),
                            Space.SINGLE_SPACE,
                            Markers.EMPTY,
                            updatedSwitch.getSelector(),
                            updatedSwitch.getCases(),
                            null);
                    result = new J.Return(
                            Tree.randomId(),
                            updatedSwitch.getPrefix(),
                            Markers.EMPTY,
                            switchExpr);
                }
                else {
                    result = updatedSwitch;
                }
            }
            return result;
        }

        private boolean isTraditional(final List<? extends Statement> cases) {
            return !cases.isEmpty() && ((J.Case) cases.get(0)).getType() == J.Case.Type.Statement;
        }

        @Override
        public J.Block visitBlock(J.Block block, ExecutionContext executionContext) {
            final J.Block visited = (J.Block) super.visitBlock(block, executionContext);
            return mergeAssignmentSwitches(visited);
        }

        private J.Block removeUnreachableCode(final J.Block visited) {
            final List<Statement> stmts = visited.getStatements();
            final List<Statement> newStmts = new ArrayList<>();
            for (final Statement s : stmts) {
                newStmts.add(s);
                if (s instanceof J.Return) {
                    break;
                }
            }
            return visited.withStatements(newStmts);
        }

        private J.Block mergeAssignmentSwitches(final J.Block visited) {
            final List<Statement> stmts = visited.getStatements();
            final List<Statement> merged = new ArrayList<>();

            int index = 0;
            while (index < stmts.size()) {
                int jump = 1;
                if (index < stmts.size() - 1) {
                    final Statement s1 = stmts.get(index);
                    final Statement s2 = stmts.get(index + 1);
                    if (s1 instanceof J.VariableDeclarations vd
                            && vd.getVariables().size() == 1
                            && vd.getVariables().get(0).getInitializer() == null
                            && s2 instanceof J.Switch sw) {
                        final String name = vd.getVariables().get(0)
                                .getName().getSimpleName();
                        final List<Statement> mergedCases =
                                sw.getCases().getStatements();
                        final boolean exhaustive =
                                isEnumExhaustive(sw.getSelector(), mergedCases);
                        if (canConvertToAssignmentSwitchExpression(
                                mergedCases, name, exhaustive)) {
                            final J.VariableDeclarations converted =
                                    convertToAssignmentSwitchExpression(
                                            vd, sw, name);
                            merged.add(converted);
                            jump = 2;
                        }
                    }
                }
                if (jump == 1) {
                    merged.add(stmts.get(index));
                }
                index += jump;
            }
            return removeUnreachableCode(visited.withStatements(merged));
        }

        private J.VariableDeclarations convertToAssignmentSwitchExpression(
                J.VariableDeclarations varDecl, J.Switch switchStmt, String varName) {
            final J.Block newCases = convertAssignmentCases(switchStmt.getCases(), varName);

            final J.SwitchExpression switchExpr = new J.SwitchExpression(
                    Tree.randomId(),
                    Space.SINGLE_SPACE,
                    Markers.EMPTY,
                    switchStmt.getSelector(),
                    newCases,
                    null);

            J.VariableDeclarations.NamedVariable namedVar = varDecl.getVariables().get(0);
            final JLeftPadded<Expression> initializer = JLeftPadded
                    .build((Expression) switchExpr)
                    .withBefore(Space.SINGLE_SPACE);
            namedVar = namedVar.getPadding().withInitializer(initializer);
            final List<J.VariableDeclarations.NamedVariable> variables =
                    new ArrayList<>(varDecl.getVariables());
            variables.set(0, namedVar);
            return varDecl.withVariables(variables);
        }

        private J.Block convertAssignmentCases(final J.Block casesBlock, final String varName) {
            final List<Statement> caseStatements = casesBlock.getStatements();
            final List<Statement> newStatements = new ArrayList<>();
            for (final Statement stmt : caseStatements) {
                final J.Case converted = convertAssignmentCase((J.Case) stmt, varName);
                newStatements.add(converted);
            }
            return casesBlock.withStatements(newStatements);
        }

        private J.Case convertAssignmentCase(J.Case caseStmt, String varName) {
            return convertArrowAssignmentCase(caseStmt, varName);
        }

        private J.Case convertArrowAssignmentCase(final J.Case caseStmt, final String varName) {
            final J body = caseStmt.getBody();
            final J.Case result;
            if (body instanceof J.Block block) {
                final List<Statement> adjusted =
                        adjustForAssignment(block.getStatements(), varName);
                result = caseStmt.withBody(block.withStatements(adjusted));
            }
            else {
                final J newBody;
                if (body instanceof J.Assignment asgn) {
                    newBody = asgn.getAssignment();
                }
                else {
                    newBody = body;
                }
                final J prefixed = newBody;
                result = caseStmt.withBody(prefixed);
            }
            return result;
        }

        private List<Statement> adjustForAssignment(
                final List<Statement> statements, final String varName) {
            final List<Statement> newStatements = new ArrayList<>(statements);
            final Statement last = newStatements.getLast();
            final J.Assignment asgn = (J.Assignment) last;
            newStatements.set(newStatements.size() - 1, new J.Yield(
                    Tree.randomId(),
                    asgn.getPrefix(),
                    Markers.EMPTY,
                    false,
                    asgn.getAssignment()));
            return newStatements;
        }

        @Override
        public J.SwitchExpression visitSwitchExpression(
                J.SwitchExpression switchExpression, ExecutionContext executionContext) {
            final J.SwitchExpression visited = (J.SwitchExpression) super.visitSwitchExpression(
                    switchExpression, executionContext);

            J.SwitchExpression result = visited;
            final boolean traditional = isTraditional(visited.getCases().getStatements());
            final boolean violation = isAtViolationLocation(visited);
            if (traditional && violation) {
                result = visited.withCases(
                        convertCases(visited.getCases(), visited.getSelector(), true));
            }

            return result;
        }

        private boolean canConvertToAssignmentSwitchExpression(
                final List<Statement> mergedCases, final String varName,
                final boolean exhaustive) {
            boolean hasDefault = false;
            boolean possible = true;
            for (final Statement stmt : mergedCases) {
                final J.Case caseStmt = (J.Case) stmt;
                if (isDefaultCase(caseStmt)) {
                    hasDefault = true;
                }

                final List<Statement> meaningful = getMeaningfulStatements(caseStmt);
                if (meaningful.isEmpty() || !isValidAssignment(meaningful, varName)) {
                    possible = false;
                    break;
                }
                if (hasDisallowedControlFlow(meaningful)) {
                    possible = false;
                    break;
                }
            }
            return possible && (hasDefault || exhaustive);
        }

        private boolean isValidAssignment(final List<Statement> meaningful,
                                          final String varName) {
            final Statement last = meaningful.getLast();
            return last instanceof J.Throw || last instanceof J.Assignment asgn
                    && asgn.getVariable() instanceof J.Identifier
                    && ((J.Identifier) asgn.getVariable()).getSimpleName().equals(varName);
        }

        private boolean canConvertToReturnSwitchExpression(
                final List<Statement> mergedCases, final boolean exhaustive) {
            boolean hasDefault = false;
            boolean possible = true;
            for (final Statement stmt : mergedCases) {
                final J.Case caseStmt = (J.Case) stmt;
                if (isDefaultCase(caseStmt)) {
                    hasDefault = true;
                }

                final List<Statement> meaningful = getMeaningfulStatements(caseStmt);
                if (meaningful.isEmpty()) {
                    possible = false;
                    break;
                }

                final Statement last = meaningful.getLast();
                if (last instanceof J.Throw) {
                    continue;
                }

                if (!(last instanceof J.Return) || hasDisallowedControlFlow(meaningful)) {
                    possible = false;
                    break;
                }
            }
            return possible && (hasDefault || exhaustive);
        }

        private boolean hasDisallowedControlFlow(final List<Statement> stmts) {
            final ControlFlowVisitor visitor = new ControlFlowVisitor();
            for (int index = 0; index < stmts.size() - 1; index++) {
                visitor.visit(stmts.get(index), null);
            }
            return visitor.hasDisallowedFlow();
        }

        private List<Statement> getMeaningfulStatements(final J.Case caseStmt) {
            final List<Statement> stmts;
            if (caseStmt.getType() == J.Case.Type.Rule) {
                final J body = caseStmt.getBody();
                if (body instanceof J.Block block) {
                    stmts = block.getStatements();
                }
                else {
                    stmts = Collections.singletonList((Statement) body);
                }
            }
            else {
                final List<Statement> caseStmts = caseStmt.getStatements();
                final Statement first = caseStmts.get(0);
                if (first instanceof J.Block block) {
                    stmts = block.getStatements();
                }
                else {
                    stmts = caseStmts;
                }
            }
            return stmts;
        }

        private boolean isDefaultCase(J.Case caseStmt) {
            return caseStmt.getCaseLabels().stream()
                    .anyMatch(label -> {
                        return label instanceof J.Identifier ident
                                && DEFAULT_LABEL.equals(ident.getSimpleName());
                    });
        }

        private J.Block convertCases(
                J.Block casesBlock, J.ControlParentheses<Expression> selector,
                boolean isExpr) {
            final List<Statement> mergedCases = combineCasesWithSameStatements(
                    casesBlock.getStatements());
            final List<Statement> newStatements = new ArrayList<>();

            for (Statement stmt : mergedCases) {
                newStatements.add(convertCase((J.Case) stmt, isExpr));
            }

            final List<Statement> finalStatements;
            if (isExpr && isEnumExhaustive(selector, mergedCases)) {
                finalStatements = removeDefaultCase(newStatements);
            }
            else {
                finalStatements = newStatements;
            }

            return casesBlock.withStatements(finalStatements);
        }

        private boolean isEnumExhaustive(
                final J.ControlParentheses<Expression> selector,
                final List<Statement> mergedCases) {
            boolean result = false;
            final JavaType type = selector.getTree().getType();
            if (type instanceof FullyQualified fq
                    && fq.getKind() == FullyQualified.Kind.Enum) {
                final List<JavaType.Variable> members = fq.getMembers();
                final Set<String> handled = collectHandled(mergedCases);
                boolean exhaustive = true;
                for (final JavaType.Variable member : members) {
                    if (member.hasFlags(Flag.Enum)
                            && !handled.contains(member.getName())) {
                        exhaustive = false;
                        break;
                    }
                }
                result = exhaustive;
            }
            return result;
        }

        private Set<String> collectHandled(
                final List<Statement> mergedCases) {
            final Set<String> handledConstants =
                    new HashSet<>();
            for (final Statement stmt : mergedCases) {
                for (final J label : ((J.Case) stmt).getCaseLabels()) {
                    handledConstants.add(((J.Identifier) label).getSimpleName());
                }
            }
            return handledConstants;
        }

        private List<Statement> removeDefaultCase(final List<Statement> statements) {
            return statements.stream()
                    .filter(stmt -> !isDefaultCase((J.Case) stmt))
                    .toList();
        }

        private List<Statement> combineCasesWithSameStatements(
                final List<Statement> statements) {
            final List<Statement> result = new ArrayList<>();
            final List<J.Case> pendingCases = new ArrayList<>();

            for (final Statement stmt : statements) {
                final J.Case caseStmt = (J.Case) stmt;

                pendingCases.add(caseStmt);
                if (!caseStmt.getStatements().isEmpty()) {
                    result.add(buildMergedCase(pendingCases));
                    pendingCases.clear();
                }
            }

            if (!pendingCases.isEmpty()) {
                result.add(buildMergedCase(pendingCases));
            }
            return result;
        }

        private J.Case buildMergedCase(final List<J.Case> cases) {
            final J.Case last = cases.getLast();
            final List<J> resultLabels;
            if (isDefaultCase(last)) {
                resultLabels = last.getCaseLabels();
            }
            else {
                resultLabels = new ArrayList<>();
                for (final J.Case pending : cases) {
                    resultLabels.addAll(pending.getCaseLabels());
                }
            }
            return last.withCaseLabels(resultLabels);
        }

        private J.Case convertCase(J.Case caseStmt, boolean isExpr) {
            final List<Statement> stmts = caseStmt.getStatements();
            final J.Case result;
            if (stmts.size() == 1 && stmts.get(0) instanceof J.Block block) {
                result = convertBlockCase(caseStmt, block, isExpr);
            }
            else {
                final List<Statement> sorted = removeBreak(stmts);
                if (sorted.size() == 1 && isValidSingleStatementArrow(sorted.get(0), isExpr)) {
                    result = convertToSingleStatementArrow(caseStmt, sorted.get(0), isExpr);
                }
                else {
                    result = convertToBlockArrow(caseStmt, sorted, isExpr);
                }
            }
            return result;
        }

        private J.Case convertBlockCase(final J.Case caseStmt, final J.Block block,
                                        final boolean isExpr) {
            final List<Statement> stmts = block.getStatements();
            final List<Statement> cleaned = removeBreak(stmts);
            final J.Case result;

            if (cleaned.size() == 1 && isValidSingleStatementArrow(cleaned.get(0), isExpr)) {
                result = convertToSingleStatementArrow(caseStmt, cleaned.get(0), isExpr);
            }
            else {
                final J.Case arrowCase = setArrowType(caseStmt);
                if (cleaned.isEmpty()) {
                    result = arrowCase.withBody(J.Block.createEmptyBlock()
                            .withPrefix(Space.SINGLE_SPACE));
                }
                else {
                    final int indentDelta = computeIndentDelta(
                            caseStmt.getPrefix(), block.getPrefix());

                    final J.Block newBlock = block
                            .withPrefix(Space.SINGLE_SPACE)
                            .withStatements(adjustForExpression(cleaned, isExpr));

                    final J.Block resultBlock;
                    if (indentDelta > 0) {
                        resultBlock = (J.Block) new IndentVisitor(-indentDelta)
                                .visit(newBlock, null);
                    }
                    else {
                        resultBlock = newBlock;
                    }
                    result = arrowCase.withBody(resultBlock);
                }
            }
            return result;
        }

        private boolean isValidSingleStatementArrow(
                Statement stmt, boolean isExpr) {
            final J target = getArrowTarget(stmt, isExpr);
            return target instanceof Expression || target instanceof J.Throw;
        }

        private J getArrowTarget(Statement stmt, boolean isExpr) {
            J target = stmt;
            if (stmt instanceof J.Yield yield) {
                target = yield.getValue();
            }
            else if (isExpr && stmt instanceof J.Return ret) {
                target = ret.getExpression();
            }
            return target;
        }

        private Space createBlockEndSpace(final Space casePrefix) {
            final String ws = casePrefix.getWhitespace();
            final Space result;
            if (ws.indexOf('\n') >= 0) {
                result = Space.build(ws, Collections.emptyList());
            }
            else {
                result = Space.EMPTY;
            }
            return result;
        }

        private List<Statement> adjustForExpression(
                List<Statement> statements, boolean isExpr) {
            final List<Statement> newStatements = new ArrayList<>(statements);
            if (isExpr) {
                final Statement lastStmt = newStatements.getLast();

                if (lastStmt instanceof J.Return ret) {
                    newStatements.set(newStatements.size() - 1, new J.Yield(
                            Tree.randomId(),
                            lastStmt.getPrefix(),
                            Markers.EMPTY,
                            false,
                            ret.getExpression()));
                }
            }
            return newStatements;
        }

        private J.Case convertToSingleStatementArrow(
                J.Case caseStmt, Statement stmt, boolean isExpr) {
            return setArrowType(caseStmt).withBody(
                    getArrowTarget(stmt, isExpr).withPrefix(Space.SINGLE_SPACE));
        }

        private J.Case convertToBlockArrow(
                J.Case caseStmt, List<Statement> statements,
                boolean isExpr) {
            final Space endSpace;
            if (statements.isEmpty()) {
                endSpace = Space.EMPTY;
            }
            else {
                endSpace = createBlockEndSpace(caseStmt.getPrefix());
            }
            return setArrowType(caseStmt).withBody(J.Block.createEmptyBlock()
                    .withPrefix(Space.SINGLE_SPACE)
                    .withStatements(adjustForExpression(
                            new ArrayList<>(statements), isExpr))
                    .withEnd(endSpace));
        }

        private J.Case setArrowType(J.Case caseStmt) {
            return caseStmt.withType(J.Case.Type.Rule)
                    .getPadding().withStatements(
                            JContainer.<Statement>empty().withBefore(Space.SINGLE_SPACE));
        }

        private List<Statement> removeBreak(List<Statement> statements) {
            final List<Statement> filtered = new ArrayList<>();
            for (Statement stmt : statements) {
                if (!(stmt instanceof J.Break breakStmt)
                        || breakStmt.getLabel() != null) {
                    filtered.add(stmt);
                }
            }
            return filtered;
        }

        private boolean isAtViolationLocation(final J switchNode) {
            final int line = PositionHelper.computeLinePosition(compUnit, switchNode, getCursor());
            return violations.stream().anyMatch(violation -> {
                return violation.getLine() == line
                        && isSameFile(violation.getFilePath(), sourcePath);
            });
        }

        private boolean isSameFile(final Path path1, final Path path2) {
            return path1.equals(path2);
        }

        private static int computeIndentDelta(
                final Space casePrefix, final Space blockPrefix) {
            final int caseIndent = getIndentLevel(casePrefix);
            final int blockIndent = getIndentLevel(blockPrefix);
            return blockIndent - caseIndent;
        }

        private static int getIndentLevel(final Space space) {
            final String ws = space.getWhitespace();
            final int lastNewline = ws.lastIndexOf('\n');
            final int result;
            if (lastNewline >= 0) {
                result = ws.substring(lastNewline + 1).length();
            }
            else {
                result = 0;
            }
            return result;
        }

        private static final class ControlFlowVisitor extends JavaVisitor<Void> {
            private boolean hasDisallowedFlow;

            public boolean hasDisallowedFlow() {
                return hasDisallowedFlow;
            }

            @Override
            public J.Return visitReturn(J.Return returnObj, Void voidObj) {
                hasDisallowedFlow = true;
                return returnObj;
            }

            @Override
            public J.Break visitBreak(J.Break breakObj, Void voidObj) {
                hasDisallowedFlow = true;
                return breakObj;
            }

            @Override
            public J.Continue visitContinue(J.Continue continueObj, Void voidObj) {
                hasDisallowedFlow = true;
                return continueObj;
            }
        }

        private static final class IndentVisitor extends JavaVisitor<Void> {
            private final int indentDelta;

            private IndentVisitor(int indentDelta) {
                this.indentDelta = indentDelta;
            }

            @Override
            public J.Block visitBlock(final J.Block block, final Void unused) {
                final J.Block visited = (J.Block) super.visitBlock(
                        block, unused);
                final List<Statement> adjusted = new ArrayList<>();
                for (Statement stmt : visited.getStatements()) {
                    adjusted.add(stmt.withPrefix(
                            adjustSpace(stmt.getPrefix(),
                                    indentDelta)));
                }
                return visited.withStatements(adjusted).withEnd(
                        adjustSpace(visited.getEnd(), indentDelta));
            }

            private Space adjustSpace(final Space space, final int delta) {
                final String ws = space.getWhitespace();
                final int lastNewline = ws.lastIndexOf('\n');
                Space result = space;
                if (lastNewline >= 0) {
                    final int currentIndent = ws.substring(lastNewline + 1).length();
                    final int safeSize = Math.max(0, currentIndent + delta);
                    final String padding = " ".repeat(safeSize);
                    result = space.withWhitespace(ws.substring(0, lastNewline + 1) + padding);
                }
                return result;
            }
        }
    }
}
