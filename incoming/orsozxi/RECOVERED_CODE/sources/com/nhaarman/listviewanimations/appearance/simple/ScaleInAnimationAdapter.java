package com.nhaarman.listviewanimations.appearance.simple;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

/* loaded from: classes2.dex */
public class ScaleInAnimationAdapter extends AnimationAdapter {
    private static final float DEFAULT_SCALE_FROM = 0.8f;
    private static final String SCALE_X = "scaleX";
    private static final String SCALE_Y = "scaleY";
    private final float mScaleFrom;

    public ScaleInAnimationAdapter(BaseAdapter baseAdapter) {
        this(baseAdapter, DEFAULT_SCALE_FROM);
    }

    public ScaleInAnimationAdapter(BaseAdapter baseAdapter, float f) {
        super(baseAdapter);
        this.mScaleFrom = f;
    }

    @Override // com.nhaarman.listviewanimations.appearance.AnimationAdapter
    public Animator[] getAnimators(ViewGroup viewGroup, View view) {
        return new ObjectAnimator[]{ObjectAnimator.ofFloat(view, SCALE_X, this.mScaleFrom, 1.0f), ObjectAnimator.ofFloat(view, SCALE_Y, this.mScaleFrom, 1.0f)};
    }
}
