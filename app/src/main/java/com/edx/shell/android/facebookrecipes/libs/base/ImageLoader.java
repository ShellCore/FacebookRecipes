package com.edx.shell.android.facebookrecipes.libs.base;

import android.widget.ImageView;

public interface ImageLoader {
    void load(ImageView imageView, String url);
    void setOnFinishedImageLoadingListener(Object listener);
}
