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

import org.checkstyle.autofix.CheckFullName;
import org.checkstyle.autofix.marker.CheckstyleViolationMarker;
import org.openrewrite.Cursor;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.Tree;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaVisitor;
import org.openrewrite.java.tree.Expression;
import org.openrewrite.java.tree.Flag;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JLeftPadded;
import org.openrewrite.java.tree.JavaType;
import org.openrewrite.java.tree.Space;
import org.openrewrite.java.tree.Statement;
import org.openrewrite.java.tree.TypeUtils;
import org.openrewrite.marker.Markers;

/**
 * Fixes Checkstyle RequireThis violations by adding 'this.' prefix
 * to references of instance variables and methods.
 */
public class RequireThis extends Recipe {

    public RequireThis() {
    }

    @Override
    public String getDisplayName() {
        return "RequireThis recipe";
    }

    @Override
    public String getDescription() {
        return "Adds 'this.' prefix to references of instance variables and methods "
                + "that require explicit 'this.' qualification.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new RequireThisVisitor();
    }

    private final class RequireThisVisitor extends JavaVisitor<ExecutionContext> {

        private static final String THIS_PREFIX = "this";
        private static final char DOT = '.';

        private RequireThisVisitor() {
        }

        @Override
        public J visitIdentifier(J.Identifier identifier, ExecutionContext executionContext) {
            final J.Identifier ident = (J.Identifier) super.visitIdentifier(
                    identifier, executionContext);
            J result = ident;

            if (isRequireThisViolation(ident) && !isTypeMismatchWithField(ident)
                    && !isConstantVariable(ident)) {
                if (!isParentFieldAccess() && !isMethodInvocationName()) {
                    result = createThisFieldAccess(ident);
                }
            }
            return result;
        }

        private boolean isConstantVariable(J.Identifier ident) {
            boolean isConstant = false;
            final JavaType.Variable varType = ident.getFieldType();
            if (varType != null && varType.hasFlags(Flag.Final)) {
                final JavaType type = varType.getType();
                if (type instanceof JavaType.Primitive || TypeUtils.isString(type)) {
                    isConstant = true;
                }
            }
            return isConstant;
        }

        @Override
        public J visitMethodInvocation(J.MethodInvocation method,
                                        ExecutionContext executionContext) {
            J.MethodInvocation result = (J.MethodInvocation) super.visitMethodInvocation(
                    method, executionContext);

            final boolean hasViolation = isRequireThisViolation(result.getName());

            if (hasViolation && result.getSelect() == null) {
                JavaType type = null;
                if (result.getMethodType() != null) {
                    type = result.getMethodType().getDeclaringType();
                }

                final Expression thisExpr = buildThisExpression(type);
                if (thisExpr != null) {
                    result = result.withSelect(thisExpr);
                }
            }
            return result;
        }

        private Expression createThisFieldAccess(J.Identifier ident) {
            JavaType type = null;
            if (ident.getFieldType() != null) {
                type = ident.getFieldType().getOwner();
            }

            Expression result = ident;
            final Expression thisExpression = buildThisExpression(type);
            if (thisExpression != null) {
                result = new J.FieldAccess(
                        Tree.randomId(),
                        ident.getPrefix(),
                        Markers.EMPTY,
                        thisExpression,
                        new JLeftPadded<>(Space.EMPTY,
                                ident.withPrefix(Space.EMPTY), Markers.EMPTY),
                        null
                );
            }
            return result;
        }

        private boolean declaresField(J.Block body, J node) {
            boolean declares = false;
            if (node instanceof J.Identifier) {
                final String fieldName = ((J.Identifier) node).getSimpleName();
                for (Statement statement : body.getStatements()) {
                    if (statement instanceof J.VariableDeclarations) {
                        for (J.VariableDeclarations.NamedVariable variable
                                : ((J.VariableDeclarations) statement).getVariables()) {
                            if (variable.getSimpleName().equals(fieldName)) {
                                declares = true;
                                break;
                            }
                        }
                    }
                    if (declares) {
                        break;
                    }
                }
            }
            return declares;
        }

        private Expression buildThisExpression(JavaType type) {
            final Expression thisExpression;
            if (type instanceof JavaType.FullyQualified) {
                thisExpression = buildOuterThisExpression((JavaType.FullyQualified) type);
            }
            else {
                thisExpression = buildSimpleThisExpression(type);
            }
            return thisExpression;
        }

