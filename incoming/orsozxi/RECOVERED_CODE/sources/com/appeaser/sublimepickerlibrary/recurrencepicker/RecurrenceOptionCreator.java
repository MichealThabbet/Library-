package com.appeaser.sublimepickerlibrary.recurrencepicker;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TimeFormatException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import com.appeaser.sublimepickerlibrary.R;
import com.appeaser.sublimepickerlibrary.common.DecisionButtonLayout;
import com.appeaser.sublimepickerlibrary.datepicker.RecurrenceEndDatePicker;
import com.appeaser.sublimepickerlibrary.drawables.CheckableDrawable;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.appeaser.sublimepickerlibrary.utilities.RecurrenceUtils;
import com.appeaser.sublimepickerlibrary.utilities.SUtils;
import com.google.android.material.timepicker.TimeModel;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/* loaded from: classes.dex */
public class RecurrenceOptionCreator extends FrameLayout implements AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener, RecurrenceEndDatePicker.OnDateSetListener {
    private static final int COUNT_DEFAULT = 5;
    private static final int COUNT_MAX = 730;
    private static final int FIFTH_WEEK_IN_A_MONTH = 5;
    private static final int INTERVAL_DEFAULT = 1;
    private static final int INTERVAL_MAX = 99;
    private static final int LAST_NTH_DAY_OF_WEEK = -1;
    private static final String TAG = "RecurrenceOptionCreator";
    private static final int[] mFreqModelToEventRecurrence = {4, 5, 6, 7};
    private final int[] TIME_DAY_TO_CALENDAR_DAY;
    private DecisionButtonLayout mButtonLayout;
    private DecisionButtonLayout.Callback mButtonLayoutCallback;
    private RecurrenceEndDatePicker mDateOnlyPicker;
    private EditText mEndCount;
    private String mEndCountLabel;
    private DateFormat mEndDateFormatter;
    private String mEndDateLabel;
    private TextView mEndDateTextView;
    private String mEndNeverStr;
    private Spinner mEndSpinner;
    private EndSpinnerAdapter mEndSpinnerAdapter;
    private ArrayList<CharSequence> mEndSpinnerArray;
    private Spinner mFreqSpinner;
    int mHeaderBackgroundColor;
    private boolean mHidePostEndCount;
    private EditText mInterval;
    private TextView mIntervalPostText;
    private TextView mIntervalPreText;
    private int mIntervalResId;
    private RecurrenceModel mModel;
    private String mMonthRepeatByDayOfWeekStr;
    private String[][] mMonthRepeatByDayOfWeekStrs;
    private RadioGroup mMonthRepeatByRadioGroup;
    private TextView mPostEndCount;
    private EventRecurrence mRecurrence;
    private View mRecurrencePicker;
    private OnRecurrenceSetListener mRecurrenceSetListener;
    private RadioButton mRepeatMonthlyByNthDayOfMonth;
    private RadioButton mRepeatMonthlyByNthDayOfWeek;
    private Resources mResources;
    private Time mTime;
    private Toast mToast;
    private WeekButton[] mWeekByDayButtons;
    private LinearLayout mWeekGroup;
    private LinearLayout mWeekGroup2;

    private enum CurrentView {
        RECURRENCE_PICKER,
        DATE_ONLY_PICKER
    }

    public interface OnRecurrenceSetListener {
        void onCancelled();

        void onRecurrenceSet(String str);
    }

