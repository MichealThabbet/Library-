package com.aurelhubert.ahbottomnavigation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

/* loaded from: classes.dex */
public class AHBottomNavigationItem {
    private int color;
    private int colorRes;
    private Drawable drawable;
    private int drawableRes;
    private String title;
    private int titleRes;

    public AHBottomNavigationItem(String str, int i) {
        this.title = "";
        this.color = -7829368;
        this.titleRes = 0;
        this.drawableRes = 0;
        this.colorRes = 0;
        this.title = str;
        this.drawableRes = i;
    }

    @Deprecated
    public AHBottomNavigationItem(String str, int i, int i2) {
        this.title = "";
        this.color = -7829368;
        this.titleRes = 0;
        this.drawableRes = 0;
        this.colorRes = 0;
        this.title = str;
        this.drawableRes = i;
        this.color = i2;
    }

    public AHBottomNavigationItem(int i, int i2, int i3) {
        this.title = "";
        this.color = -7829368;
        this.titleRes = 0;
        this.drawableRes = 0;
        this.colorRes = 0;
        this.titleRes = i;
        this.drawableRes = i2;
        this.colorRes = i3;
    }

    public AHBottomNavigationItem(String str, Drawable drawable) {
        this.title = "";
        this.color = -7829368;
        this.titleRes = 0;
        this.drawableRes = 0;
        this.colorRes = 0;
        this.title = str;
        this.drawable = drawable;
    }

    public AHBottomNavigationItem(String str, Drawable drawable, int i) {
        this.title = "";
        this.color = -7829368;
        this.titleRes = 0;
        this.drawableRes = 0;
        this.colorRes = 0;
        this.title = str;
        this.drawable = drawable;
        this.color = i;
    }

    public String getTitle(Context context) {
        int i = this.titleRes;
        if (i != 0) {
            return context.getString(i);
        }
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
        this.titleRes = 0;
    }

    public void setTitle(int i) {
        this.titleRes = i;
        this.title = "";
    }

    public int getColor(Context context) {
        int i = this.colorRes;
        if (i != 0) {
            return ContextCompat.getColor(context, i);
        }
        return this.color;
    }

    public void setColor(int i) {
        this.color = i;
        this.colorRes = 0;
    }

    public void setColorRes(int i) {
        this.colorRes = i;
        this.color = 0;
    }

    public Drawable getDrawable(Context context) {
        if (this.drawableRes != 0) {
            try {
                return VectorDrawableCompat.create(context.getResources(), this.drawableRes, null);
            } catch (Resources.NotFoundException unused) {
                return ContextCompat.getDrawable(context, this.drawableRes);
            }
        }
        return this.drawable;
    }

    public void setDrawable(int i) {
        this.drawableRes = i;
        this.drawable = null;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
        this.drawableRes = 0;
    }
}
