package com.app.piapostolos.Alarm.Bubbles;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import com.app.piapostolos.R;

/* loaded from: classes.dex */
public class BubbleTrashLayout extends BubbleBaseLayout {
    private boolean attachedToWindow;
    private boolean magnetismApplied;

    public BubbleTrashLayout(Context context) {
        super(context);
        this.magnetismApplied = false;
        this.attachedToWindow = false;
    }

    public BubbleTrashLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.magnetismApplied = false;
        this.attachedToWindow = false;
    }

    public BubbleTrashLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.magnetismApplied = false;
        this.attachedToWindow = false;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.attachedToWindow = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.attachedToWindow = false;
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        if (this.attachedToWindow && i != getVisibility()) {
            if (i == 0) {
                playAnimation(R.animator.bubble_trash_shown_animator);
            } else {
                playAnimation(R.animator.bubble_trash_hide_animator);
            }
        }
        super.setVisibility(i);
    }

    void applyMagnetism() {
        if (this.magnetismApplied) {
            return;
        }
        this.magnetismApplied = true;
        playAnimation(R.animator.bubble_trash_shown_magnetism_animator);
    }

    void releaseMagnetism() {
        if (this.magnetismApplied) {
            this.magnetismApplied = false;
            playAnimation(R.animator.bubble_trash_hide_magnetism_animator);
        }
    }

    private void playAnimation(int i) {
        if (isInEditMode()) {
            return;
        }
        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), i);
        animatorSet.setTarget(getChildAt(0));
        animatorSet.start();
    }
}
