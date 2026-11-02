package com.app.piapostolos.Youtube.Beans;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes.dex */
public class Thumbnails {

    @SerializedName("default")
    private ThumbItem defaultItem;

    @SerializedName("high")
    private ThumbItem high;
    private ThumbItem medium;

    public ThumbItem getDefaultItem() {
        return this.defaultItem;
    }

    public void setDefaultItem(ThumbItem thumbItem) {
        this.defaultItem = thumbItem;
    }

    public ThumbItem getMedium() {
        return this.medium;
    }

    public void setMedium(ThumbItem thumbItem) {
        this.medium = thumbItem;
    }

    public ThumbItem getHigh() {
        return this.high;
    }

    public void setHigh(ThumbItem thumbItem) {
        this.high = thumbItem;
    }
}
