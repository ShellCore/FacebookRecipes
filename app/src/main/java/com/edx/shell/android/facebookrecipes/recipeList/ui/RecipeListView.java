package com.edx.shell.android.facebookrecipes.recipeList.ui;

import com.edx.shell.android.facebookrecipes.entities.Recipe;

import java.util.List;

public interface RecipeListView {
    void setRecipes(List<Recipe> data);
    void recipeUpdated();
    void recipeDeleted(Recipe recipe);
}
