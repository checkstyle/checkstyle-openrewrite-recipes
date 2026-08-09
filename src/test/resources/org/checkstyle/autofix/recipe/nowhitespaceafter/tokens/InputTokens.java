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

class InputTokens {
    public void dotOperator(String s) {
        Integer.parseInt(s);
        Integer . parseInt(s); // violation ''.' is followed by whitespace.'
        hashCode();
    }

    public void arrayDec() {
        int[] arr = new int[2];
        int [] array; // violation ''int' is followed by whitespace.'
        arr [ 0 ] = 1; // violation ''arr' is followed by whitespace.'
        int[] a = { 1, 2 }; // violation 'is followed by whitespace.'
        int[] emptyArray = { }; // violation 'is followed by whitespace.'
    }

    public void fieldAccess() {
        System . out.println(); // violation ''.' is followed by whitespace.'
    }

    public void bitwiseNot(int a) {
        a = ~ a; // violation ''~' is followed by whitespace.'
        a = + a; // violation 'is followed by whitespace.'
        a = - a; // violation ''-' is followed by whitespace.'
        boolean b = ! (a > 0); // violation ''!' is followed by whitespace.'
    }

    public void incDec(int a) {
        ++ a; // violation 'is followed by whitespace.'
        -- a; // violation ''--' is followed by whitespace.'
        Object obj2 = (Object) a; // violation 'is followed by whitespace.'
    }

    @ SuppressWarnings("abc") // violation ''@' is followed by whitespace.'
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
        synchronized (this) {} // violation 'is followed by whitespace.'
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
        @MyAnnotation(- x1) // violation ''-' is followed by whitespace.'
        int x_tmp;
        int[] arr = new int[2];
        arr[- x1] = 1; // violation ''-' is followed by whitespace.'
        int x_val = new java.awt.Point(- x1, 0).x; // violation ''-' is followed by whitespace.'
        System.out.println(- x1); // violation ''-' is followed by whitespace.'
        int[] arr2 = new int[] {- x1}; // violation ''-' is followed by whitespace.'
        synchronized(java.lang.Integer.valueOf(- x1)) {} // violation ''-' is followed by whitespace.'
        Object o3 = (Object)(- x1); // violation ''-' is followed by whitespace.'
        int x2 = ~(- x1); // violation ''-' is followed by whitespace.'
        int @MyAnnotation(- x1) [] annotatedArray; // violation ''-' is followed by whitespace.'
    }
    public void methodReference() {
        java.util.function.Supplier<String> s = String:: new; // violation ''::' is followed by whitespace.'
        java.util.function.IntFunction<int[]> s4 = int []::new; // violation ''int' is followed by whitespace.'
        java.util.function.Supplier<String> s3 = String::
                new;
    }
}
