package com.edx.shell.android.facebookrecipes.recipeList.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.edx.shell.android.facebookrecipes.FacebookRecipesApp;
import com.edx.shell.android.facebookrecipes.R;
import com.edx.shell.android.facebookrecipes.entities.Recipe;
import com.edx.shell.android.facebookrecipes.recipeList.RecipeListPresenter;
import com.edx.shell.android.facebookrecipes.recipeList.adapters.OnItemClickListener;
import com.edx.shell.android.facebookrecipes.recipeList.adapters.RecipesAdapter;
import com.edx.shell.android.facebookrecipes.recipeMain.ui.RecipeMainActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeListActivity extends AppCompatActivity implements RecipeListView, OnItemClickListener {

    // Constantes
    private static final int NUM_COLUMNS = 2;

    // Servicios
    private RecipesAdapter adapter;
    private RecipeListPresenter presenter;

    // Componentes
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rec_recipes)
    RecyclerView recRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);
        setupToolbar();
        setupInjection();
        setupRecyclerView();
        presenter.onCreate();
        presenter.getRecipes();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipes_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_main:
                navigateToMainScreen();
                break;
            case R.id.action_logout:
                logout();
                break;
            default:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView() {
        recRecipes.setLayoutManager(new GridLayoutManager(this, NUM_COLUMNS));
        recRecipes.setAdapter(adapter);
    }

    private void setupInjection() {

    }

    private void navigateToMainScreen() {
        startActivity(new Intent(this, RecipeMainActivity.class));
    }

    private void logout() {
        FacebookRecipesApp app = (FacebookRecipesApp) getApplication();
        app.logout();
    }

    @OnClick(R.id.toolbar)
    public void onToolbarClick() {
        recRecipes.smoothScrollToPosition(0);
    }

    @Override
    public void setRecipes(List<Recipe> data) {
        adapter.setRecipes(data);
    }

    @Override
    public void recipeUpdated() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void recipeDeleted(Recipe recipe) {
        adapter.removeRecipe(recipe);
    }

    @Override
    public void onFavorite(Recipe recipe) {
        presenter.toggleFavorite(recipe);
    }

    @Override
    public void onDelete(Recipe recipe) {
        presenter.removeRecipe(recipe);
    }

    @Override
    public void onItemClick(Recipe recipe) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(recipe.getSourceUrl()));
        startActivity(intent);
    }
}
