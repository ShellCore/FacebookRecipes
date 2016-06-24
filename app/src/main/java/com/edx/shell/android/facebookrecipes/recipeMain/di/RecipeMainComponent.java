package com.edx.shell.android.facebookrecipes.recipeMain.di;

import com.edx.shell.android.facebookrecipes.libs.base.ImageLoader;
import com.edx.shell.android.facebookrecipes.libs.di.LibsModule;
import com.edx.shell.android.facebookrecipes.recipeMain.RecipeMainPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RecipeMainModule.class, LibsModule.class})
public interface RecipeMainComponent {
    // void inject(RecipeMainActivity activity);
    ImageLoader getImageLoader();
    RecipeMainPresenter getPresenter();
}
