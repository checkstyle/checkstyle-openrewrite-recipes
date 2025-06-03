package org.checkstyle.autofix;

import org.openrewrite.Recipe;

import java.util.Collections;
import java.util.List;

import org.checkstyle.autofix.recipe.UpperEllRecipe;

/**
 * Main recipe that automatically fixes all supported Checkstyle violations
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