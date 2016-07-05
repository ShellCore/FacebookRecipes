package com.edx.shell.android.facebookrecipes.api;

import com.edx.shell.android.facebookrecipes.BaseTest;
import com.edx.shell.android.facebookrecipes.BuildConfig;
import com.edx.shell.android.facebookrecipes.entities.Recipe;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import retrofit2.Call;
import retrofit2.Response;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class RecipeServiceTest extends BaseTest {
    private RecipeService service;

    @Override
    public void setup() throws Exception {
        super.setup();

        RecipeClient client = new RecipeClient();
        service = client.getRecipeService();
    }

    @Test
    public void doSearch_getRecipeFromBackend() throws Exception {
        String sort = "r";
        int count = 1;
        int page = 1;
        Call<RecipeSearchResponse> call = service.search(BuildConfig.FOOD_API_KEY, sort, count, page);

        Response<RecipeSearchResponse> response = call.execute();
        assertTrue(response.isSuccess());

        RecipeSearchResponse recipeSearchResponse = response.body();
        assertEquals(1, recipeSearchResponse.getCount());

        Recipe recipe = recipeSearchResponse.getFirstRecipe();
        assertNotNull(recipe);
    }
}
