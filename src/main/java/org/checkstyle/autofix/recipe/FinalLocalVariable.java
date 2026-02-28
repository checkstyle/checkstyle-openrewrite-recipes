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
import java.util.List;

import org.checkstyle.autofix.PositionHelper;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Tree;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.Space;
import org.openrewrite.java.tree.Statement;
import org.openrewrite.marker.Markers;

/**
 * Fixes Checkstyle FinalLocalVariable violations by adding 'final' modifier to local variables
 * that are never reassigned.
 */
public class FinalLocalVariable extends AbstractScanningRecipe {

    public FinalLocalVariable(List<CheckstyleViolation> violations) {
        super(violations);
    }

    @Override
    public String getDisplayName() {
        return "FinalLocalVariable recipe";
    }

    @Override
    public String getDescription() {
        return "Adds 'final' modifier to local variables that never have their values changed.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getScanner(ViolationAccumulator acc) {
        return new ScannerVisitor(acc);
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor(ViolationAccumulator acc) {
        return new LocalVariableVisitor(acc);
    }

    /**
     * Scanner that identifies variable declarations at violation locations
     * and records their UUIDs in the accumulator.
     */
    private final class ScannerVisitor extends JavaIsoVisitor<ExecutionContext> {

        private final ViolationAccumulator acc;
        private Path sourcePath;
        private J.CompilationUnit currentCompilationUnit;

        private ScannerVisitor(ViolationAccumulator acc) {
            this.acc = acc;
        }

        @Override
        public J.CompilationUnit visitCompilationUnit(J.CompilationUnit cu,
                                                      ExecutionContext executionContext) {
            this.sourcePath = cu.getSourcePath().toAbsolutePath();
            this.currentCompilationUnit = cu;
            return super.visitCompilationUnit(cu, executionContext);
        }

        @Override
        public J.VariableDeclarations visitVariableDeclarations(
                J.VariableDeclarations multiVariable, ExecutionContext executionContext) {

            final J.VariableDeclarations declarations = super
                    .visitVariableDeclarations(multiVariable, executionContext);

            if (!(getCursor().getParentTreeCursor().getValue() instanceof J.ClassDeclaration)
                    && !declarations.hasModifier(J.Modifier.Type.Final)) {

                final List<J.VariableDeclarations.NamedVariable> variables = declarations
                        .getVariables();
                for (J.VariableDeclarations.NamedVariable variable : variables) {
                    if (isAtViolationLocation(variable)) {
                        acc.getMatched().computeIfAbsent(variable.getId(),
                                key -> new ArrayList<>());
                    }
                }
            }
            return declarations;
        }

        private boolean isAtViolationLocation(J.VariableDeclarations.NamedVariable variable) {

            final int line = PositionHelper
                    .computeLinePosition(currentCompilationUnit, variable, getCursor());
            final int column = PositionHelper
                    .computeColumnPosition(currentCompilationUnit, variable, getCursor());

            return getViolations().removeIf(violation -> {
                final Path absolutePath = violation.getFilePath().toAbsolutePath();
                return violation.getLine() == line
                        && violation.getColumn() == column
                        && absolutePath.endsWith(sourcePath)
                        && violation.getMessage().contains(variable.getSimpleName());
            });
        }
    }

    /**
     * Visitor that processes marked variable declarations and applies the final modifier.
     * This visitor handles both single and multi-variable declarations.
     */
    private static final class LocalVariableVisitor extends JavaIsoVisitor<ExecutionContext> {

        private final ViolationAccumulator acc;

        private LocalVariableVisitor(ViolationAccumulator acc) {
            this.acc = acc;
        }

        @Override
        public J.CompilationUnit visitCompilationUnit(
                J.CompilationUnit cu, ExecutionContext executionContext) {
            return super.visitCompilationUnit(cu, executionContext);
        }

        @Override
        public J.VariableDeclarations visitVariableDeclarations(
                J.VariableDeclarations multiVariable, ExecutionContext executionContext) {

            J.VariableDeclarations declarations = super.visitVariableDeclarations(multiVariable,
                    executionContext);

            if (!(getCursor().getParentTreeCursor().getValue() instanceof J.ClassDeclaration)
                    && declarations.getVariables().size() == 1
                    && declarations.getTypeExpression() != null
                    && !declarations.hasModifier(J.Modifier.Type.Final)) {
                final J.VariableDeclarations.NamedVariable variable = declarations
                        .getVariables().get(0);
                if (acc.getMatched().containsKey(variable.getId())) {
                    declarations = addFinalModifier(declarations);
                }
            }
            return declarations;
        }

        @Override
        public J.Block visitBlock(J.Block block, ExecutionContext executionContext) {
            final J.Block visited = super.visitBlock(block, executionContext);

            final List<Statement> newStatements = new ArrayList<>();

            for (Statement stmt : visited.getStatements()) {
                if (isVariableDeclaration(stmt)) {
                    handleMultiVariableDeclaration((J.VariableDeclarations) stmt, newStatements);
                }
                else {
                    newStatements.add(stmt);
                }
            }

            return visited.withStatements(newStatements);
        }

        private void handleMultiVariableDeclaration(J.VariableDeclarations varDecl,
                                                       List<Statement> newStatements) {
            final List<J.VariableDeclarations.NamedVariable> violationsList = new ArrayList<>();
            final List<J.VariableDeclarations.NamedVariable> nonViolations = new ArrayList<>();

            for (J.VariableDeclarations.NamedVariable variable : varDecl.getVariables()) {
                if (acc.getMatched().containsKey(variable.getId())) {
                    violationsList.add(variable.withPrefix(Space.SINGLE_SPACE));
                }
                else {
                    nonViolations.add(variable.withPrefix(Space.SINGLE_SPACE));
                }
            }
            if (violationsList.isEmpty()) {
                newStatements.add(varDecl);
            }
            else if (nonViolations.isEmpty()) {
                newStatements.add(addFinalModifier(varDecl));
            }
            else {
                newStatements.add(varDecl.withVariables(nonViolations));
                for (J.VariableDeclarations.NamedVariable variable : violationsList) {
                    newStatements.add(addFinalModifier(varDecl
                            .withVariables(Collections.singletonList(variable))));
                }
            }
        }

        private J.VariableDeclarations addFinalModifier(J.VariableDeclarations varDecl) {
            final List<J.Modifier> modifiers = new ArrayList<>();
            final Space finalPrefix = varDecl.getTypeExpression().getPrefix();
            modifiers.add(new J.Modifier(Tree.randomId(), finalPrefix,
                    Markers.EMPTY, null, J.Modifier.Type.Final, new ArrayList<>()));

            modifiers.addAll(varDecl.getModifiers());

            return varDecl.withModifiers(modifiers)
                    .withTypeExpression(varDecl.getTypeExpression().withPrefix(Space.SINGLE_SPACE));
        }

        private boolean isVariableDeclaration(Statement stmt) {
            return stmt instanceof J.VariableDeclarations varDecl
                    && varDecl.getVariables().size() > 1
                    && !varDecl.hasModifier(J.Modifier.Type.Final)
                    && varDecl.getTypeExpression() != null
                    && !(getCursor().getParentTreeCursor()
                    .getValue() instanceof J.ClassDeclaration);
        }
    }
}
