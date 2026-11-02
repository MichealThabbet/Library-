package com.appeaser.sublimepickerlibrary.recurrencepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.appeaser.sublimepickerlibrary.R;
import com.appeaser.sublimepickerlibrary.recurrencepicker.RecurrenceOptionCreator;
import com.appeaser.sublimepickerlibrary.utilities.SUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimeZone;
import net.fortuna.ical4j.model.Recur;

/* loaded from: classes.dex */
public class SublimeRecurrencePicker extends FrameLayout implements View.OnClickListener {
    LinearLayout llRecurrenceOptionsMenu;
    OnRepeatOptionSetListener mCallback;
    Drawable mCheckmarkDrawable;
    RecurrenceOption mCurrentRecurrenceOption;
    CurrentView mCurrentView;
    long mCurrentlyChosenTime;
    RecurrenceOptionCreator.OnRecurrenceSetListener mOnRecurrenceSetListener;
    int mPressedStateColor;
    RecurrenceOptionCreator mRecurrenceOptionCreator;
    String mRecurrenceRule;
    ArrayList<TextView> mRepeatOptionTextViews;
    int mSelectedOptionDrawablePadding;
    int mSelectedStateTextColor;
    int mUnselectedStateTextColor;

    private enum CurrentView {
        RECURRENCE_OPTIONS_MENU,
        RECURRENCE_CREATOR
    }

    public interface OnRepeatOptionSetListener {
        void onDone();

        void onRepeatOptionSet(RecurrenceOption recurrenceOption, String str);
    }

    public enum RecurrenceOption {
        DOES_NOT_REPEAT("DOES NOT REPEAT"),
        DAILY(Recur.DAILY),
        WEEKLY(Recur.WEEKLY),
        MONTHLY(Recur.MONTHLY),
        YEARLY(Recur.YEARLY),
        CUSTOM("CUSTOM...");

        private final String optionName;

        RecurrenceOption(String str) {
            this.optionName = str;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.optionName;
        }
    }

    public SublimeRecurrencePicker(Context context) {
        this(context, null);
    }

