/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finalclass.privateconstructorwithmethod;

class InputPrivateConstructorWithMethod { // violation 'Class InputPrivateConstructorWithMethod should be declared as final.'
    private InputPrivateConstructorWithMethod() {}
    void someMethod() {}
}
