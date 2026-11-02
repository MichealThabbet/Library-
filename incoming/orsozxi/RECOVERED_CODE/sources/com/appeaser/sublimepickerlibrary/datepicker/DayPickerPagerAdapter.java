package com.appeaser.sublimepickerlibrary.datepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.PagerAdapter;
import com.appeaser.sublimepickerlibrary.R;
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.datepicker.SimpleMonthView;
import com.appeaser.sublimepickerlibrary.utilities.SUtils;
import java.util.Calendar;

/* loaded from: classes.dex */
class DayPickerPagerAdapter extends PagerAdapter {
    private static final int MONTHS_IN_YEAR = 12;
    private static final String TAG = DayPickerPagerAdapter.class.getSimpleName();
    private ColorStateList mCalendarTextColor;
    private final int mCalendarViewId;
    private int mCount;
    private final ColorStateList mDayHighlightColor;
    private int mDayOfWeekTextAppearance;
    private DaySelectionEventListener mDaySelectionEventListener;
    private ColorStateList mDaySelectorColor;
    private int mDayTextAppearance;
    private int mFirstDayOfWeek;
    private final LayoutInflater mInflater;
    private final int mLayoutResId;
    private int mMonthTextAppearance;
    private final Calendar mMinDate = Calendar.getInstance();
    private final Calendar mMaxDate = Calendar.getInstance();
    private final SparseArray<ViewHolder> mItems = new SparseArray<>();
    private SelectedDate mSelectedDay = null;
    private final SelectedDate mTempSelectedDay = new SelectedDate(Calendar.getInstance());
    private final SimpleMonthView.OnDayClickListener mOnDayClickListener = new SimpleMonthView.OnDayClickListener() { // from class: com.appeaser.sublimepickerlibrary.datepicker.DayPickerPagerAdapter.1
        @Override // com.appeaser.sublimepickerlibrary.datepicker.SimpleMonthView.OnDayClickListener
        public void onDayClick(SimpleMonthView simpleMonthView, Calendar calendar) {
            if (calendar == null || DayPickerPagerAdapter.this.mDaySelectionEventListener == null) {
                return;
            }
            DayPickerPagerAdapter.this.mDaySelectionEventListener.onDaySelected(DayPickerPagerAdapter.this, calendar);
        }
    };

    public interface DaySelectionEventListener {
        void onDateRangeSelectionEnded(SelectedDate selectedDate);

        void onDateRangeSelectionStarted(SelectedDate selectedDate);

        void onDateRangeSelectionUpdated(SelectedDate selectedDate);

        void onDaySelected(DayPickerPagerAdapter dayPickerPagerAdapter, Calendar calendar);
    }