    public SublimeRecurrencePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.spRecurrencePickerStyle);
    }

    public SublimeRecurrencePicker(Context context, AttributeSet attributeSet, int i) {
        super(SUtils.createThemeWrapper(context, R.attr.sublimePickerStyle, R.style.SublimePickerStyleLight, R.attr.spRecurrencePickerStyle, R.style.SublimeRecurrencePickerStyle), attributeSet, i);
        this.mCurrentView = CurrentView.RECURRENCE_OPTIONS_MENU;
        this.mOnRecurrenceSetListener = new RecurrenceOptionCreator.OnRecurrenceSetListener() { // from class: com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker.2
            @Override // com.appeaser.sublimepickerlibrary.recurrencepicker.RecurrenceOptionCreator.OnRecurrenceSetListener
            public void onRecurrenceSet(String str) {
                SublimeRecurrencePicker.this.mRecurrenceRule = str;
                SublimeRecurrencePicker.this.mCurrentRecurrenceOption = RecurrenceOption.CUSTOM;
                SublimeRecurrencePicker.this.mCurrentView = CurrentView.RECURRENCE_OPTIONS_MENU;
                if (SublimeRecurrencePicker.this.mCallback != null) {
                    SublimeRecurrencePicker.this.mCallback.onRepeatOptionSet(RecurrenceOption.CUSTOM, str);
                }
            }

            @Override // com.appeaser.sublimepickerlibrary.recurrencepicker.RecurrenceOptionCreator.OnRecurrenceSetListener
            public void onCancelled() {
                SublimeRecurrencePicker.this.mCurrentView = CurrentView.RECURRENCE_OPTIONS_MENU;
                SublimeRecurrencePicker.this.updateView();
            }
        };
        initializeLayout();
    }

    public SublimeRecurrencePicker(Context context, AttributeSet attributeSet, int i, int i2) {
        super(SUtils.createThemeWrapper(context, R.attr.sublimePickerStyle, R.style.SublimePickerStyleLight, R.attr.spRecurrencePickerStyle, R.style.SublimeRecurrencePickerStyle), attributeSet, i, i2);
        this.mCurrentView = CurrentView.RECURRENCE_OPTIONS_MENU;
        this.mOnRecurrenceSetListener = new RecurrenceOptionCreator.OnRecurrenceSetListener() { // from class: com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker.2
            @Override // com.appeaser.sublimepickerlibrary.recurrencepicker.RecurrenceOptionCreator.OnRecurrenceSetListener
            public void onRecurrenceSet(String str) {
                SublimeRecurrencePicker.this.mRecurrenceRule = str;
                SublimeRecurrencePicker.this.mCurrentRecurrenceOption = RecurrenceOption.CUSTOM;
                SublimeRecurrencePicker.this.mCurrentView = CurrentView.RECURRENCE_OPTIONS_MENU;
                if (SublimeRecurrencePicker.this.mCallback != null) {
                    SublimeRecurrencePicker.this.mCallback.onRepeatOptionSet(RecurrenceOption.CUSTOM, str);
                }
            }

            @Override // com.appeaser.sublimepickerlibrary.recurrencepicker.RecurrenceOptionCreator.OnRecurrenceSetListener
            public void onCancelled() {
                SublimeRecurrencePicker.this.mCurrentView = CurrentView.RECURRENCE_OPTIONS_MENU;
                SublimeRecurrencePicker.this.updateView();
            }
        };
        initializeLayout();
    }

    void initializeLayout() {
        Context context = getContext();
        LayoutInflater.from(context).inflate(R.layout.sublime_recurrence_picker, this);
        this.llRecurrenceOptionsMenu = (LinearLayout) findViewById(R.id.llRecurrenceOptionsMenu);
        this.mRecurrenceOptionCreator = (RecurrenceOptionCreator) findViewById(R.id.recurrenceOptionCreator);
        TextView textView = (TextView) findViewById(R.id.tvHeading);
        this.mSelectedOptionDrawablePadding = context.getResources().getDimensionPixelSize(R.dimen.selected_recurrence_option_drawable_padding);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(R.styleable.SublimeRecurrencePicker);
        try {
            int color = typedArrayObtainStyledAttributes.getColor(R.styleable.SublimeRecurrencePicker_spHeaderBackground, SUtils.COLOR_ACCENT);
            int color2 = typedArrayObtainStyledAttributes.getColor(R.styleable.SublimeRecurrencePicker_spPickerBackground, SUtils.COLOR_BACKGROUND);
            if (color2 != 0) {
                SUtils.setViewBackground(this, color2, 15);
            }
            SUtils.setViewBackground(textView, color, 3);
            this.mSelectedStateTextColor = typedArrayObtainStyledAttributes.getColor(R.styleable.SublimeRecurrencePicker_spSelectedOptionTextColor, SUtils.COLOR_ACCENT);
            this.mUnselectedStateTextColor = typedArrayObtainStyledAttributes.getColor(R.styleable.SublimeRecurrencePicker_spUnselectedOptionsTextColor, SUtils.COLOR_TEXT_PRIMARY);
            this.mPressedStateColor = typedArrayObtainStyledAttributes.getColor(R.styleable.SublimeRecurrencePicker_spPressedOptionBgColor, SUtils.COLOR_CONTROL_HIGHLIGHT);
            Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.SublimeRecurrencePicker_spSelectedOptionDrawable);
            this.mCheckmarkDrawable = drawable;
            if (drawable == null) {
                this.mCheckmarkDrawable = context.getResources().getDrawable(R.drawable.checkmark_medium_ff);
            }
            if (this.mCheckmarkDrawable != null) {
                this.mCheckmarkDrawable.setColorFilter(this.mSelectedStateTextColor, PorterDuff.Mode.MULTIPLY);
            }
            typedArrayObtainStyledAttributes.recycle();
            ArrayList<TextView> arrayList = new ArrayList<>();
            this.mRepeatOptionTextViews = arrayList;
            arrayList.add((TextView) findViewById(R.id.tvChosenCustomOption));
            this.mRepeatOptionTextViews.add((TextView) findViewById(R.id.tvDoesNotRepeat));
            this.mRepeatOptionTextViews.add((TextView) findViewById(R.id.tvDaily));
            this.mRepeatOptionTextViews.add((TextView) findViewById(R.id.tvWeekly));
            this.mRepeatOptionTextViews.add((TextView) findViewById(R.id.tvMonthly));
            this.mRepeatOptionTextViews.add((TextView) findViewById(R.id.tvYearly));
            this.mRepeatOptionTextViews.add((TextView) findViewById(R.id.tvCustom));
            Iterator<TextView> it2 = this.mRepeatOptionTextViews.iterator();
            while (it2.hasNext()) {
                SUtils.setViewBackground(it2.next(), createOptionBg(this.mPressedStateColor));
            }
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }

    public void initializeData(OnRepeatOptionSetListener onRepeatOptionSetListener, RecurrenceOption recurrenceOption, String str, long j) {
        this.mCallback = onRepeatOptionSetListener;
        this.mRecurrenceRule = str;
        this.mCurrentlyChosenTime = j;
        this.mCurrentRecurrenceOption = recurrenceOption;
        this.mRecurrenceOptionCreator.initializeData(j, null, str, this.mOnRecurrenceSetListener);
    }

    public void updateView() {
        if (this.mCurrentView == CurrentView.RECURRENCE_OPTIONS_MENU) {
            this.mRecurrenceOptionCreator.setVisibility(8);
            this.llRecurrenceOptionsMenu.setVisibility(0);
            updateFlowLayout(this.mCurrentRecurrenceOption);
            final ScrollView scrollView = (ScrollView) this.llRecurrenceOptionsMenu.findViewById(R.id.svRecurrenceOptionsMenu);
            this.llRecurrenceOptionsMenu.post(new Runnable() { // from class: com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker.1
                @Override // java.lang.Runnable
                public void run() {
                    if (scrollView.getScrollY() != 0) {
                        scrollView.fullScroll(33);
                    }
                }
            });
            return;
        }
        if (this.mCurrentView == CurrentView.RECURRENCE_CREATOR) {
            this.llRecurrenceOptionsMenu.setVisibility(8);
            this.mRecurrenceOptionCreator.setVisibility(0);
        }
    }

    /* renamed from: com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$appeaser$sublimepickerlibrary$recurrencepicker$SublimeRecurrencePicker$RecurrenceOption;

        static {
            int[] iArr = new int[RecurrenceOption.values().length];
            $SwitchMap$com$appeaser$sublimepickerlibrary$recurrencepicker$SublimeRecurrencePicker$RecurrenceOption = iArr;
            try {
                iArr[RecurrenceOption.DOES_NOT_REPEAT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$appeaser$sublimepickerlibrary$recurrencepicker$SublimeRecurrencePicker$RecurrenceOption[RecurrenceOption.DAILY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$appeaser$sublimepickerlibrary$recurrencepicker$SublimeRecurrencePicker$RecurrenceOption[RecurrenceOption.WEEKLY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$appeaser$sublimepickerlibrary$recurrencepicker$SublimeRecurrencePicker$RecurrenceOption[RecurrenceOption.MONTHLY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$appeaser$sublimepickerlibrary$recurrencepicker$SublimeRecurrencePicker$RecurrenceOption[RecurrenceOption.YEARLY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$appeaser$sublimepickerlibrary$recurrencepicker$SublimeRecurrencePicker$RecurrenceOption[RecurrenceOption.CUSTOM.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    void updateFlowLayout(RecurrenceOption recurrenceOption) {
        int i;
        switch (AnonymousClass3.$SwitchMap$com$appeaser$sublimepickerlibrary$recurrencepicker$SublimeRecurrencePicker$RecurrenceOption[recurrenceOption.ordinal()]) {
            case 1:
                i = R.id.tvDoesNotRepeat;
                break;
            case 2:
                i = R.id.tvDaily;
                break;
            case 3:
                i = R.id.tvWeekly;
                break;
            case 4:
                i = R.id.tvMonthly;
                break;
            case 5:
                i = R.id.tvYearly;
                break;
            case 6:
                i = R.id.tvChosenCustomOption;
                break;
            default:
                i = R.id.tvDoesNotRepeat;
                break;
        }
        Iterator<TextView> it2 = this.mRepeatOptionTextViews.iterator();
        while (it2.hasNext()) {
            TextView next = it2.next();
            next.setOnClickListener(this);
            if (next.getId() == R.id.tvChosenCustomOption) {
                if (!TextUtils.isEmpty(this.mRecurrenceRule)) {
                    EventRecurrence eventRecurrence = new EventRecurrence();
                    eventRecurrence.parse(this.mRecurrenceRule);
                    Time time = new Time(TimeZone.getDefault().getID());
                    time.set(this.mCurrentlyChosenTime);
                    eventRecurrence.setStartDate(time);
                    next.setVisibility(0);
                    next.setText(EventRecurrenceFormatter.getRepeatString(getContext(), getContext().getResources(), eventRecurrence, true));
                } else {
                    next.setVisibility(8);
                }
            }
            if (next.getId() == i) {
                next.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, this.mCheckmarkDrawable, (Drawable) null);
                next.setCompoundDrawablePadding(this.mSelectedOptionDrawablePadding);
                next.setTextColor(this.mSelectedStateTextColor);
            } else {
                next.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                next.setTextColor(this.mUnselectedStateTextColor);
            }
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.tvChosenCustomOption) {
            this.mCurrentRecurrenceOption = RecurrenceOption.CUSTOM;
            OnRepeatOptionSetListener onRepeatOptionSetListener = this.mCallback;
            if (onRepeatOptionSetListener != null) {
                onRepeatOptionSetListener.onRepeatOptionSet(RecurrenceOption.CUSTOM, this.mRecurrenceRule);
                return;
            }
            return;
        }
        if (view.getId() == R.id.tvDoesNotRepeat) {
            this.mCurrentRecurrenceOption = RecurrenceOption.DOES_NOT_REPEAT;
        } else if (view.getId() == R.id.tvDaily) {
            this.mCurrentRecurrenceOption = RecurrenceOption.DAILY;
        } else if (view.getId() == R.id.tvWeekly) {
            this.mCurrentRecurrenceOption = RecurrenceOption.WEEKLY;
        } else if (view.getId() == R.id.tvMonthly) {
            this.mCurrentRecurrenceOption = RecurrenceOption.MONTHLY;
        } else if (view.getId() == R.id.tvYearly) {
            this.mCurrentRecurrenceOption = RecurrenceOption.YEARLY;
        } else {
            if (view.getId() == R.id.tvCustom) {
                this.mCurrentView = CurrentView.RECURRENCE_CREATOR;
                updateView();
                return;
            }
            this.mCurrentRecurrenceOption = RecurrenceOption.DOES_NOT_REPEAT;
        }
        OnRepeatOptionSetListener onRepeatOptionSetListener2 = this.mCallback;
        if (onRepeatOptionSetListener2 != null) {
            onRepeatOptionSetListener2.onRepeatOptionSet(this.mCurrentRecurrenceOption, null);
        }
    }

    Drawable createOptionBg(int i) {
        if (SUtils.isApi_21_OrHigher()) {
            return createRippleDrawableForOption(i);
        }
        return createStateListDrawableForOption(i);
    }

    private Drawable createStateListDrawableForOption(int i) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(i));
        stateListDrawable.addState(new int[0], new ColorDrawable(0));
        return stateListDrawable;
    }

    private Drawable createRippleDrawableForOption(int i) {
        return new RippleDrawable(ColorStateList.valueOf(i), null, new ColorDrawable(ViewCompat.MEASURED_STATE_MASK));
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), this.mCurrentView, this.mCurrentRecurrenceOption, this.mRecurrenceRule);
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        View.BaseSavedState baseSavedState = (View.BaseSavedState) parcelable;
        super.onRestoreInstanceState(baseSavedState.getSuperState());
        SavedState savedState = (SavedState) baseSavedState;
        this.mCurrentView = savedState.getCurrentView();
        this.mCurrentRecurrenceOption = savedState.getCurrentRepeatOption();
        this.mRecurrenceRule = savedState.getRecurrenceRule();
        updateView();
    }

    private static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker.SavedState.1
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
        private final RecurrenceOption sCurrentRecurrenceOption;
        private final CurrentView sCurrentView;
        private final String sRecurrenceRule;

        private SavedState(Parcelable parcelable, CurrentView currentView, RecurrenceOption recurrenceOption, String str) {
            super(parcelable);
            this.sCurrentView = currentView;
            this.sCurrentRecurrenceOption = recurrenceOption;
            this.sRecurrenceRule = str;
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.sCurrentView = CurrentView.valueOf(parcel.readString());
            this.sCurrentRecurrenceOption = RecurrenceOption.valueOf(parcel.readString());
            this.sRecurrenceRule = parcel.readString();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.sCurrentView.name());
            parcel.writeString(this.sCurrentRecurrenceOption.name());
            parcel.writeString(this.sRecurrenceRule);
        }

        public CurrentView getCurrentView() {
            return this.sCurrentView;
        }

        public RecurrenceOption getCurrentRepeatOption() {
            return this.sCurrentRecurrenceOption;
        }

        public String getRecurrenceRule() {
            return this.sRecurrenceRule;
        }
    }
}
