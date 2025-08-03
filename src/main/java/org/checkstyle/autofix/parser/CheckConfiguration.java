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
import java.util.Set;

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

    private CheckConfiguration getParent() {
        return parent;
    }

    public List<CheckConfiguration> getChildren() {
        return children;
    }

    public String getProperty(String key) {
        String value = null;

        if (properties.containsKey(key)) {
            value = properties.get(key);
        }
        else if (getParent() != null) {
            value = getParent().getProperty(key);
        }
        return value;
    }

    public String getPropertyOrDefault(String key, String defaultValue) {
        String result = getProperty(key);
        if (result == null) {
            result = defaultValue;
        }
        return result;
    }

    public boolean hasProperty(String key) {
        boolean result = false;

        if (properties.containsKey(key)) {
            result = true;
        }
        else if (getParent() != null) {
            result = getParent().hasProperty(key);
        }
        return result;
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

    public CheckConfiguration getConfig(String childName) {
        CheckConfiguration result = null;
        if (name.equals(childName)) {
            result = this;
        }
        else {
            final List<CheckConfiguration> childrenList = getChildren();
            for (final CheckConfiguration current : childrenList) {
                if (childName.equals(current.getName())) {
                    result = current;
                    break;
                }
                childrenList.addAll(current.getChildren());
            }
        }
        return result;
    }

    public Set<String> getPropertyNames() {
        return properties.keySet();
    }

    private void setParent(CheckConfiguration parent) {
        this.parent = parent;
    }
}
