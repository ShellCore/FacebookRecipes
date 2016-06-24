package com.edx.shell.android.facebookrecipes.recipeList;

public class RecipeListInteractorImpl implements RecipeListInteractor {

    // Servicios
    private RecipeListRepository repository;

    public RecipeListInteractorImpl(RecipeListRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute() {
        repository.getSavedRecipes();
    }
}
