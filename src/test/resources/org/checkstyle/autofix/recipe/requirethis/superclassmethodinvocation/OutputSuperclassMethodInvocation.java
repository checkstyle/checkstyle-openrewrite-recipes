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

public class OutputSuperclassMethodInvocation {
    public void fetch(String key, String value) {}

    class Subclass extends InputSuperclassMethodInvocation {
        void doFetch() {
            this.fetch("key", "value");
        }
    }
}
