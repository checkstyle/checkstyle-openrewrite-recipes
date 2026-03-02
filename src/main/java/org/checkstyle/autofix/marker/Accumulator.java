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

package org.checkstyle.autofix.marker;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class Accumulator {
    private final Map<Path, Map<UUID, List<CheckstyleViolationMarker>>> byFile =
            new HashMap<>();

    public Map<UUID, List<CheckstyleViolationMarker>> getByFile(Path path) {
        return byFile.getOrDefault(path, Collections.emptyMap());
    }

    public void putByFile(Path path, Map<UUID, List<CheckstyleViolationMarker>> markers) {
        byFile.put(path, markers);
    }
}
