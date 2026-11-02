package com.marshalchen.ultimaterecyclerview.swipelistview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewConfigurationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.marshalchen.ultimaterecyclerview.R;
import java.util.List;

/* loaded from: classes2.dex */
public class SwipeListView extends RecyclerView {
    public static final boolean DEBUG = false;
    public static final int SWIPE_ACTION_CHOICE = 2;
    public static final int SWIPE_ACTION_DISMISS = 1;
    public static final int SWIPE_ACTION_NONE = 3;
    public static final int SWIPE_ACTION_REVEAL = 0;
    public static final String SWIPE_DEFAULT_BACK_VIEW = "swipelist_backview";
    public static final String SWIPE_DEFAULT_FRONT_VIEW = "swipelist_frontview";
    public static final int SWIPE_MODE_BOTH = 1;
    public static final int SWIPE_MODE_DEFAULT = -1;
    public static final int SWIPE_MODE_LEFT = 3;
    public static final int SWIPE_MODE_NONE = 0;
    public static final int SWIPE_MODE_RIGHT = 2;
    public static final String TAG = "SwipeListView";
    private static final int TOUCH_STATE_REST = 0;
    private static final int TOUCH_STATE_SCROLLING_X = 1;
    private static final int TOUCH_STATE_SCROLLING_Y = 2;
    private float lastMotionX;
    private float lastMotionY;
    private LinearLayoutManager mLayoutManager;
    int swipeBackView;
    int swipeFrontView;
    public SwipeListViewListener swipeListViewListener;
    private SwipeListViewTouchListener touchListener;
    private int touchSlop;
    private int touchState;

    public SwipeListView(Context context, int i, int i2) {
        super(context);
        this.touchState = 0;
        this.swipeFrontView = 0;
        this.swipeBackView = 0;
        this.swipeFrontView = i2;
        this.swipeBackView = i;
    }

