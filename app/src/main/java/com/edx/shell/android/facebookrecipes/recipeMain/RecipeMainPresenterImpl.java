package com.edx.shell.android.facebookrecipes.recipeMain;

import com.edx.shell.android.facebookrecipes.entities.Recipe;
import com.edx.shell.android.facebookrecipes.libs.base.EventBus;
import com.edx.shell.android.facebookrecipes.recipeMain.events.RecipeMainEvent;
import com.edx.shell.android.facebookrecipes.recipeMain.ui.RecipeMainView;

import org.greenrobot.eventbus.Subscribe;

public class RecipeMainPresenterImpl implements RecipeMainPresenter {

    // Servicios
    private EventBus eventBus;
    private RecipeMainView view;
    private SaveRecipeInteractor saveInteractor;
    private GetNextRecipeInteractor getNextInteractor;

    public RecipeMainPresenterImpl(EventBus eventBus, RecipeMainView view, SaveRecipeInteractor saveInteractor, GetNextRecipeInteractor getNextInteractor) {
        this.eventBus = eventBus;
        this.view = view;
        this.saveInteractor = saveInteractor;
        this.getNextInteractor = getNextInteractor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        view = null;
    }

    @Override
    public void dismissRecipe() {
        if (view != null) {
            view.dismissAnimation();
        }
        getNextRecipe();
    }

    @Override
    public void getNextRecipe() {
        if (view != null) {
            view.hideElements();
            view.showProgressBar();
        }
        getNextInteractor.execute();
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        if (view != null) {
            view.saveAnimation();
            view.hideElements();
            view.showProgressBar();
        }
        saveInteractor.execute(recipe);
    }

    @Override
    @Subscribe
    public void onEventMainThread(RecipeMainEvent event) {
        if (view != null) {
            String error = event.getError();
            if (error != null) {
                view.hideProgressBar();
                view.onGetRecipeError(error);
            } else {
                switch (event.getType()) {
                    case RecipeMainEvent.NEXT_EVENT:
                        view.setRecipe(event.getRecipe());
                        break;
                    case RecipeMainEvent.SAVE_EVENT:
                        view.onRecipeSaved();
                        getNextInteractor.execute();
                        break;
                    default:
                }
            }
        }
    }

    @Override
    public void imageError(String error) {
        if (view != null) {
            view.onGetRecipeError(error);
        }
    }

    @Override
    public void imageReady() {
        if (view != null) {
            view.hideProgressBar();
            view.showElements();
        }
    }

    @Override
    public RecipeMainView getView() {
        return view;
    }
}
