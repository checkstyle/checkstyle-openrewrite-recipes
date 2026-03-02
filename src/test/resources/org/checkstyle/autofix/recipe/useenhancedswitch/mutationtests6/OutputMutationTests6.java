/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests6;

public class OutputMutationTests6 {

    enum Season { SPRING, SUMMER, FALL, WINTER; int id = 0; }

    void assignmentWrongVar(int x) {
        int val = 0;
        int other = 0;
        switch (x) {
            case 1 -> other = 99;
            default -> other = 0;
        }
        System.out.println(val);
    }

    void assignmentWithAllThrows(int x) {
        int val = switch (x) {
            case 1 -> 10;
            default -> throw new RuntimeException("err");
        };
        System.out.println(val);
    }

    void blockAssignment(int x) {
        int val = switch (x) {
            case 1 -> {
                System.out.println("processing");
                System.out.println("more");
                yield 42;
            }
            case 2 -> 99;
            default -> throw new IllegalStateException("bad");
        };
        System.out.println(val);
    }

    void partialEnum(Season s) {
        switch (s) {
            case SPRING -> System.out.println("spring");
            case SUMMER -> System.out.println("summer");
            default -> System.out.println("other");
        }
    }

    int exhaustiveEnumReturn(Season s) {
        return switch (s) {
            case SPRING -> 1;
            case SUMMER -> 2;
            case FALL -> 3;
            case WINTER -> 4;
        };
    }

    int singleReturnExpr(int x) {
        return switch (x) {
            case 1 -> 10;
            case 2 -> 20;
            default -> 0;
        };
    }

    int singleThrowExpr(int x) {
        return switch (x) {
            case 1 -> 10;
            default -> throw new IllegalArgumentException("nope");
        };
    }

    void multipleVarDecls(int x) {
        int a;
        int b;
        switch (x) {
            case 1 -> a = 1;
            default -> a = 0;
        }
        switch (x) {
            case 1 -> b = 1;
            default -> b = 0;
        }
        System.out.println(a + b);
    }

    int switchExprTraditional(int x) {
        return switch (x) {
            case 1 -> 10;
            case 2 -> 20;
            default -> 0;
        };
    }

    void noAssignmentConversion(int x) {
        String s = "initial";
        switch (x) {
            case 1 -> s = "one";
            default -> s = "default";
        }
        System.out.println(s);
    }

    void enumAssignment(Season s) {
        int val = switch (s) {
            case SPRING -> 1;
            case SUMMER -> 2;
            case FALL -> 3;
            case WINTER -> 4;
            default -> 0;
        };
        System.out.println(val);
    }

    void singleBlockCase(int x) {
        switch (x) {
            case 1 -> System.out.println("block");
            default -> System.out.println("default");
        }
    }

    void multipleVarInOneDecl(int x) {
        int a, b;
        switch (x) {
            case 1 -> a = 1;
            default -> a = 0;
        }
        System.out.println(a);
    }

    void exhaustiveNoDefaultAssignment(Season s) {
        int val = 0;
        switch (s) {
            case SPRING -> val = 1;
            case SUMMER -> val = 2;
            case FALL -> val = 3;
            case WINTER -> val = 4;
        }
        System.out.println(val);
    }

    void assignmentWithBlock(int x) {
        int val = switch (x) {
            case 1 -> {
                System.out.println("block");
                yield 1;
            }
            default -> 0;
        };
        System.out.println(val);
    }

    void assignmentWithSingleStatementBlock(int x) {
        int val = switch (x) {
            case 1 -> 1;
            default -> 0;
        };
        System.out.println(val);
    }

    enum EmptyEnum {}

    void emptyEnumExhaustive(EmptyEnum e) {
        switch (e) {
            default -> System.out.println("default");
        }
    }

    void mixedLabelsAssignment(Season s) {
        int val = switch (s) {
            case SPRING, SUMMER -> 1;
            default -> 0;
        };
        System.out.println(val);
    }
}
