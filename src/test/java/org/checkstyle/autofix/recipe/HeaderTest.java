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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.checkstyle.autofix.parser.ReportParser;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck;

public class HeaderTest extends AbstractRecipeTestSupport {

    @Override
    protected String getSubpackage() {
        return "header";
    }

    @Test
    void metadata() {
        final Header recipe = new Header(null);
        assertEquals("Header recipe",
                recipe.getDisplayName());
        assertEquals("Adds headers to Java source files when missing.",
                recipe.getDescription());
    }

    @RecipeTest
    void headerTest(ReportParser parser) throws Exception {
        verify(parser, getHeaderConfig(), "HeaderBlankLines", "HeaderValid");
    }

    @RecipeTest
    void headerCommentTest(ReportParser parser) throws Exception {
        verify(parser, getHeaderConfig(), "HeaderComments");
    }

    @RecipeTest
    void headerIncorrect(ReportParser parser) throws Exception {
        verify(parser, getHeaderConfig(), "HeaderIncorrect");
    }

    @RecipeTest
    void headerValid(ReportParser parser) throws Exception {
        verify(parser, getHeaderConfig(), "HeaderValid");
    }

    @RecipeTest
    void headerPropertyTest(ReportParser parser) throws Exception {
        verify(parser, getHeaderPropertyConfig(), "HeaderProperty");
    }

    @RecipeTest
    void headerCrLfTest(ReportParser parser) throws Exception {
        verify(parser, getHeaderConfig(), "HeaderCrLf");
    }

    @RecipeTest
    void headerCharsetTest(ReportParser parser) throws Exception {
        verify(parser, getHeaderCharsetConfig(), "HeaderCharset");
    }

    private DefaultConfiguration getHeaderCharsetConfig() {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        final String headerPath = "src/test/resources/org/checkstyle/"
                + "autofix/recipe/header/header_utf16.txt";
        checkConfig.addProperty("headerFile", headerPath);
        checkConfig.addProperty("charset", "UTF-16");
        return checkConfig;
    }

    private DefaultConfiguration getHeaderPropertyConfig() {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addProperty("header", "// Checkstyle-OpenRewrite");
        return checkConfig;
    }

    private DefaultConfiguration getHeaderConfig() {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        final String headerPath = "src/test/resources/org/checkstyle/"
                + "autofix/recipe/header/header.txt";
        checkConfig.addProperty("headerFile", headerPath);
        checkConfig.addProperty("ignoreLines", "3");
        return checkConfig;
    }

}
