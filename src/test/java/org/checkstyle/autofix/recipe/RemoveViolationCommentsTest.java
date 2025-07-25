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

import static org.openrewrite.java.Assertions.java;

import java.io.IOException;

import org.checkstyle.autofix.InputClassRenamer;
import org.checkstyle.autofix.RemoveViolationComments;
import org.junit.jupiter.api.Test;
import org.openrewrite.test.RewriteTest;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;

public class RemoveViolationCommentsTest extends AbstractPathTestSupport implements RewriteTest {

    @Override
    protected String getPackageLocation() {
        return "org/checkstyle/autofix/recipe/removeviolationcomments/allcommentvariations";
    }

    @Test
    void removeAllViolationCommentPatterns() throws IOException {
        final String beforeCode = readFile(getPath("InputAllCommentVariations.java"));
        final String afterCode = readFile(getPath("OutputAllCommentVariations.java"));

        rewriteRun(
                spec -> {
                    spec.recipes(new InputClassRenamer(), new RemoveViolationComments());
                }, java(beforeCode, afterCode)
        );
    }
}
