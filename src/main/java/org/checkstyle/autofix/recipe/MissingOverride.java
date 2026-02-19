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
import org.openrewrite.java.tree.JavaType;
import org.openrewrite.java.tree.Space;
import org.openrewrite.java.tree.TypeTree;
import org.openrewrite.marker.Markers;

/**
 * Fixes Checkstyle MissingOverride violations by adding @Override annotation to methods that
 * override a method from a superclass or interface.
 */
public class MissingOverride extends Recipe {

    private final List<CheckstyleViolation> violations;

    public MissingOverride(List<CheckstyleViolation> violations) {
        this.violations = violations;
    }

    @Override
    public String getDisplayName() {
        return "Missing Override annotation";
    }

    @Override
    public String getDescription() {
        return "Add @Override annotation to methods that override a method "
                + "from a superclass or interface.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new MissingOverride.MissingOverrideVisitor();
    }

    private final class MissingOverrideVisitor extends JavaIsoVisitor<ExecutionContext> {

        private static final String OVERRIDE = "Override";

        private Path sourcePath;

        @Override
        public J.CompilationUnit visitCompilationUnit(
                J.CompilationUnit cu, ExecutionContext executionContext) {
            this.sourcePath = cu.getSourcePath();
            return super.visitCompilationUnit(cu, executionContext);
        }

        @Override
        public J.MethodDeclaration visitMethodDeclaration(J.MethodDeclaration method,
            ExecutionContext executionContext) {
            J.MethodDeclaration newMethod = method;

            final boolean hasOverride = newMethod.getLeadingAnnotations().stream()
                    .anyMatch(annotation -> OVERRIDE.equals(annotation.getSimpleName()));

            if (!hasOverride && isAtViolationLocation(newMethod)) {

                final J.Identifier overrideIdentifier = new J.Identifier(
                    Tree.randomId(),
                    Space.EMPTY,
                    Markers.EMPTY,
                    null,
                    OVERRIDE,
                    JavaType.buildType("@java.lang.Override"),
                    null
                );

                J.Annotation overrideAnnotation = new J.Annotation(
                    Tree.randomId(),
                    Space.EMPTY,
                    Markers.EMPTY,
                    overrideIdentifier,
                    null
                );

                final String newIndent = "\n" + newMethod.getPrefix().getIndent();
                final List<J.Annotation> annotations =
                    new ArrayList<>(newMethod.getLeadingAnnotations());
                if (!annotations.isEmpty()) {
                    overrideAnnotation = overrideAnnotation
                            .withPrefix(Space.format(newIndent));
                }

                annotations.add(overrideAnnotation);
                final List<J.Modifier> modifiers = new ArrayList<>(newMethod.getModifiers());

                if (!modifiers.isEmpty()) {
                    final J.Modifier newModifier = modifiers.get(0)
                                                .withPrefix(Space.format(newIndent));
                    modifiers.set(0, newModifier);
                    newMethod = newMethod.withModifiers(modifiers);
                }
                else {
                    TypeTree typeTree = newMethod.getReturnTypeExpression();
                    typeTree = typeTree.withPrefix(Space.format(newIndent));
                    newMethod = newMethod.withReturnTypeExpression(typeTree);
                }

                newMethod = newMethod.withLeadingAnnotations(annotations);
            }
            return super.visitMethodDeclaration(newMethod, executionContext);
        }

        public boolean isAtViolationLocation(J.MethodDeclaration methodDeclaration) {
            final J.CompilationUnit cursor = getCursor().firstEnclosing(J.CompilationUnit.class);

            final int line = PositionHelper
                                .computeLinePosition(cursor, methodDeclaration, getCursor());
            return violations.stream().anyMatch(violation -> {
                return violation.getLine() == line
                        && violation.getFilePath().endsWith(sourcePath);
            });
        }
    }
}
