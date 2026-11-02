package it.feio.android.checklistview.utils;

import android.content.Context;

/* loaded from: classes2.dex */
public class DensityUtil {
    public static int dpToPx(float f, Context context) {
        return Math.round(f * (context.getResources().getDisplayMetrics().densityDpi / 160.0f));
    }

    public static int pxToDp(float f, Context context) {
        return Math.round(f / (context.getResources().getDisplayMetrics().densityDpi / 160.0f));
    }
}
