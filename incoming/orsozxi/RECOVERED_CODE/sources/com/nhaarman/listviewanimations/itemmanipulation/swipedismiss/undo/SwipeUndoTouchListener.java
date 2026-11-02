package com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo;

import android.view.View;
import android.view.ViewGroup;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissTouchListener;
import com.nhaarman.listviewanimations.util.AdapterViewUtil;
import com.nhaarman.listviewanimations.util.ListViewWrapper;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class SwipeUndoTouchListener extends SwipeDismissTouchListener {
    private static final String ALPHA = "alpha";
    private static final String TRANSLATION_X = "translationX";
    private final UndoCallback mCallback;
    private final List<Integer> mDismissedPositions;
    private final Collection<View> mDismissedViews;
    private final Collection<Integer> mUndoPositions;
    private final Map<Integer, View> mUndoViews;

    public SwipeUndoTouchListener(ListViewWrapper listViewWrapper, UndoCallback undoCallback) {
        super(listViewWrapper, undoCallback);
        this.mUndoPositions = new LinkedList();
        this.mUndoViews = new HashMap();
        this.mDismissedPositions = new LinkedList();
        this.mDismissedViews = new LinkedList();
        this.mCallback = undoCallback;
    }

    @Override // com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissTouchListener, com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeTouchListener
    protected boolean willLeaveDataSetOnFling(View view, int i) {
        return this.mUndoPositions.contains(Integer.valueOf(i));
    }

    @Override // com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissTouchListener, com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeTouchListener
    protected void afterViewFling(View view, int i) {
        if (this.mUndoPositions.contains(Integer.valueOf(i))) {
            this.mUndoPositions.remove(Integer.valueOf(i));
            this.mUndoViews.remove(Integer.valueOf(i));
            performDismiss(view, i);
            hideUndoView(view);
            return;
        }
        this.mUndoPositions.add(Integer.valueOf(i));
        this.mUndoViews.put(Integer.valueOf(i), view);
        this.mCallback.onUndoShown(view, i);
        showUndoView(view);
        restoreViewPresentation(view);
    }

    @Override // com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissTouchListener, com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeTouchListener
    protected void afterCancelSwipe(View view, int i) {
        finalizeDismiss();
    }

    @Override // com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissTouchListener
    protected void performDismiss(View view, int i) {
        super.performDismiss(view, i);
        this.mDismissedViews.add(view);
        this.mDismissedPositions.add(Integer.valueOf(i));
        this.mCallback.onDismiss(view, i);
    }

    public boolean hasPendingItems() {
        return !this.mUndoPositions.isEmpty();
    }

    public void dimissPending() {
        Iterator<Integer> it2 = this.mUndoPositions.iterator();
        while (it2.hasNext()) {
            int iIntValue = it2.next().intValue();
            performDismiss(this.mUndoViews.get(Integer.valueOf(iIntValue)), iIntValue);
        }
    }

    private void showUndoView(View view) {
        this.mCallback.getPrimaryView(view).setVisibility(8);
        View undoView = this.mCallback.getUndoView(view);
        undoView.setVisibility(0);
        ObjectAnimator.ofFloat(undoView, ALPHA, 0.0f, 1.0f).start();
    }

    private void hideUndoView(View view) {
        this.mCallback.getPrimaryView(view).setVisibility(0);
        this.mCallback.getUndoView(view).setVisibility(8);
    }

    @Override // com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissTouchListener
    protected void finalizeDismiss() {
        if (getActiveDismissCount() == 0 && getActiveSwipeCount() == 0) {
            restoreViewPresentations(this.mDismissedViews);
            notifyCallback(this.mDismissedPositions);
            Collection<Integer> collectionProcessDeletions = Util.processDeletions(this.mUndoPositions, this.mDismissedPositions);
            this.mUndoPositions.clear();
            this.mUndoPositions.addAll(collectionProcessDeletions);
            this.mDismissedViews.clear();
            this.mDismissedPositions.clear();
        }
    }

    @Override // com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissTouchListener, com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeTouchListener
    protected void restoreViewPresentation(View view) {
        super.restoreViewPresentation(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = 0;
        view.setLayoutParams(layoutParams);
    }

    public void undo(View view) {
        int positionForView = AdapterViewUtil.getPositionForView(getListViewWrapper(), view);
        this.mUndoPositions.remove(Integer.valueOf(positionForView));
        View primaryView = this.mCallback.getPrimaryView(view);
        View undoView = this.mCallback.getUndoView(view);
        primaryView.setVisibility(0);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(undoView, ALPHA, 1.0f, 0.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(primaryView, ALPHA, 0.0f, 1.0f);
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(primaryView, TRANSLATION_X, primaryView.getWidth(), 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, objectAnimatorOfFloat3);
        animatorSet.addListener(new UndoAnimatorListener(undoView));
        animatorSet.start();
        this.mCallback.onUndo(view, positionForView);
    }

    @Override // com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissTouchListener
    protected void directDismiss(int i) {
        this.mDismissedPositions.add(Integer.valueOf(i));
        finalizeDismiss();
    }

    private class UndoAnimatorListener extends AnimatorListenerAdapter {
        private final View mUndoView;

        UndoAnimatorListener(View view) {
            this.mUndoView = view;
        }

        @Override // com.nineoldandroids.animation.AnimatorListenerAdapter, com.nineoldandroids.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            this.mUndoView.setVisibility(8);
            SwipeUndoTouchListener.this.finalizeDismiss();
        }
    }
}
