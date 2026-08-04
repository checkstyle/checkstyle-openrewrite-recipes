///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle-openrewrite-recipes: Automatically fix Checkstyle violations with OpenRewrite.
// Copyright (C) 2026 The Checkstyle OpenRewrite Recipes Authors
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

import java.util.List;

import org.checkstyle.autofix.marker.checks.EmptyForIteratorPadMarker;
import org.checkstyle.autofix.parser.CheckConfiguration;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JRightPadded;
import org.openrewrite.java.tree.Space;
import org.openrewrite.java.tree.Statement;

/**
 * Fixes Checkstyle EmptyForIteratorPad violations.
 */
public class EmptyForIteratorPad extends Recipe {

    private static final String OPTION_PROPERTY = "option";
    private static final String OPTION_SPACE = "space";
    private static final String OPTION_NOSPACE = "nospace";

    private final CheckConfiguration config;

    public EmptyForIteratorPad(CheckConfiguration config) {
        this.config = config;
    }

    @Override
    public String getDisplayName() {
        return "EmptyForIteratorPad recipe";
    }

    @Override
    public String getDescription() {
        return "Fixes Checkstyle EmptyForIteratorPad violations.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new EmptyForIteratorPadVisitor();
    }

    private final class EmptyForIteratorPadVisitor extends JavaIsoVisitor<ExecutionContext> {

        private EmptyForIteratorPadVisitor() {
        }

        @Override
        public J.ForLoop visitForLoop(J.ForLoop forLoop, ExecutionContext executionContext) {
            J.ForLoop result = super.visitForLoop(forLoop, executionContext);
            if (isViolationDetected(result)) {
                result = applyFix(result);
            }
            return result;
        }

        private boolean isViolationDetected(J.ForLoop forLoop) {
            boolean hasViolation = hasViolationMarker(forLoop)
                    || hasViolationMarker(forLoop.getControl());
            if (!hasViolation) {
                final List<JRightPadded<Statement>> updateList = forLoop.getControl()
                        .getPadding().getUpdate();
                if (updateList.size() == 1
                        && updateList.getFirst().getElement() instanceof J.Empty empty) {
                    hasViolation = hasViolationMarker(empty);
                }
            }
            return hasViolation;
        }

        private boolean hasViolationMarker(J tree) {
            return tree != null
                    && tree.getMarkers().findFirst(EmptyForIteratorPadMarker.class).isPresent();
        }

        private J.ForLoop applyFix(J.ForLoop forLoop) {
            J.ForLoop result = forLoop;
            final String option = config.getPropertyOrDefault(OPTION_PROPERTY, OPTION_NOSPACE);
            final J.ForLoop.Control control = result.getControl();
            final List<JRightPadded<Statement>> updateList = control.getPadding().getUpdate();
            if (updateList.size() == 1
                    && updateList.getFirst().getElement() instanceof J.Empty emptyElement) {
                final JRightPadded<Statement> emptyUpdate = updateList.getFirst();
                final String desiredWhitespace = getDesiredWhitespace(option);
                if (!desiredWhitespace.equals(emptyElement.getPrefix().getWhitespace())) {
                    final Space newPrefix = Space.build(desiredWhitespace,
                            emptyElement.getPrefix().getComments());
                    final J.Empty newEmptyElement = emptyElement.withPrefix(newPrefix);
                    final JRightPadded<Statement> newEmptyUpdate = emptyUpdate
                            .withElement(newEmptyElement);
                    final J.ForLoop.Control newControl = control.getPadding()
                            .withUpdate(List.of(newEmptyUpdate));
                    result = result.withControl(newControl);
                }
            }
            return result;
        }

        private static String getDesiredWhitespace(String option) {
            final String desiredWhitespace;
            if (OPTION_SPACE.equals(option)) {
                desiredWhitespace = " ";
            }
            else {
                desiredWhitespace = "";
            }
            return desiredWhitespace;
        }

    }

}
