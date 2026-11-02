package com.marshalchen.ultimaterecyclerview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public class SwipeableRecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {
    private float mAlpha;
    private long mAnimationTime;
    private int mDownPosition;
    private View mDownView;
    private float mDownX;
    private float mDownY;
    private float mFinalDelta;
    private int mMaxFlingVelocity;
    private int mMinFlingVelocity;
    private boolean mPaused;
    private RecyclerView mRecyclerView;
    private int mSlop;
    private SwipeListener mSwipeListener;
    private boolean mSwiping;
    private int mSwipingSlop;
    private VelocityTracker mVelocityTracker;
    private int mViewWidth = 1;
    private List<PendingDismissData> mPendingDismisses = new ArrayList();
    private int mDismissAnimationRefCount = 0;
    private int mAnimatingPosition = -1;

    public interface SwipeListener {
        boolean canSwipe(int i);

        void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] iArr);

        void onDismissedBySwipeRight(RecyclerView recyclerView, int[] iArr);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public void onRequestDisallowInterceptTouchEvent(boolean z) {
    }

    static /* synthetic */ int access$106(SwipeableRecyclerViewTouchListener swipeableRecyclerViewTouchListener) {
        int i = swipeableRecyclerViewTouchListener.mDismissAnimationRefCount - 1;
        swipeableRecyclerViewTouchListener.mDismissAnimationRefCount = i;
        return i;
    }

    public SwipeableRecyclerViewTouchListener(RecyclerView recyclerView, SwipeListener swipeListener) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(recyclerView.getContext());
        this.mSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity() * 16;
        this.mMaxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mAnimationTime = recyclerView.getContext().getResources().getInteger(android.R.integer.config_shortAnimTime);
        this.mRecyclerView = recyclerView;
        this.mSwipeListener = swipeListener;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.marshalchen.ultimaterecyclerview.SwipeableRecyclerViewTouchListener.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView2, int i, int i2) {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView2, int i) {
                SwipeableRecyclerViewTouchListener.this.setEnabled(i != 1);
            }
        });
    }

    public void setEnabled(boolean z) {
        this.mPaused = !z;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        return handleTouchEvent(motionEvent);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        handleTouchEvent(motionEvent);
    }

    private boolean handleTouchEvent(MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        final int i;
        if (this.mViewWidth < 2) {
            this.mViewWidth = this.mRecyclerView.getWidth();
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked != 2) {
                    if (actionMasked == 3 && this.mVelocityTracker != null) {
                        View view = this.mDownView;
                        if (view != null && this.mSwiping) {
                            view.animate().translationX(0.0f).alpha(this.mAlpha).setDuration(this.mAnimationTime).setListener(null);
                        }
                        this.mVelocityTracker.recycle();
                        this.mVelocityTracker = null;
                        this.mDownX = 0.0f;
                        this.mDownY = 0.0f;
                        this.mDownView = null;
                        this.mDownPosition = -1;
                        this.mSwiping = false;
                    }
                } else {
                    VelocityTracker velocityTracker = this.mVelocityTracker;
                    if (velocityTracker != null && !this.mPaused) {
                        velocityTracker.addMovement(motionEvent);
                        float rawX = motionEvent.getRawX() - this.mDownX;
                        float rawY = motionEvent.getRawY() - this.mDownY;
                        if (!this.mSwiping && Math.abs(rawX) > this.mSlop && Math.abs(rawY) < Math.abs(rawX) / 2.0f) {
                            this.mSwiping = true;
                            this.mSwipingSlop = rawX > 0.0f ? this.mSlop : -this.mSlop;
                        }
                        if (this.mSwiping) {
                            this.mDownView.setTranslationX(rawX - this.mSwipingSlop);
                            View view2 = this.mDownView;
                            float f = this.mAlpha;
                            view2.setAlpha(Math.max(0.0f, Math.min(f, (1.0f - (Math.abs(rawX) / this.mViewWidth)) * f)));
                            return true;
                        }
                    }
                }
            } else if (this.mVelocityTracker != null) {
                this.mFinalDelta = motionEvent.getRawX() - this.mDownX;
                this.mVelocityTracker.addMovement(motionEvent);
                this.mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = this.mVelocityTracker.getXVelocity();
                float fAbs = Math.abs(xVelocity);
                float fAbs2 = Math.abs(this.mVelocityTracker.getYVelocity());
                if (Math.abs(this.mFinalDelta) <= this.mViewWidth / 2 || !this.mSwiping) {
                    if (this.mMinFlingVelocity > fAbs || fAbs > this.mMaxFlingVelocity || fAbs2 >= fAbs || !this.mSwiping) {
                        z = false;
                    } else {
                        z = ((xVelocity > 0.0f ? 1 : (xVelocity == 0.0f ? 0 : -1)) < 0) == ((this.mFinalDelta > 0.0f ? 1 : (this.mFinalDelta == 0.0f ? 0 : -1)) < 0);
                        if (this.mVelocityTracker.getXVelocity() > 0.0f) {
                            z2 = true;
                        }
                    }
                    z2 = false;
                } else {
                    z2 = this.mFinalDelta > 0.0f;
                    z = true;
                }
                if (z && (i = this.mDownPosition) != this.mAnimatingPosition && i != -1) {
                    final View view3 = this.mDownView;
                    this.mDismissAnimationRefCount++;
                    this.mAnimatingPosition = i;
                    view3.animate().translationX(z2 ? this.mViewWidth : -this.mViewWidth).alpha(0.0f).setDuration(this.mAnimationTime).setListener(new AnimatorListenerAdapter() { // from class: com.marshalchen.ultimaterecyclerview.SwipeableRecyclerViewTouchListener.2
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            SwipeableRecyclerViewTouchListener.this.performDismiss(view3, i);
                        }
                    });
                } else {
                    this.mDownView.animate().translationX(0.0f).alpha(this.mAlpha).setDuration(this.mAnimationTime).setListener(null);
                }
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
                this.mDownX = 0.0f;
                this.mDownY = 0.0f;
                this.mDownView = null;
                this.mDownPosition = -1;
                this.mSwiping = false;
            }
        } else if (!this.mPaused) {
            Rect rect = new Rect();
            int childCount = this.mRecyclerView.getChildCount();
            int[] iArr = new int[2];
            this.mRecyclerView.getLocationOnScreen(iArr);
            int rawX2 = ((int) motionEvent.getRawX()) - iArr[0];
            int rawY2 = ((int) motionEvent.getRawY()) - iArr[1];
            int i2 = 0;
            while (true) {
                if (i2 >= childCount) {
                    break;
                }
                View childAt = this.mRecyclerView.getChildAt(i2);
                childAt.getHitRect(rect);
                if (rect.contains(rawX2, rawY2)) {
                    this.mDownView = childAt;
                    break;
                }
                i2++;
            }
            View view4 = this.mDownView;
            if (view4 != null && this.mAnimatingPosition != this.mRecyclerView.getChildPosition(view4)) {
                this.mAlpha = this.mDownView.getAlpha();
                this.mDownX = motionEvent.getRawX();
                this.mDownY = motionEvent.getRawY();
                int childPosition = this.mRecyclerView.getChildPosition(this.mDownView);
                this.mDownPosition = childPosition;
                if (this.mSwipeListener.canSwipe(childPosition)) {
                    VelocityTracker velocityTrackerObtain = VelocityTracker.obtain();
                    this.mVelocityTracker = velocityTrackerObtain;
                    velocityTrackerObtain.addMovement(motionEvent);
                } else {
                    this.mDownView = null;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performDismiss(final View view, int i) {
        final ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        final int i2 = layoutParams.height;
        ValueAnimator duration = ValueAnimator.ofInt(view.getHeight(), 1).setDuration(this.mAnimationTime);
        duration.addListener(new AnimatorListenerAdapter() { // from class: com.marshalchen.ultimaterecyclerview.SwipeableRecyclerViewTouchListener.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                SwipeableRecyclerViewTouchListener.access$106(SwipeableRecyclerViewTouchListener.this);
                if (SwipeableRecyclerViewTouchListener.this.mDismissAnimationRefCount == 0) {
                    Collections.sort(SwipeableRecyclerViewTouchListener.this.mPendingDismisses);
                    int[] iArr = new int[SwipeableRecyclerViewTouchListener.this.mPendingDismisses.size()];
                    for (int size = SwipeableRecyclerViewTouchListener.this.mPendingDismisses.size() - 1; size >= 0; size--) {
                        iArr[size] = ((PendingDismissData) SwipeableRecyclerViewTouchListener.this.mPendingDismisses.get(size)).position;
                    }
                    if (SwipeableRecyclerViewTouchListener.this.mFinalDelta > 0.0f) {
                        SwipeableRecyclerViewTouchListener.this.mSwipeListener.onDismissedBySwipeRight(SwipeableRecyclerViewTouchListener.this.mRecyclerView, iArr);
                    } else {
                        SwipeableRecyclerViewTouchListener.this.mSwipeListener.onDismissedBySwipeLeft(SwipeableRecyclerViewTouchListener.this.mRecyclerView, iArr);
                    }
                    SwipeableRecyclerViewTouchListener.this.mDownPosition = -1;
                    for (PendingDismissData pendingDismissData : SwipeableRecyclerViewTouchListener.this.mPendingDismisses) {
                        pendingDismissData.view.setAlpha(SwipeableRecyclerViewTouchListener.this.mAlpha);
                        pendingDismissData.view.setTranslationX(0.0f);
                        ViewGroup.LayoutParams layoutParams2 = pendingDismissData.view.getLayoutParams();
                        layoutParams2.height = i2;
                        pendingDismissData.view.setLayoutParams(layoutParams2);
                    }
                    long jUptimeMillis = SystemClock.uptimeMillis();
                    SwipeableRecyclerViewTouchListener.this.mRecyclerView.dispatchTouchEvent(MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0));
                    SwipeableRecyclerViewTouchListener.this.mPendingDismisses.clear();
                    SwipeableRecyclerViewTouchListener.this.mAnimatingPosition = -1;
                }
            }
        });
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.marshalchen.ultimaterecyclerview.SwipeableRecyclerViewTouchListener.4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                view.setLayoutParams(layoutParams);
            }
        });
        this.mPendingDismisses.add(new PendingDismissData(i, view));
        duration.start();
    }

    class PendingDismissData implements Comparable<PendingDismissData> {
        public int position;
        public View view;

        public PendingDismissData(int i, View view) {
            this.position = i;
            this.view = view;
        }

        @Override // java.lang.Comparable
        public int compareTo(PendingDismissData pendingDismissData) {
            return pendingDismissData.position - this.position;
        }
    }
}
