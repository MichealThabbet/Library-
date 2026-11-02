package com.marshalchen.ultimaterecyclerview.divideritemdecoration;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.marshalchen.ultimaterecyclerview.divideritemdecoration.FlexibleDividerDecoration;

/* loaded from: classes2.dex */
public class VerticalDividerItemDecoration extends FlexibleDividerDecoration {
    private MarginProvider mMarginProvider;

    public interface MarginProvider {
        int dividerBottomMargin(int i, RecyclerView recyclerView);

        int dividerTopMargin(int i, RecyclerView recyclerView);
    }

    protected VerticalDividerItemDecoration(Builder builder) {
        super(builder);
        this.mMarginProvider = builder.mMarginProvider;
    }

    @Override // com.marshalchen.ultimaterecyclerview.divideritemdecoration.FlexibleDividerDecoration
    protected Rect getDividerBound(int i, RecyclerView recyclerView, View view) {
        Rect rect = new Rect(0, 0, 0, 0);
        int translationX = (int) ViewCompat.getTranslationX(view);
        int translationY = (int) ViewCompat.getTranslationY(view);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        rect.top = recyclerView.getPaddingTop() + this.mMarginProvider.dividerTopMargin(i, recyclerView) + translationY;
        rect.bottom = ((recyclerView.getHeight() - recyclerView.getPaddingBottom()) - this.mMarginProvider.dividerBottomMargin(i, recyclerView)) + translationY;
        int dividerSize = getDividerSize(i, recyclerView);
        if (this.mDividerType == FlexibleDividerDecoration.DividerType.DRAWABLE) {
            rect.left = view.getRight() + layoutParams.leftMargin + translationX;
            rect.right = rect.left + dividerSize;
        } else {
            rect.left = view.getRight() + layoutParams.leftMargin + (dividerSize / 2) + translationX;
            rect.right = rect.left;
        }
        return rect;
    }

    @Override // com.marshalchen.ultimaterecyclerview.divideritemdecoration.FlexibleDividerDecoration
    protected void setItemOffsets(Rect rect, int i, RecyclerView recyclerView) {
        rect.set(0, 0, getDividerSize(i, recyclerView), 0);
    }

    private int getDividerSize(int i, RecyclerView recyclerView) {
        if (this.mPaintProvider != null) {
            return (int) this.mPaintProvider.dividerPaint(i, recyclerView).getStrokeWidth();
        }
        if (this.mSizeProvider != null) {
            return this.mSizeProvider.dividerSize(i, recyclerView);
        }
        if (this.mDrawableProvider != null) {
            return this.mDrawableProvider.drawableProvider(i, recyclerView).getIntrinsicWidth();
        }
        throw new RuntimeException("failed to get size");
    }

    public static class Builder extends FlexibleDividerDecoration.Builder<Builder> {
        private MarginProvider mMarginProvider;

        public Builder(Context context) {
            super(context);
            this.mMarginProvider = new MarginProvider() { // from class: com.marshalchen.ultimaterecyclerview.divideritemdecoration.VerticalDividerItemDecoration.Builder.1
                @Override // com.marshalchen.ultimaterecyclerview.divideritemdecoration.VerticalDividerItemDecoration.MarginProvider
                public int dividerBottomMargin(int i, RecyclerView recyclerView) {
                    return 0;
                }

                @Override // com.marshalchen.ultimaterecyclerview.divideritemdecoration.VerticalDividerItemDecoration.MarginProvider
                public int dividerTopMargin(int i, RecyclerView recyclerView) {
                    return 0;
                }
            };
        }

        public Builder margin(final int i, final int i2) {
            return marginProvider(new MarginProvider() { // from class: com.marshalchen.ultimaterecyclerview.divideritemdecoration.VerticalDividerItemDecoration.Builder.2
                @Override // com.marshalchen.ultimaterecyclerview.divideritemdecoration.VerticalDividerItemDecoration.MarginProvider
                public int dividerTopMargin(int i3, RecyclerView recyclerView) {
                    return i;
                }

                @Override // com.marshalchen.ultimaterecyclerview.divideritemdecoration.VerticalDividerItemDecoration.MarginProvider
                public int dividerBottomMargin(int i3, RecyclerView recyclerView) {
                    return i2;
                }
            });
        }

        public Builder margin(int i) {
            return margin(i, i);
        }

        public Builder marginResId(int i, int i2) {
            return margin(this.mResources.getDimensionPixelSize(i), this.mResources.getDimensionPixelSize(i2));
        }

        public Builder marginResId(int i) {
            return marginResId(i, i);
        }

        public Builder marginProvider(MarginProvider marginProvider) {
            this.mMarginProvider = marginProvider;
            return this;
        }

        public VerticalDividerItemDecoration build() {
            checkBuilderParams();
            return new VerticalDividerItemDecoration(this);
        }
    }
}
