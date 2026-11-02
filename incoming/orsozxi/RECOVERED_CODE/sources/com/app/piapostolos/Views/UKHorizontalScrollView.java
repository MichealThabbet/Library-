package com.app.piapostolos.Views;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;

/* loaded from: classes.dex */
public class UKHorizontalScrollView extends HorizontalScrollView {
    private boolean menuOpen;

    public interface SizeCallback {
        void getViewSize(int i, int i2, int i3, int[] iArr);

        void onGlobalLayout();
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override // android.widget.HorizontalScrollView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public void setMenuOpen(boolean z) {
        this.menuOpen = z;
    }

    public boolean isMenuOpen() {
        return this.menuOpen;
    }

    public UKHorizontalScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public UKHorizontalScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public UKHorizontalScrollView(Context context) {
        super(context);
        init(context);
    }

    void init(Context context) {
        setHorizontalFadingEdgeEnabled(false);
        setVerticalFadingEdgeEnabled(false);
    }

    public void initViews(View[] viewArr, int i, SizeCallback sizeCallback) {
        ViewGroup viewGroup = (ViewGroup) getChildAt(0);
        for (int i2 = 0; i2 < viewArr.length; i2++) {
            viewArr[i2].setVisibility(4);
            viewGroup.addView(viewArr[i2]);
        }
        getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener(viewGroup, viewArr, i, sizeCallback));
    }

    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        View[] children;
        ViewGroup parent;
        int scrollToViewIdx;
        int scrollToViewPos = 0;
        SizeCallback sizeCallback;

        public MyOnGlobalLayoutListener(ViewGroup viewGroup, View[] viewArr, int i, SizeCallback sizeCallback) {
            this.parent = viewGroup;
            this.children = viewArr;
            this.scrollToViewIdx = i;
            this.sizeCallback = sizeCallback;
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            final UKHorizontalScrollView uKHorizontalScrollView = UKHorizontalScrollView.this;
            uKHorizontalScrollView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            this.sizeCallback.onGlobalLayout();
            this.parent.removeViewsInLayout(0, this.children.length);
            int measuredWidth = uKHorizontalScrollView.getMeasuredWidth();
            int measuredHeight = uKHorizontalScrollView.getMeasuredHeight();
            int[] iArr = new int[2];
            this.scrollToViewPos = 0;
            for (int i = 0; i < this.children.length; i++) {
                this.sizeCallback.getViewSize(i, measuredWidth, measuredHeight, iArr);
                this.children[i].setVisibility(0);
                this.parent.addView(this.children[i], iArr[0], iArr[1]);
                if (i < this.scrollToViewIdx) {
                    this.scrollToViewPos += iArr[0];
                }
            }
            new Handler().post(new Runnable() { // from class: com.app.piapostolos.Views.UKHorizontalScrollView.MyOnGlobalLayoutListener.1
                @Override // java.lang.Runnable
                public void run() {
                    uKHorizontalScrollView.scrollBy(MyOnGlobalLayoutListener.this.scrollToViewPos, 0);
                }
            });
        }
    }
}
