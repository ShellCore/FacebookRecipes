package com.edx.shell.android.facebookrecipes.recipeMain;

import com.edx.shell.android.facebookrecipes.BaseTest;
import com.edx.shell.android.facebookrecipes.BuildConfig;
import com.edx.shell.android.facebookrecipes.api.RecipeSearchResponse;
import com.edx.shell.android.facebookrecipes.api.RecipeService;
import com.edx.shell.android.facebookrecipes.entities.Recipe;
import com.edx.shell.android.facebookrecipes.libs.base.EventBus;
import com.edx.shell.android.facebookrecipes.recipeMain.events.RecipeMainEvent;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecipeMainRepositoryImplTest extends BaseTest {

    @Mock
    private EventBus eventBus;
    @Mock
    private RecipeService service;

    @Mock
    private Recipe recipe;
    private ArgumentCaptor<RecipeMainEvent> recipeMainEventArgumentCaptor;


    private RecipeMainRepository repository;

    @Override
    public void setup() throws Exception {
        super.setup();
        repository = new RecipeMainRepositoryImpl(eventBus, service);
        recipeMainEventArgumentCaptor = ArgumentCaptor.forClass(RecipeMainEvent.class);
    }

    @Test
    public void testSaveRecipeCalled_eventPosted() throws Exception {
        when(recipe.exists()).thenReturn(true);

        repository.saveRecipe(recipe);

        verify(eventBus).post(recipeMainEventArgumentCaptor.capture());
        RecipeMainEvent event = recipeMainEventArgumentCaptor.getValue();

        assertEquals(RecipeMainEvent.SAVE_EVENT, event.getType());
        assertNull(event.getRecipe());
        assertNull(event.getError());
    }

    @Test
    public void testGetNextRecipeCalled_apiServiceSuccessCall_EventPosted() throws Exception {
        int recipePage = new Random().nextInt(RecipeMainRepository.RECIPE_RANGE);
        when(service.search(BuildConfig.FOOD_API_KEY,
                            RecipeMainRepository.RECENT_SORT,
                            RecipeMainRepository.COUNT,
                            recipePage))
                .thenReturn(buildCall(true, null));
        repository.setRecipePage(recipePage);
        repository.getNextRecipe();

        verify(service).search(BuildConfig.FOOD_API_KEY,
                               RecipeMainRepository.RECENT_SORT,
                               RecipeMainRepository.COUNT,
                               recipePage);
        verify(eventBus).post(recipeMainEventArgumentCaptor.capture());
        RecipeMainEvent event = recipeMainEventArgumentCaptor.getValue();

        assertEquals(RecipeMainEvent.NEXT_EVENT, event.getType());
        assertNull(event.getError());
        assertNotNull(event.getRecipe());
        assertEquals(recipe, event.getRecipe());
    }

    @Test
    public void testGetNextRecipeCalled_apiServiceFailedCall_EventPosted() throws Exception {
        String errorMessage = "error";
        int recipePage = new Random().nextInt(RecipeMainRepository.RECIPE_RANGE);
        when(service.search(BuildConfig.FOOD_API_KEY,
                RecipeMainRepository.RECENT_SORT,
                RecipeMainRepository.COUNT,
                recipePage))
                .thenReturn(buildCall(false, errorMessage));
        repository.setRecipePage(recipePage);
        repository.getNextRecipe();

        verify(service).search(BuildConfig.FOOD_API_KEY,
                RecipeMainRepository.RECENT_SORT,
                RecipeMainRepository.COUNT,
                recipePage);
        verify(eventBus).post(recipeMainEventArgumentCaptor.capture());
        RecipeMainEvent event = recipeMainEventArgumentCaptor.getValue();

        assertEquals(RecipeMainEvent.NEXT_EVENT, event.getType());
        assertNotNull(event.getError());
        assertNull(event.getRecipe());
        assertEquals(errorMessage, event.getError());
    }

    private Call<RecipeSearchResponse> buildCall(final boolean success, final String errorMessage) {
        Call<RecipeSearchResponse> response = new Call<RecipeSearchResponse>() {
            @Override
            public Response<RecipeSearchResponse> execute() throws IOException {
                Response<RecipeSearchResponse> result = null;
                if (success) {
                    RecipeSearchResponse response = new RecipeSearchResponse();
                    response.setCount(1);
                    response.setRecipes(Arrays.asList(recipe));
                    result = Response.success(response);
                } else {
                    result = Response.error(null, null);
                }
                return result;
            }

            @Override
            public void enqueue(Callback<RecipeSearchResponse> callback) {
                if (success) {
                    try {
                        callback.onResponse(this, execute());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    callback.onFailure(this, new Throwable(errorMessage));
                }
            }

            @Override
            public boolean isExecuted() {
                return true;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<RecipeSearchResponse> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };

        return response;
    }
}