    public static boolean isSupportedMonthlyByNthDayOfWeek(int i) {
        return (i > 0 && i <= 5) || i == -1;
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    SublimeRecurrencePicker.RecurrenceOption resolveRepeatOption() {
        if (this.mModel.freq == 0 && this.mModel.interval == 1 && this.mModel.end == 0) {
            return SublimeRecurrencePicker.RecurrenceOption.DAILY;
        }
        return SublimeRecurrencePicker.RecurrenceOption.CUSTOM;
    }

    private class RecurrenceModel implements Parcelable {
        static final int END_BY_COUNT = 2;
        static final int END_BY_DATE = 1;
        static final int END_NEVER = 0;
        static final int FREQ_DAILY = 0;
        static final int FREQ_MONTHLY = 2;
        static final int FREQ_WEEKLY = 1;
        static final int FREQ_YEARLY = 3;
        static final int MONTHLY_BY_DATE = 0;
        static final int MONTHLY_BY_NTH_DAY_OF_WEEK = 1;
        static final int STATE_NO_RECURRENCE = 0;
        static final int STATE_RECURRENCE = 1;
        public final Parcelable.Creator<RecurrenceModel> CREATOR;
        int end;
        int endCount;
        Time endDate;
        int freq;
        int interval;
        int monthlyByDayOfWeek;
        int monthlyByMonthDay;
        int monthlyByNthDayOfWeek;
        int monthlyRepeat;
        int recurrenceState;
        boolean[] weeklyByDayOfWeek;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public String toString() {
            return "Model [freq=" + this.freq + ", interval=" + this.interval + ", end=" + this.end + ", endDate=" + this.endDate + ", endCount=" + this.endCount + ", weeklyByDayOfWeek=" + Arrays.toString(this.weeklyByDayOfWeek) + ", monthlyRepeat=" + this.monthlyRepeat + ", monthlyByMonthDay=" + this.monthlyByMonthDay + ", monthlyByDayOfWeek=" + this.monthlyByDayOfWeek + ", monthlyByNthDayOfWeek=" + this.monthlyByNthDayOfWeek + "]";
        }

        public RecurrenceModel() {
            this.freq = 1;
            this.interval = 1;
            this.endCount = 5;
            this.weeklyByDayOfWeek = new boolean[7];
            this.CREATOR = new Parcelable.Creator<RecurrenceModel>() { // from class: com.appeaser.sublimepickerlibrary.recurrencepicker.RecurrenceOptionCreator.RecurrenceModel.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public RecurrenceModel createFromParcel(Parcel parcel) {
                    return RecurrenceOptionCreator.this.new RecurrenceModel(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public RecurrenceModel[] newArray(int i) {
                    return new RecurrenceModel[i];
                }
            };
        }

        public RecurrenceModel(Parcel parcel) {
            this.freq = 1;
            this.interval = 1;
            this.endCount = 5;
            this.weeklyByDayOfWeek = new boolean[7];
            this.CREATOR = new Parcelable.Creator<RecurrenceModel>() { // from class: com.appeaser.sublimepickerlibrary.recurrencepicker.RecurrenceOptionCreator.RecurrenceModel.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public RecurrenceModel createFromParcel(Parcel parcel2) {
                    return RecurrenceOptionCreator.this.new RecurrenceModel(parcel2);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public RecurrenceModel[] newArray(int i) {
                    return new RecurrenceModel[i];
                }
            };
            readFromParcel(parcel);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.freq);
            parcel.writeInt(this.interval);
            parcel.writeInt(this.end);
            parcel.writeInt(this.endDate.year);
            parcel.writeInt(this.endDate.month);
            parcel.writeInt(this.endDate.monthDay);
            parcel.writeInt(this.endCount);
            parcel.writeBooleanArray(this.weeklyByDayOfWeek);
            parcel.writeInt(this.monthlyRepeat);
            parcel.writeInt(this.monthlyByMonthDay);
            parcel.writeInt(this.monthlyByDayOfWeek);
            parcel.writeInt(this.monthlyByNthDayOfWeek);
            parcel.writeInt(this.recurrenceState);
        }

        private void readFromParcel(Parcel parcel) {
            this.freq = parcel.readInt();
            this.interval = parcel.readInt();
            this.end = parcel.readInt();
            Time time = new Time();
            this.endDate = time;
            time.year = parcel.readInt();
            this.endDate.month = parcel.readInt();
            this.endDate.monthDay = parcel.readInt();
            this.endCount = parcel.readInt();
            parcel.readBooleanArray(this.weeklyByDayOfWeek);
            this.monthlyRepeat = parcel.readInt();
            this.monthlyByMonthDay = parcel.readInt();
            this.monthlyByDayOfWeek = parcel.readInt();
            this.monthlyByNthDayOfWeek = parcel.readInt();
            this.recurrenceState = parcel.readInt();
        }
    }

    class minMaxTextWatcher implements TextWatcher {
        private int mDefault;
        private int mMax;
        private int mMin;

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        void onChange(int i) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public minMaxTextWatcher(int i, int i2, int i3) {
            this.mMin = i;
            this.mMax = i3;
            this.mDefault = i2;
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) throws NumberFormatException {
            int i;
            try {
                i = Integer.parseInt(editable.toString());
            } catch (NumberFormatException unused) {
                i = this.mDefault;
            }
            int i2 = this.mMin;
            boolean z = true;
            if (i >= i2 && i <= (i2 = this.mMax)) {
                z = false;
            } else {
                i = i2;
            }
            if (z) {
                editable.clear();
                editable.append((CharSequence) Integer.toString(i));
            }
            RecurrenceOptionCreator.this.updateDoneButtonState();
            onChange(i);
        }
    }

    public static boolean canHandleRecurrenceRule(EventRecurrence eventRecurrence) {
        int i = eventRecurrence.freq;
        if (i != 4 && i != 5 && i != 6 && i != 7) {
            return false;
        }
        if (eventRecurrence.count > 0 && !TextUtils.isEmpty(eventRecurrence.until)) {
            return false;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < eventRecurrence.bydayCount; i3++) {
            if (isSupportedMonthlyByNthDayOfWeek(eventRecurrence.bydayNum[i3])) {
                i2++;
            }
        }
        if (i2 > 1) {
            return false;
        }
        if ((i2 > 0 && eventRecurrence.freq != 6) || eventRecurrence.bymonthdayCount > 1) {
            return false;
        }
        if (eventRecurrence.freq == 6) {
            if (eventRecurrence.bydayCount > 1) {
                return false;
            }
            if (eventRecurrence.bydayCount > 0 && eventRecurrence.bymonthdayCount > 0) {
                return false;
            }
        }
        return true;
    }

    private static void copyEventRecurrenceToModel(EventRecurrence eventRecurrence, RecurrenceModel recurrenceModel) {
        int i = eventRecurrence.freq;
        if (i == 4) {
            recurrenceModel.freq = 0;
        } else if (i == 5) {
            recurrenceModel.freq = 1;
        } else if (i == 6) {
            recurrenceModel.freq = 2;
        } else if (i == 7) {
            recurrenceModel.freq = 3;
        } else {
            throw new IllegalStateException("freq=" + eventRecurrence.freq);
        }
        if (eventRecurrence.interval > 0) {
            recurrenceModel.interval = eventRecurrence.interval;
        }
        recurrenceModel.endCount = eventRecurrence.count;
        if (recurrenceModel.endCount > 0) {
            recurrenceModel.end = 2;
        }
        if (!TextUtils.isEmpty(eventRecurrence.until)) {
            if (recurrenceModel.endDate == null) {
                recurrenceModel.endDate = new Time();
            }
            try {
                recurrenceModel.endDate.parse(eventRecurrence.until);
            } catch (TimeFormatException unused) {
                recurrenceModel.endDate = null;
            }
            if (recurrenceModel.end == 2 && recurrenceModel.endDate != null) {
                throw new IllegalStateException("freq=" + eventRecurrence.freq);
            }
            recurrenceModel.end = 1;
        }
        Arrays.fill(recurrenceModel.weeklyByDayOfWeek, false);
        if (eventRecurrence.bydayCount > 0) {
            int i2 = 0;
            for (int i3 = 0; i3 < eventRecurrence.bydayCount; i3++) {
                int iDay2TimeDay = EventRecurrence.day2TimeDay(eventRecurrence.byday[i3]);
                recurrenceModel.weeklyByDayOfWeek[iDay2TimeDay] = true;
                if (recurrenceModel.freq == 2 && isSupportedMonthlyByNthDayOfWeek(eventRecurrence.bydayNum[i3])) {
                    recurrenceModel.monthlyByDayOfWeek = iDay2TimeDay;
                    recurrenceModel.monthlyByNthDayOfWeek = eventRecurrence.bydayNum[i3];
                    recurrenceModel.monthlyRepeat = 1;
                    i2++;
                }
            }
            if (recurrenceModel.freq == 2) {
                if (eventRecurrence.bydayCount != 1) {
                    throw new IllegalStateException("Can handle only 1 byDayOfWeek in monthly");
                }
                if (i2 != 1) {
                    throw new IllegalStateException("Didn't specify which nth day of week to repeat for a monthly");
                }
            }
        }
        if (recurrenceModel.freq == 2) {
            if (eventRecurrence.bymonthdayCount == 1) {
                if (recurrenceModel.monthlyRepeat == 1) {
                    throw new IllegalStateException("Can handle only by monthday or by nth day of week, not both");
                }
                recurrenceModel.monthlyByMonthDay = eventRecurrence.bymonthday[0];
                recurrenceModel.monthlyRepeat = 0;
                return;
            }
            if (eventRecurrence.bymonthCount > 1) {
                throw new IllegalStateException("Can handle only one bymonthday");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void copyModelToEventRecurrence(RecurrenceModel recurrenceModel, EventRecurrence eventRecurrence) {
        if (recurrenceModel.recurrenceState == 0) {
            throw new IllegalStateException("There's no recurrence");
        }
        eventRecurrence.freq = mFreqModelToEventRecurrence[recurrenceModel.freq];
        if (recurrenceModel.interval <= 1) {
            eventRecurrence.interval = 0;
        } else {
            eventRecurrence.interval = recurrenceModel.interval;
        }
        int i = recurrenceModel.end;
        if (i != 1) {
            if (i == 2) {
                eventRecurrence.count = recurrenceModel.endCount;
                eventRecurrence.until = null;
                if (eventRecurrence.count <= 0) {
                    throw new IllegalStateException("count is " + eventRecurrence.count);
                }
            } else {
                eventRecurrence.count = 0;
                eventRecurrence.until = null;
            }
        } else if (recurrenceModel.endDate != null) {
            recurrenceModel.endDate.switchTimezone("UTC");
            recurrenceModel.endDate.normalize(false);
            eventRecurrence.until = recurrenceModel.endDate.format2445();
            eventRecurrence.count = 0;
        } else {
            throw new IllegalStateException("end = END_BY_DATE but endDate is null");
        }
        eventRecurrence.bydayCount = 0;
        eventRecurrence.bymonthdayCount = 0;
        int i2 = recurrenceModel.freq;
        if (i2 == 1) {
            int i3 = 0;
            for (int i4 = 0; i4 < 7; i4++) {
                if (recurrenceModel.weeklyByDayOfWeek[i4]) {
                    i3++;
                }
            }
            if (eventRecurrence.bydayCount < i3 || eventRecurrence.byday == null || eventRecurrence.bydayNum == null) {
                eventRecurrence.byday = new int[i3];
                eventRecurrence.bydayNum = new int[i3];
            }
            eventRecurrence.bydayCount = i3;
            for (int i5 = 6; i5 >= 0; i5--) {
                if (recurrenceModel.weeklyByDayOfWeek[i5]) {
                    i3--;
                    eventRecurrence.bydayNum[i3] = 0;
                    eventRecurrence.byday[i3] = EventRecurrence.timeDay2Day(i5);
                }
            }
        } else if (i2 == 2) {
            if (recurrenceModel.monthlyRepeat == 0) {
                if (recurrenceModel.monthlyByMonthDay > 0) {
                    if (eventRecurrence.bymonthday == null || eventRecurrence.bymonthdayCount < 1) {
                        eventRecurrence.bymonthday = new int[1];
                    }
                    eventRecurrence.bymonthday[0] = recurrenceModel.monthlyByMonthDay;
                    eventRecurrence.bymonthdayCount = 1;
                }
            } else if (recurrenceModel.monthlyRepeat == 1) {
                if (!isSupportedMonthlyByNthDayOfWeek(recurrenceModel.monthlyByNthDayOfWeek)) {
                    throw new IllegalStateException("month repeat by nth week but n is " + recurrenceModel.monthlyByNthDayOfWeek);
                }
                if (eventRecurrence.bydayCount < 1 || eventRecurrence.byday == null || eventRecurrence.bydayNum == null) {
                    eventRecurrence.byday = new int[1];
                    eventRecurrence.bydayNum = new int[1];
                }
                eventRecurrence.bydayCount = 1;
                eventRecurrence.byday[0] = EventRecurrence.timeDay2Day(recurrenceModel.monthlyByDayOfWeek);
                eventRecurrence.bydayNum[0] = recurrenceModel.monthlyByNthDayOfWeek;
            }
        }
        if (canHandleRecurrenceRule(eventRecurrence)) {
            return;
        }
        throw new IllegalStateException("UI generated recurrence that it can't handle. ER:" + eventRecurrence.toString() + " Model: " + recurrenceModel.toString());
    }

    public RecurrenceOptionCreator(Context context) {
        this(context, null);
    }

    public RecurrenceOptionCreator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.spRecurrenceOptionCreatorStyle);
    }

    public RecurrenceOptionCreator(Context context, AttributeSet attributeSet, int i) throws Resources.NotFoundException {
        super(SUtils.createThemeWrapper(context, R.attr.sublimePickerStyle, R.style.SublimePickerStyleLight, R.attr.spRecurrenceOptionCreatorStyle, R.style.RecurrenceOptionCreatorStyle), attributeSet, i);
        this.mRecurrence = new EventRecurrence();
        this.mTime = new Time();
        this.mModel = new RecurrenceModel();
        this.TIME_DAY_TO_CALENDAR_DAY = new int[]{1, 2, 3, 4, 5, 6, 7};
        this.mIntervalResId = -1;
        this.mEndSpinnerArray = new ArrayList<>(3);
        this.mWeekByDayButtons = new WeekButton[7];
        this.mButtonLayoutCallback = new DecisionButtonLayout.Callback() { // from class: com.appeaser.sublimepickerlibrary.recurrencepicker.RecurrenceOptionCreator.1
            @Override // com.appeaser.sublimepickerlibrary.common.DecisionButtonLayout.Callback
            public void onOkay() {
                String string;
                if (RecurrenceOptionCreator.this.mModel.recurrenceState == 0) {
                    string = null;
                } else {
                    RecurrenceOptionCreator.copyModelToEventRecurrence(RecurrenceOptionCreator.this.mModel, RecurrenceOptionCreator.this.mRecurrence);
                    string = RecurrenceOptionCreator.this.mRecurrence.toString();
                }
                RecurrenceOptionCreator.this.mRecurrenceSetListener.onRecurrenceSet(string);
            }

            @Override // com.appeaser.sublimepickerlibrary.common.DecisionButtonLayout.Callback
            public void onCancel() {
                RecurrenceOptionCreator.this.mRecurrenceSetListener.onCancelled();
            }
        };
        initializeLayout();
    }

    public RecurrenceOptionCreator(Context context, AttributeSet attributeSet, int i, int i2) throws Resources.NotFoundException {
        super(SUtils.createThemeWrapper(context, R.attr.sublimePickerStyle, R.style.SublimePickerStyleLight, R.attr.spRecurrenceOptionCreatorStyle, R.style.RecurrenceOptionCreatorStyle), attributeSet, i, i2);
        this.mRecurrence = new EventRecurrence();
        this.mTime = new Time();
        this.mModel = new RecurrenceModel();
        this.TIME_DAY_TO_CALENDAR_DAY = new int[]{1, 2, 3, 4, 5, 6, 7};
        this.mIntervalResId = -1;
        this.mEndSpinnerArray = new ArrayList<>(3);
        this.mWeekByDayButtons = new WeekButton[7];
        this.mButtonLayoutCallback = new DecisionButtonLayout.Callback() { // from class: com.appeaser.sublimepickerlibrary.recurrencepicker.RecurrenceOptionCreator.1
            @Override // com.appeaser.sublimepickerlibrary.common.DecisionButtonLayout.Callback
            public void onOkay() {
                String string;
                if (RecurrenceOptionCreator.this.mModel.recurrenceState == 0) {
                    string = null;
                } else {
                    RecurrenceOptionCreator.copyModelToEventRecurrence(RecurrenceOptionCreator.this.mModel, RecurrenceOptionCreator.this.mRecurrence);
                    string = RecurrenceOptionCreator.this.mRecurrence.toString();
                }
                RecurrenceOptionCreator.this.mRecurrenceSetListener.onRecurrenceSet(string);
            }

            @Override // com.appeaser.sublimepickerlibrary.common.DecisionButtonLayout.Callback
            public void onCancel() {
                RecurrenceOptionCreator.this.mRecurrenceSetListener.onCancelled();
            }
        };
        initializeLayout();
    }

    void initializeLayout() throws Resources.NotFoundException {
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(R.styleable.RecurrenceOptionCreator);
        try {
            this.mHeaderBackgroundColor = typedArrayObtainStyledAttributes.getColor(R.styleable.RecurrenceOptionCreator_spHeaderBackground, 0);
            int i = 1;
            this.mEndDateFormatter = DateFormat.getDateInstance(typedArrayObtainStyledAttributes.getInt(R.styleable.RecurrenceOptionCreator_spEndDateFormat, 1) == 0 ? 3 : 2, Locale.getDefault());
            int color = typedArrayObtainStyledAttributes.getColor(R.styleable.RecurrenceOptionCreator_spWeekButtonUnselectedTextColor, SUtils.COLOR_ACCENT);
            int color2 = typedArrayObtainStyledAttributes.getColor(R.styleable.RecurrenceOptionCreator_spWeekButtonSelectedTextColor, SUtils.COLOR_TEXT_PRIMARY_INVERSE);
            int color3 = typedArrayObtainStyledAttributes.getColor(R.styleable.RecurrenceOptionCreator_spWeekButtonSelectedCircleColor, SUtils.COLOR_ACCENT);
            typedArrayObtainStyledAttributes.recycle();
            this.mResources = getResources();
            LayoutInflater.from(getContext()).inflate(R.layout.recurrence_picker, this);
            this.mRecurrencePicker = findViewById(R.id.recurrence_picker);
            RecurrenceEndDatePicker recurrenceEndDatePicker = (RecurrenceEndDatePicker) findViewById(R.id.date_only_picker);
            this.mDateOnlyPicker = recurrenceEndDatePicker;
            recurrenceEndDatePicker.setVisibility(8);
            DecisionButtonLayout decisionButtonLayout = (DecisionButtonLayout) findViewById(R.id.roc_decision_button_layout);
            this.mButtonLayout = decisionButtonLayout;
            decisionButtonLayout.applyOptions(this.mButtonLayoutCallback);
            SUtils.setViewBackground(findViewById(R.id.freqSpinnerHolder), this.mHeaderBackgroundColor, 3);
            Spinner spinner = (Spinner) findViewById(R.id.freqSpinner);
            this.mFreqSpinner = spinner;
            spinner.setOnItemSelectedListener(this);
            ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(getContext(), R.array.recurrence_freq, R.layout.roc_freq_spinner_item);
            arrayAdapterCreateFromResource.setDropDownViewResource(R.layout.roc_spinner_dropdown_item);
            this.mFreqSpinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.abc_spinner_mtrl_am_alpha);
            PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(SUtils.COLOR_TEXT_PRIMARY_INVERSE, PorterDuff.Mode.SRC_IN);
            if (drawable != null) {
                drawable.setColorFilter(porterDuffColorFilter);
                SUtils.setViewBackground(this.mFreqSpinner, drawable);
            }
            EditText editText = (EditText) findViewById(R.id.interval);
            this.mInterval = editText;
            editText.addTextChangedListener(new minMaxTextWatcher(i, i, 99) { // from class: com.appeaser.sublimepickerlibrary.recurrencepicker.RecurrenceOptionCreator.2
                @Override // com.appeaser.sublimepickerlibrary.recurrencepicker.RecurrenceOptionCreator.minMaxTextWatcher
                void onChange(int i2) {
                    if (RecurrenceOptionCreator.this.mIntervalResId == -1 || RecurrenceOptionCreator.this.mInterval.getText().toString().length() <= 0) {
                        return;
                    }
                    RecurrenceOptionCreator.this.mModel.interval = i2;
                    RecurrenceOptionCreator.this.updateIntervalText();
                    RecurrenceOptionCreator.this.mInterval.requestLayout();
                }
            });
            this.mIntervalPreText = (TextView) findViewById(R.id.intervalPreText);
            this.mIntervalPostText = (TextView) findViewById(R.id.intervalPostText);
            this.mEndNeverStr = this.mResources.getString(R.string.recurrence_end_continously);
            this.mEndDateLabel = this.mResources.getString(R.string.recurrence_end_date_label);
            this.mEndCountLabel = this.mResources.getString(R.string.recurrence_end_count_label);
            this.mEndSpinnerArray.add(this.mEndNeverStr);
            this.mEndSpinnerArray.add(this.mEndDateLabel);
            this.mEndSpinnerArray.add(this.mEndCountLabel);
            Spinner spinner2 = (Spinner) findViewById(R.id.endSpinner);
            this.mEndSpinner = spinner2;
            spinner2.setOnItemSelectedListener(this);
            EndSpinnerAdapter endSpinnerAdapter = new EndSpinnerAdapter(getContext(), this.mEndSpinnerArray, R.layout.roc_end_spinner_item, R.id.spinner_item, R.layout.roc_spinner_dropdown_item);
            this.mEndSpinnerAdapter = endSpinnerAdapter;
            this.mEndSpinner.setAdapter((SpinnerAdapter) endSpinnerAdapter);
            EditText editText2 = (EditText) findViewById(R.id.endCount);
            this.mEndCount = editText2;
            editText2.addTextChangedListener(new minMaxTextWatcher(i, 5, 730) { // from class: com.appeaser.sublimepickerlibrary.recurrencepicker.RecurrenceOptionCreator.3
                @Override // com.appeaser.sublimepickerlibrary.recurrencepicker.RecurrenceOptionCreator.minMaxTextWatcher
                void onChange(int i2) throws Resources.NotFoundException {
                    if (RecurrenceOptionCreator.this.mModel.endCount != i2) {
                        RecurrenceOptionCreator.this.mModel.endCount = i2;
                        RecurrenceOptionCreator.this.updateEndCountText();
                        RecurrenceOptionCreator.this.mEndCount.requestLayout();
                    }
                }
            });
            this.mPostEndCount = (TextView) findViewById(R.id.postEndCount);
            TextView textView = (TextView) findViewById(R.id.endDate);
            this.mEndDateTextView = textView;
            textView.setOnClickListener(this);
            SUtils.setViewBackground(this.mEndDateTextView, SUtils.createButtonBg(getContext(), SUtils.COLOR_BUTTON_NORMAL, SUtils.COLOR_CONTROL_HIGHLIGHT));
            WeekButton.setStateColors(color, color2);
            this.mWeekGroup = (LinearLayout) findViewById(R.id.weekGroup);
            this.mWeekGroup2 = (LinearLayout) findViewById(R.id.weekGroup2);
            View viewFindViewById = findViewById(R.id.week_day_8);
            if (viewFindViewById != null) {
                viewFindViewById.setVisibility(4);
            }
            String[][] strArr = new String[7][];
            this.mMonthRepeatByDayOfWeekStrs = strArr;
            strArr[0] = this.mResources.getStringArray(R.array.repeat_by_nth_sun);
            this.mMonthRepeatByDayOfWeekStrs[1] = this.mResources.getStringArray(R.array.repeat_by_nth_mon);
            this.mMonthRepeatByDayOfWeekStrs[2] = this.mResources.getStringArray(R.array.repeat_by_nth_tues);
            this.mMonthRepeatByDayOfWeekStrs[3] = this.mResources.getStringArray(R.array.repeat_by_nth_wed);
            this.mMonthRepeatByDayOfWeekStrs[4] = this.mResources.getStringArray(R.array.repeat_by_nth_thurs);
            this.mMonthRepeatByDayOfWeekStrs[5] = this.mResources.getStringArray(R.array.repeat_by_nth_fri);
            this.mMonthRepeatByDayOfWeekStrs[6] = this.mResources.getStringArray(R.array.repeat_by_nth_sat);
            int firstDayOfWeek = RecurrenceUtils.getFirstDayOfWeek();
            String[] shortWeekdays = new DateFormatSymbols().getShortWeekdays();
            int dimensionPixelSize = this.mResources.getDimensionPixelSize(R.dimen.week_button_state_on_circle_size);
            WeekButton[] weekButtonArr = {(WeekButton) findViewById(R.id.week_day_1), (WeekButton) findViewById(R.id.week_day_2), (WeekButton) findViewById(R.id.week_day_3), (WeekButton) findViewById(R.id.week_day_4), (WeekButton) findViewById(R.id.week_day_5), (WeekButton) findViewById(R.id.week_day_6), (WeekButton) findViewById(R.id.week_day_7)};
            int i2 = 0;
            while (true) {
                WeekButton[] weekButtonArr2 = this.mWeekByDayButtons;
                if (i2 < weekButtonArr2.length) {
                    weekButtonArr2[firstDayOfWeek] = weekButtonArr[i2];
                    SUtils.setViewBackground(weekButtonArr2[firstDayOfWeek], new CheckableDrawable(color3, false, dimensionPixelSize));
                    this.mWeekByDayButtons[firstDayOfWeek].setTextColor(color);
                    this.mWeekByDayButtons[firstDayOfWeek].setTextOff(shortWeekdays[this.TIME_DAY_TO_CALENDAR_DAY[firstDayOfWeek]]);
                    this.mWeekByDayButtons[firstDayOfWeek].setTextOn(shortWeekdays[this.TIME_DAY_TO_CALENDAR_DAY[firstDayOfWeek]]);
                    this.mWeekByDayButtons[firstDayOfWeek].setOnCheckedChangeListener(this);
                    firstDayOfWeek++;
                    if (firstDayOfWeek >= 7) {
                        firstDayOfWeek = 0;
                    }
                    i2++;
                } else {
                    RadioGroup radioGroup = (RadioGroup) findViewById(R.id.monthGroup);
                    this.mMonthRepeatByRadioGroup = radioGroup;
                    radioGroup.setOnCheckedChangeListener(this);
                    this.mRepeatMonthlyByNthDayOfWeek = (RadioButton) findViewById(R.id.repeatMonthlyByNthDayOfTheWeek);
                    this.mRepeatMonthlyByNthDayOfMonth = (RadioButton) findViewById(R.id.repeatMonthlyByNthDayOfMonth);
                    return;
                }
            }
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }

    public void initializeData(long j, String str, String str2, OnRecurrenceSetListener onRecurrenceSetListener) {
        this.mRecurrence.wkst = EventRecurrence.timeDay2Day(RecurrenceUtils.getFirstDayOfWeek());
        this.mRecurrenceSetListener = onRecurrenceSetListener;
        this.mTime.set(j);
        if (!TextUtils.isEmpty(str)) {
            this.mTime.timezone = str;
        }
        this.mTime.normalize(false);
        this.mModel.weeklyByDayOfWeek[this.mTime.weekDay] = true;
        if (!TextUtils.isEmpty(str2)) {
            this.mModel.recurrenceState = 1;
            this.mRecurrence.parse(str2);
            copyEventRecurrenceToModel(this.mRecurrence, this.mModel);
            if (this.mRecurrence.bydayCount == 0) {
                this.mModel.weeklyByDayOfWeek[this.mTime.weekDay] = true;
            }
        } else {
            this.mModel.recurrenceState = 1;
        }
        if (this.mModel.endDate == null) {
            this.mModel.endDate = new Time(this.mTime);
            int i = this.mModel.freq;
            if (i == 0 || i == 1) {
                this.mModel.endDate.month++;
            } else if (i == 2) {
                this.mModel.endDate.month += 3;
            } else if (i == 3) {
                this.mModel.endDate.year += 3;
            }
            this.mModel.endDate.normalize(false);
        }
        togglePickerOptions();
        updateDialog();
        showRecurrencePicker();
    }

    private void togglePickerOptions() {
        if (this.mModel.recurrenceState == 0) {
            this.mFreqSpinner.setEnabled(false);
            this.mEndSpinner.setEnabled(false);
            this.mIntervalPreText.setEnabled(false);
            this.mInterval.setEnabled(false);
            this.mIntervalPostText.setEnabled(false);
            this.mMonthRepeatByRadioGroup.setEnabled(false);
            this.mEndCount.setEnabled(false);
            this.mPostEndCount.setEnabled(false);
            this.mEndDateTextView.setEnabled(false);
            this.mRepeatMonthlyByNthDayOfWeek.setEnabled(false);
            this.mRepeatMonthlyByNthDayOfMonth.setEnabled(false);
            for (WeekButton weekButton : this.mWeekByDayButtons) {
                weekButton.setEnabled(false);
            }
        } else {
            findViewById(R.id.options).setEnabled(true);
            this.mFreqSpinner.setEnabled(true);
            this.mEndSpinner.setEnabled(true);
            this.mIntervalPreText.setEnabled(true);
            this.mInterval.setEnabled(true);
            this.mIntervalPostText.setEnabled(true);
            this.mMonthRepeatByRadioGroup.setEnabled(true);
            this.mEndCount.setEnabled(true);
            this.mPostEndCount.setEnabled(true);
            this.mEndDateTextView.setEnabled(true);
            this.mRepeatMonthlyByNthDayOfWeek.setEnabled(true);
            this.mRepeatMonthlyByNthDayOfMonth.setEnabled(true);
            for (WeekButton weekButton2 : this.mWeekByDayButtons) {
                weekButton2.setEnabled(true);
            }
        }
        updateDoneButtonState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDoneButtonState() {
        if (this.mModel.recurrenceState == 0) {
            this.mButtonLayout.updateValidity(true);
            return;
        }
        if (this.mInterval.getText().toString().length() == 0) {
            this.mButtonLayout.updateValidity(false);
            return;
        }
        if (this.mEndCount.getVisibility() == 0 && this.mEndCount.getText().toString().length() == 0) {
            this.mButtonLayout.updateValidity(false);
            return;
        }
        if (this.mModel.freq == 1) {
            for (WeekButton weekButton : this.mWeekByDayButtons) {
                if (weekButton.isChecked()) {
                    this.mButtonLayout.updateValidity(true);
                    return;
                }
            }
            this.mButtonLayout.updateValidity(false);
            return;
        }
        this.mButtonLayout.updateValidity(true);
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), this.mModel, this.mEndCount.hasFocus(), this.mRecurrencePicker.getVisibility() == 0 ? CurrentView.RECURRENCE_PICKER : CurrentView.DATE_ONLY_PICKER);
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        View.BaseSavedState baseSavedState = (View.BaseSavedState) parcelable;
        super.onRestoreInstanceState(baseSavedState.getSuperState());
        SavedState savedState = (SavedState) baseSavedState;
        final boolean endCountHasFocus = savedState.getEndCountHasFocus();
        RecurrenceModel recurrenceModel = savedState.getRecurrenceModel();
        if (recurrenceModel != null) {
            this.mModel = recurrenceModel;
        }
        this.mRecurrence.wkst = EventRecurrence.timeDay2Day(RecurrenceUtils.getFirstDayOfWeek());
        togglePickerOptions();
        updateDialog();
        if (savedState.getCurrentView() == CurrentView.RECURRENCE_PICKER) {
            showRecurrencePicker();
            post(new Runnable() { // from class: com.appeaser.sublimepickerlibrary.recurrencepicker.RecurrenceOptionCreator.4
                @Override // java.lang.Runnable
                public void run() {
                    if (RecurrenceOptionCreator.this.mEndCount == null || !endCountHasFocus) {
                        return;
                    }
                    RecurrenceOptionCreator.this.mEndCount.requestFocus();
                }
            });
        } else {
            showDateOnlyPicker();
        }
    }

    private static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.appeaser.sublimepickerlibrary.recurrencepicker.RecurrenceOptionCreator.SavedState.1
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
        private final boolean mEndCountHasFocus;
        private final RecurrenceModel mRecurrenceModel;
        private final CurrentView sCurrentView;

        private SavedState(Parcelable parcelable, RecurrenceModel recurrenceModel, boolean z, CurrentView currentView) {
            super(parcelable);
            this.mRecurrenceModel = recurrenceModel;
            this.mEndCountHasFocus = z;
            this.sCurrentView = currentView;
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.mRecurrenceModel = (RecurrenceModel) parcel.readParcelable(RecurrenceModel.class.getClassLoader());
            this.mEndCountHasFocus = parcel.readByte() != 0;
            this.sCurrentView = CurrentView.valueOf(parcel.readString());
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.mRecurrenceModel, i);
            parcel.writeByte(this.mEndCountHasFocus ? (byte) 1 : (byte) 0);
            parcel.writeString(this.sCurrentView.name());
        }