    public DayPickerPagerAdapter(Context context, int i, int i2) {
        this.mInflater = LayoutInflater.from(context);
        this.mLayoutResId = i;
        this.mCalendarViewId = i2;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{R.attr.colorControlHighlight});
        this.mDayHighlightColor = typedArrayObtainStyledAttributes.getColorStateList(0);
        typedArrayObtainStyledAttributes.recycle();
    }

    public void setRange(Calendar calendar, Calendar calendar2) {
        this.mMinDate.setTimeInMillis(calendar.getTimeInMillis());
        this.mMaxDate.setTimeInMillis(calendar2.getTimeInMillis());
        this.mCount = (this.mMaxDate.get(2) - this.mMinDate.get(2)) + ((this.mMaxDate.get(1) - this.mMinDate.get(1)) * 12) + 1;
        notifyDataSetChanged();
    }

    public void setFirstDayOfWeek(int i) {
        this.mFirstDayOfWeek = i;
        int size = this.mItems.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mItems.valueAt(i2).calendar.setFirstDayOfWeek(i);
        }
    }

    public int getFirstDayOfWeek() {
        return this.mFirstDayOfWeek;
    }

    public void setSelectedDay(SelectedDate selectedDate) {
        int[] positionsForDay = getPositionsForDay(this.mSelectedDay);
        int[] positionsForDay2 = getPositionsForDay(selectedDate);
        if (positionsForDay != null) {
            for (int i = positionsForDay[0]; i <= positionsForDay[positionsForDay.length - 1]; i++) {
                ViewHolder viewHolder = this.mItems.get(i, null);
                if (viewHolder != null) {
                    viewHolder.calendar.setSelectedDays(-1, -1, SelectedDate.Type.SINGLE);
                }
            }
        }
        if (positionsForDay2 != null) {
            if (positionsForDay2.length == 1) {
                ViewHolder viewHolder2 = this.mItems.get(positionsForDay2[0], null);
                if (viewHolder2 != null) {
                    int i2 = selectedDate.getFirstDate().get(5);
                    viewHolder2.calendar.setSelectedDays(i2, i2, SelectedDate.Type.SINGLE);
                }
            } else if (positionsForDay2.length == 2) {
                if (positionsForDay2[0] == positionsForDay2[1]) {
                    ViewHolder viewHolder3 = this.mItems.get(positionsForDay2[0], null);
                    if (viewHolder3 != null) {
                        viewHolder3.calendar.setSelectedDays(selectedDate.getFirstDate().get(5), selectedDate.getSecondDate().get(5), SelectedDate.Type.RANGE);
                    }
                } else {
                    ViewHolder viewHolder4 = this.mItems.get(positionsForDay2[0], null);
                    if (viewHolder4 != null) {
                        viewHolder4.calendar.setSelectedDays(selectedDate.getFirstDate().get(5), selectedDate.getFirstDate().getActualMaximum(5), SelectedDate.Type.RANGE);
                    }
                    for (int i3 = positionsForDay2[0] + 1; i3 < positionsForDay2[1]; i3++) {
                        ViewHolder viewHolder5 = this.mItems.get(i3, null);
                        if (viewHolder5 != null) {
                            viewHolder5.calendar.selectAllDays();
                        }
                    }
                    ViewHolder viewHolder6 = this.mItems.get(positionsForDay2[1], null);
                    if (viewHolder6 != null) {
                        viewHolder6.calendar.setSelectedDays(selectedDate.getSecondDate().getMinimum(5), selectedDate.getSecondDate().get(5), SelectedDate.Type.RANGE);
                    }
                }
            }
        }
        this.mSelectedDay = selectedDate;
    }

    public void setDaySelectionEventListener(DaySelectionEventListener daySelectionEventListener) {
        this.mDaySelectionEventListener = daySelectionEventListener;
    }

    void setCalendarTextColor(ColorStateList colorStateList) {
        this.mCalendarTextColor = colorStateList;
    }

    void setDaySelectorColor(ColorStateList colorStateList) {
        this.mDaySelectorColor = colorStateList;
    }

    void setMonthTextAppearance(int i) {
        this.mMonthTextAppearance = i;
    }

    void setDayOfWeekTextAppearance(int i) {
        this.mDayOfWeekTextAppearance = i;
    }

    int getDayOfWeekTextAppearance() {
        return this.mDayOfWeekTextAppearance;
    }

    void setDayTextAppearance(int i) {
        this.mDayTextAppearance = i;
    }

    int getDayTextAppearance() {
        return this.mDayTextAppearance;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.mCount;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return view == ((ViewHolder) obj).container;
    }

    private int getMonthForPosition(int i) {
        return (i + this.mMinDate.get(2)) % 12;
    }

    private int getYearForPosition(int i) {
        return ((i + this.mMinDate.get(2)) / 12) + this.mMinDate.get(1);
    }

    private int getPositionForDay(Calendar calendar) {
        if (calendar == null) {
            return -1;
        }
        return ((calendar.get(1) - this.mMinDate.get(1)) * 12) + (calendar.get(2) - this.mMinDate.get(2));
    }

    private int[] getPositionsForDay(SelectedDate selectedDate) {
        if (selectedDate == null) {
            return null;
        }
        SelectedDate.Type type = selectedDate.getType();
        if (type == SelectedDate.Type.SINGLE) {
            return new int[]{((selectedDate.getFirstDate().get(1) - this.mMinDate.get(1)) * 12) + (selectedDate.getFirstDate().get(2) - this.mMinDate.get(2))};
        }
        if (type == SelectedDate.Type.RANGE) {
            return new int[]{((selectedDate.getFirstDate().get(1) - this.mMinDate.get(1)) * 12) + (selectedDate.getFirstDate().get(2) - this.mMinDate.get(2)), ((selectedDate.getSecondDate().get(1) - this.mMinDate.get(1)) * 12) + (selectedDate.getSecondDate().get(2) - this.mMinDate.get(2))};
        }
        return null;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View viewInflate = this.mInflater.inflate(this.mLayoutResId, viewGroup, false);
        SimpleMonthView simpleMonthView = (SimpleMonthView) viewInflate.findViewById(this.mCalendarViewId);
        simpleMonthView.setOnDayClickListener(this.mOnDayClickListener);
        simpleMonthView.setMonthTextAppearance(this.mMonthTextAppearance);
        simpleMonthView.setDayOfWeekTextAppearance(this.mDayOfWeekTextAppearance);
        simpleMonthView.setDayTextAppearance(this.mDayTextAppearance);
        ColorStateList colorStateList = this.mDaySelectorColor;
        if (colorStateList != null) {
            simpleMonthView.setDaySelectorColor(colorStateList);
        }
        ColorStateList colorStateList2 = this.mDayHighlightColor;
        if (colorStateList2 != null) {
            simpleMonthView.setDayHighlightColor(colorStateList2);
        }
        ColorStateList colorStateList3 = this.mCalendarTextColor;
        if (colorStateList3 != null) {
            simpleMonthView.setMonthTextColor(colorStateList3);
            simpleMonthView.setDayOfWeekTextColor(this.mCalendarTextColor);
            simpleMonthView.setDayTextColor(this.mCalendarTextColor);
        }
        int monthForPosition = getMonthForPosition(i);
        int yearForPosition = getYearForPosition(i);
        int[] iArrResolveSelectedDayBasedOnType = resolveSelectedDayBasedOnType(monthForPosition, yearForPosition);
        int i2 = (this.mMinDate.get(2) == monthForPosition && this.mMinDate.get(1) == yearForPosition) ? this.mMinDate.get(5) : 1;
        int i3 = (this.mMaxDate.get(2) == monthForPosition && this.mMaxDate.get(1) == yearForPosition) ? this.mMaxDate.get(5) : 31;
        int i4 = this.mFirstDayOfWeek;
        int i5 = iArrResolveSelectedDayBasedOnType[0];
        int i6 = iArrResolveSelectedDayBasedOnType[1];
        SelectedDate selectedDate = this.mSelectedDay;
        simpleMonthView.setMonthParams(monthForPosition, yearForPosition, i4, i2, i3, i5, i6, selectedDate != null ? selectedDate.getType() : null);
        ViewHolder viewHolder = new ViewHolder(i, viewInflate, simpleMonthView);
        this.mItems.put(i, viewHolder);
        viewGroup.addView(viewInflate);
        return viewHolder;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView(((ViewHolder) obj).container);
        this.mItems.remove(i);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getItemPosition(Object obj) {
        return ((ViewHolder) obj).position;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public CharSequence getPageTitle(int i) {
        SimpleMonthView simpleMonthView = this.mItems.get(i).calendar;
        if (simpleMonthView != null) {
            return simpleMonthView.getTitle();
        }
        return null;
    }

    private static class ViewHolder {
        public final SimpleMonthView calendar;
        public final View container;
        public final int position;

        public ViewHolder(int i, View view, SimpleMonthView simpleMonthView) {
            this.position = i;
            this.container = view;
            this.calendar = simpleMonthView;
        }
    }

    public SelectedDate resolveStartDateForRange(int i, int i2, int i3) {
        ViewHolder viewHolder;
        if (i3 >= 0 && (viewHolder = this.mItems.get(i3, null)) != null) {
            Calendar calendarComposeDate = viewHolder.calendar.composeDate(viewHolder.calendar.getDayAtLocation(i, i2));
            if (calendarComposeDate != null) {
                this.mTempSelectedDay.setDate(calendarComposeDate);
                return this.mTempSelectedDay;
            }
        }
        return null;
    }

    public SelectedDate resolveEndDateForRange(int i, int i2, int i3, boolean z) {
        ViewHolder viewHolder;
        if (i3 >= 0 && (viewHolder = this.mItems.get(i3, null)) != null) {
            Calendar calendarComposeDate = viewHolder.calendar.composeDate(viewHolder.calendar.getDayAtLocation(i, i2));
            if (calendarComposeDate != null && (!z || this.mSelectedDay.getSecondDate().getTimeInMillis() != calendarComposeDate.getTimeInMillis())) {
                this.mTempSelectedDay.setSecondDate(calendarComposeDate);
                return this.mTempSelectedDay;
            }
        }
        return null;
    }

    private int[] resolveSelectedDayBasedOnType(int i, int i2) {
        SelectedDate selectedDate = this.mSelectedDay;
        if (selectedDate == null) {
            return new int[]{-1, -1};
        }
        if (selectedDate.getType() == SelectedDate.Type.SINGLE) {
            return resolveSelectedDayForTypeSingle(i, i2);
        }
        return this.mSelectedDay.getType() == SelectedDate.Type.RANGE ? resolveSelectedDayForTypeRange(i, i2) : new int[]{-1, -1};
    }

    private int[] resolveSelectedDayForTypeSingle(int i, int i2) {
        if (this.mSelectedDay.getFirstDate().get(2) != i || this.mSelectedDay.getFirstDate().get(1) != i2) {
            return new int[]{-1, -1};
        }
        int i3 = this.mSelectedDay.getFirstDate().get(5);
        return new int[]{i3, i3};
    }

    private int[] resolveSelectedDayForTypeRange(int i, int i2) {
        int daysInMonth;
        float f = this.mSelectedDay.getStartDate().get(1) + ((this.mSelectedDay.getStartDate().get(2) + 1) / 100.0f);
        float f2 = this.mSelectedDay.getEndDate().get(1) + ((this.mSelectedDay.getEndDate().get(2) + 1) / 100.0f);
        float f3 = i2 + ((i + 1) / 100.0f);
        if (f3 < f || f3 > f2) {
            return new int[]{-1, -1};
        }
        int i3 = f3 == f ? this.mSelectedDay.getStartDate().get(5) : 1;
        if (f3 == f2) {
            daysInMonth = this.mSelectedDay.getEndDate().get(5);
        } else {
            daysInMonth = SUtils.getDaysInMonth(i, i2);
        }
        return new int[]{i3, daysInMonth};
    }

    public void onDateRangeSelectionStarted(SelectedDate selectedDate) {
        DaySelectionEventListener daySelectionEventListener = this.mDaySelectionEventListener;
        if (daySelectionEventListener != null) {
            daySelectionEventListener.onDateRangeSelectionStarted(selectedDate);
        }
    }

    public void onDateRangeSelectionEnded(SelectedDate selectedDate) {
        DaySelectionEventListener daySelectionEventListener = this.mDaySelectionEventListener;
        if (daySelectionEventListener != null) {
            daySelectionEventListener.onDateRangeSelectionEnded(selectedDate);
        }
    }

    public void onDateRangeSelectionUpdated(SelectedDate selectedDate) {
        DaySelectionEventListener daySelectionEventListener = this.mDaySelectionEventListener;
        if (daySelectionEventListener != null) {
            daySelectionEventListener.onDateRangeSelectionUpdated(selectedDate);
        }
    }
}
