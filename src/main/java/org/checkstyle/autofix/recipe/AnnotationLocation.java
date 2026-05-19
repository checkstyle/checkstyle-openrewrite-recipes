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
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.Space;

/**
 * Fixes Checkstyle AnnotationLocation violations by moving annotations
 * to their own line with proper indentation.
 */
public class AnnotationLocation extends Recipe {

    private static final String NEWLINE = "\n";
    private static final String DEFAULT_INDENT = "    ";

    private final List<CheckstyleViolation> violations;

    public AnnotationLocation(final List<CheckstyleViolation> violations) {
        this.violations = violations;
    }

    @Override
    public String getDisplayName() {
        return "AnnotationLocation recipe";
    }

    @Override
    public String getDescription() {
        return "Move annotations to their own line with proper indentation.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new AnnotationLocationVisitor();
    }

    private final class AnnotationLocationVisitor
            extends JavaIsoVisitor<ExecutionContext> {

        private Path sourcePath;
        private J.CompilationUnit currentCompilationUnit;

        @Override
        public J.CompilationUnit visitCompilationUnit(
                J.CompilationUnit cu, ExecutionContext executionContext) {
            this.sourcePath = cu.getSourcePath().toAbsolutePath();
            this.currentCompilationUnit = cu;
            return super.visitCompilationUnit(cu, executionContext);
        }

        @Override
        public J.MethodDeclaration visitMethodDeclaration(
                J.MethodDeclaration method, ExecutionContext executionContext) {

            J.MethodDeclaration result = super.visitMethodDeclaration(method, executionContext);

            if (isAtViolationLocation(result)) {
                result = fixAnnotations(result);
            }

            return result;
        }

        @Override
        public J.VariableDeclarations visitVariableDeclarations(
                J.VariableDeclarations varDecl, ExecutionContext executionContext) {

            J.VariableDeclarations result = super.visitVariableDeclarations(
                    varDecl, executionContext);

            if (isAtViolationLocation(result)) {
                result = fixAnnotations(result);
            }

            return result;
        }

        @Override
        public J.ClassDeclaration visitClassDeclaration(
                J.ClassDeclaration classDecl, ExecutionContext executionContext) {

            J.ClassDeclaration result = super.visitClassDeclaration(classDecl, executionContext);

            if (isAtViolationLocation(result)) {
                result = fixAnnotations(result);
            }

            return result;
        }

        private boolean isAtViolationLocation(J element) {
            final int line = PositionHelper.computeLinePosition(
                    currentCompilationUnit, element, getCursor());

            return violations.removeIf(violation -> {
                final Path absolutePath = violation.getFilePath().toAbsolutePath();
                return violation.getLine() == line
                        && absolutePath.equals(sourcePath);
            });
        }

        private J.MethodDeclaration fixAnnotations(J.MethodDeclaration method) {
            final String indent = getIndentation(method);

            J.MethodDeclaration result = method;

            final List<J.Annotation> annotations = method.getLeadingAnnotations();
            final List<J.Annotation> fixedAnnotations = fixAnnotationList(annotations, indent);
            if (fixedAnnotations != annotations) {
                result = method.withLeadingAnnotations(fixedAnnotations);
            }

            if (!result.getModifiers().isEmpty()) {
                final J.Modifier firstModifier = result.getModifiers().get(0);
                if (!firstModifier.getPrefix().getWhitespace().contains(NEWLINE)) {
                    final List<J.Modifier> mods = new ArrayList<>(result.getModifiers());
                    mods.set(0, firstModifier.withPrefix(Space.format(NEWLINE + indent)));
                    result = result.withModifiers(mods);
                }
            }
            else if (result.getReturnTypeExpression() != null) {
                final Space retTypePrefix = result.getReturnTypeExpression().getPrefix();
                if (!retTypePrefix.getWhitespace().contains(NEWLINE)) {
                    result = result.withReturnTypeExpression(
                            result.getReturnTypeExpression()
                                    .withPrefix(Space.format(NEWLINE + indent)));
                }
            }

            return result;
        }

        private J.VariableDeclarations fixAnnotations(J.VariableDeclarations varDecl) {
            final String indent = getIndentation(varDecl);

            J.VariableDeclarations result = varDecl;

            final List<J.Annotation> annotations = varDecl.getLeadingAnnotations();
            final List<J.Annotation> fixedAnnotations = fixAnnotationList(annotations, indent);
            if (fixedAnnotations != annotations) {
                result = varDecl.withLeadingAnnotations(fixedAnnotations);
            }

            if (!result.getModifiers().isEmpty()) {
                final J.Modifier firstModifier = result.getModifiers().get(0);
                if (!firstModifier.getPrefix().getWhitespace().contains(NEWLINE)) {
                    final List<J.Modifier> mods = new ArrayList<>(result.getModifiers());
                    mods.set(0, firstModifier.withPrefix(Space.format(NEWLINE + indent)));
                    result = result.withModifiers(mods);
                }
            }
            else if (result.getTypeExpression() != null) {
                final Space typePrefix = result.getTypeExpression().getPrefix();
                if (!typePrefix.getWhitespace().contains(NEWLINE)) {
                    result = result.withTypeExpression(
                            result.getTypeExpression()
                                    .withPrefix(Space.format(NEWLINE + indent)));
                }
            }

            return result;
        }

        private J.ClassDeclaration fixAnnotations(J.ClassDeclaration classDecl) {
            final String indent = getIndentation(classDecl);

            J.ClassDeclaration result = classDecl;

            final List<J.Annotation> annotations = classDecl.getLeadingAnnotations();
            final List<J.Annotation> fixedAnnotations = fixAnnotationList(annotations, indent);
            if (fixedAnnotations != annotations) {
                result = classDecl.withLeadingAnnotations(fixedAnnotations);
            }

            if (!result.getModifiers().isEmpty()) {
                final J.Modifier firstModifier = result.getModifiers().get(0);
                if (!firstModifier.getPrefix().getWhitespace().contains(NEWLINE)) {
                    final List<J.Modifier> mods = new ArrayList<>(result.getModifiers());
                    mods.set(0, firstModifier.withPrefix(Space.format(NEWLINE + indent)));
                    result = result.withModifiers(mods);
                }
            }
            else {
                final Space kindPrefix = result.getPadding().getKind().getPrefix();
                if (!kindPrefix.getWhitespace().contains(NEWLINE)) {
                    result = result.getPadding()
                            .withKind(result.getPadding()
                                    .getKind()
                                    .withPrefix(Space.format(NEWLINE + indent)));
                }
            }

            return result;
        }

        private List<J.Annotation> fixAnnotationList(
                final List<J.Annotation> annotations, final String indent) {
            List<J.Annotation> fixedAnnotations = null;

            for (int index = 0; index < annotations.size(); index++) {
                J.Annotation ann = annotations.get(index);
                final Space prefix = ann.getPrefix();

                if (index > 0 && !prefix.getWhitespace().contains(NEWLINE)) {
                    if (fixedAnnotations == null) {
                        fixedAnnotations = new ArrayList<>(annotations.subList(0, index));
                    }
                    ann = ann.withPrefix(Space.format(NEWLINE + indent));
                }

                if (fixedAnnotations != null) {
                    fixedAnnotations.add(ann);
                }
            }

            final List<J.Annotation> result;
            if (fixedAnnotations != null) {
                result = fixedAnnotations;
            }
            else {
                result = annotations;
            }
            return result;
        }

        private String getIndentation(final J element) {
            final Space prefix = getElementPrefix(element);
            String indentation = DEFAULT_INDENT;
            if (prefix != null) {
                final String whitespace = prefix.getWhitespace();
                final int lastNewline = whitespace.lastIndexOf('\n');
                if (lastNewline >= 0) {
                    indentation = whitespace.substring(lastNewline + 1);
                }
            }
            return indentation;
        }

        private Space getElementPrefix(final J element) {
            Space prefix = null;
            if (element instanceof J.MethodDeclaration method) {
                prefix = method.getPrefix();
            }
            else if (element instanceof J.VariableDeclarations varDecl) {
                prefix = varDecl.getPrefix();
            }
            else if (element instanceof J.ClassDeclaration classDecl) {
                prefix = classDecl.getPrefix();
            }
            return prefix;
        }
    }
}
