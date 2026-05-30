/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.MissingDeprecatedCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.missingdeprecated.addannotation;

public class InputAddAnnotation {
    /**
     * @deprecated Use something else.
     */
    @SuppressWarnings("deprecation") // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'
    public void doSomething() {}

    /**
     * @deprecated
     */
    @SuppressWarnings("deprecation") // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'
    public class InnerClass {
        /**
         * @deprecated
         */
        @SuppressWarnings("deprecation") // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'
        public int field;
    }
}
