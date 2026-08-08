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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
 * Fixes Checkstyle AvoidStaticImport violations by removing static imports
 * and fully qualifying their usages in the code.
 */
public class AvoidStaticImport extends Recipe {

    public AvoidStaticImport() {
    }

    @Override
    public String getDisplayName() {
        return "Avoid static import recipe";
    }

    @Override
    public String getDescription() {
        return "Removes static imports and fully qualifies their usages.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new AvoidStaticImportVisitor();
    }

    private static boolean isAtViolationLocation(J.Import importStmt) {
        return hasViolation(importStmt);
    }

    private static boolean hasViolation(Tree tree) {
        return tree.getMarkers()
                .findAll(CheckstyleViolationMarker.class).stream()
                .anyMatch(marker -> marker.isFor(CheckFullName.AVOID_STATIC_IMPORT));
    }

    private static final class AvoidStaticImportVisitor extends JavaVisitor<ExecutionContext> {

        private final Set<String> classesToQualify = new HashSet<>();
        private final Set<UUID> importsToRemove = new HashSet<>();

        private AvoidStaticImportVisitor() {
        }

        @Override
        public J visitCompilationUnit(J.CompilationUnit compilationUnit,
                                      ExecutionContext executionContext) {
            for (J.Import importStmt : compilationUnit.getImports()) {
                if (isAtViolationLocation(importStmt)) {
                    importsToRemove.add(importStmt.getId());
                    final String target = importStmt.getQualid().getTarget()
                            .printTrimmed(getCursor());
                    classesToQualify.add(target);
                }
            }

            final J.CompilationUnit cu = (J.CompilationUnit) super.visitCompilationUnit(
                    compilationUnit, executionContext);
            return cu.withImports(cu.getImports().stream()
                    .filter(importId -> !importsToRemove.contains(importId.getId()))
                    .toList());
        }

        @Override
        public J visitMethodInvocation(J.MethodInvocation method,
                                       ExecutionContext executionContext) {
            J.MethodInvocation modifiedMethod = method;
            if (method.getSelect() == null && method.getMethodType() != null) {
                final JavaType.FullyQualified declaringType =
                        method.getMethodType().getDeclaringType();
                if (matchesClassToQualify(declaringType.getFullyQualifiedName())) {
                    maybeAddImport(getTopLevelClassName(declaringType));
                    final Expression select =
                            buildQualifier(declaringType);
                    modifiedMethod = method.withSelect(select);
                }
            }
            return super.visitMethodInvocation(modifiedMethod, executionContext);
        }

        @Override
        public J visitIdentifier(J.Identifier identifier, ExecutionContext executionContext) {
            J result = identifier;
            JavaType.FullyQualified ownerToQualify = null;

            final JavaType.Variable fieldType = identifier.getFieldType();
            if (fieldType != null) {
                if (fieldType.getOwner() instanceof JavaType.FullyQualified) {
                    ownerToQualify = (JavaType.FullyQualified) fieldType.getOwner();
                }
            }
            else if (identifier.getType() instanceof JavaType.FullyQualified) {
                ownerToQualify = ((JavaType.FullyQualified) identifier.getType()).getOwningClass();
            }

            if (ownerToQualify != null) {
                if (!shouldSkipQualifying(identifier)
                        && matchesClassToQualify(ownerToQualify.getFullyQualifiedName())) {
                    maybeAddImport(getTopLevelClassName(ownerToQualify));
                    final Expression target =
                            buildQualifier(ownerToQualify);
                    result = new J.FieldAccess(Tree.randomId(), identifier.getPrefix(),
                            Markers.EMPTY, target, new JLeftPadded<>(Space.EMPTY,
                                    identifier.withPrefix(Space.EMPTY), Markers.EMPTY),
                            null);
                }
            }

            return result;
        }

        private Expression buildQualifier(JavaType.FullyQualified type) {
            Expression qualifier;
            if (type.getOwningClass() != null) {
                qualifier = buildQualifier(type.getOwningClass());
                final String className = type.getClassName();
                final int dotIndex = className.lastIndexOf('.');
                final int dollarIndex = className.lastIndexOf('$');
                final String simpleName = className.substring(
                        Math.max(dotIndex, dollarIndex) + 1);
                qualifier = new J.FieldAccess(Tree.randomId(), Space.EMPTY, Markers.EMPTY,
                        qualifier, new JLeftPadded<>(Space.EMPTY,
                                new J.Identifier(Tree.randomId(), Space.EMPTY, Markers.EMPTY,
                                        null, simpleName, type, null), Markers.EMPTY), type);
            }
            else {
                qualifier = new J.Identifier(Tree.randomId(), Space.EMPTY, Markers.EMPTY,
                        null, type.getClassName(), type, null);
            }
            return qualifier;
        }

        private boolean shouldSkipQualifying(J.Identifier ident) {
            final Object parentValue = getCursor().getParentTreeCursor().getValue();
            final boolean skip;

            if (parentValue instanceof J.FieldAccess || parentValue instanceof J.Case) {
                skip = true;
            }
            else if (parentValue instanceof J.VariableDeclarations.NamedVariable) {
                final J.VariableDeclarations.NamedVariable namedVar =
                        (J.VariableDeclarations.NamedVariable) parentValue;
                skip = namedVar.getName().getId().equals(ident.getId());
            }
            else if (parentValue instanceof J.MethodDeclaration) {
                final J.MethodDeclaration methodDecl = (J.MethodDeclaration) parentValue;
                skip = methodDecl.getName().getId().equals(ident.getId());
            }
            else if (parentValue instanceof J.ClassDeclaration) {
                final J.ClassDeclaration classDecl = (J.ClassDeclaration) parentValue;
                skip = classDecl.getName().getId().equals(ident.getId());
            }
            else if (parentValue instanceof J.MethodInvocation) {
                final J.MethodInvocation methodInv = (J.MethodInvocation) parentValue;
                skip = methodInv.getName().getId().equals(ident.getId());
            }
            else {
                skip = false;
            }

            return skip;
        }

        private boolean matchesClassToQualify(String fqn) {
            return classesToQualify.contains(fqn)
                    || classesToQualify.contains(fqn.replace('$', '.'));
        }

        private String getTopLevelClassName(JavaType.FullyQualified type) {
            JavaType.FullyQualified topLevel = type;
            while (topLevel.getOwningClass() != null) {
                topLevel = topLevel.getOwningClass();
            }
            return topLevel.getFullyQualifiedName();
        }
    }

}
