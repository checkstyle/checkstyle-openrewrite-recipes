/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finalclass.privateconstructor;

public class InputPrivateConstructor { // violation 'Class InputPrivateConstructor should be declared as final.'
    private InputPrivateConstructor() {
    }
}
