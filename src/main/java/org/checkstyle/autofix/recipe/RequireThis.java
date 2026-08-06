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
import org.openrewrite.Tree;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaVisitor;
import org.openrewrite.java.tree.Expression;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JLeftPadded;
import org.openrewrite.java.tree.JavaType;
import org.openrewrite.java.tree.Space;
import org.openrewrite.marker.Markers;

/**
 * Fixes Checkstyle RequireThis violations by adding 'this.' prefix
 * to references of instance variables and methods.
 */
public class RequireThis extends Recipe {

    public RequireThis() {
    }

    @Override
    public String getDisplayName() {
        return "RequireThis recipe";
    }

    @Override
    public String getDescription() {
        return "Adds 'this.' prefix to references of instance variables and methods "
                + "that require explicit 'this.' qualification.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new RequireThisVisitor();
    }

    private final class RequireThisVisitor extends JavaVisitor<ExecutionContext> {

        private static final String THIS_PREFIX = "this";

        private RequireThisVisitor() {
        }

        @Override
        public J visitIdentifier(J.Identifier identifier, ExecutionContext executionContext) {
            final J.Identifier ident = (J.Identifier) super.visitIdentifier(
                    identifier, executionContext);
            J result = ident;

            if (isRequireThisViolation(ident)) {
                if (!isParentFieldAccess() && !isMethodInvocationName()) {
                    result = createThisFieldAccess(ident);
                }
            }
            return result;
        }

        @Override
        public J visitMethodInvocation(J.MethodInvocation method,
                                        ExecutionContext executionContext) {
            J.MethodInvocation result = (J.MethodInvocation) super.visitMethodInvocation(
                    method, executionContext);

            final boolean hasViolation = isRequireThisViolation(result.getName());

            if (hasViolation && result.getSelect() == null) {
                final JavaType type = result.getMethodType().getDeclaringType();

                final J.Identifier thisIdent = new J.Identifier(
                        Tree.randomId(),
                        Space.EMPTY,
                        Markers.EMPTY,
                        null,
                        THIS_PREFIX,
                        type,
                        null
                );
                result = result.withSelect(thisIdent);
            }
            return result;
        }

        private Expression createThisFieldAccess(J.Identifier ident) {
            final J.Identifier thisIdent = new J.Identifier(
                    Tree.randomId(),
                    Space.EMPTY,
                    Markers.EMPTY,
                    null,
                    THIS_PREFIX,
                    null,
                    null
            );
            return new J.FieldAccess(
                    Tree.randomId(),
                    ident.getPrefix(),
                    Markers.EMPTY,
                    thisIdent,
                    new JLeftPadded<>(Space.EMPTY,
                            ident.withPrefix(Space.EMPTY), Markers.EMPTY),
                    null
            );
        }

        private boolean isParentFieldAccess() {
            return getCursor().getParentTreeCursor().getValue() instanceof J.FieldAccess;
        }

        private boolean isMethodInvocationName() {
            final Object parent = getCursor().getParentTreeCursor().getValue();
            boolean isMethodName = false;
            if (parent instanceof J.MethodInvocation method) {
                isMethodName = method.getName() == getCursor().getValue();
            }
            return isMethodName;
        }

        private boolean isRequireThisViolation(J tree) {
            return tree.getMarkers()
                    .findAll(CheckstyleViolationMarker.class).stream()
                    .anyMatch(marker -> marker.isFor(CheckFullName.REQUIRE_THIS));
        }
    }

}
