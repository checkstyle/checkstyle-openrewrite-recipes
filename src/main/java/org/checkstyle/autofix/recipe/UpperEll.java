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

import org.checkstyle.autofix.PositionHelper;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JavaType;

/**
 * Fixes Checkstyle UpperEll violations by replacing lowercase 'l' suffix
 * in long literals with uppercase 'L'.
 */
public class UpperEll extends Recipe {

    private final List<CheckstyleViolation> violations;

    public UpperEll(List<CheckstyleViolation> violations) {
        this.violations = violations;
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
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new UpperEllVisitor();
    }

    private final class UpperEllVisitor extends JavaIsoVisitor<ExecutionContext> {

        private static final String LOWERCASE_L = "l";
        private static final String UPPERCASE_L = "L";

        private Path sourcePath;

        @Override
        public J.CompilationUnit visitCompilationUnit(
                J.CompilationUnit cu, ExecutionContext executionContext) {
            this.sourcePath = cu.getSourcePath().toAbsolutePath();
            return super.visitCompilationUnit(cu, executionContext);
        }

        @Override
        public J.Literal visitLiteral(J.Literal literal, ExecutionContext executionContext) {
            J.Literal result = super.visitLiteral(literal, executionContext);
            final String valueSource = result.getValueSource();

            if (valueSource != null && valueSource.endsWith(LOWERCASE_L)
                    && result.getType() == JavaType.Primitive.Long
                    && isAtViolationLocation(result)) {

                final String numericPart = valueSource.substring(0, valueSource.length() - 1);
                result = result.withValueSource(numericPart + UPPERCASE_L);
            }

            return result;
        }

        private boolean isAtViolationLocation(J.Literal literal) {
            final J.CompilationUnit cursor = getCursor().firstEnclosing(J.CompilationUnit.class);

            final int line = PositionHelper.computeLinePosition(cursor, literal, getCursor());
            final int column = PositionHelper.computeColumnPosition(cursor, literal, getCursor());

            return violations.stream().anyMatch(violation -> {
                final Path absolutePath = Path.of(violation.getFileName()).toAbsolutePath();
                return violation.getLine() == line
                        && violation.getColumn() == column
                        && absolutePath.endsWith(sourcePath);
            });
        }
    }
}
