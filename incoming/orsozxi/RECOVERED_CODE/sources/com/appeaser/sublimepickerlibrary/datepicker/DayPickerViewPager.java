package com.appeaser.sublimepickerlibrary.datepicker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.appeaser.sublimepickerlibrary.R;
import com.appeaser.sublimepickerlibrary.utilities.SUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/* loaded from: classes.dex */
class DayPickerViewPager extends ViewPager {
    private static final int NOT_SCROLLING = 0;
    private static final int SCROLLING_LEFT = -1;
    private static final int SCROLLING_RIGHT = 1;
    private static final String TAG = DayPickerViewPager.class.getSimpleName();
    private final int MONTH_SCROLL_THRESHOLD;
    private final int TOUCH_SLOP_SQUARED;
    private boolean mAlreadyTriedAccessingMethod;
    private boolean mCanPickRange;
    private CheckForLongPress mCheckForLongPress;
    private DayPickerPagerAdapter mDayPickerPagerAdapter;
    private float mInitialDownX;
    private float mInitialDownY;
    private boolean mIsLongPressed;
    private final ArrayList<View> mMatchParentChildren;
    private Method mPopulateMethod;
    private ScrollerRunnable mScrollerRunnable;
    private int mScrollingDirection;
    private SelectedDate mTempSelectedDate;

    public DayPickerViewPager(Context context) {
        this(context, null);
    }

