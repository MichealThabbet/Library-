package it.piapostolos.android.pinotes.utils;

import android.graphics.Color;

/* loaded from: classes3.dex */
public class ColorsUtil {
    private static final int COLOR_DARK = 0;
    private static final int COLOR_LIGHT = 1;
    private static final double CONTRAST_THRESHOLD = 100.0d;

    private ColorsUtil() {
    }

    public static double calculateColorLuminance(int i) {
        return (Color.red(i) * 0.2126d) + (Color.green(i) * 0.7152d) + (Color.blue(i) * 0.0722d);
    }

    public static int getContrastedColor(int i) {
        return calculateColorLuminance(i) > CONTRAST_THRESHOLD ? 0 : 1;
    }
}
