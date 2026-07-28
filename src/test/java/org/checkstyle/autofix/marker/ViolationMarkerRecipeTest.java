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

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ViolationMarkerRecipeTest {

    public ViolationMarkerRecipeTest() {
    }

    @Test
    public void testGetDisplayName() {
        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(Collections.emptyList());
        final String expectedDisplayName = "Checkstyle violation marker";

        Assertions.assertEquals(expectedDisplayName, recipe.getDisplayName(),
                "Invalid display name");
    }

    @Test
    public void testGetDescription() {
        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(Collections.emptyList());
        final String expectedDescription =
                "Marks AST nodes that correspond to Checkstyle violations.";

        Assertions.assertEquals(expectedDescription, recipe.getDescription(),
                "Invalid description");
    }

    @Test
    public void testMarkersAppliedMethods() throws Exception {
        final Class<?>[] declaredClasses = ViolationMarkerRecipe.class.getDeclaredClasses();
        Class<?> markersAppliedClass = null;
        for (Class<?> clazz : declaredClasses) {
            if ("MarkersApplied".equals(clazz.getSimpleName())) {
                markersAppliedClass = clazz;
                break;
            }
        }
        Assertions.assertNotNull(markersAppliedClass, "MarkersApplied class should exist");
        final UUID originalId = UUID.randomUUID();
        final Constructor<?> constructor =
                markersAppliedClass.getDeclaredConstructor(UUID.class);
        constructor.setAccessible(true);
        final Object marker = constructor.newInstance(originalId);

        final Method getIdMethod = markersAppliedClass.getDeclaredMethod("getId");
        getIdMethod.setAccessible(true);
        final Object retrievedId = getIdMethod.invoke(marker);
        Assertions.assertEquals(originalId, retrievedId, "getId() should return the original ID");

        final UUID newId = UUID.randomUUID();
        final Method withIdMethod =
                markersAppliedClass.getDeclaredMethod("withId", UUID.class);
        withIdMethod.setAccessible(true);
        final Object newMarker = withIdMethod.invoke(marker, newId);

        final Object newRetrievedId = getIdMethod.invoke(newMarker);
        Assertions.assertEquals(newId, newRetrievedId,
                "withId() should create a new marker with the new ID");
    }

}
