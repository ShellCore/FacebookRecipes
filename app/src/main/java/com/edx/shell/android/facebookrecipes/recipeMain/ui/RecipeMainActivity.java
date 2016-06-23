package com.edx.shell.android.facebookrecipes.recipeMain.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.edx.shell.android.facebookrecipes.R;
import com.edx.shell.android.facebookrecipes.entities.Recipe;
import com.edx.shell.android.facebookrecipes.libs.base.ImageLoader;
import com.edx.shell.android.facebookrecipes.recipeMain.RecipeMainPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeMainActivity extends AppCompatActivity implements RecipeMainView {

    // Variables
    private Recipe currentRecipe;

    // Servicios
    RecipeMainPresenter presenter;
    private ImageLoader imageLoader;

    // Componentes de la vista
    @Bind(R.id.img_recipe)
    ImageView imgRecipe;
    @Bind(R.id.btn_dismiss)
    ImageButton btnDismiss;
    @Bind(R.id.btn_keep)
    ImageButton btnKeep;
    @Bind(R.id.recipe_progress_bar)
    ProgressBar recipeProgressBar;
    @Bind(R.id.recipe_container)
    RelativeLayout recipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_main);
        ButterKnife.bind(this);
        setupInjection();
        presenter.onCreate();
        presenter.getNextRecipe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @OnClick(R.id.btn_dismiss)
    public void onDismiss() {
        presenter.dismissRecipe();
    }

    @OnClick(R.id.btn_keep)
    public void onKeep() {
        if (currentRecipe != null) {
            presenter.saveRecipe(currentRecipe);
        }
    }

    private void setupInjection() {

    }

    @Override
    public void showElements() {
        btnDismiss.setVisibility(View.VISIBLE);
        btnKeep.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideElements() {
        btnDismiss.setVisibility(View.GONE);
        btnKeep.setVisibility(View.GONE);

    }

    @Override
    public void showProgressBar() {
        recipeProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        recipeProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void saveAnimation() {

    }

    @Override
    public void dismissAnimation() {

    }

    @Override
    public void onRecipeSaved() {
        Snackbar.make(recipeContainer, R.string.recipemain_notice_saved, Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void setRecipe(Recipe recipe) {
        currentRecipe = recipe;
        imageLoader.load(imgRecipe, recipe.getImageUrl());
    }

    @Override
    public void onGetRecipeError(String error) {
        String msgError = String.format(getString(R.string.recipemain_error), error);
        Snackbar.make(recipeContainer, msgError, Snackbar.LENGTH_SHORT)
                .show();
    }
}
