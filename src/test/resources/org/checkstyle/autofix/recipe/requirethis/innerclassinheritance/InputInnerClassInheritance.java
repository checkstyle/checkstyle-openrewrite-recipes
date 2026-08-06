/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck">
      <property name="validateOnlyOverlapping" value="false"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.requirethis.innerclassinheritance;

public class InputInnerClassInheritance {
    public void someMethod() {}

    private static class MyInner extends InputInnerClassInheritance {
        public void test() {
            someMethod(); // violation 'Method call to 'someMethod' needs "InputInnerClassInheritance.this.".'
        }

        Runnable r = new Runnable() {
            public void run() {
                someMethod(); // violation 'Method call to 'someMethod' needs "InputInnerClassInheritance.this.".'
            }
        };
    }
}
