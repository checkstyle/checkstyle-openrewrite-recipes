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

package org.checkstyle.autofix.recipe.useenhancedswitch.survivingmutations;

public class InputSurvivingMutations {
  
    void m1(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1: break;
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
               // violation below, 'Switch can be replaced with enhanced switch'
               return switch (x) {
            case 1: yield 1;
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
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                // violation below, 'Switch can be replaced with enhanced switch'
                switch (y) {
                    case 2: break;
                    default: break;
                }
                break;
            default: break;
        }
    }

    void m4(int x) {
    // violation below, 'Switch can be replaced with enhanced switch'
    switch (x) {
    case 1: break;
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
                // violation below, 'Switch can be replaced with enhanced switch'
                yield switch (y) {
                    case 2: yield 2;
                    default: yield 0;
                };
            default: yield 0;
        };
    }
    
    void m6(int x) {
          // violation below, 'Switch can be replaced with enhanced switch'
          switch (x) {
            case 1: break;
            default: break;
          }
    }

    void m7(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
case 1:
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
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) { case 1: switch (y) { case 1: System.out.println("1"); case 2: break; default: break; } break;
            default: break;
        }
    }

    int m9(int x, int y) {
        // violation below, 'Switch can be replaced with enhanced switch'
        return switch (x) { case 1 -> switch (y) { case 1: System.out.println("1"); yield 1; case 2: yield 2; default: yield 0; };
            default -> 0;
        };
    }

    void m10(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) { case 1:      System.out.println("1"); System.out.println("1b"); break;
            default: break;
        }
    }

    void emptySwitch(int x) {
        switch (x) {
        }
    }

}
