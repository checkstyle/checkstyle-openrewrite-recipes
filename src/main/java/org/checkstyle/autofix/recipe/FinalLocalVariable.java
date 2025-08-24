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
        System.out.println("Created FinalLocalVariable");
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
            public J.CompilationUnit visitCompilationUnit(J.CompilationUnit cu, ExecutionContext ctx) {
                cu = new MarkViolationVisitor().visitCompilationUnit(cu, ctx);

                cu = new FinalLocalVariableVisitor().visitCompilationUnit(cu, ctx);

                return cu;
            }
        };
    }

    public static class FinalLocalVariableMarker implements Marker {
        private final UUID id;

        public FinalLocalVariableMarker(UUID id) {
            this.id = id;
        }

        @Override
        public UUID getId() {
            return id;
        }

        @Override
        public <M extends Marker> M withId(UUID id) {
            return (M) new FinalLocalVariableMarker(id);
        }

    }

    private class MarkViolationVisitor extends JavaIsoVisitor<ExecutionContext> {

        private Path sourcePath;
        private J.CompilationUnit currentCompilationUnit;

        @Override
        public J.CompilationUnit visitCompilationUnit(J.CompilationUnit cu, ExecutionContext ctx) {
            this.sourcePath = cu.getSourcePath().toAbsolutePath();
            this.currentCompilationUnit = cu;
            return super.visitCompilationUnit(cu, ctx);
        }

        @Override
        public J.VariableDeclarations.NamedVariable visitVariable(J.VariableDeclarations.NamedVariable variable,
                                                                  ExecutionContext ctx) {


            if (isAtViolationLocation(variable)) {
                return variable.withMarkers(
                        variable.getMarkers().add(new FinalLocalVariableMarker(UUID.randomUUID()))
                );
            }
            return variable;
        }

        private boolean isAtViolationLocation(J.VariableDeclarations.NamedVariable variable) {

            final int line = PositionHelper.computeLinePosition(currentCompilationUnit, variable, getCursor());
            final int column = PositionHelper.computeColumnPosition(currentCompilationUnit, variable, getCursor());

            System.out.println("Variable: " + variable.getSimpleName() +
                    " at " + line + ":" + column);

            violations.forEach(v -> {
                if (v.getFileName().contains("InputSingleLocalTest")) {
                    System.out.println("  Looking for violation at " + v.getLine() + ":" + v.getColumn());
                }
            });

            return violations.removeIf(violation -> {
                final Path absolutePath = Path.of(violation.getFileName()).toAbsolutePath();
                return violation.getLine() == line
                        && violation.getColumn() == column
                        && absolutePath.equals(sourcePath);
            });
        }
    }

    private final class FinalLocalVariableVisitor extends JavaIsoVisitor<ExecutionContext> {


        @Override
        public J.CompilationUnit visitCompilationUnit(J.CompilationUnit cu, ExecutionContext ctx) {
            return super.visitCompilationUnit(cu, ctx);
        }

        @Override
        public J.VariableDeclarations visitVariableDeclarations(
                J.VariableDeclarations multiVariable, ExecutionContext executionContext) {

            final J.VariableDeclarations declarations =
                    super.visitVariableDeclarations(multiVariable, executionContext);

            J.VariableDeclarations result = declarations;

            if (!(getCursor().getParentTreeCursor().getValue() instanceof J.ClassDeclaration)
                    && !declarations.hasModifier(J.Modifier.Type.Final)) {

                boolean hasViolation = false;
                for (J.VariableDeclarations.NamedVariable variable : declarations.getVariables()) {
                    if (variable.getMarkers().findFirst(FinalLocalVariableMarker.class).isPresent()) {
                        hasViolation = true;
                        break;
                    }
                }

                if (hasViolation && declarations.getVariables().size() == 1
                        && declarations.getTypeExpression() != null) {
                    result = addFinalModifier(declarations);
                }
            }

            return result;
        }

        @Override
        public J.Block visitBlock(J.Block block, ExecutionContext executionContext) {
            J.Block visited = super.visitBlock(block, executionContext);

            final List<Statement> newStatements = new ArrayList<>();
            boolean changed = false;

            for (Statement stmt : visited.getStatements()) {
                if (!(stmt instanceof J.VariableDeclarations)) {
                    newStatements.add(stmt);
                    continue;
                }

                final J.VariableDeclarations varDecl = (J.VariableDeclarations) stmt;

                if (varDecl.hasModifier(J.Modifier.Type.Final) || getCursor().getParentTreeCursor()
                        .getValue() instanceof J.ClassDeclaration) {
                    newStatements.add(stmt);
                    continue;
                }

                if (varDecl.getVariables().size() > 1) {
                    changed |= handleMultiVariableDeclaration(varDecl, newStatements);
                }
                else {
                    newStatements.add(stmt);
                }
            }

            if (changed) {
                visited = visited.withStatements(newStatements);
            }

            return visited;
        }

        private boolean handleMultiVariableDeclaration(J.VariableDeclarations varDecl,
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

            boolean changed = false;

            if (violationsList.isEmpty()) {
                newStatements.add(varDecl);
            }
            else if (nonViolations.isEmpty()) {
                newStatements.add(addFinalModifier(varDecl));
                changed = true;
            }
            else {
                newStatements.add(varDecl.withVariables(nonViolations));
                for (J.VariableDeclarations.NamedVariable variable : violationsList) {
                    newStatements.add(addFinalModifier(varDecl
                            .withVariables(Collections.singletonList(variable))));
                }
                changed = true;
            }

            return changed;
        }

        private J.VariableDeclarations addFinalModifier(J.VariableDeclarations varDecl) {
            final List<J.Modifier> modifiers = new ArrayList<>();
            modifiers.add(new J.Modifier(Tree.randomId(), Space.EMPTY,
                    Markers.EMPTY, null, J.Modifier.Type.Final, new ArrayList<>()));
            modifiers.addAll(varDecl.getModifiers());

            J.VariableDeclarations result = varDecl.withModifiers(modifiers);

            if (result.getTypeExpression() != null) {
                result = result.withTypeExpression(
                        result.getTypeExpression().withPrefix(Space.SINGLE_SPACE));
            }

            return result;
        }
    }
}
