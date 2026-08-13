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

package org.checkstyle.autofix.marker;

import java.util.UUID;

import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.marker.Marker;

public interface CheckstyleViolationMarker extends Marker {

    UUID id();

    CheckstyleViolation violation();

    @Override
    default UUID getId() {
        return id();
    }

    @Override
    @SuppressWarnings("unchecked")
    default <M extends Marker> M withId(UUID newId) {
        return (M) MarkerRegistry.create(newId, violation());
    }

}