        public RecurrenceModel getRecurrenceModel() {
            return this.mRecurrenceModel;
        }

        public boolean getEndCountHasFocus() {
            return this.mEndCountHasFocus;
        }

        public CurrentView getCurrentView() {
            return this.sCurrentView;
        }
    }

    public void updateDialog() {
        String string = Integer.toString(this.mModel.interval);
        if (!string.equals(this.mInterval.getText().toString())) {
            this.mInterval.setText(string);
        }
        this.mFreqSpinner.setSelection(this.mModel.freq);
        this.mWeekGroup.setVisibility(this.mModel.freq == 1 ? 0 : 8);
        LinearLayout linearLayout = this.mWeekGroup2;
        if (linearLayout != null) {
            linearLayout.setVisibility(this.mModel.freq == 1 ? 0 : 8);
        }
        this.mMonthRepeatByRadioGroup.setVisibility(this.mModel.freq == 2 ? 0 : 8);
        int i = this.mModel.freq;
        if (i == 0) {
            this.mIntervalResId = R.plurals.recurrence_interval_daily;
        } else if (i == 1) {
            this.mIntervalResId = R.plurals.recurrence_interval_weekly;
            for (int i2 = 0; i2 < 7; i2++) {
                this.mWeekByDayButtons[i2].setCheckedNoAnimate(this.mModel.weeklyByDayOfWeek[i2]);
            }
        } else if (i == 2) {
            this.mIntervalResId = R.plurals.recurrence_interval_monthly;
            if (this.mModel.monthlyRepeat == 0) {
                this.mMonthRepeatByRadioGroup.check(R.id.repeatMonthlyByNthDayOfMonth);
            } else if (this.mModel.monthlyRepeat == 1) {
                this.mMonthRepeatByRadioGroup.check(R.id.repeatMonthlyByNthDayOfTheWeek);
            }
            if (this.mMonthRepeatByDayOfWeekStr == null) {
                if (this.mModel.monthlyByNthDayOfWeek == 0) {
                    this.mModel.monthlyByNthDayOfWeek = (this.mTime.monthDay + 6) / 7;
                    if (this.mModel.monthlyByNthDayOfWeek >= 5) {
                        this.mModel.monthlyByNthDayOfWeek = -1;
                    }
                    this.mModel.monthlyByDayOfWeek = this.mTime.weekDay;
                }
                String str = this.mMonthRepeatByDayOfWeekStrs[this.mModel.monthlyByDayOfWeek][(this.mModel.monthlyByNthDayOfWeek >= 0 ? this.mModel.monthlyByNthDayOfWeek : 5) - 1];
                this.mMonthRepeatByDayOfWeekStr = str;
                this.mRepeatMonthlyByNthDayOfWeek.setText(str);
            }
        } else if (i == 3) {
            this.mIntervalResId = R.plurals.recurrence_interval_yearly;
        }
        updateIntervalText();
        updateDoneButtonState();
        this.mEndSpinner.setSelection(this.mModel.end);
        if (this.mModel.end == 1) {
            this.mEndDateTextView.setText(this.mEndDateFormatter.format(Long.valueOf(this.mModel.endDate.toMillis(false))));
        } else if (this.mModel.end == 2) {
            String string2 = Integer.toString(this.mModel.endCount);
            if (string2.equals(this.mEndCount.getText().toString())) {
                return;
            }
            this.mEndCount.setText(string2);
        }
    }

    private void setEndSpinnerEndDateStr(String str) {
        this.mEndSpinnerArray.set(1, str);
        this.mEndSpinnerAdapter.notifyDataSetChanged();
    }

    private void doToast() {
        String string;
        Log.e(TAG, "Model = " + this.mModel.toString());
        if (this.mModel.recurrenceState == 0) {
            string = "Not repeating";
        } else {
            copyModelToEventRecurrence(this.mModel, this.mRecurrence);
            string = this.mRecurrence.toString();
        }
        Toast toast = this.mToast;
        if (toast != null) {
            toast.cancel();
        }
        Toast toastMakeText = Toast.makeText(getContext(), string, 1);
        this.mToast = toastMakeText;
        toastMakeText.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateIntervalText() {
        String quantityString;
        int iIndexOf;
        int i = this.mIntervalResId;
        if (i == -1 || (iIndexOf = (quantityString = this.mResources.getQuantityString(i, this.mModel.interval)).indexOf(TimeModel.NUMBER_FORMAT)) == -1) {
            return;
        }
        this.mIntervalPostText.setText(quantityString.substring(iIndexOf + 2, quantityString.length()).trim());
        this.mIntervalPreText.setText(quantityString.substring(0, iIndexOf).trim());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateEndCountText() throws Resources.NotFoundException {
        String quantityString = this.mResources.getQuantityString(R.plurals.recurrence_end_count, this.mModel.endCount);
        int iIndexOf = quantityString.indexOf(TimeModel.NUMBER_FORMAT);
        if (iIndexOf != -1) {
            if (iIndexOf == 0) {
                Log.e(TAG, "No text to put in to recurrence's end spinner.");
            } else {
                this.mPostEndCount.setText(quantityString.substring(iIndexOf + 2, quantityString.length()).trim());
            }
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) throws Resources.NotFoundException {
        if (adapterView == this.mFreqSpinner) {
            this.mModel.freq = i;
        } else if (adapterView == this.mEndSpinner) {
            if (i == 0) {
                this.mModel.end = 0;
            } else if (i == 1) {
                this.mModel.end = 1;
            } else if (i == 2) {
                this.mModel.end = 2;
                if (this.mModel.endCount <= 1) {
                    this.mModel.endCount = 1;
                } else if (this.mModel.endCount > 730) {
                    this.mModel.endCount = 730;
                }
                updateEndCountText();
            }
            this.mEndCount.setVisibility(this.mModel.end == 2 ? 0 : 8);
            this.mEndDateTextView.setVisibility(this.mModel.end == 1 ? 0 : 8);
            this.mPostEndCount.setVisibility((this.mModel.end != 2 || this.mHidePostEndCount) ? 8 : 0);
        }
        updateDialog();
    }

    @Override // com.appeaser.sublimepickerlibrary.datepicker.RecurrenceEndDatePicker.OnDateSetListener
    public void onDateSet(RecurrenceEndDatePicker recurrenceEndDatePicker, int i, int i2, int i3) {
        showRecurrencePicker();
        if (this.mModel.endDate == null) {
            this.mModel.endDate = new Time(this.mTime.timezone);
            Time time = this.mModel.endDate;
            Time time2 = this.mModel.endDate;
            this.mModel.endDate.second = 0;
            time2.minute = 0;
            time.hour = 0;
        }
        this.mModel.endDate.year = i;
        this.mModel.endDate.month = i2;
        this.mModel.endDate.monthDay = i3;
        this.mModel.endDate.normalize(false);
        updateDialog();
    }

    @Override // com.appeaser.sublimepickerlibrary.datepicker.RecurrenceEndDatePicker.OnDateSetListener
    public void onDateOnlyPickerCancelled(RecurrenceEndDatePicker recurrenceEndDatePicker) {
        showRecurrencePicker();
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        int i = -1;
        for (int i2 = 0; i2 < 7; i2++) {
            if (i == -1 && compoundButton == this.mWeekByDayButtons[i2]) {
                this.mModel.weeklyByDayOfWeek[i2] = z;
                i = i2;
            }
        }
        updateDialog();
    }

    @Override // android.widget.RadioGroup.OnCheckedChangeListener
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (i == R.id.repeatMonthlyByNthDayOfMonth) {
            this.mModel.monthlyRepeat = 0;
        } else if (i == R.id.repeatMonthlyByNthDayOfTheWeek) {
            this.mModel.monthlyRepeat = 1;
        }
        updateDialog();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.mEndDateTextView == view) {
            showDateOnlyPicker();
        }
    }

    private void showRecurrencePicker() {
        this.mDateOnlyPicker.setVisibility(8);
        this.mRecurrencePicker.setVisibility(0);
    }

    private void showDateOnlyPicker() {
        this.mDateOnlyPicker.init(this.mModel.endDate.year, this.mModel.endDate.month, this.mModel.endDate.monthDay, this);
        this.mDateOnlyPicker.setFirstDayOfWeek(RecurrenceUtils.getFirstDayOfWeekAsCalendar());
        this.mRecurrencePicker.setVisibility(8);
        this.mDateOnlyPicker.setVisibility(0);
    }

    private class EndSpinnerAdapter extends ArrayAdapter<CharSequence> {
        final String END_COUNT_MARKER;
        final String END_DATE_MARKER;
        private int mDropDownLayoutId;
        private String mEndDateString;
        private LayoutInflater mInflater;
        private int mItemLayoutId;
        private ArrayList<CharSequence> mStrings;
        private int mTextResourceId;
        private boolean mUseFormStrings;

        public EndSpinnerAdapter(Context context, ArrayList<CharSequence> arrayList, int i, int i2, int i3) throws Resources.NotFoundException {
            super(context, i, arrayList);
            this.END_DATE_MARKER = "%s";
            this.END_COUNT_MARKER = TimeModel.NUMBER_FORMAT;
            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
            this.mItemLayoutId = i;
            this.mTextResourceId = i2;
            this.mDropDownLayoutId = i3;
            this.mStrings = arrayList;
            String string = RecurrenceOptionCreator.this.getResources().getString(R.string.recurrence_end_date);
            this.mEndDateString = string;
            if (string.indexOf("%s") <= 0 || RecurrenceOptionCreator.this.getResources().getQuantityString(R.plurals.recurrence_end_count, 1).indexOf(TimeModel.NUMBER_FORMAT) <= 0) {
                this.mUseFormStrings = true;
            }
            if (this.mUseFormStrings) {
                RecurrenceOptionCreator.this.mEndSpinner.setLayoutParams(new TableLayout.LayoutParams(0, -2, 1.0f));
            }
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) throws Resources.NotFoundException {
            if (view == null) {
                view = this.mInflater.inflate(this.mItemLayoutId, viewGroup, false);
            }
            TextView textView = (TextView) view.findViewById(this.mTextResourceId);
            if (i == 0) {
                textView.setText(this.mStrings.get(0));
                return view;
            }
            if (i == 1) {
                int iIndexOf = this.mEndDateString.indexOf("%s");
                if (iIndexOf == -1) {
                    return view;
                }
                if (this.mUseFormStrings || iIndexOf == 0) {
                    textView.setText(RecurrenceOptionCreator.this.mEndDateLabel);
                    return view;
                }
                textView.setText(this.mEndDateString.substring(0, iIndexOf).trim());
                return view;
            }
            if (i != 2) {
                return null;
            }
            String quantityString = RecurrenceOptionCreator.this.mResources.getQuantityString(R.plurals.recurrence_end_count, RecurrenceOptionCreator.this.mModel.endCount);
            int iIndexOf2 = quantityString.indexOf(TimeModel.NUMBER_FORMAT);
            if (iIndexOf2 == -1) {
                return view;
            }
            if (this.mUseFormStrings || iIndexOf2 == 0) {
                textView.setText(RecurrenceOptionCreator.this.mEndCountLabel);
                RecurrenceOptionCreator.this.mPostEndCount.setVisibility(8);
                RecurrenceOptionCreator.this.mHidePostEndCount = true;
                return view;
            }
            RecurrenceOptionCreator.this.mPostEndCount.setText(quantityString.substring(iIndexOf2 + 2, quantityString.length()).trim());
            if (RecurrenceOptionCreator.this.mModel.end == 2) {
                RecurrenceOptionCreator.this.mPostEndCount.setVisibility(0);
            }
            if (quantityString.charAt(iIndexOf2 - 1) == ' ') {
                iIndexOf2--;
            }
            textView.setText(quantityString.substring(0, iIndexOf2).trim());
            return view;
        }

        @Override // android.widget.ArrayAdapter, android.widget.BaseAdapter, android.widget.SpinnerAdapter
        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = this.mInflater.inflate(this.mDropDownLayoutId, viewGroup, false);
            }
            ((TextView) view.findViewById(this.mTextResourceId)).setText(this.mStrings.get(i));
            return view;
        }
    }
}
