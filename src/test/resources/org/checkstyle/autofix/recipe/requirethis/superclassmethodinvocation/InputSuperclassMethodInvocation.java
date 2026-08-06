/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck">
      <property name="validateOnlyOverlapping" value="false"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.requirethis.superclassmethodinvocation;

public class InputSuperclassMethodInvocation {
    public void fetch(String key, String value) {}

    class Subclass extends InputSuperclassMethodInvocation {
        void doFetch() {
            fetch("key", "value"); // violation 'Method call to 'fetch' needs "InputSuperclassMethodInvocation.this.".'
        }
    }
}
