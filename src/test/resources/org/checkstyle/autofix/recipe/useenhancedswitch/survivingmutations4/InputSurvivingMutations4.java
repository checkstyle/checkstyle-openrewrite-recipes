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

package org.checkstyle.autofix.recipe.useenhancedswitch.survivingmutations4;

public class InputSurvivingMutations4 {
    
    void m6(int x) {
                   
          switch (x) { // (suppressed violation)
            case 1: break; // (suppressed violation)
            default: break;
          }
    }

    void m7(int x) {
                 
        switch (x) { // (suppressed violation)
case 1: // (suppressed violation)
System.out.println("0");
System.out.println("0b");
break;
  default:
  System.out.println("2");
  System.out.println("2b");
  break;
        }
    }

    void m8(int x, int y) {
                 
        switch (x) { case 1: switch (y) { case 1: System.out.println("1"); case 2: break; default: break; } break; // (suppressed violation)
            default: break;
        }
    }

    int m9(int x, int y) {
                 
        return switch (x) { case 1: yield switch (y) { case 1: System.out.println("1"); case 2: yield 2; default: yield 0; }; // (suppressed violation)
            default: yield 0;
        };
    }

    void m10(int x) {
                 
        switch (x) { case 1:      System.out.println("1"); System.out.println("1b"); break; // (suppressed violation)
            default: break;
        }
    }

    void unconvertibleAssignment(int x) {
        int y; // (suppressed violation)
        switch (x) { // (suppressed violation)
            case 1:
                y = 1;
                break;
            case 2:
                System.out.println("not an assignment");
                break;
            default:
                y = 0;
                break;
        }
    }

    void returnVoid(int x) {
        switch (x) { // (suppressed violation)
            case 1:
                System.out.println("1");
                return;
            default:
                return;
        }
    }

    int fallThrough(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
            case 2:
                return 1;
            default:
                return 0;
        }
    }

    void fallThroughAssignment(int x) {
        int y;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
            case 2:
                y = 1;
                break;
            default:
                y = 0;
                break;
        }
    }
}
