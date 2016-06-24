package com.edx.shell.android.facebookrecipes.recipeMain;

import com.edx.shell.android.facebookrecipes.BuildConfig;
import com.edx.shell.android.facebookrecipes.api.RecipeSearchResponse;
import com.edx.shell.android.facebookrecipes.api.RecipeService;
import com.edx.shell.android.facebookrecipes.entities.Recipe;
import com.edx.shell.android.facebookrecipes.libs.base.EventBus;
import com.edx.shell.android.facebookrecipes.recipeMain.events.RecipeMainEvent;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeMainRepositoryImpl implements RecipeMainRepository {
    private int recipePage;
    private EventBus eventBus;
    private RecipeService service;

    public RecipeMainRepositoryImpl( EventBus eventBus, RecipeService service) {
        this.eventBus = eventBus;
        this.service = service;
    }


    @Override
    public void getNextRecipe() {
        Call<RecipeSearchResponse> call = service.search(BuildConfig.FOOD_API_KEY,
                RECENT_SORT,
                COUNT,
                recipePage);
        Callback<RecipeSearchResponse> callback = new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                if (response.isSuccessful()) {
                    RecipeSearchResponse recipeSearchResponse = response.body();
                    if (recipeSearchResponse.getCount() == 0) { // No devolvió nada
                        setRecipePage(new Random().nextInt(RECIPE_RANGE));
                        getNextRecipe();
                    } else {
                        Recipe recipe = recipeSearchResponse.getFirstRecipe();
                        if (recipe != null) {
                            post(recipe);
                        } else {
                            post(response.message());
                        }
                    }
                } else {
                    post(response.message()); // Es error
                }
            }

            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
                post(t.getLocalizedMessage());
            }
        };

        call.enqueue(callback);
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        recipe.save();
        post();
    }

    @Override
    public void setRecipePage(int recipePage) {
        this.recipePage = recipePage;
    }

    private void post(Recipe recipe) {
        post(null, RecipeMainEvent.NEXT_EVENT, recipe);
    }

    private void post(String error) {
        post(error, RecipeMainEvent.NEXT_EVENT, null);
    }

    private void post() {
        post(null, RecipeMainEvent.SAVE_EVENT, null);
    }

    private void post(String error, int type, Recipe recipe) {
        RecipeMainEvent event = new RecipeMainEvent();
        event.setError(error);
        event.setType(type);
        event.setRecipe(recipe);
        eventBus.post(event);
    }
}
