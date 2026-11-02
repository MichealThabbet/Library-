package com.appeaser.sublimepickerlibrary.datepicker;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import com.appeaser.sublimepickerlibrary.R;
import com.appeaser.sublimepickerlibrary.common.DecisionButtonLayout;
import com.appeaser.sublimepickerlibrary.datepicker.DayPickerView;
import com.appeaser.sublimepickerlibrary.utilities.AccessibilityUtils;
import com.appeaser.sublimepickerlibrary.utilities.SUtils;
import java.util.Calendar;
import java.util.Locale;

/* loaded from: classes.dex */
public class RecurrenceEndDatePicker extends FrameLayout {
    private static final int DEFAULT_END_YEAR = 2100;
    private static final int DEFAULT_START_YEAR = 1900;
    private static final String TAG = RecurrenceEndDatePicker.class.getSimpleName();
    private ViewGroup mContainer;
    private Context mContext;
    private SelectedDate mCurrentDate;
    private Locale mCurrentLocale;
    private DayPickerView mDayPickerView;
    private DecisionButtonLayout mDecisionButtonLayout;
    private DecisionButtonLayout.Callback mDecisionButtonLayoutCallback;
    private int mFirstDayOfWeek;
    private Calendar mMaxDate;
    private Calendar mMinDate;
    private OnDateSetListener mOnDateSetListener;
    private final DayPickerView.ProxyDaySelectionEventListener mProxyDaySelectionEventListener;
    private Calendar mTempDate;
    private DatePickerValidationCallback mValidationCallback;

    public interface DatePickerValidationCallback {
        void onDatePickerValidationChanged(boolean z);
    }

    public interface OnDateChangedListener {
        void onDateChanged(RecurrenceEndDatePicker recurrenceEndDatePicker, SelectedDate selectedDate);
    }

    public interface OnDateSetListener {
        void onDateOnlyPickerCancelled(RecurrenceEndDatePicker recurrenceEndDatePicker);

        void onDateSet(RecurrenceEndDatePicker recurrenceEndDatePicker, int i, int i2, int i3);
    }

    public RecurrenceEndDatePicker(Context context) {
        this(context, null);
    }

