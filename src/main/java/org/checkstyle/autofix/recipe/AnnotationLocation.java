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

import java.util.List;

import org.checkstyle.autofix.marker.checks.AnnotationLocationMarker;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;

public class AnnotationLocation extends Recipe {

    public AnnotationLocation() {
    }

    @Override
    public String getDisplayName() {
        return "Annotation location";
    }

    @Override
    public String getDescription() {
        return "Checks whether annotation is on a separate line.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new AnnotationLocationVisitor();
    }

    private final class AnnotationLocationVisitor extends JavaIsoVisitor<ExecutionContext> {

        private AnnotationLocationVisitor() {
        }

        @Override
        public J.ClassDeclaration visitClassDeclaration(J.ClassDeclaration classDecl,
                                                      ExecutionContext executionContext) {
            final J.ClassDeclaration decl =
                    super.visitClassDeclaration(classDecl, executionContext);
            return autoFormatIfViolates(decl, decl.getLeadingAnnotations(), decl.getName(),
                    executionContext);
        }

        @Override
        public J.MethodDeclaration visitMethodDeclaration(J.MethodDeclaration method,
                                                          ExecutionContext executionContext) {
            final J.MethodDeclaration decl =
                    super.visitMethodDeclaration(method, executionContext);
            return autoFormatIfViolates(decl, decl.getLeadingAnnotations(), decl.getName(),
                    executionContext);
        }

        @Override
        public J.VariableDeclarations visitVariableDeclarations(J.VariableDeclarations multiVar,
                                                                ExecutionContext executionContext) {
            final J.VariableDeclarations decl =
                    super.visitVariableDeclarations(multiVar, executionContext);
            return autoFormatIfViolates(decl, decl.getLeadingAnnotations(),
                    decl.getVariables().getFirst().getName(), executionContext);
        }

        private <T extends J> T autoFormatIfViolates(T decl, List<J.Annotation> annotations, J name,
                                                     ExecutionContext executionContext) {
            T formattedDecl = decl;
            if (lastAnnotationViolates(annotations)) {
                formattedDecl = autoFormat(decl, name, executionContext,
                        getCursor().getParentOrThrow());
            }
            return formattedDecl;
        }

        private boolean lastAnnotationViolates(List<J.Annotation> annos) {
            boolean result = false;
            if (!annos.isEmpty()) {
                result = hasMarker(annos.getLast());
            }
            return result;
        }

        private boolean hasMarker(J.Annotation anno) {
            return anno.getMarkers().findFirst(AnnotationLocationMarker.class).isPresent();
        }

    }

}
