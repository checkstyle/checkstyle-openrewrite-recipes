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
import java.util.function.UnaryOperator;

import org.checkstyle.autofix.parser.CheckConfiguration;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.Tree;
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

    private final List<CheckstyleViolation> violations;
    private final CheckConfiguration config;

    public NewlineAtEndOfFile(List<CheckstyleViolation> violations, CheckConfiguration config) {
        this.violations = violations;
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
        return new NewLineAtEndOfFileVisitor(violations, lineSeparator);
    }

    private static class NewLineAtEndOfFileVisitor extends JavaIsoVisitor<ExecutionContext> {
        private final List<CheckstyleViolation> violations;
        private final String lineSeparatorConfig;

        NewLineAtEndOfFileVisitor(List<CheckstyleViolation> violations,
                                         String lineSeparatorConfig) {
            this.violations = violations;
            this.lineSeparatorConfig = lineSeparatorConfig.toLowerCase();
        }

        @Override
        public J visit(Tree tree, ExecutionContext executionContext) {
            J result = (J) tree;

            if (tree instanceof JavaSourceFile sourceFile) {

                final Path filePath = sourceFile.getSourcePath().toAbsolutePath();

                if (hasViolation(filePath)) {
                    final String expectedLineEnding = determineLineEnding(sourceFile);

                    final Space eof = sourceFile.getEof();
                    final String lastWhitespace = eof.getLastWhitespace();

                    if (!expectedLineEnding.equals(lastWhitespace)) {
                        final List<Comment> comments = eof.getComments();
                        if (comments.isEmpty()) {
                            result = sourceFile.withEof(Space.format(expectedLineEnding));
                        }
                        else {
                            result = sourceFile.withEof(sourceFile.getEof().withComments(mapLast(
                                    comments, comment -> comment.withSuffix(expectedLineEnding))));
                        }
                    }
                }
            }

            return result;
        }

        private String determineLineEnding(JavaSourceFile sourceFile) {
            return switch (lineSeparatorConfig) {
                case "lf" -> "\n";
                case "crlf" -> "\r\n";
                case "cr" -> "\r";
                case "system" -> System.lineSeparator();
                case "lf_cr_crlf" -> getAutodetectedLineEnding(sourceFile);
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

        private static List<Comment> mapLast(List<Comment> comments,
                                             UnaryOperator<Comment> mapper) {
            List<Comment> result = comments;

            if (comments != null && !comments.isEmpty()) {
                final int lastIndex = comments.size() - 1;
                final Comment last = comments.get(lastIndex);
                final Comment newLast = mapper.apply(last);

                if (last != newLast) {
                    result = new ArrayList<>(comments);
                    result.set(lastIndex, newLast);
                }
            }

            return result;
        }

        private boolean hasViolation(Path filePath) {
            return violations.stream()
                    .anyMatch(violation -> violation.getFilePath().endsWith(filePath));
        }
    }
}
