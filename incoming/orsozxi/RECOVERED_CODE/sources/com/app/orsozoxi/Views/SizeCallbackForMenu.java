package com.app.orsozoxi.Views;

import android.view.View;
import com.app.orsozoxi.Views.UKHorizontalScrollView;

/* loaded from: classes.dex */
public class SizeCallbackForMenu implements UKHorizontalScrollView.SizeCallback {
    private View btnSlide;
    private int btnWidth;

    public SizeCallbackForMenu(View view) {
        this.btnSlide = view;
    }

    @Override // com.app.orsozoxi.Views.UKHorizontalScrollView.SizeCallback
    public void onGlobalLayout() {
        this.btnWidth = this.btnSlide.getMeasuredWidth();
    }

    @Override // com.app.orsozoxi.Views.UKHorizontalScrollView.SizeCallback
    public void getViewSize(int i, int i2, int i3, int[] iArr) {
        iArr[0] = i2;
        iArr[1] = i3;
        if (i == 0) {
            iArr[0] = i2 - (this.btnWidth + 32);
        }
    }
}
