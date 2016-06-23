package com.edx.shell.android.facebookrecipes.recipeMain;

import com.edx.shell.android.facebookrecipes.entities.Recipe;
import com.edx.shell.android.facebookrecipes.recipeMain.events.RecipeMainEvent;
import com.edx.shell.android.facebookrecipes.recipeMain.ui.RecipeMainView;

public interface RecipeMainPresenter {
    void onCreate();
    void onDestroy();

    void dismissRecipe();
    void getNextRecipe();
    void saveRecipe(Recipe recipe);
    void onEventMainThread(RecipeMainEvent event);

    void imageError(String error);
    void imageReady();

    RecipeMainView getView();
}
