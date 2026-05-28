/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
    <module name="com.puppycrawl.tools.checkstyle.filters.SuppressWithNearbyCommentFilter">
      <property name="commentFormat" value="suppressed violation"/>
      <property name="checkFormat" value=".*"/>
      <property name="influenceFormat" value="0"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.useenhancedswitch.survivingmutations3;

public class OutputSurvivingMutations3 {
    
    void m6(int x) {
          switch (x) {
            case 1 -> {}
            default -> {}
          }
    }

    void m7(int x) {
        switch (x) {
case 1 -> {
System.out.println("0");
System.out.println("0b");
}
  default -> {
  System.out.println("2");
  System.out.println("2b");
  }
        }
    }

    void m8(int x, int y) {
        switch (x) { case 1 -> { switch (y) { case 1 -> System.out.println("1"); case 2 -> {} default -> {} }}
            default -> {}
        }
    }

    int m9(int x, int y) {
        return switch (x) { case 1 -> switch (y) { case 1 -> { System.out.println("1"); yield 1;} case 2 -> 2; default -> 0; };
            default -> 0;
        };
    }

    void m10(int x) {
        switch (x) { case 1 -> {      System.out.println("1"); System.out.println("1b");}
            default -> {}
        }
    }

    void emptySwitch(int x) {
        switch (x) {
        }
    }

}
