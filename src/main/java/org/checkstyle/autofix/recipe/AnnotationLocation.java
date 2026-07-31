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

import java.util.List;

import org.checkstyle.autofix.CheckFullName;
import org.checkstyle.autofix.marker.CheckstyleViolationMarker;
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
            J.ClassDeclaration decl = super.visitClassDeclaration(classDecl, executionContext);

            if (lastAnnotationViolates(decl.getLeadingAnnotations())) {
                decl = autoFormat(decl, decl.getName(), executionContext,
                        getCursor().getParentOrThrow());
            }
            return decl;
        }

        @Override
        public J.MethodDeclaration visitMethodDeclaration(J.MethodDeclaration method,
                                                          ExecutionContext executionContext) {
            J.MethodDeclaration decl = super.visitMethodDeclaration(method, executionContext);

            if (lastAnnotationViolates(decl.getLeadingAnnotations())) {
                decl = autoFormat(decl, decl.getName(), executionContext,
                        getCursor().getParentOrThrow());
            }
            return decl;
        }

        @Override
        public J.VariableDeclarations visitVariableDeclarations(J.VariableDeclarations multiVar,
                                                                ExecutionContext executionContext) {
            J.VariableDeclarations decl =
                    super.visitVariableDeclarations(multiVar, executionContext);

            if (lastAnnotationViolates(decl.getLeadingAnnotations())) {
                decl = autoFormat(decl, decl.getVariables().get(0).getName(), executionContext,
                        getCursor().getParentOrThrow());
            }
            return decl;
        }

        private boolean hasMarker(J.Annotation anno) {
            return anno.getMarkers().findAll(CheckstyleViolationMarker.class).stream()
                    .anyMatch(marker -> marker.isFor(CheckFullName.ANNOTATION_LOCATION));
        }

        private boolean lastAnnotationViolates(List<J.Annotation> annos) {
            boolean result = false;
            if (!annos.isEmpty()) {
                result = hasMarker(annos.get(annos.size() - 1));
            }
            return result;
        }

    }

}
