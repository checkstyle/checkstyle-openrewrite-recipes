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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.checkstyle.autofix.CheckFullName;
import org.checkstyle.autofix.CheckstyleCheck;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openrewrite.Cursor;
import org.openrewrite.ExecutionContext;
import org.openrewrite.InMemoryExecutionContext;
import org.openrewrite.PrintOutputCapture;
import org.openrewrite.Tree;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.JavaParser;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.Space;
import org.openrewrite.marker.Marker;
import org.openrewrite.marker.Markers;

public class ViolationMarkerRecipeTest {

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
    public void testMarkerVisitorDoesNotApplyTwice() {
        final Path path = Paths.get("TestIdempotency.java");
        final CheckstyleViolation violation = new CheckstyleViolation(
                2, 1, "error", new CheckstyleCheck(
                        CheckFullName.FINAL_CLASS, "id"),
                "msg", path);

        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(List.of(violation));
        final JavaParser parser = JavaParser.fromJavaVersion().build();
        final J.CompilationUnit compUnit = parser.parse(
                "\nfinal class A {}").findFirst().get().withSourcePath(path);

        final ExecutionContext ctx = new InMemoryExecutionContext();
        final var acc = recipe.getInitialValue(ctx);
        recipe.getScanner(acc).visit(compUnit, ctx);

        final J.CompilationUnit afterFirst =
                (J.CompilationUnit) recipe.getVisitor(acc).visit(compUnit, ctx);

        final J.CompilationUnit afterSecond =
                (J.CompilationUnit) recipe.getVisitor(acc).visit(afterFirst, ctx);

        final int[] markerCount = new int[1];
        new JavaIsoVisitor<Integer>() {
            @Override
            public J preVisit(J tree, Integer p) {
                markerCount[0] += tree.getMarkers().findAll(
                        CheckstyleViolationMarker.class).size();
                return super.preVisit(tree, p);
            }
        }.visit(afterSecond, 0);

        Assertions.assertEquals(1, markerCount[0],
                "Marker should only be applied once even if visitor runs twice");
        Assertions.assertEquals(afterFirst.getMarkers().getMarkers().size(),
                afterSecond.getMarkers().getMarkers().size(),
                "Total markers count should be stable on second visit");
    }

    @Test
    public void testTargetNodeNotFoundIsHandled() {
        final Path path = Paths.get("TestTargetNotFound.java");
        final CheckstyleViolation violation = new CheckstyleViolation(
                1, 1, "error", new CheckstyleCheck(
                        CheckFullName.FINAL_LOCAL_VARIABLE, "id"),
                "msg", path);

        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(List.of(violation));
        final JavaParser parser = JavaParser.fromJavaVersion().build();
        final J.CompilationUnit compUnit = parser.parse(
                "public class A {}").findFirst().get().withSourcePath(path);

        final ExecutionContext ctx = new InMemoryExecutionContext();
        final var acc = recipe.getInitialValue(ctx);
        recipe.getScanner(acc).visit(compUnit, ctx);

        Assertions.assertFalse(acc.getByFile(path).isEmpty(),
                "Markers should be stored on the smallest node "
                + "fallback if target node is not found");
    }

    @Test
    public void testChildWinsWhenVisitedAfterParent() {
        final Path path = Paths.get("TestChildAfterParent.java");
        final CheckstyleViolation violation = new CheckstyleViolation(
                2, 15, "error", new CheckstyleCheck(
                        CheckFullName.FINAL_LOCAL_VARIABLE, "id"),
                "msg", path);

        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(List.of(violation));
        final JavaParser parser = JavaParser.fromJavaVersion().build();
        final J.CompilationUnit compUnit = parser.parse(
                "\nclass A { int a; }").findFirst().get().withSourcePath(path);

        final ExecutionContext ctx = new InMemoryExecutionContext();
        final var acc = recipe.getInitialValue(ctx);
        recipe.getScanner(acc).visit(compUnit, ctx);
        final J.CompilationUnit after =
                (J.CompilationUnit) recipe.getVisitor(acc).visit(compUnit, ctx);

        final boolean[] varHasMarker = new boolean[1];
        final boolean[] classHasMarker = new boolean[1];
        new JavaIsoVisitor<Integer>() {
            @Override
            public J preVisit(J tree, Integer p) {
                if (tree.getMarkers().findFirst(CheckstyleViolationMarker.class).isPresent()) {
                    if (tree instanceof J.VariableDeclarations.NamedVariable) {
                        varHasMarker[0] = true;
                    }
                    else if (tree instanceof J.ClassDeclaration) {
                        classHasMarker[0] = true;
                    }
                }
                return super.preVisit(tree, p);
            }
        }.visit(after, 0);

        Assertions.assertTrue(varHasMarker[0],
                "VariableDeclarations.NamedVariable should have the marker");
        Assertions.assertFalse(classHasMarker[0], "ClassDeclaration should NOT have the marker");
    }

    @Test
    public void testMultilineParentAndSingleLineChild() {
        final Path path = Paths.get("TestMultilineParentAndSingleLineChild.java");
        final CheckstyleViolation violation = new CheckstyleViolation(
                2, 9, "error", new CheckstyleCheck(
                        CheckFullName.FINAL_LOCAL_VARIABLE, "id"),
                "msg", path);

        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(List.of(violation));
        final JavaParser parser = JavaParser.fromJavaVersion().build();
        final J.CompilationUnit compUnit = parser.parse(
                "class A {\n    int aaaaaaa;\n}").findFirst().get().withSourcePath(path);

        final ExecutionContext ctx = new InMemoryExecutionContext();
        final var acc = recipe.getInitialValue(ctx);
        recipe.getScanner(acc).visit(compUnit, ctx);
        final J.CompilationUnit after =
                (J.CompilationUnit) recipe.getVisitor(acc).visit(compUnit, ctx);

        final boolean[] childHasMarker = new boolean[1];
        final boolean[] blockHasMarker = new boolean[1];
        new JavaIsoVisitor<Integer>() {
            @Override
            public J preVisit(J tree, Integer p) {
                if (tree.getMarkers().findFirst(CheckstyleViolationMarker.class).isPresent()) {
                    if (tree instanceof J.VariableDeclarations.NamedVariable) {
                        childHasMarker[0] = true;
                    }
                    else if (tree instanceof J.Block) {
                        blockHasMarker[0] = true;
                    }
                }
                return super.preVisit(tree, p);
            }
        }.visit(after, 0);

        Assertions.assertTrue(childHasMarker[0],
                "Child node should have the marker because it is smaller in columns");
        Assertions.assertFalse(blockHasMarker[0], "Block should NOT have the marker");
    }

