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

import java.util.List;
import java.util.Map;

public final class CheckConfiguration {
    private final String name;
    private final Map<String, String> properties;
    private final List<CheckConfiguration> children;
    private CheckConfiguration parent;

    public CheckConfiguration(String name,
                              Map<String, String> properties, List<CheckConfiguration> children) {
        this.name = name;
        this.properties = properties;
        this.children = children;

        for (CheckConfiguration child : children) {
            child.setParent(this);
        }
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public List<CheckConfiguration> getChildren() {
        return children;
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    public int getInt(String propertyName) {
        final String value = properties.get(propertyName);
        final int result;
        try {
            result = Integer.parseInt(value);
        }
        catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Invalid integer value for property '"
                    + propertyName + "': " + value, exception);
        }
        return result;
    }

    public boolean getBoolean(String propertyName) {
        return Boolean.parseBoolean(properties.get(propertyName));
    }

    public int[] getIntArray(String propertyName) {
        final String value = properties.get(propertyName);
        final int[] result;
        final String[] parts = value.split(",");
        result = new int[parts.length];
        for (int index = 0; index < parts.length; index++) {
            try {
                result[index] = Integer.parseInt(parts[index].trim());
            }
            catch (NumberFormatException exception) {
                throw new IllegalArgumentException("Property '" + propertyName
                        + "' has an invalid integer value: " + parts[index].trim(), exception);
            }
        }
        return result;
    }

    public CheckConfiguration getChildByName(String childName) {
        CheckConfiguration result = null;
        for (CheckConfiguration child : children) {
            if (childName.equals(child.getName())) {
                result = child;
                break;
            }
        }
        return result;
    }

    public CheckConfiguration getParent() {
        return parent;
    }

    private void setParent(CheckConfiguration parent) {
        this.parent = parent;
    }
}
