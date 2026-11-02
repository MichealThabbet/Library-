package it.feio.android.simplegallery.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.viewpager.widget.ViewPager;
import it.feio.android.simplegallery.models.listeners.OnViewTouchedListener;
import it.feio.android.simplegallery.models.ui.CoverFlowPageTransformer;
import it.feio.android.simplegallery.models.ui.DepthPageTransformer;
import it.feio.android.simplegallery.models.ui.RotationPageTransformer;
import it.feio.android.simplegallery.models.ui.ZoomOutPageTransformer;

/* loaded from: classes2.dex */
public class GalleryViewPager extends ViewPager {
    public static final int PAGE_TRANSFORMER_COVER_FLOW = 2;
    public static final int PAGE_TRANSFORMER_DEPTH = 0;
    public static final int PAGE_TRANSFORMER_ROTATION = 3;
    private static final int PAGE_TRANSFORMER_ROTATION_ANGLE = 160;
    public static final int PAGE_TRANSFORMER_ZOOM_OUT = 1;
    PointF last;
    public TouchImageView mCurrentView;
    private OnViewTouchedListener mOnViewTouchedListener;

    public GalleryViewPager(Context context) {
        super(context);
    }

    public GalleryViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // androidx.viewpager.widget.ViewPager
    protected boolean canScroll(View view, boolean z, int i, int i2, int i3) {
        if (view instanceof TouchImageView) {
            return ((TouchImageView) view).canScrollHorizontallyFroyo(-i);
        }
        return super.canScroll(view, z, i, i2, i3);
    }

    public void setPageTransformer(int i) throws Resources.NotFoundException {
        setClipChildren(false);
        setOffscreenPageLimit(1);
        if (i == 0) {
            super.setPageTransformer(true, new DepthPageTransformer());
            return;
        }
        if (i == 1) {
            super.setPageTransformer(false, new ZoomOutPageTransformer());
        } else if (i == 2) {
            super.setPageTransformer(false, new CoverFlowPageTransformer());
        } else {
            if (i != 3) {
                return;
            }
            super.setPageTransformer(true, new RotationPageTransformer(160));
        }
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        OnViewTouchedListener onViewTouchedListener = this.mOnViewTouchedListener;
        if (onViewTouchedListener != null) {
            onViewTouchedListener.onViewTouchOccurred(motionEvent);
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    public void setOnViewTouchedListener(OnViewTouchedListener onViewTouchedListener) {
        this.mOnViewTouchedListener = onViewTouchedListener;
    }
}
