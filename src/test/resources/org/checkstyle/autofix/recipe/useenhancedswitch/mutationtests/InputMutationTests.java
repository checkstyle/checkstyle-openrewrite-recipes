/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests;

public class InputMutationTests {

    void doSomething(String p) {}

    void singleSwitchInBlock(int x, int y) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                {
                    // violation below, 'Switch can be replaced with enhanced switch'
                    switch (y) {
                        case 1: break;
                    }
                    break;
                }
        }
    }

    void emptyBlock(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                {
                    break;
                }
        }
    }

    void weirdlyIndented(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                {
doSomething("no indent");
                    break;
                }
        }
    }

    void multiBreaksInBlock(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                {
                    if (x > 0) break;
                    doSomething("multi");
                    break;
                }
        }
    }

    void underIndented(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                {
  doSomething("less indent 1");
  doSomething("less indent 2");
                    break;
                }
        }
    }

    void multiLinePrefix(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                /* comment */
                {
                    doSomething("a");
                    doSomething("b");
                    break;
                }
        }
    }

    void blankLines(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:

                {
                    doSomething("blankLine 1");
                    doSomething("blankLine 2");
                    break;
                }
        }
    }
}
