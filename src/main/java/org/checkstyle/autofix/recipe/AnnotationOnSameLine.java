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
import java.util.List;

import org.checkstyle.autofix.CheckFullName;
import org.checkstyle.autofix.marker.CheckstyleViolationMarker;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.Comment;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.Space;
import org.openrewrite.java.tree.TextComment;

public class AnnotationOnSameLine extends Recipe {

    private static final String SINGLE_SPACE = " ";

    @Override
    public String getDisplayName() {
        return "Annotation on same line";
    }

    @Override
    public String getDescription() {
        return "Checks whether annotation is on the same line with its target element.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new AnnotationModifierVisitor();
    }

    private final class AnnotationModifierVisitor extends JavaIsoVisitor<ExecutionContext> {

        private static Space forceSingleSpace(Space space) {
            final List<Comment> updatedComments = new ArrayList<>();
            boolean hasUpdatedComments = false;

            for (Comment originalComment : space.getComments()) {
                final TextComment updatedComment = ((TextComment) originalComment
                        .withSuffix(SINGLE_SPACE))
                        .withMultiline(true)
                        .withText(normalizeCommentText(((TextComment) originalComment).getText()));
                updatedComments.add(updatedComment);
                if (updatedComment != originalComment) {
                    hasUpdatedComments = true;
                }
            }

            Space updatedSpace = space.withWhitespace(SINGLE_SPACE);
            if (hasUpdatedComments) {
                updatedSpace = updatedSpace.withComments(updatedComments);
            }
            return updatedSpace;
        }

        private static String normalizeCommentText(String text) {
            return text.stripTrailing() + SINGLE_SPACE;
        }

        @Override
        public J.ClassDeclaration visitClassDeclaration(J.ClassDeclaration classDecl,
                                                      ExecutionContext executionContext) {
            J.ClassDeclaration decl = super.visitClassDeclaration(classDecl, executionContext);
            decl = decl.withLeadingAnnotations(mapAnnotations(decl.getLeadingAnnotations()));

            if (lastAnnotationViolates(decl.getLeadingAnnotations())) {
                if (decl.getModifiers().isEmpty()) {
                    decl = decl.getPadding().withKind(decl.getPadding().getKind().withPrefix(
                            forceSingleSpace(decl.getPadding().getKind().getPrefix())));
                }
                else {
                    decl = decl.withModifiers(withUpdatedFirstModifierPrefix(decl.getModifiers()));
                }
            }
            return decl;
        }

        @Override
        public J.MethodDeclaration visitMethodDeclaration(J.MethodDeclaration method,
                                                          ExecutionContext executionContext) {
            J.MethodDeclaration decl = super.visitMethodDeclaration(method, executionContext);
            decl = decl.withLeadingAnnotations(mapAnnotations(decl.getLeadingAnnotations()));

            if (lastAnnotationViolates(decl.getLeadingAnnotations())) {
                if (!decl.getModifiers().isEmpty()) {
                    decl = decl.withModifiers(withUpdatedFirstModifierPrefix(decl.getModifiers()));
                }
                else if (decl.getReturnTypeExpression() != null) {
                    decl = decl.withReturnTypeExpression(decl.getReturnTypeExpression()
                            .withPrefix(forceSingleSpace(decl.getReturnTypeExpression()
                                    .getPrefix())));
                }
                else {
                    decl = decl.withName(decl.getName().withPrefix(
                            forceSingleSpace(decl.getName().getPrefix())));
                }
            }
            return decl;
        }

        @Override
        public J.VariableDeclarations visitVariableDeclarations(J.VariableDeclarations multiVar,
                                                                ExecutionContext executionContext) {
            J.VariableDeclarations decl =
                    super.visitVariableDeclarations(multiVar, executionContext);
            decl = decl.withLeadingAnnotations(mapAnnotations(decl.getLeadingAnnotations()));

            if (lastAnnotationViolates(decl.getLeadingAnnotations())) {
                if (!decl.getModifiers().isEmpty()) {
                    decl = decl.withModifiers(withUpdatedFirstModifierPrefix(decl.getModifiers()));
                }
                else {
                    decl = decl.withTypeExpression(decl.getTypeExpression()
                            .withPrefix(forceSingleSpace(decl.getTypeExpression().getPrefix())));
                }
            }
            return decl;
        }

        private boolean hasMarker(J.Annotation anno) {
            return anno.getMarkers().findAll(CheckstyleViolationMarker.class).stream()
                    .anyMatch(marker -> marker.isFor(CheckFullName.ANNOTATION_ON_SAME_LINE));
        }

        private boolean lastAnnotationViolates(List<J.Annotation> annos) {
            boolean result = false;
            if (!annos.isEmpty()) {
                result = hasMarker(annos.get(annos.size() - 1));
            }
            return result;
        }

        private J.Annotation fixConsecutiveAnnotation(List<J.Annotation> annos,
                                                      int index, J.Annotation anno) {
            J.Annotation result = anno;
            if (index > 0) {
                if (hasMarker(annos.get(index - 1))) {
                    result = anno.withPrefix(forceSingleSpace(anno.getPrefix()));
                }
            }
            return result;
        }

        private List<J.Annotation> mapAnnotations(List<J.Annotation> annos) {
            final List<J.Annotation> updatedAnnos = new ArrayList<>();
            boolean hasUpdatedAnnotations = false;

            for (int index = 0; index < annos.size(); index++) {
                final J.Annotation originalAnno = annos.get(index);
                final J.Annotation newAnno = fixConsecutiveAnnotation(annos, index, originalAnno);
                updatedAnnos.add(newAnno);
                if (newAnno != originalAnno) {
                    hasUpdatedAnnotations = true;
                }
            }

            List<J.Annotation> result = annos;
            if (hasUpdatedAnnotations) {
                result = updatedAnnos;
            }
            return result;
        }

        private List<J.Modifier> withUpdatedFirstModifierPrefix(List<J.Modifier> mods) {
            final J.Modifier firstModifier = mods.get(0);
            final J.Modifier updatedFirstModifier =
                    firstModifier.withPrefix(forceSingleSpace(firstModifier.getPrefix()));
            List<J.Modifier> result = mods;
            if (updatedFirstModifier != firstModifier) {
                final List<J.Modifier> updatedMods = new ArrayList<>(mods);
                updatedMods.set(0, updatedFirstModifier);
                result = updatedMods;
            }
            return result;
        }
    }
}
