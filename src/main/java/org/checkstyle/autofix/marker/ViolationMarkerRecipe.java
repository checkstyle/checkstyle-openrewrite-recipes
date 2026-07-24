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

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.checkstyle.autofix.CheckFullName;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.openrewrite.Cursor;
import org.openrewrite.ExecutionContext;
import org.openrewrite.PrintOutputCapture;
import org.openrewrite.ScanningRecipe;
import org.openrewrite.Tree;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.marker.Marker;

public class ViolationMarkerRecipe extends ScanningRecipe<Accumulator> {

    private static final String DISPLAY_NAME = "Checkstyle violation marker";
    private static final String DESCRIPTION =
            "Marks AST nodes that correspond to Checkstyle violations.";

    private static final MarkersApplied APPLIED_MARKER = new MarkersApplied(Tree.randomId());

    private static final Map<CheckFullName, Class<? extends Tree>> TARGET_TYPES =
            new EnumMap<>(CheckFullName.class);

    static {
        TARGET_TYPES.put(CheckFullName.HEADER, J.CompilationUnit.class);
        TARGET_TYPES.put(CheckFullName.FINAL_CLASS, J.ClassDeclaration.class);
        TARGET_TYPES.put(CheckFullName.FINAL_LOCAL_VARIABLE,
                J.VariableDeclarations.NamedVariable.class);
        TARGET_TYPES.put(CheckFullName.ANNOTATION_ON_SAME_LINE, J.Annotation.class);
        TARGET_TYPES.put(CheckFullName.AVOID_STAR_IMPORT, J.Import.class);
        TARGET_TYPES.put(CheckFullName.UNUSED_IMPORT, J.Import.class);
        TARGET_TYPES.put(CheckFullName.UNUSED_LOCAL_VARIABLE, J.VariableDeclarations.class);
        TARGET_TYPES.put(CheckFullName.MISSING_OVERRIDE, J.MethodDeclaration.class);
        TARGET_TYPES.put(CheckFullName.USE_ENHANCED_SWITCH, J.Switch.class);
        TARGET_TYPES.put(CheckFullName.CONSTRUCTORS_DECLARATION_GROUPING,
                J.MethodDeclaration.class);
    }

    private final List<CheckstyleViolation> violations;

