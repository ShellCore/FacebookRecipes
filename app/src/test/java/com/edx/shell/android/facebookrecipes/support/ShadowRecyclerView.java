package com.edx.shell.android.facebookrecipes.support;

import android.support.v7.widget.RecyclerView;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowViewGroup;

@Implements(RecyclerView.class)
public class ShadowRecyclerView extends ShadowViewGroup {
    private int smoothScrollPosition = -1;

    @Implementation
    public void smoothScrollToPosition(int position) {
        setSmoothScrollPosition(position);
    }

    public int getSmoothScrollPosition() {
        return smoothScrollPosition;
    }

    public void setSmoothScrollPosition(int smoothScrollPosition) {
        this.smoothScrollPosition = smoothScrollPosition;
    }
}
