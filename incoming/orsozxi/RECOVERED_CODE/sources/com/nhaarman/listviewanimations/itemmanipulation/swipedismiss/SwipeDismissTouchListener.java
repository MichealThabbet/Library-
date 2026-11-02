package com.nhaarman.listviewanimations.itemmanipulation.swipedismiss;

import android.R;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import com.nhaarman.listviewanimations.util.AdapterViewUtil;
import com.nhaarman.listviewanimations.util.ListViewWrapper;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes2.dex */
public class SwipeDismissTouchListener extends SwipeTouchListener {
    private int mActiveDismissCount;
    private final OnDismissCallback mCallback;
    private final long mDismissAnimationTime;
    private final List<Integer> mDismissedPositions;
    private final Collection<View> mDismissedViews;
    private final Handler mHandler;

    @Override // com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeTouchListener
    protected boolean willLeaveDataSetOnFling(View view, int i) {
        return true;
    }

    static /* synthetic */ int access$110(SwipeDismissTouchListener swipeDismissTouchListener) {
        int i = swipeDismissTouchListener.mActiveDismissCount;
        swipeDismissTouchListener.mActiveDismissCount = i - 1;
        return i;
    }

    public SwipeDismissTouchListener(ListViewWrapper listViewWrapper, OnDismissCallback onDismissCallback) {
        super(listViewWrapper);
        this.mDismissedViews = new LinkedList();
        this.mDismissedPositions = new LinkedList();
        this.mHandler = new Handler();
        this.mCallback = onDismissCallback;
        this.mDismissAnimationTime = listViewWrapper.getListView().getContext().getResources().getInteger(R.integer.config_shortAnimTime);
    }

    public void dismiss(int i) {
        fling(i);
    }

    @Override // com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeTouchListener
    public void fling(int i) {
        int firstVisiblePosition = getListViewWrapper().getFirstVisiblePosition();
        int lastVisiblePosition = getListViewWrapper().getLastVisiblePosition();
        if (firstVisiblePosition <= i && i <= lastVisiblePosition) {
            super.fling(i);
        } else if (i > lastVisiblePosition) {
            directDismiss(i);
        } else {
            dismissAbove(i);
        }
    }

    protected void directDismiss(int i) {
        this.mDismissedPositions.add(Integer.valueOf(i));
        finalizeDismiss();
    }

    private void dismissAbove(int i) {
        View viewForPosition = AdapterViewUtil.getViewForPosition(getListViewWrapper(), getListViewWrapper().getFirstVisiblePosition());
        if (viewForPosition != null) {
            viewForPosition.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
            int measuredHeight = viewForPosition.getMeasuredHeight();
            getListViewWrapper().smoothScrollBy(measuredHeight, (int) this.mDismissAnimationTime);
            this.mHandler.postDelayed(new RestoreScrollRunnable(measuredHeight, i), this.mDismissAnimationTime);
        }
    }

    @Override // com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeTouchListener
    protected void afterCancelSwipe(View view, int i) {
        finalizeDismiss();
    }

    @Override // com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeTouchListener
    protected void afterViewFling(View view, int i) {
        performDismiss(view, i);
    }

    protected void performDismiss(View view, int i) {
        this.mDismissedViews.add(view);
        this.mDismissedPositions.add(Integer.valueOf(i));
        ValueAnimator duration = ValueAnimator.ofInt(view.getHeight(), 1).setDuration(this.mDismissAnimationTime);
        duration.addUpdateListener(new DismissAnimatorUpdateListener(view));
        duration.addListener(new DismissAnimatorListener());
        duration.start();
        this.mActiveDismissCount++;
    }

    protected void finalizeDismiss() {
        if (this.mActiveDismissCount == 0 && getActiveSwipeCount() == 0) {
            restoreViewPresentations(this.mDismissedViews);
            notifyCallback(this.mDismissedPositions);
            this.mDismissedViews.clear();
            this.mDismissedPositions.clear();
        }
    }

    protected void notifyCallback(List<Integer> list) {
        if (list.isEmpty()) {
            return;
        }
        Collections.sort(list, Collections.reverseOrder());
        int[] iArr = new int[list.size()];
        int i = 0;
        Iterator<Integer> it2 = list.iterator();
        while (it2.hasNext()) {
            iArr[i] = it2.next().intValue();
            i++;
        }
        this.mCallback.onDismiss(getListViewWrapper().getListView(), iArr);
    }

    protected void restoreViewPresentations(Iterable<View> iterable) {
        Iterator<View> it2 = iterable.iterator();
        while (it2.hasNext()) {
            restoreViewPresentation(it2.next());
        }
    }

    @Override // com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeTouchListener
    protected void restoreViewPresentation(View view) {
        super.restoreViewPresentation(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = 0;
        view.setLayoutParams(layoutParams);
    }

    protected int getActiveDismissCount() {
        return this.mActiveDismissCount;
    }

    public long getDismissAnimationTime() {
        return this.mDismissAnimationTime;
    }

    private static class DismissAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {
        private final View mView;

        DismissAnimatorUpdateListener(View view) {
            this.mView = view;
        }

        @Override // com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            ViewGroup.LayoutParams layoutParams = this.mView.getLayoutParams();
            layoutParams.height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
            this.mView.setLayoutParams(layoutParams);
        }
    }

    private class DismissAnimatorListener extends AnimatorListenerAdapter {
        private DismissAnimatorListener() {
        }

        @Override // com.nineoldandroids.animation.AnimatorListenerAdapter, com.nineoldandroids.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            SwipeDismissTouchListener.access$110(SwipeDismissTouchListener.this);
            SwipeDismissTouchListener.this.finalizeDismiss();
        }
    }

    private class RestoreScrollRunnable implements Runnable {
        private final int mPosition;
        private final int mScrollDistance;

        RestoreScrollRunnable(int i, int i2) {
            this.mScrollDistance = i;
            this.mPosition = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            SwipeDismissTouchListener.this.getListViewWrapper().smoothScrollBy(-this.mScrollDistance, 1);
            SwipeDismissTouchListener.this.directDismiss(this.mPosition);
        }
    }
}
