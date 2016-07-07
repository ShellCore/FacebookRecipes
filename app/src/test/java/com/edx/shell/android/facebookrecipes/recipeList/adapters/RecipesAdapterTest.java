package com.edx.shell.android.facebookrecipes.recipeList.adapters;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.edx.shell.android.facebookrecipes.BaseTest;
import com.edx.shell.android.facebookrecipes.BuildConfig;
import com.edx.shell.android.facebookrecipes.R;
import com.edx.shell.android.facebookrecipes.entities.Recipe;
import com.edx.shell.android.facebookrecipes.libs.base.ImageLoader;
import com.edx.shell.android.facebookrecipes.support.ShadowRecyclerViewAdapter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.internal.ShadowExtractor;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class,
        sdk = 21,
        shadows = {ShadowRecyclerViewAdapter.class})
public class RecipesAdapterTest extends BaseTest {

    @Mock
    private Recipe recipe;
    @Mock
    private ImageLoader imageLoader;
    @Mock
    private List<Recipe> recipes;
    @Mock
    private OnItemClickListener onItemClickListener;

    private RecipesAdapter adapter;
    private ShadowRecyclerViewAdapter shadowAdapter;

    @Override
    public void setup() throws Exception {
        super.setup();
        when(recipe.getSourceUrl()).thenReturn("http://www.google.com");

        adapter = new RecipesAdapter(imageLoader, recipes, onItemClickListener);
        shadowAdapter = (ShadowRecyclerViewAdapter) ShadowExtractor.extract(adapter);

        Activity activity = Robolectric.setupActivity(Activity.class);
        RecyclerView recyclerView = new RecyclerView(activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        recyclerView.setAdapter(adapter);
    }

    @Test
    public void testSetRecipes_ItemCountMatches() throws Exception {
        int itemCount = 5;
        when(recipes.size()).thenReturn(itemCount);
        adapter.setRecipes(recipes);

        assertEquals(itemCount, adapter.getItemCount());
    }

    @Test
    public void testRemoveRecipe_IsRemovedFromAdapter() throws Exception {
        adapter.removeRecipe(recipe);
        verify(recipes).remove(recipe);
    }

    @Test
    public void testOnItemClick_ShouldCallListener() throws Exception {
        int positionToClick = 0;
        when(recipes.get(positionToClick)).thenReturn(recipe);

        shadowAdapter.itemVisible(positionToClick);
        shadowAdapter.performItemClick(positionToClick);

        verify(onItemClickListener).onItemClick(recipe);
    }

    @Test
    public void testViewHolder_ShouldRenderTitle() throws Exception {
        int positionToShow = 0;
        String recipeTitle = "title";
        when(recipe.getTitle()).thenReturn(recipeTitle);
        when(recipes.get(positionToShow)).thenReturn(recipe);

        shadowAdapter.itemVisible(positionToShow);

        View view = shadowAdapter.getViewForHolderPosition(positionToShow);
        TextView txtRecipeName = (TextView) view.findViewById(R.id.txt_recipe_name);

        assertEquals(recipeTitle, txtRecipeName.getText().toString());
    }
}