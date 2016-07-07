package com.edx.shell.android.facebookrecipes.recipeList;

import com.edx.shell.android.facebookrecipes.BaseTest;

import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

public class RecipeListInteractorImplTest extends BaseTest {

    @Mock
    private RecipeListRepository repository;

    private RecipeListInteractorImpl interactor;

    @Override
    public void setup() throws Exception {
        super.setup();
        interactor = new RecipeListInteractorImpl(repository);
    }

    @Test
    public void testExecute_ShouldCallRepository() throws Exception {
        interactor.execute();
        verify(repository).getSavedRecipes();
    }
}
