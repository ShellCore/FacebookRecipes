package com.edx.shell.android.facebookrecipes;

import android.app.Application;

import com.facebook.FacebookSdk;
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
}
