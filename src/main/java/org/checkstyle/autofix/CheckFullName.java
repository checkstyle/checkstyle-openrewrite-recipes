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

import java.util.Arrays;
import java.util.Optional;

public enum CheckFullName {
    FINAL_LOCAL_VARIABLE("com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck"),
    HEADER("com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck"),
    NEWLINE_AT_END_OF_FILE("com.puppycrawl.tools.checkstyle.checks.NewlineAtEndOfFileCheck"),
    UPPER_ELL("com.puppycrawl.tools.checkstyle.checks.UpperEllCheck"),
    HEX_LITERAL_CASE("com.puppycrawl.tools.checkstyle.checks.HexLiteralCaseCheck"),
    NUMERICAL_PREFIXES_INF_SUF_CASE(
      "com.puppycrawl.tools.checkstyle.checks.NumericalPrefixesInfixesSuffixesCharacterCaseCheck"),
    UNUSED_LOCAL_VARIABLE(
        "com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"),
    REDUNDANT_IMPORT("com.puppycrawl.tools.checkstyle.checks.imports.RedundantImportCheck");

    private final String id;

    CheckFullName(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static Optional<CheckFullName> fromSource(String source) {
        return Arrays.stream(values())
                .filter(check -> check.getId().contains(source.split("#")[0]))
                .findFirst();
    }
}
