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

import org.checkstyle.autofix.CheckFullName;
import org.checkstyle.autofix.marker.CheckstyleViolationMarker;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;

/**
 * Fixes Checkstyle UpperEll violations by replacing lowercase 'l' suffix
 * in long literals with uppercase 'L'.
 */
public class UpperEll extends Recipe {

    @Override
    public String getDisplayName() {
        return "UpperEll recipe";
    }

    @Override
    public String getDescription() {
        return "Replace lowercase 'l' suffix in long literals with uppercase 'L' "
                + "to improve readability.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new UpperEllVisitor();
    }

    private final class UpperEllVisitor extends JavaIsoVisitor<ExecutionContext> {

        private static final String LOWERCASE_L = "l";
        private static final String UPPERCASE_L = "L";

        @Override
        public J.Literal visitLiteral(J.Literal literal, ExecutionContext executionContext) {
            final String valueSource = literal.getValueSource();
            J.Literal result = literal;

            if (valueSource.endsWith(LOWERCASE_L)
                    && isAtViolationLocation(literal)) {
                final String numericPart = valueSource.substring(0, valueSource.length() - 1);
                result = literal.withValueSource(numericPart + UPPERCASE_L);
            }

            return result;
        }

        private boolean isAtViolationLocation(J.Literal literal) {
            return literal.getMarkers()
                    .findAll(CheckstyleViolationMarker.class).stream()
                    .anyMatch(marker -> marker.isFor(CheckFullName.UPPER_ELL));
        }
    }
}
