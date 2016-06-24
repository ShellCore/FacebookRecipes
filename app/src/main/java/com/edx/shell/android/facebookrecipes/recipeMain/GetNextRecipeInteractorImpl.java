package com.edx.shell.android.facebookrecipes.recipeMain;

import java.util.Random;

public class GetNextRecipeInteractorImpl implements GetNextRecipeInteractor {

    // Servicios
    RecipeMainRepository repository;

    public GetNextRecipeInteractorImpl(RecipeMainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute() {
        int recipeRange = new Random()
                .nextInt(RecipeMainRepository.RECIPE_RANGE);
        repository.setRecipePage(recipeRange);
        repository.getNextRecipe();
    }
}
