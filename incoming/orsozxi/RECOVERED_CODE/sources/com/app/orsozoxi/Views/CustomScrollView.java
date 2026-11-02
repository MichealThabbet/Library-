package com.app.orsozoxi.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
import androidx.viewpager.widget.ViewPager;

/* loaded from: classes.dex */
public class CustomScrollView extends ScrollView {
    ViewPager parentPager;
    float touchX;
    float touchY;

    public void setParentPager(ViewPager viewPager) {
        this.parentPager = viewPager;
    }

    public CustomScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.touchX = 0.0f;
        this.touchY = 0.0f;
    }

    public CustomScrollView(Context context) {
        super(context);
        this.touchX = 0.0f;
        this.touchY = 0.0f;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0031  */
    @Override // android.widget.ScrollView, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onTouchEvent(android.view.MotionEvent r3) {
        /*
            r2 = this;
            int r0 = r3.getActionMasked()
            if (r0 == 0) goto L3b
            r1 = 1
            if (r0 == r1) goto L31
            r1 = 2
            if (r0 == r1) goto L10
            r1 = 3
            if (r0 == r1) goto L31
            goto L36
        L10:
            float r0 = r2.touchX
            float r1 = r3.getX()
            float r0 = r0 - r1
            float r0 = java.lang.Math.abs(r0)
            r1 = 1109393408(0x42200000, float:40.0)
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 >= 0) goto L26
            boolean r3 = super.onTouchEvent(r3)
            return r3
        L26:
            androidx.viewpager.widget.ViewPager r0 = r2.parentPager
            if (r0 != 0) goto L2c
            r3 = 0
            return r3
        L2c:
            boolean r3 = r0.onTouchEvent(r3)
            return r3
        L31:
            r0 = 0
            r2.touchX = r0
            r2.touchY = r0
        L36:
            boolean r3 = super.onTouchEvent(r3)
            return r3
        L3b:
            float r0 = r3.getX()
            r2.touchX = r0
            float r0 = r3.getY()
            r2.touchY = r0
            boolean r3 = super.onTouchEvent(r3)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.orsozoxi.Views.CustomScrollView.onTouchEvent(android.view.MotionEvent):boolean");
    }
}
