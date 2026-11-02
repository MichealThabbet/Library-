package com.app.piapostolos.Beans;

import android.widget.ImageView;
import android.widget.RelativeLayout;

/* loaded from: classes.dex */
public class LeftSliderMenu {
    private int defaultImageId;
    private ImageView itemImageView;
    private RelativeLayout itemLayout;
    private int selectedImageId;

    public int getSelectedImageId() {
        return this.selectedImageId;
    }

    public void setSelectedImageId(int i) {
        this.selectedImageId = i;
    }

    public int getImageId() {
        return this.defaultImageId;
    }

    public void setImageId(int i) {
        this.defaultImageId = i;
    }

    public RelativeLayout getItemLayout() {
        return this.itemLayout;
    }

    public void setItemLayout(RelativeLayout relativeLayout) {
        this.itemLayout = relativeLayout;
    }

    public ImageView getItemImageView() {
        return this.itemImageView;
    }

    public void setItemImageView(ImageView imageView) {
        this.itemImageView = imageView;
    }
}
