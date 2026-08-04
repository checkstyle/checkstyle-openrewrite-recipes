/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyForIteratorPadCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.emptyforiteratorpad.defaultnospace;

public class InputDefaultNoSpace {
    public void test() {
        for (int i = 0; i < 10; ) { // violation '';' is followed by whitespace.'
        }
        for (int i = 0; i < 10;) {
        }
    }
}
