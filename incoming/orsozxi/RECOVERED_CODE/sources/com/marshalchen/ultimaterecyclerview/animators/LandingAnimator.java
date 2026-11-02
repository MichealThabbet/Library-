package com.marshalchen.ultimaterecyclerview.animators;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.marshalchen.ultimaterecyclerview.animators.BaseItemAnimator;

/* loaded from: classes2.dex */
public class LandingAnimator extends BaseItemAnimator {
    @Override // com.marshalchen.ultimaterecyclerview.animators.BaseItemAnimator
    protected void animateRemoveImpl(RecyclerView.ViewHolder viewHolder) {
        ViewCompat.animate(viewHolder.itemView).alpha(0.0f).scaleX(1.5f).scaleY(1.5f).setDuration(getRemoveDuration()).setListener(new BaseItemAnimator.DefaultRemoveVpaListener(viewHolder)).start();
    }

    @Override // com.marshalchen.ultimaterecyclerview.animators.BaseItemAnimator
    protected void preAnimateAddImpl(RecyclerView.ViewHolder viewHolder) {
        ViewCompat.setAlpha(viewHolder.itemView, 0.0f);
        ViewCompat.setScaleX(viewHolder.itemView, 1.5f);
        ViewCompat.setScaleY(viewHolder.itemView, 1.5f);
    }

    @Override // com.marshalchen.ultimaterecyclerview.animators.BaseItemAnimator
    protected void animateAddImpl(RecyclerView.ViewHolder viewHolder) {
        ViewCompat.animate(viewHolder.itemView).alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(getAddDuration()).setListener(new BaseItemAnimator.DefaultAddVpaListener(viewHolder)).start();
    }
}
