package com.nhaarman.listviewanimations.appearance;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.nineoldandroids.animation.Animator;

/* loaded from: classes2.dex */
public abstract class SingleAnimationAdapter extends AnimationAdapter {
    protected abstract Animator getAnimator(ViewGroup viewGroup, View view);

    protected SingleAnimationAdapter(BaseAdapter baseAdapter) {
        super(baseAdapter);
    }

    @Override // com.nhaarman.listviewanimations.appearance.AnimationAdapter
    public Animator[] getAnimators(ViewGroup viewGroup, View view) {
        return new Animator[]{getAnimator(viewGroup, view)};
    }
}
