package com.appeaser.sublimepickerlibrary.timepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.text.style.TtsSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.appeaser.sublimepickerlibrary.R;
import com.appeaser.sublimepickerlibrary.common.DateTimePatternHelper;
import com.appeaser.sublimepickerlibrary.timepicker.RadialTimePickerView;
import com.appeaser.sublimepickerlibrary.utilities.AccessibilityUtils;
import com.appeaser.sublimepickerlibrary.utilities.SUtils;
import com.google.android.material.timepicker.TimeModel;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import org.apache.http.message.TokenParser;

/* loaded from: classes.dex */
public class SublimeTimePicker extends FrameLayout implements RadialTimePickerView.OnValueSelectedListener {
    private static final int AM = 0;
    private static final int AMPM_INDEX = 2;
    private static final int ENABLE_PICKER_INDEX = 3;
    private static final int HOURS_IN_HALF_DAY = 12;
    private static final int HOUR_INDEX = 0;
    private static final int MINUTE_INDEX = 1;
    private static final int PM = 1;
    private static final String TAG = SublimeTimePicker.class.getSimpleName();
    private boolean mAllowAutoAdvance;
    private int mAmKeyCode;
    private CheckedTextView mAmLabel;
    private View mAmPmLayout;
    private String mAmText;
    private final View.OnClickListener mClickListener;
    private Context mContext;
    private Locale mCurrentLocale;
    private String mDeletedKeyFormat;
    private String mDoublePlaceholderText;
    private final View.OnFocusChangeListener mFocusListener;
    private View mHeaderView;
    private TextView mHourView;
    private boolean mInKbMode;
    private int mInitialHourOfDay;
    private int mInitialMinute;
    private boolean mIs24HourView;
    private boolean mIsAmPmAtStart;
    private boolean mIsEnabled;
    private final View.OnKeyListener mKeyListener;
    private boolean mLastAnnouncedIsHour;
    private CharSequence mLastAnnouncedText;
    private Node mLegalTimesTree;
    private TextView mMinuteView;
    private OnTimeChangedListener mOnTimeChangedListener;
    private char mPlaceholderText;
    private int mPmKeyCode;
    private CheckedTextView mPmLabel;
    private String mPmText;
    private RadialTimePickerView mRadialTimePickerView;
    private String mSelectHours;
    private String mSelectMinutes;
    private TextView mSeparatorView;
    private Calendar mTempCalendar;
    private ArrayList<Integer> mTypedTimes;
    private TimePickerValidationCallback mValidationCallback;

    public interface OnTimeChangedListener {
        void onTimeChanged(SublimeTimePicker sublimeTimePicker, int i, int i2);
    }

    public interface TimePickerValidationCallback {
        void onTimePickerValidationChanged(boolean z);
    }

    private int getValFromKeyCode(int i) {
        switch (i) {
            case 7:
                return 0;
            case 8:
                return 1;
            case 9:
                return 2;
            case 10:
                return 3;
            case 11:
                return 4;
            case 12:
                return 5;
            case 13:
                return 6;
            case 14:
                return 7;
            case 15:
                return 8;
            case 16:
                return 9;
            default:
                return -1;
        }
    }

    @Override // android.view.View
    public int getBaseline() {
        return -1;
    }

    public SublimeTimePicker(Context context) {
        this(context, null);
    }

