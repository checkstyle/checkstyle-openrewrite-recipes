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

package org.checkstyle.autofix.recipe.useenhancedswitch.survivingmutations2;

public class OutputSurvivingMutations2 {
  
    void m1(int x) {
                 
        switch (x) { // (suppressed violation)
            case 1: break; // (suppressed violation)
            default: break;
        }
    }

    void decoyM1a(int x) {
              switch (x) { // (suppressed violation)
            case 1: break; // (suppressed violation)
            default: break;
        }
    }

    void decoyM1b(int x) {
        switch (x) { // (suppressed violation)
            case 1: break; // (suppressed violation)
            default: break;
        }
    }

    int m2(int x) {
                        
               return switch (x) { // (suppressed violation)
            case 1: yield 1; // (suppressed violation)
            default: yield 0;
        };
    }

    int decoyM2a(int x) {
                      return switch (x) { // (suppressed violation)
            case 1: yield 1; // (suppressed violation)
            default: yield 0;
        };
    }

    int decoyM2b(int x) {
               return switch (x) { // (suppressed violation)
            case 1: yield 1; // (suppressed violation)
            default: yield 0;
        };
    }

    void m3(int x, int y) {
                 
        switch (x) { // (suppressed violation)
            case 1: // (suppressed violation)
                         
                switch (y) { // (suppressed violation)
                    case 2: break;
                    default: break;
                }
                break;
            default: break;
        }
    }

    void m4(int x) {
             
    switch (x) { // (suppressed violation)
    case 1: break; // (suppressed violation)
    default: break;
    }

    switch (x) { // (suppressed violation)
    case 1: break; // (suppressed violation)
    default: break;
    }
    }

    int m5(int x, int y) {
        return switch (x) { // (suppressed violation)
            case 1: // (suppressed violation)
                         
                yield switch (y) { // (suppressed violation)
                    case 2: yield 2;
                    default: yield 0;
                };
            default: yield 0;
        };
    }
    
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
        return switch (x) {
            case 1, 2 -> 1;
            default -> 0;
        };
    }

    void fallThroughAssignment(int x) {
        int y = switch (x) {
            case 1, 2 -> 1;
            default -> 0;
        };
    }
}
