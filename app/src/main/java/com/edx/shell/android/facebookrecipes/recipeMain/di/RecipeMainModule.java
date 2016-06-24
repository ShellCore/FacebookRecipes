package com.edx.shell.android.facebookrecipes.recipeMain.di;

import com.edx.shell.android.facebookrecipes.api.RecipeClient;
import com.edx.shell.android.facebookrecipes.api.RecipeService;
import com.edx.shell.android.facebookrecipes.libs.base.EventBus;
import com.edx.shell.android.facebookrecipes.recipeMain.GetNextRecipeInteractor;
import com.edx.shell.android.facebookrecipes.recipeMain.GetNextRecipeInteractorImpl;
import com.edx.shell.android.facebookrecipes.recipeMain.RecipeMainPresenter;
import com.edx.shell.android.facebookrecipes.recipeMain.RecipeMainPresenterImpl;
import com.edx.shell.android.facebookrecipes.recipeMain.RecipeMainRepository;
import com.edx.shell.android.facebookrecipes.recipeMain.RecipeMainRepositoryImpl;
import com.edx.shell.android.facebookrecipes.recipeMain.SaveRecipeInteractor;
import com.edx.shell.android.facebookrecipes.recipeMain.SaveRecipeInteractorImpl;
import com.edx.shell.android.facebookrecipes.recipeMain.ui.RecipeMainView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RecipeMainModule {
    private RecipeMainView view;

    public RecipeMainModule(RecipeMainView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    RecipeMainView providesRecipeMainView() {
        return view;
    }

    @Provides
    @Singleton
    RecipeMainPresenter providesRecipeMainPresenter(EventBus eventBus, RecipeMainView view, SaveRecipeInteractor saveInteractor, GetNextRecipeInteractor getNextInteractor) {
        return new RecipeMainPresenterImpl(eventBus, view, saveInteractor, getNextInteractor);
    }

    @Provides
    @Singleton
    SaveRecipeInteractor providesSaveRecipeInteractor(RecipeMainRepository repository) {
        return new SaveRecipeInteractorImpl(repository);
    }

    @Provides
    @Singleton
    GetNextRecipeInteractor providesGetNextRecipeInteractor(RecipeMainRepository repository) {
        return new GetNextRecipeInteractorImpl(repository);
    }

    @Provides
    @Singleton
    RecipeMainRepository providesRecipeMainRepository(EventBus eventBus, RecipeService service) {
        return new RecipeMainRepositoryImpl(eventBus, service);
    }

    @Provides
    @Singleton
    RecipeService providesRecipeService() {
        return new RecipeClient().getRecipeService();
    }
}
