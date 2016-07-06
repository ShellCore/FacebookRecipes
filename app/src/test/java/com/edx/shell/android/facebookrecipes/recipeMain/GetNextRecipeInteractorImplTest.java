package com.edx.shell.android.facebookrecipes.recipeMain;

import com.edx.shell.android.facebookrecipes.BaseTest;

import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

public class GetNextRecipeInteractorImplTest extends BaseTest {

    @Mock
    private RecipeMainRepository repository;

    private GetNextRecipeInteractorImpl interactor;

    @Override
    public void setup() throws Exception {
        super.setup();
        interactor = new GetNextRecipeInteractorImpl(repository);
    }

    @Test
    public void testExecute_callRepository() throws Exception {
        interactor.execute();
        verify(repository).getNextRecipe();
    }
}
