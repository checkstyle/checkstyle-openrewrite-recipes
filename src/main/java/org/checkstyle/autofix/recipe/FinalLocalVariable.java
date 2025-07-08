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
import java.util.concurrent.CancellationException;
import java.util.function.Function;

import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.Cursor;
import org.openrewrite.ExecutionContext;
import org.openrewrite.PrintOutputCapture;
import org.openrewrite.Recipe;
import org.openrewrite.Tree;
import org.openrewrite.TreeVisitor;
import org.openrewrite.internal.RecipeRunException;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.Space;
import org.openrewrite.marker.Markers;

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
        public J.CompilationUnit visitCompilationUnit(J.CompilationUnit cu, ExecutionContext ctx) {
            this.sourcePath = cu.getSourcePath();
            return super.visitCompilationUnit(cu, ctx);
        }

        @Override
        public J.VariableDeclarations visitVariableDeclarations(
                J.VariableDeclarations multiVariable, ExecutionContext ctx) {
            J.VariableDeclarations declarations = super.visitVariableDeclarations(multiVariable,
                    ctx);

            if (!(getCursor().getParentTreeCursor().getValue() instanceof J.ClassDeclaration)
                    && declarations.getVariables().size() == 1) {
                final J.VariableDeclarations.NamedVariable variable = declarations
                        .getVariables().get(0);
                if (isAtViolationLocation(variable)) {
                    final List<J.Modifier> modifiers = new ArrayList<>();
                    modifiers.add(new J.Modifier(Tree.randomId(), Space.EMPTY,
                            Markers.EMPTY, null, J.Modifier.Type.Final, new ArrayList<>()));
                    modifiers.addAll(Space.formatFirstPrefix(declarations.getModifiers(),
                            Space.SINGLE_SPACE));
                    declarations = declarations.withModifiers(modifiers)
                            .withTypeExpression(declarations.getTypeExpression()
                                    .withPrefix(Space.SINGLE_SPACE));
                }
            }
            return declarations;
        }

        private boolean isAtViolationLocation(J.VariableDeclarations.NamedVariable literal) {
            final J.CompilationUnit cursor = getCursor().firstEnclosing(J.CompilationUnit.class);

            final int line = computeLinePosition(cursor, literal, getCursor());
            final int column = computeColumnPosition(cursor, literal, getCursor());

            return violations.stream().anyMatch(violation -> {
                return violation.getLine() == line
                        && violation.getColumn() == column
                        && Path.of(violation.getFileName()).equals(sourcePath);
            });
        }

        private int computePosition(
                J tree,
                J targetElement,
                Cursor cursor,
                Function<String, Integer> positionCalculator
        ) {
            final TreeVisitor<?, PrintOutputCapture<TreeVisitor<?, ?>>> printer =
                    tree.printer(cursor);

            final PrintOutputCapture<TreeVisitor<?, ?>> capture =
                    new PrintOutputCapture<>(printer) {
                        @Override
                        public PrintOutputCapture<TreeVisitor<?, ?>> append(String text) {
                            if (targetElement.isScope(getContext().getCursor().getValue())) {
                                super.append(targetElement.getPrefix().getWhitespace());
                                throw new CancellationException();
                            }
                            return super.append(text);
                        }
                    };

            final int result;
            try {
                printer.visit(tree, capture, cursor.getParentOrThrow());
                throw new IllegalStateException("Target element: " + targetElement
                        + ", not found in the syntax tree.");
            }
            catch (CancellationException exception) {
                result = positionCalculator.apply(capture.getOut());
            }
            catch (RecipeRunException exception) {
                if (exception.getCause() instanceof CancellationException) {
                    result = positionCalculator.apply(capture.getOut());
                }
                else {
                    throw exception;
                }
            }
            return result;
        }

        private int computeLinePosition(J tree, J targetElement, Cursor cursor) {
            return computePosition(tree, targetElement, cursor,
                    out -> 1 + Math.toIntExact(out.chars().filter(chr -> chr == '\n').count()));
        }

        private int computeColumnPosition(J tree, J targetElement, Cursor cursor) {
            return computePosition(tree, targetElement, cursor, this::calculateColumnOffset);
        }

        private int calculateColumnOffset(String out) {
            final int lineBreakIndex = out.lastIndexOf('\n');
            final int result;
            if (lineBreakIndex == -1) {
                result = out.length();
            }
            else {
                result = out.length() - lineBreakIndex - 1;
            }
            return result;
        }
    }
}
