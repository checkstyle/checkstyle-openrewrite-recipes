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
import java.util.stream.IntStream;

import org.checkstyle.autofix.CheckFullName;
import org.checkstyle.autofix.marker.CheckstyleViolationMarker;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.Tree;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.Comment;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JavaType;
import org.openrewrite.java.tree.Space;
import org.openrewrite.java.tree.TextComment;
import org.openrewrite.java.tree.TypeTree;
import org.openrewrite.marker.Markers;

/**
 * Fixes Checkstyle MissingDeprecated violations by ensuring that both
 * the @Deprecated
 * annotation and the {@code @deprecated} Javadoc tag are present when either
 * one is present.
 */
public class MissingDeprecated extends Recipe {

    @Override
    public String getDisplayName() {
        return "Missing Deprecated annotation or Javadoc tag";
    }

    @Override
    public String getDescription() {
        return "Add @Deprecated annotation or {@code @deprecated} Javadoc tag "
                + "when either is missing.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new MissingDeprecatedVisitor();
    }

    private static final class MissingDeprecatedVisitor extends JavaIsoVisitor<ExecutionContext> {

        private static final String DEPRECATED = "Deprecated";
        private static final String NEWLINE = "\n";
        private static final String SPACE = " ";

        @Override
        public J.MethodDeclaration visitMethodDeclaration(J.MethodDeclaration method,
                ExecutionContext executionContext) {
            J.MethodDeclaration result = method;

            final boolean hasMarker = hasMissingDeprecatedMarker(result);

            if (hasMarker) {
                final boolean hasAnnotation = hasDeprecatedAnnotation(
                        result.getLeadingAnnotations());
                final boolean hasJavadocTag = hasDeprecatedJavadocTag(result.getPrefix());

                if (!hasAnnotation) {
                    result = addDeprecatedAnnotationToMethod(result);
                }
                if (!hasJavadocTag) {
                    result = addDeprecatedJavadocTagToMethod(result);
                }
            }
            return super.visitMethodDeclaration(result, executionContext);
        }

        @Override
        public J.VariableDeclarations visitVariableDeclarations(
                J.VariableDeclarations multiVariable,
                ExecutionContext executionContext) {
            J.VariableDeclarations result = multiVariable;

            final boolean hasMarker = hasMissingDeprecatedMarker(result);

            if (hasMarker) {
                final boolean hasAnnotation = hasDeprecatedAnnotation(
                        result.getLeadingAnnotations());
                final boolean hasJavadocTag = hasDeprecatedJavadocTag(result.getPrefix());

                if (!hasAnnotation) {
                    result = addDeprecatedAnnotationToVariable(result);
                }
                if (!hasJavadocTag) {
                    result = addDeprecatedJavadocTagToVariable(result);
                }
            }
            return super.visitVariableDeclarations(result, executionContext);
        }

        @Override
        public J.ClassDeclaration visitClassDeclaration(J.ClassDeclaration classDecl,
                ExecutionContext executionContext) {
            J.ClassDeclaration result = classDecl;

            final boolean hasMarker = hasMissingDeprecatedMarker(result);

            if (hasMarker) {
                final boolean hasAnnotation = hasDeprecatedAnnotation(
                        result.getLeadingAnnotations());
                final boolean hasJavadocTag = hasDeprecatedJavadocTag(result.getPrefix());

                if (!hasAnnotation) {
                    result = addDeprecatedAnnotationToClass(result);
                }
                if (!hasJavadocTag) {
                    result = addDeprecatedJavadocTagToClass(result);
                }
            }
            return super.visitClassDeclaration(result, executionContext);
        }

        private boolean hasMissingDeprecatedMarker(Tree tree) {
            return tree.getMarkers()
                    .findAll(CheckstyleViolationMarker.class).stream()
                    .anyMatch(marker -> marker.isFor(CheckFullName.MISSING_DEPRECATED));
        }