    public RecurrenceEndDatePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.spDatePickerStyle);
    }

    public RecurrenceEndDatePicker(Context context, AttributeSet attributeSet, int i) throws Resources.NotFoundException {
        super(context, attributeSet, i);
        this.mDecisionButtonLayoutCallback = new DecisionButtonLayout.Callback() { // from class: com.appeaser.sublimepickerlibrary.datepicker.RecurrenceEndDatePicker.1
            @Override // com.appeaser.sublimepickerlibrary.common.DecisionButtonLayout.Callback
            public void onOkay() {
                if (RecurrenceEndDatePicker.this.mOnDateSetListener != null) {
                    OnDateSetListener onDateSetListener = RecurrenceEndDatePicker.this.mOnDateSetListener;
                    RecurrenceEndDatePicker recurrenceEndDatePicker = RecurrenceEndDatePicker.this;
                    onDateSetListener.onDateSet(recurrenceEndDatePicker, recurrenceEndDatePicker.mCurrentDate.getStartDate().get(1), RecurrenceEndDatePicker.this.mCurrentDate.getStartDate().get(2), RecurrenceEndDatePicker.this.mCurrentDate.getStartDate().get(5));
                }
            }

            @Override // com.appeaser.sublimepickerlibrary.common.DecisionButtonLayout.Callback
            public void onCancel() {
                if (RecurrenceEndDatePicker.this.mOnDateSetListener != null) {
                    RecurrenceEndDatePicker.this.mOnDateSetListener.onDateOnlyPickerCancelled(RecurrenceEndDatePicker.this);
                }
            }
        };
        this.mProxyDaySelectionEventListener = new DayPickerView.ProxyDaySelectionEventListener() { // from class: com.appeaser.sublimepickerlibrary.datepicker.RecurrenceEndDatePicker.2
            @Override // com.appeaser.sublimepickerlibrary.datepicker.DayPickerView.ProxyDaySelectionEventListener
            public void onDaySelected(DayPickerView dayPickerView, Calendar calendar) {
                RecurrenceEndDatePicker.this.mCurrentDate = new SelectedDate(calendar);
                RecurrenceEndDatePicker.this.onDateChanged(true, true);
            }

            @Override // com.appeaser.sublimepickerlibrary.datepicker.DayPickerView.ProxyDaySelectionEventListener
            public void onDateRangeSelectionStarted(SelectedDate selectedDate) {
                RecurrenceEndDatePicker.this.mCurrentDate = new SelectedDate(selectedDate);
                RecurrenceEndDatePicker.this.onDateChanged(false, false);
            }

            @Override // com.appeaser.sublimepickerlibrary.datepicker.DayPickerView.ProxyDaySelectionEventListener
            public void onDateRangeSelectionEnded(SelectedDate selectedDate) {
                if (selectedDate != null) {
                    RecurrenceEndDatePicker.this.mCurrentDate = new SelectedDate(selectedDate);
                    RecurrenceEndDatePicker.this.onDateChanged(false, false);
                }
            }

            @Override // com.appeaser.sublimepickerlibrary.datepicker.DayPickerView.ProxyDaySelectionEventListener
            public void onDateRangeSelectionUpdated(SelectedDate selectedDate) {
                RecurrenceEndDatePicker.this.mCurrentDate = new SelectedDate(selectedDate);
                RecurrenceEndDatePicker.this.onDateChanged(false, false);
            }
        };
        initializeLayout(attributeSet, i, R.style.SublimeDatePickerStyle);
    }

    public RecurrenceEndDatePicker(Context context, AttributeSet attributeSet, int i, int i2) throws Resources.NotFoundException {
        super(context, attributeSet, i, i2);
        this.mDecisionButtonLayoutCallback = new DecisionButtonLayout.Callback() { // from class: com.appeaser.sublimepickerlibrary.datepicker.RecurrenceEndDatePicker.1
            @Override // com.appeaser.sublimepickerlibrary.common.DecisionButtonLayout.Callback
            public void onOkay() {
                if (RecurrenceEndDatePicker.this.mOnDateSetListener != null) {
                    OnDateSetListener onDateSetListener = RecurrenceEndDatePicker.this.mOnDateSetListener;
                    RecurrenceEndDatePicker recurrenceEndDatePicker = RecurrenceEndDatePicker.this;
                    onDateSetListener.onDateSet(recurrenceEndDatePicker, recurrenceEndDatePicker.mCurrentDate.getStartDate().get(1), RecurrenceEndDatePicker.this.mCurrentDate.getStartDate().get(2), RecurrenceEndDatePicker.this.mCurrentDate.getStartDate().get(5));
                }
            }

            @Override // com.appeaser.sublimepickerlibrary.common.DecisionButtonLayout.Callback
            public void onCancel() {
                if (RecurrenceEndDatePicker.this.mOnDateSetListener != null) {
                    RecurrenceEndDatePicker.this.mOnDateSetListener.onDateOnlyPickerCancelled(RecurrenceEndDatePicker.this);
                }
            }
        };
        this.mProxyDaySelectionEventListener = new DayPickerView.ProxyDaySelectionEventListener() { // from class: com.appeaser.sublimepickerlibrary.datepicker.RecurrenceEndDatePicker.2
            @Override // com.appeaser.sublimepickerlibrary.datepicker.DayPickerView.ProxyDaySelectionEventListener
            public void onDaySelected(DayPickerView dayPickerView, Calendar calendar) {
                RecurrenceEndDatePicker.this.mCurrentDate = new SelectedDate(calendar);
                RecurrenceEndDatePicker.this.onDateChanged(true, true);
            }

            @Override // com.appeaser.sublimepickerlibrary.datepicker.DayPickerView.ProxyDaySelectionEventListener
            public void onDateRangeSelectionStarted(SelectedDate selectedDate) {
                RecurrenceEndDatePicker.this.mCurrentDate = new SelectedDate(selectedDate);
                RecurrenceEndDatePicker.this.onDateChanged(false, false);
            }

            @Override // com.appeaser.sublimepickerlibrary.datepicker.DayPickerView.ProxyDaySelectionEventListener
            public void onDateRangeSelectionEnded(SelectedDate selectedDate) {
                if (selectedDate != null) {
                    RecurrenceEndDatePicker.this.mCurrentDate = new SelectedDate(selectedDate);
                    RecurrenceEndDatePicker.this.onDateChanged(false, false);
                }
            }

            @Override // com.appeaser.sublimepickerlibrary.datepicker.DayPickerView.ProxyDaySelectionEventListener
            public void onDateRangeSelectionUpdated(SelectedDate selectedDate) {
                RecurrenceEndDatePicker.this.mCurrentDate = new SelectedDate(selectedDate);
                RecurrenceEndDatePicker.this.onDateChanged(false, false);
            }
        };
        initializeLayout(attributeSet, i, i2);
    }

    private void initializeLayout(AttributeSet attributeSet, int i, int i2) throws Resources.NotFoundException {
        this.mContext = getContext();
        setCurrentLocale(Locale.getDefault());
        this.mCurrentDate = new SelectedDate(Calendar.getInstance(this.mCurrentLocale));
        this.mTempDate = Calendar.getInstance(this.mCurrentLocale);
        this.mMinDate = Calendar.getInstance(this.mCurrentLocale);
        this.mMaxDate = Calendar.getInstance(this.mCurrentLocale);
        this.mMinDate.set(1900, 0, 1);
        this.mMaxDate.set(2100, 11, 31);
        Resources resources = getResources();
        TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(attributeSet, R.styleable.SublimeDatePicker, i, i2);
        try {
            this.mContainer = (ViewGroup) ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.recurrence_end_date_picker, (ViewGroup) this, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addView(this.mContainer);
        int i3 = typedArrayObtainStyledAttributes.getInt(R.styleable.SublimeDatePicker_spFirstDayOfWeek, this.mCurrentDate.getFirstDate().getFirstDayOfWeek());
        String string = typedArrayObtainStyledAttributes.getString(R.styleable.SublimeDatePicker_spMinDate);
        String string2 = typedArrayObtainStyledAttributes.getString(R.styleable.SublimeDatePicker_spMaxDate);
        Calendar calendar = Calendar.getInstance();
        if (!SUtils.parseDate(string, calendar)) {
            calendar.set(1900, 0, 1);
        }
        long timeInMillis = calendar.getTimeInMillis();
        if (!SUtils.parseDate(string2, calendar)) {
            calendar.set(2100, 11, 31);
        }
        long timeInMillis2 = calendar.getTimeInMillis();
        if (timeInMillis2 < timeInMillis) {
            throw new IllegalArgumentException("maxDate must be >= minDate");
        }
        long jConstrain = SUtils.constrain(System.currentTimeMillis(), timeInMillis, timeInMillis2);
        this.mMinDate.setTimeInMillis(timeInMillis);
        this.mMaxDate.setTimeInMillis(timeInMillis2);
        this.mCurrentDate.setTimeInMillis(jConstrain);
        typedArrayObtainStyledAttributes.recycle();
        DecisionButtonLayout decisionButtonLayout = (DecisionButtonLayout) this.mContainer.findViewById(R.id.redp_decision_button_layout);
        this.mDecisionButtonLayout = decisionButtonLayout;
        decisionButtonLayout.applyOptions(this.mDecisionButtonLayoutCallback);
        this.mDayPickerView = (DayPickerView) this.mContainer.findViewById(R.id.redp_day_picker);
        setFirstDayOfWeek(i3);
        this.mDayPickerView.setMinDate(this.mMinDate.getTimeInMillis());
        this.mDayPickerView.setMaxDate(this.mMaxDate.getTimeInMillis());
        this.mDayPickerView.setDate(this.mCurrentDate);
        this.mDayPickerView.setProxyDaySelectionEventListener(this.mProxyDaySelectionEventListener);
        this.mDayPickerView.setCanPickRange(false);
        String string3 = resources.getString(R.string.select_day);
        onLocaleChanged(this.mCurrentLocale);
        AccessibilityUtils.makeAnnouncement(this.mDayPickerView, string3);
    }

    private void onLocaleChanged(Locale locale) {
        if (this.mDayPickerView == null) {
            return;
        }
        onCurrentDateChanged(false);
    }

    private void onCurrentDateChanged(boolean z) {
        if (this.mDayPickerView != null && z) {
            AccessibilityUtils.makeAnnouncement(this.mDayPickerView, DateUtils.formatDateTime(this.mContext, this.mCurrentDate.getStartDate().getTimeInMillis(), 20));
        }
    }

    public void init(int i, int i2, int i3, OnDateSetListener onDateSetListener) {
        this.mCurrentDate.set(1, i);
        this.mCurrentDate.set(2, i2);
        this.mCurrentDate.set(5, i3);
        this.mOnDateSetListener = onDateSetListener;
        onDateChanged(false, true);
    }

    public void updateDate(int i, int i2, int i3) {
        this.mCurrentDate.set(1, i);
        this.mCurrentDate.set(2, i2);
        this.mCurrentDate.set(5, i3);
        onDateChanged(false, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDateChanged(boolean z, boolean z2) {
        this.mDayPickerView.setDate(new SelectedDate(this.mCurrentDate), false, z2);
        onCurrentDateChanged(z);
        if (z) {
            SUtils.vibrateForDatePicker(this);
        }
    }

    public SelectedDate getSelectedDate() {
        return new SelectedDate(this.mCurrentDate);
    }

    public long getSelectedDateInMillis() {
        return this.mCurrentDate.getStartDate().getTimeInMillis();
    }

    public void setMinDate(long j) {
        this.mTempDate.setTimeInMillis(j);
        if (this.mTempDate.get(1) != this.mMinDate.get(1) || this.mTempDate.get(6) == this.mMinDate.get(6)) {
            if (this.mCurrentDate.getStartDate().before(this.mTempDate)) {
                this.mCurrentDate.getStartDate().setTimeInMillis(j);
                onDateChanged(false, true);
            }
            this.mMinDate.setTimeInMillis(j);
            this.mDayPickerView.setMinDate(j);
        }
    }

    public Calendar getMinDate() {
        return this.mMinDate;
    }

    public void setMaxDate(long j) {
        this.mTempDate.setTimeInMillis(j);
        if (this.mTempDate.get(1) != this.mMaxDate.get(1) || this.mTempDate.get(6) == this.mMaxDate.get(6)) {
            if (this.mCurrentDate.getEndDate().after(this.mTempDate)) {
                this.mCurrentDate.getEndDate().setTimeInMillis(j);
                onDateChanged(false, true);
            }
            this.mMaxDate.setTimeInMillis(j);
            this.mDayPickerView.setMaxDate(j);
        }
    }

    public Calendar getMaxDate() {
        return this.mMaxDate;
    }

    public void setFirstDayOfWeek(int i) {
        if (i < 1 || i > 7) {
            i = this.mCurrentDate.getFirstDate().getFirstDayOfWeek();
        }
        this.mFirstDayOfWeek = i;
        this.mDayPickerView.setFirstDayOfWeek(i);
    }

    public int getFirstDayOfWeek() {
        return this.mFirstDayOfWeek;
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        if (isEnabled() == z) {
            return;
        }
        this.mContainer.setEnabled(z);
        this.mDayPickerView.setEnabled(z);
    }

    @Override // android.view.View
    public boolean isEnabled() {
        return this.mContainer.isEnabled();
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        setCurrentLocale(configuration.locale);
    }

    private void setCurrentLocale(Locale locale) {
        if (locale.equals(this.mCurrentLocale)) {
            return;
        }
        this.mCurrentLocale = locale;
        onLocaleChanged(locale);
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), this.mCurrentDate, this.mMinDate.getTimeInMillis(), this.mMaxDate.getTimeInMillis(), this.mDayPickerView.getMostVisiblePosition());
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        View.BaseSavedState baseSavedState = (View.BaseSavedState) parcelable;
        super.onRestoreInstanceState(baseSavedState.getSuperState());
        SavedState savedState = (SavedState) baseSavedState;
        Calendar calendar = Calendar.getInstance(this.mCurrentLocale);
        calendar.set(savedState.getSelectedYear(), savedState.getSelectedMonth(), savedState.getSelectedDay());
        this.mCurrentDate.setDate(calendar);
        this.mMinDate.setTimeInMillis(savedState.getMinDate());
        this.mMaxDate.setTimeInMillis(savedState.getMaxDate());
        onCurrentDateChanged(false);
        int listPosition = savedState.getListPosition();
        if (listPosition != -1) {
            this.mDayPickerView.setPosition(listPosition);
        }
    }

    @Override // android.view.View
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    @Override // android.view.View
    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.getText().add(this.mCurrentDate.getStartDate().getTime().toString());
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return SublimeDatePicker.class.getName();
    }

    public void setValidationCallback(DatePickerValidationCallback datePickerValidationCallback) {
        this.mValidationCallback = datePickerValidationCallback;
    }

    protected void onValidationChanged(boolean z) {
        DatePickerValidationCallback datePickerValidationCallback = this.mValidationCallback;
        if (datePickerValidationCallback != null) {
            datePickerValidationCallback.onDatePickerValidationChanged(z);
        }
        this.mDecisionButtonLayout.updateValidity(z);
    }

    private static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.appeaser.sublimepickerlibrary.datepicker.RecurrenceEndDatePicker.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        private final int mListPosition;
        private final long mMaxDate;
        private final long mMinDate;
        private final int mSelectedDay;
        private final int mSelectedMonth;
        private final int mSelectedYear;

        private SavedState(Parcelable parcelable, SelectedDate selectedDate, long j, long j2, int i) {
            super(parcelable);
            this.mSelectedYear = selectedDate.getStartDate().get(1);
            this.mSelectedMonth = selectedDate.getStartDate().get(2);
            this.mSelectedDay = selectedDate.getStartDate().get(5);
            this.mMinDate = j;
            this.mMaxDate = j2;
            this.mListPosition = i;
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.mSelectedYear = parcel.readInt();
            this.mSelectedMonth = parcel.readInt();
            this.mSelectedDay = parcel.readInt();
            this.mMinDate = parcel.readLong();
            this.mMaxDate = parcel.readLong();
            this.mListPosition = parcel.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mSelectedYear);
            parcel.writeInt(this.mSelectedMonth);
            parcel.writeInt(this.mSelectedDay);
            parcel.writeLong(this.mMinDate);
            parcel.writeLong(this.mMaxDate);
            parcel.writeInt(this.mListPosition);
        }

        public int getSelectedDay() {
            return this.mSelectedDay;
        }

        public int getSelectedMonth() {
            return this.mSelectedMonth;
        }

        public int getSelectedYear() {
            return this.mSelectedYear;
        }

        public long getMinDate() {
            return this.mMinDate;
        }

        public long getMaxDate() {
            return this.mMaxDate;
        }

        public int getListPosition() {
            return this.mListPosition;
        }
    }
}
