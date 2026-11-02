package it.feio.android.checklistview.utils;

import android.os.Build;
import android.view.View;
import android.view.animation.AlphaAnimation;

/* loaded from: classes2.dex */
public class AlphaManager {
    public static void setAlpha(View view, float f) {
        if (Build.VERSION.SDK_INT < 11) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, f);
            alphaAnimation.setFillAfter(true);
            view.startAnimation(alphaAnimation);
            return;
        }
        view.setAlpha(f);
    }
}
