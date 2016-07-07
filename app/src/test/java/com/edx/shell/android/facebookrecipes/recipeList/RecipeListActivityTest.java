package com.edx.shell.android.facebookrecipes.recipeList;

import android.support.annotation.StyleRes;

import com.edx.shell.android.facebookrecipes.BaseTest;
import com.edx.shell.android.facebookrecipes.BuildConfig;
import com.edx.shell.android.facebookrecipes.R;
import com.edx.shell.android.facebookrecipes.entities.Recipe;
import com.edx.shell.android.facebookrecipes.recipeList.adapters.RecipesAdapter;
import com.edx.shell.android.facebookrecipes.recipeList.ui.RecipeListActivity;
import com.edx.shell.android.facebookrecipes.recipeList.ui.RecipeListView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import java.util.List;

import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class RecipeListActivityTest extends BaseTest {

    @Mock
    private RecipesAdapter adapter;
    @Mock
    private RecipeListPresenter presenter;
    @Mock
    private List<Recipe> recipes;
    @Mock
    private Recipe recipe;

    private RecipeListView view;
    private ActivityController<RecipeListActivity> controller;

    @Override
    public void setup() throws Exception {
        super.setup();
        RecipeListActivity activity = new RecipeListActivity() {
            @Override
            public void setTheme(@StyleRes int resid) {
                super.setTheme(R.style.AppTheme_NoActionBar);
            }

            public RecipeListPresenter getListPresenter() {
                return presenter;
            }

            public RecipesAdapter getListAdapter() {
                return adapter;
            }
        };

        controller = ActivityController.of(Robolectric.getShadowsAdapter(), activity).create().visible();
        view = (RecipeListView) controller.get();
    }

    @Test
    public void testOnCreate_ShouldCallPresenter() throws Exception {
        verify(presenter).onCreate();
        verify(presenter).getRecipes();
    }

    @Test
    public void testOnDestroy_ShouldCallPresenter() throws Exception {
        controller.destroy();
        verify(presenter).onDestroy();
    }

    @Test
    public void testSetRecipes_ShouldSetInAdapter() throws Exception {
        view.setRecipes(recipes);
        verify(adapter).setRecipes(recipes);
    }

    @Test
    public void testRecipeUpdated_ShouldUpdateAdapter() throws Exception {
        view.recipeUpdated();
        verify(adapter).notifyDataSetChanged();
    }

    @Test
    public void testRecipeDeleted_ShouldDeleteAdapter() throws Exception {
        view.recipeDeleted(recipe);
        verify(adapter).removeRecipe(recipe);
    }
}
