package com.edx.shell.android.facebookrecipes.recipeMain;

import com.edx.shell.android.facebookrecipes.BaseTest;
import com.edx.shell.android.facebookrecipes.entities.Recipe;

import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

public class SaveRecipeInteractorImplTest extends BaseTest {

    @Mock
    private RecipeMainRepository repository;

    @Mock
    Recipe recipe;

    private SaveRecipeInteractorImpl interactor;


    @Override
    public void setup() throws Exception {
        super.setup();
        interactor = new SaveRecipeInteractorImpl(repository);
    }

    @Test
    public void testExecute_callRepository() throws Exception {
        interactor.execute(recipe);
        verify(repository).saveRecipe(recipe);
    }
}
