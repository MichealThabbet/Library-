package com.appeaser.sublimepickerlibrary;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.appeaser.sublimepickerlibrary.common.ButtonHandler;
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.datepicker.SublimeDatePicker;
import com.appeaser.sublimepickerlibrary.drawables.OverflowDrawable;
import com.appeaser.sublimepickerlibrary.helpers.SublimeListenerAdapter;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.appeaser.sublimepickerlibrary.timepicker.SublimeTimePicker;
import com.appeaser.sublimepickerlibrary.utilities.SUtils;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes.dex */
public class SublimePicker extends FrameLayout implements SublimeDatePicker.OnDateChangedListener, SublimeDatePicker.DatePickerValidationCallback, SublimeTimePicker.TimePickerValidationCallback {
    private static final long MONTH_IN_MILLIS = 2620800000L;
    private static final String TAG = SublimePicker.class.getSimpleName();
    private ImageView ivRecurrenceOptionsDP;
    private ImageView ivRecurrenceOptionsTP;
    private LinearLayout llMainContentHolder;
    private ButtonHandler mButtonLayout;
    private final ButtonHandler.Callback mButtonLayoutCallback;
    private SublimeOptions.Picker mCurrentPicker;
    private SublimeRecurrencePicker.RecurrenceOption mCurrentRecurrenceOption;
    private SublimeDatePicker mDatePicker;
    private boolean mDatePickerEnabled;
    private boolean mDatePickerSyncStateCalled;
    private boolean mDatePickerValid;
    private DateFormat mDefaultDateFormatter;
    private DateFormat mDefaultTimeFormatter;
    private SublimeOptions.Picker mHiddenPicker;
    private SublimeListenerAdapter mListener;
    private SublimeOptions mOptions;
    private boolean mRecurrencePickerEnabled;
    private String mRecurrenceRule;
    private final SublimeRecurrencePicker.OnRepeatOptionSetListener mRepeatOptionSetListener;
    private SublimeRecurrencePicker mSublimeRecurrencePicker;
    private SublimeTimePicker mTimePicker;
    private boolean mTimePickerEnabled;
    private boolean mTimePickerValid;

    public SublimePicker(Context context) {
        this(context, null);
    }

