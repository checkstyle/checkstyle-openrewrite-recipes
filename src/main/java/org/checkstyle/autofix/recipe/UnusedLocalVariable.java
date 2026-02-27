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
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.checkstyle.autofix.PositionHelper;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.Comment;
import org.openrewrite.java.tree.Expression;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.Space;
import org.openrewrite.java.tree.Statement;

/**
 * Fixes Checkstyle UnusedLocalVariable violations by removing local variable
 * declarations that are never used.
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
        return "Removes pure unused variables while keeping statements with side effects unchanged "
                + "to ensure maximum code safety.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new RemoveUnusedVisitor();
    }

    private final class RemoveUnusedVisitor extends JavaIsoVisitor<ExecutionContext> {

        private static final String QUOTE = "'";
        private static final String NEW_LINE = "\n";
        private static final String SINGLE_SPACE = " ";
        private static final EnumSet<J.Unary.Type> UNARY_TYPES = EnumSet.of(
                J.Unary.Type.PreDecrement,
                J.Unary.Type.PreIncrement,
                J.Unary.Type.PostIncrement,
                J.Unary.Type.PostDecrement);

        private final Set<UUID> namedVariablesToRemove = new HashSet<>();
        private Set<String> removedVarNamesInMethod = new HashSet<>();

        private J.CompilationUnit originalCu;

        @Override
        public J.CompilationUnit visitCompilationUnit(J.CompilationUnit compilationUnit,
                ExecutionContext executionContext) {
            originalCu = compilationUnit;
            return super.visitCompilationUnit(compilationUnit, executionContext);
        }

        @Override
        public J.MethodDeclaration visitMethodDeclaration(J.MethodDeclaration method,
                ExecutionContext executionContext) {
            final Set<String> savedRemovedNames = this.removedVarNamesInMethod;
            this.removedVarNamesInMethod = new HashSet<>();
            final J.MethodDeclaration result = super.visitMethodDeclaration(
                    method, executionContext);
            this.removedVarNamesInMethod = savedRemovedNames;
            return result;
        }

        @Override
        public J.VariableDeclarations visitVariableDeclarations(J.VariableDeclarations varDecl,
                ExecutionContext executionContext) {
            final J.VariableDeclarations visited =
                    super.visitVariableDeclarations(varDecl, executionContext);

            for (J.VariableDeclarations.NamedVariable variable : visited.getVariables()) {
                if (isAtViolationLocation(visited, variable)) {
                    namedVariablesToRemove.add(variable.getId());
                    removedVarNamesInMethod.add(variable.getSimpleName());
                }
            }
            return visited;
        }

        private boolean isAtViolationLocation(J.VariableDeclarations varDecl,
                J.VariableDeclarations.NamedVariable variable) {
            boolean matches = false;
            final int line = PositionHelper
                    .computeLinePosition(originalCu, varDecl, getCursor());

            for (CheckstyleViolation violation : violations) {
                final String violationPath = violation.getFilePath().toString();
                if (violation.getLine() == line
                        && violationPath.endsWith(originalCu.getSourcePath().toString())
                        && violation.getMessage().contains(
                                QUOTE + variable.getSimpleName() + QUOTE)) {
                    matches = true;
                    break;
                }
            }
            return matches;
        }

        @Override
        public J.Block visitBlock(J.Block block, ExecutionContext executionContext) {
            final J.Block visited = super.visitBlock(block, executionContext);
            return visited.withStatements(buildNewStatements(visited.getStatements()));
        }

        @Override
        public J.Case visitCase(J.Case _case, ExecutionContext executionContext) {
            final J.Case visited = super.visitCase(_case, executionContext);
            return visited.withStatements(buildNewStatements(visited.getStatements()));
        }

        private List<Statement> buildNewStatements(List<Statement> statements) {
            return rewriteStatements(statements);
        }

        private List<Statement> rewriteStatements(List<Statement> statements) {
            final List<Statement> out = new ArrayList<>();
            final CommentsState commentState = new CommentsState();
            for (int index = 0; index < statements.size(); index++) {
                final Statement stmt = statements.get(index);
                final boolean noOutputYet = out.isEmpty();
                final boolean replacingRemovedLead = index > 0 && noOutputYet;
                out.addAll(rewriteOne(stmt, noOutputYet, replacingRemovedLead, commentState));
            }
            return out;
        }

        private List<Statement> rewriteOne(Statement stmt, boolean noOutputYet,
                boolean replacingRemovedLead, CommentsState commentState) {
            final RewriteResult res = produceReplacements(stmt);
            final List<Statement> result;

            if (res.getStatements().isEmpty()) {
                commentState.absorb(stmt.getPrefix(), noOutputYet);
                result = new ArrayList<>();
            }
            else if (res.isDerived()) {
                final boolean ensureNewline = stmt instanceof J.VariableDeclarations;
                result = applyPrefix(res.getStatements(), ensureNewline,
                        replacingRemovedLead, commentState);
            }
            else {
                result = applyPrefix(res.getStatements(), false,
                        replacingRemovedLead, commentState);
            }
            return result;
        }

        private RewriteResult produceReplacements(Statement stmt) {
            final RewriteResult result;
            if (stmt instanceof J.VariableDeclarations varDecl) {
                result = rewriteVariableDeclaration(varDecl);
            }
            else if (isOrphanedAssignment(stmt, removedVarNamesInMethod)) {
                result = rewriteOrphanedAssignment(stmt);
            }
            else {
                final List<Statement> kept = new ArrayList<>();
                kept.add(stmt);
                result = new RewriteResult(kept, false);
            }
            return result;
        }

        private RewriteResult rewriteVariableDeclaration(J.VariableDeclarations varDecl) {
            final List<J.VariableDeclarations.NamedVariable> removed = new ArrayList<>();
            final List<J.VariableDeclarations.NamedVariable> remaining = new ArrayList<>();
            for (J.VariableDeclarations.NamedVariable variable : varDecl.getVariables()) {
                if (namedVariablesToRemove.contains(variable.getId())) {
                    removed.add(variable);
                }
                else {
                    remaining.add(variable);
                }
            }

            final RewriteResult result;
            if (!remaining.isEmpty()) {
                final List<Statement> stmts = new ArrayList<>();
                stmts.add(varDecl.withVariables(remaining));
                stmts.addAll(addInitializerStatements(removed, varDecl.getPrefix()));
                result = new RewriteResult(stmts, false);
            }
            else {
                final List<Statement> initializers = extractInitializerStatements(varDecl);
                result = new RewriteResult(initializers, true);
            }
            return result;
        }

        private static RewriteResult rewriteOrphanedAssignment(Statement stmt) {
            final List<Statement> result = new ArrayList<>();
            final Statement sideEffectStmt = extractSideEffectFromAssignment(stmt);
            if (sideEffectStmt != null) {
                result.add(sideEffectStmt);
            }
            return new RewriteResult(result, true);
        }

        private static List<Statement> applyPrefix(List<Statement> produced,
                boolean ensureNewline, boolean replacingRemovedLead,
                CommentsState commentState) {
            final List<Statement> result = new ArrayList<>(produced);
            Statement first = result.get(0);

            if (commentState.unclearedPendingWhitespace != null) {
                final String ws = first.getPrefix().getWhitespace();
                if (!ws.contains(NEW_LINE)) {
                    first = first.withPrefix(first.getPrefix().withWhitespace(
                            commentState.unclearedPendingWhitespace));
                }
                commentState.unclearedPendingWhitespace = null;
            }

            if (ensureNewline && first.getPrefix().getWhitespace().contains(NEW_LINE)) {
                first = ensureNewlineInPrefix(first);
            }

            if (!commentState.getPending().isEmpty()) {
                final String pendingWhitespace = commentState.getPendingWhitespace();
                first = prependComments(first, commentState.getPending(), pendingWhitespace);
                commentState.setPending(new ArrayList<>());
            }

            if (replacingRemovedLead) {
                first = stripLeadingBlankLines(first);
            }

            first = attachTrailingComments(first, commentState.getTrailing());
            commentState.getTrailing().clear();

            result.set(0, first);
            return result;
        }

        private static Statement stripLeadingBlankLines(Statement stmt) {
            Space prefix = stmt.getPrefix();

            final String whitespace = prefix.getWhitespace();
            final int wsLastNl = whitespace.lastIndexOf(NEW_LINE);
            if (wsLastNl >= 0) {
                prefix = prefix.withWhitespace(whitespace.substring(wsLastNl));
            }
            else {
                prefix = ensureLeadingNewline(prefix);
            }

            final List<Comment> comments = prefix.getComments();
            if (!comments.isEmpty()) {
                final int lastIndex = comments.size() - 1;
                final Comment lastComment = comments.get(lastIndex);
                final String suffix = lastComment.getSuffix();
                final int suffixLastNewline = suffix.lastIndexOf(NEW_LINE);
                if (suffixLastNewline >= 0) {
                    final Comment updatedComment = lastComment.withSuffix(
                            suffix.substring(suffixLastNewline));
                    final List<Comment> updatedComments = new ArrayList<>(comments);
                    updatedComments.set(lastIndex, updatedComment);
                    prefix = prefix.withComments(updatedComments);
                }
            }

            return stmt.withPrefix(prefix);
        }

        private static Space ensureLeadingNewline(Space prefix) {
            Space result = prefix;
            final List<Comment> comments = prefix.getComments();
            for (Comment comment : comments) {
                if (comment.getSuffix().contains(NEW_LINE)) {
                    final String indent = extractIndentFromWhitespace(
                            comment.getSuffix());
                    result = prefix.withWhitespace(NEW_LINE + indent);
                    break;
                }
            }
            return result;
        }

        private static Statement ensureNewlineInPrefix(Statement stmt) {
            return stmt.withPrefix(ensureLeadingNewline(stmt.getPrefix()));
        }

        private static Statement prependComments(Statement stmt, List<Comment> comments,
                String pendingWhitespace) {
            final Space prefix = stmt.getPrefix();
            final List<Comment> merged = new ArrayList<>(comments);
            merged.addAll(prefix.getComments());
            final String originalWs = prefix.getWhitespace();
            final String whitespace = pendingWhitespace;
            final int lastPendingIndex = comments.size() - 1;
            final Comment lastPending = merged.get(lastPendingIndex);
            if (originalWs.contains(NEW_LINE)) {
                final String indent = extractIndentFromWhitespace(originalWs);
                merged.set(lastPendingIndex,
                        lastPending.withSuffix(NEW_LINE + indent));
            }
            return stmt.withPrefix(prefix.withComments(merged).withWhitespace(whitespace));
        }

        private static String extractIndentFromWhitespace(String whitespace) {
            final int lastNl = whitespace.lastIndexOf(NEW_LINE);
            return whitespace.substring(lastNl + 1);
        }

        private static Statement attachTrailingComments(Statement stmt,
                List<Comment> trailingComments) {
            Statement result = stmt;
            if (!trailingComments.isEmpty()) {
                final Space prefix = stmt.getPrefix();
                final List<Comment> allComments = new ArrayList<>(trailingComments);
                final String originalWs = prefix.getWhitespace();
                if (originalWs.contains(NEW_LINE)) {
                    final int lastIdx = allComments.size() - 1;
                    final String indent = extractIndentFromWhitespace(originalWs);
                    allComments.set(lastIdx,
                            allComments.get(lastIdx).withSuffix(NEW_LINE + indent));
                }
                allComments.addAll(prefix.getComments());
                result = stmt.withPrefix(prefix.withComments(allComments)
                        .withWhitespace(SINGLE_SPACE));
            }
            return result;
        }

        private static boolean isOrphanedAssignment(Statement stmt, Set<String> removedNames) {
            boolean result = false;
            if (stmt instanceof J.Assignment assignment) {
                if (assignment.getVariable() instanceof J.Identifier id) {
                    result = removedNames.contains(id.getSimpleName());
                }
            }
            else if (stmt instanceof J.Unary unary) {
                if (unary.getExpression() instanceof J.Identifier id) {
                    result = removedNames.contains(id.getSimpleName());
                }
            }
            return result;
        }

        private static Statement extractSideEffectFromAssignment(Statement stmt) {
            Statement result = null;
            if (stmt instanceof J.Assignment assignment) {
                final Expression assignedExpression = unwrap(assignment.getAssignment());
                if (assignedExpression instanceof Statement assignedStmt) {
                    result = assignedStmt.withPrefix(stmt.getPrefix());
                }
            }
            return result;
        }

        private static List<Statement> addInitializerStatements(
                List<J.VariableDeclarations.NamedVariable> removed, Space prefix) {
            final List<Statement> result = new ArrayList<>();
            final Space indentPrefix = getIndentPrefix(prefix);
            for (J.VariableDeclarations.NamedVariable variable : removed) {
                final Expression unwrapped = unwrap(variable.getInitializer());
                if (unwrapped instanceof Statement initStmt
                        && isStatementExpression(unwrapped)) {
                    result.add(initStmt.withPrefix(indentPrefix));
                }
            }
            return result;
        }

        private static Space getIndentPrefix(Space originalPrefix) {
            Space result = null;
            final String whitespace = originalPrefix.getWhitespace();
            final int lastNl = whitespace.lastIndexOf(NEW_LINE);
            if (lastNl >= 0) {
                result = Space.format(whitespace.substring(lastNl));
            }
            else {
                for (Comment comment : originalPrefix.getComments()) {
                    final String suffix = comment.getSuffix();
                    final int commentLastNl = suffix.lastIndexOf(NEW_LINE);
                    if (commentLastNl >= 0) {
                        result = Space.format(suffix.substring(commentLastNl));
                        break;
                    }
                }
            }
            if (result == null) {
                result = Space.format(NEW_LINE);
            }
            return result;
        }

        private static List<Statement> extractInitializerStatements(
                J.VariableDeclarations varDecl) {
            final List<Statement> initStatements = new ArrayList<>();
            boolean isFirst = true;
            for (J.VariableDeclarations.NamedVariable variable : varDecl.getVariables()) {
                final Expression initializer = variable.getInitializer();
                final Expression unwrapped = unwrap(initializer);
                if (unwrapped instanceof Statement initStmt
                        && isStatementExpression(unwrapped)) {
                    final Space prefix;
                    if (isFirst) {
                        prefix = varDecl.getPrefix();
                    }
                    else {
                        prefix = variable.getPrefix();
                    }
                    initStatements.add(initStmt.withPrefix(prefix));
                    isFirst = false;
                }
            }
            return initStatements;
        }

        private static Expression unwrap(Expression expr) {
            Expression result = expr;
            if (result instanceof J.Parentheses<?> parens) {
                result = unwrap((Expression) parens.getTree());
            }
            return result;
        }

        private static boolean isStatementExpression(Expression expr) {
            final boolean isUnaryIncremental;
            if (expr instanceof J.Unary unary) {
                isUnaryIncremental = UNARY_TYPES.contains(unary.getOperator());
            }
            else {
                isUnaryIncremental = false;
            }

            return isUnaryIncremental
                    || expr instanceof J.Assignment
                    || expr instanceof J.AssignmentOperation
                    || expr instanceof J.MethodInvocation
                    || expr instanceof J.NewClass;
        }

        private static final class RewriteResult {
            private final List<Statement> statements;
            private final boolean isDerived;

            RewriteResult(List<Statement> statements, boolean isDerived) {
                this.statements = statements;
                this.isDerived = isDerived;
            }

            public List<Statement> getStatements() {
                return statements;
            }

            public boolean isDerived() {
                return isDerived;
            }
        }

        private static final class CommentsState {
            private List<Comment> pending = new ArrayList<>();
            private String pendingWhitespace;
            private List<Comment> trailing = new ArrayList<>();
            private String unclearedPendingWhitespace;

            public List<Comment> getPending() {
                return pending;
            }

            public void setPending(List<Comment> pending) {
                this.pending = pending;
            }

            public String getPendingWhitespace() {
                return pendingWhitespace;
            }

            public List<Comment> getTrailing() {
                return trailing;
            }

            public void absorb(Space prefix, boolean isNewStatementsEmpty) {
                String currentWhitespace = prefix.getWhitespace();
                if (!currentWhitespace.contains(NEW_LINE)) {
                    unclearedPendingWhitespace = null;
                }
                final List<Comment> comments = prefix.getComments();

                if (comments.isEmpty()) {
                    if (currentWhitespace.contains(NEW_LINE)) {
                        final int lastNl = currentWhitespace.lastIndexOf(NEW_LINE);
                        unclearedPendingWhitespace = currentWhitespace.substring(lastNl);
                    }
                }
                else {
                    boolean isFirstPending = true;

                    for (Comment comment : comments) {
                        final boolean isTrailing = !currentWhitespace.contains(NEW_LINE)
                                && !isNewStatementsEmpty;
                        if (isTrailing) {
                            trailing.add(comment);
                        }
                        else {
                            if (isFirstPending) {
                                if (unclearedPendingWhitespace != null) {
                                    pendingWhitespace = unclearedPendingWhitespace;
                                }
                                else {
                                    pendingWhitespace = currentWhitespace;
                                }
                                unclearedPendingWhitespace = null;
                                isFirstPending = false;
                            }
                            pending.add(comment);
                        }
                        currentWhitespace = comment.getSuffix();
                    }
                }
            }
        }
    }
}
