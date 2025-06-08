package org.checkstyle.autofix;

import java.util.Collections;
import java.util.List;

import org.checkstyle.autofix.recipe.UpperEllRecipe;
import org.openrewrite.Recipe;

/**
 * Main recipe that automatically fixes all supported Checkstyle violations.
 */
public class CheckstyleAutoFix extends Recipe {

    @Override
    public String getDisplayName() {
        return "Checkstyle autoFix";
    }

    @Override
    public String getDescription() {
        return "Automatically fixes Checkstyle violations.";
    }

    @Override
    public List<Recipe> getRecipeList() {
        return Collections.singletonList(

                new UpperEllRecipe()
        );
    }
}
