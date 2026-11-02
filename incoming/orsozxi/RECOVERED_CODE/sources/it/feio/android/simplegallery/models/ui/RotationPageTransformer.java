package it.feio.android.simplegallery.models.ui;

import android.view.View;
import androidx.viewpager.widget.ViewPager;

/* loaded from: classes2.dex */
public class RotationPageTransformer implements ViewPager.PageTransformer {
    private int degrees;
    private float distanceToCentreFactor;
    private float minAlpha;

    public RotationPageTransformer(int i) {
        this(i, 0.7f);
    }

    public RotationPageTransformer(int i, float f) {
        this.degrees = i;
        this.distanceToCentreFactor = ((float) Math.tan(Math.toRadians(i / 2))) / 2.0f;
        this.minAlpha = f;
    }

    @Override // androidx.viewpager.widget.ViewPager.PageTransformer
    public void transformPage(View view, float f) {
        int width = view.getWidth();
        int height = view.getHeight();
        float f2 = width;
        view.setPivotX(f2 / 2.0f);
        view.setPivotY(height + (this.distanceToCentreFactor * f2));
        if (f < -1.0f) {
            view.setRotation(0.0f);
            view.setAlpha(0.0f);
        } else if (f <= 1.0f) {
            view.setTranslationX((-f) * f2);
            view.setRotation((180 - this.degrees) * f);
            view.setAlpha(Math.max(this.minAlpha, 1.0f - (Math.abs(f) / 3.0f)));
        } else {
            view.setRotation(0.0f);
            view.setAlpha(0.0f);
        }
    }
}