    @Test
    public void testCheckPartialLinesMultiLine() throws Exception {
        final Class<?> scannerClass = Class.forName(
                "org.checkstyle.autofix.marker.ViolationMarkerRecipe$ScannerVisitor");
        final Method method = scannerClass.getDeclaredMethod(
                "checkPartialLines",
                Class.forName("org.checkstyle.autofix.marker.ViolationMarkerRecipe$Range"),
                CheckstyleViolation.class);
        method.setAccessible(true);

        final Class<?> rangeClass = Class.forName(
                "org.checkstyle.autofix.marker.ViolationMarkerRecipe$Range");
        final Constructor<?> rangeCtor = rangeClass.getDeclaredConstructor(
                int.class, int.class, int.class, int.class);
        rangeCtor.setAccessible(true);

        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(Collections.emptyList());
        final Object scanner = recipe.getScanner(
                recipe.getInitialValue(new InMemoryExecutionContext()));

        final Object rangeStartLineMatch = rangeCtor.newInstance(2, 5, 4, 10);
        final CheckstyleCheck check1 = new CheckstyleCheck(
                CheckFullName.FINAL_LOCAL_VARIABLE, "id");
        final CheckstyleViolation violationStart = new CheckstyleViolation(
                2, 6, "err", check1, "msg", Paths.get("a"));
        final boolean resultStart = (Boolean) method.invoke(
                scanner, rangeStartLineMatch, violationStart);
        Assertions.assertTrue(resultStart);

        final Object rangeEndLineMatch = rangeCtor.newInstance(1, 1, 3, 10);
        final CheckstyleViolation violationEnd = new CheckstyleViolation(
                3, 9, "err", check1, "msg", Paths.get("a"));
        final boolean resultEnd = (Boolean) method.invoke(
                scanner, rangeEndLineMatch, violationEnd);
        Assertions.assertTrue(resultEnd);

        final CheckstyleViolation violationStartFail = new CheckstyleViolation(
                2, 4, "err", check1, "msg", Paths.get("a"));
        final boolean resultStartFail = (Boolean) method.invoke(
                scanner, rangeStartLineMatch, violationStartFail);
        Assertions.assertFalse(resultStartFail);

        final CheckstyleViolation violationEndFail = new CheckstyleViolation(
                3, 11, "err", check1, "msg", Paths.get("a"));
        final boolean resultEndFail = (Boolean) method.invoke(
                scanner, rangeEndLineMatch, violationEndFail);
        Assertions.assertFalse(resultEndFail);
    }

