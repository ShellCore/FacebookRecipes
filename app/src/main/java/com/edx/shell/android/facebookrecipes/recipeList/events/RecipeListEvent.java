package com.edx.shell.android.facebookrecipes.recipeList.events;

import com.edx.shell.android.facebookrecipes.entities.Recipe;

import java.util.List;

public class RecipeListEvent {

    // Constantes
    public static final int READ_EVENT = 0;
    public static final int UPDATE_EVENT = 1;
    public static final int DELETE_EVENT = 2;

    // Variables
    private int type;
    private List<Recipe> recipes;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
