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

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.checkstyle.autofix.parser.CheckConfiguration;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.checkstyle.autofix.parser.ConfigurationLoader;
import org.checkstyle.autofix.parser.ReportParser;
import org.checkstyle.autofix.parser.SarifReportParser;
import org.checkstyle.autofix.parser.XmlReportParser;
import org.openrewrite.Option;
import org.openrewrite.Recipe;

/**
 * Main recipe that automatically fixes all supported Checkstyle violations.
 */
public class CheckstyleAutoFix extends Recipe {

    @Option(displayName = "Violation report path",
            description = "Path to the checkstyle violation report file."
                    + " Supported formats: XML, SARIF.",
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

    public CheckstyleAutoFix() {
        // default constructor
    }

    public CheckstyleAutoFix(String violationReportPath, String configurationPath) {
        this.violationReportPath = violationReportPath;
        this.configurationPath = configurationPath;
    }

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
        validateInputs();
        final ReportParser reportParser = createReportParser(getViolationReportPath());
        final List<CheckstyleViolation> violations = reportParser
                .parse(Path.of(getViolationReportPath()));
        final Map<CheckstyleCheck,
                CheckConfiguration> configuration = loadCheckstyleConfiguration();

        return CheckstyleRecipeRegistry.getRecipes(violations, configuration);
    }

    private void validateInputs() {
        if (violationReportPath == null || violationReportPath.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Violation report path cannot be null or empty");
        }
        if (configurationPath == null || configurationPath.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Configuration path cannot be null or empty");
        }
    }

    private ReportParser createReportParser(String path) {
        final ReportParser result;
        if (path.endsWith(".xml")) {
            result = new XmlReportParser();
        }
        else if (path.endsWith(".sarif") || path.endsWith(".sarif.json")) {
            result = new SarifReportParser();
        }
        else {
            throw new IllegalArgumentException("Unsupported report format: " + path);
        }
        return result;
    }

    private Map<CheckstyleCheck, CheckConfiguration> loadCheckstyleConfiguration() {
        return ConfigurationLoader.loadConfiguration(getConfigurationPath(), getPropertiesPath());
    }
}
