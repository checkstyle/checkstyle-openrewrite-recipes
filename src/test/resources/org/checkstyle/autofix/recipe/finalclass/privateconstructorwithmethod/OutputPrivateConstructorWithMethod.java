/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finalclass.privateconstructorwithmethod;

final class OutputPrivateConstructorWithMethod {
    private OutputPrivateConstructorWithMethod() {}
    void someMethod() {}
}
