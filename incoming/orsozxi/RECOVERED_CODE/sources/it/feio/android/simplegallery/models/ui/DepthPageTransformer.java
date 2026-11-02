package it.feio.android.simplegallery.models.ui;

import android.view.View;
import androidx.viewpager.widget.ViewPager;

/* loaded from: classes2.dex */
public class DepthPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;

    @Override // androidx.viewpager.widget.ViewPager.PageTransformer
    public void transformPage(View view, float f) {
        int width = view.getWidth();
        if (f < -1.0f) {
            view.setAlpha(0.0f);
            return;
        }
        if (f <= 0.0f) {
            view.setAlpha(1.0f);
            view.setTranslationX(0.0f);
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
            return;
        }
        if (f <= 1.0f) {
            view.setAlpha(1.0f - f);
            view.setTranslationX(width * (-f));
            float fAbs = ((1.0f - Math.abs(f)) * 0.25f) + 0.75f;
            view.setScaleX(fAbs);
            view.setScaleY(fAbs);
            return;
        }
        view.setAlpha(0.0f);
    }
}
