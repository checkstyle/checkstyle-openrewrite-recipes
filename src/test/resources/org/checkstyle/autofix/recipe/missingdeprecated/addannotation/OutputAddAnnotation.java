/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.MissingDeprecatedCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.missingdeprecated.addannotation;

public class OutputAddAnnotation {
    /**
     * @deprecated Use something else.
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public void doSomething() {}

    /**
     * @deprecated
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public class InnerClass {
        /**
         * @deprecated
         */
        @SuppressWarnings("deprecation")
        @Deprecated
        public int field;
    }
}