    public ViolationMarkerRecipe(List<CheckstyleViolation> violations) {
        this.violations = new ArrayList<>(violations);
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public Accumulator getInitialValue(ExecutionContext executionContext) {
        return new Accumulator();
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getScanner(Accumulator acc) {
        return new ScannerVisitor(acc);
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor(Accumulator acc) {
        return new MarkerVisitor(acc);
    }

    private final class ScannerVisitor extends JavaIsoVisitor<ExecutionContext> {
        private final Accumulator acc;

        ScannerVisitor(Accumulator acc) {
            this.acc = acc;
        }

        @Override
        public J.CompilationUnit visitCompilationUnit(J.CompilationUnit compUnit,
                                                      ExecutionContext executionContext) {
            final Path sourcePath = compUnit.getSourcePath();
            final List<CheckstyleViolation> fileViolations = violations.stream()
                    .filter(violation -> violation.getFilePath().endsWith(sourcePath))
                    .toList();

            processFileViolations(compUnit, sourcePath, fileViolations);
            return compUnit;
        }

        private void processFileViolations(J.CompilationUnit compilationUnit, Path sourcePath,
                                           List<CheckstyleViolation> fileViolations) {
            final Map<UUID, Range> nodeRanges = new LinkedHashMap<>();
            final Map<UUID, UUID> parentMap = new HashMap<>();
            final Map<UUID, Tree> nodeTrees = new HashMap<>();
            final Cursor rootCursor = new Cursor(null, "root");
            final TreeVisitor<?, PrintOutputCapture<TreeVisitor<?, ?>>> printer =
                    compilationUnit.printer(rootCursor);

            final BoundingBoxCapture capture = new BoundingBoxCapture(
                    printer, nodeRanges, parentMap, nodeTrees);
            printer.visit(compilationUnit, capture, rootCursor);

            final Map<UUID, List<CheckstyleViolationMarker>> markersToAdd = new HashMap<>();
            for (CheckstyleViolation violation : fileViolations) {
                final Map<UUID, List<CheckstyleViolationMarker>> result = findSmallestNode(
                        violation, nodeRanges, parentMap, nodeTrees);
                for (Map.Entry<UUID, List<CheckstyleViolationMarker>> entry : result.entrySet()) {
                    markersToAdd.computeIfAbsent(entry.getKey(), id -> new ArrayList<>())
                            .addAll(entry.getValue());
                }
            }

            acc.putByFile(sourcePath, markersToAdd);
        }

        private Map<UUID, List<CheckstyleViolationMarker>> findSmallestNode(
                CheckstyleViolation violation, Map<UUID, Range> nodeRanges,
                Map<UUID, UUID> parentMap, Map<UUID, Tree> nodeTrees) {

            return findEnclosingNodeId(violation, nodeRanges)
                    .map(smallestNodeId -> {
                        final UUID targetId = resolveTargetNode(smallestNodeId, violation,
                                parentMap, nodeTrees);
                        final List<CheckstyleViolationMarker> markerList =
                            new ArrayList<>(List.of(
                                new CheckstyleViolationMarker(Tree.randomId(), violation)));
                        return Map.of(targetId, markerList);
                    })
                    .orElseGet(Collections::emptyMap);
        }

        private Optional<UUID> findEnclosingNodeId(CheckstyleViolation violation,
                                                   Map<UUID, Range> nodeRanges) {
            return Optional.ofNullable(findEnclosingNode(violation, nodeRanges, false))
                    .or(() -> Optional.ofNullable(findEnclosingNode(violation, nodeRanges, true)));
        }

        private UUID findEnclosingNode(CheckstyleViolation violation,
                                       Map<UUID, Range> nodeRanges,
                                       boolean lineOnly) {
            final Comparator<Range> rangeCmp = Comparator.comparingInt(Range::startLine)
                    .thenComparingInt(Range::startCol)
                    .reversed();
            final Comparator<Map.Entry<UUID, Range>> cmp = Map.Entry.comparingByValue(rangeCmp);

            return nodeRanges.entrySet().stream()
                    .filter(entry -> {
                        final boolean matches;
                        if (lineOnly) {
                            matches = startsBeforeOrAtLine(entry.getValue(), violation);
                        }
                        else {
                            matches = encloses(entry.getValue(), violation);
                        }
                        return matches;
                    })
                    .min(cmp)
                    .map(Map.Entry::getKey)
                    .orElse(null);
        }

        private boolean startsBeforeOrAtLine(Range range, CheckstyleViolation violation) {
            return range.startLine() <= violation.getLine();
        }

        private UUID resolveTargetNode(UUID startNodeId, CheckstyleViolation violation,
                                       Map<UUID, UUID> parentMap, Map<UUID, Tree> nodeTrees) {
            UUID resultId = startNodeId;
            final CheckFullName checkFullName = violation.getSource().checkName();
            final Class<? extends Tree> targetType = TARGET_TYPES.get(checkFullName);

            if (targetType != null) {
                UUID currentId = startNodeId;
                boolean found = false;
                while (currentId != null && !found) {
                    if (targetType.isInstance(nodeTrees.get(currentId))) {
                        resultId = currentId;
                        found = true;
                    }
                    currentId = parentMap.get(currentId);
                }
            }
            return resultId;
        }

        private boolean encloses(Range range, CheckstyleViolation violation) {
            final boolean result;
            if (range.startLine() == violation.getLine()) {
                result = range.startCol() <= violation.getColumn();
            }
            else {
                result = range.startLine() < violation.getLine();
            }
            return result;
        }
    }

    private static final class BoundingBoxCapture extends PrintOutputCapture<TreeVisitor<?, ?>> {
        private final Map<UUID, Range> nodeRanges;
        private final Map<UUID, UUID> parentMap;
        private final Map<UUID, Tree> nodeTrees;
        private int line;
        private int column;
        // Index in out where last append(String) ended. The printer may also write
        // to out directly (bypassing this override), so anything past this offset is
        // output we haven't yet counted into line/column.
        private int lastAppendEnd;

        BoundingBoxCapture(TreeVisitor<?, PrintOutputCapture<TreeVisitor<?, ?>>> printer,
                           Map<UUID, Range> nodeRanges,
                           Map<UUID, UUID> parentMap,
                           Map<UUID, Tree> nodeTrees) {
            super(printer);
            this.nodeRanges = nodeRanges;
            this.parentMap = parentMap;
            this.nodeTrees = nodeTrees;
            this.line = 1;
        }

        private void advance(String segment) {
            for (int index = 0; index < segment.length(); index++) {
                if (segment.charAt(index) == '\n') {
                    line++;
                    column = 1;
                }
                else {
                    column++;
                }
            }
        }

        @Override
        public PrintOutputCapture<TreeVisitor<?, ?>> append(String text) {
            advance(out.substring(lastAppendEnd));

            final Cursor currentCursor = getContext().getCursor();
            final Iterator<Object> it = currentCursor.getPath();

            UUID childId = null;
            while (it.hasNext()) {
                final Object obj = it.next();
                if (obj instanceof Tree treeNode) {
                    final UUID currentId = treeNode.getId();
                    nodeRanges.putIfAbsent(currentId,
                            new Range(line, column, 0, 0));
                    nodeTrees.putIfAbsent(currentId, treeNode);
                    if (childId != null) {
                        parentMap.putIfAbsent(childId, currentId);
                    }
                    childId = currentId;
                }
            }

            advance(text);

            super.append(text);
            lastAppendEnd = out.length();
            return this;
        }
    }

    private final class MarkerVisitor extends JavaIsoVisitor<ExecutionContext> {
        private final Accumulator acc;
        private Map<UUID, List<CheckstyleViolationMarker>> fileMarkers = Collections.emptyMap();

        MarkerVisitor(Accumulator acc) {
            this.acc = acc;
        }

        @Override
        public J.CompilationUnit visitCompilationUnit(
                J.CompilationUnit compUnit, ExecutionContext executionContext) {
            J.CompilationUnit result = compUnit;
            if (!compUnit.getMarkers().findFirst(MarkersApplied.class).isPresent()) {
                fileMarkers = acc.getByFile(compUnit.getSourcePath());
                if (!fileMarkers.isEmpty()) {
                    result = super.visitCompilationUnit(compUnit, executionContext);

                    final List<CheckstyleViolationMarker> markers = fileMarkers.get(result.getId());
                    if (markers != null) {
                        for (CheckstyleViolationMarker marker : markers) {
                            result = result.withMarkers(result.getMarkers().add(marker));
                        }
                    }
                }
                result = result.withMarkers(result.getMarkers().add(APPLIED_MARKER));
            }
            return result;
        }

        @Override
        public J preVisit(J tree, ExecutionContext executionContext) {
            J result = tree;
            final List<CheckstyleViolationMarker> markers =
                    fileMarkers.get(result.getId());
            if (markers != null) {
                for (CheckstyleViolationMarker marker : markers) {
                    result = result.withMarkers(result.getMarkers().add(marker));
                }
            }
            return result;
        }
    }

    private record Range(int startLine, int startCol, int endLine, int endCol) {
    }

    private record MarkersApplied(UUID id) implements Marker {

        @Override
        public UUID getId() {
            return id;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <M extends Marker> M withId(UUID uuid) {
            return (M) new MarkersApplied(uuid);
        }
    }
}
