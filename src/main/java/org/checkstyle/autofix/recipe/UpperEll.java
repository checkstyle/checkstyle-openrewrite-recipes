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

import org.checkstyle.autofix.PositionHelper;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.ExecutionContext;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JavaType;

/**
 * Fixes Checkstyle UpperEll violations by replacing lowercase 'l' suffix
 * in long literals with uppercase 'L'.
 */
public class UpperEll extends AbstractScanningRecipe {

    private static final String LOWERCASE_L = "l";
    private static final String UPPERCASE_L = "L";

    public UpperEll(List<CheckstyleViolation> violations) {
        super(violations);
    }

    @Override
    public String getDisplayName() {
        return "UpperEll recipe";
    }

    @Override
    public String getDescription() {
        return "Replace lowercase 'l' suffix in long literals with uppercase 'L' "
                + "to improve readability.";
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
        public J.CompilationUnit visitCompilationUnit(
                J.CompilationUnit cu, ExecutionContext executionContext) {
            this.sourcePath = cu.getSourcePath().toAbsolutePath();
            return super.visitCompilationUnit(cu, executionContext);
        }

        @Override
        public J.Literal visitLiteral(J.Literal literal, ExecutionContext executionContext) {
            final J.Literal result = super.visitLiteral(literal, executionContext);
            final String valueSource = result.getValueSource();

            if (valueSource != null && valueSource.endsWith(LOWERCASE_L)
                    && result.getType() == JavaType.Primitive.Long) {

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
        public J.Literal visitLiteral(J.Literal literal, ExecutionContext executionContext) {
            J.Literal result = super.visitLiteral(literal, executionContext);
            final String valueSource = result.getValueSource();

            if (acc.getMatched().containsKey(result.getId())
                    && valueSource != null && valueSource.endsWith(LOWERCASE_L)
                    && result.getType() == JavaType.Primitive.Long) {

                final String numericPart =
                        valueSource.substring(0, valueSource.length() - 1);
                result = result.withValueSource(numericPart + UPPERCASE_L);
            }

            return result;
        }
    }
}
