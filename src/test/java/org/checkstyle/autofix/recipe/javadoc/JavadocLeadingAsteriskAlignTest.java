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

package org.checkstyle.autofix.recipe.javadoc;

import java.util.Properties;

import org.checkstyle.autofix.parser.ReportParser;
import org.checkstyle.autofix.recipe.AbstractRecipeTestSupport;
import org.checkstyle.autofix.recipe.RecipeTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class JavadocLeadingAsteriskAlignTest extends AbstractRecipeTestSupport {

    public JavadocLeadingAsteriskAlignTest() {
    }

    @Override
    protected String getSubpackage() {
        return "javadocleadingasteriskalign";
    }

    @Test
    public void description() {
        final JavadocLeadingAsteriskAlign recipe = new JavadocLeadingAsteriskAlign(null);

        final String expectedDescription =
                "Fixes Checkstyle JavadocLeadingAsteriskAlign violations.";

        Assertions.assertEquals(expectedDescription, recipe.getDescription(),
                "Invalid description");
    }

    @Test
    public void displayName() {
        final JavadocLeadingAsteriskAlign recipe = new JavadocLeadingAsteriskAlign(null);

        final String expectedDisplayName = "JavadocLeadingAsteriskAlign recipe";

        Assertions.assertEquals(expectedDisplayName, recipe.getDisplayName(),
                "Invalid display name");
    }

    @RecipeTest
    void defaultAlign(ReportParser parser) throws Exception {
        verify(parser, "DefaultAlign");
    }

    @RecipeTest
    void tabWidth(ReportParser parser) throws Exception {
        verify(parser, "TabWidth");
    }

    @RecipeTest
    void tabWidthInheritance(ReportParser parser) throws Exception {
        final Configuration config = ConfigurationLoader.loadConfiguration(
                getPath("tabwidthinheritance/checkstyle.xml"),
                new PropertiesExpander(new Properties()));
        verify(parser, config, "TabWidthInheritance");
    }

    @RecipeTest
    void killMutation(ReportParser parser) throws Exception {
        verify(parser, "KillMutation1", "KillMutation2");
    }

}
