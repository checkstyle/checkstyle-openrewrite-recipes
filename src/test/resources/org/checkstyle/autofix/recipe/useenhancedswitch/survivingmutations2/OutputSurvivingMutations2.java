/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
    <module name="com.puppycrawl.tools.checkstyle.filters.SuppressWithNearbyCommentFilter">
      <property name="commentFormat" value="SUPPRESS"/>
      <property name="checkFormat" value=".*"/>
      <property name="influenceFormat" value="0"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.useenhancedswitch.survivingmutations2;

public class OutputSurvivingMutations2 {
  
    void m1(int x) {
                 
        switch (x) { // (SUPPRESS)
            case 1: break; // (SUPPRESS)
            default: break;
        }
    }

    void decoyM1a(int x) {
              switch (x) { // (SUPPRESS)
            case 1: break; // (SUPPRESS)
            default: break;
        }
    }

    void decoyM1b(int x) {
        switch (x) { // (SUPPRESS)
            case 1: break; // (SUPPRESS)
            default: break;
        }
    }

    int m2(int x) {
                        
               return switch (x) { // (SUPPRESS)
            case 1: yield 1; // (SUPPRESS)
            default: yield 0;
        };
    }

    int decoyM2a(int x) {
                      return switch (x) { // (SUPPRESS)
            case 1: yield 1; // (SUPPRESS)
            default: yield 0;
        };
    }

    int decoyM2b(int x) {
               return switch (x) { // (SUPPRESS)
            case 1: yield 1; // (SUPPRESS)
            default: yield 0;
        };
    }

    void m3(int x, int y) {
                 
        switch (x) { // (SUPPRESS)
            case 1: // (SUPPRESS)
                         
                switch (y) { // (SUPPRESS)
                    case 2: break;
                    default: break;
                }
                break;
            default: break;
        }
    }

    void m4(int x) {
             
    switch (x) { // (SUPPRESS)
    case 1: break; // (SUPPRESS)
    default: break;
    }

    switch (x) { // (SUPPRESS)
    case 1: break; // (SUPPRESS)
    default: break;
    }
    }

    int m5(int x, int y) {
        return switch (x) { // (SUPPRESS)
            case 1: // (SUPPRESS)
                         
                yield switch (y) { // (SUPPRESS)
                    case 2: yield 2;
                    default: yield 0;
                };
            default: yield 0;
        };
    }
}
