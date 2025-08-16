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

import java.util.HashMap;
import java.util.Map;

import org.checkstyle.autofix.CheckstyleCheck;

public final class CheckConfiguration {
    private final CheckstyleCheck check;
    private final Map<String, String> globalProperties;
    private final Map<String, String> properties;

    public CheckConfiguration(CheckstyleCheck name,
                              Map<String, String> globalProperties,
                              Map<String, String> properties) {
        this.check = name;
        this.globalProperties = new HashMap<>(globalProperties);
        this.properties = new HashMap<>(properties);
    }

    public CheckstyleCheck getCheck() {
        return check;
    }

    public String getProperty(String key) {
        final String result;
        if (properties.containsKey(key)) {
            result = properties.get(key);
        }
        else {
            result = globalProperties.get(key);
        }
        return result;
    }

    public String getPropertyOrDefault(String key, String defaultValue) {
        String result = getProperty(key);
        if (result == null) {
            result = defaultValue;
        }
        return result;
    }

    public boolean hasProperty(String key) {
        return properties.containsKey(key) || globalProperties.containsKey(key);
    }

    public void setGlobalProperty(String key, String value) {
        globalProperties.put(key, value);
    }
}
