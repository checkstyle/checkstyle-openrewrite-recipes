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

public class CheckstyleConfigModule {
    private final CheckstyleCheck check;
    private final String id;

    public CheckstyleConfigModule(CheckstyleCheck check, String id) {
        this.check = check;
        this.id = id;
    }

    public boolean matchesId(String input) {
        return id != null && id.equals(input);
    }

    public boolean matchesCheck(String input) {
        return CheckstyleCheck.fromSourceExact(input)
                .map(checkFromInput -> checkFromInput == check)
                .orElse(false);
    }
}
