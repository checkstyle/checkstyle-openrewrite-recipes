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

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

import org.checkstyle.autofix.CheckFullName;
import org.checkstyle.autofix.marker.checks.AnnotationLocationMarker;
import org.checkstyle.autofix.marker.checks.AnnotationOnSameLineMarker;
import org.checkstyle.autofix.marker.checks.ArrayTrailingCommaMarker;
import org.checkstyle.autofix.marker.checks.AvoidNoArgumentSuperConstructorCallMarker;
import org.checkstyle.autofix.marker.checks.AvoidStarImportMarker;
import org.checkstyle.autofix.marker.checks.ConstructorsDeclarationGroupingMarker;
import org.checkstyle.autofix.marker.checks.EmptyForInitializerPadMarker;
import org.checkstyle.autofix.marker.checks.EmptyForIteratorPadMarker;
import org.checkstyle.autofix.marker.checks.EmptyStatementMarker;
import org.checkstyle.autofix.marker.checks.FinalClassMarker;
import org.checkstyle.autofix.marker.checks.FinalLocalVariableMarker;
import org.checkstyle.autofix.marker.checks.HeaderMarker;
import org.checkstyle.autofix.marker.checks.HexLiteralCaseMarker;
import org.checkstyle.autofix.marker.checks.MissingDeprecatedMarker;
import org.checkstyle.autofix.marker.checks.MissingOverrideMarker;
import org.checkstyle.autofix.marker.checks.MissingSwitchDefaultMarker;
import org.checkstyle.autofix.marker.checks.NewlineAtEndOfFileMarker;
import org.checkstyle.autofix.marker.checks.NoWhitespaceAfterMarker;
import org.checkstyle.autofix.marker.checks.NumericalPrefixesInfixesSuffixesCharacterCaseMarker;
import org.checkstyle.autofix.marker.checks.RedundantImportMarker;
import org.checkstyle.autofix.marker.checks.UnnecessaryParenthesesMarker;
import org.checkstyle.autofix.marker.checks.UnusedImportsMarker;
import org.checkstyle.autofix.marker.checks.UnusedLocalVariableMarker;
import org.checkstyle.autofix.marker.checks.UpperEllMarker;
import org.checkstyle.autofix.marker.checks.UseEnhancedSwitchMarker;
import org.checkstyle.autofix.parser.CheckstyleViolation;

public final class MarkerRegistry {

    private static final Map<CheckFullName, BiFunction<UUID, CheckstyleViolation,
            CheckstyleViolationMarker>> FACTORIES = new EnumMap<>(CheckFullName.class);

    private MarkerRegistry() {
    }

    static {
        FACTORIES.put(CheckFullName.ARRAY_TRAILING_COMMA, ArrayTrailingCommaMarker::new);
        FACTORIES.put(CheckFullName.FINAL_LOCAL_VARIABLE, FinalLocalVariableMarker::new);
        FACTORIES.put(CheckFullName.AVOID_STAR_IMPORT, AvoidStarImportMarker::new);
        FACTORIES.put(CheckFullName.EMPTY_FOR_INITIALIZER_PAD, EmptyForInitializerPadMarker::new);
        FACTORIES.put(CheckFullName.EMPTY_FOR_ITERATOR_PAD, EmptyForIteratorPadMarker::new);
        FACTORIES.put(CheckFullName.EMPTY_STATEMENT, EmptyStatementMarker::new);
        FACTORIES.put(CheckFullName.ANNOTATION_LOCATION, AnnotationLocationMarker::new);
        FACTORIES.put(CheckFullName.ANNOTATION_ON_SAME_LINE, AnnotationOnSameLineMarker::new);
        FACTORIES.put(CheckFullName.FINAL_CLASS, FinalClassMarker::new);
        FACTORIES.put(CheckFullName.HEADER, HeaderMarker::new);
        FACTORIES.put(CheckFullName.NEWLINE_AT_END_OF_FILE, NewlineAtEndOfFileMarker::new);
        FACTORIES.put(CheckFullName.NO_WHITESPACE_AFTER, NoWhitespaceAfterMarker::new);
        FACTORIES.put(CheckFullName.UPPER_ELL, UpperEllMarker::new);
        FACTORIES.put(CheckFullName.HEX_LITERAL_CASE, HexLiteralCaseMarker::new);
        FACTORIES.put(
                CheckFullName.NUMERICAL_PREFIXES_INF_SUF_CASE,
                NumericalPrefixesInfixesSuffixesCharacterCaseMarker::new);
        FACTORIES.put(CheckFullName.UNUSED_LOCAL_VARIABLE, UnusedLocalVariableMarker::new);
        FACTORIES.put(CheckFullName.REDUNDANT_IMPORT, RedundantImportMarker::new);
        FACTORIES.put(CheckFullName.USE_ENHANCED_SWITCH, UseEnhancedSwitchMarker::new);
        FACTORIES.put(CheckFullName.MISSING_OVERRIDE, MissingOverrideMarker::new);
        FACTORIES.put(CheckFullName.MISSING_DEPRECATED, MissingDeprecatedMarker::new);
        FACTORIES.put(
                CheckFullName.CONSTRUCTORS_DECLARATION_GROUPING,
                ConstructorsDeclarationGroupingMarker::new);
        FACTORIES.put(CheckFullName.MISSING_SWITCH_DEFAULT, MissingSwitchDefaultMarker::new);
        FACTORIES.put(CheckFullName.UNUSED_IMPORT, UnusedImportsMarker::new);
        FACTORIES.put(CheckFullName.UNNECESSARY_PARENTHESES, UnnecessaryParenthesesMarker::new);
        FACTORIES.put(
                CheckFullName.AVOID_NO_ARGUMENT_SUPER_CONSTRUCTOR_CALL,
                AvoidNoArgumentSuperConstructorCallMarker::new);
    }

    public static CheckstyleViolationMarker create(UUID id, CheckstyleViolation violation) {
        return FACTORIES.getOrDefault(violation.getSource().checkName(),
                MarkerRegistry::unsupportedCheck).apply(id, violation);
    }

    private static CheckstyleViolationMarker unsupportedCheck(UUID id,
            CheckstyleViolation violation) {
        throw new IllegalArgumentException("No marker factory found for check");
    }

}
