/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.MissingOverrideCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.missingoverride.overridecasethree;

import javax.annotation.Nullable;

public class OutputOverrideCaseThree extends TestClass {

    /**
     * {@inheritDoc}
     */
    // hello
    @Override
    void doFoo() {
        int a  = 20;
    }

    Runnable r = new Runnable() {


        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public void run() {
            Throwable t = new Throwable() {

                /**
                 * {@inheritDoc}
                 */
                @Override
                public String
                toString() {
                    return "junk";
                }
            };
        }
    };
}
