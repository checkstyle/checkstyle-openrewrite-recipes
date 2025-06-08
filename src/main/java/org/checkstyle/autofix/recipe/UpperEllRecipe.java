package org.checkstyle.autofix.recipe;

import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JavaType;

/**
 * Fixes Checkstyle UpperEll violations by replacing lowercase 'l' suffix
 * in long literals with uppercase 'L'.
 */
public class UpperEllRecipe extends Recipe {

    @Override
    public String getDisplayName() {
        return "UpperEll recipe";
    }

    @Override
    public String getDescription() {
        return "Replace lowercase 'l' suffix in long literals with uppercase 'L' "
                + "to improve readability.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new UpperEllVisitor();
    }

    /**
     * Visitor that replaces lowercase 'l' suffixes in long literals with uppercase 'L'.
     */
    private static final class UpperEllVisitor extends JavaIsoVisitor<ExecutionContext> {
        @Override
        public J.Literal visitLiteral(J.Literal literal, ExecutionContext ctx) {
            J.Literal result = super.visitLiteral(literal, ctx);
            final String valueSource = result.getValueSource();

            if (valueSource != null && valueSource.endsWith("l")
                    && result.getType() == JavaType.Primitive.Long) {
                final String numericPart = valueSource.substring(0, valueSource.length() - 1);
                final String newValueSource = numericPart + "L";
                result = result.withValueSource(newValueSource);
            }
            return result;
        }

    }
}
