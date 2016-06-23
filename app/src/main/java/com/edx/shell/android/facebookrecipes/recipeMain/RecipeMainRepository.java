package com.edx.shell.android.facebookrecipes.recipeMain;

import com.edx.shell.android.facebookrecipes.entities.Recipe;

public interface RecipeMainRepository {
    public static final int COUNT = 1;
    public static final String RECENT_SORT = "r";
    public static final int RECIPE_RANGE = 100000;

    void nextRecipe();
    void saveRecipe(Recipe recipe);
    void setRecipePage(int recipePage);
}
