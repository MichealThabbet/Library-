package com.appeaser.sublimepickerlibrary.helpers;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.appeaser.sublimepickerlibrary.utilities.SUtils;
import java.util.Calendar;
import java.util.Locale;

/* loaded from: classes.dex */
public class SublimeOptions implements Parcelable {
    public static final int ACTIVATE_DATE_PICKER = 1;
    public static final int ACTIVATE_RECURRENCE_PICKER = 4;
    public static final int ACTIVATE_TIME_PICKER = 2;
    public static final Parcelable.Creator<SublimeOptions> CREATOR = new Parcelable.Creator<SublimeOptions>() { // from class: com.appeaser.sublimepickerlibrary.helpers.SublimeOptions.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SublimeOptions createFromParcel(Parcel parcel) {
            return new SublimeOptions(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SublimeOptions[] newArray(int i) {
            return new SublimeOptions[i];
        }
    };
    private boolean mAnimateLayoutChanges;
    private boolean mCanPickDateRange;
    private int mDisplayOptions;
    private int mEndDayOfMonth;
    private int mEndMonth;
    private int mEndYear;
    private int mHourOfDay;
    private boolean mIs24HourView;
    private long mMaxDate;
    private long mMinDate;
    private int mMinute;
    private Picker mPickerToShow;
    private SublimeRecurrencePicker.RecurrenceOption mRecurrenceOption;
    private String mRecurrenceRule;
    private int mStartDayOfMonth;
    private int mStartMonth;
    private int mStartYear;

    public enum Picker {
        DATE_PICKER,
        TIME_PICKER,
        REPEAT_OPTION_PICKER,
        INVALID
    }

    private boolean areValidDisplayOptions(int i) {
        return (i & (-8)) == 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SublimeOptions() {
        this.mDisplayOptions = 7;
        this.mStartYear = -1;
        this.mStartMonth = -1;
        this.mStartDayOfMonth = -1;
        this.mEndYear = -1;
        this.mEndMonth = -1;
        this.mEndDayOfMonth = -1;
        this.mHourOfDay = -1;
        this.mMinute = -1;
        this.mMinDate = Long.MIN_VALUE;
        this.mMaxDate = Long.MIN_VALUE;
        this.mRecurrenceOption = SublimeRecurrencePicker.RecurrenceOption.DOES_NOT_REPEAT;
        this.mRecurrenceRule = "";
        this.mPickerToShow = Picker.DATE_PICKER;
    }

    private SublimeOptions(Parcel parcel) {
        this.mDisplayOptions = 7;
        this.mStartYear = -1;
        this.mStartMonth = -1;
        this.mStartDayOfMonth = -1;
        this.mEndYear = -1;
        this.mEndMonth = -1;
        this.mEndDayOfMonth = -1;
        this.mHourOfDay = -1;
        this.mMinute = -1;
        this.mMinDate = Long.MIN_VALUE;
        this.mMaxDate = Long.MIN_VALUE;
        this.mRecurrenceOption = SublimeRecurrencePicker.RecurrenceOption.DOES_NOT_REPEAT;
        this.mRecurrenceRule = "";
        this.mPickerToShow = Picker.DATE_PICKER;
        readFromParcel(parcel);
    }

    public SublimeOptions setAnimateLayoutChanges(boolean z) {
        this.mAnimateLayoutChanges = z;
        return this;
    }

    public boolean animateLayoutChanges() {
        return this.mAnimateLayoutChanges;
    }

    public SublimeOptions setPickerToShow(Picker picker) {
        this.mPickerToShow = picker;
        return this;
    }

    /* renamed from: com.appeaser.sublimepickerlibrary.helpers.SublimeOptions$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$appeaser$sublimepickerlibrary$helpers$SublimeOptions$Picker;

        static {
            int[] iArr = new int[Picker.values().length];
            $SwitchMap$com$appeaser$sublimepickerlibrary$helpers$SublimeOptions$Picker = iArr;
            try {
                iArr[Picker.DATE_PICKER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$appeaser$sublimepickerlibrary$helpers$SublimeOptions$Picker[Picker.TIME_PICKER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$appeaser$sublimepickerlibrary$helpers$SublimeOptions$Picker[Picker.REPEAT_OPTION_PICKER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private boolean isPickerActive(Picker picker) {
        int i = AnonymousClass2.$SwitchMap$com$appeaser$sublimepickerlibrary$helpers$SublimeOptions$Picker[picker.ordinal()];
        if (i == 1) {
            return isDatePickerActive();
        }
        if (i == 2) {
            return isTimePickerActive();
        }
        if (i != 3) {
            return false;
        }
        return isRecurrencePickerActive();
    }

    public Picker getPickerToShow() {
        return this.mPickerToShow;
    }

    public SublimeOptions setDisplayOptions(int i) {
        if (!areValidDisplayOptions(i)) {
            throw new IllegalArgumentException("Invalid display options.");
        }
        this.mDisplayOptions = i;
        return this;
    }

    public SublimeOptions setDateParams(int i, int i2, int i3) {
        return setDateParams(i, i2, i3, i, i2, i3);
    }

    public SublimeOptions setDateParams(int i, int i2, int i3, int i4, int i5, int i6) {
        this.mStartYear = i;
        this.mStartMonth = i2;
        this.mStartDayOfMonth = i3;
        this.mEndYear = i4;
        this.mEndMonth = i5;
        this.mEndDayOfMonth = i6;
        return this;
    }

    public SublimeOptions setDateParams(Calendar calendar) {
        return setDateParams(calendar.get(1), calendar.get(2), calendar.get(5), calendar.get(1), calendar.get(2), calendar.get(5));
    }

    public SublimeOptions setDateParams(Calendar calendar, Calendar calendar2) {
        return setDateParams(calendar.get(1), calendar.get(2), calendar.get(5), calendar2.get(1), calendar2.get(2), calendar2.get(5));
    }

    public SublimeOptions setDateParams(SelectedDate selectedDate) {
        return setDateParams(selectedDate.getStartDate().get(1), selectedDate.getStartDate().get(2), selectedDate.getStartDate().get(5), selectedDate.getEndDate().get(1), selectedDate.getEndDate().get(2), selectedDate.getEndDate().get(5));
    }

    public SublimeOptions setDateRange(long j, long j2) {
        this.mMinDate = j;
        this.mMaxDate = j2;
        return this;
    }

    public SublimeOptions setTimeParams(int i, int i2, boolean z) {
        this.mHourOfDay = i;
        this.mMinute = i2;
        this.mIs24HourView = z;
        return this;
    }

    public SublimeOptions setRecurrenceParams(SublimeRecurrencePicker.RecurrenceOption recurrenceOption, String str) {
        if (recurrenceOption == null || (recurrenceOption == SublimeRecurrencePicker.RecurrenceOption.CUSTOM && TextUtils.isEmpty(str))) {
            recurrenceOption = SublimeRecurrencePicker.RecurrenceOption.DOES_NOT_REPEAT;
        } else {
            if (recurrenceOption != SublimeRecurrencePicker.RecurrenceOption.CUSTOM) {
            }
            this.mRecurrenceOption = recurrenceOption;
            this.mRecurrenceRule = str;
            return this;
        }
        str = null;
        this.mRecurrenceOption = recurrenceOption;
        this.mRecurrenceRule = str;
        return this;
    }

    public String getRecurrenceRule() {
        String str = this.mRecurrenceRule;
        return str == null ? "" : str;
    }

    public SublimeRecurrencePicker.RecurrenceOption getRecurrenceOption() {
        SublimeRecurrencePicker.RecurrenceOption recurrenceOption = this.mRecurrenceOption;
        return recurrenceOption == null ? SublimeRecurrencePicker.RecurrenceOption.DOES_NOT_REPEAT : recurrenceOption;
    }

    public boolean isDatePickerActive() {
        return (this.mDisplayOptions & 1) == 1;
    }

    public boolean isTimePickerActive() {
        return (this.mDisplayOptions & 2) == 2;
    }

    public boolean isRecurrencePickerActive() {
        return (this.mDisplayOptions & 4) == 4;
    }

    public SelectedDate getDateParams() {
        int i;
        int i2;
        int i3;
        int i4;
        Calendar calendarForLocale = SUtils.getCalendarForLocale(null, Locale.getDefault());
        int i5 = this.mStartYear;
        if (i5 == -1 || (i3 = this.mStartMonth) == -1 || (i4 = this.mStartDayOfMonth) == -1) {
            this.mStartYear = calendarForLocale.get(1);
            this.mStartMonth = calendarForLocale.get(2);
            this.mStartDayOfMonth = calendarForLocale.get(5);
        } else {
            calendarForLocale.set(i5, i3, i4);
        }
        Calendar calendarForLocale2 = SUtils.getCalendarForLocale(null, Locale.getDefault());
        int i6 = this.mEndYear;
        if (i6 == -1 || (i = this.mEndMonth) == -1 || (i2 = this.mEndDayOfMonth) == -1) {
            this.mEndYear = calendarForLocale2.get(1);
            this.mEndMonth = calendarForLocale2.get(2);
            this.mEndDayOfMonth = calendarForLocale2.get(5);
        } else {
            calendarForLocale2.set(i6, i, i2);
        }
        return new SelectedDate(calendarForLocale, calendarForLocale2);
    }

    public long[] getDateRange() {
        return new long[]{this.mMinDate, this.mMaxDate};
    }

    public int[] getTimeParams() {
        if (this.mHourOfDay == -1 || this.mMinute == -1) {
            Calendar calendarForLocale = SUtils.getCalendarForLocale(null, Locale.getDefault());
            this.mHourOfDay = calendarForLocale.get(11);
            this.mMinute = calendarForLocale.get(12);
        }
        return new int[]{this.mHourOfDay, this.mMinute};
    }

    public boolean is24HourView() {
        return this.mIs24HourView;
    }

    public void verifyValidity() {
        Picker picker = this.mPickerToShow;
        if (picker == null || picker == Picker.INVALID) {
            throw new InvalidOptionsException("The picker set using setPickerToShow(Picker) cannot be null or Picker.INVALID.");
        }
        if (isPickerActive(this.mPickerToShow)) {
            return;
        }
        throw new InvalidOptionsException("The picker you have requested to show(" + this.mPickerToShow.name() + ") is not activated. Use setDisplayOptions(int) to activate it, or use an activated Picker with setPickerToShow(Picker).");
    }

    public SublimeOptions setCanPickDateRange(boolean z) {
        this.mCanPickDateRange = z;
        return this;
    }

    public boolean canPickDateRange() {
        return this.mCanPickDateRange;
    }

    private void readFromParcel(Parcel parcel) {
        this.mAnimateLayoutChanges = parcel.readByte() != 0;
        this.mPickerToShow = Picker.valueOf(parcel.readString());
        this.mDisplayOptions = parcel.readInt();
        this.mStartYear = parcel.readInt();
        this.mStartMonth = parcel.readInt();
        this.mStartDayOfMonth = parcel.readInt();
        this.mEndYear = parcel.readInt();
        this.mEndMonth = parcel.readInt();
        this.mEndDayOfMonth = parcel.readInt();
        this.mHourOfDay = parcel.readInt();
        this.mMinute = parcel.readInt();
        this.mIs24HourView = parcel.readByte() != 0;
        this.mRecurrenceRule = parcel.readString();
        this.mCanPickDateRange = parcel.readByte() != 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mAnimateLayoutChanges ? (byte) 1 : (byte) 0);
        parcel.writeString(this.mPickerToShow.name());
        parcel.writeInt(this.mDisplayOptions);
        parcel.writeInt(this.mStartYear);
        parcel.writeInt(this.mStartMonth);
        parcel.writeInt(this.mStartDayOfMonth);
        parcel.writeInt(this.mEndYear);
        parcel.writeInt(this.mEndMonth);
        parcel.writeInt(this.mEndDayOfMonth);
        parcel.writeInt(this.mHourOfDay);
        parcel.writeInt(this.mMinute);
        parcel.writeByte(this.mIs24HourView ? (byte) 1 : (byte) 0);
        parcel.writeString(this.mRecurrenceRule);
        parcel.writeByte(this.mCanPickDateRange ? (byte) 1 : (byte) 0);
    }

    public class InvalidOptionsException extends RuntimeException {
        public InvalidOptionsException(String str) {
            super(str);
        }
    }
}
