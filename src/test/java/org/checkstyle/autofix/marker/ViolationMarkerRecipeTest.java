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
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
