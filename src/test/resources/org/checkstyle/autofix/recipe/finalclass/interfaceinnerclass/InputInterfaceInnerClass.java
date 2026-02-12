/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finalclass.interfaceinnerclass;

interface InputInterfaceInnerClass {

    class SomeClass { // violation 'Class SomeClass should be declared as final.'
        private SomeClass() {}
    }
}
