/*xml
  <module name="Checker">
    <module name="TreeWalker">
      <module name="com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceAfterCheck">
        <property name="tokens" value="ARRAY_INIT, AT, INC, DEC, UNARY_MINUS, UNARY_PLUS, BNOT, LNOT, DOT, ARRAY_DECLARATOR, INDEX_OP, TYPECAST, METHOD_REF, LITERAL_SYNCHRONIZED"/>
      </module>
    </module>
  </module>
*/
package org.checkstyle.autofix.recipe.nowhitespaceafter.tokens;

class OutputTokens {
    public void dotOperator(String s) {
        Integer.parseInt(s);
        Integer .parseInt(s);
        hashCode();
    }

    public void arrayDec() {
        int[] arr = new int[2];
        int[] array;
        arr[ 0 ] = 1;
        int[] a = {1, 2 };
        int[] emptyArray = {};
    }

    public void fieldAccess() {
        System .out.println();
    }

    public void bitwiseNot(int a) {
        a = ~a;
        a = +a;
        a = -a;
        boolean b = !(a > 0);
    }

    public void incDec(int a) {
        ++a;
        --a;
        Object obj2 = (Object)a;
    }

    @SuppressWarnings("abc")
    public void annotationSpace() {
    }

    public void negativeCases(int a) {
        a = -
                a;
        a = +
                a;
        Integer.
                parseInt("1");
        int
                [] array2;
        int[] arr = new int[2];
        arr
                [0] = 1;
        int[] a2 = {
                1, 2 };
        Object o2 = (Object)
                a;
        synchronized(this) {}
        java.lang.
                String s2;
    }

    @
            SuppressWarnings("abc")
    public void annotationNewLine() {
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_USE, java.lang.annotation.ElementType.LOCAL_VARIABLE})
    @interface MyAnnotation {
        int value();
    }

    public void nestedViolations() {
        final int x1 = 1;
        @MyAnnotation(-x1)
        int x_tmp;
        int[] arr = new int[2];
        arr[-x1] = 1;
        int x_val = new java.awt.Point(-x1, 0).x;
        System.out.println(-x1);
        int[] arr2 = new int[] {-x1};
        synchronized(java.lang.Integer.valueOf(-x1)) {}
        Object o3 = (Object)(-x1);
        int x2 = ~(-x1);
        int @MyAnnotation(-x1) [] annotatedArray;
    }
    public void methodReference() {
        java.util.function.Supplier<String> s = String::new;
        java.util.function.IntFunction<int[]> s4 = int[]::new;
        java.util.function.Supplier<String> s3 = String::
                new;
    }
}
