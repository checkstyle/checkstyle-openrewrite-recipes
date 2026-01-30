package org.checkstyle.autofix.recipe;

import org.junit.jupiter.api.Test;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

/**
 * Test cases for EmptyStatement recipe.
 */
class EmptyStatementTest implements RewriteTest {

    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new EmptyStatement());
    }

    @Test
    void removeDoubleSermicolon() {
        rewriteRun(
            java(
                """
                class Test {
                    void method() {
                        int x = 5;;
                    }
                }
                """,
                """
                class Test {
                    void method() {
                        int x = 5;
                    }
                }
                """
            )
        );
    }

    @Test
    void removeEmptyStatementInBlock() {
        rewriteRun(
            java(
                """
                class Test {
                    void method() {
                        int x = 1;
                        ;
                        int y = 2;
                    }
                }
                """,
                """
                class Test {
                    void method() {
                        int x = 1;
                        int y = 2;
                    }
                }
                """
            )
        );
    }

    @Test
    void multipleEmptyStatements() {
        rewriteRun(
            java(
                """
                class Test {
                    void method() {
                        int x = 5;;;
                    }
                }
                """,
                """
                class Test {
                    void method() {
                        int x = 5;
                    }
                }
                """
            )
        );
    }

    @Test
    void noChangeWhenNoViolation() {
        rewriteRun(
            java(
                """
                class Test {
                    void method() {
                        int x = 5;
                        int y = 10;
                    }
                }
                """
            )
        );
    }
}
