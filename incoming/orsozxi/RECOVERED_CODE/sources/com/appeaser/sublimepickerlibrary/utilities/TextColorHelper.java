package com.appeaser.sublimepickerlibrary.utilities;

import android.R;
import android.content.res.ColorStateList;

/* loaded from: classes.dex */
public class TextColorHelper {
    public static ColorStateList resolveMaterialHeaderTextColor() {
        return new ColorStateList(new int[][]{new int[]{R.attr.state_activated}, new int[0]}, new int[]{SUtils.COLOR_TEXT_PRIMARY, SUtils.COLOR_TEXT_SECONDARY});
    }
}