        private Expression buildOuterThisExpression(JavaType.FullyQualified ownerType) {
            Expression result = null;
            final Cursor cursor = getCursor();
            final Object enclosing = cursor.dropParentUntil(node -> {
                return node instanceof J.ClassDeclaration
                        || node instanceof J.NewClass && ((J.NewClass) node).getBody() != null
                        || node == Cursor.ROOT_VALUE;
            }).getValue();

            if (isAnonymousClass(ownerType)) {
                if (enclosing instanceof J.NewClass) {
                    final J.NewClass newClass = (J.NewClass) enclosing;
                    if (newClass.getBody() != null
                            && declaresField(newClass.getBody(), getCursor().getValue())) {
                        result = buildSimpleThisExpression(ownerType);
                    }
                }
            }
            else {
                final boolean needsOuter = needsOuterClass(enclosing, ownerType);

                if (needsOuter) {
                    final String className = findLexicallyEnclosingClass(cursor, ownerType);
                    final J.Identifier outerIdent = new J.Identifier(
                            Tree.randomId(), Space.EMPTY, Markers.EMPTY, null,
                            className, null, null);
                    final J.Identifier thisIdent = new J.Identifier(
                            Tree.randomId(), Space.EMPTY, Markers.EMPTY, null, THIS_PREFIX,
                            ownerType, null);
                    result = new J.FieldAccess(
                            Tree.randomId(), Space.EMPTY, Markers.EMPTY, outerIdent,
                            new JLeftPadded<>(Space.EMPTY, thisIdent, Markers.EMPTY), null);
                }
                else {
                    result = buildSimpleThisExpression(ownerType);
                }
            }
            return result;
        }

        private static String findLexicallyEnclosingClass(Cursor startCursor,
                                                          JavaType.FullyQualified ownerType) {
            Cursor searchCursor = startCursor;
            String foundName = null;
            while (searchCursor != null && foundName == null) {
                final Object val = searchCursor.getValue();
                if (val instanceof J.ClassDeclaration) {
                    final JavaType.FullyQualified fullyQualifiedType =
                            ((J.ClassDeclaration) val).getType();
                    if (TypeUtils.isAssignableTo(ownerType, fullyQualifiedType)) {
                        foundName = getClassNameWithoutPackage(fullyQualifiedType);
                    }
                }
                searchCursor = searchCursor.getParent();
            }
            if (foundName == null) {
                foundName = getClassNameWithoutPackage(ownerType);
            }
            return foundName;
        }

        private static boolean needsOuterClass(Object enclosing,
                                               JavaType.FullyQualified ownerType) {
            JavaType enclosingType = null;
            if (enclosing instanceof J.ClassDeclaration) {
                enclosingType = ((J.ClassDeclaration) enclosing).getType();
            }
            else if (enclosing instanceof J.NewClass) {
                enclosingType = ((J.NewClass) enclosing).getType();
            }
            return enclosingType instanceof JavaType.FullyQualified
                    && !isTypeAssignable(ownerType, (JavaType.FullyQualified) enclosingType);
        }

        private static boolean isAnonymousClass(JavaType.FullyQualified type) {
            boolean isAnonymous = false;
            if (type != null && type.getClassName() != null) {
                final String simpleName = getClassNameWithoutPackage(type);
                isAnonymous = simpleName.isEmpty() || simpleName.matches("\\d+");
            }
            return isAnonymous;
        }

        private static boolean isTypeAssignable(JavaType.FullyQualified target,
                                                JavaType.FullyQualified source) {
            return TypeUtils.isAssignableTo(target.getFullyQualifiedName(), source);
        }

        private static String getClassNameWithoutPackage(JavaType.FullyQualified type) {
            final String className = type.getClassName();
            return className.substring(className.lastIndexOf(DOT) + 1);
        }

        private static Expression buildSimpleThisExpression(JavaType type) {
            return new J.Identifier(
                    Tree.randomId(),
                    Space.EMPTY,
                    Markers.EMPTY,
                    null,
                    THIS_PREFIX,
                    type,
                    null
            );
        }

        private boolean isParentFieldAccess() {
            return getCursor().getParentTreeCursor().getValue() instanceof J.FieldAccess;
        }

        private boolean isMethodInvocationName() {
            final Object parent = getCursor().getParentTreeCursor().getValue();
            boolean isMethodName = false;
            if (parent instanceof J.MethodInvocation method) {
                isMethodName = method.getName() == getCursor().getValue();
            }
            return isMethodName;
        }

        private boolean isTypeMismatchWithField(J.Identifier ident) {
            boolean isMismatch = false;
            if (ident.getFieldType() != null
                    && ident.getFieldType().getOwner() instanceof JavaType.Method) {
                final J.ClassDeclaration enclosingClass = getCursor()
                        .firstEnclosing(J.ClassDeclaration.class);
                if (enclosingClass != null && enclosingClass.getType() != null) {
                    JavaType.Variable field = null;
                    for (JavaType.Variable member : enclosingClass.getType().getMembers()) {
                        if (member.getName().equals(ident.getSimpleName())) {
                            field = member;
                            break;
                        }
                    }
                    isMismatch = field != null && !(TypeUtils.isAssignableTo(
                            field.getType(), ident.getFieldType().getType())
                            || TypeUtils.isAssignableTo(
                                    ident.getFieldType().getType(), field.getType()));
                }
            }
            return isMismatch;
        }

        private boolean isRequireThisViolation(J tree) {
            return tree.getMarkers()
                    .findAll(CheckstyleViolationMarker.class).stream()
                    .anyMatch(marker -> marker.isFor(CheckFullName.REQUIRE_THIS));
        }
    }

}
