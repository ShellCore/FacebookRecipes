package com.edx.shell.android.facebookrecipes.recipeList;

import com.edx.shell.android.facebookrecipes.BaseTest;
import com.edx.shell.android.facebookrecipes.BuildConfig;
import com.edx.shell.android.facebookrecipes.FacebookRecipesApp;
import com.edx.shell.android.facebookrecipes.entities.Recipe;
import com.edx.shell.android.facebookrecipes.libs.base.EventBus;
import com.edx.shell.android.facebookrecipes.recipeList.events.RecipeListEvent;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class RecipeListRepositoryImplTest extends BaseTest {

    @Mock
    private EventBus eventBus;

    private FacebookRecipesApp app;
    private ArgumentCaptor<RecipeListEvent> recipeListEventArgumentCaptor;

    private RecipeListRepositoryImpl repository;

    @Override
    public void setup() throws Exception {
        super.setup();
        repository = new RecipeListRepositoryImpl(eventBus);
        app = (FacebookRecipesApp) RuntimeEnvironment.application;
        recipeListEventArgumentCaptor = ArgumentCaptor.forClass(RecipeListEvent.class);

        app.onCreate();
    }

    @After
    public void tearDown() throws Exception {
        app.onTerminate();
    }

    @Test
    public void testGetSavedRecipes_EventPosted() throws Exception {
        // Inicialización de la lista
        int recipesToStore = 5;
        Recipe currentRecipe;
        List<Recipe> testRecipeList = new ArrayList<>();
        for (int i = 0; i < recipesToStore; i++) {
            currentRecipe = new Recipe();
            currentRecipe.setRecipeId("id " + i);
            currentRecipe.save();
            testRecipeList.add(currentRecipe);
        }

        List<Recipe> recipesFromDB = new Select().from(Recipe.class)
                                                 .queryList();

        // Empiezan las pruebas
        repository.getSavedRecipes();
        verify(eventBus).post(recipeListEventArgumentCaptor.capture());
        RecipeListEvent event = recipeListEventArgumentCaptor.getValue();
        assertEquals(RecipeListEvent.READ_EVENT, event.getType());
        assertEquals(recipesFromDB, event.getRecipes());

        // Terminación de las pruebas, limpieza de la memoria
        for (Recipe recipe : testRecipeList) {
            recipe.delete();
        }
    }

    @Test
    public void testUpdateRecipe() throws Exception {

    }

    @Test
    public void testRemoveRecipe() throws Exception {

    }
}
