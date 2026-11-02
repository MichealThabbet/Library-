package com.marshalchen.ultimaterecyclerview.animators;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.marshalchen.ultimaterecyclerview.animators.BaseItemAnimator;

/* loaded from: classes2.dex */
public class SlideInDownAnimator extends BaseItemAnimator {
    @Override // com.marshalchen.ultimaterecyclerview.animators.BaseItemAnimator
    protected void animateRemoveImpl(RecyclerView.ViewHolder viewHolder) {
        ViewCompat.animate(viewHolder.itemView).translationY(-viewHolder.itemView.getHeight()).alpha(0.0f).setDuration(getRemoveDuration()).setListener(new BaseItemAnimator.DefaultRemoveVpaListener(viewHolder)).start();
    }

    @Override // com.marshalchen.ultimaterecyclerview.animators.BaseItemAnimator
    protected void preAnimateAddImpl(RecyclerView.ViewHolder viewHolder) {
        ViewCompat.setTranslationY(viewHolder.itemView, -viewHolder.itemView.getHeight());
        ViewCompat.setAlpha(viewHolder.itemView, 0.0f);
    }

    @Override // com.marshalchen.ultimaterecyclerview.animators.BaseItemAnimator
    protected void animateAddImpl(RecyclerView.ViewHolder viewHolder) {
        ViewCompat.animate(viewHolder.itemView).translationY(0.0f).alpha(1.0f).setDuration(getAddDuration()).setListener(new BaseItemAnimator.DefaultAddVpaListener(viewHolder)).start();
    }
}