        private boolean hasDeprecatedAnnotation(List<J.Annotation> annotations) {
            return annotations.stream()
                    .anyMatch(annotation -> DEPRECATED.equals(annotation.getSimpleName()));
        }

        private boolean hasDeprecatedJavadocTag(Space prefix) {
            return prefix.getComments().stream()
                    .anyMatch(comment -> {
                        return comment.printComment(getCursor()).contains("@deprecated");
                    });
        }

        private J.Annotation createDeprecatedAnnotation() {
            final J.Identifier deprecatedIdentifier = new J.Identifier(
                    Tree.randomId(),
                    Space.EMPTY,
                    Markers.EMPTY,
                    null,
                    DEPRECATED,
                    JavaType.buildType("@java.lang.Deprecated"),
                    null);

            return new J.Annotation(
                    Tree.randomId(),
                    Space.EMPTY,
                    Markers.EMPTY,
                    deprecatedIdentifier,
                    null);
        }

        private J.MethodDeclaration addDeprecatedAnnotationToMethod(
                J.MethodDeclaration method) {
            J.MethodDeclaration result = method;
            J.Annotation deprecatedAnnotation = createDeprecatedAnnotation();

            final String newIndent = NEWLINE + result.getPrefix().getIndent();
            final List<J.Annotation> annotations = new ArrayList<>(result.getLeadingAnnotations());
            if (!annotations.isEmpty()) {
                deprecatedAnnotation = deprecatedAnnotation
                        .withPrefix(Space.format(newIndent));
            }

            annotations.add(deprecatedAnnotation);
            final List<J.Modifier> modifiers = new ArrayList<>(result.getModifiers());

            if (!modifiers.isEmpty()) {
                final J.Modifier newModifier = modifiers.get(0)
                        .withPrefix(Space.format(newIndent));
                modifiers.set(0, newModifier);
                result = result.withModifiers(modifiers);
            }
            else {
                TypeTree typeTree = result.getReturnTypeExpression();
                if (typeTree != null) {
                    typeTree = typeTree.withPrefix(Space.format(newIndent));
                    result = result.withReturnTypeExpression(typeTree);
                }
                else {
                    result = result.withName(
                            result.getName().withPrefix(Space.format(newIndent)));
                }
            }

            return result.withLeadingAnnotations(annotations);
        }

        private J.VariableDeclarations addDeprecatedAnnotationToVariable(
                J.VariableDeclarations variable) {
            J.VariableDeclarations result = variable;
            J.Annotation deprecatedAnnotation = createDeprecatedAnnotation();

            final String newIndent = NEWLINE + result.getPrefix().getIndent();
            final List<J.Annotation> annotations = new ArrayList<>(result.getLeadingAnnotations());
            if (!annotations.isEmpty()) {
                deprecatedAnnotation = deprecatedAnnotation
                        .withPrefix(Space.format(newIndent));
            }

            annotations.add(deprecatedAnnotation);
            final List<J.Modifier> modifiers = new ArrayList<>(result.getModifiers());

            if (!modifiers.isEmpty()) {
                final J.Modifier newModifier = modifiers.get(0)
                        .withPrefix(Space.format(newIndent));
                modifiers.set(0, newModifier);
                result = result.withModifiers(modifiers);
            }
            else {
                result = result.withTypeExpression(
                        result.getTypeExpression()
                                .withPrefix(Space.format(newIndent)));
            }

            return result.withLeadingAnnotations(annotations);
        }

