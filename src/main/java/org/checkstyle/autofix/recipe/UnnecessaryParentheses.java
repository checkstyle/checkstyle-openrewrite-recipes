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

import org.checkstyle.autofix.marker.checks.UnnecessaryParenthesesMarker;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaVisitor;
import org.openrewrite.java.tree.J;

public class UnnecessaryParentheses extends Recipe {

    public UnnecessaryParentheses() {
    }

    @Override
    public String getDisplayName() {
        return "UnnecessaryParentheses Recipe";
    }

    @Override
    public String getDescription() {
        return "Removes unnecessary parentheses at Checkstyle violation locations.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new UnnecessaryParenthesesVisitor();
    }

    private final class UnnecessaryParenthesesVisitor extends JavaVisitor<ExecutionContext> {
        private UnnecessaryParenthesesVisitor() {
        }

        @Override
        public <T extends J> J visitParentheses(
                J.Parentheses<T> parens, ExecutionContext executionContext) {
            final J.Parentheses<?> parensNode =
                    (J.Parentheses<?>) super.visitParentheses(parens, executionContext);
            J result = parensNode;
            if (isAtViolationLocation(parensNode)) {
                result = parensNode.getTree().withPrefix(parensNode.getPrefix());
            }
            return result;
        }

        private boolean isAtViolationLocation(J.Parentheses<?> parens) {
            return parens.getMarkers()
                    .findFirst(UnnecessaryParenthesesMarker.class).isPresent();
        }

    }

}
