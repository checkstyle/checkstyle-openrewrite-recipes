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

package org.checkstyle.autofix;

import java.util.concurrent.CancellationException;
import java.util.function.Function;

import org.openrewrite.Cursor;
import org.openrewrite.PrintOutputCapture;
import org.openrewrite.TreeVisitor;
import org.openrewrite.internal.RecipeRunException;
import org.openrewrite.java.tree.J;

public final class PositionHelper {

    private PositionHelper() {
        // Utility class
    }

    public static int computeLinePosition(J tree, J targetElement, Cursor cursor) {
        return computePosition(tree, targetElement, cursor,
                out -> 1 + Math.toIntExact(out.chars().filter(chr -> chr == '\n').count()));
    }

    public static int computeColumnPosition(J tree, J targetElement, Cursor cursor) {
        return computePosition(tree, targetElement, cursor, out -> {
            int column = calculateColumnOffset(out);
            if (targetElement instanceof J.Literal literal
                    && literal.getValue() instanceof Number
                    && literal.getValueSource() != null
                    && literal.getValueSource().matches("^[+-].*")) {
                column++;
            }
            return column;
        });
    }

    private static int computePosition(
            J tree,
            J targetElement,
            Cursor cursor,
            Function<String, Integer> positionCalculator
    ) {
        final TreeVisitor<?, PrintOutputCapture<TreeVisitor<?, ?>>> printer =
                tree.printer(cursor);

        final PrintOutputCapture<TreeVisitor<?, ?>> capture =
                new PrintOutputCapture<>(printer) {
                    @Override
                    public PrintOutputCapture<TreeVisitor<?, ?>> append(String text) {
                        if (targetElement.isScope(getContext().getCursor().getValue())) {
                            super.append(targetElement.getPrefix().getWhitespace());
                            throw new CancellationException();
                        }
                        return super.append(text);
                    }
                };

        final int result;
        try {
            printer.visit(tree, capture, cursor.getParentOrThrow());
            throw new IllegalStateException("Target element: " + targetElement
                    + ", not found in the syntax tree.");
        }
        catch (CancellationException exception) {
            result = positionCalculator.apply(capture.getOut());
        }
        catch (RecipeRunException exception) {
            if (exception.getCause() instanceof CancellationException) {
                result = positionCalculator.apply(capture.getOut());
            }
            else {
                throw exception;
            }
        }
        return result;
    }

    private static int calculateColumnOffset(String out) {
        final int lineBreakIndex = out.lastIndexOf('\n');
        final int result;
        if (lineBreakIndex == -1) {
            result = out.length();
        }
        else {
            result = out.length() - lineBreakIndex;
        }
        return result;
    }
}
