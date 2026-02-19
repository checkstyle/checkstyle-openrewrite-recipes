/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.MissingOverrideCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.missingoverride.overridecasethree;

import javax.annotation.Nullable;

public class InputOverrideCaseThree extends TestClass {

    /**
     * {@inheritDoc}
     */
    // hello
    void doFoo() {// violation 'include.*@java.lang.Override.*when.*'@inheritDoc''
        int a  = 20;
    }

    Runnable r = new Runnable() {


        /**
         * {@inheritDoc}
         */
        @Nullable// violation 'include.*@java.lang.Override.*when.*'@inheritDoc''
        public void run() {
            Throwable t = new Throwable() {

                /**
                 * {@inheritDoc}
                 */
                public String// violation 'include.*@java.lang.Override.*when.*'@inheritDoc''
                toString() {
                    return "junk";
                }
            };
        }
    };
}
