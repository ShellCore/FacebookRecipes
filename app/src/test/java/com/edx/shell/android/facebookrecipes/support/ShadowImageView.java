package com.edx.shell.android.facebookrecipes.support;

import android.view.MotionEvent;
import android.widget.ImageView;

import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;
import org.robolectric.shadows.ShadowView;

@Implements(ImageView.class)
public class ShadowImageView extends ShadowView {
    @RealObject
    private ImageView realImageView;

    public void performSwipe(float xStart, float yStart, float xEnd, float yEnd, long duration) {
        long downtime = 0;
        long moveTime = downtime + duration / 2;
        long upTime = downtime + duration;

        realImageView.dispatchTouchEvent(MotionEvent.obtain(downtime, downtime, MotionEvent.ACTION_DOWN, xStart, yStart, 0));
        realImageView.dispatchTouchEvent(MotionEvent.obtain(downtime, moveTime, MotionEvent.ACTION_MOVE, xEnd/2, yEnd/2, 0));
        realImageView.dispatchTouchEvent(MotionEvent.obtain(downtime, moveTime, MotionEvent.ACTION_MOVE, xEnd/2, yEnd/2, 0));
        realImageView.dispatchTouchEvent(MotionEvent.obtain(downtime, downtime, MotionEvent.ACTION_UP, xEnd, yEnd, 0));
    }
}
