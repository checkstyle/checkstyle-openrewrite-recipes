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

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck;

public class HeaderTest extends AbstractRecipeTestSupport {

    @Override
    protected String getSubpackage() {
        return "header";
    }

    @Test
    void headerTest() throws Exception {
        verify(getHeaderConfig(), "HeaderBlankLines");
    }

    @Test
    void headerCommentTest() throws Exception {
        verify(getHeaderConfig(), "HeaderComments");
    }

    @Test
    void headerIncorrect() throws Exception {
        verify(getHeaderConfig(), "HeaderIncorrect");
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
