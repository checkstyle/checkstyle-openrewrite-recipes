package org.checkstyle.autofix;

import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;

/**
 * Recipe to rename classes starting with 'Input' to 'Output'.
 * For example, a class named InputExample will be renamed to OutputExample.
 */
public class ClassRenameRecipe extends Recipe {

    @Override
    public String getDisplayName() {
        return "Rename Input-prefixed classes";
    }

    @Override
    public String getDescription() {
        return "Renames classes from InputXxx to OutputXxx.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new ClassRenameVisitor();
    }

    /**
     * A visitor that traverse Java AST nodes and renames classes starting with "Input" to "Output".
     */
    private static final class ClassRenameVisitor extends JavaIsoVisitor<ExecutionContext> {

        /** The prefix to match in class names. */
        private static final String FROM_PREFIX = "Input";
        /** The prefix to replace with in class names. */
        private static final String TO_PREFIX = "Output";

        @Override
        public J.ClassDeclaration visitClassDeclaration(J.ClassDeclaration classDecl,
                                                        ExecutionContext executionContext) {
            final String newName = renameIfMatch(classDecl.getSimpleName());
            final J.ClassDeclaration result;
            if (newName != null) {
                result = classDecl.withName(classDecl.getName().withSimpleName(newName));
            }
            else {
                result = classDecl;
            }
            return result;
        }

        @Override
        public J.NewClass visitNewClass(J.NewClass constructorNode,
                                        ExecutionContext executionContext) {
            J.NewClass result = constructorNode;
            if (constructorNode.getClazz() instanceof J.Identifier) {
                final J.Identifier clazz = (J.Identifier) constructorNode.getClazz();
                final String newName = renameIfMatch(clazz.getSimpleName());
                if (newName != null) {
                    result = constructorNode.withClazz(clazz.withSimpleName(newName));
                }
            }
            return result;
        }

        /**
         * Checks if a given class name starts with the FROM_PREFIX
         * and returns the renamed version with TO_PREFIX.
         *
         * @param originalName The original class name.
         * @return The new class name with TO_PREFIX if matched, otherwise null.
         */
        private String renameIfMatch(String originalName) {
            final String result;
            if (originalName.startsWith(FROM_PREFIX)) {
                result = TO_PREFIX + originalName.substring(FROM_PREFIX.length());
            }
            else {
                result = null;
            }
            return result;
        }
    }
}
