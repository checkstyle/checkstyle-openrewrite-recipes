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
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.checkstyle.autofix.CheckFullName;
import org.checkstyle.autofix.marker.CheckstyleViolationMarker;
import org.checkstyle.autofix.parser.CheckConfiguration;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.Statement;

/**
 * Fixes Checkstyle ConstructorsDeclarationGrouping violations by moving
 * out-of-order constructors to group them together.
 */
public class ConstructorsDeclarationGrouping extends Recipe {
    private static final String ORDER_BY_PARAM_COUNT_PROPERTY =
        "orderByIncreasingParameterCount";

    private static final String ORDERING_MESSAGE_PREFIX =
        "Constructors should be ordered";
    private static final String GROUPING_MESSAGE_PREFIX =
        "Constructors should be grouped together";

    private final boolean orderByIncreasingParameterCount;

    public ConstructorsDeclarationGrouping(CheckConfiguration config) {
        final String value;
        if (config != null) {
            value = config.getProperty(ORDER_BY_PARAM_COUNT_PROPERTY);
        }
        else {
            value = null;
        }
        this.orderByIncreasingParameterCount = "true".equals(value);
    }

    @Override
    public String getDisplayName() {
        return "ConstructorsDeclarationGrouping recipe";
    }

    @Override
    public String getDescription() {
        return "Groups all constructors together in a class.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new ConstructorsDeclarationGroupingVisitor();
    }

