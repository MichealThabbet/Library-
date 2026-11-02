package com.appeaser.sublimepickerlibrary.datepicker;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageButton;
import androidx.viewpager.widget.ViewPager;
import com.appeaser.sublimepickerlibrary.datepicker.DayPickerPagerAdapter;
import com.appeaser.sublimepickerlibrary.utilities.SUtils;
import java.util.Calendar;

/* loaded from: classes.dex */
class DayPickerView extends ViewGroup {
    private final AccessibilityManager mAccessibilityManager;
    private final DayPickerPagerAdapter mAdapter;
    private final Calendar mMaxDate;
    private final Calendar mMinDate;
    private final ImageButton mNextButton;
    private final ImageButton mPrevButton;
    private ProxyDaySelectionEventListener mProxyDaySelectionEventListener;
    private SelectedDate mSelectedDay;
    private Calendar mTempCalendar;
    private final DayPickerViewPager mViewPager;
    private static final String TAG = DayPickerView.class.getSimpleName();
    private static final int[] ATTRS_TEXT_COLOR = {R.attr.textColor};

    public interface ProxyDaySelectionEventListener {
        void onDateRangeSelectionEnded(SelectedDate selectedDate);

        void onDateRangeSelectionStarted(SelectedDate selectedDate);

        void onDateRangeSelectionUpdated(SelectedDate selectedDate);

        void onDaySelected(DayPickerView dayPickerView, Calendar calendar);
    }

    public DayPickerView(Context context) {
        this(context, null);
    }

