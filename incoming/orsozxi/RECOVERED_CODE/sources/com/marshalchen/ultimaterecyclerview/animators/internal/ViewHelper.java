package com.marshalchen.ultimaterecyclerview.animators.internal;

import android.view.View;
import androidx.core.view.ViewCompat;

/* loaded from: classes2.dex */
public final class ViewHelper {
    public static void clear(View view) {
        ViewCompat.setAlpha(view, 1.0f);
        ViewCompat.setScaleY(view, 1.0f);
        ViewCompat.setScaleX(view, 1.0f);
        ViewCompat.setTranslationY(view, 0.0f);
        ViewCompat.setTranslationX(view, 0.0f);
        ViewCompat.setRotation(view, 0.0f);
        ViewCompat.setRotationY(view, 0.0f);
        ViewCompat.setRotationX(view, 0.0f);
        view.setPivotY(view.getMeasuredHeight() / 2);
        ViewCompat.setPivotX(view, view.getMeasuredWidth() / 2);
        ViewCompat.animate(view).setInterpolator(null);
    }
}
