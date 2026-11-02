package it.piapostolos.android.pinotes.utils.date;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import it.piapostolos.android.pinotes.helpers.date.RecurrenceHelper;
import it.piapostolos.android.pinotes.models.listeners.OnReminderPickedListener;
import it.piapostolos.android.pinotes.utils.date.SublimePickerFragment;
import java.util.Calendar;

/* loaded from: classes3.dex */
public class ReminderPickers {
    private FragmentActivity mActivity;
    private OnReminderPickedListener mOnReminderPickedListener;

    public ReminderPickers(FragmentActivity fragmentActivity, OnReminderPickedListener onReminderPickedListener) {
        this.mActivity = fragmentActivity;
        this.mOnReminderPickedListener = onReminderPickedListener;
    }

    public void pick(Long l, String str) {
        showDateTimeSelectors(DateUtils.getCalendar(l), str);
    }

    private void showDateTimeSelectors(Calendar calendar, String str) {
        SublimePickerFragment sublimePickerFragment = new SublimePickerFragment();
        sublimePickerFragment.setCallback(new SublimePickerFragment.Callback() { // from class: it.piapostolos.android.pinotes.utils.date.ReminderPickers.1
            @Override // it.piapostolos.android.pinotes.utils.date.SublimePickerFragment.Callback
            public void onCancelled() {
            }

            @Override // it.piapostolos.android.pinotes.utils.date.SublimePickerFragment.Callback
            public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int i, int i2, SublimeRecurrencePicker.RecurrenceOption recurrenceOption, String str2) {
                Calendar firstDate = selectedDate.getFirstDate();
                firstDate.set(11, i);
                firstDate.set(12, i2);
                ReminderPickers.this.mOnReminderPickedListener.onReminderPicked(firstDate.getTimeInMillis());
                ReminderPickers.this.mOnReminderPickedListener.onRecurrenceReminderPicked(RecurrenceHelper.buildRecurrenceRuleByRecurrenceOptionAndRule(recurrenceOption, str2));
            }
        });
        SublimeOptions sublimeOptions = new SublimeOptions();
        sublimeOptions.setPickerToShow(SublimeOptions.Picker.TIME_PICKER);
        sublimeOptions.setDisplayOptions(7);
        sublimeOptions.setDateParams(calendar);
        sublimeOptions.setRecurrenceParams(SublimeRecurrencePicker.RecurrenceOption.CUSTOM, str);
        sublimeOptions.setTimeParams(calendar.get(11), calendar.get(12), DateUtils.is24HourMode(this.mActivity));
        Bundle bundle = new Bundle();
        bundle.putParcelable("SUBLIME_OPTIONS", sublimeOptions);
        sublimePickerFragment.setArguments(bundle);
        sublimePickerFragment.setStyle(1, 0);
        sublimePickerFragment.show(this.mActivity.getSupportFragmentManager(), "SUBLIME_PICKER");
    }
}
