package com.afollestad.materialdialogs.internal;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import com.afollestad.materialdialogs.GravityEnum;

/* loaded from: classes.dex */
public class ThemeSingleton {
    private static ThemeSingleton singleton;
    public boolean darkTheme = false;
    public int titleColor = 0;
    public int contentColor = 0;
    public ColorStateList positiveColor = null;
    public ColorStateList neutralColor = null;
    public ColorStateList negativeColor = null;
    public int widgetColor = 0;
    public int itemColor = 0;
    public Drawable icon = null;
    public int backgroundColor = 0;
    public int dividerColor = 0;
    public ColorStateList linkColor = null;
    public int listSelector = 0;
    public int btnSelectorStacked = 0;
    public int btnSelectorPositive = 0;
    public int btnSelectorNeutral = 0;
    public int btnSelectorNegative = 0;
    public GravityEnum titleGravity = GravityEnum.START;
    public GravityEnum contentGravity = GravityEnum.START;
    public GravityEnum btnStackedGravity = GravityEnum.END;
    public GravityEnum itemsGravity = GravityEnum.START;
    public GravityEnum buttonsGravity = GravityEnum.START;

    public static ThemeSingleton get(boolean z) {
        if (singleton == null && z) {
            singleton = new ThemeSingleton();
        }
        return singleton;
    }

    public static ThemeSingleton get() {
        return get(true);
    }
}
