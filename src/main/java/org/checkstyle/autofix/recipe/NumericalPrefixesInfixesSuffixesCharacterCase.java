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
import java.util.Locale;

import org.checkstyle.autofix.PositionHelper;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JavaType;

/**
 * Fixes Checkstyle NumericalPrefixesInfixesSuffixesCharacterCase violations by replacing
 * uppercase prefixes : {@code 0x, 0b}, infixes : {@code e, p}, and suffixes : {@code f, d}
 * with lowercase literals.
 */
public class NumericalPrefixesInfixesSuffixesCharacterCase extends Recipe {

    private final List<CheckstyleViolation> violations;

    public NumericalPrefixesInfixesSuffixesCharacterCase(List<CheckstyleViolation> violations) {
        this.violations = violations;
    }

    @Override
    public String getDisplayName() {
        return "NumericalPrefixesInfixesSuffixesCharacterCase Recipe";
    }

    @Override
    public String getDescription() {
        return "Replace Uppercase numerical prefixes, infixes and suffixes with lowercase literals "
                + "to improve readability.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new NumericalPrefixesInfixesSuffixesCharacterCase
            .NumericalPrefixesInfixesSuffixesCharacterCaseVisitor();
    }

    private final class NumericalPrefixesInfixesSuffixesCharacterCaseVisitor
            extends JavaIsoVisitor<ExecutionContext> {

        private static final String HEX_PREFIX = "0x";
        private static final String BYTE_PREFIX = "0b";

        private static final String EXPONENT_P = "p";

        private Path sourcePath;

        @Override
        public J.CompilationUnit visitCompilationUnit(J.CompilationUnit cu,
                ExecutionContext executionContext) {
            this.sourcePath = cu.getSourcePath();
            return super.visitCompilationUnit(cu, executionContext);
        }

        @Override
        public J.Literal visitLiteral(J.Literal literal, ExecutionContext executionContext) {
            J.Literal result = literal;
            final String valueSource = result.getValueSource();

            if (shouldProcessLongAndIntLiteral(result)) {
                final String newValueSource = convertToLowerCaseForLongAndIntLiterals(valueSource);
                result = result.withValueSource(newValueSource);
            }

            if (shouldProcessFloatAndDoubleLiteral(result)) {
                final String newValueSource =
                    convertToLowerCaseForFloatAndDoubleLiterals(valueSource);
                result = result.withValueSource(newValueSource);
            }

            return result;
        }

        private boolean shouldProcessLongAndIntLiteral(J.Literal literal) {
            return (literal.getType() == JavaType.Primitive.Long
                            || literal.getType() == JavaType.Primitive.Int)
                    && isAtViolationLocation(literal);
        }

        private boolean shouldProcessFloatAndDoubleLiteral(J.Literal literal) {
            return (literal.getType() == JavaType.Primitive.Float
                            || literal.getType() == JavaType.Primitive.Double)
                    && isAtViolationLocation(literal);
        }

        private String convertToLowerCaseForFloatAndDoubleLiterals(String valueSource) {

            final StringBuilder result = new StringBuilder();
            final String lowerCaseValueSource = valueSource.toLowerCase(Locale.ROOT);

            if (lowerCaseValueSource.startsWith(HEX_PREFIX)) {
                result.append(HEX_PREFIX);
                final int pIndex = lowerCaseValueSource.indexOf(EXPONENT_P);
                result.append(valueSource, HEX_PREFIX.length(), pIndex);
                result.append(EXPONENT_P);
                result.append(lowerCaseValueSource, pIndex + 1, lowerCaseValueSource.length());
            }
            else {
                result.append(lowerCaseValueSource);
            }

            String finalResult = result.toString();
            if (finalResult.equals(valueSource)) {
                finalResult = valueSource;
            }

            return finalResult;
        }

        private String convertToLowerCaseForLongAndIntLiterals(String valueSource) {

            final StringBuilder result = new StringBuilder();
            final String lowerCaseValueSource = valueSource.toLowerCase(Locale.ROOT);

            if (lowerCaseValueSource.startsWith(HEX_PREFIX)) {
                result.append(HEX_PREFIX);
                result.append(valueSource, HEX_PREFIX.length(), valueSource.length());
            }
            else {
                result.append(BYTE_PREFIX);
                result.append(valueSource, BYTE_PREFIX.length(), valueSource.length());
            }

            String finalResult = result.toString();
            if (finalResult.equals(valueSource)) {
                finalResult = valueSource;
            }

            return finalResult;
        }

        private boolean isAtViolationLocation(J.Literal literal) {
            final J.CompilationUnit cursor = getCursor().firstEnclosing(J.CompilationUnit.class);

            final int line = PositionHelper.computeLinePosition(cursor, literal, getCursor());
            final int column = PositionHelper.computeColumnPosition(cursor, literal, getCursor());

            return violations.stream().anyMatch(violation -> {
                final Path absolutePath = violation.getFilePath();
                return violation.getLine() == line
                        && violation.getColumn() == column
                        && absolutePath.endsWith(sourcePath);
            });
        }
    }
}
