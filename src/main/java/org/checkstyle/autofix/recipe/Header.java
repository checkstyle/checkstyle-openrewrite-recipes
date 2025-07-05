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
import java.util.Objects;

import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.Tree;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JavaSourceFile;
import org.openrewrite.java.tree.Space;

public class Header extends Recipe {

    private List<CheckstyleViolation> violations;
    private String licenseText;

    public Header() {
        this.violations = new ArrayList<>();
        this.licenseText = "";
    }

    public Header(List<CheckstyleViolation> violations, String licenseText) {
        this.violations = violations;
        this.licenseText = licenseText;
    }

    @Override
    public String getDisplayName() {
        return "Header recipe";
    }

    @Override
    public String getDescription() {
        return "Adds headers to Java source files when missing."
                + " Does not override existing license headers.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new JavaIsoVisitor<>() {
            @Override
            public J visit(Tree tree, ExecutionContext ctx) {
                final J result;
                if (tree instanceof JavaSourceFile) {
                    JavaSourceFile sourceFile =
                            (JavaSourceFile) java.util.Objects.requireNonNull(tree);
                    final Path absolutePath = sourceFile.getSourcePath().toAbsolutePath();
                    if (sourceFile.getComments().isEmpty() && isAtViolationLocation(absolutePath)) {
                        sourceFile = sourceFile.withPrefix(
                                Space.format(licenseText + System.lineSeparator().repeat(2)));
                    }
                    result = super.visit(sourceFile, ctx);
                }
                else {
                    result = super.visit(tree, ctx);
                }
                return result;
            }
        };
    }

    private boolean isAtViolationLocation(Path currentFileName) {

        return violations.stream().anyMatch(violation -> {

            final Path absolutePath =
                    Path.of(violation.getFileName()).toAbsolutePath();

            return violation.getLine() == 1
                    && Objects.isNull(violation.getColumn())
                    && absolutePath.equals(currentFileName);
        });
    }
}
