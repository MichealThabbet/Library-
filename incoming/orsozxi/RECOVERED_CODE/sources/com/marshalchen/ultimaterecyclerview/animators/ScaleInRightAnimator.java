package com.marshalchen.ultimaterecyclerview.animators;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.marshalchen.ultimaterecyclerview.animators.BaseItemAnimator;

/* loaded from: classes2.dex */
public class ScaleInRightAnimator extends BaseItemAnimator {
    @Override // com.marshalchen.ultimaterecyclerview.animators.BaseItemAnimator
    protected void preAnimateRemoveImpl(RecyclerView.ViewHolder viewHolder) {
        ViewCompat.setPivotX(viewHolder.itemView, viewHolder.itemView.getWidth());
    }

    @Override // com.marshalchen.ultimaterecyclerview.animators.BaseItemAnimator
    protected void animateRemoveImpl(RecyclerView.ViewHolder viewHolder) {
        ViewCompat.animate(viewHolder.itemView).scaleX(0.0f).scaleY(0.0f).setDuration(getRemoveDuration()).setListener(new BaseItemAnimator.DefaultRemoveVpaListener(viewHolder)).start();
    }

    @Override // com.marshalchen.ultimaterecyclerview.animators.BaseItemAnimator
    protected void preAnimateAddImpl(RecyclerView.ViewHolder viewHolder) {
        ViewCompat.setPivotX(viewHolder.itemView, viewHolder.itemView.getWidth());
        ViewCompat.setScaleX(viewHolder.itemView, 0.0f);
        ViewCompat.setScaleY(viewHolder.itemView, 0.0f);
    }

    @Override // com.marshalchen.ultimaterecyclerview.animators.BaseItemAnimator
    protected void animateAddImpl(RecyclerView.ViewHolder viewHolder) {
        ViewCompat.animate(viewHolder.itemView).scaleX(1.0f).scaleY(1.0f).setDuration(getAddDuration()).setListener(new BaseItemAnimator.DefaultAddVpaListener(viewHolder)).start();
    }
}
