package com.nhaarman.listviewanimations.appearance;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.SparseArray;
import android.view.View;
import android.widget.GridView;
import com.nhaarman.listviewanimations.util.ListViewWrapper;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.view.ViewHelper;

/* loaded from: classes2.dex */
public class ViewAnimator {
    private static final int DEFAULT_ANIMATION_DELAY_MILLIS = 100;
    private static final int DEFAULT_ANIMATION_DURATION_MILLIS = 300;
    private static final int INITIAL_DELAY_MILLIS = 150;
    private static final String SAVEDINSTANCESTATE_FIRSTANIMATEDPOSITION = "savedinstancestate_firstanimatedposition";
    private static final String SAVEDINSTANCESTATE_LASTANIMATEDPOSITION = "savedinstancestate_lastanimatedposition";
    private static final String SAVEDINSTANCESTATE_SHOULDANIMATE = "savedinstancestate_shouldanimate";
    private final ListViewWrapper mListViewWrapper;
    private final SparseArray<Animator> mAnimators = new SparseArray<>();
    private int mInitialDelayMillis = 150;
    private int mAnimationDelayMillis = 100;
    private int mAnimationDurationMillis = 300;
    private boolean mShouldAnimate = true;
    private long mAnimationStartMillis = -1;
    private int mFirstAnimatedPosition = -1;
    private int mLastAnimatedPosition = -1;

    public ViewAnimator(ListViewWrapper listViewWrapper) {
        this.mListViewWrapper = listViewWrapper;
    }

    public void reset() {
        for (int i = 0; i < this.mAnimators.size(); i++) {
            SparseArray<Animator> sparseArray = this.mAnimators;
            sparseArray.get(sparseArray.keyAt(i)).cancel();
        }
        this.mAnimators.clear();
        this.mFirstAnimatedPosition = -1;
        this.mLastAnimatedPosition = -1;
        this.mAnimationStartMillis = -1L;
        this.mShouldAnimate = true;
    }

    public void setShouldAnimateFromPosition(int i) {
        enableAnimations();
        int i2 = i - 1;
        this.mFirstAnimatedPosition = i2;
        this.mLastAnimatedPosition = i2;
    }

    public void setShouldAnimateNotVisible() {
        enableAnimations();
        this.mFirstAnimatedPosition = this.mListViewWrapper.getLastVisiblePosition();
        this.mLastAnimatedPosition = this.mListViewWrapper.getLastVisiblePosition();
    }

    void setLastAnimatedPosition(int i) {
        this.mLastAnimatedPosition = i;
    }

    public void setInitialDelayMillis(int i) {
        this.mInitialDelayMillis = i;
    }

    public void setAnimationDelayMillis(int i) {
        this.mAnimationDelayMillis = i;
    }

    public void setAnimationDurationMillis(int i) {
        this.mAnimationDurationMillis = i;
    }

    public void enableAnimations() {
        this.mShouldAnimate = true;
    }

    public void disableAnimations() {
        this.mShouldAnimate = false;
    }

    void cancelExistingAnimation(View view) {
        int iHashCode = view.hashCode();
        Animator animator = this.mAnimators.get(iHashCode);
        if (animator != null) {
            animator.end();
            this.mAnimators.remove(iHashCode);
        }
    }

    public void animateViewIfNecessary(int i, View view, Animator[] animatorArr) {
        if (!this.mShouldAnimate || i <= this.mLastAnimatedPosition) {
            return;
        }
        if (this.mFirstAnimatedPosition == -1) {
            this.mFirstAnimatedPosition = i;
        }
        animateView(i, view, animatorArr);
        this.mLastAnimatedPosition = i;
    }

    private void animateView(int i, View view, Animator[] animatorArr) {
        if (this.mAnimationStartMillis == -1) {
            this.mAnimationStartMillis = SystemClock.uptimeMillis();
        }
        ViewHelper.setAlpha(view, 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorArr);
        animatorSet.setStartDelay(calculateAnimationDelay(i));
        animatorSet.setDuration(this.mAnimationDurationMillis);
        animatorSet.start();
        this.mAnimators.put(view.hashCode(), animatorSet);
    }

    private int calculateAnimationDelay(int i) {
        if ((this.mListViewWrapper.getLastVisiblePosition() - this.mListViewWrapper.getFirstVisiblePosition()) + 1 < (i - 1) - this.mFirstAnimatedPosition) {
            int i2 = this.mAnimationDelayMillis;
            if (!(this.mListViewWrapper.getListView() instanceof GridView) || Build.VERSION.SDK_INT < 11) {
                return i2;
            }
            return i2 + (this.mAnimationDelayMillis * (i % ((GridView) this.mListViewWrapper.getListView()).getNumColumns()));
        }
        return Math.max(0, (int) ((-SystemClock.uptimeMillis()) + this.mAnimationStartMillis + this.mInitialDelayMillis + ((i - r2) * this.mAnimationDelayMillis)));
    }

    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putInt(SAVEDINSTANCESTATE_FIRSTANIMATEDPOSITION, this.mFirstAnimatedPosition);
        bundle.putInt(SAVEDINSTANCESTATE_LASTANIMATEDPOSITION, this.mLastAnimatedPosition);
        bundle.putBoolean(SAVEDINSTANCESTATE_SHOULDANIMATE, this.mShouldAnimate);
        return bundle;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            this.mFirstAnimatedPosition = bundle.getInt(SAVEDINSTANCESTATE_FIRSTANIMATEDPOSITION);
            this.mLastAnimatedPosition = bundle.getInt(SAVEDINSTANCESTATE_LASTANIMATEDPOSITION);
            this.mShouldAnimate = bundle.getBoolean(SAVEDINSTANCESTATE_SHOULDANIMATE);
        }
    }
}
