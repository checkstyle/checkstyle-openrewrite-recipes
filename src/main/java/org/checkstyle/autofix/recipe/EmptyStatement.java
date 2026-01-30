package org.checkstyle.autofix.recipe;

import java.util.ArrayList;
import java.util.List;

import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.Statement;

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
        return new JavaIsoVisitor<ExecutionContext>() {

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

        };
    }
}