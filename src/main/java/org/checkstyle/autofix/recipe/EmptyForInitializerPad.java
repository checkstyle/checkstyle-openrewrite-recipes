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

import java.util.Collections;

import org.checkstyle.autofix.marker.checks.EmptyForInitializerPadMarker;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.Space;
import org.openrewrite.marker.Markers;

/**
 * Fixes Checkstyle EmptyForInitializerPad violations by removing spacing
 * before an empty for-loop initializer.
 */
public class EmptyForInitializerPad extends Recipe {

    public EmptyForInitializerPad() {
    }

    @Override
    public String getDisplayName() {
        return "EmptyForInitializerPad recipe";
    }

    @Override
    public String getDescription() {
        return "Fixes padding around empty for-loop initializers.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new EmptyForInitializerPadVisitor();
    }

    private static final class EmptyForInitializerPadVisitor
            extends JavaIsoVisitor<ExecutionContext> {

        private static final String SPACE = " ";

        EmptyForInitializerPadVisitor() {
        }

        @Override
        public J.Empty visitEmpty(J.Empty empty, ExecutionContext executionContext) {
            J.Empty result = empty;

            final boolean hasViolation = !empty.getMarkers()
                    .findAll(EmptyForInitializerPadMarker.class)
                    .isEmpty();

            if (hasViolation) {
                final J.Empty fixedEmpty;
                if (empty.getPrefix().getWhitespace().isEmpty()) {
                    fixedEmpty = empty.withPrefix(Space.build(SPACE, Collections.emptyList()));
                }
                else {
                    fixedEmpty = empty.withPrefix(Space.EMPTY);
                }

                result = fixedEmpty.withMarkers(Markers.EMPTY);
            }

            return result;
        }

    }

}
