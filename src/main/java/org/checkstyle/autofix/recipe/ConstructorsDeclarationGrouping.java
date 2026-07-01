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
                final int groupEnd =
                    findFirstConstructorGroupEnd(statements, firstConstructorIndex);
                final List<J.MethodDeclaration> candidates;
                if (orderByIncreasingParameterCount) {
                    candidates = findConstructorsToSort(
                        statements, firstConstructorIndex, groupEnd);
                }
                else {
                    candidates = findConstructorsToGroup(statements, groupEnd);
                }
                final List<Statement> reordered;
                if (orderByIncreasingParameterCount) {
                    reordered = sortConstructors(statements,
                        firstConstructorIndex, candidates);
                }
                else {
                    reordered = groupConstructors(
                        statements, groupEnd, candidates);
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
                        && isViolationMarked(method)) {
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
         * Collects constructors for parameter-count ordering: the entire initial
         * group (they all participate in sorting) plus any violation-marked
         * constructors after it. Unmarked constructors after the group are
         * skipped — their violations were suppressed.
         *
         * @param statements the class body statements
         * @param firstConstructorIndex the index of the first constructor
         * @param groupEnd the exclusive end of the initial constructor group
         * @return constructors to sort, in original declaration order
         */
        private static List<J.MethodDeclaration> findConstructorsToSort(
                List<Statement> statements, int firstConstructorIndex, int groupEnd) {
            final List<J.MethodDeclaration> result = new ArrayList<>();
            // Every element in [firstConstructorIndex, groupEnd) is guaranteed
            // a constructor — no instanceof or isConstructor() guard needed.
            for (int index = firstConstructorIndex; index < groupEnd; index++) {
                result.add((J.MethodDeclaration) statements.get(index));
            }
            for (int index = groupEnd; index < statements.size(); index++) {
                final Statement statement = statements.get(index);
                if (statement instanceof J.MethodDeclaration method
                        && isViolationMarked(method)) {
                    result.add(method);
                }
            }
            return result;
        }

        /**
         * Sorts the candidates by parameter count ascending, removes them from
         * their original positions, and re-inserts them as a contiguous block
         * at the first constructor index.
         *
         * @param statements the original class body statements
         * @param firstConstructorIndex the insertion position
         * @param candidates the constructors to sort
         * @return the reordered statement list
         */
        private static List<Statement> sortConstructors(List<Statement> statements,
                int firstConstructorIndex,
                List<J.MethodDeclaration> candidates) {
            final Comparator<J.MethodDeclaration> cmp =
                Comparator.comparingInt(ConstructorsDeclarationGroupingVisitor
                    ::countParameters);
            candidates.sort(cmp);

            final List<Statement> result = new ArrayList<>(statements);
            result.removeAll(candidates);
            result.addAll(firstConstructorIndex, candidates);
            return result;
        }

        /**
         * Returns the number of parameters of a constructor.
         *
         * @param method the constructor
         * @return parameter count, 0 for a no-arg constructor
         */
        private static int countParameters(J.MethodDeclaration method) {
            return method.getMethodType().getParameterTypes().size();
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
         * Checks whether a constructor carries a violation marker for this check.
         * A constructor without a marker either has no violation or its violation
         * was suppressed — in either case the recipe leaves it alone.
         *
         * @param method the constructor to check
         * @return true if the method has a relevant violation marker
         */
        private static boolean isViolationMarked(J.MethodDeclaration method) {
            final List<CheckstyleViolationMarker> markers =
                method.getMarkers().findAll(CheckstyleViolationMarker.class);
            final CheckFullName targetCheck =
                    CheckFullName.CONSTRUCTORS_DECLARATION_GROUPING;
            return markers.stream()
                    .anyMatch(marker -> marker.isFor(targetCheck));
        }
    }
}
