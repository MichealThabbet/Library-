package de.keyboardsurfer.android.widget.crouton;

import android.R;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/* loaded from: classes2.dex */
public class Style {
    public static final int NOT_SET = -1;
    final int backgroundColorResourceId;
    final int backgroundColorValue;
    final int backgroundDrawableResourceId;
    final Configuration configuration;
    final int gravity;
    final int heightDimensionResId;
    final int heightInPixels;
    final Drawable imageDrawable;
    final int imageResId;
    final ImageView.ScaleType imageScaleType;
    final boolean isTileEnabled;
    final int paddingDimensionResId;
    final int paddingInPixels;
    final int textAppearanceResId;
    final int textColorResourceId;
    final int textColorValue;
    final int textShadowColorResId;
    final float textShadowDx;
    final float textShadowDy;
    final float textShadowRadius;
    final int textSize;
    final int widthDimensionResId;
    final int widthInPixels;
    public static final int holoRedLight = -48060;
    public static final Style ALERT = new Builder().setBackgroundColorValue(holoRedLight).build();
    public static final int holoGreenLight = -6697984;
    public static final Style CONFIRM = new Builder().setBackgroundColorValue(holoGreenLight).build();
    public static final int holoBlueLight = -13388315;
    public static final Style INFO = new Builder().setBackgroundColorValue(holoBlueLight).build();

    private Style(Builder builder) {
        this.configuration = builder.configuration;
        this.backgroundColorResourceId = builder.backgroundColorResourceId;
        this.backgroundDrawableResourceId = builder.backgroundDrawableResourceId;
        this.isTileEnabled = builder.isTileEnabled;
        this.textColorResourceId = builder.textColorResourceId;
        this.textColorValue = builder.textColorValue;
        this.heightInPixels = builder.heightInPixels;
        this.heightDimensionResId = builder.heightDimensionResId;
        this.widthInPixels = builder.widthInPixels;
        this.widthDimensionResId = builder.widthDimensionResId;
        this.gravity = builder.gravity;
        this.imageDrawable = builder.imageDrawable;
        this.textSize = builder.textSize;
        this.textShadowColorResId = builder.textShadowColorResId;
        this.textShadowRadius = builder.textShadowRadius;
        this.textShadowDx = builder.textShadowDx;
        this.textShadowDy = builder.textShadowDy;
        this.textAppearanceResId = builder.textAppearanceResId;
        this.imageResId = builder.imageResId;
        this.imageScaleType = builder.imageScaleType;
        this.paddingInPixels = builder.paddingInPixels;
        this.paddingDimensionResId = builder.paddingDimensionResId;
        this.backgroundColorValue = builder.backgroundColorValue;
    }

    public static class Builder {
        private int backgroundColorResourceId;
        private int backgroundColorValue;
        private int backgroundDrawableResourceId;
        private Configuration configuration;
        private int gravity;
        private int heightDimensionResId;
        private int heightInPixels;
        private Drawable imageDrawable;
        private int imageResId;
        private ImageView.ScaleType imageScaleType;
        private boolean isTileEnabled;
        private int paddingDimensionResId;
        private int paddingInPixels;
        private int textAppearanceResId;
        private int textColorResourceId;
        private int textColorValue;
        private int textShadowColorResId;
        private float textShadowDx;
        private float textShadowDy;
        private float textShadowRadius;
        private int textSize;
        private int widthDimensionResId;
        private int widthInPixels;

        public Builder() {
            this.configuration = Configuration.DEFAULT;
            this.paddingInPixels = 10;
            this.backgroundColorResourceId = R.color.holo_blue_light;
            this.backgroundDrawableResourceId = 0;
            this.backgroundColorValue = -1;
            this.isTileEnabled = false;
            this.textColorResourceId = R.color.white;
            this.textColorValue = -1;
            this.heightInPixels = -2;
            this.widthInPixels = -1;
            this.gravity = 17;
            this.imageDrawable = null;
            this.imageResId = 0;
            this.imageScaleType = ImageView.ScaleType.FIT_XY;
        }

