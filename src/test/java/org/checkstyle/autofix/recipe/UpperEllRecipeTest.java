package org.checkstyle.autofix.recipe;

import org.junit.jupiter.api.Test;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import static org.checkstyle.autofix.recipe.util.TestFileUtils.readTestFile;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openrewrite.java.Assertions.java;

import java.io.IOException;

public class UpperEllRecipeTest implements RewriteTest {

    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new UpperEllRecipe());
    }

    @Test
    void test1() throws IOException {
        String beforeCode = readTestFile("upperell/before/InputUpperEllTest.java");
        String afterCode = readTestFile("upperell/after/InputUpperEllTest.java");

        rewriteRun(java(beforeCode, afterCode));

        assertTrue(true, "Test completed successfully");
    }

    @Test
    void test2() throws IOException {
        String beforeCode = readTestFile("upperell/before/InputUpperEllTest2.java");
        String afterCode = readTestFile("upperell/after/InputUpperEllTest2.java");

        rewriteRun(java(beforeCode, afterCode));

        assertTrue(true, "Test completed successfully");
    }


}
