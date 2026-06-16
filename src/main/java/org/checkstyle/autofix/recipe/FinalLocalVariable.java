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
import java.util.Collections;
import java.util.List;

import org.checkstyle.autofix.CheckFullName;
import org.checkstyle.autofix.marker.CheckstyleViolationMarker;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
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
public class FinalLocalVariable extends Recipe {

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
        return new LocalVariableVisitor();
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

            if (declarations.getVariables().size() == 1
                    && !declarations.hasModifier(J.Modifier.Type.Final)) {
                final J.VariableDeclarations.NamedVariable variable = declarations
                        .getVariables().get(0);
                if (isViolationMarked(variable)) {
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
                if (stmt instanceof J.VariableDeclarations varDecl
                        && !varDecl.hasModifier(J.Modifier.Type.Final)) {
                    handleMultiVariableDeclaration(varDecl, newStatements);
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
                if (isViolationMarked(variable)) {
                    violationsList.add(variable.withPrefix(Space.SINGLE_SPACE));
                }
                else {
                    nonViolations.add(variable);
                }
            }
            if (violationsList.size() == varDecl.getVariables().size()) {
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

        private boolean isViolationMarked(J.VariableDeclarations.NamedVariable variable) {
            return variable.getMarkers().findAll(CheckstyleViolationMarker.class).stream()
                    .anyMatch(marker -> marker.isFor(CheckFullName.FINAL_LOCAL_VARIABLE));
        }

        private J.VariableDeclarations addFinalModifier(J.VariableDeclarations varDecl) {
            final List<J.Modifier> modifiers = new ArrayList<>();
            final Space finalPrefix = varDecl.getTypeExpression().getPrefix();
            modifiers.add(new J.Modifier(Tree.randomId(), finalPrefix,
                    Markers.EMPTY, null, J.Modifier.Type.Final, new ArrayList<>()));

            return varDecl.withModifiers(modifiers)
                    .withTypeExpression(varDecl.getTypeExpression().withPrefix(Space.SINGLE_SPACE));
        }
    }
}
