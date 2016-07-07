package com.edx.shell.android.facebookrecipes.recipeList;

import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.StyleRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.edx.shell.android.facebookrecipes.BaseTest;
import com.edx.shell.android.facebookrecipes.BuildConfig;
import com.edx.shell.android.facebookrecipes.R;
import com.edx.shell.android.facebookrecipes.entities.Recipe;
import com.edx.shell.android.facebookrecipes.libs.base.ImageLoader;
import com.edx.shell.android.facebookrecipes.login.ui.LoginActivity;
import com.edx.shell.android.facebookrecipes.recipeList.adapters.OnItemClickListener;
import com.edx.shell.android.facebookrecipes.recipeList.adapters.RecipesAdapter;
import com.edx.shell.android.facebookrecipes.recipeList.ui.RecipeListActivity;
import com.edx.shell.android.facebookrecipes.recipeList.ui.RecipeListView;
import com.edx.shell.android.facebookrecipes.recipeMain.ui.RecipeMainActivity;
import com.edx.shell.android.facebookrecipes.support.ShadowRecyclerView;
import com.edx.shell.android.facebookrecipes.support.ShadowRecyclerViewAdapter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.internal.ShadowExtractor;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21,
        shadows = {ShadowRecyclerView.class, ShadowRecyclerViewAdapter.class})
public class RecipeListActivityTest extends BaseTest {

    @Mock
    private RecipesAdapter adapter;
    @Mock
    private RecipeListPresenter presenter;
    @Mock
    private List<Recipe> recipes;
    @Mock
    private Recipe recipe;
    @Mock
    private ImageLoader imageLoader;

    private RecipeListView view;
    private RecipeListActivity activity;
    private OnItemClickListener clickListener;
    private ActivityController<RecipeListActivity> controller;

    private ShadowActivity shadowActivity;
    private ShadowRecyclerViewAdapter shadowAdapter;

    @Override
    public void setup() throws Exception {
        super.setup();
        RecipeListActivity recipeListActivity = new RecipeListActivity() {
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

        controller = ActivityController.of(Robolectric.getShadowsAdapter(), recipeListActivity).create().visible();
        view = (RecipeListView) controller.get();
        activity = controller.get();
        clickListener = (OnItemClickListener) controller.get();

        shadowActivity = shadowOf(recipeListActivity);
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
    public void testLogoutMenuClicked_ShouldLaunchLoginActivity() throws Exception {
        shadowActivity.clickMenuItem(R.id.action_logout);

        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(new ComponentName(activity, LoginActivity.class), intent.getComponent());
    }

    @Test
    public void testMainMenuClicked_ShouldLaunchRecipeMainActivity() throws Exception {
        shadowActivity.clickMenuItem(R.id.action_main);

        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(new ComponentName(activity, RecipeMainActivity.class), intent.getComponent());
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

    @Test
    public void testOnToolBarClickedtestOnRecyclerViewScroll_ShouldChangeScrollPosition() throws Exception {
        int scrollPosition = 1;

        RecyclerView recRecipes = (RecyclerView) activity.findViewById(R.id.rec_recipes);
        ShadowRecyclerView shadowRecyclerView = (ShadowRecyclerView) ShadowExtractor.extract(recRecipes);

        recRecipes.smoothScrollToPosition(scrollPosition);
        assertEquals(scrollPosition, shadowRecyclerView.getSmoothScrollPosition());
    }

    @Test
    public void testOnToolBarClicked_RecyclerViewShouldScrollToTop() throws Exception {
        int scrollPosition = 1;
        int topScrollPosition = 0;
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        RecyclerView recRecipes = (RecyclerView) activity.findViewById(R.id.rec_recipes);
        ShadowRecyclerView shadowRecyclerView = (ShadowRecyclerView) ShadowExtractor.extract(recRecipes);
        shadowRecyclerView.setSmoothScrollPosition(scrollPosition);

        toolbar.performClick();
        assertEquals(topScrollPosition, shadowRecyclerView.getSmoothScrollPosition());
    }

    @Test
    public void testRecyclerViewItemClicked_ShouldStartViewActivity() throws Exception {
        int positionToClick = 0;
        setUpShadowAdapter(positionToClick);

        shadowAdapter.itemVisible(positionToClick);
        shadowAdapter.performItemClick(positionToClick);

        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(Intent.ACTION_VIEW, intent.getAction());
        assertEquals(recipes.get(positionToClick).getSourceUrl(), intent.getDataString());
    }

    private void setUpShadowAdapter(int positionToClick) {
        when(recipe.getSourceUrl()).thenReturn("http://www.google.com");
        when(recipes.get(positionToClick)).thenReturn(recipe);

        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.rec_recipes);
        RecipesAdapter adapterPopulated = new RecipesAdapter(imageLoader, recipes, clickListener);
        recyclerView.setAdapter(adapterPopulated);
        shadowAdapter = (ShadowRecyclerViewAdapter) ShadowExtractor.extract(recyclerView.getAdapter());
    }
}
