package com.edx.shell.android.facebookrecipes.recipeMain.ui;

import com.edx.shell.android.facebookrecipes.entities.Recipe;

public interface RecipeMainView {
    void showElements();
    void hideElements();
    void showProgressBar();
    void hideProgressBar();
    void saveAnimation();
    void dismissAnimation();

    void onRecipeSaved();

    void setRecipe(Recipe recipe);
    void onGetRecipeError(String error);

}
