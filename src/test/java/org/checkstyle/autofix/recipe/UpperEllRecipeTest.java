package org.checkstyle.autofix.recipe;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;

public class UpperEllRecipeTest extends AbstractRecipeTest {

    @Override
    protected Recipe getRecipe() {
        return new UpperEllRecipe();
    }

    @Test
    void hexOctalLiteralTest() throws IOException {
        testRecipe("upperell", "HexOctalLiteral");
    }

    @Test
    void complexLongLiterals() throws IOException {
        testRecipe("upperell", "ComplexLongLiterals");
    }

    @Test
    void stringAndCommentTest() throws IOException {
        testRecipe("upperell", "StringAndComments");
    }
}
