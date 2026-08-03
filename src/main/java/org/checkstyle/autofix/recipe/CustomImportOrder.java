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

import java.util.Collections;
import java.util.UUID;

import org.checkstyle.autofix.parser.CheckConfiguration;
import org.openrewrite.Cursor;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.OrderImports;
import org.openrewrite.java.style.ImportLayoutStyle;
import org.openrewrite.java.tree.J;
import org.openrewrite.style.NamedStyles;

/**
 * Fixes Checkstyle CustomImportOrder violations by ordering imports according
 * to the project's layout style.
 */
public class CustomImportOrder extends Recipe {

    private static final String DEFAULT_SPECIAL_IMPORTS_REGEXP = "^$";
    private static final String STAR_WILDCARD = ".*";

    private final String customImportOrderRules;
    private final boolean separateLineBetweenGroups;
    private final String specialImportsRegExp;

    public CustomImportOrder() {
        this(new CheckConfiguration(null, Collections.emptyMap(), Collections.emptyMap()));
    }

    public CustomImportOrder(CheckConfiguration checkConfig) {
        customImportOrderRules = checkConfig.getPropertyOrDefault("customImportOrderRules", "");
        separateLineBetweenGroups = Boolean.parseBoolean(
            checkConfig.getPropertyOrDefault("separateLineBetweenGroups", "true")
        );
        specialImportsRegExp = checkConfig.getPropertyOrDefault(
            "specialImportsRegExp", DEFAULT_SPECIAL_IMPORTS_REGEXP);
    }

    @Override
    public String getDisplayName() {
        return "CustomImportOrder Recipe";
    }

    @Override
    public String getDescription() {
        return "Orders imports to fix CustomImportOrder violations "
                + "using OpenRewrite's OrderImports.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new CustomImportOrderVisitor(customImportOrderRules, separateLineBetweenGroups,
                specialImportsRegExp);
    }

    private static class CustomImportOrderVisitor extends JavaIsoVisitor<ExecutionContext> {
        private final String rules;
        private final boolean separateLines;
        private final String specialImportsRegExp;

        CustomImportOrderVisitor(String rules, boolean separateLines, String specialImportsRegExp) {
            this.rules = rules;
            this.separateLines = separateLines;
            this.specialImportsRegExp = specialImportsRegExp;
        }

        @Override
        public J.CompilationUnit visitCompilationUnit(J.CompilationUnit compilationUnit,
                                                      ExecutionContext executionContext) {
            J.CompilationUnit modifiedCu = compilationUnit;
            if (!rules.isEmpty()) {
                final ImportLayoutStyle style = buildLayoutStyle(compilationUnit);
                final NamedStyles namedStyles = new NamedStyles(
                        UUID.randomUUID(),
                        "checkstyle-custom-import-order",
                        "Checkstyle CustomImportOrder",
                        "Custom import layout style from Checkstyle CustomImportOrder",
                        Collections.emptySet(),
                        Collections.singletonList(style)
                );
                modifiedCu = compilationUnit.withMarkers(
                        compilationUnit.getMarkers().addIfAbsent(namedStyles));
            }

            return (J.CompilationUnit) new OrderImports(false, null)
                    .getVisitor().visitNonNull(modifiedCu, executionContext);
        }

        private ImportLayoutStyle buildLayoutStyle(J.CompilationUnit compilationUnit) {
            final ImportLayoutStyle.Builder builder = ImportLayoutStyle.builder();
            final String[] parsedRules = rules.split("###");

            for (String rule : parsedRules) {
                applyRule(builder, rule.trim(), compilationUnit, getCursor());
                if (separateLines) {
                    builder.blankLine();
                }
            }

            return builder.build();
        }

        private void applyRule(ImportLayoutStyle.Builder builder, String rule,
                               J.CompilationUnit compilationUnit, Cursor cursor) {
            if (!rule.isEmpty()) {
                if (rule.startsWith("STATIC")) {
                    builder.importStaticAllOthers();
                }
                else if (rule.startsWith("STANDARD_JAVA_PACKAGE")) {
                    builder.importPackage("java.*");
                    builder.importPackage("javax.*");
                }
                else if (rule.startsWith("SPECIAL_IMPORTS")) {
                    applySpecialImportsRule(builder);
                }
                else if (rule.startsWith("THIRD_PARTY_PACKAGE")) {
                    builder.importAllOthers();
                }
                else if (rule.startsWith("SAME_PACKAGE")) {
                    applySamePackageRule(builder, compilationUnit, cursor);
                }
            }
        }

        private void applySpecialImportsRule(ImportLayoutStyle.Builder builder) {
            if (!DEFAULT_SPECIAL_IMPORTS_REGEXP.equals(specialImportsRegExp)
                    && !specialImportsRegExp.isEmpty()) {
                final String pkg = specialImportsRegExp
                        .replaceAll("^\\^", "")
                        .replaceAll("\\\\.", ".")
                        .replaceAll("\\$$", "")
                        .replaceAll("\\.\\*$", "")
                        .replaceAll("\\.$", "");
                if (!pkg.isEmpty()) {
                    builder.importPackage(pkg + STAR_WILDCARD);
                }
            }
        }

        private void applySamePackageRule(ImportLayoutStyle.Builder builder,
                                          J.CompilationUnit compilationUnit,
                                          Cursor cursor) {
            if (compilationUnit.getPackageDeclaration() != null) {
                final String pkg = compilationUnit.getPackageDeclaration()
                        .getExpression().printTrimmed(cursor);
                builder.importPackage(pkg + STAR_WILDCARD);
            }
        }
    }

}
