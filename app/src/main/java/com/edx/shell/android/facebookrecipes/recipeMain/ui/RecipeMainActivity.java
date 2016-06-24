package com.edx.shell.android.facebookrecipes.recipeMain.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.edx.shell.android.facebookrecipes.FacebookRecipesApp;
import com.edx.shell.android.facebookrecipes.R;
import com.edx.shell.android.facebookrecipes.entities.Recipe;
import com.edx.shell.android.facebookrecipes.libs.base.ImageLoader;
import com.edx.shell.android.facebookrecipes.recipeList.ui.RecipeListActivity;
import com.edx.shell.android.facebookrecipes.recipeMain.RecipeMainPresenter;
import com.edx.shell.android.facebookrecipes.recipeMain.di.RecipeMainComponent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeMainActivity extends AppCompatActivity implements RecipeMainView, SwipeGestureListener {

    // Variables
    private Recipe currentRecipe;

    // Servicios
    RecipeMainPresenter presenter;
    private ImageLoader imageLoader;
    private RecipeMainComponent component;

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
        setupImageLoader();
        setupGestureDetection();
        presenter.onCreate();
        presenter.getNextRecipe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipes_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_list:
                navigateToListScreen();
                break;
            case  R.id.action_logout:
                logout();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @OnClick(R.id.btn_dismiss)
    @Override
    public void onDismiss() {
        presenter.dismissRecipe();
    }

    @OnClick(R.id.btn_keep)
    @Override
    public void onKeep() {
        if (currentRecipe != null) {
            presenter.saveRecipe(currentRecipe);
        }
    }

    private void navigateToListScreen() {
        startActivity(new Intent(this, RecipeListActivity.class));
    }

    private void logout() {
        FacebookRecipesApp app = (FacebookRecipesApp) getApplication();
        app.logout();
    }

    private void setupInjection() {
        FacebookRecipesApp app = (FacebookRecipesApp) getApplication();
        component = app.getRecipeMainComponent(this, this);
        imageLoader = getImageLoader();
        presenter = getPresenter();
    }

    private ImageLoader getImageLoader() {
        return component.getImageLoader();
    }

    private RecipeMainPresenter getPresenter() {
        return component.getPresenter();
    }

    private void setupImageLoader() {
        RequestListener glideRequestListener = new RequestListener() {
            @Override
            public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                presenter.imageError(e.getLocalizedMessage());
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                presenter.imageReady();
                return false;
            }
        };
        imageLoader.setOnFinishedImageLoadingListener(glideRequestListener);
    }

    private void setupGestureDetection() {
        final GestureDetector gestureDetector = new GestureDetector(this, new SwipeGestureDetector(this));
        View.OnTouchListener gestureListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        imgRecipe.setOnTouchListener(gestureListener);
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
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.save_animation);
        anim.setAnimationListener(getAnimationListener());
        imgRecipe.startAnimation(anim);
    }

    @Override
    public void dismissAnimation() {
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.dismiss_animation);
        anim.setAnimationListener(getAnimationListener());
        imgRecipe.startAnimation(anim);
    }

    private Animation.AnimationListener getAnimationListener() {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cleanImage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }

    private void cleanImage() {
        imgRecipe.setImageResource(0);
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
