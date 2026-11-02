package it.feio.android.simplegallery.models.ui;

import android.view.View;
import androidx.viewpager.widget.ViewPager;

/* loaded from: classes2.dex */
public class CoverFlowPageTransformer implements ViewPager.PageTransformer {
    @Override // androidx.viewpager.widget.ViewPager.PageTransformer
    public void transformPage(View view, float f) {
        view.setRotationY(f * (-30.0f));
    }
}
