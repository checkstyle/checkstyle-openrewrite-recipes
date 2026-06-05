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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.checkstyle.autofix.PositionHelper;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.Tree;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.marker.TrailingComma;
import org.openrewrite.java.tree.Expression;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JContainer;
import org.openrewrite.java.tree.JRightPadded;
import org.openrewrite.java.tree.Space;

/**
 * Fixes Checkstyle ArrayTrailingComma violations by adding a trailing comma
 * after the last element of multi-line array initializers.
 */
public class ArrayTrailingComma extends Recipe {

    private final List<CheckstyleViolation> violations;

    public ArrayTrailingComma(List<CheckstyleViolation> violations) {
        this.violations = violations;
    }

    @Override
    public String getDisplayName() {
        return "ArrayTrailingComma recipe";
    }

    @Override
    public String getDescription() {
        return "Add trailing comma to array initializers "
                + "to satisfy the ArrayTrailingComma check.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new ArrayTrailingCommaVisitor();
    }

    private final class ArrayTrailingCommaVisitor extends JavaIsoVisitor<ExecutionContext> {

        private J.CompilationUnit compilationUnit;

        @Override
        public J.CompilationUnit visitCompilationUnit(J.CompilationUnit compUnit,
                                                      ExecutionContext executionContext) {
            compilationUnit = compUnit;
            return super.visitCompilationUnit(compUnit, executionContext);
        }

        @Override
        public J.NewArray visitNewArray(J.NewArray newArray, ExecutionContext executionContext) {
            final JContainer<Expression> originalInitializer =
                newArray.getPadding().getInitializer();

            final boolean shouldAddComma = originalInitializer != null
                    && !hasTrailingComma(originalInitializer)
                    && isLastElementAtViolationLocation(originalInitializer);

            J.NewArray result = super.visitNewArray(newArray, executionContext);

            if (shouldAddComma) {
                result = addTrailingComma(result, result.getPadding().getInitializer());
            }
            return result;
        }

        private boolean hasTrailingComma(JContainer<Expression> initializer) {
            final List<JRightPadded<Expression>> elements = initializer.getPadding().getElements();
            final JRightPadded<Expression> lastElement = elements.get(elements.size() - 1);
            return lastElement.getMarkers().findFirst(TrailingComma.class).isPresent();
        }

        private J.NewArray addTrailingComma(J.NewArray newArray,
                                            JContainer<Expression> initializer) {
            final List<JRightPadded<Expression>> elements = initializer.getPadding().getElements();
            final List<JRightPadded<Expression>> updatedElements = new ArrayList<>(elements);
            final JRightPadded<Expression> lastElement = elements.get(elements.size() - 1);

            final UUID id = Tree.randomId();
            final TrailingComma trailingComma =
                    new TrailingComma(id, lastElement.getAfter());

            final JRightPadded<Expression> updatedLastElement = lastElement
                    .withAfter(Space.EMPTY)
                    .withMarkers(lastElement.getMarkers().addIfAbsent(trailingComma));

            updatedElements.set(updatedElements.size() - (id.variant() / 2), updatedLastElement);

            final JContainer<Expression> updatedInitializer =
                    initializer.getPadding().withElements(updatedElements);
            return newArray.getPadding().withInitializer(updatedInitializer);
        }

        private boolean isLastElementAtViolationLocation(
                JContainer<Expression> initializer) {
            final List<JRightPadded<Expression>> elements = initializer.getPadding().getElements();
            final Expression lastElement = elements.get(elements.size() - 1).getElement();

            final int line = PositionHelper.computeLinePosition(
                    compilationUnit, lastElement, getCursor());
            return violations.stream()
                    .anyMatch(violation -> {
                        final boolean isSameLine = violation.getLine().equals(line);
                        final boolean isSameFile = violation.getFilePath()
                                .endsWith(compilationUnit.getSourcePath());
                        return isSameLine & isSameFile;
                    });
        }
    }
}
