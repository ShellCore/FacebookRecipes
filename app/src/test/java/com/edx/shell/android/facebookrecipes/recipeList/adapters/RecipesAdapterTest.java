package com.edx.shell.android.facebookrecipes.recipeList.adapters;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.edx.shell.android.facebookrecipes.BaseTest;
import com.edx.shell.android.facebookrecipes.BuildConfig;
import com.edx.shell.android.facebookrecipes.entities.Recipe;
import com.edx.shell.android.facebookrecipes.libs.base.ImageLoader;
import com.edx.shell.android.facebookrecipes.support.ShadowRecyclerViewAdapter;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.internal.ShadowExtractor;

import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, shadows = {ShadowRecyclerViewAdapter.class})
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
        when(recipe.getSourceUrl()).thenReturn("http://www.galileo.edu");

        adapter = new RecipesAdapter(imageLoader, recipes, onItemClickListener);
        shadowAdapter = (ShadowRecyclerViewAdapter) ShadowExtractor.extract(adapter);

        Activity activity = Robolectric.setupActivity(Activity.class);
        RecyclerView recyclerView = new RecyclerView(activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        recyclerView.setAdapter(adapter);
    }
}