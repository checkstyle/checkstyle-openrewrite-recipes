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
import java.util.List;

import org.checkstyle.autofix.PositionHelper;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.Tree;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.Space;
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
        return new LocalVariableVisitor();
    }

    private final class LocalVariableVisitor extends JavaIsoVisitor<ExecutionContext> {

        private Path sourcePath;

        @Override
        public J.CompilationUnit visitCompilationUnit(
                J.CompilationUnit cu, ExecutionContext executionContext) {
            this.sourcePath = cu.getSourcePath();
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
                if (isAtViolationLocation(variable)) {
                    final List<J.Modifier> modifiers = new ArrayList<>();

                    final Space finalPrefix = declarations.getTypeExpression().getPrefix();

                    modifiers.add(new J.Modifier(Tree.randomId(), finalPrefix,
                            Markers.EMPTY, null, J.Modifier.Type.Final, new ArrayList<>()));
                    modifiers.addAll(declarations.getModifiers());
                    declarations = declarations.withModifiers(modifiers)
                            .withTypeExpression(declarations.getTypeExpression()
                                    .withPrefix(Space.SINGLE_SPACE));
                }
            }
            return declarations;
        }

        private boolean isAtViolationLocation(J.VariableDeclarations.NamedVariable literal) {
            final J.CompilationUnit cursor = getCursor().firstEnclosing(J.CompilationUnit.class);

            final int line = PositionHelper.computeLinePosition(cursor, literal, getCursor());
            final int column = PositionHelper.computeColumnPosition(cursor, literal, getCursor());

            return violations.stream().anyMatch(violation -> {
                return violation.getLine() == line
                        && violation.getColumn() == column
                        && Path.of(violation.getFileName()).endsWith(sourcePath);
            });
        }
    }
}
