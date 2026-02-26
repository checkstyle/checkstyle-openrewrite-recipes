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
import java.util.List;

import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;

/**
 * Fixes Checkstyle UnusedImports violations by removing unused imports.
 */
public class UnusedImports extends Recipe {

    private final List<CheckstyleViolation> violations;

    public UnusedImports(List<CheckstyleViolation> violations) {
        this.violations = violations;
    }

    @Override
    public String getDisplayName() {
        return "UnusedImports Recipe";
    }

    @Override
    public String getDescription() {
        return "Remove unused imports";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new UnusedImports.UnusedImportsVisitor();
    }

    private final class UnusedImportsVisitor extends JavaIsoVisitor<ExecutionContext> {

        private static final String DOT_OPERATOR = ".";

        private Path sourcePath;

        @Override
        public J.CompilationUnit visitCompilationUnit(J.CompilationUnit cu,
                                                      ExecutionContext executionContext) {

            this.sourcePath = cu.getSourcePath();
            return cu.withImports(
                    cu.getImports().stream()
                            .filter(importStmt -> !isAtViolationLocation(importStmt))
                            .toList()
            );
        }

        private String createMessage(J.Import literal) {
            String fullImport = literal.getTypeName();
            if (literal.isStatic()) {
                fullImport += DOT_OPERATOR + literal.getQualid().getSimpleName();
            }
            return "Unused import - " + fullImport + DOT_OPERATOR;
        }

        private boolean isAtViolationLocation(J.Import literal) {

            final String message = createMessage(literal);
            return violations.stream().anyMatch(violation -> {
                final Path absolutePath = violation.getFilePath();
                return violation.getMessage().equals(message)
                        && absolutePath.endsWith(sourcePath);
            });
        }
    }
}
