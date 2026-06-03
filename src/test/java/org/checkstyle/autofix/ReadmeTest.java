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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.google.common.truth.Truth;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;

public class ReadmeTest {

    @Test
    public void testAllChecksAreDocumentedInReadme() throws Exception {
        final Set<Class<?>> checks = CheckUtil.getCheckstyleChecks();

        final Set<String> checkstyleChecks = checks.stream()
                .map(Class::getSimpleName)
                .map(name -> {
                    final String result;
                    if (name.endsWith("Check")) {
                        result = name.substring(0, name.length() - 5);
                    }
                    else {
                        result = name;
                    }
                    return result;
                })
                .collect(Collectors.toSet());

        final String readmeContent = new String(Files.readAllBytes(Paths.get("README.md")));

        final Set<String> readmeChecks = new TreeSet<>();
        final Pattern pattern = Pattern.compile(
                "\\[`([A-Za-z0-9]+)`\\]\\(https://checkstyle\\.sourceforge\\.io");
        final Matcher matcher = pattern.matcher(readmeContent);
        while (matcher.find()) {
            readmeChecks.add(matcher.group(1));
        }

        final Set<String> missingChecks = new TreeSet<>(checkstyleChecks);
        missingChecks.removeAll(readmeChecks);

        Truth.assertWithMessage("The following checks from Checkstyle are missing in the README:")
                .that(missingChecks)
                .isEmpty();
    }
}
