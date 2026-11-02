package com.marshalchen.ultimaterecyclerview.animators.adapters;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
public class SlideInLeftAnimationAdapter extends AnimationAdapter {
    public SlideInLeftAnimationAdapter(RecyclerView.Adapter adapter) {
        super(adapter);
    }

    @Override // com.marshalchen.ultimaterecyclerview.animators.adapters.AnimationAdapter
    protected Animator[] getAnimators(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "translationX", -view.getRootView().getWidth(), 0.0f)};
    }
}
