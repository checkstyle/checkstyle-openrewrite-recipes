/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.sideeffect;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class OutputSideEffect {

    public void method() {
        final AtomicInteger reportedSize = new AtomicInteger(200);

        new BoundedCommandCache(reportedSize::set);

        if (reportedSize.get() != 0) {
            throw new RuntimeException("Side effect failed! Value is " + reportedSize.get());
        }
    }

    static class BoundedCommandCache {
        BoundedCommandCache(Consumer<Integer> callback) {
            callback.accept(0);
        }
    }

    public void test() {
        sideEffect();
    }

    public int sideEffect() {
        System.out.println("Side effect executed!");
        return 42;
    }

}
