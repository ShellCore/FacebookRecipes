package com.edx.shell.android.facebookrecipes.recipeMain;

import com.edx.shell.android.facebookrecipes.BaseTest;
import com.edx.shell.android.facebookrecipes.entities.Recipe;
import com.edx.shell.android.facebookrecipes.libs.base.EventBus;
import com.edx.shell.android.facebookrecipes.recipeMain.ui.RecipeMainView;

import org.junit.Test;
import org.mockito.Mock;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.verify;

public class RecipeMainPresenterImplTest extends BaseTest {

    @Mock
    private EventBus eventBus;
    @Mock
    private RecipeMainView view;
    @Mock
    private SaveRecipeInteractor saveInteractor;
    @Mock
    private GetNextRecipeInteractor getNextInteractor;

    @Mock
    Recipe recipe;

    private RecipeMainPresenterImpl presenter;

    @Override
    public void setup() throws Exception {
        super.setup();
        presenter = new RecipeMainPresenterImpl(eventBus, view, saveInteractor, getNextInteractor);
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
    public void testSaveRecipe_executeSaveinteractor() throws Exception {
        presenter.saveRecipe(recipe);

        assertNotNull(presenter.getView());
        verify(view).saveAnimation();
        verify(view).hideElements();
        verify(view).showProgressBar();
        verify(saveInteractor).execute(recipe);
    }

    @Test
    public void testDismissRecipe_executeGetNextRecipeInteractor() throws Exception {
        presenter.dismissRecipe();

        assertNotNull(presenter.getView());
        verify(view).dismissAnimation();
    }

    @Test
    public void testGetNextRecipe_executeGetNextRecipeInteractor() throws Exception {
        presenter.getNextRecipe();

        assertNotNull(presenter.getView());
        verify(view).hideElements();
        verify(view).showProgressBar();
        verify(getNextInteractor).execute();
    }
}
