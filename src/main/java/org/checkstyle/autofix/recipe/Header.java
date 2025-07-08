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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

    private static final String HEADER_PROP = "header";
    private static final String HEADER_FILE_PROP = "headerFile";

    private List<CheckstyleViolation> violations;
    private Configuration headerConfig;

    public Header(List<CheckstyleViolation> violations, Configuration headerConfig) {
        this.violations = violations;
        this.headerConfig = headerConfig;
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
        final String licenseHeader = getLicenseHeader(headerConfig);
        return new HeaderVisitor(violations, licenseHeader);
    }

    private static String getLicenseHeader(Configuration child) {
        final String result;
        final String[] propertyNames = child.getPropertyNames();
        final boolean hasHeaderProperty = Arrays.asList(propertyNames).contains(HEADER_PROP);
        try {
            if (hasHeaderProperty) {
                result = child.getProperty(HEADER_PROP);
            }
            else {
                final String headerFile = child.getProperty(HEADER_FILE_PROP);
                result = readHeaderFileContent(headerFile);
            }
        }
        catch (CheckstyleException exception) {
            throw new IllegalArgumentException("Failed to extract header from config", exception);
        }
        return result;
    }

    private static String readHeaderFileContent(String headerFilePath) {
        final String content;
        try {
            content = Files.readString(Path.of(headerFilePath));
        }
        catch (IOException exception) {
            throw new IllegalArgumentException("Failed to read: " + headerFilePath, exception);
        }
        return content;
    }

    private static class HeaderVisitor extends JavaIsoVisitor<ExecutionContext> {
        private final List<CheckstyleViolation> violations;
        private final String licenseHeader;

        HeaderVisitor(List<CheckstyleViolation> violations, String licenseHeader) {
            this.violations = violations;
            this.licenseHeader = licenseHeader;
        }

        @Override
        public J visit(Tree tree, ExecutionContext ctx) {
            J result = super.visit(tree, ctx);

            if (tree instanceof JavaSourceFile) {
                JavaSourceFile sourceFile = (JavaSourceFile) tree;
                final Path absolutePath = sourceFile.getSourcePath().toAbsolutePath();

                final String sourceHeader = sourceFile.getComments().stream()
                        .map(comment -> comment.printComment(getCursor()))
                        .collect(Collectors.joining(System.lineSeparator()));

                if (!sourceHeader.equals(licenseHeader.stripTrailing())
                        && isAtViolation(absolutePath)) {
                    sourceFile = sourceFile.withPrefix(
                            Space.format(licenseHeader
                                    + System.lineSeparator()));
                }
                result = super.visit(sourceFile, ctx);
            }
            return result;
        }

        private boolean isAtViolation(Path currentFileName) {
            final Optional<CheckstyleViolation> match = violations.stream()
                    .filter(violation -> {
                        return currentFileName.equals(Path.of(violation.getFileName())
                                .toAbsolutePath());
                    }).findFirst();

            return match.isPresent();
        }
    }
}
