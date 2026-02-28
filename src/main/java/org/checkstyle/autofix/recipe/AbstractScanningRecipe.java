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

package org.checkstyle.autofix.recipe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.ExecutionContext;
import org.openrewrite.ScanningRecipe;

/**
 * Abstract base class for Checkstyle scanning recipes that use a two-phase approach:
 * scan phase (match violations to LST nodes by position) and
 * edit phase (apply fixes using stable node UUIDs).
 *
 * <p>Provides common infrastructure: violation storage, accumulator management,
 * and the shared {@link ViolationAccumulator} type.
 */
public abstract class AbstractScanningRecipe
        extends ScanningRecipe<AbstractScanningRecipe.ViolationAccumulator> {

    private final List<CheckstyleViolation> violations;

    protected AbstractScanningRecipe(List<CheckstyleViolation> violations) {
        this.violations = violations;
    }

    protected List<CheckstyleViolation> getViolations() {
        return violations;
    }

    @Override
    public ViolationAccumulator getInitialValue(ExecutionContext executionContext) {
        return new ViolationAccumulator();
    }

    /**
     * Accumulator that stores the mapping from LST node UUIDs to their
     * corresponding Checkstyle violations. Populated during the scan phase,
     * consumed during the edit phase. Uses a list to support multiple
     * violations targeting the same AST node.
     */
    public static final class ViolationAccumulator {
        private final Map<UUID, List<CheckstyleViolation>> matched = new HashMap<>();

        public Map<UUID, List<CheckstyleViolation>> getMatched() {
            return matched;
        }
    }
}
