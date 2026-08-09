/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceAfterCheck">
      <property name="tokens" value="ARRAY_INIT, AT, INC, DEC, UNARY_MINUS, UNARY_PLUS, BNOT, LNOT, DOT, ARRAY_DECLARATOR, INDEX_OP, TYPECAST, METHOD_REF"/>
    </module>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.nowhitespaceafter.alltokens;

class InputAllTokens {
    public void typeCast(int a) {
        Object o2 = ( Object ) a; // violation 'is followed by whitespace.'
    }

    public void sync() {
        synchronized (this) {}
    }
}
