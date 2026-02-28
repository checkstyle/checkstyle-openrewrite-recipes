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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.checkstyle.autofix.PositionHelper;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.ExecutionContext;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;

public class RedundantImport extends AbstractScanningRecipe {

    private static final String JAVA_LANG_PREFIX = "java.lang.";

    public RedundantImport(List<CheckstyleViolation> violations) {
        super(violations);
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
    public TreeVisitor<?, ExecutionContext> getScanner(ViolationAccumulator acc) {
        return new ScannerVisitor(acc);
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor(ViolationAccumulator acc) {
        return new FixerVisitor(acc);
    }

    private final class ScannerVisitor extends JavaIsoVisitor<ExecutionContext> {

        private final ViolationAccumulator acc;
        private Path sourcePath;

        private ScannerVisitor(ViolationAccumulator acc) {
            this.acc = acc;
        }

        @Override
        public J.CompilationUnit visitCompilationUnit(J.CompilationUnit cu,
                                                       ExecutionContext executionContext) {
            this.sourcePath = cu.getSourcePath().toAbsolutePath();
            return super.visitCompilationUnit(cu, executionContext);
        }

        @Override
        public J.Import visitImport(J.Import importStmt, ExecutionContext executionContext) {
            final J.Import result = super.visitImport(importStmt, executionContext);

            final J.CompilationUnit cursor =
                    getCursor().firstEnclosing(J.CompilationUnit.class);
            final int line = PositionHelper.computeLinePosition(
                    cursor, result, getCursor());
            final int column = PositionHelper.computeColumnPosition(
                    cursor, result, getCursor());

            for (CheckstyleViolation violation : getViolations()) {
                final Path absolutePath = violation.getFilePath().toAbsolutePath();
                if (violation.getLine() == line
                        && violation.getColumn() == column
                        && absolutePath.endsWith(sourcePath)) {
                    acc.getMatched().computeIfAbsent(result.getId(),
                            key -> new ArrayList<>()).add(violation);
                    break;
                }
            }

            return result;
        }
    }

    private static final class FixerVisitor extends JavaIsoVisitor<ExecutionContext> {

        private final ViolationAccumulator acc;

        private FixerVisitor(ViolationAccumulator acc) {
            this.acc = acc;
        }

        @Override
        public J.CompilationUnit visitCompilationUnit(J.CompilationUnit cu,
                                                       ExecutionContext executionContext) {

            final Set<String> seenImports = new HashSet<>();
            final String currentPackage = getCurrentPackage(cu);

            return cu.withImports(
                    cu.getImports().stream()
                            .filter(importStmt -> {
                                return !isRedundant(importStmt, seenImports,
                                    currentPackage)
                                    || !acc.getMatched().containsKey(importStmt.getId()); })
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
    }
}
