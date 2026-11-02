package de.keyboardsurfer.android.widget.crouton;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/* loaded from: classes2.dex */
final class DefaultAnimationsBuilder {
    private static final long DURATION = 400;
    private static int lastInAnimationHeight;
    private static int lastOutAnimationHeight;
    private static Animation slideInDownAnimation;
    private static Animation slideOutUpAnimation;

    private DefaultAnimationsBuilder() {
    }

    static Animation buildDefaultSlideInDownAnimation(View view) {
        if (!areLastMeasuredInAnimationHeightAndCurrentEqual(view) || slideInDownAnimation == null) {
            TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, -view.getMeasuredHeight(), 0.0f);
            slideInDownAnimation = translateAnimation;
            translateAnimation.setDuration(DURATION);
            setLastInAnimationHeight(view.getMeasuredHeight());
        }
        return slideInDownAnimation;
    }

    static Animation buildDefaultSlideOutUpAnimation(View view) {
        if (!areLastMeasuredOutAnimationHeightAndCurrentEqual(view) || slideOutUpAnimation == null) {
            TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, -view.getMeasuredHeight());
            slideOutUpAnimation = translateAnimation;
            translateAnimation.setDuration(DURATION);
            setLastOutAnimationHeight(view.getMeasuredHeight());
        }
        return slideOutUpAnimation;
    }

    private static boolean areLastMeasuredInAnimationHeightAndCurrentEqual(View view) {
        return areLastMeasuredAnimationHeightAndCurrentEqual(lastInAnimationHeight, view);
    }

    private static boolean areLastMeasuredOutAnimationHeightAndCurrentEqual(View view) {
        return areLastMeasuredAnimationHeightAndCurrentEqual(lastOutAnimationHeight, view);
    }

    private static boolean areLastMeasuredAnimationHeightAndCurrentEqual(int i, View view) {
        return i == view.getMeasuredHeight();
    }

    private static void setLastInAnimationHeight(int i) {
        lastInAnimationHeight = i;
    }

    private static void setLastOutAnimationHeight(int i) {
        lastOutAnimationHeight = i;
    }
}
