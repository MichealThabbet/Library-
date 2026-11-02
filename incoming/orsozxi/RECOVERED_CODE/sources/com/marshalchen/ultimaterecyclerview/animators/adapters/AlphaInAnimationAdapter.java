package com.marshalchen.ultimaterecyclerview.animators.adapters;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
public class AlphaInAnimationAdapter extends AnimationAdapter {
    private static final float DEFAULT_ALPHA_FROM = 0.0f;
    private final float mFrom;

    public AlphaInAnimationAdapter(RecyclerView.Adapter adapter) {
        this(adapter, 0.0f);
    }

    public AlphaInAnimationAdapter(RecyclerView.Adapter adapter, float f) {
        super(adapter);
        this.mFrom = f;
    }

    @Override // com.marshalchen.ultimaterecyclerview.animators.adapters.AnimationAdapter
    protected Animator[] getAnimators(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "alpha", this.mFrom, 1.0f)};
    }
}
