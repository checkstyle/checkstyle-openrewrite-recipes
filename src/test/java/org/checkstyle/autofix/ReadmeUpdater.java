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

package org.checkstyle.autofix;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;

public class ReadmeUpdater {

    public ReadmeUpdater() {
    }

    @Test
    @EnabledIfSystemProperty(named = "runReadmeUpdater", matches = "true")
    public void testUpdateReadme() throws Exception {
        final Set<Class<?>> checks = CheckUtil.getCheckstyleChecks();

        final Map<String, Class<?>> checkstyleChecks = checks.stream()
                .collect(Collectors.toMap(
                    ReadmeUpdater::getCheckName,
                    clazz -> clazz,
                    (clazz1, clazz2) -> clazz1
                ));

        final String readmeContent = new String(Files.readAllBytes(Paths.get("README.md")));
        final Set<String> readmeChecks = getReadmeChecks(readmeContent);

        final Set<String> missingChecks = new TreeSet<>(checkstyleChecks.keySet());
        missingChecks.removeAll(readmeChecks);

        if (!missingChecks.isEmpty()) {
            final Map<String, List<String>> checksByCategory =
                    groupChecksByCategory(missingChecks, checkstyleChecks);

            final List<String> readmeLines = Files.readAllLines(Paths.get("README.md"));
            updateReadmeLines(readmeLines, checksByCategory);

            Files.write(Paths.get("README.md"), readmeLines);
            System.out.println("Updated README.md with new checks: " + missingChecks);
        }
        else {
            System.out.println("README.md is already up-to-date with Checkstyle checks.");
        }
    }

    private static Set<String> getReadmeChecks(String readmeContent) {
        final Set<String> readmeChecks = new TreeSet<>();
        final Pattern pattern = Pattern.compile(
                "\\[`([A-Za-z0-9]+)`\\]\\(https://"
                        + "(?:checkstyle\\.(?:org|sourceforge\\.io)|github\\.com/checkstyle)");
        final Matcher matcher = pattern.matcher(readmeContent);
        while (matcher.find()) {
            readmeChecks.add(matcher.group(1));
        }
        return readmeChecks;
    }

    private static Map<String, List<String>> groupChecksByCategory(
            Set<String> missingChecks, Map<String, Class<?>> checkstyleChecks) {
        final Map<String, List<String>> checksByCategory = new HashMap<>();
        for (String missingCheck : missingChecks) {
            final Class<?> clazz = checkstyleChecks.get(missingCheck);
            final String packageName = clazz.getPackage().getName();
            String category = "misc";
            if (packageName.startsWith("com.puppycrawl.tools.checkstyle.checks.")) {
                final String subpackage = packageName
                        .substring("com.puppycrawl.tools.checkstyle.checks.".length());
                if (!subpackage.isEmpty()) {
                    category = subpackage.split("\\.")[0];
                }
            }
            checksByCategory.computeIfAbsent(category, categoryKey -> new ArrayList<>())
                    .add(missingCheck);
        }
        return checksByCategory;
    }

    private static void updateReadmeLines(List<String> readmeLines,
            Map<String, List<String>> checksByCategory) {
        final Map<String, String> categoryToHeader = getCategoryToHeaderMap();
        final Pattern pattern = Pattern.compile(
                "\\[`([A-Za-z0-9]+)`\\]\\(https://"
                        + "(?:checkstyle\\.(?:org|sourceforge\\.io)|github\\.com/checkstyle)");

        for (Map.Entry<String, List<String>> entry : checksByCategory.entrySet()) {
            final String category = entry.getKey();
            final List<String> checksToAdd = entry.getValue();
            final String header = categoryToHeader.getOrDefault(category, "### Miscellaneous");

            int headerIndex = findHeaderIndex(readmeLines, header);
            if (headerIndex == -1) {
                headerIndex = appendNewSection(readmeLines, header);
            }

            final int tableDataStartIndex = getTableDataStartIndex(readmeLines, headerIndex);
            if (tableDataStartIndex == -1) {
                continue;
            }

            insertChecks(readmeLines, category, checksToAdd, tableDataStartIndex, pattern);
        }
    }

    private static Map<String, String> getCategoryToHeaderMap() {
        final Map<String, String> map = new HashMap<>();
        map.put("annotation", "### Annotations");
        map.put("blocks", "### Block Checks");
        map.put("design", "### Class Design");
        map.put("coding", "### Coding");
        map.put("header", "### Headers");
        map.put("imports", "### Imports");
        map.put("javadoc", "### Javadoc Comments");
        map.put("metrics", "### Metrics");
        map.put("misc", "### Miscellaneous");
        map.put("modifier", "### Modifiers");
        map.put("naming", "### Naming Conventions");
        map.put("regexp", "### Regexp");
        map.put("sizes", "### Size Violations");
        map.put("whitespace", "### Whitespace");
        return map;
    }

    private static int findHeaderIndex(List<String> readmeLines, String header) {
        int headerIndex = -1;
        for (int idx = 0; idx < readmeLines.size(); idx++) {
            if (readmeLines.get(idx).trim().equals(header)) {
                headerIndex = idx;
                break;
            }
        }
        return headerIndex;
    }

    private static int appendNewSection(List<String> readmeLines, String header) {
        readmeLines.add("");
        readmeLines.add(header);
        readmeLines.add("");
        readmeLines.add("| Status | Check | Coverage Notes |");
        readmeLines.add("|---|---|---|");
        return readmeLines.size() - 5;
    }

    private static int getTableDataStartIndex(List<String> readmeLines, int headerIndex) {
        int tableStartIndex = headerIndex + 1;
        int result = -1;
        while (tableStartIndex < readmeLines.size()
                && !readmeLines.get(tableStartIndex).trim().startsWith("| Status |")) {
            tableStartIndex++;
        }
        if (tableStartIndex < readmeLines.size()) {
            result = tableStartIndex + 2;
        }
        return result;
    }

    private static void insertChecks(List<String> readmeLines, String category,
            List<String> checksToAdd, int tableDataStartIndex, Pattern pattern) {
        for (String missingCheck : checksToAdd) {
            final String url = "https://checkstyle.org/checks/" + category + "/"
                    + missingCheck.toLowerCase() + ".html#" + missingCheck;
            final String circle = "\u26AA";
            final String newRow = String.format("| %s | [`%s`](%s) | |", circle, missingCheck, url);

            int insertIndex = tableDataStartIndex;
            while (insertIndex < readmeLines.size()
                    && readmeLines.get(insertIndex).trim().startsWith("|")) {
                final String row = readmeLines.get(insertIndex);
                final Matcher m = pattern.matcher(row);
                if (m.find()) {
                    final String existingCheck = m.group(1);
                    if (missingCheck.compareTo(existingCheck) < 0) {
                        break;
                    }
                }
                insertIndex++;
            }
            readmeLines.add(insertIndex, newRow);
        }
    }

    private static String getCheckName(Class<?> clazz) {
        final String name = clazz.getSimpleName();
        final String result;
        if (name.endsWith("Check")) {
            result = name.substring(0, name.length() - 5);
        }
        else {
            result = name;
        }
        return result;
    }

}
