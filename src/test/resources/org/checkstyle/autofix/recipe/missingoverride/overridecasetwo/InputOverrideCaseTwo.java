/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.MissingOverrideCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.missingoverride.overridecasetwo;

import javax.annotation.Nullable;

public class InputOverrideCaseTwo {
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
                public String toString() {// violation 'include.*@java.lang.Override.*when.*'@inheritDoc''
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
        public void run() {// violation 'include.*@java.lang.Override.*when.*'@inheritDoc''
            Throwable t = new Throwable() {

            /**
             * {@inheritDoc}
             */
            public String toString() {// violation 'include.*@java.lang.Override.*when.*'@inheritDoc''
                return "junk";
            }
            };
        }
        });
    }
}
