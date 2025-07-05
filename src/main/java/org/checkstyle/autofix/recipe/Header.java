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

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.Tree;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JavaSourceFile;
import org.openrewrite.java.tree.Space;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class Header extends Recipe {
    private static final String HEADER_PROPERTY = "header";
    private static final String HEADER_FILE_PROPERTY = "headerFile";
    private static final String IGNORE_LINES_PROPERTY = "ignoreLines";
    private static final String CHARSET_PROPERTY = "charset";

    private final List<CheckstyleViolation> violations;
    private final Configuration config;
    private final Charset charset;

    public Header(List<CheckstyleViolation> violations, Configuration config, Charset charset) {
        this.violations = violations;
        this.config = config;
        this.charset = charset;
    }

    @Override
    public String getDisplayName() {
        return "Header recipe";
    }

    @Override
    public String getDescription() {
        return "Adds headers to Java source files when missing.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        final String licenseHeader = extractLicenseHeader(config, charset);
        final List<Integer> ignoreLines = extractIgnoreLines(config);
        return new HeaderVisitor(violations, licenseHeader, ignoreLines);
    }

    private static String extractLicenseHeader(Configuration config, Charset charset) {
        final String header;
        try {
            if (hasProperty(config, HEADER_PROPERTY)) {
                header = config.getProperty(HEADER_PROPERTY);
            }
            else {
                final Charset charsetToUse;
                if (hasProperty(config, CHARSET_PROPERTY)) {
                    charsetToUse = Charset.forName(config.getProperty(CHARSET_PROPERTY));
                }
                else {
                    charsetToUse = charset;
                }
                final String headerFilePath = config.getProperty(HEADER_FILE_PROPERTY);
                header = Files.readString(Path.of(headerFilePath), charsetToUse);
            }
        }
        catch (CheckstyleException | IOException exception) {
            throw new IllegalArgumentException("Failed to extract header from config", exception);
        }
        return header;
    }

    private static List<Integer> extractIgnoreLines(Configuration config) {
        final List<Integer> ignoreLinesList;
        try {
            if (!hasProperty(config, IGNORE_LINES_PROPERTY)) {
                ignoreLinesList = new ArrayList<>();
            }
            else {
                final String ignoreLines = config.getProperty(IGNORE_LINES_PROPERTY);
                ignoreLinesList = Arrays.stream(ignoreLines.split(","))
                        .map(String::trim)
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
            }
        }
        catch (CheckstyleException exception) {
            throw new IllegalArgumentException(
                    "Failed to extract ignore lines from config", exception);
        }
        return ignoreLinesList;
    }

    private static boolean hasProperty(Configuration config, String propertyName) {
        return Arrays.asList(config.getPropertyNames()).contains(propertyName);
    }

    private static class HeaderVisitor extends JavaIsoVisitor<ExecutionContext> {
        private final List<CheckstyleViolation> violations;
        private final String licenseHeader;
        private final List<Integer> ignoreLines;

        HeaderVisitor(List<CheckstyleViolation> violations, String licenseHeader,
                      List<Integer> ignoreLines) {
            this.violations = violations;
            this.licenseHeader = licenseHeader;
            this.ignoreLines = ignoreLines;
        }

        @Override
        public J visit(Tree tree, ExecutionContext ctx) {
            J result = super.visit(tree, ctx);

            if (tree instanceof JavaSourceFile) {
                JavaSourceFile sourceFile = (JavaSourceFile) tree;
                final Path filePath = sourceFile.getSourcePath().toAbsolutePath();

                if (hasViolation(filePath)) {
                    final String currentHeader = extractCurrentHeader(sourceFile);
                    final String fixedHeader = fixHeaderLines(licenseHeader,
                                                                currentHeader, ignoreLines);

                    sourceFile = sourceFile.withPrefix(
                            Space.format(fixedHeader + System.lineSeparator()));
                }
                result = super.visit(sourceFile, ctx);
            }
            return result;
        }

        private String extractCurrentHeader(JavaSourceFile sourceFile) {
            return sourceFile.getComments().stream()
                    .map(comment -> comment.printComment(getCursor()))
                    .collect(Collectors.joining(System.lineSeparator()));
        }

        private static String fixHeaderLines(String licenseHeader,
                                             String currentHeader, List<Integer> ignoreLines) {
            final List<String> currentLines = Arrays
                    .stream(currentHeader.split(System.lineSeparator()))
                    .collect(Collectors.toList());
            final List<String> licenseLines = Arrays.stream(licenseHeader.split(
                    System.lineSeparator(), -1)).toList();

            final Set<Integer> ignoredLineNumbers = new HashSet<>(ignoreLines);

            for (int lineNumber = 1; lineNumber <= licenseLines.size(); lineNumber++) {
                final String expectedLine = licenseLines.get(lineNumber - 1);

                if (lineNumber <= currentLines.size()) {
                    if (!ignoredLineNumbers.contains(lineNumber)
                            && !expectedLine.equals(currentLines.get(lineNumber - 1))) {
                        currentLines.set(lineNumber - 1, expectedLine);
                    }
                }
                else {
                    currentLines.add(expectedLine);
                }
            }

            return String.join(System.lineSeparator(), currentLines);
        }

        private boolean hasViolation(Path filePath) {
            return violations.removeIf(violation -> {
                return filePath.equals(Path.of(violation.getFileName()).toAbsolutePath());
            });
        }
    }
}
