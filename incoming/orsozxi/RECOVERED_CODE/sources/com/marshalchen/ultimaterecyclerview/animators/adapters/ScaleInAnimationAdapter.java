package com.marshalchen.ultimaterecyclerview.animators.adapters;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
public class ScaleInAnimationAdapter extends AnimationAdapter {
    private static final float DEFAULT_SCALE_FROM = 0.5f;
    private final float mFrom;

    public ScaleInAnimationAdapter(RecyclerView.Adapter adapter) {
        this(adapter, 0.5f);
    }

    public ScaleInAnimationAdapter(RecyclerView.Adapter adapter, float f) {
        super(adapter);
        this.mFrom = f;
    }

    @Override // com.marshalchen.ultimaterecyclerview.animators.adapters.AnimationAdapter
    protected Animator[] getAnimators(View view) {
        return new ObjectAnimator[]{ObjectAnimator.ofFloat(view, "scaleX", this.mFrom, 1.0f), ObjectAnimator.ofFloat(view, "scaleY", this.mFrom, 1.0f)};
    }
}
