package com.edx.shell.android.facebookrecipes;

import android.app.Application;
import android.content.Intent;

import com.edx.shell.android.facebookrecipes.libs.di.LibsModule;
import com.edx.shell.android.facebookrecipes.login.ui.LoginActivity;
import com.edx.shell.android.facebookrecipes.recipeMain.di.DaggerRecipeMainComponent;
import com.edx.shell.android.facebookrecipes.recipeMain.di.RecipeMainComponent;
import com.edx.shell.android.facebookrecipes.recipeMain.di.RecipeMainModule;
import com.edx.shell.android.facebookrecipes.recipeMain.ui.RecipeMainActivity;
import com.edx.shell.android.facebookrecipes.recipeMain.ui.RecipeMainView;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.raizlabs.android.dbflow.config.FlowManager;

public class FacebookRecipesApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initFacebook();
        initDb();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        dbTearDown();
    }

    private void initFacebook() {
        FacebookSdk.sdkInitialize(this);
    }

    private void initDb() {
        FlowManager.init(this);
    }

    private void dbTearDown() {
        FlowManager.destroy();
    }

    public void logout() {
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public RecipeMainComponent getRecipeMainComponent(RecipeMainActivity activity, RecipeMainView view) {
        return DaggerRecipeMainComponent.builder()
                .libsModule(new LibsModule(activity))
                .recipeMainModule(new RecipeMainModule(view))
                .build();
    }
}
