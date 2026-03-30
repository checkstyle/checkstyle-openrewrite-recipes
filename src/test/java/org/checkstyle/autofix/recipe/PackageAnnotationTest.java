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

import org.junit.jupiter.api.Test;
import org.openrewrite.java.Assertions;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

/**
 * Test for PackageAnnotation recipe.
 */
class PackageAnnotationTest implements RewriteTest {

    @Test
    void shouldRemoveAnnotationFromRegularFile() {
        rewriteRun(
            spec -> spec.recipe(new PackageAnnotation()),
            Assertions.java(
                "@Deprecated\n"
                + "package org.checkstyle.test;\n"
                + "\n"
                + "public class Example {}",
                "package org.checkstyle.test;\n"
                + "\n"
                + "public class Example {}"
            )
        );
    }

    @Test
    void shouldKeepAnnotationInPackageInfo() {
        rewriteRun(
            spec -> spec.recipe(new PackageAnnotation()),
            Assertions.java(
                "@Deprecated\n"
                + "package org.checkstyle.test;",
                spec -> spec.path("package-info.java")
            )
        );
    }

    @Test
    void shouldNotModifyFileWithoutPackage() {
        rewriteRun(
            spec -> spec.recipe(new PackageAnnotation()),
            java(
                "public class NoPackage {}"
            )
        );
    }

    @Test
    void shouldHaveDisplayNames() {
        PackageAnnotation recipe = new PackageAnnotation();
        org.assertj.core.api.Assertions.assertThat(recipe.getDisplayName())
            .isEqualTo("Move package-level annotations to package-info.java");
        org.assertj.core.api.Assertions.assertThat(recipe.getDescription())
            .isEqualTo("Checks that all package annotations are in the package-info.java file.");
    }
}
