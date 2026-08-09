/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceAfterCheck">
      <property name="allowLineBreaks" value="false"/>
      <property name="tokens" value="ARRAY_INIT, AT, INC, DEC, UNARY_MINUS, UNARY_PLUS, BNOT, LNOT, DOT, ARRAY_DECLARATOR, INDEX_OP, TYPECAST, METHOD_REF"/>
    </module>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.nowhitespaceafter.linebreaks;

class InputLineBreaks {
    public void negativeCases(int a) {
        int
                [] array2; // violation ''int' is followed by whitespace.'
        int[] arr = new int[2];
        arr
                [0] = 1; // violation ''arr' is followed by whitespace.'
        synchronized (this) {}
    }
}
