package com.edx.shell.android.facebookrecipes.recipeMain;

import com.edx.shell.android.facebookrecipes.BaseTest;
import com.edx.shell.android.facebookrecipes.api.RecipeService;
import com.edx.shell.android.facebookrecipes.entities.Recipe;
import com.edx.shell.android.facebookrecipes.libs.base.EventBus;
import com.edx.shell.android.facebookrecipes.recipeMain.events.RecipeMainEvent;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
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
}
