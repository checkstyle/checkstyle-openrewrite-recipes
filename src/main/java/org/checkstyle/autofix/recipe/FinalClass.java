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
import org.openrewrite.java.tree.J.Modifier;
import org.openrewrite.java.tree.Space;
import org.openrewrite.marker.Markers;

/**
 * Fixes Checkstyle FinalClass violations by adding 'final' modifier
 * to classes that have only private constructors or no constructors.
 */
public class FinalClass extends Recipe {

    private final List<CheckstyleViolation> violations;

    public FinalClass(final List<CheckstyleViolation> violations) {
        this.violations = violations;
    }

    @Override
    public String getDisplayName() {
        return "FinalClass recipe";
    }

    @Override
    public String getDescription() {
        return "Add 'final' modifier to classes that have only private constructors.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new FinalClassVisitor();
    }

    private final class FinalClassVisitor
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
                final J.ClassDeclaration classDeclaration,
                final ExecutionContext executionContext) {

            J.ClassDeclaration result =
                    super.visitClassDeclaration(classDeclaration, executionContext);

            if (isAtViolationLocation(classDeclaration) && !hasFinalModifier(result)) {
                result = addFinalModifier(result);
            }

            return result;
        }

        private J.ClassDeclaration addFinalModifier(J.ClassDeclaration classDeclaration) {
            J.ClassDeclaration result = classDeclaration;
            final List<J.Modifier> modifiers = new ArrayList<>(classDeclaration.getModifiers());
            final int insertPosition = getInsertPosition(modifiers);

            Space prefixForFinal = Space.SINGLE_SPACE;

            if (modifiers.isEmpty()) {
                prefixForFinal = classDeclaration.getPadding().getKind().getPrefix();
                result = classDeclaration.getPadding()
                        .withKind(classDeclaration.getPadding()
                                .getKind()
                                .withPrefix(Space.SINGLE_SPACE));
            }
            else if (insertPosition == 0) {
                prefixForFinal = modifiers.get(0).getPrefix();
                modifiers.set(0, modifiers.get(0).withPrefix(Space.SINGLE_SPACE));
            }

            modifiers.add(
                    insertPosition,
                    new J.Modifier(
                            org.openrewrite.Tree.randomId(),
                            prefixForFinal,
                            Markers.EMPTY,
                            null,
                            Modifier.Type.Final,
                            new ArrayList<>()
                    )
            );

            return result.withModifiers(modifiers);
        }

        private int getInsertPosition(List<J.Modifier> modifiers) {
            int insertPosition = 0;

            for (int index = 0; index < modifiers.size(); index++) {
                if (isAccessOrStatic(modifiers.get(index).getType())) {
                    insertPosition = index + 1;
                }
            }

            return insertPosition;
        }

        private boolean isAccessOrStatic(Modifier.Type type) {
            final boolean result;

            switch (type) {
                case Public:
                case Protected:
                case Private:
                case Static:
                    result = true;
                    break;
                default:
                    result = false;
                    break;
            }

            return result;
        }

        private static boolean hasFinalModifier(
                final J.ClassDeclaration classDeclaration) {

            boolean foundFinal = false;

            for (J.Modifier modifier : classDeclaration.getModifiers()) {
                if (modifier.getType() == Modifier.Type.Final) {
                    foundFinal = true;
                    break;
                }
            }

            return foundFinal;
        }

        private boolean isAtViolationLocation(J.ClassDeclaration classDeclaration) {
            final J.CompilationUnit cursor =
                    getCursor().firstEnclosing(J.CompilationUnit.class);

            final int line = PositionHelper.computeLinePosition(
                    cursor, classDeclaration, getCursor());

            boolean matches = false;

            for (CheckstyleViolation violation : violations) {
                if (violation.getLine() == line
                        && violation.getFilePath().endsWith(sourcePath)) {
                    matches = true;
                    break;
                }
            }

            return matches;
        }
    }
}
