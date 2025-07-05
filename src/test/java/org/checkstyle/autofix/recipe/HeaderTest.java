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

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;

import org.checkstyle.autofix.parser.CheckstyleReportParser;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;

import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class HeaderTest extends AbstractRecipeTest {

    @Override
    protected Recipe getRecipe() throws CheckstyleException {
        final String reportPath = "src/test/resources/org/checkstyle/autofix/recipe/header"
                + "/report.xml";

        final String configPath = "src/test/resources/org/checkstyle/autofix/recipe/header"
                + "/config.xml";

        final Configuration config = ConfigurationLoader.loadConfiguration(
                configPath, new PropertiesExpander(new Properties())
        );

        final List<CheckstyleViolation> violations =
                CheckstyleReportParser.parse(Path.of(reportPath));

        return new Header(violations,
                extractCheckConfiguration(config, "Header"), getCharset(config));
    }

    @Test
    void headerTest() throws IOException, CheckstyleException {
        testRecipe("header", "HeaderBlankLines");
    }

    @Test
    void headerCommentTest() throws IOException, CheckstyleException {
        testRecipe("header", "HeaderComments");
    }

    @Test
    void headerIncorrect() throws IOException, CheckstyleException {
        testRecipe("header", "HeaderIncorrect");
    }

}
