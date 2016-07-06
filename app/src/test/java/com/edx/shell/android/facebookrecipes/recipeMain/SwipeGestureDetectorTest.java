package com.edx.shell.android.facebookrecipes.recipeMain;

import android.view.MotionEvent;

import com.edx.shell.android.facebookrecipes.BaseTest;
import com.edx.shell.android.facebookrecipes.BuildConfig;
import com.edx.shell.android.facebookrecipes.recipeMain.ui.SwipeGestureDetector;
import com.edx.shell.android.facebookrecipes.recipeMain.ui.SwipeGestureListener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class SwipeGestureDetectorTest extends BaseTest {

    @Mock
    private SwipeGestureListener listener;

    private SwipeGestureDetector detector;

    @Override
    public void setup() throws Exception {
        super.setup();
        detector = new SwipeGestureDetector(listener);
    }

    @Test
    public void testSwipeRight_ShouldCallKeepOnListener() throws Exception {
        long downtime = 0;
        long movetime = downtime + 500;
        long uptime = downtime + 1000;
        float xStart = 200;
        float yStart = 200;
        float xEnd = 500;
        float yEnd = 250;

        MotionEvent e1 = MotionEvent.obtain(downtime, movetime, MotionEvent.ACTION_MOVE, xStart, yStart, 0);
        MotionEvent e2 = MotionEvent.obtain(downtime, uptime, MotionEvent.ACTION_UP, xEnd, yEnd, 0);
        float velocity = 120;

        detector.onFling(e1, e2, velocity, 0);

        verify(listener).onKeep();
    }

    @Test
    public void testSwipeLeft_ShouldCallDismissOnListener() throws Exception {
        long downtime = 0;
        long movetime = downtime + 500;
        long uptime = downtime + 1000;
        float xStart = 200;
        float yStart = 200;
        float xEnd = -500;
        float yEnd = 250;

        MotionEvent e1 = MotionEvent.obtain(downtime, movetime, MotionEvent.ACTION_MOVE, xStart, yStart, 0);
        MotionEvent e2 = MotionEvent.obtain(downtime, uptime, MotionEvent.ACTION_UP, xEnd, yEnd, 0);
        float velocity = 120;

        detector.onFling(e1, e2, velocity, 0);

        verify(listener).onDismiss();
    }
}
