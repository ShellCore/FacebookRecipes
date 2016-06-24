package com.edx.shell.android.facebookrecipes.recipeList;

import com.edx.shell.android.facebookrecipes.entities.Recipe;

public interface RecipeListRepository {
    void getSavedRecipes();
    void updateRecipe(Recipe recipe);
    void removeRecipe(Recipe recipe);
}
