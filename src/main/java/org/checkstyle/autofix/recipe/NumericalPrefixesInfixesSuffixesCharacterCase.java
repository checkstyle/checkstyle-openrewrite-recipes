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
import java.util.Locale;

import org.checkstyle.autofix.PositionHelper;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.ExecutionContext;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JavaType;

/**
 * Fixes Checkstyle NumericalPrefixesInfixesSuffixesCharacterCase violations by replacing
 * uppercase prefixes : {@code 0x, 0b}, infixes : {@code e, p}, and suffixes : {@code f, d}
 * with lowercase literals.
 */
public class NumericalPrefixesInfixesSuffixesCharacterCase extends AbstractScanningRecipe {

    public NumericalPrefixesInfixesSuffixesCharacterCase(
            List<CheckstyleViolation> violations) {
        super(violations);
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
            this.sourcePath = cu.getSourcePath();
            return super.visitCompilationUnit(cu, executionContext);
        }

        @Override
        public J.Literal visitLiteral(J.Literal literal, ExecutionContext executionContext) {
            final J.Literal result = super.visitLiteral(literal, executionContext);

            if (isLongOrInt(result) || isFloatOrDouble(result)) {
                final J.CompilationUnit cursor =
                        getCursor().firstEnclosing(J.CompilationUnit.class);
                final int line = PositionHelper.computeLinePosition(
                        cursor, result, getCursor());
                final int column = PositionHelper.computeColumnPosition(
                        cursor, result, getCursor());

                for (CheckstyleViolation violation : getViolations()) {
                    final Path absolutePath = violation.getFilePath();
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

        private boolean isLongOrInt(J.Literal literal) {
            return literal.getType() == JavaType.Primitive.Long
                    || literal.getType() == JavaType.Primitive.Int;
        }

        private boolean isFloatOrDouble(J.Literal literal) {
            return literal.getType() == JavaType.Primitive.Float
                    || literal.getType() == JavaType.Primitive.Double;
        }
    }

    private static final class FixerVisitor extends JavaIsoVisitor<ExecutionContext> {

        private static final String HEX_PREFIX = "0x";
        private static final String BYTE_PREFIX = "0b";
        private static final String EXPONENT_P = "p";

        private final ViolationAccumulator acc;

        private FixerVisitor(ViolationAccumulator acc) {
            this.acc = acc;
        }

        @Override
        public J.Literal visitLiteral(J.Literal literal, ExecutionContext executionContext) {
            J.Literal result = literal;
            final String valueSource = result.getValueSource();

            if (acc.getMatched().containsKey(result.getId())) {
                if (shouldProcessLongAndIntLiteral(result)) {
                    final String newValueSource =
                            convertToLowerCaseForLongAndIntLiterals(valueSource);
                    result = result.withValueSource(newValueSource);
                }

                if (shouldProcessFloatAndDoubleLiteral(result)) {
                    final String newValueSource =
                        convertToLowerCaseForFloatAndDoubleLiterals(valueSource);
                    result = result.withValueSource(newValueSource);
                }
            }

            return result;
        }

        private boolean shouldProcessLongAndIntLiteral(J.Literal literal) {
            return literal.getType() == JavaType.Primitive.Long
                    || literal.getType() == JavaType.Primitive.Int;
        }

        private boolean shouldProcessFloatAndDoubleLiteral(J.Literal literal) {
            return literal.getType() == JavaType.Primitive.Float
                    || literal.getType() == JavaType.Primitive.Double;
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
    }
}