    public DayPickerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.appeaser.sublimepickerlibrary.R.attr.spDayPickerStyle);
    }

    public DayPickerView(Context context, AttributeSet attributeSet, int i) throws Resources.NotFoundException {
        int i2;
        int i3;
        super(SUtils.createThemeWrapper(context, com.appeaser.sublimepickerlibrary.R.attr.sublimePickerStyle, com.appeaser.sublimepickerlibrary.R.style.SublimePickerStyleLight, i, com.appeaser.sublimepickerlibrary.R.style.DayPickerViewStyle), attributeSet);
        this.mSelectedDay = null;
        this.mMinDate = Calendar.getInstance();
        this.mMaxDate = Calendar.getInstance();
        Context context2 = getContext();
        this.mAccessibilityManager = (AccessibilityManager) context2.getSystemService("accessibility");
        TypedArray typedArrayObtainStyledAttributes = context2.obtainStyledAttributes(attributeSet, com.appeaser.sublimepickerlibrary.R.styleable.DayPickerView, i, com.appeaser.sublimepickerlibrary.R.style.DayPickerViewStyle);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(com.appeaser.sublimepickerlibrary.R.styleable.DayPickerView_spMonthTextAppearance, com.appeaser.sublimepickerlibrary.R.style.SPMonthLabelTextAppearance);
        int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(com.appeaser.sublimepickerlibrary.R.styleable.DayPickerView_spWeekDayTextAppearance, com.appeaser.sublimepickerlibrary.R.style.SPWeekDayLabelTextAppearance);
        int resourceId3 = typedArrayObtainStyledAttributes.getResourceId(com.appeaser.sublimepickerlibrary.R.styleable.DayPickerView_spDateTextAppearance, com.appeaser.sublimepickerlibrary.R.style.SPDayTextAppearance);
        ColorStateList colorStateList = typedArrayObtainStyledAttributes.getColorStateList(com.appeaser.sublimepickerlibrary.R.styleable.DayPickerView_spDaySelectorColor);
        typedArrayObtainStyledAttributes.recycle();
        DayPickerPagerAdapter dayPickerPagerAdapter = new DayPickerPagerAdapter(context2, com.appeaser.sublimepickerlibrary.R.layout.date_picker_month_item, com.appeaser.sublimepickerlibrary.R.id.month_view);
        this.mAdapter = dayPickerPagerAdapter;
        dayPickerPagerAdapter.setMonthTextAppearance(resourceId);
        this.mAdapter.setDayOfWeekTextAppearance(resourceId2);
        this.mAdapter.setDayTextAppearance(resourceId3);
        this.mAdapter.setDaySelectorColor(colorStateList);
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(context2);
        if (getTag() != null && (getTag() instanceof String) && getResources().getString(com.appeaser.sublimepickerlibrary.R.string.recurrence_end_date_picker_tag).equals(getTag())) {
            i2 = com.appeaser.sublimepickerlibrary.R.layout.day_picker_content_redp;
            i3 = com.appeaser.sublimepickerlibrary.R.id.redp_view_pager;
        } else {
            i2 = com.appeaser.sublimepickerlibrary.R.layout.day_picker_content_sdp;
            i3 = com.appeaser.sublimepickerlibrary.R.id.sdp_view_pager;
        }
        layoutInflaterFrom.inflate(i2, (ViewGroup) this, true);
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.appeaser.sublimepickerlibrary.datepicker.DayPickerView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int i4;
                if (view == DayPickerView.this.mPrevButton) {
                    i4 = -1;
                } else if (view != DayPickerView.this.mNextButton) {
                    return;
                } else {
                    i4 = 1;
                }
                DayPickerView.this.mViewPager.setCurrentItem(DayPickerView.this.mViewPager.getCurrentItem() + i4, !DayPickerView.this.mAccessibilityManager.isEnabled());
            }
        };
        ImageButton imageButton = (ImageButton) findViewById(com.appeaser.sublimepickerlibrary.R.id.prev);
        this.mPrevButton = imageButton;
        imageButton.setOnClickListener(onClickListener);
        ImageButton imageButton2 = (ImageButton) findViewById(com.appeaser.sublimepickerlibrary.R.id.next);
        this.mNextButton = imageButton2;
        imageButton2.setOnClickListener(onClickListener);
        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.appeaser.sublimepickerlibrary.datepicker.DayPickerView.2
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i4) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i4, float f, int i5) {
                float fAbs = Math.abs(0.5f - f) * 2.0f;
                DayPickerView.this.mPrevButton.setAlpha(fAbs);
                DayPickerView.this.mNextButton.setAlpha(fAbs);
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i4) {
                DayPickerView.this.updateButtonVisibility(i4);
            }
        };
        DayPickerViewPager dayPickerViewPager = (DayPickerViewPager) findViewById(i3);
        this.mViewPager = dayPickerViewPager;
        dayPickerViewPager.setAdapter(this.mAdapter);
        this.mViewPager.addOnPageChangeListener(onPageChangeListener);
        if (resourceId != 0) {
            TypedArray typedArrayObtainStyledAttributes2 = context2.obtainStyledAttributes(null, ATTRS_TEXT_COLOR, 0, resourceId);
            ColorStateList colorStateList2 = typedArrayObtainStyledAttributes2.getColorStateList(0);
            if (colorStateList2 != null) {
                SUtils.setImageTintList(this.mPrevButton, colorStateList2);
                SUtils.setImageTintList(this.mNextButton, colorStateList2);
            }
            typedArrayObtainStyledAttributes2.recycle();
        }
        this.mAdapter.setDaySelectionEventListener(new DayPickerPagerAdapter.DaySelectionEventListener() { // from class: com.appeaser.sublimepickerlibrary.datepicker.DayPickerView.3
            @Override // com.appeaser.sublimepickerlibrary.datepicker.DayPickerPagerAdapter.DaySelectionEventListener
            public void onDaySelected(DayPickerPagerAdapter dayPickerPagerAdapter2, Calendar calendar) {
                if (DayPickerView.this.mProxyDaySelectionEventListener != null) {
                    DayPickerView.this.mProxyDaySelectionEventListener.onDaySelected(DayPickerView.this, calendar);
                }
            }

            @Override // com.appeaser.sublimepickerlibrary.datepicker.DayPickerPagerAdapter.DaySelectionEventListener
            public void onDateRangeSelectionStarted(SelectedDate selectedDate) {
                if (DayPickerView.this.mProxyDaySelectionEventListener != null) {
                    DayPickerView.this.mProxyDaySelectionEventListener.onDateRangeSelectionStarted(selectedDate);
                }
            }

            @Override // com.appeaser.sublimepickerlibrary.datepicker.DayPickerPagerAdapter.DaySelectionEventListener
            public void onDateRangeSelectionEnded(SelectedDate selectedDate) {
                if (DayPickerView.this.mProxyDaySelectionEventListener != null) {
                    DayPickerView.this.mProxyDaySelectionEventListener.onDateRangeSelectionEnded(selectedDate);
                }
            }

            @Override // com.appeaser.sublimepickerlibrary.datepicker.DayPickerPagerAdapter.DaySelectionEventListener
            public void onDateRangeSelectionUpdated(SelectedDate selectedDate) {
                if (DayPickerView.this.mProxyDaySelectionEventListener != null) {
                    DayPickerView.this.mProxyDaySelectionEventListener.onDateRangeSelectionUpdated(selectedDate);
                }
            }
        });
    }

    public void setCanPickRange(boolean z) {
        this.mViewPager.setCanPickRange(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateButtonVisibility(int i) {
        boolean z = i > 0;
        boolean z2 = i < this.mAdapter.getCount() - 1;
        this.mPrevButton.setVisibility(z ? 0 : 4);
        this.mNextButton.setVisibility(z2 ? 0 : 4);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        DayPickerViewPager dayPickerViewPager = this.mViewPager;
        measureChild(dayPickerViewPager, i, i2);
        setMeasuredDimension(dayPickerViewPager.getMeasuredWidthAndState(), dayPickerViewPager.getMeasuredHeightAndState());
        int measuredWidth = dayPickerViewPager.getMeasuredWidth();
        int measuredHeight = dayPickerViewPager.getMeasuredHeight();
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredWidth, Integer.MIN_VALUE);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(measuredHeight, Integer.MIN_VALUE);
        this.mPrevButton.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mNextButton.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        requestLayout();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        ImageButton imageButton;
        ImageButton imageButton2;
        if (SUtils.isLayoutRtlCompat(this)) {
            imageButton = this.mNextButton;
            imageButton2 = this.mPrevButton;
        } else {
            imageButton = this.mPrevButton;
            imageButton2 = this.mNextButton;
        }
        int i5 = i3 - i;
        this.mViewPager.layout(0, 0, i5, i4 - i2);
        SimpleMonthView simpleMonthView = (SimpleMonthView) this.mViewPager.getChildAt(0).findViewById(com.appeaser.sublimepickerlibrary.R.id.month_view);
        int monthHeight = simpleMonthView.getMonthHeight();
        int cellWidth = simpleMonthView.getCellWidth();
        int measuredWidth = imageButton.getMeasuredWidth();
        int measuredHeight = imageButton.getMeasuredHeight();
        int paddingTop = simpleMonthView.getPaddingTop() + ((monthHeight - measuredHeight) / 2);
        int paddingLeft = simpleMonthView.getPaddingLeft() + ((cellWidth - measuredWidth) / 2);
        imageButton.layout(paddingLeft, paddingTop, measuredWidth + paddingLeft, measuredHeight + paddingTop);
        int measuredWidth2 = imageButton2.getMeasuredWidth();
        int measuredHeight2 = imageButton2.getMeasuredHeight();
        int paddingTop2 = simpleMonthView.getPaddingTop() + ((monthHeight - measuredHeight2) / 2);
        int paddingRight = (i5 - simpleMonthView.getPaddingRight()) - ((cellWidth - measuredWidth2) / 2);
        imageButton2.layout(paddingRight - measuredWidth2, paddingTop2, paddingRight, measuredHeight2 + paddingTop2);
    }

    public void setDayOfWeekTextAppearance(int i) {
        this.mAdapter.setDayOfWeekTextAppearance(i);
    }

    public int getDayOfWeekTextAppearance() {
        return this.mAdapter.getDayOfWeekTextAppearance();
    }

    public void setDayTextAppearance(int i) {
        this.mAdapter.setDayTextAppearance(i);
    }

    public int getDayTextAppearance() {
        return this.mAdapter.getDayTextAppearance();
    }

    public void setDate(SelectedDate selectedDate) {
        setDate(selectedDate, false);
    }

    public void setDate(SelectedDate selectedDate, boolean z) {
        setDate(selectedDate, z, true, true);
    }

    public void setDate(SelectedDate selectedDate, boolean z, boolean z2) {
        setDate(selectedDate, z, true, z2);
    }

    private void setDate(SelectedDate selectedDate, boolean z, boolean z2, boolean z3) {
        long timeInMillis;
        if (z2) {
            this.mSelectedDay = selectedDate;
        }
        SelectedDate selectedDate2 = this.mSelectedDay;
        if (selectedDate2 == null) {
            timeInMillis = Calendar.getInstance().getTimeInMillis();
        } else {
            timeInMillis = selectedDate2.getStartDate().getTimeInMillis();
        }
        int positionFromDay = getPositionFromDay(timeInMillis);
        if (z3 && positionFromDay != this.mViewPager.getCurrentItem()) {
            this.mViewPager.setCurrentItem(positionFromDay, z);
        }
        this.mAdapter.setSelectedDay(new SelectedDate(this.mSelectedDay));
    }

    public SelectedDate getDate() {
        return this.mSelectedDay;
    }

    public void setFirstDayOfWeek(int i) {
        this.mAdapter.setFirstDayOfWeek(i);
    }

    public int getFirstDayOfWeek() {
        return this.mAdapter.getFirstDayOfWeek();
    }

    public void setMinDate(long j) {
        this.mMinDate.setTimeInMillis(j);
        onRangeChanged();
    }

    public long getMinDate() {
        return this.mMinDate.getTimeInMillis();
    }

    public void setMaxDate(long j) {
        this.mMaxDate.setTimeInMillis(j);
        onRangeChanged();
    }

    public long getMaxDate() {
        return this.mMaxDate.getTimeInMillis();
    }

    private void onRangeChanged() {
        this.mAdapter.setRange(this.mMinDate, this.mMaxDate);
        setDate(this.mSelectedDay, false, false, true);
        updateButtonVisibility(this.mViewPager.getCurrentItem());
    }

    public void setProxyDaySelectionEventListener(ProxyDaySelectionEventListener proxyDaySelectionEventListener) {
        this.mProxyDaySelectionEventListener = proxyDaySelectionEventListener;
    }

    private int getDiffMonths(Calendar calendar, Calendar calendar2) {
        return (calendar2.get(2) - calendar.get(2)) + ((calendar2.get(1) - calendar.get(1)) * 12);
    }

    private int getPositionFromDay(long j) {
        return SUtils.constrain(getDiffMonths(this.mMinDate, getTempCalendarForTime(j)), 0, getDiffMonths(this.mMinDate, this.mMaxDate));
    }

    private Calendar getTempCalendarForTime(long j) {
        if (this.mTempCalendar == null) {
            this.mTempCalendar = Calendar.getInstance();
        }
        this.mTempCalendar.setTimeInMillis(j);
        return this.mTempCalendar;
    }

    public int getMostVisiblePosition() {
        return this.mViewPager.getCurrentItem();
    }

    public void setPosition(int i) {
        this.mViewPager.setCurrentItem(i, false);
    }
}
