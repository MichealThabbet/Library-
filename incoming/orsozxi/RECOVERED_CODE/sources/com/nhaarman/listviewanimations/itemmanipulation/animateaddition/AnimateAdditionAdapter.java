package com.nhaarman.listviewanimations.itemmanipulation.animateaddition;

import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import com.nhaarman.listviewanimations.BaseAdapterDecorator;
import com.nhaarman.listviewanimations.util.AbsListViewWrapper;
import com.nhaarman.listviewanimations.util.Insertable;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class AnimateAdditionAdapter<T> extends BaseAdapterDecorator {
    private static final String ALPHA = "alpha";
    private static final long DEFAULT_INSERTION_ANIMATION_MS = 300;
    private static final long DEFAULT_SCROLLDOWN_ANIMATION_MS = 300;
    private final InsertQueue<T> mInsertQueue;
    private final Insertable<T> mInsertable;
    private long mInsertionAnimationDurationMs;
    private long mScrolldownAnimationDurationMs;
    private boolean mShouldAnimateDown;

    protected Animator[] getAdditionalAnimators(View view, ViewGroup viewGroup) {
        return new Animator[0];
    }

    public AnimateAdditionAdapter(BaseAdapter baseAdapter) {
        super(baseAdapter);
        this.mScrolldownAnimationDurationMs = 300L;
        this.mInsertionAnimationDurationMs = 300L;
        this.mShouldAnimateDown = true;
        SpinnerAdapter rootAdapter = getRootAdapter();
        if (!(rootAdapter instanceof Insertable)) {
            throw new IllegalArgumentException("BaseAdapter should implement Insertable!");
        }
        Insertable<T> insertable = (Insertable) rootAdapter;
        this.mInsertable = insertable;
        this.mInsertQueue = new InsertQueue<>(insertable);
    }

    @Override // com.nhaarman.listviewanimations.BaseAdapterDecorator
    @Deprecated
    public void setAbsListView(AbsListView absListView) {
        if (absListView instanceof ListView) {
            setListView((ListView) absListView);
            return;
        }
        throw new IllegalArgumentException("AnimateAdditionAdapter requires a ListView!");
    }

    public void setListView(ListView listView) {
        setListViewWrapper(new AbsListViewWrapper(listView));
    }

    public void setShouldAnimateDown(boolean z) {
        this.mShouldAnimateDown = z;
    }

    public void setScrolldownAnimationDuration(long j) {
        this.mScrolldownAnimationDurationMs = j;
    }

    public void setInsertionAnimationDuration(long j) {
        this.mInsertionAnimationDurationMs = j;
    }

    public void insert(int i, T t) {
        insert(new Pair<>(Integer.valueOf(i), t));
    }

    public void insert(int i, T... tArr) {
        Pair<Integer, T>[] pairArr = new Pair[tArr.length];
        for (int i2 = 0; i2 < tArr.length; i2++) {
            pairArr[i2] = new Pair<>(Integer.valueOf(i + i2), tArr[i2]);
        }
        insert(pairArr);
    }

    public void insert(Pair<Integer, T>... pairArr) {
        insert(Arrays.asList(pairArr));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void insert(Iterable<Pair<Integer, T>> iterable) {
        if (getListViewWrapper() == null) {
            throw new IllegalStateException("Call setListView on this AnimateAdditionAdapter!");
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        int measuredHeight = 0;
        int i = 0;
        for (Pair<Integer, T> pair : iterable) {
            if (getListViewWrapper().getFirstVisiblePosition() > ((Integer) pair.first).intValue()) {
                int iIntValue = ((Integer) pair.first).intValue();
                Iterator it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    if (iIntValue >= ((Integer) it2.next()).intValue()) {
                        iIntValue++;
                    }
                }
                this.mInsertable.add(iIntValue, pair.second);
                arrayList2.add(Integer.valueOf(iIntValue));
                i++;
                if (this.mShouldAnimateDown) {
                    View view = getView(((Integer) pair.first).intValue(), null, getListViewWrapper().getListView());
                    view.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
                    measuredHeight -= view.getMeasuredHeight();
                }
            } else if (getListViewWrapper().getLastVisiblePosition() >= ((Integer) pair.first).intValue() || getListViewWrapper().getLastVisiblePosition() == -1 || !childrenFillAbsListView()) {
                int iIntValue2 = ((Integer) pair.first).intValue();
                Iterator it3 = arrayList2.iterator();
                while (it3.hasNext()) {
                    if (iIntValue2 >= ((Integer) it3.next()).intValue()) {
                        iIntValue2++;
                    }
                }
                arrayList.add(new Pair(Integer.valueOf(iIntValue2), pair.second));
            } else {
                int iIntValue3 = ((Integer) pair.first).intValue();
                Iterator it4 = arrayList2.iterator();
                while (it4.hasNext()) {
                    if (iIntValue3 >= ((Integer) it4.next()).intValue()) {
                        iIntValue3++;
                    }
                }
                Iterator it5 = arrayList3.iterator();
                while (it5.hasNext()) {
                    if (iIntValue3 >= ((Integer) it5.next()).intValue()) {
                        iIntValue3++;
                    }
                }
                arrayList3.add(Integer.valueOf(iIntValue3));
                this.mInsertable.add(iIntValue3, pair.second);
            }
        }
        if (this.mShouldAnimateDown) {
            ((AbsListView) getListViewWrapper().getListView()).smoothScrollBy(measuredHeight, (int) (this.mScrolldownAnimationDurationMs * i));
        }
        this.mInsertQueue.insert(arrayList);
        int firstVisiblePosition = getListViewWrapper().getFirstVisiblePosition();
        View childAt = getListViewWrapper().getChildAt(0);
        ((ListView) getListViewWrapper().getListView()).setSelectionFromTop(firstVisiblePosition + i, childAt != null ? childAt.getTop() : 0);
    }

    private boolean childrenFillAbsListView() {
        if (getListViewWrapper() == null) {
            throw new IllegalStateException("Call setListView on this AnimateAdditionAdapter first!");
        }
        int height = 0;
        for (int i = 0; i < getListViewWrapper().getCount(); i++) {
            View childAt = getListViewWrapper().getChildAt(i);
            if (childAt != null) {
                height += childAt.getHeight();
            }
        }
        return getListViewWrapper().getListView().getHeight() <= height;
    }

    @Override // com.nhaarman.listviewanimations.BaseAdapterDecorator, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2 = super.getView(i, view, viewGroup);
        if (this.mInsertQueue.getActiveIndexes().contains(Integer.valueOf(i))) {
            view2.measure(View.MeasureSpec.makeMeasureSpec(-1, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(-2, 0));
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(1, view2.getMeasuredHeight());
            valueAnimatorOfInt.addUpdateListener(new HeightUpdater(view2));
            Animator[] additionalAnimators = getAdditionalAnimators(view2, viewGroup);
            Animator[] animatorArr = new Animator[additionalAnimators.length + 1];
            animatorArr[0] = valueAnimatorOfInt;
            System.arraycopy(additionalAnimators, 0, animatorArr, 1, additionalAnimators.length);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(animatorArr);
            ViewHelper.setAlpha(view2, 0.0f);
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view2, ALPHA, 0.0f, 1.0f);
            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.playSequentially(animatorSet, objectAnimatorOfFloat);
            animatorSet2.setDuration(this.mInsertionAnimationDurationMs);
            animatorSet2.addListener(new ExpandAnimationListener(i));
            animatorSet2.start();
        }
        return view2;
    }

    private static class HeightUpdater implements ValueAnimator.AnimatorUpdateListener {
        private final View mView;

        HeightUpdater(View view) {
            this.mView = view;
        }

        @Override // com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            ViewGroup.LayoutParams layoutParams = this.mView.getLayoutParams();
            layoutParams.height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
            this.mView.setLayoutParams(layoutParams);
        }
    }

    private class ExpandAnimationListener extends AnimatorListenerAdapter {
        private final int mPosition;

        ExpandAnimationListener(int i) {
            this.mPosition = i;
        }

        @Override // com.nineoldandroids.animation.AnimatorListenerAdapter, com.nineoldandroids.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            AnimateAdditionAdapter.this.mInsertQueue.removeActiveIndex(this.mPosition);
        }
    }
}
