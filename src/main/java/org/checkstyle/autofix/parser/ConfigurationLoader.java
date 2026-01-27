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

package org.checkstyle.autofix.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.checkstyle.autofix.CheckFullName;
import org.checkstyle.autofix.CheckstyleCheck;

import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public final class ConfigurationLoader {

    private ConfigurationLoader() {
        // utility class
    }

    public static Map<CheckstyleCheck,
            CheckConfiguration> mapConfiguration(Configuration config) {

        final Map<CheckstyleCheck, CheckConfiguration> result = new HashMap<>();
        final Map<String, String> inherited = getProperties(config);
        final Optional<CheckFullName> checkName = CheckFullName.fromSource(config.getName());

        checkName.ifPresent(checkstyleCheck -> {
            result.put(new CheckstyleCheck(checkstyleCheck, inherited.get("id")),
                    new CheckConfiguration(checkstyleCheck, new HashMap<>(), inherited));
        });

        for (Configuration child : config.getChildren()) {
            mapConfiguration(child).forEach((childModule, childConfig) -> {
                inherited.forEach(childConfig::setGlobalProperty);
                result.put(childModule, childConfig);
            });
        }

        return result;
    }

    private static Map<String, String> getProperties(Configuration config) {
        final Map<String, String> props = new HashMap<>();
        for (String prop : config.getPropertyNames()) {
            try {
                props.put(prop, config.getProperty(prop));
            }
            catch (CheckstyleException exception) {
                throw new IllegalStateException("Failed to get property: " + prop, exception);
            }
        }
        return props;
    }

    public static Map<CheckstyleCheck, CheckConfiguration> loadConfiguration(
            String checkstyleConfigurationPath, String propFile) {
        Properties props = new Properties();
        if (propFile == null) {
            props = System.getProperties();
        }
        else {
            try (FileInputStream input = new FileInputStream(propFile)) {
                props.load(input);
            }
            catch (IOException exception) {
                throw new IllegalStateException("Failed to read: " + propFile, exception);
            }
        }

        final Configuration checkstyleConfig;
        try {
            checkstyleConfig = com.puppycrawl.tools.checkstyle.ConfigurationLoader
                    .loadConfiguration(checkstyleConfigurationPath,
                            new PropertiesExpander(props));
        }
        catch (CheckstyleException exception) {
            throw new IllegalStateException("Failed to load configuration:"
                    + checkstyleConfigurationPath, exception);
        }

        return mapConfiguration(checkstyleConfig);
    }

}
