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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.checkstyle.autofix.PositionHelper;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;

public class RedundantImport extends Recipe {

    private static final String JAVA_LANG_PREFIX = "java.lang.";

    private final List<CheckstyleViolation> violations;

    public RedundantImport(List<CheckstyleViolation> violations) {
        this.violations = violations;
    }

    @Override
    public String getDisplayName() {
        return "Remove redundant imports";
    }

    @Override
    public String getDescription() {
        return "Remove duplicate imports, java.lang imports, and same package imports";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new RemoveRedundantImportsVisitor();
    }

    private final class RemoveRedundantImportsVisitor extends JavaIsoVisitor<ExecutionContext> {

        private Path sourcePath;

        @Override
        public J.CompilationUnit visitCompilationUnit(J.CompilationUnit cu,
                                                      ExecutionContext executionContext) {

            this.sourcePath = cu.getSourcePath().toAbsolutePath();

            final Set<String> seenImports = new HashSet<>();
            final String currentPackage = getCurrentPackage(cu);

            return cu.withImports(
                    cu.getImports().stream()
                            .filter(importStmt -> {
                                return !isRedundant(importStmt, seenImports,
                                    currentPackage) || !isAtViolationLocation(importStmt); })
                            .toList()
            );
        }

        private boolean isRedundant(J.Import importStmt,
                                    Set<String> seenImports, String currentPackage) {
            final String importName = importStmt.getQualid().toString();

            boolean isRedundant = false;

            if (seenImports.contains(importName)) {
                isRedundant = true;
            }
            else {
                seenImports.add(importName);

                if (!importStmt.isStatic() && importName.startsWith(JAVA_LANG_PREFIX)) {
                    isRedundant = true;
                }
                else if (currentPackage != null && !importStmt.isStatic()
                        && importName.startsWith(currentPackage + ".")) {
                    isRedundant = true;
                }
            }

            return isRedundant;
        }

        private String getCurrentPackage(J.CompilationUnit compilationUnit) {
            String currentPackage = null;
            if (compilationUnit.getPackageDeclaration() != null) {
                currentPackage = compilationUnit.getPackageDeclaration()
                        .getExpression().printTrimmed(getCursor());
            }
            return currentPackage;
        }

        private boolean isAtViolationLocation(J.Import literal) {
            final J.CompilationUnit cursor = getCursor().firstEnclosing(J.CompilationUnit.class);

            final int line = PositionHelper.computeLinePosition(cursor, literal, getCursor());
            final int column = PositionHelper.computeColumnPosition(cursor, literal, getCursor());
            return violations.removeIf(violation -> {
                final Path absolutePath = violation.getFilePath().toAbsolutePath();
                return violation.getLine() == line
                        && violation.getColumn() == column
                        && absolutePath.endsWith(sourcePath);
            });
        }

    }
}
