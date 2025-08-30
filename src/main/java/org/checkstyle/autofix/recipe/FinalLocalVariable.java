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
import java.util.UUID;

import org.checkstyle.autofix.PositionHelper;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.Tree;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.Space;
import org.openrewrite.java.tree.Statement;
import org.openrewrite.marker.Marker;
import org.openrewrite.marker.Markers;

/**
 * Fixes Checkstyle FinalLocalVariable violations by adding 'final' modifier to local variables
 * that are never reassigned.
 */
public class FinalLocalVariable extends Recipe {

    private final List<CheckstyleViolation> violations;

    public FinalLocalVariable(List<CheckstyleViolation> violations) {
        this.violations = violations;
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
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new JavaIsoVisitor<>() {
            @Override
            public J.CompilationUnit visitCompilationUnit(J.CompilationUnit cu,
                                                          ExecutionContext executionContext) {
                return new LocalVariableVisitor()
                        .visitCompilationUnit(new MarkViolationVisitor()
                                .visitCompilationUnit(cu, executionContext), executionContext);
            }
        };
    }

    /**
     * Visitor that identifies and marks variable declarations at violation locations.
     * This visitor traverses the AST and adds markers to variables that match
     * the checkstyle violation locations, preparing them for the final modifier addition.
     */
    private final class MarkViolationVisitor extends JavaIsoVisitor<ExecutionContext> {

        private Path sourcePath;
        private J.CompilationUnit currentCompilationUnit;

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

            final J.VariableDeclarations variableDeclarations;

            final J.VariableDeclarations declarations = super
                    .visitVariableDeclarations(multiVariable, executionContext);

            if (!(getCursor().getParentTreeCursor().getValue() instanceof J.ClassDeclaration)
                    && !declarations.hasModifier(J.Modifier.Type.Final)) {

                final List<J.VariableDeclarations.NamedVariable> variables = declarations
                        .getVariables();
                final List<J.VariableDeclarations.NamedVariable> marked = new ArrayList<>();
                for (J.VariableDeclarations.NamedVariable variable : variables) {
                    if (isAtViolationLocation(variable)) {
                        marked.add(variable.withMarkers(
                                variable.getMarkers().add(
                                        new FinalLocalVariableMarker(UUID.randomUUID()))));
                    }
                    else {
                        marked.add(variable);
                    }
                }
                variableDeclarations = declarations.withVariables(marked);
            }
            else {
                variableDeclarations = declarations;
            }
            return variableDeclarations;
        }

        private boolean isAtViolationLocation(J.VariableDeclarations.NamedVariable variable) {

            final int line = PositionHelper
                    .computeLinePosition(currentCompilationUnit, variable, getCursor());
            final int column = PositionHelper
                    .computeColumnPosition(currentCompilationUnit, variable, getCursor());

            return violations.removeIf(violation -> {
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
    private final class LocalVariableVisitor extends JavaIsoVisitor<ExecutionContext> {

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
                if (variable.getMarkers().findFirst(FinalLocalVariableMarker.class).isPresent()) {
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
                if (variable.getMarkers().findFirst(FinalLocalVariableMarker.class).isPresent()) {
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

    private static final class FinalLocalVariableMarker implements Marker {

        private final UUID id;

        private FinalLocalVariableMarker(UUID uuid) {
            this.id = uuid;
        }

        @Override
        public UUID getId() {
            return id;
        }

        @Override
        public <M extends Marker> M withId(UUID uuid) {
            return (M) new FinalLocalVariableMarker(uuid);
        }
    }
}
