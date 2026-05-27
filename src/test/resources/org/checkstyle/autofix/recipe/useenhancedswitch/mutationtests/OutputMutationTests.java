/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests;

public class OutputMutationTests {

    void doSomething(String p) {}

    void singleSwitchInBlock(int x, int y) {
        switch (x) {
            case 1 -> {
                    switch (y) {
                    case 1 -> {}
                }
            }
        }
    }

    void emptyBlock(int x) {
        switch (x) {
            case 1 -> {}
        }
    }

    void weirdlyIndented(int x) {
        switch (x) {
            case 1 -> doSomething("no indent");
        }
    }

    void multiBreaksInBlock(int x) {
        switch (x) {
            case 1 -> {
                if (x > 0) break;
                doSomething("multi");
            }
        }
    }

    void underIndented(int x) {
        switch (x) {
            case 1 -> {
doSomething("less indent 1");
doSomething("less indent 2");
            }
        }
    }

    void multiLinePrefix(int x) {
        switch (x) {
            case 1 -> {
                doSomething("a");
                doSomething("b");
            }
        }
    }

    void blankLines(int x) {
        switch (x) {
            case 1 -> {
                doSomething("blankLine 1");
                doSomething("blankLine 2");
            }
        }
    }
}
