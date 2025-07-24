package org.checkstyle.autofix;

import org.junit.jupiter.api.Test;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

class RemoveViolationCommentsTest implements RewriteTest {

    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new RemoveViolationComments());
    }

    @Test
    void removesViolationComments() {
        rewriteRun(
                java(
                        """
                        package org.checkstyle.autofix;
                        public class Test {
                        int a;  //hloo
                        //violation
                        int b;
                        int c;    //violation
                        }
                        """,
                        """
                        package org.checkstyle.autofix;
                        public class Test {
                        int a;  //hloo
                        
                        int b;
                        int c;
                        }
                        """
                )
        );
    }
}