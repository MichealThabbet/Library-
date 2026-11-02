package com.marshalchen.ultimaterecyclerview.ui;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.core.view.ViewCompat;
import it.piapostolos.android.pinotes.R2;

/* loaded from: classes2.dex */
public class AdGoogleDisplaySupport {
    public static final int BANNER = 101;
    public static final int FULL_BANNER = 102;
    public static final int LARGE_BANNER = 103;
    public static final int LEADERBOARD = 104;
    public static final int MEDIUM_RECTANGLE = 105;
    public static final int WIDE_SKYSCRAPER = 106;

    public static RelativeLayout initialSupport(Activity activity, DisplayMetrics displayMetrics) {
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return new RelativeLayout(activity);
    }

    public static <ad extends ViewGroup> void panelAdjust(ad ad, int i, int i2) {
        panelAdjustApply(ad, i);
    }

    public static <ad extends ViewGroup> void panelAdjust(ad ad, int i) {
        panelAdjustApply(ad, i);
    }

    private static <ad extends ViewGroup> void panelAdjustApply(ad ad, int i) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, i);
        layoutParams.addRule(11, -1);
        layoutParams.addRule(9, -1);
        layoutParams.addRule(10, -1);
        layoutParams.addRule(8, -1);
        layoutParams.addRule(14, -1);
        ad.setLayoutParams(layoutParams);
    }

    public static double ratioMatching(DisplayMetrics displayMetrics) {
        int i = displayMetrics.densityDpi;
        return 1.2d;
    }

    public static int defaultHeight(DisplayMetrics displayMetrics) {
        return defaultHeightApply(displayMetrics, 105);
    }

    public static int defaultHeight(DisplayMetrics displayMetrics, int i) {
        return defaultHeightApply(displayMetrics, i);
    }

    private static int defaultHeightApply(DisplayMetrics displayMetrics, int i) {
        if (i != 105) {
            return (i != 101 || displayMetrics.widthPixels == 1080) ? 100 : 80;
        }
        int i2 = displayMetrics.widthPixels;
        if (i2 == 720) {
            return 400;
        }
        if (i2 != 1080) {
            return 500;
        }
        return R2.attr.layout_constraintBottom_creator;
    }

    public static <T extends ViewGroup> void scale(T t, double d) {
        float f = (float) d;
        ViewCompat.setScaleX(t, f);
        ViewCompat.setScaleY(t, f);
    }
}
