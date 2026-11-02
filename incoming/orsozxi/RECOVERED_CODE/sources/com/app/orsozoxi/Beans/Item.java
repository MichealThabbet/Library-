package com.app.orsozoxi.Beans;

import android.graphics.Bitmap;

/* loaded from: classes.dex */
public class Item {
    Bitmap image;
    String title;

    public Item(Bitmap bitmap, String str) {
        this.image = bitmap;
        this.title = str;
    }

    public Bitmap getImage() {
        return this.image;
    }

    public void setImage(Bitmap bitmap) {
        this.image = bitmap;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }
}
