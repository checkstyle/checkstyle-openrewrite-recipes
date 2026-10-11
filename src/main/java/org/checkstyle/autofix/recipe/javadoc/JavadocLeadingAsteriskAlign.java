///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle-openrewrite-recipes: Automatically fix Checkstyle violations with OpenRewrite.
// Copyright (C) 2026 The Checkstyle OpenRewrite Recipes Authors
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

package org.checkstyle.autofix.recipe.javadoc;

import java.util.ArrayList;
import java.util.List;

import org.checkstyle.autofix.marker.checks.JavadocLeadingAsteriskAlignMarker;
import org.checkstyle.autofix.parser.CheckConfiguration;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.Comment;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.Space;
import org.openrewrite.java.tree.TextComment;

public class JavadocLeadingAsteriskAlign extends Recipe {

    private static final String TAB_WIDTH_PROPERTY = "tabWidth";
    private static final int DEFAULT_TAB_WIDTH = 8;

    private final int tabWidth;

    public JavadocLeadingAsteriskAlign(CheckConfiguration config) {
        this.tabWidth = getTabWidth(config);
    }

    @Override
    public String getDisplayName() {
        return "JavadocLeadingAsteriskAlign recipe";
    }

    @Override
    public String getDescription() {
        return "Fixes Checkstyle JavadocLeadingAsteriskAlign violations.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new JavadocLeadingAsteriskAlignVisitor(tabWidth);
    }

    private static int getTabWidth(CheckConfiguration config) {
        final int result;
        if (config != null && config.getProperty(TAB_WIDTH_PROPERTY) != null) {
            result = Integer.parseInt(config.getProperty(TAB_WIDTH_PROPERTY));
        }
        else {
            result = DEFAULT_TAB_WIDTH;
        }
        return result;
    }

    private static final class JavadocLeadingAsteriskAlignVisitor
            extends JavaIsoVisitor<ExecutionContext> {

        private static final String JAVADOC_START = "/**";
        private static final String LF = "\n";
        private static final String SPACE = " ";
        private static final char LF_CHAR = '\n';
        private static final char TAB_CHAR = '\t';
        private static final char ASTERISK_CHAR = '*';

        private final int tabWidth;

        private JavadocLeadingAsteriskAlignVisitor(int tabWidth) {
            this.tabWidth = tabWidth;
        }

        @Override
        public J.CompilationUnit visitCompilationUnit(
                J.CompilationUnit compUnit, ExecutionContext executionContext) {
            final boolean hasMarker = compUnit.getMarkers()
                    .findFirst(JavadocLeadingAsteriskAlignMarker.class).isPresent();

            final J.CompilationUnit result;
            if (hasMarker) {
                result = super.visitCompilationUnit(compUnit, executionContext);
            }
            else {
                result = compUnit;
            }
            return result;
        }

        @Override
        public Space visitSpace(
                Space space, Space.Location loc, ExecutionContext executionContext) {
            return alignJavadocComments(space);
        }

        private Space alignJavadocComments(Space space) {
            boolean changed = false;
            final List<Comment> comments = space.getComments();
            final List<Comment> newComments = new ArrayList<>();

            String prevIndent = extractIndent(space.getWhitespace());

            for (Comment comment : comments) {
                final String commentText = comment.printComment(getCursor());
                if (isJavadoc(commentText)) {
                    final String aligned = alignComment(commentText, prevIndent);
                    if (!aligned.equals(commentText)) {
                        final String innerText = extractInnerText(aligned);
                        final Comment updatedComment = new TextComment(
                                true,
                                innerText,
                                comment.getSuffix(),
                                comment.getMarkers()
                        );
                        newComments.add(updatedComment);
                        changed = true;
                    }
                    else {
                        newComments.add(comment);
                    }
                }
                else {
                    newComments.add(comment);
                }
                prevIndent = extractIndent(comment.getSuffix());
            }

            final Space result;
            if (changed) {
                result = space.withComments(newComments);
            }
            else {
                result = space;
            }
            return result;
        }

        private static boolean isJavadoc(String commentText) {
            return commentText.startsWith(JAVADOC_START);
        }

        private static String extractInnerText(String commentText) {
            return commentText.substring(2, commentText.length() - 2);
        }

        private static String extractIndent(String whitespace) {
            return whitespace.substring(whitespace.lastIndexOf(LF_CHAR) + 1);
        }

        private String alignComment(String commentText, String baseIndent) {
            final String[] lines = commentText.split(LF, -1);
            final int expectedCol = computeExpandedColumn(baseIndent, tabWidth) + 2;
            final String expectedIndent = baseIndent + SPACE;

            for (int index = 1; index < lines.length; index++) {
                final String line = lines[index];
                final int asteriskIndex = findLeadingAsteriskIndex(line);
                if (asteriskIndex != -1) {
                    final String leadingWs = line.substring(0, asteriskIndex);
                    final int actualCol = computeExpandedColumn(leadingWs, tabWidth) + 1;
                    if (actualCol != expectedCol) {
                        lines[index] = expectedIndent + line.substring(asteriskIndex);
                    }
                }
            }

            return String.join(LF, lines);
        }

        private static int findLeadingAsteriskIndex(String line) {
            int result = -1;
            for (int index = 0; index < line.length(); index++) {
                final char chr = line.charAt(index);
                if (chr == ASTERISK_CHAR) {
                    result = index;
                    break;
                }
                else if (!Character.isWhitespace(chr)) {
                    break;
                }
            }
            return result;
        }

        private static int computeExpandedColumn(String linePrefix, int tabWidth) {
            int col = 0;
            for (int index = 0; index < linePrefix.length(); index++) {
                final char chr = linePrefix.charAt(index);
                if (chr == TAB_CHAR) {
                    col += tabWidth - (col % tabWidth);
                }
                else {
                    col++;
                }
            }
            return col;
        }

    }

}
