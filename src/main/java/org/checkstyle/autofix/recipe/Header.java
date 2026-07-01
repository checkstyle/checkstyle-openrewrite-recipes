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
import java.util.stream.Collectors;

import org.checkstyle.autofix.CheckFullName;
import org.checkstyle.autofix.marker.CheckstyleViolationMarker;
import org.checkstyle.autofix.parser.CheckConfiguration;
import org.openrewrite.Cursor;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JavaSourceFile;
import org.openrewrite.java.tree.Space;

public class Header extends Recipe {
    private static final String HEADER_PROPERTY = "header";
    private static final String HEADER_FILE_PROPERTY = "headerFile";
    private static final String CHARSET_PROPERTY = "charset";
    private static final String LINE_SEPARATOR = "\n";
    private static final String CRLF_REGEX = "(?x)\\\\r(?=\\\\n)|\\r(?=\\n)";

    private final CheckConfiguration config;

    public Header(CheckConfiguration config) {
        this.config = config;
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
        final String licenseHeader = extractLicenseHeader(config);
        return new HeaderVisitor(licenseHeader);
    }

    private static String extractLicenseHeader(CheckConfiguration config) {
        final String header;
        if (config.hasProperty(HEADER_PROPERTY)) {
            header = config.getProperty(HEADER_PROPERTY);
        }
        else {
            final Charset charsetToUse = Charset.forName(config
                    .getPropertyOrDefault(CHARSET_PROPERTY, Charset.defaultCharset().name()));
            final String headerFilePath = config.getProperty(HEADER_FILE_PROPERTY);
            try {
                header = toLfLineEnding(Files.readString(Path.of(headerFilePath), charsetToUse));
            }
            catch (IOException exception) {
                throw new IllegalArgumentException("Failed to extract header from config",
                        exception);
            }
        }
        return header;
    }

    private static String toLfLineEnding(String text) {
        return text.replaceAll(CRLF_REGEX, "");
    }

    private static class HeaderVisitor extends JavaIsoVisitor<ExecutionContext> {
        private final String licenseHeader;

        HeaderVisitor(String licenseHeader) {
            this.licenseHeader = licenseHeader;
        }

        @Override
        public J.CompilationUnit visitCompilationUnit(J.CompilationUnit cu,
                                                      ExecutionContext executionContext) {
            final boolean hasMarker = cu.getMarkers()
                    .findAll(CheckstyleViolationMarker.class).stream()
                    .anyMatch(marker -> marker.isFor(CheckFullName.HEADER));

            J.CompilationUnit result = cu;
            if (hasMarker) {
                final String currentHeader = extractCurrentHeader(result);
                if (!currentHeader.startsWith(licenseHeader)) {
                    final String fixedHeader = licenseHeader + LINE_SEPARATOR + currentHeader;
                    result = result.withPrefix(Space.format(fixedHeader));
                }
            }

            return result;
        }

        private String extractCurrentHeader(JavaSourceFile sourceFile) {
            return sourceFile.getComments().stream()
                    .map(comment -> {
                        return comment.printComment(new Cursor(null, sourceFile))
                                + toLfLineEnding(comment.getSuffix());
                    })
                    .collect(Collectors.joining(""));
        }
    }
}
