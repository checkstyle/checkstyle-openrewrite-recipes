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
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.checkstyle.autofix.CheckFullName;
import org.checkstyle.autofix.marker.CheckstyleViolationMarker;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.Flag;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JavaType;

/**
 * Fixes Checkstyle AvoidStarImport violations by expanding star imports into individual imports.
 */
public class AvoidStarImport extends Recipe {

    @Override
    public String getDisplayName() {
        return "Avoid Star Import recipe";
    }

    @Override
    public String getDescription() {
        return "Expands star imports into individual ones to avoid star imports.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new AvoidStarImportVisitor();
    }

    private final class AvoidStarImportVisitor extends JavaIsoVisitor<ExecutionContext> {

        private final Set<String> packagesToExpand = new HashSet<>();
        private final Set<UUID> starImportIdsToRemove = new HashSet<>();

        @Override
        public J.CompilationUnit visitCompilationUnit(J.CompilationUnit compilationUnit,
                                                      ExecutionContext executionContext) {

            for (J.Import importStmt : compilationUnit.getImports()) {
                if (isAtViolationLocation(importStmt)) {
                    starImportIdsToRemove.add(importStmt.getId());
                    final String packageName = importStmt.getQualid().getTarget()
                            .printTrimmed(getCursor());
                    packagesToExpand.add(packageName);
                }
            }

            final J.CompilationUnit result = compilationUnit.withImports(
                    compilationUnit.getImports().stream()
                            .filter(importStmt -> {
                                return !starImportIdsToRemove.contains(importStmt.getId());
                            }).toList()
            );

            return super.visitCompilationUnit(result, executionContext);
        }

        @Override
        public J.Identifier visitIdentifier(J.Identifier identifier,
                                            ExecutionContext executionContext) {
            addImportForType(identifier.getType());
            addStaticImportForField(identifier);
            return identifier;
        }

        private void addImportForType(JavaType type) {
            if (type instanceof JavaType.FullyQualified fullyQualified) {
                final String fullyQualifiedName = fullyQualified.getFullyQualifiedName();
                final int lastDot = fullyQualifiedName.lastIndexOf('.');
                if (lastDot != -1) {
                    final String packageName = fullyQualifiedName.substring(0, lastDot);
                    if (packagesToExpand.contains(packageName)) {
                        maybeAddImport(fullyQualifiedName);
                    }
                }
            }
        }

        @Override
        public J.MethodInvocation visitMethodInvocation(J.MethodInvocation method,
                                                        ExecutionContext executionContext) {
            addStaticImportForMethod(method);
            return super.visitMethodInvocation(method, executionContext);
        }

        private void addStaticImportForMethod(J.MethodInvocation method) {
            Optional.ofNullable(method.getMethodType())
                    .filter(type -> type.hasFlags(Flag.Static))
                    .map(JavaType.Method::getDeclaringType)
                    .map(JavaType.FullyQualified::getFullyQualifiedName)
                    .filter(packagesToExpand::contains)
                    .ifPresent(ownerFqn -> maybeAddImport(ownerFqn, method.getSimpleName(), false));
        }

        private void addStaticImportForField(J.Identifier identifier) {
            final JavaType.Variable fieldType = identifier.getFieldType();
            if (fieldType != null && fieldType.hasFlags(Flag.Static)
                    && fieldType.getOwner() instanceof JavaType.FullyQualified fqOwner) {
                final String ownerFqn = fqOwner.getFullyQualifiedName();
                if (packagesToExpand.contains(ownerFqn)) {
                    maybeAddImport(ownerFqn, identifier.getSimpleName(), false);
                }
            }
        }

        private boolean isAtViolationLocation(J.Import importStmt) {
            return importStmt.getMarkers()
                    .findAll(CheckstyleViolationMarker.class).stream()
                    .anyMatch(marker -> marker.isFor(CheckFullName.AVOID_STAR_IMPORT));
        }
    }
}
