package com.edx.shell.android.facebookrecipes.recipeList.di;

import com.edx.shell.android.facebookrecipes.libs.di.LibsModule;
import com.edx.shell.android.facebookrecipes.recipeList.RecipeListPresenter;
import com.edx.shell.android.facebookrecipes.recipeList.adapters.RecipesAdapter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RecipeListModule.class, LibsModule.class})
public interface RecipeListComponent {
    RecipesAdapter getAdapter();
    RecipeListPresenter getPresenter();
}