    private final class ConstructorsDeclarationGroupingVisitor
            extends JavaIsoVisitor<ExecutionContext> {

        @Override
        public J.ClassDeclaration visitClassDeclaration(
                J.ClassDeclaration classDecl, ExecutionContext executionContext) {

            final J.ClassDeclaration classDeclaration =
                super.visitClassDeclaration(classDecl, executionContext);

            final List<Statement> statements =
                new ArrayList<>(classDeclaration.getBody().getStatements());

            final int firstConstructorIndex = findFirstConstructorIndex(statements);

            final J.ClassDeclaration newClassDeclaration;
            if (firstConstructorIndex != -1) {
                final List<Statement> reordered;
                if (orderByIncreasingParameterCount) {
                    reordered = applyFixWithOrdering(statements, firstConstructorIndex);
                }
                else {
                    reordered = applyFixWithoutOrdering(statements, firstConstructorIndex);
                }
                newClassDeclaration = classDeclaration.withBody(
                    classDeclaration.getBody().withStatements(reordered));
            }
            else {
                newClassDeclaration = classDeclaration;
            }
            return newClassDeclaration;
        }

        /**
         * Scans past the initial constructor group for constructors that carry
         * a violation marker and need to be moved into the group.
         *
         * @param statements the class body statements
         * @param groupEnd the exclusive end of the initial constructor group
         * @return violating constructors found after the initial group
         */
        private static List<J.MethodDeclaration> findConstructorsToGroup(
                List<Statement> statements, int groupEnd) {
            final List<J.MethodDeclaration> violating = new ArrayList<>();
            for (int index = groupEnd; index < statements.size(); index++) {
                final Statement statement = statements.get(index);
                if (statement instanceof J.MethodDeclaration method
                        && hasGroupingViolationMarker(method)) {
                    violating.add(method);
                }
            }
            return violating;
        }

        /**
         * Moves each violating constructor to the end of the initial group,
         * preserving their original relative order.
         *
         * @param statements the original class body statements
         * @param groupEnd the exclusive end of the initial group, also the insert
         *     position
         * @param candidates the constructors to move into the group
         * @return the reordered statement list
         */
        private static List<Statement> groupConstructors(
                List<Statement> statements, int groupEnd, List<J.MethodDeclaration> candidates) {

            final List<Statement> result = new ArrayList<>(statements);
            result.removeAll(candidates);
            result.addAll(groupEnd, candidates);
            return result;
        }

        /**
         * Applies the ordering fix: collects the non-decreasing prefix of
         * contiguous constructors and all ordering-marked constructors, removes
         * them, sorts by parameter count, and inserts them as a contiguous block
         * at the first constructor index. Then appends grouping-only constructors
         * (those with grouping markers but no ordering markers) after the sorted
         * block.
         *
         * @param statements the class body statements
         * @param firstConstructorIndex the index of the first constructor
         * @return the reordered statement list
         */
        private List<Statement> applyFixWithOrdering(
                List<Statement> statements, int firstConstructorIndex) {
            // Sort ordering-marked constructors with already grouped and sorted constructors
            final List<J.MethodDeclaration> constructorsToSort =
                collectGroupedAndSortedConstructors(statements, firstConstructorIndex);
            for (final Statement statement : statements) {
                if (statement instanceof J.MethodDeclaration method
                        && hasOrderingViolationMarker(method)
                        && !constructorsToSort.contains(method)) {
                    constructorsToSort.add(method);
                }
            }
            final Comparator<J.MethodDeclaration> cmp =
                Comparator.comparingInt(ConstructorsDeclarationGroupingVisitor
                    ::countParameters);
            constructorsToSort.sort(cmp);

            final List<Statement> result = new ArrayList<>(statements);
            result.removeAll(constructorsToSort);
            result.addAll(firstConstructorIndex, constructorsToSort);

            // Append grouping-only constructors after the sorted block.
            final List<J.MethodDeclaration> constructorsToGroup = new ArrayList<>();
            for (final Statement statement : statements) {
                if (statement instanceof J.MethodDeclaration method
                        && hasGroupingViolationMarker(method)
                        && !hasOrderingViolationMarker(method)) {
                    constructorsToGroup.add(method);
                }
            }
            result.removeAll(constructorsToGroup);
            result.addAll(firstConstructorIndex + constructorsToSort.size(), constructorsToGroup);

            return result;
        }

        /**
         * Applies the grouping-only fix: collects constructors with grouping
         * violation markers past the initial constructor group and appends them
         * to the group, preserving original relative order.
         *
         * @param statements the class body statements
         * @param firstConstructorIndex the index of the first constructor
         * @return the reordered statement list
         */
        private static List<Statement> applyFixWithoutOrdering(
                List<Statement> statements, int firstConstructorIndex) {
            final int groupEnd =
                findFirstConstructorGroupEnd(statements, firstConstructorIndex);
            final List<J.MethodDeclaration> candidates =
                findConstructorsToGroup(statements, groupEnd);
            return groupConstructors(statements, groupEnd, candidates);
        }

        /**
         * Collects contiguous constructors that are already grouped and in
         * non-decreasing parameter-count order. Stops when the next statement
         * is not a constructor or its parameter count is lower than the
         * previous one.
         *
         * @param statements the class body statements
         * @param firstConstructorIndex the index of the first constructor
         * @return the grouped-and-sorted constructors in original order
         */
        private static List<J.MethodDeclaration> collectGroupedAndSortedConstructors(
                List<Statement> statements, int firstConstructorIndex) {
            final List<J.MethodDeclaration> result = new ArrayList<>();
            result.add((J.MethodDeclaration) statements.get(firstConstructorIndex));
            int prevCount = countParameters(result.get(0));
            int index = firstConstructorIndex + 1;
            int currentCount;
            while (index < statements.size()
                    && statements.get(index) instanceof J.MethodDeclaration method
                    && method.isConstructor()
                    && (currentCount = countParameters(method)) >= prevCount) {
                result.add(method);
                prevCount = currentCount;
                index++;
            }
            return result;
        }

        /**
         * Returns the number of parameters of a constructor.
         *
         * @param method the constructor
         * @return parameter count, 0 for a no-arg constructor
         */
        private static int countParameters(J.MethodDeclaration method) {
            final int parameterCount;
            if (method.getParameters().get(0) instanceof J.Empty) {
                parameterCount = 0;
            }
            else {
                parameterCount = method.getParameters().size();
            }
            return parameterCount;
        }

        /**
         * Returns the index of the first constructor in the statement list,
         * or -1 if the class has no constructors.
         *
         * @param statements the class body statements
         * @return index of the first constructor, or -1
         */
        private static int findFirstConstructorIndex(List<Statement> statements) {
            int firstConstructorIndex = -1;
            for (int index = 0; index < statements.size(); index++) {
                if (statements.get(index) instanceof J.MethodDeclaration method
                        && method.isConstructor()) {
                    firstConstructorIndex = index;
                    break;
                }
            }
            return firstConstructorIndex;
        }

        /**
         * Returns the exclusive end index of the initial contiguous block of
         * constructors. Stops at the first non-constructor statement or end
         * of list.
         *
         * @param statements the class body statements
         * @param firstConstructorIndex the index of the first constructor
         * @return the index after the last constructor in the initial group
         */
        private static int findFirstConstructorGroupEnd(List<Statement> statements,
                int firstConstructorIndex) {
            int result = firstConstructorIndex;
            while (result < statements.size()
                    && statements.get(result) instanceof J.MethodDeclaration method
                    && method.isConstructor()) {
                result++;
            }
            return result;
        }

        /**
         * Checks whether a constructor carries a grouping violation marker.
         *
         * @param method the constructor to check
         * @return true if the method has a grouping violation marker
         */
        private static boolean hasGroupingViolationMarker(J.MethodDeclaration method) {
            return hasViolationMarkerMatching(method,
                marker -> {
                    return marker.violation().getMessage()
                        .startsWith(GROUPING_MESSAGE_PREFIX);
                });
        }

        /**
         * Checks whether a constructor carries an ordering violation marker.
         *
         * @param method the constructor to check
         * @return true if the method has an ordering violation marker
         */
        private static boolean hasOrderingViolationMarker(J.MethodDeclaration method) {
            return hasViolationMarkerMatching(method,
                marker -> {
                    return marker.violation().getMessage()
                        .startsWith(ORDERING_MESSAGE_PREFIX);
                });
        }

        /**
         * Checks whether a constructor carries a violation marker for this check
         * that satisfies the given predicate.
         *
         * @param method the constructor to check
         * @param predicate predicate to test the marker
         * @return true if the method has a matching violation marker
         */
        private static boolean hasViolationMarkerMatching(
                J.MethodDeclaration method,
                Predicate<CheckstyleViolationMarker> predicate) {
            final List<CheckstyleViolationMarker> markers =
                method.getMarkers().findAll(CheckstyleViolationMarker.class);
            final CheckFullName targetCheck =
                    CheckFullName.CONSTRUCTORS_DECLARATION_GROUPING;
            return markers.stream()
                    .filter(marker -> marker.isFor(targetCheck))
                    .anyMatch(predicate);
        }
    }
}
