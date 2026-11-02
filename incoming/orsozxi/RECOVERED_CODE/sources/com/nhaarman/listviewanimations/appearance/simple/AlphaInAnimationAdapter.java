package com.nhaarman.listviewanimations.appearance.simple;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nineoldandroids.animation.Animator;

/* loaded from: classes2.dex */
public class AlphaInAnimationAdapter extends AnimationAdapter {
    @Override // com.nhaarman.listviewanimations.appearance.AnimationAdapter
    public Animator[] getAnimators(ViewGroup viewGroup, View view) {
        return new Animator[0];
    }

    public AlphaInAnimationAdapter(BaseAdapter baseAdapter) {
        super(baseAdapter);
    }
}
