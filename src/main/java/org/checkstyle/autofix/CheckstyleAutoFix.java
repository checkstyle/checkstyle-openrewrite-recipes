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

package org.checkstyle.autofix;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;

import org.checkstyle.autofix.parser.CheckConfiguration;
import org.checkstyle.autofix.parser.CheckstyleReportParser;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.checkstyle.autofix.parser.ConfigurationMapper;
import org.openrewrite.Option;
import org.openrewrite.Recipe;

import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

/**
 * Main recipe that automatically fixes all supported Checkstyle violations.
 */
public class CheckstyleAutoFix extends Recipe {

    @Option(displayName = "Violation report path",
            description = "Path to the checkstyle violation report XML file.",
            example = "target/checkstyle/checkstyle-report.xml")
    private String violationReportPath;

    @Option(displayName = "Checkstyle config path",
            description = "Path to the file containing Checkstyle configuration.",
            example = "config/checkstyle.xml")
    private String configurationPath;

    @Option(displayName = "Checkstyle properties file path",
            description = "Path to the file containing the Checkstyle Properties.",
            example = "config/checkstyle.properties",
            required = false)
    private String propertiesPath;

    @Override
    public String getDisplayName() {
        return "Checkstyle autoFix";
    }

    @Override
    public String getDescription() {
        return "Automatically fixes Checkstyle violations.";
    }

    public String getViolationReportPath() {
        return violationReportPath;
    }

    public String getConfigurationPath() {
        return configurationPath;
    }

    public String getPropertiesPath() {
        return propertiesPath;
    }

    @Override
    public List<Recipe> getRecipeList() {
        final List<CheckstyleViolation> violations = CheckstyleReportParser
                .parse(Path.of(getViolationReportPath()));

        return CheckstyleRecipeRegistry.getRecipes(violations);
    }

    private CheckConfiguration loadCheckstyleConfiguration()
            throws CheckstyleException, IOException {
        Properties props = new Properties();
        final String propFile = getPropertiesPath();

        if (propFile == null) {
            props = System.getProperties();
        }
        else {
            try (FileInputStream input = new FileInputStream(propFile)) {
                props.load(input);
            }
            catch (FileNotFoundException exception) {
                throw new IllegalArgumentException("Failed to read: " + propFile, exception);
            }
        }
        return ConfigurationMapper.mapConfiguration(ConfigurationLoader.loadConfiguration(
                getConfigurationPath(), new PropertiesExpander(props)));
    }
}
