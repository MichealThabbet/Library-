package it.orsozoxi.android.orsonotes.utils;

import android.view.View;

/* loaded from: classes3.dex */
public class AlphaManager {
    private AlphaManager() {
    }

    public static void setAlpha(View view, float f) {
        if (view != null) {
            view.setAlpha(f);
        }
    }
}