    @Test
    public void testCheckPartialLinesNoMatch() throws Exception {
        final Class<?> scannerClass = Class.forName(
                "org.checkstyle.autofix.marker.ViolationMarkerRecipe$ScannerVisitor");
        final Method method = scannerClass.getDeclaredMethod(
                "checkPartialLines",
                Class.forName("org.checkstyle.autofix.marker.ViolationMarkerRecipe$Range"),
                CheckstyleViolation.class);
        method.setAccessible(true);

        final Class<?> rangeClass = Class.forName(
                "org.checkstyle.autofix.marker.ViolationMarkerRecipe$Range");
        final Constructor<?> rangeCtor = rangeClass.getDeclaredConstructor(
                int.class, int.class, int.class, int.class);
        rangeCtor.setAccessible(true);

        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(Collections.emptyList());
        final Object scanner = recipe.getScanner(
                recipe.getInitialValue(new InMemoryExecutionContext()));

        final Object range = rangeCtor.newInstance(1, 1, 2, 2);
        final CheckstyleCheck check1 = new CheckstyleCheck(
                CheckFullName.FINAL_LOCAL_VARIABLE, "id");
        final CheckstyleViolation violation = new CheckstyleViolation(
                4, 5, "err", check1, "msg", Paths.get("a"));
        final boolean result = (Boolean) method.invoke(scanner, range, violation);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEnclosesViaReflection() throws Exception {
        final Class<?> scannerClass = Class.forName(
                "org.checkstyle.autofix.marker.ViolationMarkerRecipe$ScannerVisitor");
        final Class<?> rangeClass = Class.forName(
                "org.checkstyle.autofix.marker.ViolationMarkerRecipe$Range");
        final Method enclosesMethod = scannerClass.getDeclaredMethod(
                "encloses", rangeClass, CheckstyleViolation.class);
        enclosesMethod.setAccessible(true);

        final Constructor<?> rangeCtor = rangeClass.getDeclaredConstructor(
                int.class, int.class, int.class, int.class);
        rangeCtor.setAccessible(true);

        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(Collections.emptyList());
        final Object scanner = recipe.getScanner(
                recipe.getInitialValue(new InMemoryExecutionContext()));

        final CheckstyleCheck check = new CheckstyleCheck(
                CheckFullName.FINAL_LOCAL_VARIABLE, "id");
        final Object rangeBefore = rangeCtor.newInstance(5, 1, 10, 20);
        final CheckstyleViolation violBefore = new CheckstyleViolation(
                4, 5, "err", check, "msg", Paths.get("a"));
        Assertions.assertFalse((Boolean) enclosesMethod.invoke(scanner, rangeBefore, violBefore),
                "Violation before range should not be enclosed");
        final CheckstyleViolation violAfter = new CheckstyleViolation(
                11, 5, "err", check, "msg", Paths.get("a"));
        Assertions.assertFalse((Boolean) enclosesMethod.invoke(scanner, rangeBefore, violAfter),
                "Violation after range should not be enclosed");
        final CheckstyleViolation violInside = new CheckstyleViolation(
                7, 5, "err", check, "msg", Paths.get("a"));
        Assertions.assertTrue((Boolean) enclosesMethod.invoke(scanner, rangeBefore, violInside),
                "Violation strictly inside range should be enclosed");
        final Object rangeSingleLine = rangeCtor.newInstance(5, 3, 5, 15);
        final CheckstyleViolation violSingleInside = new CheckstyleViolation(
                5, 10, "err", check, "msg", Paths.get("a"));
        Assertions.assertTrue(
                (Boolean) enclosesMethod.invoke(scanner, rangeSingleLine, violSingleInside),
                "Violation inside single-line range should be enclosed");
        final CheckstyleViolation violSingleStart = new CheckstyleViolation(
                5, 3, "err", check, "msg", Paths.get("a"));
        Assertions.assertTrue(
                (Boolean) enclosesMethod.invoke(scanner, rangeSingleLine, violSingleStart),
                "Violation at start col of single-line range should be enclosed");
        final CheckstyleViolation violSingleEnd = new CheckstyleViolation(
                5, 15, "err", check, "msg", Paths.get("a"));
        Assertions.assertTrue(
                (Boolean) enclosesMethod.invoke(scanner, rangeSingleLine, violSingleEnd),
                "Violation at end col of single-line range should be enclosed");
        final CheckstyleViolation violSingleBeforeCol = new CheckstyleViolation(
                5, 2, "err", check, "msg", Paths.get("a"));
        Assertions.assertFalse(
                (Boolean) enclosesMethod.invoke(scanner, rangeSingleLine, violSingleBeforeCol),
                "Violation before start col should not be enclosed");
        final CheckstyleViolation violSingleAfterCol = new CheckstyleViolation(
                5, 16, "err", check, "msg", Paths.get("a"));
        Assertions.assertFalse(
                (Boolean) enclosesMethod.invoke(scanner, rangeSingleLine, violSingleAfterCol),
                "Violation after end col should not be enclosed");
        final CheckstyleViolation violOnStartLine = new CheckstyleViolation(
                5, 5, "err", check, "msg", Paths.get("a"));
        Assertions.assertTrue(
                (Boolean) enclosesMethod.invoke(scanner, rangeBefore, violOnStartLine),
                "Violation on start line within col bounds should be enclosed");
        final CheckstyleViolation violOnEndLine = new CheckstyleViolation(
                10, 10, "err", check, "msg", Paths.get("a"));
        Assertions.assertTrue(
                (Boolean) enclosesMethod.invoke(scanner, rangeBefore, violOnEndLine),
                "Violation on end line within col bounds should be enclosed");
        final Object rangeStartCol = rangeCtor.newInstance(5, 10, 10, 20);
        final CheckstyleViolation violStartColBefore = new CheckstyleViolation(
                5, 9, "err", check, "msg", Paths.get("a"));
        Assertions.assertFalse(
                (Boolean) enclosesMethod.invoke(scanner, rangeStartCol, violStartColBefore),
                "Violation on start line with col before startCol should not be enclosed");
        final CheckstyleViolation violEndColAfter = new CheckstyleViolation(
                10, 21, "err", check, "msg", Paths.get("a"));
        Assertions.assertFalse(
                (Boolean) enclosesMethod.invoke(scanner, rangeStartCol, violEndColAfter),
                "Violation on end line with col after endCol should not be enclosed");
    }

    @Test
    public void testFindSmallestNodeViaReflection() throws Exception {
        final Class<?> scannerClass = Class.forName(
                "org.checkstyle.autofix.marker.ViolationMarkerRecipe$ScannerVisitor");
        final Class<?> rangeClass = Class.forName(
                "org.checkstyle.autofix.marker.ViolationMarkerRecipe$Range");
        final Method findMethod = scannerClass.getDeclaredMethod(
                "findSmallestNode",
                CheckstyleViolation.class,
                Map.class, Map.class, Map.class);
        findMethod.setAccessible(true);

        final Constructor<?> rangeCtor = rangeClass.getDeclaredConstructor(
                int.class, int.class, int.class, int.class);
        rangeCtor.setAccessible(true);

        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(Collections.emptyList());
        final Object scanner = recipe.getScanner(
                recipe.getInitialValue(new InMemoryExecutionContext()));

        final CheckstyleCheck check = new CheckstyleCheck(
                CheckFullName.FINAL_LOCAL_VARIABLE, "id");

        final Map<UUID, Object> emptyRanges = new LinkedHashMap<>();
        final Map<UUID, UUID> emptyParent = new HashMap<>();
        final Map<UUID, Tree> emptyTrees =
                new HashMap<>();
        final CheckstyleViolation viol1 = new CheckstyleViolation(
                100, 100, "err", check, "msg", Paths.get("a"));
        @SuppressWarnings("unchecked")
        final Map<UUID, ?> result1 =
                (Map<UUID, ?>) findMethod.invoke(
                        scanner, viol1, emptyRanges, emptyParent, emptyTrees);
        Assertions.assertTrue(result1.isEmpty(),
                "Should return empty map when no node encloses violation");

        final UUID id1 = Tree.randomId();
        final UUID id2 = Tree.randomId();
        final Map<UUID, Object> ranges = new LinkedHashMap<>();
        ranges.put(id1, rangeCtor.newInstance(1, 1, 3, 20));
        ranges.put(id2, rangeCtor.newInstance(1, 5, 3, 10));
        final Map<UUID, UUID> parentMap = new HashMap<>();
        final Map<UUID, Tree> nodeTrees =
                new HashMap<>();
        final CheckstyleViolation viol2 = new CheckstyleViolation(
                2, 7, "err", check, "msg", Paths.get("a"));
        @SuppressWarnings("unchecked")
        final Map<UUID, ?> result2 =
                (Map<UUID, ?>) findMethod.invoke(
                        scanner, viol2, ranges, parentMap, nodeTrees);
        Assertions.assertFalse(result2.isEmpty(),
                "Should find a node");
        Assertions.assertTrue(result2.containsKey(id2),
                "Should pick the node with smaller col span");
    }

    @Test
    public void testResolveTargetNodeViaReflection() throws Exception {
        final Class<?> scannerClass = Class.forName(
                "org.checkstyle.autofix.marker.ViolationMarkerRecipe$ScannerVisitor");
        final Method resolveMethod = scannerClass.getDeclaredMethod(
                "resolveTargetNode",
                UUID.class, CheckstyleViolation.class,
                Map.class, Map.class);
        resolveMethod.setAccessible(true);

        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(Collections.emptyList());
        final Object scanner = recipe.getScanner(
                recipe.getInitialValue(new InMemoryExecutionContext()));

        final UUID startId = Tree.randomId();
        final Map<UUID, UUID> parentMap = new HashMap<>();
        final Map<UUID, Tree> nodeTrees =
                new HashMap<>();

        final CheckstyleCheck checkNull = new CheckstyleCheck(null, "id");
        final CheckstyleViolation violNull = new CheckstyleViolation(
                1, 1, "err", checkNull, "msg", Paths.get("a"));
        final UUID resultNull = (UUID) resolveMethod.invoke(
                scanner, startId, violNull, parentMap, nodeTrees);
        Assertions.assertEquals(startId, resultNull,
                "When checkFullName is null, should return startNodeId");

        final CheckstyleCheck checkDefault = new CheckstyleCheck(
                CheckFullName.AVOID_STAR_IMPORT, "id");
        final CheckstyleViolation violDefault = new CheckstyleViolation(
                1, 1, "err", checkDefault, "msg", Paths.get("a"));
        final UUID resultDefault = (UUID) resolveMethod.invoke(
                scanner, startId, violDefault, parentMap, nodeTrees);
        Assertions.assertEquals(startId, resultDefault,
                "When targetType is null (default switch), should return startNodeId");
    }

    @Test
    public void testNoViolationsForFile() {
        final Path path = Paths.get("TestNoViolations.java");
        final CheckstyleViolation violation = new CheckstyleViolation(
                1, 1, "error", new CheckstyleCheck(
                        CheckFullName.FINAL_CLASS, "id"),
                "msg", Paths.get("OtherFile.java"));

        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(List.of(violation));
        final JavaParser parser = JavaParser.fromJavaVersion().build();
        final J.CompilationUnit compUnit = parser.parse(
                "class A {}").findFirst().get().withSourcePath(path);

        final ExecutionContext ctx = new InMemoryExecutionContext();
        final var acc = recipe.getInitialValue(ctx);
        recipe.getScanner(acc).visit(compUnit, ctx);

        Assertions.assertTrue(acc.getByFile(path).isEmpty(),
                "No markers should be stored when no violations match the file");
    }

    @Test
    public void testVisitorWithNoViolationsDoesNotAddMarkers() {
        final Path path = Paths.get("TestNoViolVisitor.java");
        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(Collections.emptyList());
        final JavaParser parser = JavaParser.fromJavaVersion().build();
        final J.CompilationUnit compUnit = parser.parse(
                "class A {}").findFirst().get().withSourcePath(path);

        final ExecutionContext ctx = new InMemoryExecutionContext();
        final var acc = recipe.getInitialValue(ctx);
        recipe.getScanner(acc).visit(compUnit, ctx);
        final J.CompilationUnit after = (J.CompilationUnit)
                recipe.getVisitor(acc).visit(compUnit, ctx);

        final int[] markerCount = new int[1];
        new JavaIsoVisitor<Integer>() {
            @Override
            public J preVisit(J tree, Integer p) {
                markerCount[0] += tree.getMarkers().findAll(
                        CheckstyleViolationMarker.class).size();
                return super.preVisit(tree, p);
            }
        }.visit(after, 0);

        Assertions.assertEquals(0, markerCount[0],
                "No violation markers should be added when accumulator is empty");
    }

    @Test
    public void testScannerIdempotency() {
        final Path path = Paths.get("TestScannerIdem.java");
        final CheckstyleViolation violation = new CheckstyleViolation(
                1, 1, "error", new CheckstyleCheck(
                        CheckFullName.FINAL_CLASS, "id"),
                "msg", path);

        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(List.of(violation));
        final JavaParser parser = JavaParser.fromJavaVersion().build();
        final J.CompilationUnit compUnit = parser.parse(
                "final class A {}").findFirst().get().withSourcePath(path);

        final ExecutionContext ctx = new InMemoryExecutionContext();
        final var acc = recipe.getInitialValue(ctx);

        recipe.getScanner(acc).visit(compUnit, ctx);
        final int firstSize = acc.getByFile(path).size();

        final J.CompilationUnit afterVisit = (J.CompilationUnit)
                recipe.getVisitor(acc).visit(compUnit, ctx);

        recipe.getScanner(acc).visit(afterVisit, ctx);
        final int secondSize = acc.getByFile(path).size();

        Assertions.assertEquals(firstSize, secondSize,
                "Scanner should not process a file that already has MarkersApplied marker");
    }

    @Test
    public void testViolationOnSameLineAsRangeStart() {
        final Path path = Paths.get("TestStartLine.java");
        final CheckstyleViolation violation = new CheckstyleViolation(
                1, 7, "error", new CheckstyleCheck(
                        CheckFullName.FINAL_CLASS, "id"),
                "msg", path);

        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(List.of(violation));
        final JavaParser parser = JavaParser.fromJavaVersion().build();
        final J.CompilationUnit compUnit = parser.parse(
                "final class A {}").findFirst().get().withSourcePath(path);

        final ExecutionContext ctx = new InMemoryExecutionContext();
        final var acc = recipe.getInitialValue(ctx);
        recipe.getScanner(acc).visit(compUnit, ctx);

        Assertions.assertFalse(acc.getByFile(path).isEmpty(),
                "Should find markers even when violation is on the range start line");
    }

    @Test
    public void testMultipleViolationsSameFile() {
        final Path path = Paths.get("TestMultiViol.java");
        final CheckstyleViolation viol1 = new CheckstyleViolation(
                2, 5, "error", new CheckstyleCheck(
                        CheckFullName.FINAL_LOCAL_VARIABLE, "id"),
                "msg1", path);
        final CheckstyleViolation viol2 = new CheckstyleViolation(
                3, 5, "error", new CheckstyleCheck(
                        CheckFullName.FINAL_LOCAL_VARIABLE, "id"),
                "msg2", path);

        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(List.of(viol1, viol2));
        final JavaParser parser = JavaParser.fromJavaVersion().build();
        final J.CompilationUnit compUnit = parser.parse(
                "class A {\n    int a;\n    int b;\n}").findFirst().get().withSourcePath(path);

        final ExecutionContext ctx = new InMemoryExecutionContext();
        final var acc = recipe.getInitialValue(ctx);
        recipe.getScanner(acc).visit(compUnit, ctx);
        final J.CompilationUnit after = (J.CompilationUnit)
                recipe.getVisitor(acc).visit(compUnit, ctx);

        final int[] markerCount = new int[1];
        new JavaIsoVisitor<Integer>() {
            @Override
            public J preVisit(J tree, Integer p) {
                markerCount[0] += tree.getMarkers().findAll(
                        CheckstyleViolationMarker.class).size();
                return super.preVisit(tree, p);
            }
        }.visit(after, 0);

        Assertions.assertEquals(2, markerCount[0],
                "Should have 2 markers for 2 violations");
    }

    @Test
    public void testCheckPartialLinesStartLineExactBoundary() throws Exception {
        final Class<?> scannerClass = Class.forName(
                "org.checkstyle.autofix.marker.ViolationMarkerRecipe$ScannerVisitor");
        final Class<?> rangeClass = Class.forName(
                "org.checkstyle.autofix.marker.ViolationMarkerRecipe$Range");
        final Method method = scannerClass.getDeclaredMethod(
                "checkPartialLines", rangeClass, CheckstyleViolation.class);
        method.setAccessible(true);

        final Constructor<?> rangeCtor = rangeClass.getDeclaredConstructor(
                int.class, int.class, int.class, int.class);
        rangeCtor.setAccessible(true);

        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(Collections.emptyList());
        final Object scanner = recipe.getScanner(
                recipe.getInitialValue(new InMemoryExecutionContext()));

        final CheckstyleCheck check = new CheckstyleCheck(
                CheckFullName.FINAL_LOCAL_VARIABLE, "id");

        final Object range1 = rangeCtor.newInstance(2, 5, 4, 10);
        final CheckstyleViolation viol1 = new CheckstyleViolation(
                2, 5, "err", check, "msg", Paths.get("a"));
        Assertions.assertTrue((Boolean) method.invoke(scanner, range1, viol1),
                "Col exactly at startCol should be enclosed");

        final CheckstyleViolation viol2 = new CheckstyleViolation(
                2, 4, "err", check, "msg", Paths.get("a"));
        Assertions.assertFalse((Boolean) method.invoke(scanner, range1, viol2),
                "Col before startCol should not be enclosed");

        final Object range2 = rangeCtor.newInstance(1, 1, 3, 10);
        final CheckstyleViolation viol3 = new CheckstyleViolation(
                3, 10, "err", check, "msg", Paths.get("a"));
        Assertions.assertTrue((Boolean) method.invoke(scanner, range2, viol3),
                "Col exactly at endCol should be enclosed");

        final CheckstyleViolation viol4 = new CheckstyleViolation(
                3, 11, "err", check, "msg", Paths.get("a"));
        Assertions.assertFalse((Boolean) method.invoke(scanner, range2, viol4),
                "Col after endCol should not be enclosed");

        final CheckstyleViolation viol5 = new CheckstyleViolation(
                2, 5, "err", check, "msg", Paths.get("a"));
        Assertions.assertFalse((Boolean) method.invoke(scanner, range2, viol5),
                "Violation in middle of range should return false from checkPartialLines");
    }

    @Test
    public void testSingleLineViolationExactRange() {
        final Path path = Paths.get("TestSingleLineExact.java");
        final CheckstyleViolation violation = new CheckstyleViolation(
                1, 1, "error", new CheckstyleCheck(
                        CheckFullName.FINAL_CLASS, "id"),
                "msg", path);

        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(List.of(violation));
        final JavaParser parser = JavaParser.fromJavaVersion().build();
        final J.CompilationUnit compUnit = parser.parse(
                "class A {}").findFirst().get().withSourcePath(path);

        final ExecutionContext ctx = new InMemoryExecutionContext();
        final var acc = recipe.getInitialValue(ctx);
        recipe.getScanner(acc).visit(compUnit, ctx);

        Assertions.assertFalse(acc.getByFile(path).isEmpty(),
                "Single-line range should enclose the violation");

        final J.CompilationUnit after = (J.CompilationUnit)
                recipe.getVisitor(acc).visit(compUnit, ctx);
        final boolean[] classHasMarker = new boolean[1];
        new JavaIsoVisitor<Integer>() {
            @Override
            public J preVisit(J tree, Integer p) {
                if (tree instanceof J.ClassDeclaration
                        && tree.getMarkers().findFirst(
                        CheckstyleViolationMarker.class).isPresent()) {
                    classHasMarker[0] = true;
                }
                return super.preVisit(tree, p);
            }
        }.visit(after, 0);

        Assertions.assertTrue(classHasMarker[0],
                "ClassDeclaration should have marker for FINAL_CLASS check");
    }

    @Test
    public void testViolationOutsideAllRanges() {
        final Path path = Paths.get("TestOutside.java");
        final CheckstyleViolation violation = new CheckstyleViolation(
                100, 1, "error", new CheckstyleCheck(
                        CheckFullName.FINAL_CLASS, "id"),
                "msg", path);

        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(List.of(violation));
        final JavaParser parser = JavaParser.fromJavaVersion().build();
        final J.CompilationUnit compUnit = parser.parse(
                "class A {}").findFirst().get().withSourcePath(path);

        final ExecutionContext ctx = new InMemoryExecutionContext();
        final var acc = recipe.getInitialValue(ctx);
        recipe.getScanner(acc).visit(compUnit, ctx);

        Assertions.assertTrue(acc.getByFile(path).isEmpty(),
                "No markers should exist when violation is outside all node ranges");
    }

    @Test
    public void testCheckPartialLinesSingleLineRangeFalse() throws Exception {
        final Class<?> scannerClass = Class.forName(
                "org.checkstyle.autofix.marker.ViolationMarkerRecipe$ScannerVisitor");
        final Class<?> rangeClass = Class.forName(
                "org.checkstyle.autofix.marker.ViolationMarkerRecipe$Range");
        final Method method = scannerClass.getDeclaredMethod(
                "checkPartialLines", rangeClass, CheckstyleViolation.class);
        method.setAccessible(true);

        final Constructor<?> rangeCtor = rangeClass.getDeclaredConstructor(
                int.class, int.class, int.class, int.class);
        rangeCtor.setAccessible(true);

        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(Collections.emptyList());
        final Object scanner = recipe.getScanner(
                recipe.getInitialValue(new InMemoryExecutionContext()));

        final CheckstyleCheck check = new CheckstyleCheck(
                CheckFullName.FINAL_LOCAL_VARIABLE, "id");
        final Object singleLineRange = rangeCtor.newInstance(3, 1, 3, 20);
        final CheckstyleViolation viol = new CheckstyleViolation(
                3, 5, "err", check, "msg", Paths.get("a"));
        Assertions.assertFalse((Boolean) method.invoke(scanner, singleLineRange, viol),
                "checkPartialLines should return false for single-line range");
        final Object rangeStartMatch = rangeCtor.newInstance(3, 1, 3, 20);
        final CheckstyleViolation violStartMatch = new CheckstyleViolation(
                3, 1, "err", check, "msg", Paths.get("a"));
        Assertions.assertFalse(
                (Boolean) method.invoke(scanner, rangeStartMatch, violStartMatch),
                "startLine == violLine but endLine not > violLine should be false");

        final Object rangeEndMatch = rangeCtor.newInstance(3, 1, 3, 20);
        final CheckstyleViolation violEndMatch = new CheckstyleViolation(
                3, 10, "err", check, "msg", Paths.get("a"));
        Assertions.assertFalse(
                (Boolean) method.invoke(scanner, rangeEndMatch, violEndMatch),
                "endLine == violLine but startLine not < violLine should be false");
    }

    @Test
    public void testFindSmallestNodeEqualColsTieBreak() throws Exception {
        final Class<?> scannerClass = Class.forName(
                "org.checkstyle.autofix.marker.ViolationMarkerRecipe$ScannerVisitor");
        final Class<?> rangeClass = Class.forName(
                "org.checkstyle.autofix.marker.ViolationMarkerRecipe$Range");
        final Method findMethod = scannerClass.getDeclaredMethod(
                "findSmallestNode",
                CheckstyleViolation.class,
                Map.class, Map.class, Map.class);
        findMethod.setAccessible(true);

        final Constructor<?> rangeCtor = rangeClass.getDeclaredConstructor(
                int.class, int.class, int.class, int.class);
        rangeCtor.setAccessible(true);

        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(Collections.emptyList());
        final Object scanner = recipe.getScanner(
                recipe.getInitialValue(
                        new InMemoryExecutionContext()));

        final CheckstyleCheck check = new CheckstyleCheck(
                CheckFullName.FINAL_LOCAL_VARIABLE, "id");

        final UUID id1 = Tree.randomId();
        final UUID id2 = Tree.randomId();
        final Map<UUID, Object> ranges = new LinkedHashMap<>();
        ranges.put(id1, rangeCtor.newInstance(1, 5, 3, 15));
        ranges.put(id2, rangeCtor.newInstance(1, 5, 3, 15));
        final Map<UUID, UUID> parentMap = new HashMap<>();
        final Map<UUID, Tree> nodeTrees =
                new HashMap<>();
        final CheckstyleViolation viol = new CheckstyleViolation(
                2, 10, "err", check, "msg", Paths.get("a"));
        @SuppressWarnings("unchecked")
        final Map<UUID, ?> result =
                (Map<UUID, ?>) findMethod.invoke(
                        scanner, viol, ranges, parentMap, nodeTrees);
        Assertions.assertFalse(result.isEmpty(), "Should find a node");
        Assertions.assertTrue(result.containsKey(id2),
                "Second node should win when cols are equal (<=)");

        final UUID idA = Tree.randomId();
        final UUID idB = Tree.randomId();
        final UUID idC = Tree.randomId();
        final Map<UUID, Object> ranges2 = new LinkedHashMap<>();
        ranges2.put(idA, rangeCtor.newInstance(1, 1, 3, 20));
        ranges2.put(idB, rangeCtor.newInstance(1, 5, 3, 10));
        ranges2.put(idC, rangeCtor.newInstance(1, 5, 3, 15));
        @SuppressWarnings("unchecked")
        final Map<UUID, ?> result2 =
                (Map<UUID, ?>) findMethod.invoke(
                        scanner, viol, ranges2, parentMap, nodeTrees);
        Assertions.assertFalse(result2.isEmpty(), "result2 shouldn't be empty");
        final UUID idX = Tree.randomId();
        final UUID idY = Tree.randomId();
        final Map<UUID, Object> ranges3 = new LinkedHashMap<>();
        ranges3.put(idX, rangeCtor.newInstance(1, 15, 3, 20));
        ranges3.put(idY, rangeCtor.newInstance(1, 10, 3, 20));
        @SuppressWarnings("unchecked")
        final Map<UUID, ?> result3 =
                (Map<UUID, ?>) findMethod.invoke(
                        scanner, viol, ranges3, parentMap, nodeTrees);
        Assertions.assertFalse(result3.isEmpty(), "result3 shouldn't be empty");
        final UUID idC2 = Tree.randomId();
        final UUID idD = Tree.randomId();
        final Map<UUID, Object> ranges4 = new LinkedHashMap<>();
        ranges4.put(idC2, rangeCtor.newInstance(1, 1, 2, 100));
        ranges4.put(idD, rangeCtor.newInstance(1, 1, 10, 5));
        @SuppressWarnings("unchecked")
        final Map<UUID, ?> result4 =
                (Map<UUID, ?>) findMethod.invoke(
                        scanner, viol, ranges4, parentMap, nodeTrees);
        Assertions.assertTrue(result4.containsKey(idC2),
                "Node with fewer lines should win regardless of column size");
        final UUID idE = Tree.randomId();
        final UUID idF = Tree.randomId();
        final Map<UUID, Object> ranges5 = new LinkedHashMap<>();
        ranges5.put(idE, rangeCtor.newInstance(1, 10, 3, 15));
        ranges5.put(idF, rangeCtor.newInstance(1, 20, 3, 30));
        @SuppressWarnings("unchecked")
        final Map<UUID, ?> result5 =
                (Map<UUID, ?>) findMethod.invoke(
                        scanner, viol, ranges5, parentMap, nodeTrees);
        Assertions.assertTrue(result5.containsKey(idE),
                "Node with smaller actual size should win, ignoring endCol removal mutation");
        final UUID idG = Tree.randomId();
        final UUID idH = Tree.randomId();
        final Map<UUID, Object> ranges6 = new LinkedHashMap<>();
        ranges6.put(idG, rangeCtor.newInstance(1, 25, 3, 30));
        ranges6.put(idH, rangeCtor.newInstance(1, 10, 3, 20));
        @SuppressWarnings("unchecked")
        final Map<UUID, ?> result6 =
                (Map<UUID, ?>) findMethod.invoke(
                        scanner, viol, ranges6, parentMap, nodeTrees);
        Assertions.assertTrue(result6.containsKey(idG),
                "Node with smaller actual size should win, ignoring startCol removal mutation");
    }

    @Test
    public void testResolveTargetNodeFinalClassSwitch() throws Exception {
        final Class<?> scannerClass = Class.forName(
                "org.checkstyle.autofix.marker.ViolationMarkerRecipe$ScannerVisitor");
        final Method resolveMethod = scannerClass.getDeclaredMethod(
                "resolveTargetNode",
                UUID.class, CheckstyleViolation.class,
                Map.class, Map.class);
        resolveMethod.setAccessible(true);

        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(Collections.emptyList());
        final Object scanner = recipe.getScanner(
                recipe.getInitialValue(new InMemoryExecutionContext()));

        final JavaParser parser = JavaParser.fromJavaVersion().build();
        final J.CompilationUnit cu =
                (J.CompilationUnit) parser.parse("class A {}").findFirst().get();
        final J.ClassDeclaration classDecl = cu.getClasses().get(0);

        final UUID childId = Tree.randomId();
        final UUID parentId = classDecl.getId();
        final Map<UUID, UUID> parentMap = new HashMap<>();
        parentMap.put(childId, parentId);
        final Map<UUID, Tree> nodeTrees = new HashMap<>();
        nodeTrees.put(childId, cu);
        nodeTrees.put(parentId, classDecl);
        final CheckstyleCheck checkFinalClass = new CheckstyleCheck(
                CheckFullName.FINAL_CLASS, "id");
        final CheckstyleViolation violFinalClass = new CheckstyleViolation(
                1, 1, "err", checkFinalClass, "msg", Paths.get("a"));
        final UUID resultFinalClass = (UUID) resolveMethod.invoke(
                scanner, childId, violFinalClass, parentMap, nodeTrees);
        Assertions.assertEquals(parentId, resultFinalClass,
                "FINAL_CLASS should resolve up to ClassDeclaration");
        final CheckstyleCheck checkFinalVar = new CheckstyleCheck(
                CheckFullName.FINAL_LOCAL_VARIABLE, "id");
        final CheckstyleViolation violFinalVar = new CheckstyleViolation(
                1, 1, "err", checkFinalVar, "msg", Paths.get("a"));
        final UUID resultFinalVar = (UUID) resolveMethod.invoke(
                scanner, childId, violFinalVar, parentMap, nodeTrees);
        Assertions.assertEquals(childId, resultFinalVar,
                "FINAL_LOCAL_VARIABLE with no NamedVariable should return startId");
    }

    @Test
    public void testVisitorWithMarkersAlreadyApplied() {
        final Path path = Paths.get("TestMarkersApplied.java");
        final CheckstyleViolation violation = new CheckstyleViolation(
                1, 1, "error", new CheckstyleCheck(
                        CheckFullName.FINAL_CLASS, "id"),
                "msg", path);

        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(List.of(violation));
        final JavaParser parser = JavaParser.fromJavaVersion().build();
        final J.CompilationUnit compUnit = parser.parse(
                "final class A {}").findFirst().get().withSourcePath(path);

        final ExecutionContext ctx = new InMemoryExecutionContext();
        final var acc = recipe.getInitialValue(ctx);
        recipe.getScanner(acc).visit(compUnit, ctx);
        final J.CompilationUnit afterFirst = (J.CompilationUnit)
                recipe.getVisitor(acc).visit(compUnit, ctx);
        final int[] firstCount = new int[1];
        new JavaIsoVisitor<Integer>() {
            @Override
            public J preVisit(J tree, Integer p) {
                firstCount[0] += tree.getMarkers().findAll(
                        CheckstyleViolationMarker.class).size();
                return super.preVisit(tree, p);
            }
        }.visit(afterFirst, 0);
        final J.CompilationUnit afterSecond = (J.CompilationUnit)
                recipe.getVisitor(acc).visit(afterFirst, ctx);
        final int[] secondCount = new int[1];
        new JavaIsoVisitor<Integer>() {
            @Override
            public J preVisit(J tree, Integer p) {
                secondCount[0] += tree.getMarkers().findAll(
                        CheckstyleViolationMarker.class).size();
                return super.preVisit(tree, p);
            }
        }.visit(afterSecond, 0);

        Assertions.assertEquals(firstCount[0], secondCount[0],
                "Marker count should not increase on second visitor pass");
        Assertions.assertSame(afterFirst, afterSecond,
                "Second visit should return same instance since "
                + "MarkersApplied prevents processing");
    }

    @Test
    public void testScannerDoesNotAddEmptyMapToAccumulator() throws Exception {
        final Path path = Paths.get("TestNoViolationsEmptyMap.java");
        final CheckstyleViolation violation = new CheckstyleViolation(
                100, 1, "error", new CheckstyleCheck(
                        CheckFullName.FINAL_CLASS, "id"),
                "msg", path);

        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(List.of(violation));
        final JavaParser parser = JavaParser.fromJavaVersion().build();
        final J.CompilationUnit compUnit = parser.parse(
                "class A {}").findFirst().get().withSourcePath(path);

        final ExecutionContext ctx = new InMemoryExecutionContext();
        final var acc = recipe.getInitialValue(ctx);
        recipe.getScanner(acc).visit(compUnit, ctx);
        final Field field = Accumulator.class.getDeclaredField("byFile");
        field.setAccessible(true);
        @SuppressWarnings("unchecked")
        final Map<Path, ?> internalMap = (Map<Path, ?>) field.get(acc);

        Assertions.assertFalse(internalMap.containsKey(path),
                "Scanner should not put an empty map into the accumulator");
    }

    @Test
    public void testScannerIdempotencyClearsAccumulator() {
        final Path path = Paths.get("TestScannerIdemClear.java");
        final CheckstyleViolation violation = new CheckstyleViolation(
                1, 1, "error", new CheckstyleCheck(
                        CheckFullName.FINAL_CLASS, "id"),
                "msg", path);

        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(List.of(violation));
        final JavaParser parser = JavaParser.fromJavaVersion().build();
        final J.CompilationUnit compUnit = parser.parse(
                "final class A {}").findFirst().get().withSourcePath(path);

        final ExecutionContext ctx = new InMemoryExecutionContext();
        final var acc = recipe.getInitialValue(ctx);

        recipe.getScanner(acc).visit(compUnit, ctx);
        Assertions.assertFalse(acc.getByFile(path).isEmpty(), "First scan should find violations");

        final J.CompilationUnit afterVisit = (J.CompilationUnit)
                recipe.getVisitor(acc).visit(compUnit, ctx);
        acc.putByFile(path, Collections.emptyMap());
        recipe.getScanner(acc).visit(afterVisit, ctx);

        Assertions.assertTrue(acc.getByFile(path).isEmpty(),
                "Scanner should completely skip the file, leaving accumulator empty");
    }

    @Test
    public void testBoundingBoxCaptureInternals() throws Exception {
        final Class<?> captureClass = Class.forName(
                "org.checkstyle.autofix.marker"
                + ".ViolationMarkerRecipe$BoundingBoxCapture");
        final Class<?> rangeClass = Class.forName(
                "org.checkstyle.autofix.marker.ViolationMarkerRecipe$Range");
        final Constructor<?> captureCtor =
                captureClass.getDeclaredConstructor(
                        TreeVisitor.class,
                        Map.class, Map.class, Map.class);
        captureCtor.setAccessible(true);

        final Map<UUID, Object> nodeRanges = new HashMap<>();
        final Map<UUID, UUID> parentMap = new HashMap<>();
        final Map<UUID, Tree> nodeTrees =
                new HashMap<>();
        final TreeVisitor<?,
                PrintOutputCapture<TreeVisitor<?, ?>>>
                dummyPrinter = new TreeVisitor<>() {
                };

        final Field cursorField =
                TreeVisitor.class.getDeclaredField("cursor");
        cursorField.setAccessible(true);
        cursorField.set(dummyPrinter, new Cursor(null, "dummy"));

        final Object capture = captureCtor.newInstance(
                dummyPrinter, nodeRanges, parentMap, nodeTrees);

        final Method appendMethod =
                captureClass.getMethod("append", String.class);

        final Object result = appendMethod.invoke(capture, "test");
        Assertions.assertSame(capture, result, "append should return this instance");

        final Field outField =
                PrintOutputCapture.class.getDeclaredField("out");
        outField.setAccessible(true);
        final StringBuilder outBuilder = (StringBuilder) outField.get(capture);
        Assertions.assertEquals("test", outBuilder.toString(),
                "append should delegate to super.append and capture the text");

        final UUID testId = Tree.randomId();
        final Constructor<?> rangeCtor = rangeClass.getDeclaredConstructor(
                int.class, int.class, int.class, int.class);
        rangeCtor.setAccessible(true);
        nodeRanges.put(testId, rangeCtor.newInstance(1, 42, 0, 0));

        final Tree fakeTree = new J.Empty(
                testId, Space.EMPTY,
                Markers.EMPTY);
        cursorField.set(dummyPrinter, new Cursor(null, fakeTree));

        appendMethod.invoke(capture, "hello");
        Assertions.assertFalse(parentMap.containsKey(null),
                "parentMap should not have null keys if childId was null");
        final Object newRange = nodeRanges.get(testId);
        final Method startColMethod = rangeClass.getDeclaredMethod("startCol");
        startColMethod.setAccessible(true);
        final int startCol = (Integer) startColMethod.invoke(newRange);
        Assertions.assertEquals(42, startCol, "startCol should be preserved and not default to 0");
    }

    @Test
    public void testMarkerVisitorDoesNotEnterBlockIfMarkerPresent() throws Exception {
        final Path path = Paths.get("TestMarkerProxy.java");
        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(Collections.emptyList());
        final JavaParser parser = JavaParser.fromJavaVersion().build();
        final J.CompilationUnit compUnit = parser.parse("class A {}")
                .findFirst().get().withSourcePath(path);

        final ExecutionContext ctx = new InMemoryExecutionContext();
        final var acc = recipe.getInitialValue(ctx);
        final Object visitor = recipe.getVisitor(acc);

        final Class<?> markerClass = Class.forName(
                "org.checkstyle.autofix.marker.ViolationMarkerRecipe$MarkersApplied");
        final Constructor<?> markerCtor =
                markerClass.getDeclaredConstructor(UUID.class);
        markerCtor.setAccessible(true);
        final Marker appliedMarker = (Marker)
                markerCtor.newInstance(Tree.randomId());
        final J.CompilationUnit markedUnit =
                compUnit.withMarkers(compUnit.getMarkers().add(appliedMarker));

        final Field field = Accumulator.class.getDeclaredField("byFile");
        field.setAccessible(true);
        final Map<Path, Map<UUID,
                List<CheckstyleViolationMarker>>> throwingMap = new HashMap<>() {
                    @Override
                    public Map<UUID, List<CheckstyleViolationMarker>>
                            getOrDefault(
                                    Object key,
                                    Map<UUID,
                                            List<CheckstyleViolationMarker>> defValue) {
                        throw new RuntimeException("Should not have queried accumulator");
                    }
                };
        field.set(acc, throwingMap);
        ((TreeVisitor<?, ExecutionContext>) visitor)
                .visit(markedUnit, ctx);
    }

    @Test
    public void testMarkerVisitorDoesNotVisitChildrenIfFileMarkersEmpty() throws Exception {
        final Path path = Paths.get("TestEmptyFileMarkers.java");
        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(Collections.emptyList());
        final JavaParser parser = JavaParser.fromJavaVersion().build();
        final J.CompilationUnit compUnit = parser.parse("class A {}")
                .findFirst().get().withSourcePath(path);

        final ExecutionContext ctx = new InMemoryExecutionContext();
        final var acc = recipe.getInitialValue(ctx);
        final Object visitor = recipe.getVisitor(acc);

        final List<J.ClassDeclaration> throwingClasses =
                new AbstractList<>() {
                    @Override
                    public J.ClassDeclaration get(int index) {
                        throw new RuntimeException("Visited children!");
                    }

                    @Override
                    public int size() {
                        return 1;
                    }

                    @Override
                    public Iterator<J.ClassDeclaration>
                            iterator() {
                        throw new RuntimeException("Visited children!");
                    }
                };
        final J.CompilationUnit hackedUnit = compUnit.withClasses(throwingClasses);

        ((TreeVisitor<?, ExecutionContext>) visitor)
                .visit(hackedUnit, ctx);
    }

    @Test
    public void testMarkerVisitorPreVisitDoesNotGetFromEmptyMap() throws Exception {
        final Path path = Paths.get("TestEmptyFileMarkersPreVisit.java");
        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(Collections.emptyList());
        final JavaParser parser = JavaParser.fromJavaVersion().build();
        final J.CompilationUnit compUnit = parser.parse("class A {}")
                .findFirst().get().withSourcePath(path);

        final ExecutionContext ctx = new InMemoryExecutionContext();
        final var acc = recipe.getInitialValue(ctx);
        final Object visitor = recipe.getVisitor(acc);

        final Map<UUID, List<CheckstyleViolationMarker>> emptyThrowingMap =
                new AbstractMap<>() {
                    @Override
                    public boolean isEmpty() {
                        return true;
                    }

                    @Override
                    public List<CheckstyleViolationMarker> get(Object key) {
                        throw new RuntimeException("Should not have called get() "
                                + "because map is empty");
                    }

                    @Override
                    public Set<Map.Entry<UUID,
                            List<CheckstyleViolationMarker>>> entrySet() {
                        return Collections.emptySet();
                    }
                };

        final Field fileMarkersField =
                visitor.getClass().getDeclaredField("fileMarkers");
        fileMarkersField.setAccessible(true);
        fileMarkersField.set(visitor, emptyThrowingMap);
        final Method preVisitMethod =
                visitor.getClass().getDeclaredMethod(
                "preVisit", J.class,
                ExecutionContext.class);
        preVisitMethod.setAccessible(true);

        try {
            preVisitMethod.invoke(visitor, compUnit, ctx);
        }
        catch (InvocationTargetException exc) {
            Assertions.fail("Mutation bypassed the empty check in preVisit and threw: "
                    + exc.getCause());
        }
    }

    @Test
    public void testScannerVisitorDoesNotEnterProcessFileViolationsIfEmpty() throws Exception {
        final Path path = Paths.get("TestScannerEmpty.java");
        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(Collections.emptyList());
        final JavaParser parser = JavaParser.fromJavaVersion().build();
        final J.CompilationUnit compUnit = parser.parse("class A {}")
                .findFirst().get().withSourcePath(path);

        final ExecutionContext ctx = new InMemoryExecutionContext();
        final var acc = recipe.getInitialValue(ctx);
        final Object scanner = recipe.getScanner(acc);

        final Cursor noParentCursor = new Cursor(null, compUnit);
        final Field cursorField =
                TreeVisitor.class.getDeclaredField("cursor");
        cursorField.setAccessible(true);
        cursorField.set(scanner, noParentCursor);
        final Method visitMethod = scanner.getClass().getDeclaredMethod(
                "visitCompilationUnit", J.CompilationUnit.class,
                ExecutionContext.class);
        visitMethod.setAccessible(true);

        try {
            visitMethod.invoke(scanner, compUnit, ctx);
        }
        catch (InvocationTargetException exc) {
            Assertions.fail("Mutation bypassed the empty check and threw: " + exc.getCause());
        }
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

    @Test
    public void testMarkerVisitorOuterClassReference() throws Exception {
        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(Collections.emptyList());
        final ExecutionContext ctx = new InMemoryExecutionContext();
        final var acc = recipe.getInitialValue(ctx);
        final Object visitor = recipe.getVisitor(acc);

        Field outerClassField = null;
        for (Field field : visitor.getClass().getDeclaredFields()) {
            if (field.getType().equals(ViolationMarkerRecipe.class)) {
                outerClassField = field;
                break;
            }
        }

        if (outerClassField != null) {
            outerClassField.setAccessible(true);
            final Object outerClassInstance = outerClassField.get(visitor);
            Assertions.assertSame(recipe, outerClassInstance,
                    "MarkerVisitor should retain reference to its enclosing "
                    + "ViolationMarkerRecipe");
        }

    }

    @Test
    public void testFindSmallestNodeExactMatchMutations() throws Exception {
        final Class<?> scannerClass = Class.forName(
            "org.checkstyle.autofix.marker.ViolationMarkerRecipe$ScannerVisitor");
        final Class<?> rangeClass = Class.forName(
            "org.checkstyle.autofix.marker.ViolationMarkerRecipe$Range");
        final Method findMethod = scannerClass.getDeclaredMethod(
            "findSmallestNode",
            CheckstyleViolation.class,
            Map.class, Map.class, Map.class);
        findMethod.setAccessible(true);

        final Constructor<?> rangeCtor = rangeClass.getDeclaredConstructor(
            int.class, int.class, int.class, int.class);
        rangeCtor.setAccessible(true);

        final ViolationMarkerRecipe recipe = new ViolationMarkerRecipe(Collections.emptyList());
        final Object scanner = recipe.getScanner(
            recipe.getInitialValue(new InMemoryExecutionContext()));

        final CheckstyleCheck check = new CheckstyleCheck(
            CheckFullName.FINAL_LOCAL_VARIABLE, "id");
        final Map<UUID, UUID> parentMap = new HashMap<>();
        final Map<UUID, Tree> nodeTrees = new HashMap<>();

        final CheckstyleViolation viol1 = new CheckstyleViolation(
            2, 10, "err", check, "msg", Paths.get("a"));

        final UUID id1 = Tree.randomId();
        final UUID id2 = Tree.randomId();
        final Map<UUID, Object> ranges1 = new LinkedHashMap<>();
        ranges1.put(id1, rangeCtor.newInstance(1, 1, 3, 10));
        ranges1.put(id2, rangeCtor.newInstance(2, 5, 2, 15));

        final Map<UUID, ?> result1 = (Map<UUID, ?>) findMethod.invoke(
            scanner, viol1, ranges1, parentMap, nodeTrees);
        Assertions.assertTrue(result1.containsKey(id2), "id2 should win");

        final UUID id3 = Tree.randomId();
        final UUID id4 = Tree.randomId();
        final Map<UUID, Object> ranges2 = new LinkedHashMap<>();
        ranges2.put(id3, rangeCtor.newInstance(2, 5, 2, 10));
        ranges2.put(id4, rangeCtor.newInstance(2, 2, 2, 10));

        final Map<UUID, ?> result2 = (Map<UUID, ?>) findMethod.invoke(
            scanner, viol1, ranges2, parentMap, nodeTrees);
        Assertions.assertTrue(result2.containsKey(id3), "id3 should win");

        final UUID id5 = Tree.randomId();
        final UUID id6 = Tree.randomId();
        final Map<UUID, Object> ranges3 = new LinkedHashMap<>();
        ranges3.put(id5, rangeCtor.newInstance(1, 1, 2, 10));
        ranges3.put(id6, rangeCtor.newInstance(2, 5, 2, 15));

        final Map<UUID, ?> result3 = (Map<UUID, ?>) findMethod.invoke(
            scanner, viol1, ranges3, parentMap, nodeTrees);
        Assertions.assertTrue(result3.containsKey(id5), "id5 should win");
    }

}