    public SublimeTimePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.spTimePickerStyle);
    }

    public SublimeTimePicker(Context context, AttributeSet attributeSet, int i) {
        super(SUtils.createThemeWrapper(context, R.attr.sublimePickerStyle, R.style.SublimePickerStyleLight, R.attr.spTimePickerStyle, R.style.SublimeTimePickerStyle), attributeSet, i);
        this.mIsEnabled = true;
        this.mTypedTimes = new ArrayList<>();
        this.mClickListener = new View.OnClickListener() { // from class: com.appeaser.sublimepickerlibrary.timepicker.SublimeTimePicker.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (view.getId() == R.id.am_label) {
                    SublimeTimePicker.this.setAmOrPm(0);
                } else if (view.getId() == R.id.pm_label) {
                    SublimeTimePicker.this.setAmOrPm(1);
                } else if (view.getId() == R.id.hours) {
                    SublimeTimePicker.this.setCurrentItemShowing(0, true, true);
                } else if (view.getId() != R.id.minutes) {
                    return;
                } else {
                    SublimeTimePicker.this.setCurrentItemShowing(1, true, true);
                }
                SUtils.vibrateForTimePicker(SublimeTimePicker.this);
            }
        };
        this.mKeyListener = new View.OnKeyListener() { // from class: com.appeaser.sublimepickerlibrary.timepicker.SublimeTimePicker.2
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i2, KeyEvent keyEvent) {
                return keyEvent.getAction() == 1 && SublimeTimePicker.this.processKeyUp(i2);
            }
        };
        this.mFocusListener = new View.OnFocusChangeListener() { // from class: com.appeaser.sublimepickerlibrary.timepicker.SublimeTimePicker.3
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean z) {
                if (!z && SublimeTimePicker.this.mInKbMode && SublimeTimePicker.this.isTypedTimeFullyLegal()) {
                    SublimeTimePicker.this.finishKbMode();
                    if (SublimeTimePicker.this.mOnTimeChangedListener != null) {
                        OnTimeChangedListener onTimeChangedListener = SublimeTimePicker.this.mOnTimeChangedListener;
                        SublimeTimePicker sublimeTimePicker = SublimeTimePicker.this;
                        onTimeChangedListener.onTimeChanged(sublimeTimePicker, sublimeTimePicker.mRadialTimePickerView.getCurrentHour(), SublimeTimePicker.this.mRadialTimePickerView.getCurrentMinute());
                    }
                }
            }
        };
        initializeLayout();
    }

    public SublimeTimePicker(Context context, AttributeSet attributeSet, int i, int i2) {
        super(SUtils.createThemeWrapper(context, R.attr.sublimePickerStyle, R.style.SublimePickerStyleLight, R.attr.spTimePickerStyle, R.style.SublimeTimePickerStyle), attributeSet, i, i2);
        this.mIsEnabled = true;
        this.mTypedTimes = new ArrayList<>();
        this.mClickListener = new View.OnClickListener() { // from class: com.appeaser.sublimepickerlibrary.timepicker.SublimeTimePicker.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (view.getId() == R.id.am_label) {
                    SublimeTimePicker.this.setAmOrPm(0);
                } else if (view.getId() == R.id.pm_label) {
                    SublimeTimePicker.this.setAmOrPm(1);
                } else if (view.getId() == R.id.hours) {
                    SublimeTimePicker.this.setCurrentItemShowing(0, true, true);
                } else if (view.getId() != R.id.minutes) {
                    return;
                } else {
                    SublimeTimePicker.this.setCurrentItemShowing(1, true, true);
                }
                SUtils.vibrateForTimePicker(SublimeTimePicker.this);
            }
        };
        this.mKeyListener = new View.OnKeyListener() { // from class: com.appeaser.sublimepickerlibrary.timepicker.SublimeTimePicker.2
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i22, KeyEvent keyEvent) {
                return keyEvent.getAction() == 1 && SublimeTimePicker.this.processKeyUp(i22);
            }
        };
        this.mFocusListener = new View.OnFocusChangeListener() { // from class: com.appeaser.sublimepickerlibrary.timepicker.SublimeTimePicker.3
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean z) {
                if (!z && SublimeTimePicker.this.mInKbMode && SublimeTimePicker.this.isTypedTimeFullyLegal()) {
                    SublimeTimePicker.this.finishKbMode();
                    if (SublimeTimePicker.this.mOnTimeChangedListener != null) {
                        OnTimeChangedListener onTimeChangedListener = SublimeTimePicker.this.mOnTimeChangedListener;
                        SublimeTimePicker sublimeTimePicker = SublimeTimePicker.this;
                        onTimeChangedListener.onTimeChanged(sublimeTimePicker, sublimeTimePicker.mRadialTimePickerView.getCurrentHour(), SublimeTimePicker.this.mRadialTimePickerView.getCurrentMinute());
                    }
                }
            }
        };
        initializeLayout();
    }

    private void initializeLayout() {
        this.mContext = getContext();
        setCurrentLocale(Locale.getDefault());
        TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(R.styleable.SublimeTimePicker);
        LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
        Resources resources = this.mContext.getResources();
        this.mSelectHours = resources.getString(R.string.select_hours);
        this.mSelectMinutes = resources.getString(R.string.select_minutes);
        String[] amPmStrings = DateFormatSymbols.getInstance(this.mCurrentLocale).getAmPmStrings();
        if (amPmStrings.length == 2 && !TextUtils.isEmpty(amPmStrings[0]) && !TextUtils.isEmpty(amPmStrings[1])) {
            this.mAmText = amPmStrings[0].length() > 2 ? amPmStrings[0].substring(0, 2) : amPmStrings[0];
            this.mPmText = amPmStrings[1].length() > 2 ? amPmStrings[1].substring(0, 2) : amPmStrings[1];
        } else {
            this.mAmText = "AM";
            this.mPmText = "PM";
        }
        View viewInflate = layoutInflater.inflate(R.layout.time_picker_layout, this);
        this.mHeaderView = viewInflate.findViewById(R.id.time_header);
        TextView textView = (TextView) viewInflate.findViewById(R.id.hours);
        this.mHourView = textView;
        textView.setOnClickListener(this.mClickListener);
        ViewCompat.setAccessibilityDelegate(this.mHourView, new ClickActionDelegate(this.mContext, R.string.select_hours));
        this.mSeparatorView = (TextView) viewInflate.findViewById(R.id.separator);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.minutes);
        this.mMinuteView = textView2;
        textView2.setOnClickListener(this.mClickListener);
        ViewCompat.setAccessibilityDelegate(this.mMinuteView, new ClickActionDelegate(this.mContext, R.string.select_minutes));
        TextView textView3 = this.mHourView;
        textView3.setMinWidth(computeStableWidth(textView3, 24));
        TextView textView4 = this.mMinuteView;
        textView4.setMinWidth(computeStableWidth(textView4, 60));
        View viewFindViewById = viewInflate.findViewById(R.id.ampm_layout);
        this.mAmPmLayout = viewFindViewById;
        CheckedTextView checkedTextView = (CheckedTextView) viewFindViewById.findViewById(R.id.am_label);
        this.mAmLabel = checkedTextView;
        checkedTextView.setText(obtainVerbatim(amPmStrings[0]));
        this.mAmLabel.setOnClickListener(this.mClickListener);
        CheckedTextView checkedTextView2 = (CheckedTextView) this.mAmPmLayout.findViewById(R.id.pm_label);
        this.mPmLabel = checkedTextView2;
        checkedTextView2.setText(obtainVerbatim(amPmStrings[1]));
        this.mPmLabel.setOnClickListener(this.mClickListener);
        ColorStateList colorStateList = typedArrayObtainStyledAttributes.getColorStateList(R.styleable.SublimeTimePicker_spHeaderTextColor);
        if (colorStateList != null) {
            this.mHourView.setTextColor(colorStateList);
            this.mSeparatorView.setTextColor(colorStateList);
            this.mMinuteView.setTextColor(colorStateList);
            this.mAmLabel.setTextColor(colorStateList);
            this.mPmLabel.setTextColor(colorStateList);
        }
        if (SUtils.isApi_22_OrHigher()) {
            if (typedArrayObtainStyledAttributes.hasValueOrEmpty(R.styleable.SublimeTimePicker_spHeaderBackground)) {
                SUtils.setViewBackground(this.mHeaderView, typedArrayObtainStyledAttributes.getDrawable(R.styleable.SublimeTimePicker_spHeaderBackground));
            }
        } else if (typedArrayObtainStyledAttributes.hasValue(R.styleable.SublimeTimePicker_spHeaderBackground)) {
            SUtils.setViewBackground(this.mHeaderView, typedArrayObtainStyledAttributes.getDrawable(R.styleable.SublimeTimePicker_spHeaderBackground));
        }
        typedArrayObtainStyledAttributes.recycle();
        this.mRadialTimePickerView = (RadialTimePickerView) viewInflate.findViewById(R.id.radial_picker);
        setupListeners();
        this.mAllowAutoAdvance = true;
        this.mDoublePlaceholderText = resources.getString(R.string.time_placeholder);
        this.mDeletedKeyFormat = resources.getString(R.string.deleted_key);
        this.mPlaceholderText = this.mDoublePlaceholderText.charAt(0);
        this.mPmKeyCode = -1;
        this.mAmKeyCode = -1;
        generateLegalTimesTree();
        Calendar calendar = Calendar.getInstance(this.mCurrentLocale);
        initialize(calendar.get(11), calendar.get(12), false, 0);
    }

    private CharSequence obtainVerbatim(String str) {
        return SUtils.isApi_21_OrHigher() ? new SpannableStringBuilder().append(str, new TtsSpan.VerbatimBuilder(str).build(), 0) : str;
    }

    private static class ClickActionDelegate extends AccessibilityDelegateCompat {
        private final AccessibilityNodeInfoCompat.AccessibilityActionCompat mClickAction;

        public ClickActionDelegate(Context context, int i) {
            this.mClickAction = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(16, context.getString(i));
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.addAction(this.mClickAction);
        }
    }

    private int computeStableWidth(TextView textView, int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            textView.setText(String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i3)));
            textView.measure(0, 0);
            int measuredWidth = textView.getMeasuredWidth();
            if (measuredWidth > i2) {
                i2 = measuredWidth;
            }
        }
        return i2;
    }

    private void initialize(int i, int i2, boolean z, int i3) {
        this.mInitialHourOfDay = i;
        this.mInitialMinute = i2;
        this.mIs24HourView = z;
        this.mInKbMode = false;
        updateUI(i3);
    }

    private void setupListeners() {
        this.mHeaderView.setOnKeyListener(this.mKeyListener);
        this.mHeaderView.setOnFocusChangeListener(this.mFocusListener);
        this.mHeaderView.setFocusable(true);
        this.mRadialTimePickerView.setOnValueSelectedListener(this);
    }

    private void updateUI(int i) {
        updateRadialPicker(i);
        updateHeaderAmPm();
        updateHeaderHour(this.mInitialHourOfDay, false);
        updateHeaderSeparator();
        updateHeaderMinute(this.mInitialMinute, false);
        invalidate();
    }

    private void updateRadialPicker(int i) {
        this.mRadialTimePickerView.initialize(this.mInitialHourOfDay, this.mInitialMinute, this.mIs24HourView);
        setCurrentItemShowing(i, false, true);
    }

    private void updateHeaderAmPm() {
        String bestDateTimePattern;
        if (this.mIs24HourView) {
            this.mAmPmLayout.setVisibility(8);
            return;
        }
        if (SUtils.isApi_18_OrHigher()) {
            bestDateTimePattern = DateFormat.getBestDateTimePattern(this.mCurrentLocale, "hm");
        } else {
            bestDateTimePattern = DateTimePatternHelper.getBestDateTimePattern(this.mCurrentLocale, 2);
        }
        setAmPmAtStart(bestDateTimePattern.startsWith("a"));
        updateAmPmLabelStates(this.mInitialHourOfDay < 12 ? 0 : 1);
    }

    private void setAmPmAtStart(boolean z) {
        if (this.mIsAmPmAtStart != z) {
            this.mIsAmPmAtStart = z;
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mAmPmLayout.getLayoutParams();
            int[] rules = layoutParams.getRules();
            if (rules[1] != 0 || rules[0] != 0) {
                if (z) {
                    layoutParams.addRule(1, 0);
                    layoutParams.addRule(0, this.mHourView.getId());
                } else {
                    layoutParams.addRule(0, 0);
                    layoutParams.addRule(1, this.mMinuteView.getId());
                }
            }
            this.mAmPmLayout.setLayoutParams(layoutParams);
        }
    }

    public void setCurrentHour(int i) {
        if (this.mInitialHourOfDay == i) {
            return;
        }
        this.mInitialHourOfDay = i;
        updateHeaderHour(i, true);
        updateHeaderAmPm();
        this.mRadialTimePickerView.setCurrentHour(i);
        this.mRadialTimePickerView.setAmOrPm(this.mInitialHourOfDay < 12 ? 0 : 1);
        invalidate();
        onTimeChanged();
    }

    public int getCurrentHour() {
        int currentHour = this.mRadialTimePickerView.getCurrentHour();
        if (this.mIs24HourView) {
            return currentHour;
        }
        if (this.mRadialTimePickerView.getAmOrPm() == 1) {
            return (currentHour % 12) + 12;
        }
        return currentHour % 12;
    }

    public void setCurrentMinute(int i) {
        if (this.mInitialMinute == i) {
            return;
        }
        this.mInitialMinute = i;
        updateHeaderMinute(i, true);
        this.mRadialTimePickerView.setCurrentMinute(i);
        invalidate();
        onTimeChanged();
    }

    public int getCurrentMinute() {
        return this.mRadialTimePickerView.getCurrentMinute();
    }

    public void setIs24HourView(boolean z) {
        if (z == this.mIs24HourView) {
            return;
        }
        this.mIs24HourView = z;
        generateLegalTimesTree();
        int currentHour = this.mRadialTimePickerView.getCurrentHour();
        this.mInitialHourOfDay = currentHour;
        updateHeaderHour(currentHour, false);
        updateHeaderAmPm();
        updateRadialPicker(this.mRadialTimePickerView.getCurrentItemShowing());
        invalidate();
    }

    public boolean is24HourView() {
        return this.mIs24HourView;
    }

    public void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener) {
        this.mOnTimeChangedListener = onTimeChangedListener;
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        this.mHourView.setEnabled(z);
        this.mMinuteView.setEnabled(z);
        this.mAmLabel.setEnabled(z);
        this.mPmLabel.setEnabled(z);
        this.mRadialTimePickerView.setEnabled(z);
        this.mIsEnabled = z;
    }

    @Override // android.view.View
    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        updateUI(this.mRadialTimePickerView.getCurrentItemShowing());
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), getCurrentHour(), getCurrentMinute(), is24HourView(), inKbMode(), getTypedTimes(), getCurrentItemShowing());
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        View.BaseSavedState baseSavedState = (View.BaseSavedState) parcelable;
        super.onRestoreInstanceState(baseSavedState.getSuperState());
        SavedState savedState = (SavedState) baseSavedState;
        setInKbMode(savedState.inKbMode());
        setTypedTimes(savedState.getTypesTimes());
        initialize(savedState.getHour(), savedState.getMinute(), savedState.is24HourMode(), savedState.getCurrentItemShowing());
        this.mRadialTimePickerView.invalidate();
        if (this.mInKbMode) {
            tryStartingKbMode(-1);
            this.mHourView.invalidate();
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
        int i = this.mIs24HourView ? 129 : 65;
        this.mTempCalendar.set(11, getCurrentHour());
        this.mTempCalendar.set(12, getCurrentMinute());
        accessibilityEvent.getText().add(DateUtils.formatDateTime(this.mContext, this.mTempCalendar.getTimeInMillis(), i));
    }

    private void setInKbMode(boolean z) {
        this.mInKbMode = z;
    }

    private boolean inKbMode() {
        return this.mInKbMode;
    }

    private void setTypedTimes(ArrayList<Integer> arrayList) {
        this.mTypedTimes = arrayList;
    }

    private ArrayList<Integer> getTypedTimes() {
        return this.mTypedTimes;
    }

    private int getCurrentItemShowing() {
        return this.mRadialTimePickerView.getCurrentItemShowing();
    }

    private void onTimeChanged() {
        sendAccessibilityEvent(4);
        OnTimeChangedListener onTimeChangedListener = this.mOnTimeChangedListener;
        if (onTimeChangedListener != null) {
            onTimeChangedListener.onTimeChanged(this, getCurrentHour(), getCurrentMinute());
        }
    }

    private static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.appeaser.sublimepickerlibrary.timepicker.SublimeTimePicker.SavedState.1
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
        private final int mCurrentItemShowing;
        private final int mHour;
        private final boolean mInKbMode;
        private final boolean mIs24HourMode;
        private final int mMinute;
        private final ArrayList<Integer> mTypedTimes;

        private SavedState(Parcelable parcelable, int i, int i2, boolean z, boolean z2, ArrayList<Integer> arrayList, int i3) {
            super(parcelable);
            this.mHour = i;
            this.mMinute = i2;
            this.mIs24HourMode = z;
            this.mInKbMode = z2;
            this.mTypedTimes = arrayList;
            this.mCurrentItemShowing = i3;
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.mHour = parcel.readInt();
            this.mMinute = parcel.readInt();
            this.mIs24HourMode = parcel.readInt() == 1;
            this.mInKbMode = parcel.readInt() == 1;
            this.mTypedTimes = parcel.readArrayList(getClass().getClassLoader());
            this.mCurrentItemShowing = parcel.readInt();
        }

        public int getHour() {
            return this.mHour;
        }

        public int getMinute() {
            return this.mMinute;
        }

        public boolean is24HourMode() {
            return this.mIs24HourMode;
        }

        public boolean inKbMode() {
            return this.mInKbMode;
        }

        public ArrayList<Integer> getTypesTimes() {
            return this.mTypedTimes;
        }

        public int getCurrentItemShowing() {
            return this.mCurrentItemShowing;
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mHour);
            parcel.writeInt(this.mMinute);
            parcel.writeInt(this.mIs24HourMode ? 1 : 0);
            parcel.writeInt(this.mInKbMode ? 1 : 0);
            parcel.writeList(this.mTypedTimes);
            parcel.writeInt(this.mCurrentItemShowing);
        }
    }

    private void updateAmPmLabelStates(int i) {
        boolean z = i == 0;
        this.mAmLabel.setActivated(z);
        this.mAmLabel.setChecked(z);
        boolean z2 = i == 1;
        this.mPmLabel.setActivated(z2);
        this.mPmLabel.setChecked(z2);
    }

    @Override // com.appeaser.sublimepickerlibrary.timepicker.RadialTimePickerView.OnValueSelectedListener
    public void onValueSelected(int i, int i2, boolean z) {
        if (i != 0) {
            if (i == 1) {
                updateHeaderMinute(i2, true);
            } else if (i == 2) {
                updateAmPmLabelStates(i2);
            } else if (i == 3) {
                if (!isTypedTimeFullyLegal()) {
                    this.mTypedTimes.clear();
                }
                finishKbMode();
            }
        } else if (this.mAllowAutoAdvance && z) {
            updateHeaderHour(i2, false);
            setCurrentItemShowing(1, true, false);
            AccessibilityUtils.makeAnnouncement(this, i2 + ". " + this.mSelectMinutes);
        } else {
            updateHeaderHour(i2, true);
        }
        OnTimeChangedListener onTimeChangedListener = this.mOnTimeChangedListener;
        if (onTimeChangedListener != null) {
            onTimeChangedListener.onTimeChanged(this, getCurrentHour(), getCurrentMinute());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:53:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void updateHeaderHour(int r10, boolean r11) {
        /*
            r9 = this;
            boolean r0 = com.appeaser.sublimepickerlibrary.utilities.SUtils.isApi_18_OrHigher()
            if (r0 == 0) goto L16
            java.util.Locale r0 = r9.mCurrentLocale
            boolean r1 = r9.mIs24HourView
            if (r1 == 0) goto Lf
            java.lang.String r1 = "Hm"
            goto L11
        Lf:
            java.lang.String r1 = "hm"
        L11:
            java.lang.String r0 = android.text.format.DateFormat.getBestDateTimePattern(r0, r1)
            goto L23
        L16:
            java.util.Locale r0 = r9.mCurrentLocale
            boolean r1 = r9.mIs24HourView
            if (r1 == 0) goto L1e
            r1 = 3
            goto L1f
        L1e:
            r1 = 2
        L1f:
            java.lang.String r0 = com.appeaser.sublimepickerlibrary.common.DateTimePatternHelper.getBestDateTimePattern(r0, r1)
        L23:
            int r1 = r0.length()
            r2 = 0
            r3 = 0
        L29:
            r4 = 107(0x6b, float:1.5E-43)
            r5 = 75
            r6 = 1
            if (r3 >= r1) goto L51
            char r7 = r0.charAt(r3)
            r8 = 72
            if (r7 == r8) goto L44
            r8 = 104(0x68, float:1.46E-43)
            if (r7 == r8) goto L44
            if (r7 == r5) goto L44
            if (r7 != r4) goto L41
            goto L44
        L41:
            int r3 = r3 + 1
            goto L29
        L44:
            int r3 = r3 + r6
            if (r3 >= r1) goto L4f
            char r0 = r0.charAt(r3)
            if (r7 != r0) goto L4f
            r0 = 1
            goto L53
        L4f:
            r0 = 0
            goto L53
        L51:
            r0 = 0
            r7 = 0
        L53:
            if (r0 == 0) goto L58
            java.lang.String r0 = "%02d"
            goto L5a
        L58:
            java.lang.String r0 = "%d"
        L5a:
            boolean r1 = r9.mIs24HourView
            if (r1 == 0) goto L65
            if (r7 != r4) goto L6e
            if (r10 != 0) goto L6e
            r10 = 24
            goto L6e
        L65:
            if (r7 != r5) goto L69
            r1 = 1
            goto L6a
        L69:
            r1 = 0
        L6a:
            int r10 = modulo12(r10, r1)
        L6e:
            java.lang.Object[] r1 = new java.lang.Object[r6]
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            r1[r2] = r10
            java.lang.String r10 = java.lang.String.format(r0, r1)
            android.widget.TextView r0 = r9.mHourView
            r0.setText(r10)
            if (r11 == 0) goto L84
            r9.tryAnnounceForAccessibility(r10, r6)
        L84:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appeaser.sublimepickerlibrary.timepicker.SublimeTimePicker.updateHeaderHour(int, boolean):void");
    }

    private void tryAnnounceForAccessibility(CharSequence charSequence, boolean z) {
        if (this.mLastAnnouncedIsHour == z && charSequence.equals(this.mLastAnnouncedText)) {
            return;
        }
        AccessibilityUtils.makeAnnouncement(this, charSequence);
        this.mLastAnnouncedText = charSequence;
        this.mLastAnnouncedIsHour = z;
    }

    private static int modulo12(int i, boolean z) {
        int i2 = i % 12;
        if (i2 != 0 || z) {
            return i2;
        }
        return 12;
    }

    private void updateHeaderSeparator() {
        String bestDateTimePattern;
        if (SUtils.isApi_18_OrHigher()) {
            bestDateTimePattern = DateFormat.getBestDateTimePattern(this.mCurrentLocale, this.mIs24HourView ? com.ibm.icu.text.DateFormat.HOUR24_MINUTE : "hm");
        } else {
            bestDateTimePattern = DateTimePatternHelper.getBestDateTimePattern(this.mCurrentLocale, this.mIs24HourView ? 3 : 2);
        }
        int iLastIndexOfAny = lastIndexOfAny(bestDateTimePattern, new char[]{'H', 'h', 'K', 'k'});
        this.mSeparatorView.setText(iLastIndexOfAny == -1 ? ":" : Character.toString(bestDateTimePattern.charAt(iLastIndexOfAny + 1)));
    }

    private static int lastIndexOfAny(String str, char[] cArr) {
        if (cArr.length <= 0) {
            return -1;
        }
        for (int length = str.length() - 1; length >= 0; length--) {
            char cCharAt = str.charAt(length);
            for (char c : cArr) {
                if (cCharAt == c) {
                    return length;
                }
            }
        }
        return -1;
    }

    private void updateHeaderMinute(int i, boolean z) {
        if (i == 60) {
            i = 0;
        }
        String str = String.format(this.mCurrentLocale, TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i));
        this.mMinuteView.setText(str);
        if (z) {
            tryAnnounceForAccessibility(str, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCurrentItemShowing(int i, boolean z, boolean z2) {
        this.mRadialTimePickerView.setCurrentItemShowing(i, z);
        if (i == 0) {
            if (z2) {
                AccessibilityUtils.makeAnnouncement(this, this.mSelectHours);
            }
        } else if (z2) {
            AccessibilityUtils.makeAnnouncement(this, this.mSelectMinutes);
        }
        this.mHourView.setActivated(i == 0);
        this.mMinuteView.setActivated(i == 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAmOrPm(int i) {
        updateAmPmLabelStates(i);
        this.mRadialTimePickerView.setAmOrPm(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean processKeyUp(int i) {
        String str;
        if (i == 67) {
            if (this.mInKbMode && !this.mTypedTimes.isEmpty()) {
                int iDeleteLastTypedKey = deleteLastTypedKey();
                if (iDeleteLastTypedKey == getAmOrPmKeyCode(0)) {
                    str = this.mAmText;
                } else {
                    str = iDeleteLastTypedKey == getAmOrPmKeyCode(1) ? this.mPmText : String.format(TimeModel.NUMBER_FORMAT, Integer.valueOf(getValFromKeyCode(iDeleteLastTypedKey)));
                }
                AccessibilityUtils.makeAnnouncement(this, String.format(this.mDeletedKeyFormat, str));
                updateDisplay(true);
            }
        } else if (i == 7 || i == 8 || i == 9 || i == 10 || i == 11 || i == 12 || i == 13 || i == 14 || i == 15 || i == 16 || (!this.mIs24HourView && (i == getAmOrPmKeyCode(0) || i == getAmOrPmKeyCode(1)))) {
            if (!this.mInKbMode) {
                if (this.mRadialTimePickerView == null) {
                    Log.e(TAG, "Unable to initiate keyboard mode, TimePicker was null.");
                    return true;
                }
                this.mTypedTimes.clear();
                tryStartingKbMode(i);
                return true;
            }
            if (addKeyIfLegal(i)) {
                updateDisplay(false);
            }
            return true;
        }
        return false;
    }

    private void tryStartingKbMode(int i) {
        if (i == -1 || addKeyIfLegal(i)) {
            this.mInKbMode = true;
            onValidationChanged(false);
            updateDisplay(false);
            this.mRadialTimePickerView.setInputEnabled(false);
        }
    }

    private boolean addKeyIfLegal(int i) {
        if ((this.mIs24HourView && this.mTypedTimes.size() == 4) || (!this.mIs24HourView && isTypedTimeFullyLegal())) {
            return false;
        }
        this.mTypedTimes.add(Integer.valueOf(i));
        if (!isTypedTimeLegalSoFar()) {
            deleteLastTypedKey();
            return false;
        }
        AccessibilityUtils.makeAnnouncement(this, String.format(TimeModel.NUMBER_FORMAT, Integer.valueOf(getValFromKeyCode(i))));
        if (isTypedTimeFullyLegal()) {
            if (!this.mIs24HourView && this.mTypedTimes.size() <= 3) {
                ArrayList<Integer> arrayList = this.mTypedTimes;
                arrayList.add(arrayList.size() - 1, 7);
                ArrayList<Integer> arrayList2 = this.mTypedTimes;
                arrayList2.add(arrayList2.size() - 1, 7);
            }
            onValidationChanged(true);
        }
        return true;
    }

    private boolean isTypedTimeLegalSoFar() {
        Node nodeCanReach = this.mLegalTimesTree;
        Iterator<Integer> it2 = this.mTypedTimes.iterator();
        while (it2.hasNext()) {
            nodeCanReach = nodeCanReach.canReach(it2.next().intValue());
            if (nodeCanReach == null) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isTypedTimeFullyLegal() {
        if (!this.mIs24HourView) {
            return this.mTypedTimes.contains(Integer.valueOf(getAmOrPmKeyCode(0))) || this.mTypedTimes.contains(Integer.valueOf(getAmOrPmKeyCode(1)));
        }
        int[] enteredTime = getEnteredTime(null);
        return enteredTime[0] >= 0 && enteredTime[1] >= 0 && enteredTime[1] < 60;
    }

    private int deleteLastTypedKey() {
        int iIntValue = this.mTypedTimes.remove(r0.size() - 1).intValue();
        if (!isTypedTimeFullyLegal()) {
            onValidationChanged(false);
        }
        return iIntValue;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishKbMode() {
        this.mInKbMode = false;
        if (!this.mTypedTimes.isEmpty()) {
            int[] enteredTime = getEnteredTime(null);
            this.mRadialTimePickerView.setCurrentHour(enteredTime[0]);
            this.mRadialTimePickerView.setCurrentMinute(enteredTime[1]);
            if (!this.mIs24HourView) {
                this.mRadialTimePickerView.setAmOrPm(enteredTime[2]);
            }
            this.mTypedTimes.clear();
        }
        updateDisplay(false);
        this.mRadialTimePickerView.setInputEnabled(true);
    }

    private void updateDisplay(boolean z) {
        if (!z && this.mTypedTimes.isEmpty()) {
            int currentHour = this.mRadialTimePickerView.getCurrentHour();
            int currentMinute = this.mRadialTimePickerView.getCurrentMinute();
            updateHeaderHour(currentHour, false);
            updateHeaderMinute(currentMinute, false);
            if (!this.mIs24HourView) {
                updateAmPmLabelStates(currentHour >= 12 ? 1 : 0);
            }
            setCurrentItemShowing(this.mRadialTimePickerView.getCurrentItemShowing(), true, true);
            onValidationChanged(true);
            return;
        }
        boolean[] zArr = {false, false};
        int[] enteredTime = getEnteredTime(zArr);
        boolean z2 = zArr[0];
        String str = TimeModel.ZERO_LEADING_NUMBER_FORMAT;
        String str2 = z2 ? TimeModel.ZERO_LEADING_NUMBER_FORMAT : "%2d";
        if (!zArr[1]) {
            str = "%2d";
        }
        String strReplace = enteredTime[0] == -1 ? this.mDoublePlaceholderText : String.format(str2, Integer.valueOf(enteredTime[0])).replace(TokenParser.SP, this.mPlaceholderText);
        String strReplace2 = enteredTime[1] == -1 ? this.mDoublePlaceholderText : String.format(str, Integer.valueOf(enteredTime[1])).replace(TokenParser.SP, this.mPlaceholderText);
        this.mHourView.setText(strReplace);
        this.mHourView.setActivated(false);
        this.mMinuteView.setText(strReplace2);
        this.mMinuteView.setActivated(false);
        if (this.mIs24HourView) {
            return;
        }
        updateAmPmLabelStates(enteredTime[2]);
    }

    public void setValidationCallback(TimePickerValidationCallback timePickerValidationCallback) {
        this.mValidationCallback = timePickerValidationCallback;
    }

    protected void onValidationChanged(boolean z) {
        TimePickerValidationCallback timePickerValidationCallback = this.mValidationCallback;
        if (timePickerValidationCallback != null) {
            timePickerValidationCallback.onTimePickerValidationChanged(z);
        }
    }

    public void setCurrentLocale(Locale locale) {
        if (locale.equals(this.mCurrentLocale)) {
            return;
        }
        this.mCurrentLocale = locale;
        this.mTempCalendar = Calendar.getInstance(locale);
    }

    private int[] getEnteredTime(boolean[] zArr) {
        int i;
        int i2;
        int i3 = -1;
        if (this.mIs24HourView || !isTypedTimeFullyLegal()) {
            i = -1;
            i2 = 1;
        } else {
            ArrayList<Integer> arrayList = this.mTypedTimes;
            int iIntValue = arrayList.get(arrayList.size() - 1).intValue();
            if (iIntValue == getAmOrPmKeyCode(0)) {
                i = 0;
            } else {
                i = iIntValue == getAmOrPmKeyCode(1) ? 1 : -1;
            }
            i2 = 2;
        }
        int i4 = -1;
        for (int i5 = i2; i5 <= this.mTypedTimes.size(); i5++) {
            ArrayList<Integer> arrayList2 = this.mTypedTimes;
            int valFromKeyCode = getValFromKeyCode(arrayList2.get(arrayList2.size() - i5).intValue());
            if (i5 == i2) {
                i4 = valFromKeyCode;
            } else if (i5 == i2 + 1) {
                i4 += valFromKeyCode * 10;
                if (zArr != null && valFromKeyCode == 0) {
                    zArr[1] = true;
                }
            } else if (i5 == i2 + 2) {
                i3 = valFromKeyCode;
            } else if (i5 == i2 + 3) {
                i3 += valFromKeyCode * 10;
                if (zArr != null && valFromKeyCode == 0) {
                    zArr[0] = true;
                }
            }
        }
        return new int[]{i3, i4, i};
    }

    private int getAmOrPmKeyCode(int i) {
        if (this.mAmKeyCode == -1 || this.mPmKeyCode == -1) {
            KeyCharacterMap keyCharacterMapLoad = KeyCharacterMap.load(-1);
            String lowerCase = this.mAmText.toLowerCase(this.mCurrentLocale);
            String lowerCase2 = this.mPmText.toLowerCase(this.mCurrentLocale);
            int iMin = Math.min(lowerCase.length(), lowerCase2.length());
            int i2 = 0;
            while (true) {
                if (i2 >= iMin) {
                    break;
                }
                char cCharAt = lowerCase.charAt(i2);
                char cCharAt2 = lowerCase2.charAt(i2);
                if (cCharAt != cCharAt2) {
                    KeyEvent[] events = keyCharacterMapLoad.getEvents(new char[]{cCharAt, cCharAt2});
                    if (events != null && events.length == 4) {
                        this.mAmKeyCode = events[0].getKeyCode();
                        this.mPmKeyCode = events[2].getKeyCode();
                    } else {
                        Log.e(TAG, "Unable to find keycodes for AM and PM.");
                    }
                } else {
                    i2++;
                }
            }
        }
        if (i == 0) {
            return this.mAmKeyCode;
        }
        if (i == 1) {
            return this.mPmKeyCode;
        }
        return -1;
    }

    private void generateLegalTimesTree() {
        this.mLegalTimesTree = new Node(new int[0]);
        if (this.mIs24HourView) {
            Node node = new Node(7, 8, 9, 10, 11, 12);
            Node node2 = new Node(7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
            node.addChild(node2);
            Node node3 = new Node(7, 8);
            this.mLegalTimesTree.addChild(node3);
            Node node4 = new Node(7, 8, 9, 10, 11, 12);
            node3.addChild(node4);
            node4.addChild(node);
            node4.addChild(new Node(13, 14, 15, 16));
            Node node5 = new Node(13, 14, 15, 16);
            node3.addChild(node5);
            node5.addChild(node);
            Node node6 = new Node(9);
            this.mLegalTimesTree.addChild(node6);
            Node node7 = new Node(7, 8, 9, 10);
            node6.addChild(node7);
            node7.addChild(node);
            Node node8 = new Node(11, 12);
            node6.addChild(node8);
            node8.addChild(node2);
            Node node9 = new Node(10, 11, 12, 13, 14, 15, 16);
            this.mLegalTimesTree.addChild(node9);
            node9.addChild(node);
            return;
        }
        Node node10 = new Node(getAmOrPmKeyCode(0), getAmOrPmKeyCode(1));
        Node node11 = new Node(8);
        this.mLegalTimesTree.addChild(node11);
        node11.addChild(node10);
        Node node12 = new Node(7, 8, 9);
        node11.addChild(node12);
        node12.addChild(node10);
        Node node13 = new Node(7, 8, 9, 10, 11, 12);
        node12.addChild(node13);
        node13.addChild(node10);
        Node node14 = new Node(7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        node13.addChild(node14);
        node14.addChild(node10);
        Node node15 = new Node(13, 14, 15, 16);
        node12.addChild(node15);
        node15.addChild(node10);
        Node node16 = new Node(10, 11, 12);
        node11.addChild(node16);
        Node node17 = new Node(7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        node16.addChild(node17);
        node17.addChild(node10);
        Node node18 = new Node(9, 10, 11, 12, 13, 14, 15, 16);
        this.mLegalTimesTree.addChild(node18);
        node18.addChild(node10);
        Node node19 = new Node(7, 8, 9, 10, 11, 12);
        node18.addChild(node19);
        Node node20 = new Node(7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        node19.addChild(node20);
        node20.addChild(node10);
    }

    private class Node {
        private final ArrayList<Node> mChildren = new ArrayList<>();
        private final int[] mLegalKeys;

        public Node(int... iArr) {
            this.mLegalKeys = iArr;
        }

        public void addChild(Node node) {
            this.mChildren.add(node);
        }

        public boolean containsKey(int i) {
            for (int i2 : this.mLegalKeys) {
                if (i2 == i) {
                    return true;
                }
            }
            return false;
        }

        public Node canReach(int i) {
            ArrayList<Node> arrayList = this.mChildren;
            if (arrayList == null) {
                return null;
            }
            Iterator<Node> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                Node next = it2.next();
                if (next.containsKey(i)) {
                    return next;
                }
            }
            return null;
        }
    }
}