        public Builder(Style style) {
            this.configuration = style.configuration;
            this.backgroundColorValue = style.backgroundColorValue;
            this.backgroundColorResourceId = style.backgroundColorResourceId;
            this.backgroundDrawableResourceId = style.backgroundDrawableResourceId;
            this.isTileEnabled = style.isTileEnabled;
            this.textColorResourceId = style.textColorResourceId;
            this.textColorValue = style.textColorValue;
            this.heightInPixels = style.heightInPixels;
            this.heightDimensionResId = style.heightDimensionResId;
            this.widthInPixels = style.widthInPixels;
            this.widthDimensionResId = style.widthDimensionResId;
            this.gravity = style.gravity;
            this.imageDrawable = style.imageDrawable;
            this.textSize = style.textSize;
            this.textShadowColorResId = style.textShadowColorResId;
            this.textShadowRadius = style.textShadowRadius;
            this.textShadowDx = style.textShadowDx;
            this.textShadowDy = style.textShadowDy;
            this.textAppearanceResId = style.textAppearanceResId;
            this.imageResId = style.imageResId;
            this.imageScaleType = style.imageScaleType;
            this.paddingInPixels = style.paddingInPixels;
            this.paddingDimensionResId = style.paddingDimensionResId;
        }

        public Builder setConfiguration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        public Builder setBackgroundColor(int i) {
            this.backgroundColorResourceId = i;
            return this;
        }

        public Builder setBackgroundColorValue(int i) {
            this.backgroundColorValue = i;
            return this;
        }

        public Builder setBackgroundDrawable(int i) {
            this.backgroundDrawableResourceId = i;
            return this;
        }

        public Builder setHeight(int i) {
            this.heightInPixels = i;
            return this;
        }

        public Builder setHeightDimensionResId(int i) {
            this.heightDimensionResId = i;
            return this;
        }

        public Builder setWidth(int i) {
            this.widthInPixels = i;
            return this;
        }

        public Builder setWidthDimensionResId(int i) {
            this.widthDimensionResId = i;
            return this;
        }

        public Builder setTileEnabled(boolean z) {
            this.isTileEnabled = z;
            return this;
        }

        public Builder setTextColor(int i) {
            this.textColorResourceId = i;
            return this;
        }

        public Builder setTextColorValue(int i) {
            this.textColorValue = i;
            return this;
        }

        public Builder setGravity(int i) {
            this.gravity = i;
            return this;
        }

        public Builder setImageDrawable(Drawable drawable) {
            this.imageDrawable = drawable;
            return this;
        }

        public Builder setImageResource(int i) {
            this.imageResId = i;
            return this;
        }

        public Builder setTextSize(int i) {
            this.textSize = i;
            return this;
        }

        public Builder setTextShadowColor(int i) {
            this.textShadowColorResId = i;
            return this;
        }

        public Builder setTextShadowRadius(float f) {
            this.textShadowRadius = f;
            return this;
        }

        public Builder setTextShadowDx(float f) {
            this.textShadowDx = f;
            return this;
        }

        public Builder setTextShadowDy(float f) {
            this.textShadowDy = f;
            return this;
        }

        public Builder setTextAppearance(int i) {
            this.textAppearanceResId = i;
            return this;
        }

        public Builder setImageScaleType(ImageView.ScaleType scaleType) {
            this.imageScaleType = scaleType;
            return this;
        }

        public Builder setPaddingInPixels(int i) {
            this.paddingInPixels = i;
            return this;
        }

        public Builder setPaddingDimensionResId(int i) {
            this.paddingDimensionResId = i;
            return this;
        }

        public Style build() {
            return new Style(this);
        }
    }

    public String toString() {
        return "Style{configuration=" + this.configuration + ", backgroundColorResourceId=" + this.backgroundColorResourceId + ", backgroundDrawableResourceId=" + this.backgroundDrawableResourceId + ", backgroundColorValue=" + this.backgroundColorValue + ", isTileEnabled=" + this.isTileEnabled + ", textColorResourceId=" + this.textColorResourceId + ", textColorValue=" + this.textColorValue + ", heightInPixels=" + this.heightInPixels + ", heightDimensionResId=" + this.heightDimensionResId + ", widthInPixels=" + this.widthInPixels + ", widthDimensionResId=" + this.widthDimensionResId + ", gravity=" + this.gravity + ", imageDrawable=" + this.imageDrawable + ", imageResId=" + this.imageResId + ", imageScaleType=" + this.imageScaleType + ", textSize=" + this.textSize + ", textShadowColorResId=" + this.textShadowColorResId + ", textShadowRadius=" + this.textShadowRadius + ", textShadowDy=" + this.textShadowDy + ", textShadowDx=" + this.textShadowDx + ", textAppearanceResId=" + this.textAppearanceResId + ", paddingInPixels=" + this.paddingInPixels + ", paddingDimensionResId=" + this.paddingDimensionResId + '}';
    }
}
