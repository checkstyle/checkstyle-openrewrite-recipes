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

package org.checkstyle.autofix;

import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;

/**
 * Recipe to rename classes starting with 'Input' to 'Output'.
 * For example, a class named InputExample will be renamed to OutputExample.
 */
public class InputClassRenamer extends Recipe {

    @Override
    public String getDisplayName() {
        return "Rename Input-prefixed classes";
    }

    @Override
    public String getDescription() {
        return "Renames classes from InputXxx to OutputXxx.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new ClassRenameVisitor();
    }

    private static final class ClassRenameVisitor extends JavaIsoVisitor<ExecutionContext> {

        private static final String FROM_PREFIX = "Input";
        private static final String TO_PREFIX = "Output";

        @Override
        public J.ClassDeclaration visitClassDeclaration(J.ClassDeclaration classDecl,
                                                        ExecutionContext executionContext) {
            final String newName = renameIfMatch(classDecl.getSimpleName());
            final J.ClassDeclaration result;
            if (newName != null) {
                result = classDecl.withName(classDecl.getName().withSimpleName(newName));
            }
            else {
                result = classDecl;
            }
            return result;
        }

        @Override
        public J.NewClass visitNewClass(J.NewClass constructorNode,
                                        ExecutionContext executionContext) {
            J.NewClass result = constructorNode;
            if (constructorNode.getClazz() instanceof J.Identifier) {
                final J.Identifier clazz = (J.Identifier) constructorNode.getClazz();
                final String newName = renameIfMatch(clazz.getSimpleName());
                if (newName != null) {
                    result = constructorNode.withClazz(clazz.withSimpleName(newName));
                }
            }
            return result;
        }

        private String renameIfMatch(String originalName) {
            final String result;
            if (originalName.startsWith(FROM_PREFIX)) {
                result = TO_PREFIX + originalName.substring(FROM_PREFIX.length());
            }
            else {
                result = null;
            }
            return result;
        }
    }
}
