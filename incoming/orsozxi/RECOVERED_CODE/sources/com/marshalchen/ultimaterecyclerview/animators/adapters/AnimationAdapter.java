package com.marshalchen.ultimaterecyclerview.animators.adapters;

import android.animation.Animator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.recyclerview.widget.RecyclerView;
import com.marshalchen.ultimaterecyclerview.animators.internal.ViewHelper;

/* loaded from: classes2.dex */
public abstract class AnimationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;
    private int mDuration = 300;
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mLastPosition = -1;
    private boolean isFirstOnly = true;

    protected abstract Animator[] getAnimators(View view);

    public AnimationAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        this.mAdapter = adapter;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return this.mAdapter.onCreateViewHolder(viewGroup, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        this.mAdapter.onBindViewHolder(viewHolder, i);
        if (!this.isFirstOnly || i > this.mLastPosition) {
            for (Animator animator : getAnimators(viewHolder.itemView)) {
                animator.setDuration(this.mDuration).start();
                animator.setInterpolator(this.mInterpolator);
            }
            this.mLastPosition = i;
            return;
        }
        ViewHelper.clear(viewHolder.itemView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mAdapter.getItemCount();
    }

    public void setDuration(int i) {
        this.mDuration = i;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public void setStartPosition(int i) {
        this.mLastPosition = i;
    }

    public void setFirstOnly(boolean z) {
        this.isFirstOnly = z;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return this.mAdapter.getItemViewType(i);
    }
}
