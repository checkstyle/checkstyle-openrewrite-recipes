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

    private final List<CheckstyleViolation> violations;

    public ConstructorsDeclarationGrouping(List<CheckstyleViolation> violations) {
        this.violations = violations;
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

        private Path sourcePath;

        @Override
        public J.CompilationUnit visitCompilationUnit(
                J.CompilationUnit cu, ExecutionContext executionContext) {
            this.sourcePath = cu.getSourcePath();
            return super.visitCompilationUnit(cu, executionContext);
        }

        @Override
        public J.ClassDeclaration visitClassDeclaration(
            J.ClassDeclaration classDecl, ExecutionContext executionContext) {

            final J.ClassDeclaration classDeclaration =
                super.visitClassDeclaration(classDecl, executionContext);

            final List<Statement> statements =
                new ArrayList<>(classDeclaration.getBody().getStatements());

            final int firstConstructorIndex = findFirstConstructorIndex(statements);
            final List<J.MethodDeclaration> violatingConstructors =
                findViolatingConstructors(statements);

            final J.ClassDeclaration newClassDeclaration;

            // keep code intact if there are no constructors at all
            if (firstConstructorIndex == -1) {
                newClassDeclaration = classDeclaration;
            }
            else {
                final List<Statement> reordered =
                    reorderConstructors(statements, firstConstructorIndex, violatingConstructors);

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

        private List<J.MethodDeclaration> findViolatingConstructors(List<Statement> statements) {
            final List<J.MethodDeclaration> violating = new ArrayList<>();
            for (final Statement statement : statements) {
                if (statement instanceof J.MethodDeclaration method
                        && method.isConstructor()
                        && isAtViolationLocation(method)) {
                    violating.add(method);
                }
            }
            return violating;
        }

        private static List<Statement> reorderConstructors(
            List<Statement> statements, int firstConstructorIndex,
            List<J.MethodDeclaration> violatingConstructors) {

            int insertIndex = firstConstructorIndex;
            while (statements.get(insertIndex) instanceof J.MethodDeclaration method
                    && method.isConstructor()) {
                insertIndex++;
            }

            final List<Statement> result = new ArrayList<>(statements);
            result.removeAll(violatingConstructors);

            for (int index = 0; index < violatingConstructors.size(); index++) {
                result.add(insertIndex + index, violatingConstructors.get(index));
            }

            return result;
        }

        private boolean isAtViolationLocation(J.MethodDeclaration methodDeclaration) {
            final J.CompilationUnit cursor =
                    getCursor().firstEnclosing(J.CompilationUnit.class);

            final int line = PositionHelper.computeLinePosition(
                    cursor, methodDeclaration, getCursor());

            final int column = PositionHelper.computeColumnPosition(
                    cursor, methodDeclaration, getCursor());

            boolean matches = false;

            for (final CheckstyleViolation violation : violations) {
                if (violation.getLine() == line
                        && violation.getColumn() == column
                        && violation.getFilePath().endsWith(sourcePath)) {
                    matches = true;
                    break;
                }
            }

            return matches;
        }
    }
}