    public DayPickerViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMatchParentChildren = new ArrayList<>(1);
        this.mIsLongPressed = false;
        this.mScrollingDirection = 0;
        this.TOUCH_SLOP_SQUARED = ViewConfiguration.get(context).getScaledTouchSlop() * ViewConfiguration.get(context).getScaledTouchSlop();
        this.MONTH_SCROLL_THRESHOLD = context.getResources().getDimensionPixelSize(R.dimen.sp_month_scroll_threshold);
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    protected void onMeasure(int i, int i2) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        int childMeasureSpec;
        int childMeasureSpec2;
        Drawable foreground;
        callPopulate();
        int childCount = getChildCount();
        boolean z = (View.MeasureSpec.getMode(i) == 1073741824 && View.MeasureSpec.getMode(i2) == 1073741824) ? false : true;
        int iMax = 0;
        int iMax2 = 0;
        int iCombineMeasuredStates = 0;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                measureChild(childAt, i, i2);
                ViewPager.LayoutParams layoutParams = (ViewPager.LayoutParams) childAt.getLayoutParams();
                iMax = Math.max(iMax, childAt.getMeasuredWidth());
                iMax2 = Math.max(iMax2, childAt.getMeasuredHeight());
                iCombineMeasuredStates = combineMeasuredStates(iCombineMeasuredStates, childAt.getMeasuredState());
                if (z && (layoutParams.width == -1 || layoutParams.height == -1)) {
                    this.mMatchParentChildren.add(childAt);
                }
            }
        }
        int paddingLeft = iMax + getPaddingLeft() + getPaddingRight();
        int iMax3 = Math.max(iMax2 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight());
        int iMax4 = Math.max(paddingLeft, getSuggestedMinimumWidth());
        if (SUtils.isApi_23_OrHigher() && (foreground = getForeground()) != null) {
            iMax3 = Math.max(iMax3, foreground.getMinimumHeight());
            iMax4 = Math.max(iMax4, foreground.getMinimumWidth());
        }
        setMeasuredDimension(resolveSizeAndState(iMax4, i, iCombineMeasuredStates), resolveSizeAndState(iMax3, i2, iCombineMeasuredStates << 16));
        int size = this.mMatchParentChildren.size();
        if (size > 1) {
            for (int i4 = 0; i4 < size; i4++) {
                View view = this.mMatchParentChildren.get(i4);
                ViewPager.LayoutParams layoutParams2 = (ViewPager.LayoutParams) view.getLayoutParams();
                if (layoutParams2.width == -1) {
                    childMeasureSpec = View.MeasureSpec.makeMeasureSpec((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), BasicMeasure.EXACTLY);
                } else {
                    childMeasureSpec = getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight(), layoutParams2.width);
                }
                if (layoutParams2.height == -1) {
                    childMeasureSpec2 = View.MeasureSpec.makeMeasureSpec((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), BasicMeasure.EXACTLY);
                } else {
                    childMeasureSpec2 = getChildMeasureSpec(i2, getPaddingTop() + getPaddingBottom(), layoutParams2.height);
                }
                view.measure(childMeasureSpec, childMeasureSpec2);
            }
        }
        this.mMatchParentChildren.clear();
    }

    private void initializePopulateMethod() throws NoSuchMethodException, SecurityException {
        try {
            Method declaredMethod = ViewPager.class.getDeclaredMethod("populate", (Class[]) null);
            this.mPopulateMethod = declaredMethod;
            declaredMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        this.mAlreadyTriedAccessingMethod = true;
    }

    private void callPopulate() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (!this.mAlreadyTriedAccessingMethod) {
            initializePopulateMethod();
        }
        Method method = this.mPopulateMethod;
        if (method != null) {
            try {
                method.invoke(this, new Object[0]);
                return;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return;
            } catch (InvocationTargetException e2) {
                e2.printStackTrace();
                return;
            }
        }
        Log.e(TAG, "Could not call `ViewPager.populate()`");
    }

    protected void setCanPickRange(boolean z) {
        this.mCanPickRange = z;
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        CheckForLongPress checkForLongPress;
        if (!this.mCanPickRange) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        if (motionEvent.getAction() == 0) {
            this.mInitialDownX = motionEvent.getX();
            this.mInitialDownY = motionEvent.getY();
            if (this.mCheckForLongPress == null) {
                this.mCheckForLongPress = new CheckForLongPress();
            }
            postDelayed(this.mCheckForLongPress, ViewConfiguration.getLongPressTimeout());
        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            CheckForLongPress checkForLongPress2 = this.mCheckForLongPress;
            if (checkForLongPress2 != null) {
                removeCallbacks(checkForLongPress2);
            }
            this.mIsLongPressed = false;
            this.mInitialDownX = -1.0f;
            this.mInitialDownY = -1.0f;
        } else if (motionEvent.getAction() == 2 && !isStillALongPress((int) motionEvent.getX(), (int) motionEvent.getY()) && (checkForLongPress = this.mCheckForLongPress) != null) {
            removeCallbacks(checkForLongPress);
        }
        return this.mIsLongPressed || super.onInterceptTouchEvent(motionEvent);
    }

    private boolean isStillALongPress(int i, int i2) {
        float f = i;
        float f2 = this.mInitialDownX;
        float f3 = (f - f2) * (f - f2);
        float f4 = i2;
        float f5 = this.mInitialDownY;
        return f3 + ((f4 - f5) * (f4 - f5)) <= ((float) this.TOUCH_SLOP_SQUARED);
    }

    private class CheckForLongPress implements Runnable {
        private CheckForLongPress() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (DayPickerViewPager.this.mDayPickerPagerAdapter != null) {
                DayPickerViewPager dayPickerViewPager = DayPickerViewPager.this;
                dayPickerViewPager.mTempSelectedDate = dayPickerViewPager.mDayPickerPagerAdapter.resolveStartDateForRange((int) DayPickerViewPager.this.mInitialDownX, (int) DayPickerViewPager.this.mInitialDownY, DayPickerViewPager.this.getCurrentItem());
                if (DayPickerViewPager.this.mTempSelectedDate != null) {
                    DayPickerViewPager.this.mIsLongPressed = true;
                    DayPickerViewPager.this.mDayPickerPagerAdapter.onDateRangeSelectionStarted(DayPickerViewPager.this.mTempSelectedDate);
                }
            }
        }
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        ScrollerRunnable scrollerRunnable;
        DayPickerPagerAdapter dayPickerPagerAdapter;
        if (!this.mCanPickRange) {
            return super.onTouchEvent(motionEvent);
        }
        CheckForLongPress checkForLongPress = this.mCheckForLongPress;
        if (checkForLongPress != null) {
            removeCallbacks(checkForLongPress);
        }
        if ((this.mIsLongPressed && motionEvent.getAction() == 1) || motionEvent.getAction() == 3) {
            if (motionEvent.getAction() == 1 && (dayPickerPagerAdapter = this.mDayPickerPagerAdapter) != null) {
                SelectedDate selectedDateResolveEndDateForRange = dayPickerPagerAdapter.resolveEndDateForRange((int) motionEvent.getX(), (int) motionEvent.getY(), getCurrentItem(), false);
                this.mTempSelectedDate = selectedDateResolveEndDateForRange;
                this.mDayPickerPagerAdapter.onDateRangeSelectionEnded(selectedDateResolveEndDateForRange);
            }
            this.mIsLongPressed = false;
            this.mInitialDownX = -1.0f;
            this.mInitialDownY = -1.0f;
            this.mScrollingDirection = 0;
            ScrollerRunnable scrollerRunnable2 = this.mScrollerRunnable;
            if (scrollerRunnable2 != null) {
                removeCallbacks(scrollerRunnable2);
            }
        } else if (this.mIsLongPressed && motionEvent.getAction() == 0) {
            this.mScrollingDirection = 0;
        } else if (this.mIsLongPressed && motionEvent.getAction() == 2) {
            int iResolveDirectionForScroll = resolveDirectionForScroll(motionEvent.getX());
            boolean z = this.mScrollingDirection != iResolveDirectionForScroll;
            if (z && (scrollerRunnable = this.mScrollerRunnable) != null) {
                removeCallbacks(scrollerRunnable);
            }
            if (this.mScrollerRunnable == null) {
                this.mScrollerRunnable = new ScrollerRunnable();
            }
            this.mScrollingDirection = iResolveDirectionForScroll;
            if (iResolveDirectionForScroll == 0) {
                DayPickerPagerAdapter dayPickerPagerAdapter2 = this.mDayPickerPagerAdapter;
                if (dayPickerPagerAdapter2 != null) {
                    SelectedDate selectedDateResolveEndDateForRange2 = dayPickerPagerAdapter2.resolveEndDateForRange((int) motionEvent.getX(), (int) motionEvent.getY(), getCurrentItem(), true);
                    this.mTempSelectedDate = selectedDateResolveEndDateForRange2;
                    if (selectedDateResolveEndDateForRange2 != null) {
                        this.mDayPickerPagerAdapter.onDateRangeSelectionUpdated(selectedDateResolveEndDateForRange2);
                    }
                }
            } else if (z) {
                post(this.mScrollerRunnable);
            }
        }
        return this.mIsLongPressed || super.onTouchEvent(motionEvent);
    }

    private int resolveDirectionForScroll(float f) {
        if (f - getLeft() < this.MONTH_SCROLL_THRESHOLD) {
            return -1;
        }
        return ((float) getRight()) - f < ((float) this.MONTH_SCROLL_THRESHOLD) ? 1 : 0;
    }

    private class ScrollerRunnable implements Runnable {
        private ScrollerRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (DayPickerViewPager.this.mScrollingDirection == 0) {
                return;
            }
            int i = DayPickerViewPager.this.mScrollingDirection;
            DayPickerViewPager dayPickerViewPager = DayPickerViewPager.this;
            dayPickerViewPager.setCurrentItem(dayPickerViewPager.getCurrentItem() + i, true);
            DayPickerViewPager.this.postDelayed(this, 1000L);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager
    public void setAdapter(PagerAdapter pagerAdapter) throws Resources.NotFoundException {
        super.setAdapter(pagerAdapter);
        if (pagerAdapter instanceof DayPickerPagerAdapter) {
            this.mDayPickerPagerAdapter = (DayPickerPagerAdapter) pagerAdapter;
        }
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) throws Resources.NotFoundException {
        super.onRestoreInstanceState(parcelable);
    }
}
