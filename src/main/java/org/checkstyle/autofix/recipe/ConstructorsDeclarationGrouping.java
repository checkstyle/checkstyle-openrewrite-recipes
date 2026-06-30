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

import org.checkstyle.autofix.CheckFullName;
import org.checkstyle.autofix.marker.CheckstyleViolationMarker;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.Statement;

/**
 * Fixes Checkstyle ConstructorsDeclarationGrouping violations by moving
 * out-of-order constructors to group them together with other constructors.
 */
public class ConstructorsDeclarationGrouping extends Recipe {

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

    private static final class ConstructorsDeclarationGroupingVisitor
            extends JavaIsoVisitor<ExecutionContext> {

        @Override
        public J.ClassDeclaration visitClassDeclaration(
            J.ClassDeclaration classDecl, ExecutionContext executionContext) {

            final J.ClassDeclaration classDeclaration =
                super.visitClassDeclaration(classDecl, executionContext);

            final List<Statement> statements = classDeclaration.getBody().getStatements();

            final int groupEnd = findFirstConstructorGroupEnd(statements);
            final List<J.MethodDeclaration> violatingConstructors =
                findViolatingConstructors(statements, groupEnd);

            final J.ClassDeclaration newClassDeclaration;

            // keep code intact if there are no violations
            if (violatingConstructors.isEmpty()) {
                newClassDeclaration = classDeclaration;
            }
            else {
                final List<Statement> reordered =
                    reorderConstructors(statements, groupEnd, violatingConstructors);

                newClassDeclaration = classDeclaration.withBody(
                    classDeclaration.getBody().withStatements(reordered));
            }
            return newClassDeclaration;
        }

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

        private static int findFirstConstructorGroupEnd(List<Statement> statements) {
            int result = findFirstConstructorIndex(statements);
            if (result != -1) {
                while (result < statements.size()
                        && statements.get(result) instanceof J.MethodDeclaration method
                        && method.isConstructor()) {
                    result++;
                }
            }
            return result;
        }

        private static List<J.MethodDeclaration> findViolatingConstructors(
            List<Statement> statements, int groupEnd) {
            // Only scan past the initial constructor group so leftover
            // markers on already-moved constructors don't cause infinite reordering.
            final List<J.MethodDeclaration> violating = new ArrayList<>();
            if (groupEnd != -1) {
                for (int index = groupEnd; index < statements.size(); index++) {
                    final Statement statement = statements.get(index);
                    if (statement instanceof J.MethodDeclaration method
                            && isViolationMarked(method)) {
                        violating.add(method);
                    }
                }
            }
            return violating;
        }

        private static boolean isViolationMarked(J.MethodDeclaration method) {
            final List<CheckstyleViolationMarker> markers =
                method.getMarkers().findAll(CheckstyleViolationMarker.class);
            final CheckFullName targetCheck =
                    CheckFullName.CONSTRUCTORS_DECLARATION_GROUPING;
            return markers.stream()
                    .anyMatch(marker -> marker.isFor(targetCheck));
        }

        private static List<Statement> reorderConstructors(
            List<Statement> statements, int insertIndex,
            List<J.MethodDeclaration> violatingConstructors) {

            final List<Statement> result = new ArrayList<>(statements);
            result.removeAll(violatingConstructors);

            result.add(insertIndex, violatingConstructors.get(0));
            for (int index = 1; index < violatingConstructors.size(); index++) {
                result.add(insertIndex + index, violatingConstructors.get(index));
            }

            return result;
        }
    }
}
