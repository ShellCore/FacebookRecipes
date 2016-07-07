package com.edx.shell.android.facebookrecipes.recipeList.adapters;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.edx.shell.android.facebookrecipes.BaseTest;
import com.edx.shell.android.facebookrecipes.BuildConfig;
import com.edx.shell.android.facebookrecipes.R;
import com.edx.shell.android.facebookrecipes.entities.Recipe;
import com.edx.shell.android.facebookrecipes.libs.base.ImageLoader;
import com.edx.shell.android.facebookrecipes.support.ShadowRecyclerViewAdapter;
import com.facebook.share.model.ShareContent;
import com.facebook.share.widget.SendButton;
import com.facebook.share.widget.ShareButton;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.internal.ShadowExtractor;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
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
    private String URL;

    @Override
    public void setup() throws Exception {
        super.setup();
        URL = "http://www.google.com";
        when(recipe.getSourceUrl()).thenReturn(URL);

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

    @Test
    public void testOnFavoriteClick_ShouldCallListener() throws Exception {
        int positionToClick = 0;
        boolean favorite = true;
        when(recipe.getFavorite()).thenReturn(favorite);
        when(recipes.get(positionToClick)).thenReturn(recipe);

        shadowAdapter.itemVisible(positionToClick);
        shadowAdapter.performItemClickOverViewInHolder(positionToClick, R.id.btn_favorite);

        View view = shadowAdapter.getViewForHolderPosition(positionToClick);
        ImageButton btnFav = (ImageButton) view.findViewById(R.id.btn_favorite);

        assertEquals(favorite, btnFav.getTag());
        verify(onItemClickListener).onFavorite(recipe);
    }

    @Test
    public void testOnNonFavoriteClick_ShouldCallListener() throws Exception {
        int positionToClick = 0;
        boolean favorite = false;
        when(recipe.getFavorite()).thenReturn(favorite);
        when(recipes.get(positionToClick)).thenReturn(recipe);

        shadowAdapter.itemVisible(positionToClick);
        shadowAdapter.performItemClickOverViewInHolder(positionToClick, R.id.btn_favorite);

        View view = shadowAdapter.getViewForHolderPosition(positionToClick);
        ImageButton btnFav = (ImageButton) view.findViewById(R.id.btn_favorite);

        assertEquals(favorite, btnFav.getTag());
        verify(onItemClickListener).onFavorite(recipe);
    }

    @Test
    public void testOnDeleteClick_ShouldCallListener() throws Exception {
        int positionToClick = 0;
        when(recipes.get(positionToClick)).thenReturn(recipe);

        shadowAdapter.itemVisible(positionToClick);
        shadowAdapter.performItemClickOverViewInHolder(positionToClick, R.id.btn_delete);

        verify(onItemClickListener).onDelete(recipe);
    }

    @Test
    public void testFBShareBind_shareContentSet() throws Exception {
        int positionToShow = 0;
        when(recipes.get(positionToShow)).thenReturn(recipe);

        shadowAdapter.itemVisible(positionToShow);
        View v = shadowAdapter.getViewForHolderPosition(positionToShow);
        ShareButton btnShare = (ShareButton) v.findViewById(R.id.btn_share);

        ShareContent shareContent = btnShare.getShareContent();
        assertNotNull(shareContent);
        assertEquals(URL, shareContent.getContentUrl().toString());
    }

    @Test
    public void testFBSendBind_sendContentSet() throws Exception {
        int positionToShow = 0;
        when(recipes.get(positionToShow)).thenReturn(recipe);

        shadowAdapter.itemVisible(positionToShow);
        View v = shadowAdapter.getViewForHolderPosition(positionToShow);
        SendButton btnSend = (SendButton) v.findViewById(R.id.btn_send);

        ShareContent shareContent = btnSend.getShareContent();
        assertNotNull(shareContent);
        assertEquals(URL, shareContent.getContentUrl().toString());
    }
}