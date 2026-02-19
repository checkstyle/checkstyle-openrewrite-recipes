/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.MissingOverrideCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.missingoverride.overridecasetwo;

import javax.annotation.Nullable;

public class OutputOverrideCaseTwo {
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
                public String toString() {
                    return "junk";
                }
            };
        }
    };

    void doFoo(Runnable r) {
        doFoo(new Runnable() {

        /**
         * {@inheritDoc}
         */
        @Override
        public void run() {
            Throwable t = new Throwable() {

            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                return "junk";
            }
            };
        }
        });
    }
}
