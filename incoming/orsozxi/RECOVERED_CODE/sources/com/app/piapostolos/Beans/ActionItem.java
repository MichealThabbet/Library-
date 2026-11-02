package com.app.piapostolos.Beans;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/* loaded from: classes.dex */
public class ActionItem {
    private int actionId;
    private Drawable icon;
    private boolean selected;
    private boolean sticky;
    private Bitmap thumb;
    private String title;

    public ActionItem(int i, String str, Drawable drawable) {
        this.actionId = -1;
        this.title = str;
        this.icon = drawable;
        this.actionId = i;
    }

    public ActionItem() {
        this(-1, null, null);
    }

    public ActionItem(int i, String str) {
        this(i, str, null);
    }

    public ActionItem(Drawable drawable) {
        this(-1, null, drawable);
    }

    public ActionItem(int i, Drawable drawable) {
        this(i, null, drawable);
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setIcon(Drawable drawable) {
        this.icon = drawable;
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public void setActionId(int i) {
        this.actionId = i;
    }

    public int getActionId() {
        return this.actionId;
    }

    public void setSticky(boolean z) {
        this.sticky = z;
    }

    public boolean isSticky() {
        return this.sticky;
    }

    public void setSelected(boolean z) {
        this.selected = z;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setThumb(Bitmap bitmap) {
        this.thumb = bitmap;
    }

    public Bitmap getThumb() {
        return this.thumb;
    }
}
