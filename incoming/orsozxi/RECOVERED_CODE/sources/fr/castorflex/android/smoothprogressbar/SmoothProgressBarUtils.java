package fr.castorflex.android.smoothprogressbar;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;

/* loaded from: classes2.dex */
public final class SmoothProgressBarUtils {
    private SmoothProgressBarUtils() {
    }

    public static Drawable generateDrawableWithColors(int[] iArr, float f) {
        if (iArr == null || iArr.length == 0) {
            return null;
        }
        return new ShapeDrawable(new ColorsShape(f, iArr));
    }

    static void checkSpeed(float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("Speed must be >= 0");
        }
    }

    static void checkColors(int[] iArr) {
        if (iArr == null || iArr.length == 0) {
            throw new IllegalArgumentException("You must provide at least 1 color");
        }
    }

    static void checkAngle(int i) {
        if (i < 0 || i > 360) {
            throw new IllegalArgumentException(String.format("Illegal angle %d: must be >=0 and <= 360", Integer.valueOf(i)));
        }
    }

    static void checkPositiveOrZero(float f, String str) {
        if (f < 0.0f) {
            throw new IllegalArgumentException(String.format("%s %d must be positive", str, Float.valueOf(f)));
        }
    }

    static void checkPositive(int i, String str) {
        if (i <= 0) {
            throw new IllegalArgumentException(String.format("%s must not be null", str));
        }
    }

    static void checkNotNull(Object obj, String str) {
        if (obj == null) {
            throw new IllegalArgumentException(String.format("%s must be not null", str));
        }
    }
}
