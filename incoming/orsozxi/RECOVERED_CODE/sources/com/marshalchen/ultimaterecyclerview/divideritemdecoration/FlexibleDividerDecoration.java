package com.marshalchen.ultimaterecyclerview.divideritemdecoration;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
public abstract class FlexibleDividerDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = {R.attr.listDivider};
    private static final int DEFAULT_SIZE = 2;
    protected ColorProvider mColorProvider;
    protected DividerType mDividerType;
    protected DrawableProvider mDrawableProvider;
    private Paint mPaint;
    protected PaintProvider mPaintProvider;
    protected boolean mShowLastDivider;
    protected SizeProvider mSizeProvider;
    protected VisibilityProvider mVisibilityProvider;

    public interface ColorProvider {
        int dividerColor(int i, RecyclerView recyclerView);
    }

    protected enum DividerType {
        DRAWABLE,
        PAINT,
        COLOR
    }

    public interface DrawableProvider {
        Drawable drawableProvider(int i, RecyclerView recyclerView);
    }

    public interface PaintProvider {
        Paint dividerPaint(int i, RecyclerView recyclerView);
    }

    public interface SizeProvider {
        int dividerSize(int i, RecyclerView recyclerView);
    }

    public interface VisibilityProvider {
        boolean shouldHideDivider(int i, RecyclerView recyclerView);
    }

    protected abstract Rect getDividerBound(int i, RecyclerView recyclerView, View view);

    protected abstract void setItemOffsets(Rect rect, int i, RecyclerView recyclerView);

    protected FlexibleDividerDecoration(Builder builder) {
        this.mDividerType = DividerType.DRAWABLE;
        if (builder.mPaintProvider != null) {
            this.mDividerType = DividerType.PAINT;
            this.mPaintProvider = builder.mPaintProvider;
        } else if (builder.mColorProvider != null) {
            this.mDividerType = DividerType.COLOR;
            this.mColorProvider = builder.mColorProvider;
            this.mPaint = new Paint();
            setSizeProvider(builder);
        } else {
            this.mDividerType = DividerType.DRAWABLE;
            if (builder.mDrawableProvider == null) {
                TypedArray typedArrayObtainStyledAttributes = builder.mContext.obtainStyledAttributes(ATTRS);
                final Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0);
                typedArrayObtainStyledAttributes.recycle();
                this.mDrawableProvider = new DrawableProvider() { // from class: com.marshalchen.ultimaterecyclerview.divideritemdecoration.FlexibleDividerDecoration.1
                    @Override // com.marshalchen.ultimaterecyclerview.divideritemdecoration.FlexibleDividerDecoration.DrawableProvider
                    public Drawable drawableProvider(int i, RecyclerView recyclerView) {
                        return drawable;
                    }
                };
            } else {
                this.mDrawableProvider = builder.mDrawableProvider;
            }
            this.mSizeProvider = builder.mSizeProvider;
        }
        this.mVisibilityProvider = builder.mVisibilityProvider;
        this.mShowLastDivider = builder.mShowLastDivider;
    }

    private void setSizeProvider(Builder builder) {
        SizeProvider sizeProvider = builder.mSizeProvider;
        this.mSizeProvider = sizeProvider;
        if (sizeProvider == null) {
            this.mSizeProvider = new SizeProvider() { // from class: com.marshalchen.ultimaterecyclerview.divideritemdecoration.FlexibleDividerDecoration.2
                @Override // com.marshalchen.ultimaterecyclerview.divideritemdecoration.FlexibleDividerDecoration.SizeProvider
                public int dividerSize(int i, RecyclerView recyclerView) {
                    return 2;
                }
            };
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        int childCount = this.mShowLastDivider ? recyclerView.getChildCount() : recyclerView.getChildCount() - 1;
        int i = -1;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = recyclerView.getChildAt(i2);
            int childAdapterPosition = recyclerView.getChildAdapterPosition(childAt);
            if (childAdapterPosition >= i) {
                if (ViewCompat.getAlpha(childAt) >= 1.0f && !this.mVisibilityProvider.shouldHideDivider(childAdapterPosition, recyclerView)) {
                    Rect dividerBound = getDividerBound(childAdapterPosition, recyclerView, childAt);
                    int i3 = AnonymousClass3.$SwitchMap$com$marshalchen$ultimaterecyclerview$divideritemdecoration$FlexibleDividerDecoration$DividerType[this.mDividerType.ordinal()];
                    if (i3 == 1) {
                        Drawable drawableDrawableProvider = this.mDrawableProvider.drawableProvider(childAdapterPosition, recyclerView);
                        drawableDrawableProvider.setBounds(dividerBound);
                        drawableDrawableProvider.draw(canvas);
                    } else if (i3 == 2) {
                        this.mPaint = this.mPaintProvider.dividerPaint(childAdapterPosition, recyclerView);
                        canvas.drawLine(dividerBound.left, dividerBound.top, dividerBound.right, dividerBound.bottom, this.mPaint);
                    } else if (i3 == 3) {
                        this.mPaint.setColor(this.mColorProvider.dividerColor(childAdapterPosition, recyclerView));
                        this.mPaint.setStrokeWidth(this.mSizeProvider.dividerSize(childAdapterPosition, recyclerView));
                        canvas.drawLine(dividerBound.left, dividerBound.top, dividerBound.right, dividerBound.bottom, this.mPaint);
                    }
                }
                i = childAdapterPosition;
            }
        }
    }

    /* renamed from: com.marshalchen.ultimaterecyclerview.divideritemdecoration.FlexibleDividerDecoration$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$marshalchen$ultimaterecyclerview$divideritemdecoration$FlexibleDividerDecoration$DividerType;

        static {
            int[] iArr = new int[DividerType.values().length];
            $SwitchMap$com$marshalchen$ultimaterecyclerview$divideritemdecoration$FlexibleDividerDecoration$DividerType = iArr;
            try {
                iArr[DividerType.DRAWABLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$marshalchen$ultimaterecyclerview$divideritemdecoration$FlexibleDividerDecoration$DividerType[DividerType.PAINT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$marshalchen$ultimaterecyclerview$divideritemdecoration$FlexibleDividerDecoration$DividerType[DividerType.COLOR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        setItemOffsets(rect, recyclerView.getChildAdapterPosition(view), recyclerView);
    }

    public static class Builder<T extends Builder> {
        private ColorProvider mColorProvider;
        private Context mContext;
        private DrawableProvider mDrawableProvider;
        private PaintProvider mPaintProvider;
        protected Resources mResources;
        private SizeProvider mSizeProvider;
        private VisibilityProvider mVisibilityProvider = new VisibilityProvider() { // from class: com.marshalchen.ultimaterecyclerview.divideritemdecoration.FlexibleDividerDecoration.Builder.1
            @Override // com.marshalchen.ultimaterecyclerview.divideritemdecoration.FlexibleDividerDecoration.VisibilityProvider
            public boolean shouldHideDivider(int i, RecyclerView recyclerView) {
                return false;
            }
        };
        private boolean mShowLastDivider = false;

        public Builder(Context context) {
            this.mContext = context;
            this.mResources = context.getResources();
        }

        public T paint(final Paint paint) {
            return (T) paintProvider(new PaintProvider() { // from class: com.marshalchen.ultimaterecyclerview.divideritemdecoration.FlexibleDividerDecoration.Builder.2
                @Override // com.marshalchen.ultimaterecyclerview.divideritemdecoration.FlexibleDividerDecoration.PaintProvider
                public Paint dividerPaint(int i, RecyclerView recyclerView) {
                    return paint;
                }
            });
        }

        public T paintProvider(PaintProvider paintProvider) {
            this.mPaintProvider = paintProvider;
            return this;
        }

        public T color(final int i) {
            return (T) colorProvider(new ColorProvider() { // from class: com.marshalchen.ultimaterecyclerview.divideritemdecoration.FlexibleDividerDecoration.Builder.3
                @Override // com.marshalchen.ultimaterecyclerview.divideritemdecoration.FlexibleDividerDecoration.ColorProvider
                public int dividerColor(int i2, RecyclerView recyclerView) {
                    return i;
                }
            });
        }

        public T colorResId(int i) {
            return (T) color(this.mResources.getColor(i));
        }

        public T colorProvider(ColorProvider colorProvider) {
            this.mColorProvider = colorProvider;
            return this;
        }

        public T drawable(int i) {
            return (T) drawable(this.mResources.getDrawable(i));
        }

        public T drawable(final Drawable drawable) {
            return (T) drawableProvider(new DrawableProvider() { // from class: com.marshalchen.ultimaterecyclerview.divideritemdecoration.FlexibleDividerDecoration.Builder.4
                @Override // com.marshalchen.ultimaterecyclerview.divideritemdecoration.FlexibleDividerDecoration.DrawableProvider
                public Drawable drawableProvider(int i, RecyclerView recyclerView) {
                    return drawable;
                }
            });
        }

        public T drawableProvider(DrawableProvider drawableProvider) {
            this.mDrawableProvider = drawableProvider;
            return this;
        }

        public T size(final int i) {
            return (T) sizeProvider(new SizeProvider() { // from class: com.marshalchen.ultimaterecyclerview.divideritemdecoration.FlexibleDividerDecoration.Builder.5
                @Override // com.marshalchen.ultimaterecyclerview.divideritemdecoration.FlexibleDividerDecoration.SizeProvider
                public int dividerSize(int i2, RecyclerView recyclerView) {
                    return i;
                }
            });
        }

        public T sizeResId(int i) {
            return (T) size(this.mResources.getDimensionPixelSize(i));
        }

        public T sizeProvider(SizeProvider sizeProvider) {
            this.mSizeProvider = sizeProvider;
            return this;
        }

        public T visibilityProvider(VisibilityProvider visibilityProvider) {
            this.mVisibilityProvider = visibilityProvider;
            return this;
        }

        public T showLastDivider() {
            this.mShowLastDivider = true;
            return this;
        }

        protected void checkBuilderParams() {
            if (this.mPaintProvider != null) {
                if (this.mColorProvider != null) {
                    throw new IllegalArgumentException("Use setColor method of Paint class to specify line color. Do not provider ColorProvider if you set PaintProvider.");
                }
                if (this.mSizeProvider != null) {
                    throw new IllegalArgumentException("Use setStrokeWidth method of Paint class to specify line size. Do not provider SizeProvider if you set PaintProvider.");
                }
            }
        }
    }
}
