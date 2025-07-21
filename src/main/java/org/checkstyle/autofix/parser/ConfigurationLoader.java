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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public final class ConfigurationLoader {

    private ConfigurationLoader() {
        // utility class
    }

    public static CheckConfiguration mapConfiguration(Configuration config) {
        final Map<String, String> properties = new HashMap<>();
        final String[] propertyNames = config.getPropertyNames();
        for (String propertyName : propertyNames) {
            try {
                final String value = config.getProperty(propertyName);
                properties.put(propertyName, value);

            }
            catch (CheckstyleException exception) {
                throw new IllegalStateException("Error getting property " + propertyName,
                        exception);
            }
        }

        final Configuration[] checkstyleChildren = config.getChildren();
        final CheckConfiguration[] simpleChildren =
                new CheckConfiguration[checkstyleChildren.length];
        for (int index = 0; index < checkstyleChildren.length; index++) {
            simpleChildren[index] = mapConfiguration(checkstyleChildren[index]);
        }

        return new CheckConfiguration(config.getName().toUpperCase(Locale.ROOT),
                properties, List.of(simpleChildren));
    }

    public static CheckConfiguration loadConfiguration(String checkstyleConfigurationPath,
                                                       String propFile) {
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
