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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.Tree;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JRightPadded;
import org.openrewrite.java.tree.Space;
import org.openrewrite.java.tree.Statement;
import org.openrewrite.marker.Markers;

/**
 * OpenRewrite recipe to fix Checkstyle EmptyStatement violations.
 *
 * <p>Detects and removes empty statements (standalone semicolons) that serve no purpose.
 *
 * <p>Examples of violations:
 * <ul>
 *   <li>Double semicolons: int x = 5;;</li>
 *   <li>Empty if bodies: if (condition);</li>
 *   <li>Empty while/for loops: while(true);</li>
 * </ul>
 *
 * @see <a href="https://checkstyle.sourceforge.io/checks/coding/emptystatement.html">
 *     EmptyStatement Check</a>
 */
public class EmptyStatement extends Recipe {

    @Override
    public String getDisplayName() {
        return "EmptyStatement - Remove empty statements";
    }

    @Override
    public String getDescription() {
        return "Detects and removes empty statements (standalone semicolons).";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new EmptyStatementVisitor();
    }

    /**
     * Creates an empty block to replace an empty statement.
     *
     * @param prefix the prefix space to use for the block
     * @return a new empty J.Block
     */
    private static J.Block createEmptyBlock(Space prefix) {
        return new J.Block(
                Tree.randomId(),
                prefix,
                Markers.EMPTY,
                JRightPadded.build(false),
                Collections.emptyList(),
                Space.EMPTY
        );
    }

    /**
     * Visitor that removes empty statements from blocks and replaces empty
     * control flow bodies with empty blocks.
     */
    private static class EmptyStatementVisitor extends JavaIsoVisitor<ExecutionContext> {

        @Override
        public J.Block visitBlock(J.Block block, ExecutionContext ctx) {
            final J.Block visited = super.visitBlock(block, ctx);

            final List<Statement> filtered = new ArrayList<>();
            for (Statement stmt : visited.getStatements()) {
                if (!(stmt instanceof J.Empty)) {
                    filtered.add(stmt);
                }
            }

            if (filtered.size() != visited.getStatements().size()) {
                return visited.withStatements(filtered);
            }
            return visited;
        }

        @Override
        public J.If visitIf(J.If iff, ExecutionContext ctx) {
            J.If visited = super.visitIf(iff, ctx);

            if (visited.getThenPart() instanceof J.Empty) {
                visited = visited.withThenPart(
                        createEmptyBlock(Space.build(" ", Collections.emptyList())));
            }

            return visited;
        }

        @Override
        public J.WhileLoop visitWhileLoop(J.WhileLoop whileLoop, ExecutionContext ctx) {
            J.WhileLoop visited = super.visitWhileLoop(whileLoop, ctx);

            if (visited.getBody() instanceof J.Empty) {
                visited = visited.withBody(
                        createEmptyBlock(Space.build(" ", Collections.emptyList())));
            }

            return visited;
        }

        @Override
        public J.ForLoop visitForLoop(J.ForLoop forLoop, ExecutionContext ctx) {
            J.ForLoop visited = super.visitForLoop(forLoop, ctx);

            if (visited.getBody() instanceof J.Empty) {
                visited = visited.withBody(
                        createEmptyBlock(Space.build(" ", Collections.emptyList())));
            }

            return visited;
        }

        @Override
        public J.DoWhileLoop visitDoWhileLoop(J.DoWhileLoop doWhileLoop, ExecutionContext ctx) {
            J.DoWhileLoop visited = super.visitDoWhileLoop(doWhileLoop, ctx);

            if (visited.getBody() instanceof J.Empty) {
                visited = visited.withBody(
                        createEmptyBlock(Space.build(" ", Collections.emptyList())));
            }

            return visited;
        }
    }
}