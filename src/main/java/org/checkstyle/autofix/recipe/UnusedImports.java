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
 * Fixes Checkstyle UnusedImports violations by removing unused imports.
 */
public class UnusedImports extends Recipe {

    @Override
    public String getDisplayName() {
        return "UnusedImports Recipe";
    }

    @Override
    public String getDescription() {
        return "Remove unused imports";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new UnusedImportsVisitor();
    }

    private static final class UnusedImportsVisitor extends JavaIsoVisitor<ExecutionContext> {

        @Override
        public J.CompilationUnit visitCompilationUnit(J.CompilationUnit cu,
                                                      ExecutionContext executionContext) {

            return cu.withImports(
                    cu.getImports().stream()
                            .filter(importStmt -> !hasUnusedImportMarker(importStmt))
                            .toList()
            );
        }

        private boolean hasUnusedImportMarker(J.Import importStmt) {
            return importStmt.getMarkers()
                    .findAll(CheckstyleViolationMarker.class).stream()
                    .anyMatch(marker -> marker.isFor(CheckFullName.UNUSED_IMPORT));
        }
    }
}
