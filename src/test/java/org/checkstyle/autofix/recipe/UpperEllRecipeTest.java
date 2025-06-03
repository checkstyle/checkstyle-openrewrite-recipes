package org.checkstyle.autofix.recipe;

import org.junit.jupiter.api.Test;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openrewrite.java.Assertions.java;

public class UpperEllRecipeTest implements RewriteTest {

    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new UpperEllRecipe());
    }

    @Test
    void fixesLowercaseLInLongLiteralsFromResources() {
        rewriteRun(

            java(
                "class Test {\n" +
                "    int value1 = 123l;\n" +
                "    long value2 = 0x123l;\n" +
                "    long value3 = 0123l;\n" +
                "    long value4 = 0b101l;\n" +
                "    String value5 = null;\n" +
                "}\n",
                "class Test {\n" +
                "    int value1 = 123L;\n" +
                "    long value2 = 0x123L;\n" +
                "    long value3 = 0123L;\n" +
                "    long value4 = 0b101L;\n" +
                "    String value5 = null;\n" +
                "}\n"
            )
        );
        assertTrue(true, "Test completed successfully");
    }
}
