package com.edx.shell.android.facebookrecipes.recipeMain;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.edx.shell.android.facebookrecipes.BaseTest;
import com.edx.shell.android.facebookrecipes.BuildConfig;
import com.edx.shell.android.facebookrecipes.R;
import com.edx.shell.android.facebookrecipes.entities.Recipe;
import com.edx.shell.android.facebookrecipes.libs.base.ImageLoader;
import com.edx.shell.android.facebookrecipes.recipeMain.ui.RecipeMainActivity;
import com.edx.shell.android.facebookrecipes.recipeMain.ui.RecipeMainView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class RecipeMainActivityTest extends BaseTest {

    @Mock
    private Recipe currentRecipe;
    @Mock
    private RecipeMainPresenter presenter;
    @Mock
    private ImageLoader imageLoader;

    private RecipeMainActivity activity;

    private RecipeMainView view;

    private ActivityController<RecipeMainActivity> controller;

    @Override
    public void setup() throws Exception {
        super.setup();
        RecipeMainActivity recipeMainActivity = new RecipeMainActivity() {
            public ImageLoader getImageLoader() {
                return imageLoader;
            }

            public RecipeMainPresenter getPresenter() {
                return presenter;
            }
        };

        controller = ActivityController.of(Robolectric.getShadowsAdapter(), recipeMainActivity)
                .create()
                .visible();

        activity = controller.get();
        view = (RecipeMainView) activity;
    }

    @Test
    public void testShowProgressBar_progressBarShouldBeVisible() throws Exception {
        view.showProgressBar();

        ProgressBar progressBar = (ProgressBar) activity.findViewById(R.id.recipe_progress_bar);
        assertEquals(View.VISIBLE, progressBar.getVisibility());
    }

    @Test
    public void testHideProgressBar_progressBarShouldBeGone() throws Exception {
        view.hideProgressBar();

        ProgressBar progressBar = (ProgressBar) activity.findViewById(R.id.recipe_progress_bar);
        assertEquals(View.GONE, progressBar.getVisibility());
    }

    @Test
    public void testShowElements_buttonsShouldBeVisible() throws Exception {
        view.showElements();

        ImageButton btnDismiss = (ImageButton) activity.findViewById(R.id.btn_dismiss);
        ImageButton btnKeep = (ImageButton) activity.findViewById(R.id.btn_keep);
        assertEquals(View.VISIBLE, btnDismiss.getVisibility());
        assertEquals(View.VISIBLE, btnKeep.getVisibility());

    }

    @Test
    public void testHideElements_buttonsShouldBeGone() throws Exception {
        view.hideElements();

        ImageButton btnDismiss = (ImageButton) activity.findViewById(R.id.btn_dismiss);
        ImageButton btnKeep = (ImageButton) activity.findViewById(R.id.btn_keep);
        assertEquals(View.GONE, btnDismiss.getVisibility());
        assertEquals(View.GONE, btnKeep.getVisibility());
    }

    @Test
    public void testSetRecipe_ImageLoaderShouldBeCalled() throws Exception {
        String url = "http://www.galileo.edu";
        when(currentRecipe.getImageUrl()).thenReturn(url);

        view.setRecipe(currentRecipe);
        ImageView imgRecipe = (ImageView) activity.findViewById(R.id.img_recipe);
        verify(imageLoader).load(imgRecipe, currentRecipe.getImageUrl());
    }

    @Test
    public void testSaveAnimation_AnimationShouldBeStarted() throws Exception {
        view.saveAnimation();

        ImageView imgRecipe = (ImageView) activity.findViewById(R.id.img_recipe);
        assertNotNull(imgRecipe.getAnimation());
        assertTrue(imgRecipe.getAnimation().hasStarted());
    }

    @Test
    public void testDismissAnimation_AnimationShouldBeStarted() throws Exception {
        view.dismissAnimation();

        ImageView imgRecipe = (ImageView) activity.findViewById(R.id.img_recipe);
        assertNotNull(imgRecipe.getAnimation());
        assertTrue(imgRecipe.getAnimation().hasStarted());
    }
}
