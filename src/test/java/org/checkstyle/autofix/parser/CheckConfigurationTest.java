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

package org.checkstyle.autofix.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.checkstyle.autofix.CheckFullName;
import org.junit.jupiter.api.Test;

public class CheckConfigurationTest {

    public CheckConfigurationTest() {
    }

    @Test
    public void testGetProperty() {
        final Map<String, String> globalProps = new HashMap<>();
        globalProps.put("charset", "UTF-16");

        final Map<String, String> localProps = new HashMap<>();
        localProps.put("headerFile", "header.txt");

        final CheckConfiguration config = new CheckConfiguration(
                CheckFullName.UPPER_ELL, globalProps, localProps);

        assertEquals("UTF-16", config.getProperty("charset"));
        assertEquals("header.txt", config.getProperty("headerFile"));
    }

    @Test
    public void testHasProperty() {
        final Map<String, String> globalProps = new HashMap<>();
        globalProps.put("charset", "UTF-16");

        final Map<String, String> localProps = new HashMap<>();
        localProps.put("headerFile", "header.txt");

        final CheckConfiguration config = new CheckConfiguration(
                CheckFullName.UPPER_ELL, globalProps, localProps);

        assertTrue(config.hasProperty("charset"));
        assertTrue(config.hasProperty("headerFile"));
        assertFalse(config.hasProperty("unknown"));
    }

    @Test
    public void testSetGlobalProperty() {
        final Map<String, String> globalProps = new HashMap<>();
        final Map<String, String> localProps = new HashMap<>();

        final CheckConfiguration config = new CheckConfiguration(
                CheckFullName.UPPER_ELL, globalProps, localProps);

        assertFalse(config.hasProperty("newProp"));

        config.setGlobalProperty("newProp", "newValue");

        assertTrue(config.hasProperty("newProp"));
        assertEquals("newValue", config.getProperty("newProp"));
    }

}
