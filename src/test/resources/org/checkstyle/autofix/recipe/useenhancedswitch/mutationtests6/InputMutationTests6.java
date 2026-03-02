/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests6;

public class InputMutationTests6 {

    enum Season { SPRING, SUMMER, FALL, WINTER; int id = 0; }

    void assignmentWrongVar(int x) {
        int val = 0;
        int other = 0;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                other = 99;
                break;
            default:
                other = 0;
                break;
        }
        System.out.println(val);
    }

    void assignmentWithAllThrows(int x) {
        int val;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                val = 10;
                break;
            default:
                throw new RuntimeException("err");
        }
        System.out.println(val);
    }

    void blockAssignment(int x) {
        int val;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                System.out.println("processing");
                System.out.println("more");
                val = 42;
                break;
            case 2:
                val = 99;
                break;
            default:
                throw new IllegalStateException("bad");
        }
        System.out.println(val);
    }

    void partialEnum(Season s) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (s) {
            case SPRING:
                System.out.println("spring");
                break;
            case SUMMER:
                System.out.println("summer");
                break;
            default:
                System.out.println("other");
                break;
        }
    }

    int exhaustiveEnumReturn(Season s) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (s) {
            case SPRING: return 1;
            case SUMMER: return 2;
            case FALL: return 3;
            case WINTER: return 4;
            default: return 0;
        }
    }

    int singleReturnExpr(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1: return 10;
            case 2: return 20;
            default: return 0;
        }
    }

    int singleThrowExpr(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1: return 10;
            default: throw new IllegalArgumentException("nope");
        }
    }

    void multipleVarDecls(int x) {
        int a;
        int b;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                a = 1;
                break;
            default:
                a = 0;
        }
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                b = 1;
                break;
            default:
                b = 0;
        }
        System.out.println(a + b);
    }

    int switchExprTraditional(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        return switch (x) {
            case 1: yield 10;
            case 2: yield 20;
            default: yield 0;
        };
    }

    void noAssignmentConversion(int x) {
        String s = "initial";
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                s = "one";
                break;
            default:
                s = "default";
        }
        System.out.println(s);
    }

    void enumAssignment(Season s) {
        int val;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (s) {
            case SPRING:
                val = 1;
                break;
            case SUMMER:
                val = 2;
                break;
            case FALL:
                val = 3;
                break;
            case WINTER:
                val = 4;
                break;
            default:
                val = 0;
                break;
        }
        System.out.println(val);
    }

    void singleBlockCase(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1: {
                System.out.println("block");
                break;
            }
            default:
                System.out.println("default");
        }
    }

    void multipleVarInOneDecl(int x) {
        int a, b;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                a = 1;
                break;
            default:
                a = 0;
        }
        System.out.println(a);
    }

    void exhaustiveNoDefaultAssignment(Season s) {
        int val = 0;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (s) {
            case SPRING: val = 1; break;
            case SUMMER: val = 2; break;
            case FALL: val = 3; break;
            case WINTER: val = 4; break;
        }
        System.out.println(val);
    }

    void assignmentWithBlock(int x) {
        int val;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1: {
                System.out.println("block");
                val = 1;
                break;
            }
            default:
                val = 0;
        }
        System.out.println(val);
    }

    void assignmentWithSingleStatementBlock(int x) {
        int val;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1: {
                val = 1;
                break;
            }
            default:
                val = 0;
        }
        System.out.println(val);
    }

    enum EmptyEnum {}

    void emptyEnumExhaustive(EmptyEnum e) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (e) {
            default:
                System.out.println("default");
        }
    }

    void mixedLabelsAssignment(Season s) {
        int val;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (s) {
            case SPRING:
            case SUMMER:
                val = 1;
                break;
            default:
                val = 0;
        }
        System.out.println(val);
    }
}
