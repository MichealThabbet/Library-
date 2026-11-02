package com.appeaser.sublimepickerlibrary.helpers;

import com.appeaser.sublimepickerlibrary.SublimePicker;
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import java.util.Date;

/* loaded from: classes.dex */
public abstract class SublimeListenerAdapter {
    public CharSequence formatDate(SelectedDate selectedDate) {
        return null;
    }

    public CharSequence formatTime(Date date) {
        return null;
    }

    public abstract void onCancelled();

    public abstract void onDateTimeRecurrenceSet(SublimePicker sublimePicker, SelectedDate selectedDate, int i, int i2, SublimeRecurrencePicker.RecurrenceOption recurrenceOption, String str);
}
