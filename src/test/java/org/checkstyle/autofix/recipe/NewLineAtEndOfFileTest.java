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

import static com.google.common.truth.Truth.assertWithMessage;

import java.util.Map;

import org.checkstyle.autofix.CheckFullName;
import org.checkstyle.autofix.parser.CheckConfiguration;
import org.checkstyle.autofix.parser.ReportParser;
import org.junit.jupiter.api.Test;

public class NewLineAtEndOfFileTest extends AbstractRecipeTestSupport {
    @Override
    protected String getSubpackage() {
        return "newlineatendoffile";
    }

    @Test
    public void checkDisplayName() {
        final NewlineAtEndOfFile recipe = new NewlineAtEndOfFile(
            new CheckConfiguration(CheckFullName.NEWLINE_AT_END_OF_FILE, Map.of(), Map.of()));

        final String expectedDisplayName = "End files with a single newline";

        assertWithMessage("Invalid display name")
                .that(recipe.getDisplayName())
                .isEqualTo(expectedDisplayName);
    }

    @Test
    public void checkDescription() {
        final NewlineAtEndOfFile recipe = new NewlineAtEndOfFile(
            new CheckConfiguration(CheckFullName.NEWLINE_AT_END_OF_FILE, Map.of(), Map.of()));

        final String expectedDescription =
                "Some tools work better when files end with an empty line.";

        assertWithMessage("Invalid description")
                .that(recipe.getDescription())
                .isEqualTo(expectedDescription);
    }

    @RecipeTest
    void newLineCr(ReportParser parser) throws Exception {
        verify(parser, "NewlineAtEndOfFileCr");
    }

    @RecipeTest
    void newLineCrlf(ReportParser parser) throws Exception {
        verify(parser, "NewlineAtEndOfFileCrlf");
    }

    @RecipeTest
    void noNewLine(ReportParser parser) throws Exception {
        verify(parser, "NewlineAtEndOfFileNoNewLine");
    }

    @RecipeTest
    void newLineComment(ReportParser parser) throws Exception {
        verify(parser, "NewlineAtEndOfFileComment");
    }

    @RecipeTest
    void newLineLf(ReportParser parser) throws Exception {
        verify(parser, "NewlineAtEndOfFileLf");
    }

    @RecipeTest
    void newLineLfCrCrlf(ReportParser parser) throws Exception {
        verify(parser, "NewlineAtEndOfFileLfCrCrlf");
    }

    @RecipeTest
    void newLineNoViolation(ReportParser parser) throws Exception {
        verify(parser, "NewLineNoViolation");
    }
}
