package com.edx.shell.android.facebookrecipes.recipeMain.events;

import com.edx.shell.android.facebookrecipes.entities.Recipe;

public class RecipeMainEvent {

    // Constantes
    public static final int NEXT_EVENT = 0;
    public static final int SAVE_EVENT = 1;

    // Variables
    private int type;
    private String error;
    private Recipe recipe;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