        private J.ClassDeclaration addDeprecatedAnnotationToClass(
                J.ClassDeclaration classDecl) {
            J.ClassDeclaration result = classDecl;
            J.Annotation deprecatedAnnotation = createDeprecatedAnnotation();

            final String newIndent = NEWLINE + result.getPrefix().getIndent();
            final List<J.Annotation> annotations = new ArrayList<>(result.getLeadingAnnotations());
            if (!annotations.isEmpty()) {
                deprecatedAnnotation = deprecatedAnnotation
                        .withPrefix(Space.format(newIndent));
            }

            annotations.add(deprecatedAnnotation);
            final List<J.Modifier> modifiers = new ArrayList<>(result.getModifiers());

            if (!modifiers.isEmpty()) {
                final J.Modifier newModifier = modifiers.get(0)
                        .withPrefix(Space.format(newIndent));
                modifiers.set(0, newModifier);
                result = result.withModifiers(modifiers);
            }
            else {
                result = result.getPadding().withKind(
                        result.getPadding().getKind()
                                .withPrefix(Space.format(newIndent)));
            }

            return result.withLeadingAnnotations(annotations);
        }

        private J.MethodDeclaration addDeprecatedJavadocTagToMethod(
                J.MethodDeclaration method) {
            final Space updatedPrefix = addDeprecatedTagToJavadoc(
                    method.getPrefix(), method.getPrefix().getIndent());
            return method.withPrefix(updatedPrefix);
        }

        private J.VariableDeclarations addDeprecatedJavadocTagToVariable(
                J.VariableDeclarations variable) {
            final Space updatedPrefix = addDeprecatedTagToJavadoc(
                    variable.getPrefix(), variable.getPrefix().getIndent());
            return variable.withPrefix(updatedPrefix);
        }

        private J.ClassDeclaration addDeprecatedJavadocTagToClass(
                J.ClassDeclaration classDecl) {
            final Space updatedPrefix = addDeprecatedTagToJavadoc(
                    classDecl.getPrefix(), classDecl.getPrefix().getIndent());
            return classDecl.withPrefix(updatedPrefix);
        }

        private Space addDeprecatedTagToJavadoc(Space prefix, String nodeIndent) {
            final List<Comment> comments = new ArrayList<>(prefix.getComments());

            return IntStream.range(0, comments.size())
                    .filter(index -> isJavadocComment(comments.get(index)))
                    .mapToObj(index -> updateJavadocComment(prefix, comments, nodeIndent, index))
                    .findFirst()
                    .orElse(prefix);
        }

        private boolean isJavadocComment(Comment comment) {
            return comment.printComment(getCursor()).startsWith("/**");
        }

        private Space updateJavadocComment(
                Space prefix, List<Comment> comments, String nodeIndent, int index) {
            final Comment comment = comments.get(index);
            final String printed = comment.printComment(getCursor());
            final String innerText = printed.substring(2, printed.length() - 2);
            final String updatedText = appendDeprecatedTag(innerText, nodeIndent);

            final Comment updatedComment = new TextComment(
                    true,
                    updatedText,
                    comment.getSuffix(),
                    comment.getMarkers());
            comments.set(index, updatedComment);
            return prefix.withComments(comments);
        }

        private String appendDeprecatedTag(String javadocText, String nodeIndent) {
            final String indent = extractIndent(javadocText, nodeIndent);
            final String deprecatedLine = NEWLINE + indent + "* @deprecated";

            String result = javadocText;
            final int lastNewline = javadocText.lastIndexOf('\n');
            if (lastNewline >= 0) {
                final String afterLastNewline = javadocText.substring(lastNewline);
                if (!afterLastNewline.trim().isEmpty()) {
                    result = javadocText + deprecatedLine + NEWLINE + indent;
                }
                else {
                    result = javadocText.substring(0, lastNewline)
                            + deprecatedLine + afterLastNewline;
                }
            }
            else {
                result = javadocText + deprecatedLine + NEWLINE + indent;
            }
            return result;
        }

        private String extractIndent(String javadocText, String nodeIndent) {
            String result = nodeIndent + SPACE;
            final int firstNewline = javadocText.indexOf('\n');
            if (firstNewline >= 0) {
                final String afterNewline = javadocText.substring(firstNewline + 1);
                final int asteriskIndex = afterNewline.indexOf('*');
                if (asteriskIndex >= 0) {
                    result = afterNewline.substring(0, asteriskIndex);
                }
            }
            return result;
        }
    }
}
