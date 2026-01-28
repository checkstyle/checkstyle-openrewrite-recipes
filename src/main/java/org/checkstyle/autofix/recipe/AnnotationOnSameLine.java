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

import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.Space;

public class AnnotationOnSameLine extends Recipe {

    private static final String NEW_LINE = "\n";
    private static final String SPACE = " ";

    /**
     * Stored only to satisfy registry constructor contract.
     */
    private final List<CheckstyleViolation> violations;

    public AnnotationOnSameLine(List<CheckstyleViolation> violations) {
        this.violations = violations;
    }

    @Override
    public String getDisplayName() {
        return "Annotation on same line";
    }

    @Override
    public String getDescription() {
        return "Moves an annotation to the same line as its target.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new JavaIsoVisitor<ExecutionContext>() {

            @Override
            public J.Annotation visitAnnotation(J.Annotation annotation, ExecutionContext ctx) {
                J.Annotation result = super.visitAnnotation(annotation, ctx);

                if (result.getPrefix().getWhitespace().contains(NEW_LINE)) {
                    final Space prefix = result.getPrefix();
                    final String fixedWhitespace = prefix.getWhitespace()
                            .replace(NEW_LINE, SPACE)
                            .replaceAll("\\s+", SPACE);
                    result = result.withPrefix(prefix.withWhitespace(fixedWhitespace));
                }

                return result;
            }
        };
    }
}