    public SublimePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.sublimePickerStyle);
    }

    public SublimePicker(Context context, AttributeSet attributeSet, int i) {
        super(createThemeWrapper(context), attributeSet, i);
        this.mCurrentRecurrenceOption = SublimeRecurrencePicker.RecurrenceOption.DOES_NOT_REPEAT;
        this.mDatePickerValid = true;
        this.mTimePickerValid = true;
        this.mRepeatOptionSetListener = new SublimeRecurrencePicker.OnRepeatOptionSetListener() { // from class: com.appeaser.sublimepickerlibrary.SublimePicker.1
            @Override // com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker.OnRepeatOptionSetListener
            public void onRepeatOptionSet(SublimeRecurrencePicker.RecurrenceOption recurrenceOption, String str) {
                SublimePicker.this.mCurrentRecurrenceOption = recurrenceOption;
                SublimePicker.this.mRecurrenceRule = str;
                onDone();
            }

            @Override // com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker.OnRepeatOptionSetListener
            public void onDone() {
                if (SublimePicker.this.mDatePickerEnabled || SublimePicker.this.mTimePickerEnabled) {
                    SublimePicker.this.updateCurrentPicker();
                    SublimePicker.this.updateDisplay();
                } else {
                    SublimePicker.this.mButtonLayoutCallback.onOkay();
                }
            }
        };
        this.mButtonLayoutCallback = new ButtonHandler.Callback() { // from class: com.appeaser.sublimepickerlibrary.SublimePicker.2
            @Override // com.appeaser.sublimepickerlibrary.common.ButtonHandler.Callback
            public void onOkay() {
                int currentHour;
                int currentMinute;
                String str = null;
                SelectedDate selectedDate = SublimePicker.this.mDatePickerEnabled ? SublimePicker.this.mDatePicker.getSelectedDate() : null;
                if (SublimePicker.this.mTimePickerEnabled) {
                    currentHour = SublimePicker.this.mTimePicker.getCurrentHour();
                    currentMinute = SublimePicker.this.mTimePicker.getCurrentMinute();
                } else {
                    currentHour = -1;
                    currentMinute = -1;
                }
                SublimeRecurrencePicker.RecurrenceOption recurrenceOption = SublimeRecurrencePicker.RecurrenceOption.DOES_NOT_REPEAT;
                if (SublimePicker.this.mRecurrencePickerEnabled && (recurrenceOption = SublimePicker.this.mCurrentRecurrenceOption) == SublimeRecurrencePicker.RecurrenceOption.CUSTOM) {
                    str = SublimePicker.this.mRecurrenceRule;
                }
                SublimePicker.this.mListener.onDateTimeRecurrenceSet(SublimePicker.this, selectedDate, currentHour, currentMinute, recurrenceOption, str);
            }

            @Override // com.appeaser.sublimepickerlibrary.common.ButtonHandler.Callback
            public void onCancel() {
                SublimePicker.this.mListener.onCancelled();
            }

            @Override // com.appeaser.sublimepickerlibrary.common.ButtonHandler.Callback
            public void onSwitch() {
                SublimePicker sublimePicker = SublimePicker.this;
                sublimePicker.mCurrentPicker = sublimePicker.mCurrentPicker == SublimeOptions.Picker.DATE_PICKER ? SublimeOptions.Picker.TIME_PICKER : SublimeOptions.Picker.DATE_PICKER;
                SublimePicker.this.updateDisplay();
            }
        };
        initializeLayout();
    }

    public SublimePicker(Context context, AttributeSet attributeSet, int i, int i2) {
        super(createThemeWrapper(context), attributeSet, i, i2);
        this.mCurrentRecurrenceOption = SublimeRecurrencePicker.RecurrenceOption.DOES_NOT_REPEAT;
        this.mDatePickerValid = true;
        this.mTimePickerValid = true;
        this.mRepeatOptionSetListener = new SublimeRecurrencePicker.OnRepeatOptionSetListener() { // from class: com.appeaser.sublimepickerlibrary.SublimePicker.1
            @Override // com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker.OnRepeatOptionSetListener
            public void onRepeatOptionSet(SublimeRecurrencePicker.RecurrenceOption recurrenceOption, String str) {
                SublimePicker.this.mCurrentRecurrenceOption = recurrenceOption;
                SublimePicker.this.mRecurrenceRule = str;
                onDone();
            }

            @Override // com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker.OnRepeatOptionSetListener
            public void onDone() {
                if (SublimePicker.this.mDatePickerEnabled || SublimePicker.this.mTimePickerEnabled) {
                    SublimePicker.this.updateCurrentPicker();
                    SublimePicker.this.updateDisplay();
                } else {
                    SublimePicker.this.mButtonLayoutCallback.onOkay();
                }
            }
        };
        this.mButtonLayoutCallback = new ButtonHandler.Callback() { // from class: com.appeaser.sublimepickerlibrary.SublimePicker.2
            @Override // com.appeaser.sublimepickerlibrary.common.ButtonHandler.Callback
            public void onOkay() {
                int currentHour;
                int currentMinute;
                String str = null;
                SelectedDate selectedDate = SublimePicker.this.mDatePickerEnabled ? SublimePicker.this.mDatePicker.getSelectedDate() : null;
                if (SublimePicker.this.mTimePickerEnabled) {
                    currentHour = SublimePicker.this.mTimePicker.getCurrentHour();
                    currentMinute = SublimePicker.this.mTimePicker.getCurrentMinute();
                } else {
                    currentHour = -1;
                    currentMinute = -1;
                }
                SublimeRecurrencePicker.RecurrenceOption recurrenceOption = SublimeRecurrencePicker.RecurrenceOption.DOES_NOT_REPEAT;
                if (SublimePicker.this.mRecurrencePickerEnabled && (recurrenceOption = SublimePicker.this.mCurrentRecurrenceOption) == SublimeRecurrencePicker.RecurrenceOption.CUSTOM) {
                    str = SublimePicker.this.mRecurrenceRule;
                }
                SublimePicker.this.mListener.onDateTimeRecurrenceSet(SublimePicker.this, selectedDate, currentHour, currentMinute, recurrenceOption, str);
            }

            @Override // com.appeaser.sublimepickerlibrary.common.ButtonHandler.Callback
            public void onCancel() {
                SublimePicker.this.mListener.onCancelled();
            }

            @Override // com.appeaser.sublimepickerlibrary.common.ButtonHandler.Callback
            public void onSwitch() {
                SublimePicker sublimePicker = SublimePicker.this;
                sublimePicker.mCurrentPicker = sublimePicker.mCurrentPicker == SublimeOptions.Picker.DATE_PICKER ? SublimeOptions.Picker.TIME_PICKER : SublimeOptions.Picker.DATE_PICKER;
                SublimePicker.this.updateDisplay();
            }
        };
        initializeLayout();
    }

    private static ContextThemeWrapper createThemeWrapper(Context context) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{R.attr.sublimePickerStyle});
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, R.style.SublimePickerStyleLight);
        typedArrayObtainStyledAttributes.recycle();
        return new ContextThemeWrapper(context, resourceId);
    }

    private void initializeLayout() {
        Context context = getContext();
        SUtils.initializeResources(context);
        LayoutInflater.from(context).inflate(R.layout.sublime_picker_view_layout, (ViewGroup) this, true);
        this.mDefaultDateFormatter = DateFormat.getDateInstance(2, Locale.getDefault());
        DateFormat timeInstance = DateFormat.getTimeInstance(3, Locale.getDefault());
        this.mDefaultTimeFormatter = timeInstance;
        timeInstance.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        this.llMainContentHolder = (LinearLayout) findViewById(R.id.llMainContentHolder);
        this.mButtonLayout = new ButtonHandler(this);
        initializeRecurrencePickerSwitch();
        this.mDatePicker = (SublimeDatePicker) findViewById(R.id.datePicker);
        this.mTimePicker = (SublimeTimePicker) findViewById(R.id.timePicker);
        this.mSublimeRecurrencePicker = (SublimeRecurrencePicker) findViewById(R.id.repeat_option_picker);
    }

    public void initializePicker(SublimeOptions sublimeOptions, SublimeListenerAdapter sublimeListenerAdapter) {
        if (sublimeListenerAdapter == null) {
            throw new IllegalArgumentException("Listener cannot be null.");
        }
        if (sublimeOptions != null) {
            sublimeOptions.verifyValidity();
        } else {
            sublimeOptions = new SublimeOptions();
        }
        this.mOptions = sublimeOptions;
        this.mListener = sublimeListenerAdapter;
        processOptions();
        updateDisplay();
    }

    private void updateHiddenPicker() {
        if (this.mDatePickerEnabled && this.mTimePickerEnabled) {
            this.mHiddenPicker = this.mDatePicker.getVisibility() == 0 ? SublimeOptions.Picker.DATE_PICKER : SublimeOptions.Picker.TIME_PICKER;
            return;
        }
        if (this.mDatePickerEnabled) {
            this.mHiddenPicker = SublimeOptions.Picker.DATE_PICKER;
        } else if (this.mTimePickerEnabled) {
            this.mHiddenPicker = SublimeOptions.Picker.TIME_PICKER;
        } else {
            this.mHiddenPicker = SublimeOptions.Picker.INVALID;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCurrentPicker() {
        if (this.mHiddenPicker != SublimeOptions.Picker.INVALID) {
            this.mCurrentPicker = this.mHiddenPicker;
            return;
        }
        throw new RuntimeException("Logic issue: No valid option for mCurrentPicker");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDisplay() {
        if (this.mCurrentPicker == SublimeOptions.Picker.DATE_PICKER) {
            if (this.mTimePickerEnabled) {
                this.mTimePicker.setVisibility(8);
            }
            if (this.mRecurrencePickerEnabled) {
                this.mSublimeRecurrencePicker.setVisibility(8);
            }
            this.mDatePicker.setVisibility(0);
            this.llMainContentHolder.setVisibility(0);
            if (this.mButtonLayout.isSwitcherButtonEnabled()) {
                Date date = new Date((this.mTimePicker.getCurrentHour() * 3600000) + (this.mTimePicker.getCurrentMinute() * 60000));
                CharSequence time = this.mListener.formatTime(date);
                if (TextUtils.isEmpty(time)) {
                    time = this.mDefaultTimeFormatter.format(date);
                }
                this.mButtonLayout.updateSwitcherText(SublimeOptions.Picker.DATE_PICKER, time);
            }
            if (this.mDatePickerSyncStateCalled) {
                return;
            }
            this.mDatePickerSyncStateCalled = true;
            return;
        }
        if (this.mCurrentPicker == SublimeOptions.Picker.TIME_PICKER) {
            if (this.mDatePickerEnabled) {
                this.mDatePicker.setVisibility(8);
            }
            if (this.mRecurrencePickerEnabled) {
                this.mSublimeRecurrencePicker.setVisibility(8);
            }
            this.mTimePicker.setVisibility(0);
            this.llMainContentHolder.setVisibility(0);
            if (this.mButtonLayout.isSwitcherButtonEnabled()) {
                SelectedDate selectedDate = this.mDatePicker.getSelectedDate();
                CharSequence date2 = this.mListener.formatDate(selectedDate);
                if (TextUtils.isEmpty(date2)) {
                    if (selectedDate.getType() == SelectedDate.Type.SINGLE) {
                        date2 = this.mDefaultDateFormatter.format(new Date(this.mDatePicker.getSelectedDateInMillis()));
                    } else if (selectedDate.getType() == SelectedDate.Type.RANGE) {
                        date2 = formatDateRange(selectedDate);
                    }
                }
                this.mButtonLayout.updateSwitcherText(SublimeOptions.Picker.TIME_PICKER, date2);
                return;
            }
            return;
        }
        if (this.mCurrentPicker == SublimeOptions.Picker.REPEAT_OPTION_PICKER) {
            updateHiddenPicker();
            this.mSublimeRecurrencePicker.updateView();
            if (this.mDatePickerEnabled || this.mTimePickerEnabled) {
                this.llMainContentHolder.setVisibility(8);
            }
            this.mSublimeRecurrencePicker.setVisibility(0);
        }
    }

    private String formatDateRange(SelectedDate selectedDate) {
        Calendar startDate = selectedDate.getStartDate();
        Calendar endDate = selectedDate.getEndDate();
        startDate.set(14, 0);
        startDate.set(13, 0);
        startDate.set(12, 0);
        startDate.set(10, 0);
        endDate.set(14, 0);
        endDate.set(13, 0);
        endDate.set(12, 0);
        endDate.set(10, 0);
        endDate.add(5, 1);
        float timeInMillis = endDate.getTimeInMillis() - startDate.getTimeInMillis();
        if (timeInMillis >= 3.14496E10f) {
            float f = timeInMillis / 3.14496E10f;
            int i = (int) f;
            if (f - ((float) i) > 0.5f) {
                i = (int) (f + 1.0f);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("~");
            sb.append(i);
            sb.append(" ");
            sb.append(i == 1 ? "year" : "years");
            return sb.toString();
        }
        if (timeInMillis >= 2.6208E9f) {
            float f2 = timeInMillis / 2.6208E9f;
            int i2 = (int) f2;
            if (f2 - ((float) i2) > 0.5f) {
                i2 = (int) (f2 + 1.0f);
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("~");
            sb2.append(i2);
            sb2.append(" ");
            sb2.append(i2 == 1 ? "month" : "months");
            return sb2.toString();
        }
        float f3 = timeInMillis / 8.64E7f;
        int i3 = (int) f3;
        if (f3 - ((float) i3) > 0.5f) {
            i3 = (int) (f3 + 1.0f);
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("~");
        sb3.append(i3);
        sb3.append(" ");
        sb3.append(i3 == 1 ? "day" : "days");
        return sb3.toString();
    }

    private void initializeRecurrencePickerSwitch() {
        this.ivRecurrenceOptionsDP = (ImageView) findViewById(R.id.ivRecurrenceOptionsDP);
        this.ivRecurrenceOptionsTP = (ImageView) findViewById(R.id.ivRecurrenceOptionsTP);
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(R.styleable.SublimePicker);
        try {
            int color = typedArrayObtainStyledAttributes.getColor(R.styleable.SublimePicker_spOverflowIconColor, SUtils.COLOR_TEXT_PRIMARY_INVERSE);
            int color2 = typedArrayObtainStyledAttributes.getColor(R.styleable.SublimePicker_spOverflowIconPressedBgColor, SUtils.COLOR_TEXT_PRIMARY);
            typedArrayObtainStyledAttributes.recycle();
            this.ivRecurrenceOptionsDP.setImageDrawable(new OverflowDrawable(getContext(), color));
            SUtils.setViewBackground(this.ivRecurrenceOptionsDP, SUtils.createOverflowButtonBg(color2));
            this.ivRecurrenceOptionsTP.setImageDrawable(new OverflowDrawable(getContext(), color));
            SUtils.setViewBackground(this.ivRecurrenceOptionsTP, SUtils.createOverflowButtonBg(color2));
            this.ivRecurrenceOptionsDP.setOnClickListener(new View.OnClickListener() { // from class: com.appeaser.sublimepickerlibrary.SublimePicker.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    SublimePicker.this.mCurrentPicker = SublimeOptions.Picker.REPEAT_OPTION_PICKER;
                    SublimePicker.this.updateDisplay();
                }
            });
            this.ivRecurrenceOptionsTP.setOnClickListener(new View.OnClickListener() { // from class: com.appeaser.sublimepickerlibrary.SublimePicker.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    SublimePicker.this.mCurrentPicker = SublimeOptions.Picker.REPEAT_OPTION_PICKER;
                    SublimePicker.this.updateDisplay();
                }
            });
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), this.mCurrentPicker, this.mHiddenPicker, this.mCurrentRecurrenceOption, this.mRecurrenceRule);
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        View.BaseSavedState baseSavedState = (View.BaseSavedState) parcelable;
        super.onRestoreInstanceState(baseSavedState.getSuperState());
        SavedState savedState = (SavedState) baseSavedState;
        this.mCurrentPicker = savedState.getCurrentPicker();
        this.mCurrentRecurrenceOption = savedState.getCurrentRepeatOption();
        this.mRecurrenceRule = savedState.getRecurrenceRule();
        this.mHiddenPicker = savedState.getHiddenPicker();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        super.dispatchRestoreInstanceState(sparseArray);
        updateDisplay();
    }

    private static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.appeaser.sublimepickerlibrary.SublimePicker.SavedState.1
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
        private final SublimeOptions.Picker sCurrentPicker;
        private final SublimeRecurrencePicker.RecurrenceOption sCurrentRecurrenceOption;
        private final SublimeOptions.Picker sHiddenPicker;
        private final String sRecurrenceRule;

        private SavedState(Parcelable parcelable, SublimeOptions.Picker picker, SublimeOptions.Picker picker2, SublimeRecurrencePicker.RecurrenceOption recurrenceOption, String str) {
            super(parcelable);
            this.sCurrentPicker = picker;
            this.sHiddenPicker = picker2;
            this.sCurrentRecurrenceOption = recurrenceOption;
            this.sRecurrenceRule = str;
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.sCurrentPicker = SublimeOptions.Picker.valueOf(parcel.readString());
            this.sHiddenPicker = SublimeOptions.Picker.valueOf(parcel.readString());
            this.sCurrentRecurrenceOption = SublimeRecurrencePicker.RecurrenceOption.valueOf(parcel.readString());
            this.sRecurrenceRule = parcel.readString();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.sCurrentPicker.name());
            parcel.writeString(this.sHiddenPicker.name());
            parcel.writeString(this.sCurrentRecurrenceOption.name());
            parcel.writeString(this.sRecurrenceRule);
        }

        public SublimeOptions.Picker getCurrentPicker() {
            return this.sCurrentPicker;
        }

        public SublimeOptions.Picker getHiddenPicker() {
            return this.sHiddenPicker;
        }

        public SublimeRecurrencePicker.RecurrenceOption getCurrentRepeatOption() {
            return this.sCurrentRecurrenceOption;
        }

        public String getRecurrenceRule() {
            return this.sRecurrenceRule;
        }
    }

    private void processOptions() {
        Calendar calendarForLocale;
        if (this.mOptions.animateLayoutChanges()) {
            LayoutTransition layoutTransition = new LayoutTransition();
            if (SUtils.isApi_16_OrHigher()) {
                layoutTransition.enableTransitionType(4);
            }
            setLayoutTransition(layoutTransition);
        } else {
            setLayoutTransition(null);
        }
        this.mDatePickerEnabled = this.mOptions.isDatePickerActive();
        this.mTimePickerEnabled = this.mOptions.isTimePickerActive();
        this.mRecurrencePickerEnabled = this.mOptions.isRecurrencePickerActive();
        if (this.mDatePickerEnabled) {
            this.mDatePicker.init(this.mOptions.getDateParams(), this.mOptions.canPickDateRange(), this);
            long[] dateRange = this.mOptions.getDateRange();
            if (dateRange[0] != Long.MIN_VALUE) {
                this.mDatePicker.setMinDate(dateRange[0]);
            }
            if (dateRange[1] != Long.MIN_VALUE) {
                this.mDatePicker.setMaxDate(dateRange[1]);
            }
            this.mDatePicker.setValidationCallback(this);
            this.ivRecurrenceOptionsDP.setVisibility(this.mRecurrencePickerEnabled ? 0 : 8);
        } else {
            this.llMainContentHolder.removeView(this.mDatePicker);
            this.mDatePicker = null;
        }
        if (this.mTimePickerEnabled) {
            int[] timeParams = this.mOptions.getTimeParams();
            this.mTimePicker.setCurrentHour(timeParams[0]);
            this.mTimePicker.setCurrentMinute(timeParams[1]);
            this.mTimePicker.setIs24HourView(this.mOptions.is24HourView());
            this.mTimePicker.setValidationCallback(this);
            this.ivRecurrenceOptionsTP.setVisibility(this.mRecurrencePickerEnabled ? 0 : 8);
        } else {
            this.llMainContentHolder.removeView(this.mTimePicker);
            this.mTimePicker = null;
        }
        if (this.mDatePickerEnabled && this.mTimePickerEnabled) {
            this.mButtonLayout.applyOptions(true, this.mButtonLayoutCallback);
        } else {
            this.mButtonLayout.applyOptions(false, this.mButtonLayoutCallback);
        }
        if (!this.mDatePickerEnabled && !this.mTimePickerEnabled) {
            removeView(this.llMainContentHolder);
            this.llMainContentHolder = null;
            this.mButtonLayout = null;
        }
        this.mCurrentRecurrenceOption = this.mOptions.getRecurrenceOption();
        this.mRecurrenceRule = this.mOptions.getRecurrenceRule();
        if (this.mRecurrencePickerEnabled) {
            if (this.mDatePickerEnabled) {
                calendarForLocale = this.mDatePicker.getSelectedDate().getStartDate();
            } else {
                calendarForLocale = SUtils.getCalendarForLocale(null, Locale.getDefault());
            }
            this.mSublimeRecurrencePicker.initializeData(this.mRepeatOptionSetListener, this.mCurrentRecurrenceOption, this.mRecurrenceRule, calendarForLocale.getTimeInMillis());
        } else {
            removeView(this.mSublimeRecurrencePicker);
            this.mSublimeRecurrencePicker = null;
        }
        this.mCurrentPicker = this.mOptions.getPickerToShow();
        this.mHiddenPicker = SublimeOptions.Picker.INVALID;
    }

    private void reassessValidity() {
        this.mButtonLayout.updateValidity(this.mDatePickerValid && this.mTimePickerValid);
    }

    @Override // com.appeaser.sublimepickerlibrary.datepicker.SublimeDatePicker.OnDateChangedListener
    public void onDateChanged(SublimeDatePicker sublimeDatePicker, SelectedDate selectedDate) {
        this.mDatePicker.init(selectedDate, this.mOptions.canPickDateRange(), this);
    }

    @Override // com.appeaser.sublimepickerlibrary.datepicker.SublimeDatePicker.DatePickerValidationCallback
    public void onDatePickerValidationChanged(boolean z) {
        this.mDatePickerValid = z;
        reassessValidity();
    }

    @Override // com.appeaser.sublimepickerlibrary.timepicker.SublimeTimePicker.TimePickerValidationCallback
    public void onTimePickerValidationChanged(boolean z) {
        this.mTimePickerValid = z;
        reassessValidity();
    }
}
