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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Custom annotation for recipe tests that automatically run with both XML and SARIF parsers.
 * This annotation combines {@link ParameterizedTest} with {@link MethodSource} to eliminate
 * boilerplate code in test methods.
 *
 * <p>Usage example:
 * <pre>
 * {@code
 * @RecipeTest
 * void myTest(ReportParser parser) throws Exception {
 *     verify(parser, "TestCase");
 * }
 * }
 * </pre>
 *
 * <p>The test method must accept a single {@link org.checkstyle.autofix.parser.ReportParser}
 * parameter, which will be automatically provided by the test framework. Each test will
 * automatically execute twice: once with the XML report parser and once with the SARIF
 * report parser.
 *
 * @see AbstractRecipeTestSupport#reportParsers()
 */
@MethodSource("reportParsers")
@ParameterizedTest
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RecipeTest {
}
