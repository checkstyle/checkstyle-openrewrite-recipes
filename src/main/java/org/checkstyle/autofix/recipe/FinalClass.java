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
public class FinalClass extends AbstractScanningRecipe {

    public FinalClass(final List<CheckstyleViolation> violations) {
        super(violations);
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
        public J.CompilationUnit visitCompilationUnit(
                J.CompilationUnit cu, ExecutionContext executionContext) {
            this.sourcePath = cu.getSourcePath();
            return super.visitCompilationUnit(cu, executionContext);
        }

        @Override
        public J.ClassDeclaration visitClassDeclaration(
                final J.ClassDeclaration classDeclaration,
                final ExecutionContext executionContext) {

            final J.ClassDeclaration result =
                    super.visitClassDeclaration(classDeclaration, executionContext);

            final J.CompilationUnit cursor =
                    getCursor().firstEnclosing(J.CompilationUnit.class);

            final int line = PositionHelper.computeLinePosition(
                    cursor, classDeclaration, getCursor());

            for (CheckstyleViolation violation : getViolations()) {
                if (violation.getLine() == line
                        && violation.getFilePath().endsWith(sourcePath)) {
                    acc.getMatched().computeIfAbsent(classDeclaration.getId(),
                            key -> new ArrayList<>()).add(violation);
                    break;
                }
            }

            return result;
        }
    }

    private static final class FixerVisitor extends JavaIsoVisitor<ExecutionContext> {

        private final ViolationAccumulator acc;

        private FixerVisitor(ViolationAccumulator acc) {
            this.acc = acc;
        }

        @Override
        public J.ClassDeclaration visitClassDeclaration(
                final J.ClassDeclaration classDeclaration,
                final ExecutionContext executionContext) {

            J.ClassDeclaration result =
                    super.visitClassDeclaration(classDeclaration, executionContext);

            if (acc.getMatched().containsKey(result.getId()) && !hasFinalModifier(result)) {
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
    }
}
