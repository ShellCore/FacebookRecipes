package com.edx.shell.android.facebookrecipes.recipeList.di;

import com.edx.shell.android.facebookrecipes.entities.Recipe;
import com.edx.shell.android.facebookrecipes.libs.base.EventBus;
import com.edx.shell.android.facebookrecipes.libs.base.ImageLoader;
import com.edx.shell.android.facebookrecipes.recipeList.RecipeListInteractor;
import com.edx.shell.android.facebookrecipes.recipeList.RecipeListInteractorImpl;
import com.edx.shell.android.facebookrecipes.recipeList.RecipeListPresenter;
import com.edx.shell.android.facebookrecipes.recipeList.RecipeListPresenterImpl;
import com.edx.shell.android.facebookrecipes.recipeList.RecipeListRepository;
import com.edx.shell.android.facebookrecipes.recipeList.RecipeListRepositoryImpl;
import com.edx.shell.android.facebookrecipes.recipeList.StoredRecipesInteractor;
import com.edx.shell.android.facebookrecipes.recipeList.StoredRecipesInteractorImpl;
import com.edx.shell.android.facebookrecipes.recipeList.adapters.OnItemClickListener;
import com.edx.shell.android.facebookrecipes.recipeList.adapters.RecipesAdapter;
import com.edx.shell.android.facebookrecipes.recipeList.ui.RecipeListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RecipeListModule {
    private RecipeListView view;
    private OnItemClickListener listener;

    public RecipeListModule(RecipeListView view, OnItemClickListener listener) {
        this.view = view;
        this.listener = listener;
    }

    @Provides
    @Singleton
    RecipeListView providesRecipeListView() {
        return view;
    }

    @Provides
    @Singleton
    RecipeListPresenter providesRecipeListPresenter(EventBus eventBus, RecipeListView view, RecipeListInteractor listInteractor, StoredRecipesInteractor storedInteractor) {
        return new RecipeListPresenterImpl(eventBus, view, listInteractor, storedInteractor);
    }

    @Provides
    @Singleton
    StoredRecipesInteractor providesStoredRecipesInteractor(RecipeListRepository repository) {
        return new StoredRecipesInteractorImpl(repository);
    }

    @Provides
    @Singleton
    RecipeListInteractor providesRecipeListInteractor(RecipeListRepository repository) {
        return new RecipeListInteractorImpl(repository);
    }

    @Provides
    @Singleton
    RecipeListRepository providesRecipeListRepository(EventBus eventBus) {
        return new RecipeListRepositoryImpl(eventBus);
    }

    @Provides
    @Singleton
    RecipesAdapter providesRecipesAdapter(ImageLoader imageLoader, List<Recipe> recipes, OnItemClickListener onItemClickListener) {
        return new RecipesAdapter(imageLoader, recipes, onItemClickListener);
    }

    @Provides
    @Singleton
    List<Recipe> providesEmptyList() {
        return new ArrayList<Recipe>();
    }

    @Provides
    @Singleton
    OnItemClickListener providesOnItemClickListener() {
        return listener;
    }
}
