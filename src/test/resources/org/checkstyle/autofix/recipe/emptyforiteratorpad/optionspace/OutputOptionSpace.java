/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyForIteratorPadCheck">
      <property name="option" value="space"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.emptyforiteratorpad.optionspace;

public class OutputOptionSpace {
    public void test() {
        for (int i = 0; i < 10; ) {
        }
        for (int i = 0; i < 10; ) {
        }
    }
}
