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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.checkstyle.autofix.marker.checks.NoWhitespaceAfterMarker;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.Expression;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JLeftPadded;
import org.openrewrite.java.tree.NameTree;
import org.openrewrite.java.tree.Space;

/**
 * Fixes Checkstyle NoWhitespaceAfter violations by removing whitespace after specific tokens.
 */
public class NoWhitespaceAfter extends Recipe {

    public NoWhitespaceAfter() {
    }

    @Override
    public String getDisplayName() {
        return "NoWhitespaceAfter recipe";
    }

    @Override
    public String getDescription() {
        return "Removes whitespace after specific tokens where forbidden by Checkstyle.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new NoWhitespaceAfterVisitor();
    }

    private static final class NoWhitespaceAfterVisitor extends JavaIsoVisitor<ExecutionContext> {

        private NoWhitespaceAfterVisitor() {
        }

        @Override
        public J.Unary visitUnary(J.Unary unary, ExecutionContext executionContext) {
            J.Unary unaryTree = super.visitUnary(unary, executionContext);
            if (isAtViolationLocation(unaryTree)) {
                final Space prefix = unaryTree.getExpression().getPrefix();
                unaryTree = unaryTree.withExpression(
                        unaryTree.getExpression().withPrefix(stripLeadingWhitespace(prefix)));
            }
            return unaryTree;
        }

        @Override
        public J.FieldAccess visitFieldAccess(J.FieldAccess fieldAccess,
                ExecutionContext executionContext) {
            J.FieldAccess fieldAccessTree = super.visitFieldAccess(fieldAccess, executionContext);
            if (isAtViolationLocation(fieldAccessTree)) {
                final Space prefix = fieldAccessTree.getName().getPrefix();
                fieldAccessTree = fieldAccessTree.withName(
                        fieldAccessTree.getName().withPrefix(stripLeadingWhitespace(prefix)));
            }
            return fieldAccessTree;
        }

        @Override
        public J.MethodInvocation visitMethodInvocation(J.MethodInvocation method,
                ExecutionContext executionContext) {
            J.MethodInvocation methodInvocationTree = super.visitMethodInvocation(method,
                    executionContext);
            if (isAtViolationLocation(methodInvocationTree, methodInvocationTree.getSelect())) {
                final Space prefix = methodInvocationTree.getName().getPrefix();
                methodInvocationTree = methodInvocationTree.withName(
                        methodInvocationTree.getName().withPrefix(stripLeadingWhitespace(prefix)));
            }
            return methodInvocationTree;
        }

        @Override
        public J.ArrayType visitArrayType(J.ArrayType arrayType,
                ExecutionContext executionContext) {
            J.ArrayType arrayTypeTree = super.visitArrayType(arrayType, executionContext);
            if (isAtViolationLocation(arrayTypeTree)) {
                final Space prefix = arrayTypeTree.getDimension().getBefore();
                arrayTypeTree = arrayTypeTree.withDimension(arrayTypeTree.getDimension()
                        .withBefore(stripLeadingWhitespace(prefix)));
            }
            return arrayTypeTree;
        }

        @Override
        public J.ArrayAccess visitArrayAccess(J.ArrayAccess arrayAccess,
                ExecutionContext executionContext) {
            J.ArrayAccess arrayAccessTree = super.visitArrayAccess(arrayAccess, executionContext);
            if (isAtViolationLocation(arrayAccessTree, arrayAccessTree.getDimension())) {
                final Space prefix = arrayAccessTree.getDimension().getPrefix();
                arrayAccessTree = arrayAccessTree.withDimension(
                        arrayAccessTree.getDimension().withPrefix(stripLeadingWhitespace(prefix)));
            }
            return arrayAccessTree;
        }

        @Override
        public J.NewArray visitNewArray(J.NewArray newArray, ExecutionContext executionContext) {
            J.NewArray newArrayTree = super.visitNewArray(newArray, executionContext);
            if (isAtViolationLocation(newArrayTree)) {
                final Expression first =
                        newArrayTree.getInitializer().getFirst();
                final Space prefix = first.getPrefix();
                final List<Expression> newInitializers =
                        new ArrayList<>(newArrayTree.getInitializer());
                newInitializers.set(0, first.withPrefix(stripLeadingWhitespace(prefix)));
                newArrayTree = newArrayTree.withInitializer(newInitializers);
            }
            return newArrayTree;
        }

        @Override
        public J.Annotation visitAnnotation(J.Annotation annotation,
                ExecutionContext executionContext) {
            J.Annotation annotationTree = super.visitAnnotation(annotation, executionContext);
            if (isAtViolationLocation(annotationTree)) {
                final NameTree nameTree =
                        annotationTree.getAnnotationType();
                final Space prefix = nameTree.getPrefix();
                annotationTree = annotationTree.withAnnotationType(
                        nameTree.withPrefix(stripLeadingWhitespace(prefix)));
            }
            return annotationTree;
        }

        @Override
        public J.TypeCast visitTypeCast(J.TypeCast typeCast, ExecutionContext executionContext) {
            J.TypeCast typeCastTree = super.visitTypeCast(typeCast, executionContext);
            if (isAtViolationLocation(typeCastTree, typeCastTree.getClazz())) {
                final Space prefix = typeCastTree.getExpression().getPrefix();
                typeCastTree = typeCastTree.withExpression(
                        typeCastTree.getExpression().withPrefix(stripLeadingWhitespace(prefix)));
            }
            return typeCastTree;
        }

        @Override
        public J.Synchronized visitSynchronized(J.Synchronized sync,
                ExecutionContext executionContext) {
            J.Synchronized syncTree = super.visitSynchronized(sync, executionContext);
            if (isAtViolationLocation(syncTree)) {
                final Space prefix = syncTree.getLock().getPrefix();
                syncTree = syncTree.withLock(
                        syncTree.getLock().withPrefix(stripLeadingWhitespace(prefix)));
            }
            return syncTree;
        }

        @Override
        public J.MemberReference visitMemberReference(J.MemberReference memberRef,
                ExecutionContext executionContext) {
            J.MemberReference memberRefTree = super.visitMemberReference(memberRef,
                    executionContext);
            if (isAtViolationLocation(memberRefTree, memberRefTree.getContaining())) {
                final JLeftPadded<J.Identifier> referencePadding =
                        memberRefTree.getPadding().getReference();
                final Space prefix = referencePadding.getBefore();
                memberRefTree = memberRefTree.getPadding().withReference(
                        referencePadding.withBefore(stripLeadingWhitespace(prefix)));
            }
            return memberRefTree;
        }

        private Space stripLeadingWhitespace(Space space) {
            return space.withWhitespace("");
        }

        private boolean isAtViolationLocation(J... trees) {
            return Arrays.stream(trees)
                    .filter(Objects::nonNull)
                    .map(J::getMarkers)
                    .anyMatch(markers -> {
                        return markers.findFirst(NoWhitespaceAfterMarker.class).isPresent();
                    });
        }

    }

}
