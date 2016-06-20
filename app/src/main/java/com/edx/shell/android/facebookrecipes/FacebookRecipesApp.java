package com.edx.shell.android.facebookrecipes;

import android.app.Application;

import com.facebook.FacebookSdk;

public class FacebookRecipesApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initFacebook();
    }

    private void initFacebook() {
        FacebookSdk.sdkInitialize(this);
    }
}