    public SwipeListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.touchState = 0;
        this.swipeFrontView = 0;
        this.swipeBackView = 0;
    }

    public SwipeListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.touchState = 0;
        this.swipeFrontView = 0;
        this.swipeBackView = 0;
    }

    public void init(AttributeSet attributeSet) {
        float f;
        int resourceId;
        int resourceId2;
        int i;
        int i2;
        int i3;
        boolean z;
        boolean z2;
        long integer;
        boolean z3;
        int i4;
        float f2 = 0.0f;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.SwipeListView);
            i = typedArrayObtainStyledAttributes.getInt(R.styleable.SwipeListView_swipeMode, 1);
            i2 = typedArrayObtainStyledAttributes.getInt(R.styleable.SwipeListView_swipeActionLeft, 0);
            i3 = typedArrayObtainStyledAttributes.getInt(R.styleable.SwipeListView_swipeActionRight, 0);
            z = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SwipeListView_onlyOneOpenedWhenSwipe, false);
            float dimension = typedArrayObtainStyledAttributes.getDimension(R.styleable.SwipeListView_swipeOffsetLeft, 0.0f);
            float dimension2 = typedArrayObtainStyledAttributes.getDimension(R.styleable.SwipeListView_swipeOffsetRight, 0.0f);
            z2 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SwipeListView_swipeOpenOnLongPress, true);
            integer = typedArrayObtainStyledAttributes.getInteger(R.styleable.SwipeListView_swipeAnimationTime, 0);
            z3 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SwipeListView_swipeCloseAllItemsWhenMoveList, true);
            resourceId2 = typedArrayObtainStyledAttributes.getResourceId(R.styleable.SwipeListView_swipeDrawableChecked, 0);
            resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.SwipeListView_swipeDrawableUnchecked, 0);
            this.swipeFrontView = typedArrayObtainStyledAttributes.getResourceId(R.styleable.SwipeListView_swipeFrontView, 0);
            this.swipeBackView = typedArrayObtainStyledAttributes.getResourceId(R.styleable.SwipeListView_swipeBackView, 0);
            typedArrayObtainStyledAttributes.recycle();
            f = dimension2;
            f2 = dimension;
        } else {
            f = 0.0f;
            resourceId = 0;
            resourceId2 = 0;
            i = 1;
            i2 = 0;
            i3 = 0;
            z = false;
            z2 = true;
            integer = 0;
            z3 = true;
        }
        if (this.swipeFrontView == 0 || this.swipeBackView == 0) {
            i4 = resourceId;
            this.swipeFrontView = getContext().getResources().getIdentifier(SWIPE_DEFAULT_FRONT_VIEW, "id", getContext().getPackageName());
            int identifier = getContext().getResources().getIdentifier(SWIPE_DEFAULT_BACK_VIEW, "id", getContext().getPackageName());
            this.swipeBackView = identifier;
            if (this.swipeFrontView == 0 || identifier == 0) {
                throw new RuntimeException(String.format("You forgot the attributes swipeFrontView or swipeBackView. You can add this attributes or use '%s' and '%s' identifiers", SWIPE_DEFAULT_FRONT_VIEW, SWIPE_DEFAULT_BACK_VIEW));
            }
        } else {
            i4 = resourceId;
        }
        this.touchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(getContext()));
        SwipeListViewTouchListener swipeListViewTouchListener = new SwipeListViewTouchListener(this, this.swipeFrontView, this.swipeBackView);
        this.touchListener = swipeListViewTouchListener;
        if (integer > 0) {
            swipeListViewTouchListener.setAnimationTime(integer);
        }
        this.touchListener.setRightOffset(f);
        this.touchListener.setLeftOffset(f2);
        this.touchListener.setSwipeActionLeft(i2);
        this.touchListener.setSwipeActionRight(i3);
        this.touchListener.setSwipeMode(i);
        this.touchListener.setOnlyOneOpenedWhenSwipe(z);
        this.touchListener.setSwipeClosesAllItemsWhenListMoves(z3);
        this.touchListener.setSwipeOpenOnLongPress(z2);
        this.touchListener.setSwipeDrawableChecked(resourceId2);
        this.touchListener.setSwipeDrawableUnchecked(i4);
        setOnTouchListener(this.touchListener);
        setOnScrollListener(this.touchListener.makeScrollListener());
    }

    public void recycle(View view, int i) {
        this.touchListener.reloadChoiceStateInView(view.findViewById(this.swipeFrontView), i);
        this.touchListener.reloadSwipeStateInView(view.findViewById(this.swipeFrontView), i);
        int i2 = 0;
        while (true) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (i2 >= viewGroup.getChildCount()) {
                return;
            }
            viewGroup.getChildAt(i2).setPressed(false);
            i2++;
        }
    }

    public boolean isChecked(int i) {
        return this.touchListener.isChecked(i);
    }

    public List<Integer> getPositionsSelected() {
        return this.touchListener.getPositionsSelected();
    }

    public int getCountSelected() {
        return this.touchListener.getCountSelected();
    }

    public void unselectedChoiceStates() {
        this.touchListener.unselectedChoiceStates();
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void setAdapter(RecyclerView.Adapter adapter) {
        super.setAdapter(adapter);
        this.touchListener.resetItems();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() { // from class: com.marshalchen.ultimaterecyclerview.swipelistview.SwipeListView.1
            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                super.onChanged();
                SwipeListView.this.onListChanged();
                SwipeListView.this.touchListener.resetItems();
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        super.setLayoutManager(layoutManager);
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
        this.mLayoutManager = linearLayoutManager;
        SwipeListViewTouchListener swipeListViewTouchListener = this.touchListener;
        if (swipeListViewTouchListener != null) {
            swipeListViewTouchListener.setLayoutManager(linearLayoutManager);
        }
    }

    public void dismiss(int i) {
        int iDismiss = this.touchListener.dismiss(i);
        if (iDismiss > 0) {
            this.touchListener.handlerPendingDismisses(iDismiss);
        } else {
            onDismiss(new int[]{i});
            this.touchListener.resetPendingDismisses();
        }
    }

    public void dismissSelected() {
        List<Integer> positionsSelected = this.touchListener.getPositionsSelected();
        int[] iArr = new int[positionsSelected.size()];
        int i = 0;
        for (int i2 = 0; i2 < positionsSelected.size(); i2++) {
            int iIntValue = positionsSelected.get(i2).intValue();
            iArr[i2] = iIntValue;
            int iDismiss = this.touchListener.dismiss(iIntValue);
            if (iDismiss > 0) {
                i = iDismiss;
            }
        }
        if (i > 0) {
            this.touchListener.handlerPendingDismisses(i);
        } else {
            onDismiss(iArr);
            this.touchListener.resetPendingDismisses();
        }
        this.touchListener.returnOldActions();
    }

    public void openAnimate(int i) {
        this.touchListener.openAnimate(i);
    }

    public void closeAnimate(int i) {
        this.touchListener.closeAnimate(i);
    }

    protected void onDismiss(int[] iArr) {
        SwipeListViewListener swipeListViewListener = this.swipeListViewListener;
        if (swipeListViewListener != null) {
            swipeListViewListener.onDismiss(iArr);
        }
    }

    protected void onStartOpen(int i, int i2, boolean z) {
        SwipeListViewListener swipeListViewListener = this.swipeListViewListener;
        if (swipeListViewListener == null || i == -1) {
            return;
        }
        swipeListViewListener.onStartOpen(i, i2, z);
    }

    protected void onStartClose(int i, boolean z) {
        SwipeListViewListener swipeListViewListener = this.swipeListViewListener;
        if (swipeListViewListener == null || i == -1) {
            return;
        }
        swipeListViewListener.onStartClose(i, z);
    }

    protected void onClickFrontView(int i) {
        SwipeListViewListener swipeListViewListener = this.swipeListViewListener;
        if (swipeListViewListener == null || i == -1) {
            return;
        }
        swipeListViewListener.onClickFrontView(i);
    }

    protected void onClickBackView(int i) {
        SwipeListViewListener swipeListViewListener = this.swipeListViewListener;
        if (swipeListViewListener == null || i == -1) {
            return;
        }
        swipeListViewListener.onClickBackView(i);
    }

    protected void onOpened(int i, boolean z) {
        SwipeListViewListener swipeListViewListener = this.swipeListViewListener;
        if (swipeListViewListener == null || i == -1) {
            return;
        }
        swipeListViewListener.onOpened(i, z);
    }

    protected void onClosed(int i, boolean z) {
        SwipeListViewListener swipeListViewListener = this.swipeListViewListener;
        if (swipeListViewListener == null || i == -1) {
            return;
        }
        swipeListViewListener.onClosed(i, z);
    }

    protected void onChoiceChanged(int i, boolean z) {
        SwipeListViewListener swipeListViewListener = this.swipeListViewListener;
        if (swipeListViewListener == null || i == -1) {
            return;
        }
        swipeListViewListener.onChoiceChanged(i, z);
    }

    protected void onChoiceStarted() {
        SwipeListViewListener swipeListViewListener = this.swipeListViewListener;
        if (swipeListViewListener != null) {
            swipeListViewListener.onChoiceStarted();
        }
    }

    protected void onChoiceEnded() {
        SwipeListViewListener swipeListViewListener = this.swipeListViewListener;
        if (swipeListViewListener != null) {
            swipeListViewListener.onChoiceEnded();
        }
    }

    protected void onFirstListItem() {
        SwipeListViewListener swipeListViewListener = this.swipeListViewListener;
        if (swipeListViewListener != null) {
            swipeListViewListener.onFirstListItem();
        }
    }

    protected void onLastListItem() {
        SwipeListViewListener swipeListViewListener = this.swipeListViewListener;
        if (swipeListViewListener != null) {
            swipeListViewListener.onLastListItem();
        }
    }

    protected void onListChanged() {
        SwipeListViewListener swipeListViewListener = this.swipeListViewListener;
        if (swipeListViewListener != null) {
            swipeListViewListener.onListChanged();
        }
    }

    protected void onMove(int i, float f) {
        SwipeListViewListener swipeListViewListener = this.swipeListViewListener;
        if (swipeListViewListener == null || i == -1) {
            return;
        }
        swipeListViewListener.onMove(i, f);
    }

    protected int changeSwipeMode(int i) {
        SwipeListViewListener swipeListViewListener = this.swipeListViewListener;
        if (swipeListViewListener == null || i == -1) {
            return -1;
        }
        return swipeListViewListener.onChangeSwipeMode(i);
    }

    public void setSwipeListViewListener(SwipeListViewListener swipeListViewListener) {
        this.swipeListViewListener = swipeListViewListener;
    }

    public void resetScrolling() {
        this.touchState = 0;
    }

    public void setOffsetRight(float f) {
        this.touchListener.setRightOffset(f);
    }

    public void setOffsetLeft(float f) {
        this.touchListener.setLeftOffset(f);
    }

    public void setOnlyOneOpenedWhenSwipe(boolean z) {
        this.touchListener.setOnlyOneOpenedWhenSwipe(z);
    }

    public void setSwipeCloseAllItemsWhenMoveList(boolean z) {
        this.touchListener.setSwipeClosesAllItemsWhenListMoves(z);
    }

    public void setSwipeOpenOnLongPress(boolean z) {
        this.touchListener.setSwipeOpenOnLongPress(z);
    }

    public void setSwipeMode(int i) {
        this.touchListener.setSwipeMode(i);
    }

    public int getSwipeActionLeft() {
        return this.touchListener.getSwipeActionLeft();
    }

    public void setSwipeActionLeft(int i) {
        this.touchListener.setSwipeActionLeft(i);
    }

    public int getSwipeActionRight() {
        return this.touchListener.getSwipeActionRight();
    }

    public void setSwipeActionRight(int i) {
        this.touchListener.setSwipeActionRight(i);
    }

    public void setAnimationTime(long j) {
        this.touchListener.setAnimationTime(j);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (isEnabled() && this.touchListener.isSwipeEnabled()) {
            if (this.touchState == 1) {
                return this.touchListener.onTouch(this, motionEvent);
            }
            if (actionMasked == 0) {
                super.onInterceptTouchEvent(motionEvent);
                this.touchListener.onTouch(this, motionEvent);
                this.touchState = 0;
                this.lastMotionX = x;
                this.lastMotionY = y;
                return false;
            }
            if (actionMasked == 1) {
                this.touchListener.onTouch(this, motionEvent);
                return this.touchState == 2;
            }
            if (actionMasked == 2) {
                checkInMoving(x, y);
                return this.touchState == 2;
            }
            if (actionMasked == 3) {
                this.touchState = 0;
            }
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    private void checkInMoving(float f, float f2) {
        int iAbs = (int) Math.abs(f - this.lastMotionX);
        int iAbs2 = (int) Math.abs(f2 - this.lastMotionY);
        int i = this.touchSlop;
        boolean z = iAbs > i;
        boolean z2 = iAbs2 > i;
        if (z) {
            this.touchState = 1;
            this.lastMotionX = f;
            this.lastMotionY = f2;
        }
        if (z2) {
            this.touchState = 2;
            this.lastMotionX = f;
            this.lastMotionY = f2;
        }
    }

    public void closeOpenedItems() {
        this.touchListener.closeOpenedItems();
    }
}
