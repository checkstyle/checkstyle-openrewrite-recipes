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

import java.util.Collections;

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

}
