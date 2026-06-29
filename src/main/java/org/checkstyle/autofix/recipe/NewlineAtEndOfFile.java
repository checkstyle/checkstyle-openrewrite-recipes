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
import org.checkstyle.autofix.parser.CheckConfiguration;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.format.AutodetectGeneralFormatStyle;
import org.openrewrite.java.tree.Comment;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JavaSourceFile;
import org.openrewrite.java.tree.Space;
import org.openrewrite.style.GeneralFormatStyle;
import org.openrewrite.style.Style;

public class NewlineAtEndOfFile extends Recipe {

    private final CheckConfiguration config;

    public NewlineAtEndOfFile(CheckConfiguration config) {
        this.config = config;
    }

    @Override
    public String getDisplayName() {
        return "End files with a single newline";
    }

    @Override
    public String getDescription() {
        return "Some tools work better when files end with an empty line.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        final String lineSeparator = config.getProperty("lineSeparator");
        return new NewLineAtEndOfFileVisitor(lineSeparator);
    }

    private static class NewLineAtEndOfFileVisitor extends JavaIsoVisitor<ExecutionContext> {

        private static final String LF = "lf";
        private static final String CRLF = "crlf";
        private static final String CR = "cr";
        private static final String LF_CR_CRLF = "lf_cr_crlf";

        private final String lineSeparatorConfig;

        NewLineAtEndOfFileVisitor(String lineSeparatorConfig) {
            if (lineSeparatorConfig == null) {
                this.lineSeparatorConfig = LF_CR_CRLF;
            }
            else {
                this.lineSeparatorConfig = lineSeparatorConfig.toLowerCase();
            }
        }

        @Override
        public J.CompilationUnit visitCompilationUnit(
                J.CompilationUnit compUnit, ExecutionContext executionContext) {
            J.CompilationUnit result = compUnit;

            final boolean hasMarker = result.getMarkers()
                    .findAll(CheckstyleViolationMarker.class).stream()
                    .anyMatch(marker -> marker.isFor(CheckFullName.NEWLINE_AT_END_OF_FILE));

            if (hasMarker) {
                final String expectedLineEnding = determineLineEnding(result);
                result = result.withEof(
                        buildNewEof(result.getEof(), expectedLineEnding));
            }

            return result;
        }

        private static Space buildNewEof(Space eof,
                String expectedLineEnding) {
            final Space result;
            final List<Comment> comments = eof.getComments();
            if (comments.isEmpty()) {
                result = eof.withWhitespace(expectedLineEnding);
            }
            else {
                final int lastIndex = comments.size() - 1;
                final Comment last = comments.get(lastIndex);
                final Comment newLast = last.withSuffix(expectedLineEnding);
                if (last == newLast) {
                    result = eof;
                }
                else {
                    final List<Comment> updated = new ArrayList<>(comments);
                    updated.set(lastIndex, newLast);
                    result = eof.withComments(updated);
                }
            }
            return result;
        }

        private String determineLineEnding(JavaSourceFile sourceFile) {
            return switch (lineSeparatorConfig) {
                case LF -> "\n";
                case CRLF -> "\r\n";
                case CR -> "\r";
                case LF_CR_CRLF -> getAutodetectedLineEnding(sourceFile);
                default -> {
                    throw new IllegalStateException("Unexpected value: " + lineSeparatorConfig);
                }
            };
        }

        private String getAutodetectedLineEnding(JavaSourceFile sourceFile) {
            final GeneralFormatStyle generalFormatStyle =
                    Style.from(GeneralFormatStyle.class, sourceFile, () -> {
                        return AutodetectGeneralFormatStyle
                                .autodetectGeneralFormatStyle(sourceFile);
                    });
            return generalFormatStyle.newLine();
        }

    }
}
