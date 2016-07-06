package com.edx.shell.android.facebookrecipes.recipeList;

import com.edx.shell.android.facebookrecipes.BaseTest;
import com.edx.shell.android.facebookrecipes.entities.Recipe;
import com.edx.shell.android.facebookrecipes.libs.base.EventBus;
import com.edx.shell.android.facebookrecipes.recipeList.ui.RecipeListView;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.verify;

public class RecipeListPresenterImplTest extends BaseTest {

    @Mock
    private EventBus eventBus;
    @Mock
    private RecipeListView view;
    @Mock
    private RecipeListInteractor listInteractor;
    @Mock
    private StoredRecipesInteractor storedInteractor;
    @Mock
    private Recipe recipe;

    private RecipeListPresenterImpl presenter;

    @Override
    public void setup() throws Exception {
        super.setup();
        presenter = new RecipeListPresenterImpl(eventBus, view, listInteractor, storedInteractor);
    }

    @Test
    public void testOnCreate_subscribedToEventBus() throws Exception {
        presenter.onCreate();
        verify(eventBus).register(presenter);
    }

    @Test
    public void testOnDestroy_unsubscribedToEventBus() throws Exception {
        presenter.onDestroy();
        verify(eventBus).unregister(presenter);
        assertNull(presenter.getView());
    }

    @Test
    public void testGetRecipes_ExecuteListInteractor() throws Exception {
        presenter.getRecipes();
        verify(listInteractor).execute();
    }

    @Test
    public void testRemoveRecipe_ExecuteStoredInteractor() throws Exception {
        presenter.removeRecipe(recipe);
        verify(storedInteractor).executeDelete(recipe);
    }

    @Test
    public void testToggleFavorite_True() throws Exception {
        Recipe recipe = new Recipe();
        boolean favorite = true;
        recipe.setFavorite(favorite);

        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        presenter.toggleFavorite(recipe);
        verify(storedInteractor).executeUpdate(recipeArgumentCaptor.capture());

        assertEquals(!favorite, recipeArgumentCaptor.getValue().getFavorite());
    }

    @Test
    public void testToggleFavorite_False() throws Exception {
        Recipe recipe = new Recipe();
        boolean favorite = false;
        recipe.setFavorite(favorite);

        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        presenter.toggleFavorite(recipe);
        verify(storedInteractor).executeUpdate(recipeArgumentCaptor.capture());

        assertEquals(!favorite, recipeArgumentCaptor.getValue().getFavorite());
    }
}
