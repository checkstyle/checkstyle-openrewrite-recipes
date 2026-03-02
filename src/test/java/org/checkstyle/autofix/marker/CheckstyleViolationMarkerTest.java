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

import java.nio.file.Paths;
import java.util.UUID;

import org.checkstyle.autofix.CheckFullName;
import org.checkstyle.autofix.CheckstyleCheck;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openrewrite.marker.Marker;

public class CheckstyleViolationMarkerTest {

    @Test
    public void testGetId() {
        final UUID id = UUID.randomUUID();
        final CheckstyleViolation violation = new CheckstyleViolation(
                1, 1, "error", new CheckstyleCheck(
                        CheckFullName.FINAL_CLASS, "id"),
                "msg", Paths.get("Test.java"));
        final CheckstyleViolationMarker marker = new CheckstyleViolationMarker(id, violation);
        Assertions.assertEquals(id, marker.getId());
    }

    @Test
    public void testIsFor() {
        final UUID id = UUID.randomUUID();
        final CheckstyleViolation violation = new CheckstyleViolation(
                1, 1, "error", new CheckstyleCheck(
                        CheckFullName.FINAL_CLASS, "id"),
                "msg", Paths.get("Test.java"));
        final CheckstyleViolationMarker marker = new CheckstyleViolationMarker(id, violation);

        Assertions.assertTrue(marker.isFor(CheckFullName.FINAL_CLASS));
        Assertions.assertFalse(marker.isFor(CheckFullName.FINAL_LOCAL_VARIABLE));
    }

    @Test
    public void testWithId() {
        final UUID id = UUID.randomUUID();
        final CheckstyleViolation violation = new CheckstyleViolation(
                1, 1, "error", new CheckstyleCheck(
                        CheckFullName.FINAL_CLASS, "id"),
                "msg", Paths.get("Test.java"));
        final CheckstyleViolationMarker marker = new CheckstyleViolationMarker(id, violation);

        final UUID newId = UUID.randomUUID();
        final Marker newMarker = marker.withId(newId);

        Assertions.assertNotSame(marker, newMarker);
        Assertions.assertEquals(newId, newMarker.getId());
        Assertions.assertTrue(newMarker instanceof CheckstyleViolationMarker);
        Assertions.assertEquals(violation, ((CheckstyleViolationMarker) newMarker).violation());
    }
}
