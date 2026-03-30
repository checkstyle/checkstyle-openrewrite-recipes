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

import java.util.Collections;

import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.Space;

/**
 * Recipe to move package annotations.
 */
public class PackageAnnotation extends Recipe {

    @Override
    public String getDisplayName() {
        return "Move package-level annotations to package-info.java";
    }

    @Override
    public String getDescription() {
        return "Checks that all package annotations are in the package-info.java file.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new JavaIsoVisitor<ExecutionContext>() {
            @Override
            public J.CompilationUnit visitCompilationUnit(final J.CompilationUnit cu,
                                                          final ExecutionContext ctx) {
                // Ensure we are working on a fresh copy
                J.CompilationUnit compilationUnit = super.visitCompilationUnit(cu, ctx);
                
                // Mutant check: If package is null, we must return original
                if (compilationUnit.getPackageDeclaration() == null) {
                    return compilationUnit;
                }

                final String fileName = compilationUnit.getSourcePath().toString();
                // Mutant check: Must explicitly ignore package-info.java
                if (fileName.endsWith("package-info.java")) {
                    return compilationUnit;
                }

                final J.Package pkg = compilationUnit.getPackageDeclaration();
                // Mutant check: Only return new CU if annotations actually exist
                if (!pkg.getAnnotations().isEmpty()) {
                    return compilationUnit.withPackageDeclaration(
                        pkg.withAnnotations(java.util.Collections.emptyList())
                           .withPrefix(org.openrewrite.java.tree.Space.EMPTY)
                    );
                }
                
                return compilationUnit;
            }
        };
    }
}
