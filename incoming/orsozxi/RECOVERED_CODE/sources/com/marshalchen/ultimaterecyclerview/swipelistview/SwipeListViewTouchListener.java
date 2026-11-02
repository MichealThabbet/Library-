package com.marshalchen.ultimaterecyclerview.swipelistview;

import android.R;
import android.graphics.Rect;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public class SwipeListViewTouchListener implements View.OnTouchListener {
    private static final int DISPLACE_CHOICE = 80;
    private long animationTime;
    private View backView;
    private long configShortAnimationTime;
    private int downPosition;
    private float downX;
    private View frontView;
    private boolean listViewMoving;
    private LinearLayoutManager mLayoutManager;
    private int maxFlingVelocity;
    private int minFlingVelocity;
    private int oldSwipeActionLeft;
    private int oldSwipeActionRight;
    private View parentView;
    private boolean paused;
    private int slop;
    private int swipeBackView;
    private int swipeFrontView;
    private SwipeListView swipeListView;
    private boolean swiping;
    private boolean swipingRight;
    private VelocityTracker velocityTracker;
    private int swipeMode = 1;
    private boolean swipeOpenOnLongPress = true;
    private boolean swipeClosesAllItemsWhenListMoves = true;
    private Rect rect = new Rect();
    private float leftOffset = 0.0f;
    private float rightOffset = 0.0f;
    private int swipeDrawableChecked = 0;
    private int swipeDrawableUnchecked = 0;
    private boolean onlyOneOpenedWhenSwipe = false;
    private int viewWidth = 1;
    private List<PendingDismissData> pendingDismisses = new ArrayList();
    private int dismissAnimationRefCount = 0;
    private int swipeCurrentAction = 3;
    private int swipeActionLeft = 0;
    private int swipeActionRight = 0;
    private List<Boolean> opened = new ArrayList();
    private List<Boolean> openedRight = new ArrayList();
    private List<Boolean> checked = new ArrayList();

    static /* synthetic */ int access$1006(SwipeListViewTouchListener swipeListViewTouchListener) {
        int i = swipeListViewTouchListener.dismissAnimationRefCount - 1;
        swipeListViewTouchListener.dismissAnimationRefCount = i;
        return i;
    }

    public SwipeListViewTouchListener(SwipeListView swipeListView, int i, int i2) {
        this.swipeFrontView = 0;
        this.swipeBackView = 0;
        this.swipeFrontView = i;
        this.swipeBackView = i2;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(swipeListView.getContext());
        this.slop = viewConfiguration.getScaledTouchSlop();
        this.minFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.maxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        long integer = swipeListView.getContext().getResources().getInteger(R.integer.config_shortAnimTime);
        this.configShortAnimationTime = integer;
        this.animationTime = integer;
        this.swipeListView = swipeListView;
    }

    private void setParentView(View view) {
        this.parentView = view;
    }

    public void setLayoutManager(LinearLayoutManager linearLayoutManager) {
        this.mLayoutManager = linearLayoutManager;
    }

    private void setFrontView(View view, final int i) {
        this.frontView = view;
        view.setOnClickListener(new View.OnClickListener() { // from class: com.marshalchen.ultimaterecyclerview.swipelistview.SwipeListViewTouchListener.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                SwipeListViewTouchListener.this.swipeListView.onClickFrontView(SwipeListViewTouchListener.this.downPosition);
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.marshalchen.ultimaterecyclerview.swipelistview.SwipeListViewTouchListener.2
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                if (SwipeListViewTouchListener.this.swipeOpenOnLongPress) {
                    if (SwipeListViewTouchListener.this.downPosition < 0) {
                        return false;
                    }
                    SwipeListViewTouchListener.this.openAnimate(i);
                    return false;
                }
                SwipeListViewTouchListener.this.swapChoiceState(i);
                return false;
            }
        });
    }

    private void setBackView(View view) {
        this.backView = view;
        view.setOnClickListener(new View.OnClickListener() { // from class: com.marshalchen.ultimaterecyclerview.swipelistview.SwipeListViewTouchListener.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                SwipeListViewTouchListener.this.swipeListView.onClickBackView(SwipeListViewTouchListener.this.downPosition);
            }
        });
    }

    public void setOnlyOneOpenedWhenSwipe(boolean z) {
        this.onlyOneOpenedWhenSwipe = z;
    }

    public boolean isListViewMoving() {
        return this.listViewMoving;
    }

    public void setAnimationTime(long j) {
        if (j > 0) {
            this.animationTime = j;
        } else {
            this.animationTime = this.configShortAnimationTime;
        }
    }

    public void setRightOffset(float f) {
        this.rightOffset = f;
    }

    public void setLeftOffset(float f) {
        this.leftOffset = f;
    }

    public void setSwipeClosesAllItemsWhenListMoves(boolean z) {
        this.swipeClosesAllItemsWhenListMoves = z;
    }

    public void setSwipeOpenOnLongPress(boolean z) {
        this.swipeOpenOnLongPress = z;
    }

    public void setSwipeMode(int i) {
        this.swipeMode = i;
    }

    protected boolean isSwipeEnabled() {
        return this.swipeMode != 0;
    }

    public int getSwipeActionLeft() {
        return this.swipeActionLeft;
    }

    public void setSwipeActionLeft(int i) {
        this.swipeActionLeft = i;
    }

    public int getSwipeActionRight() {
        return this.swipeActionRight;
    }

    public void setSwipeActionRight(int i) {
        this.swipeActionRight = i;
    }

    protected void setSwipeDrawableChecked(int i) {
        this.swipeDrawableChecked = i;
    }

    protected void setSwipeDrawableUnchecked(int i) {
        this.swipeDrawableUnchecked = i;
    }

    public void resetItems() {
        if (this.swipeListView.getAdapter() != null) {
            int itemCount = this.swipeListView.getAdapter().getItemCount();
            for (int size = this.opened.size(); size <= itemCount; size++) {
                this.opened.add(false);
                this.openedRight.add(false);
                this.checked.add(false);
            }
        }
    }

    protected void openAnimate(int i) {
        View viewFindViewById = this.swipeListView.getChildAt(i - this.mLayoutManager.findFirstVisibleItemPosition()).findViewById(this.swipeFrontView);
        if (viewFindViewById != null) {
            openAnimate(viewFindViewById, i);
        }
    }

    protected void closeAnimate(int i) {
        View viewFindViewById;
        if (this.swipeListView != null) {
            View childAt = this.swipeListView.getChildAt(i - this.mLayoutManager.findFirstVisibleItemPosition());
            if (childAt == null || (viewFindViewById = childAt.findViewById(this.swipeFrontView)) == null) {
                return;
            }
            closeAnimate(viewFindViewById, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void swapChoiceState(int i) {
        int countSelected = getCountSelected();
        boolean zBooleanValue = this.checked.get(i).booleanValue();
        this.checked.set(i, Boolean.valueOf(!zBooleanValue));
        int i2 = zBooleanValue ? countSelected - 1 : countSelected + 1;
        if (countSelected == 0 && i2 == 1) {
            this.swipeListView.onChoiceStarted();
            closeOpenedItems();
            setActionsTo(2);
        }
        if (countSelected == 1 && i2 == 0) {
            this.swipeListView.onChoiceEnded();
            returnOldActions();
        }
        this.swipeListView.onChoiceChanged(i, !zBooleanValue);
        reloadChoiceStateInView(this.frontView, i);
    }

    protected void unselectedChoiceStates() {
        int iFindFirstVisibleItemPosition = this.mLayoutManager.findFirstVisibleItemPosition();
        int iFindLastVisibleItemPosition = this.mLayoutManager.findLastVisibleItemPosition();
        for (int i = 0; i < this.checked.size(); i++) {
            if (this.checked.get(i).booleanValue() && i >= iFindFirstVisibleItemPosition && i <= iFindLastVisibleItemPosition) {
                reloadChoiceStateInView(this.swipeListView.getChildAt(i - iFindFirstVisibleItemPosition).findViewById(this.swipeFrontView), i);
            }
            this.checked.set(i, false);
        }
        this.swipeListView.onChoiceEnded();
        returnOldActions();
    }

    protected int dismiss(int i) {
        this.opened.remove(i);
        this.checked.remove(i);
        int iFindFirstVisibleItemPosition = this.mLayoutManager.findFirstVisibleItemPosition();
        int iFindLastVisibleItemPosition = this.mLayoutManager.findLastVisibleItemPosition();
        View childAt = this.swipeListView.getChildAt(i - iFindFirstVisibleItemPosition);
        this.dismissAnimationRefCount++;
        if (i >= iFindFirstVisibleItemPosition && i <= iFindLastVisibleItemPosition) {
            performDismiss(childAt, i, false);
            return childAt.getHeight();
        }
        this.pendingDismisses.add(new PendingDismissData(i, null));
        return 0;
    }

    protected void reloadChoiceStateInView(View view, int i) {
        if (isChecked(i)) {
            int i2 = this.swipeDrawableChecked;
            if (i2 > 0) {
                view.setBackgroundResource(i2);
                return;
            }
            return;
        }
        int i3 = this.swipeDrawableUnchecked;
        if (i3 > 0) {
            view.setBackgroundResource(i3);
        }
    }

    protected void reloadSwipeStateInView(View view, int i) {
        if (!this.opened.get(i).booleanValue()) {
            ViewHelper.setTranslationX(view, 0.0f);
        } else if (this.openedRight.get(i).booleanValue()) {
            ViewHelper.setTranslationX(view, this.swipeListView.getWidth());
        } else {
            ViewHelper.setTranslationX(view, -this.swipeListView.getWidth());
        }
    }

    protected boolean isChecked(int i) {
        return i < this.checked.size() && this.checked.get(i).booleanValue();
    }

    protected int getCountSelected() {
        int i = 0;
        for (int i2 = 0; i2 < this.checked.size(); i2++) {
            if (this.checked.get(i2).booleanValue()) {
                i++;
            }
        }
        return i;
    }

    protected List<Integer> getPositionsSelected() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.checked.size(); i++) {
            if (this.checked.get(i).booleanValue()) {
                arrayList.add(Integer.valueOf(i));
            }
        }
        return arrayList;
    }

    private void openAnimate(View view, int i) {
        if (this.opened.get(i).booleanValue()) {
            return;
        }
        generateRevealAnimate(view, true, false, i);
    }

    private void closeAnimate(View view, int i) {
        if (this.opened.get(i).booleanValue()) {
            generateRevealAnimate(view, true, false, i);
        }
    }

    private void generateAnimate(View view, boolean z, boolean z2, int i) {
        if (this.swipeCurrentAction == 0) {
            generateRevealAnimate(view, z, z2, i);
        }
        if (this.swipeCurrentAction == 1) {
            generateDismissAnimate(this.parentView, z, z2, i);
        }
        if (this.swipeCurrentAction == 2) {
            generateChoiceAnimate(view, i);
        }
    }

    private void generateChoiceAnimate(View view, int i) {
        ViewPropertyAnimator.animate(view).translationX(0.0f).setDuration(this.animationTime).setListener(new AnimatorListenerAdapter() { // from class: com.marshalchen.ultimaterecyclerview.swipelistview.SwipeListViewTouchListener.4
            @Override // com.nineoldandroids.animation.AnimatorListenerAdapter, com.nineoldandroids.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                SwipeListViewTouchListener.this.swipeListView.resetScrolling();
                SwipeListViewTouchListener.this.resetCell();
            }
        });
    }

    private void generateDismissAnimate(final View view, final boolean z, boolean z2, final int i) {
        float f;
        float f2;
        float f3;
        float f4;
        int i2;
        float f5;
        int i3 = 0;
        if (this.opened.get(i).booleanValue()) {
            if (!z) {
                if (this.openedRight.get(i).booleanValue()) {
                    f3 = this.viewWidth;
                    f4 = this.rightOffset;
                    f5 = f3 - f4;
                    i2 = (int) f5;
                } else {
                    f = -this.viewWidth;
                    f2 = this.leftOffset;
                    f5 = f + f2;
                    i2 = (int) f5;
                }
            }
            i2 = 0;
        } else {
            if (z) {
                if (z2) {
                    f3 = this.viewWidth;
                    f4 = this.rightOffset;
                    f5 = f3 - f4;
                    i2 = (int) f5;
                } else {
                    f = -this.viewWidth;
                    f2 = this.leftOffset;
                    f5 = f + f2;
                    i2 = (int) f5;
                }
            }
            i2 = 0;
        }
        if (z) {
            this.dismissAnimationRefCount++;
        } else {
            i3 = 1;
        }
        ViewPropertyAnimator.animate(view).translationX(i2).alpha(i3).setDuration(this.animationTime).setListener(new AnimatorListenerAdapter() { // from class: com.marshalchen.ultimaterecyclerview.swipelistview.SwipeListViewTouchListener.5
            @Override // com.nineoldandroids.animation.AnimatorListenerAdapter, com.nineoldandroids.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (z) {
                    SwipeListViewTouchListener.this.closeOpenedItems();
                    SwipeListViewTouchListener.this.performDismiss(view, i, true);
                }
                SwipeListViewTouchListener.this.resetCell();
            }
        });
    }

    private void generateRevealAnimate(View view, final boolean z, final boolean z2, final int i) {
        float f;
        float f2;
        float f3;
        float f4;
        int i2;
        float f5;
        if (this.opened.get(i).booleanValue()) {
            if (!z) {
                if (this.openedRight.get(i).booleanValue()) {
                    f3 = this.viewWidth;
                    f4 = this.rightOffset;
                    f5 = f3 - f4;
                    i2 = (int) f5;
                } else {
                    f = -this.viewWidth;
                    f2 = this.leftOffset;
                    f5 = f + f2;
                    i2 = (int) f5;
                }
            }
            i2 = 0;
        } else {
            if (z) {
                int i3 = this.viewWidth;
                if (z2) {
                    f3 = i3;
                    f4 = this.rightOffset;
                    f5 = f3 - f4;
                    i2 = (int) f5;
                } else {
                    f = -i3;
                    f2 = this.leftOffset;
                    f5 = f + f2;
                    i2 = (int) f5;
                }
            }
            i2 = 0;
        }
        final boolean z3 = !this.opened.get(i).booleanValue();
        if (this.onlyOneOpenedWhenSwipe && z) {
            this.opened.set(i, Boolean.valueOf(z3));
            this.openedRight.set(i, Boolean.valueOf(z2));
        }
        ViewPropertyAnimator.animate(view).translationX(i2).setDuration(this.animationTime).setListener(new AnimatorListenerAdapter() { // from class: com.marshalchen.ultimaterecyclerview.swipelistview.SwipeListViewTouchListener.6
            @Override // com.nineoldandroids.animation.AnimatorListenerAdapter, com.nineoldandroids.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                SwipeListViewTouchListener.this.swipeListView.resetScrolling();
                if (z) {
                    if (SwipeListViewTouchListener.this.onlyOneOpenedWhenSwipe) {
                        if (z3) {
                            SwipeListViewTouchListener.this.swipeListView.onOpened(i, z2);
                        } else {
                            SwipeListViewTouchListener.this.swipeListView.onClosed(i, ((Boolean) SwipeListViewTouchListener.this.openedRight.get(i)).booleanValue());
                        }
                    }
                    SwipeListViewTouchListener.this.opened.set(i, Boolean.valueOf(z3));
                    if (z3) {
                        SwipeListViewTouchListener.this.swipeListView.onOpened(i, z2);
                        SwipeListViewTouchListener.this.openedRight.set(i, Boolean.valueOf(z2));
                    } else {
                        SwipeListViewTouchListener.this.swipeListView.onClosed(i, ((Boolean) SwipeListViewTouchListener.this.openedRight.get(i)).booleanValue());
                    }
                }
                if (SwipeListViewTouchListener.this.onlyOneOpenedWhenSwipe) {
                    return;
                }
                SwipeListViewTouchListener.this.resetCell();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetCell() {
        if (this.downPosition != -1) {
            if (this.swipeCurrentAction == 2) {
                this.backView.setVisibility(0);
            }
            this.frontView.setClickable(this.opened.get(this.downPosition).booleanValue());
            this.frontView.setLongClickable(this.opened.get(this.downPosition).booleanValue());
            this.frontView = null;
            this.backView = null;
            this.downPosition = -1;
        }
    }

    public void setEnabled(boolean z) {
        this.paused = !z;
    }

    public RecyclerView.OnScrollListener makeScrollListener() {
        return new RecyclerView.OnScrollListener() { // from class: com.marshalchen.ultimaterecyclerview.swipelistview.SwipeListViewTouchListener.7
            private boolean isFirstItem = false;
            private boolean isLastItem = false;

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                SwipeListViewTouchListener.this.setEnabled(i != 1);
                if (SwipeListViewTouchListener.this.swipeClosesAllItemsWhenListMoves && i == 1) {
                    SwipeListViewTouchListener.this.closeOpenedItems();
                }
                if (i == 1) {
                    SwipeListViewTouchListener.this.listViewMoving = true;
                    SwipeListViewTouchListener.this.setEnabled(false);
                }
                if (i == 2 || i == 1) {
                    return;
                }
                SwipeListViewTouchListener.this.listViewMoving = false;
                SwipeListViewTouchListener.this.downPosition = -1;
                SwipeListViewTouchListener.this.swipeListView.resetScrolling();
                new Handler().postDelayed(new Runnable() { // from class: com.marshalchen.ultimaterecyclerview.swipelistview.SwipeListViewTouchListener.7.1
                    @Override // java.lang.Runnable
                    public void run() {
                        SwipeListViewTouchListener.this.setEnabled(true);
                    }
                }, 500L);
            }
        };
    }

    void closeOpenedItems() {
        if (this.opened != null) {
            int iFindFirstVisibleItemPosition = this.mLayoutManager.findFirstVisibleItemPosition();
            int iFindLastVisibleItemPosition = this.mLayoutManager.findLastVisibleItemPosition();
            for (int i = iFindFirstVisibleItemPosition; i <= iFindLastVisibleItemPosition; i++) {
                if (this.opened.get(i).booleanValue()) {
                    closeAnimate(this.swipeListView.getChildAt(i - iFindFirstVisibleItemPosition).findViewById(this.swipeFrontView), i);
                }
            }
        }
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        VelocityTracker velocityTracker;
        int i;
        int i2 = 0;
        if (!isSwipeEnabled()) {
            return false;
        }
        if (this.viewWidth < 2) {
            this.viewWidth = this.swipeListView.getWidth();
        }
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        if (actionMasked == 0) {
            if (this.paused && this.downPosition != -1) {
                return false;
            }
            this.swipeCurrentAction = 3;
            int childCount = this.swipeListView.getChildCount();
            int[] iArr = new int[2];
            this.swipeListView.getLocationOnScreen(iArr);
            int rawX = ((int) motionEvent.getRawX()) - iArr[0];
            int rawY = ((int) motionEvent.getRawY()) - iArr[1];
            while (true) {
                if (i2 >= childCount) {
                    break;
                }
                View childAt = this.swipeListView.getChildAt(i2);
                childAt.getHitRect(this.rect);
                int childPosition = this.swipeListView.getChildPosition(childAt);
                if (this.rect.contains(rawX, rawY)) {
                    setParentView(childAt);
                    setFrontView(childAt.findViewById(this.swipeFrontView), childPosition);
                    this.downX = motionEvent.getRawX();
                    this.downPosition = childPosition;
                    this.frontView.setClickable(!this.opened.get(childPosition).booleanValue());
                    this.frontView.setLongClickable(!this.opened.get(this.downPosition).booleanValue());
                    VelocityTracker velocityTrackerObtain = VelocityTracker.obtain();
                    this.velocityTracker = velocityTrackerObtain;
                    velocityTrackerObtain.addMovement(motionEvent);
                    int i3 = this.swipeBackView;
                    if (i3 > 0) {
                        setBackView(childAt.findViewById(i3));
                    }
                } else {
                    i2++;
                }
            }
            view.onTouchEvent(motionEvent);
            return true;
        }
        if (actionMasked != 1) {
            if (actionMasked == 2 && (velocityTracker = this.velocityTracker) != null && !this.paused && this.downPosition != -1) {
                velocityTracker.addMovement(motionEvent);
                this.velocityTracker.computeCurrentVelocity(1000);
                float fAbs = Math.abs(this.velocityTracker.getXVelocity());
                float fAbs2 = Math.abs(this.velocityTracker.getYVelocity());
                float rawX2 = motionEvent.getRawX() - this.downX;
                float fAbs3 = Math.abs(rawX2);
                int i4 = this.swipeMode;
                int iChangeSwipeMode = this.swipeListView.changeSwipeMode(this.downPosition);
                if (iChangeSwipeMode >= 0) {
                    i4 = iChangeSwipeMode;
                }
                if (i4 == 0 || (i4 != 1 && (!this.opened.get(this.downPosition).booleanValue() ? !((i4 != 3 || rawX2 <= 0.0f) && (i4 != 2 || rawX2 >= 0.0f)) : !((i4 != 3 || rawX2 >= 0.0f) && (i4 != 2 || rawX2 <= 0.0f))))) {
                    fAbs3 = 0.0f;
                }
                if (fAbs3 > this.slop && this.swipeCurrentAction == 3 && fAbs2 < fAbs) {
                    this.swiping = true;
                    this.swipingRight = rawX2 > 0.0f;
                    if (this.opened.get(this.downPosition).booleanValue()) {
                        this.swipeListView.onStartClose(this.downPosition, this.swipingRight);
                        this.swipeCurrentAction = 0;
                    } else {
                        if (this.swipingRight && this.swipeActionRight == 1) {
                            this.swipeCurrentAction = 1;
                        } else if (!this.swipingRight && this.swipeActionLeft == 1) {
                            this.swipeCurrentAction = 1;
                        } else if (this.swipingRight && this.swipeActionRight == 2) {
                            this.swipeCurrentAction = 2;
                        } else if (!this.swipingRight && this.swipeActionLeft == 2) {
                            this.swipeCurrentAction = 2;
                        } else {
                            this.swipeCurrentAction = 0;
                        }
                        this.swipeListView.onStartOpen(this.downPosition, this.swipeCurrentAction, this.swipingRight);
                    }
                    this.swipeListView.requestDisallowInterceptTouchEvent(true);
                    MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
                    motionEventObtain.setAction(3 | (MotionEventCompat.getActionIndex(motionEvent) << 8));
                    this.swipeListView.onTouchEvent(motionEventObtain);
                    if (this.swipeCurrentAction == 2) {
                        this.backView.setVisibility(8);
                    }
                }
                if (this.swiping && (i = this.downPosition) != -1) {
                    if (this.opened.get(i).booleanValue()) {
                        rawX2 += this.openedRight.get(this.downPosition).booleanValue() ? this.viewWidth - this.rightOffset : (-this.viewWidth) + this.leftOffset;
                    }
                    move(rawX2);
                    return true;
                }
            }
        } else if (this.velocityTracker != null && this.swiping && this.downPosition != -1) {
            float rawX3 = motionEvent.getRawX() - this.downX;
            this.velocityTracker.addMovement(motionEvent);
            this.velocityTracker.computeCurrentVelocity(1000);
            float fAbs4 = Math.abs(this.velocityTracker.getXVelocity());
            if (!this.opened.get(this.downPosition).booleanValue()) {
                if (this.swipeMode == 3 && this.velocityTracker.getXVelocity() > 0.0f) {
                    fAbs4 = 0.0f;
                }
                if (this.swipeMode == 2 && this.velocityTracker.getXVelocity() < 0.0f) {
                    fAbs4 = 0.0f;
                }
            }
            float fAbs5 = Math.abs(this.velocityTracker.getYVelocity());
            if (this.minFlingVelocity <= fAbs4 && fAbs4 <= this.maxFlingVelocity && fAbs5 * 2.0f < fAbs4) {
                z = this.velocityTracker.getXVelocity() > 0.0f;
                z2 = (z == this.swipingRight || this.swipeActionLeft == this.swipeActionRight) && !(this.opened.get(this.downPosition).booleanValue() && this.openedRight.get(this.downPosition).booleanValue() && z) && (!this.opened.get(this.downPosition).booleanValue() || this.openedRight.get(this.downPosition).booleanValue() || z);
            } else if (Math.abs(rawX3) > this.viewWidth / 2) {
                z = rawX3 > 0.0f;
            } else {
                z = false;
            }
            generateAnimate(this.frontView, z2, z, this.downPosition);
            if (this.swipeCurrentAction == 2) {
                swapChoiceState(this.downPosition);
            }
            this.velocityTracker.recycle();
            this.velocityTracker = null;
            this.downX = 0.0f;
            this.swiping = false;
        }
        if (!this.onlyOneOpenedWhenSwipe) {
            return false;
        }
        closeOtherOpenedItems();
        view.onTouchEvent(motionEvent);
        return true;
    }

    private void closeOtherOpenedItems() {
        if (this.opened == null || this.downPosition == -1) {
            return;
        }
        int iFindFirstVisibleItemPosition = this.mLayoutManager.findFirstVisibleItemPosition();
        int iFindLastVisibleItemPosition = this.mLayoutManager.findLastVisibleItemPosition();
        for (int i = iFindFirstVisibleItemPosition; i <= iFindLastVisibleItemPosition; i++) {
            if (this.opened.get(i).booleanValue() && i != this.downPosition) {
                closeAnimate(this.swipeListView.getChildAt(i - iFindFirstVisibleItemPosition).findViewById(this.swipeFrontView), i);
            }
        }
    }

    private void setActionsTo(int i) {
        this.oldSwipeActionRight = this.swipeActionRight;
        this.oldSwipeActionLeft = this.swipeActionLeft;
        this.swipeActionRight = i;
        this.swipeActionLeft = i;
    }

    protected void returnOldActions() {
        this.swipeActionRight = this.oldSwipeActionRight;
        this.swipeActionLeft = this.oldSwipeActionLeft;
    }

    public void move(float f) {
        boolean z;
        boolean z2;
        this.swipeListView.onMove(this.downPosition, f);
        float x = ViewHelper.getX(this.frontView);
        if (this.opened.get(this.downPosition).booleanValue()) {
            x += this.openedRight.get(this.downPosition).booleanValue() ? (-this.viewWidth) + this.rightOffset : this.viewWidth - this.leftOffset;
        }
        if (x > 0.0f && !(z2 = this.swipingRight)) {
            this.swipingRight = !z2;
            int i = this.swipeActionRight;
            this.swipeCurrentAction = i;
            if (i == 2) {
                this.backView.setVisibility(8);
            } else {
                this.backView.setVisibility(0);
            }
        }
        if (x < 0.0f && (z = this.swipingRight)) {
            this.swipingRight = !z;
            int i2 = this.swipeActionLeft;
            this.swipeCurrentAction = i2;
            if (i2 == 2) {
                this.backView.setVisibility(8);
            } else {
                this.backView.setVisibility(0);
            }
        }
        int i3 = this.swipeCurrentAction;
        if (i3 == 1) {
            ViewHelper.setTranslationX(this.parentView, f);
            ViewHelper.setAlpha(this.parentView, Math.max(0.0f, Math.min(1.0f, 1.0f - ((Math.abs(f) * 2.0f) / this.viewWidth))));
            return;
        }
        if (i3 == 2) {
            if ((!this.swipingRight || f <= 0.0f || x >= 80.0f) && ((this.swipingRight || f >= 0.0f || x <= -80.0f) && ((!this.swipingRight || f >= 80.0f) && (this.swipingRight || f <= -80.0f)))) {
                return;
            }
            ViewHelper.setTranslationX(this.frontView, f);
            return;
        }
        ViewHelper.setTranslationX(this.frontView, f);
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

    protected void performDismiss(final View view, int i, boolean z) {
        enableDisableViewGroup((ViewGroup) view, false);
        final ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        final int height = view.getHeight();
        ValueAnimator duration = ValueAnimator.ofInt(height, 1).setDuration(this.animationTime);
        if (z) {
            duration.addListener(new AnimatorListenerAdapter() { // from class: com.marshalchen.ultimaterecyclerview.swipelistview.SwipeListViewTouchListener.8
                @Override // com.nineoldandroids.animation.AnimatorListenerAdapter, com.nineoldandroids.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    SwipeListViewTouchListener.access$1006(SwipeListViewTouchListener.this);
                    if (SwipeListViewTouchListener.this.dismissAnimationRefCount == 0) {
                        SwipeListViewTouchListener.this.removePendingDismisses(height);
                    }
                }
            });
        }
        duration.addListener(new AnimatorListenerAdapter() { // from class: com.marshalchen.ultimaterecyclerview.swipelistview.SwipeListViewTouchListener.9
            @Override // com.nineoldandroids.animation.AnimatorListenerAdapter, com.nineoldandroids.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                SwipeListViewTouchListener.enableDisableViewGroup((ViewGroup) view, true);
            }
        });
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.marshalchen.ultimaterecyclerview.swipelistview.SwipeListViewTouchListener.10
            @Override // com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                view.setLayoutParams(layoutParams);
            }
        });
        this.pendingDismisses.add(new PendingDismissData(i, view));
        duration.start();
    }

    protected void resetPendingDismisses() {
        this.pendingDismisses.clear();
    }

    protected void handlerPendingDismisses(final int i) {
        new Handler().postDelayed(new Runnable() { // from class: com.marshalchen.ultimaterecyclerview.swipelistview.SwipeListViewTouchListener.11
            @Override // java.lang.Runnable
            public void run() {
                SwipeListViewTouchListener.this.removePendingDismisses(i);
            }
        }, this.animationTime + 100);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removePendingDismisses(int i) {
        Collections.sort(this.pendingDismisses);
        int[] iArr = new int[this.pendingDismisses.size()];
        for (int size = this.pendingDismisses.size() - 1; size >= 0; size--) {
            iArr[size] = this.pendingDismisses.get(size).position;
        }
        this.swipeListView.onDismiss(iArr);
        for (PendingDismissData pendingDismissData : this.pendingDismisses) {
            if (pendingDismissData.view != null) {
                ViewHelper.setAlpha(pendingDismissData.view, 1.0f);
                ViewHelper.setTranslationX(pendingDismissData.view, 0.0f);
                ViewGroup.LayoutParams layoutParams = pendingDismissData.view.getLayoutParams();
                layoutParams.height = i;
                pendingDismissData.view.setLayoutParams(layoutParams);
            }
        }
        resetPendingDismisses();
    }

    public static void enableDisableViewGroup(ViewGroup viewGroup, boolean z) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            childAt.setEnabled(z);
            if (childAt instanceof ViewGroup) {
                enableDisableViewGroup((ViewGroup) childAt, z);
            }
        }
    }
}
