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

public class InputSurvivingMutations2 {
  
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
}
