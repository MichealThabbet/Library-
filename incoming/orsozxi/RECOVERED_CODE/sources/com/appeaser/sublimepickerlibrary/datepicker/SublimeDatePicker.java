package com.appeaser.sublimepickerlibrary.datepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.text.style.AlignmentSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;
import com.appeaser.sublimepickerlibrary.R;
import com.appeaser.sublimepickerlibrary.common.DateTimePatternHelper;
import com.appeaser.sublimepickerlibrary.datepicker.DayPickerView;
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.datepicker.YearPickerView;
import com.appeaser.sublimepickerlibrary.utilities.AccessibilityUtils;
import com.appeaser.sublimepickerlibrary.utilities.SUtils;
import com.appeaser.sublimepickerlibrary.utilities.TextColorHelper;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/* loaded from: classes.dex */
public class SublimeDatePicker extends FrameLayout {
    private static final int DEFAULT_END_YEAR = 2100;
    private static final int DEFAULT_START_YEAR = 1900;
    private static final int RANGE_ACTIVATED_END = 2;
    private static final int RANGE_ACTIVATED_NONE = 0;
    private static final int RANGE_ACTIVATED_START = 1;
    private static final String TAG = SublimeDatePicker.class.getSimpleName();
    private static final int UNINITIALIZED = -1;
    private static final int VIEW_MONTH_DAY = 0;
    private static final int VIEW_YEAR = 1;
    private ImageView ivHeaderDateReset;
    private LinearLayout llHeaderDateRangeCont;
    private LinearLayout llHeaderDateSingleCont;
    private ViewAnimator mAnimator;
    private ViewGroup mContainer;
    private Context mContext;
    private SelectedDate mCurrentDate;
    private Locale mCurrentLocale;
    private int mCurrentView;
    private int mCurrentlyActivatedRangeItem;
    private OnDateChangedListener mDateChangedListener;
    private DayPickerView mDayPickerView;
    private int mFirstDayOfWeek;
    private TextView mHeaderMonthDay;
    private TextView mHeaderYear;
    private boolean mIsInLandscapeMode;
    private Calendar mMaxDate;
    private Calendar mMinDate;
    private SimpleDateFormat mMonthDayFormat;
    private final View.OnClickListener mOnHeaderClickListener;
    private final YearPickerView.OnYearSelectedListener mOnYearSelectedListener;
    private final DayPickerView.ProxyDaySelectionEventListener mProxyDaySelectionEventListener;
    private String mSelectDay;
    private String mSelectYear;
    private Calendar mTempDate;
    private DatePickerValidationCallback mValidationCallback;
    private SimpleDateFormat mYearFormat;
    private YearPickerView mYearPickerView;
    private TextView tvHeaderDateEnd;
    private TextView tvHeaderDateStart;

    public interface DatePickerValidationCallback {
        void onDatePickerValidationChanged(boolean z);
    }

    public interface OnDateChangedListener {
        void onDateChanged(SublimeDatePicker sublimeDatePicker, SelectedDate selectedDate);
    }

    public SublimeDatePicker(Context context) {
        this(context, null);
    }

    public SublimeDatePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.spDatePickerStyle);
    }

    public SublimeDatePicker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCurrentView = -1;
        this.mCurrentlyActivatedRangeItem = 0;
        this.mProxyDaySelectionEventListener = new DayPickerView.ProxyDaySelectionEventListener() { // from class: com.appeaser.sublimepickerlibrary.datepicker.SublimeDatePicker.1
            @Override // com.appeaser.sublimepickerlibrary.datepicker.DayPickerView.ProxyDaySelectionEventListener
            public void onDaySelected(DayPickerView dayPickerView, Calendar calendar) {
                boolean z;
                if (SublimeDatePicker.this.llHeaderDateRangeCont.getVisibility() == 0) {
                    if (SublimeDatePicker.this.tvHeaderDateStart.isActivated()) {
                        if (SelectedDate.compareDates(calendar, SublimeDatePicker.this.mCurrentDate.getEndDate()) > 0) {
                            SublimeDatePicker.this.mCurrentDate = new SelectedDate(calendar);
                        } else {
                            SublimeDatePicker sublimeDatePicker = SublimeDatePicker.this;
                            sublimeDatePicker.mCurrentDate = new SelectedDate(calendar, sublimeDatePicker.mCurrentDate.getEndDate());
                            z = false;
                        }
                    } else if (SublimeDatePicker.this.tvHeaderDateEnd.isActivated()) {
                        if (SelectedDate.compareDates(calendar, SublimeDatePicker.this.mCurrentDate.getStartDate()) < 0) {
                            SublimeDatePicker.this.mCurrentDate = new SelectedDate(calendar);
                        } else {
                            SublimeDatePicker sublimeDatePicker2 = SublimeDatePicker.this;
                            sublimeDatePicker2.mCurrentDate = new SelectedDate(sublimeDatePicker2.mCurrentDate.getStartDate(), calendar);
                            z = false;
                        }
                    }
                    SublimeDatePicker.this.onDateChanged(true, false, z);
                }
                SublimeDatePicker.this.mCurrentDate = new SelectedDate(calendar);
                z = true;
                SublimeDatePicker.this.onDateChanged(true, false, z);
            }

            @Override // com.appeaser.sublimepickerlibrary.datepicker.DayPickerView.ProxyDaySelectionEventListener
            public void onDateRangeSelectionStarted(SelectedDate selectedDate) {
                SublimeDatePicker.this.mCurrentDate = new SelectedDate(selectedDate);
                SublimeDatePicker.this.onDateChanged(false, false, false);
            }

            @Override // com.appeaser.sublimepickerlibrary.datepicker.DayPickerView.ProxyDaySelectionEventListener
            public void onDateRangeSelectionEnded(SelectedDate selectedDate) {
                if (selectedDate != null) {
                    SublimeDatePicker.this.mCurrentDate = new SelectedDate(selectedDate);
                    SublimeDatePicker.this.onDateChanged(false, false, false);
                }
            }

            @Override // com.appeaser.sublimepickerlibrary.datepicker.DayPickerView.ProxyDaySelectionEventListener
            public void onDateRangeSelectionUpdated(SelectedDate selectedDate) {
                SublimeDatePicker.this.mCurrentDate = new SelectedDate(selectedDate);
                SublimeDatePicker.this.onDateChanged(false, false, false);
            }
        };
        this.mOnYearSelectedListener = new YearPickerView.OnYearSelectedListener() { // from class: com.appeaser.sublimepickerlibrary.datepicker.SublimeDatePicker.2
            @Override // com.appeaser.sublimepickerlibrary.datepicker.YearPickerView.OnYearSelectedListener
            public void onYearChanged(YearPickerView yearPickerView, int i2) {
                int i3 = SublimeDatePicker.this.mCurrentDate.getStartDate().get(5);
                int daysInMonth = SUtils.getDaysInMonth(SublimeDatePicker.this.mCurrentDate.getStartDate().get(2), i2);
                if (i3 > daysInMonth) {
                    SublimeDatePicker.this.mCurrentDate.set(5, daysInMonth);
                }
                SublimeDatePicker.this.mCurrentDate.set(1, i2);
                SublimeDatePicker.this.onDateChanged(true, true, true);
                SublimeDatePicker.this.setCurrentView(0);
            }
        };
        this.mOnHeaderClickListener = new View.OnClickListener() { // from class: com.appeaser.sublimepickerlibrary.datepicker.SublimeDatePicker.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SUtils.vibrateForDatePicker(SublimeDatePicker.this);
                if (view.getId() == R.id.date_picker_header_year) {
                    SublimeDatePicker.this.setCurrentView(1);
                    return;
                }
                if (view.getId() == R.id.date_picker_header_date) {
                    SublimeDatePicker.this.setCurrentView(0);
                    return;
                }
                if (view.getId() == R.id.tv_header_date_start) {
                    SublimeDatePicker.this.mCurrentlyActivatedRangeItem = 1;
                    SublimeDatePicker.this.tvHeaderDateStart.setActivated(true);
                    SublimeDatePicker.this.tvHeaderDateEnd.setActivated(false);
                } else if (view.getId() == R.id.tv_header_date_end) {
                    SublimeDatePicker.this.mCurrentlyActivatedRangeItem = 2;
                    SublimeDatePicker.this.tvHeaderDateStart.setActivated(false);
                    SublimeDatePicker.this.tvHeaderDateEnd.setActivated(true);
                } else if (view.getId() == R.id.iv_header_date_reset) {
                    SublimeDatePicker sublimeDatePicker = SublimeDatePicker.this;
                    sublimeDatePicker.mCurrentDate = new SelectedDate(sublimeDatePicker.mCurrentDate.getStartDate());
                    SublimeDatePicker.this.onDateChanged(true, false, true);
                }
            }
        };
        initializeLayout(attributeSet, i, R.style.SublimeDatePickerStyle);
    }

    public SublimeDatePicker(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mCurrentView = -1;
        this.mCurrentlyActivatedRangeItem = 0;
        this.mProxyDaySelectionEventListener = new DayPickerView.ProxyDaySelectionEventListener() { // from class: com.appeaser.sublimepickerlibrary.datepicker.SublimeDatePicker.1
            @Override // com.appeaser.sublimepickerlibrary.datepicker.DayPickerView.ProxyDaySelectionEventListener
            public void onDaySelected(DayPickerView dayPickerView, Calendar calendar) {
                boolean z;
                if (SublimeDatePicker.this.llHeaderDateRangeCont.getVisibility() == 0) {
                    if (SublimeDatePicker.this.tvHeaderDateStart.isActivated()) {
                        if (SelectedDate.compareDates(calendar, SublimeDatePicker.this.mCurrentDate.getEndDate()) > 0) {
                            SublimeDatePicker.this.mCurrentDate = new SelectedDate(calendar);
                        } else {
                            SublimeDatePicker sublimeDatePicker = SublimeDatePicker.this;
                            sublimeDatePicker.mCurrentDate = new SelectedDate(calendar, sublimeDatePicker.mCurrentDate.getEndDate());
                            z = false;
                        }
                    } else if (SublimeDatePicker.this.tvHeaderDateEnd.isActivated()) {
                        if (SelectedDate.compareDates(calendar, SublimeDatePicker.this.mCurrentDate.getStartDate()) < 0) {
                            SublimeDatePicker.this.mCurrentDate = new SelectedDate(calendar);
                        } else {
                            SublimeDatePicker sublimeDatePicker2 = SublimeDatePicker.this;
                            sublimeDatePicker2.mCurrentDate = new SelectedDate(sublimeDatePicker2.mCurrentDate.getStartDate(), calendar);
                            z = false;
                        }
                    }
                    SublimeDatePicker.this.onDateChanged(true, false, z);
                }
                SublimeDatePicker.this.mCurrentDate = new SelectedDate(calendar);
                z = true;
                SublimeDatePicker.this.onDateChanged(true, false, z);
            }

            @Override // com.appeaser.sublimepickerlibrary.datepicker.DayPickerView.ProxyDaySelectionEventListener
            public void onDateRangeSelectionStarted(SelectedDate selectedDate) {
                SublimeDatePicker.this.mCurrentDate = new SelectedDate(selectedDate);
                SublimeDatePicker.this.onDateChanged(false, false, false);
            }

            @Override // com.appeaser.sublimepickerlibrary.datepicker.DayPickerView.ProxyDaySelectionEventListener
            public void onDateRangeSelectionEnded(SelectedDate selectedDate) {
                if (selectedDate != null) {
                    SublimeDatePicker.this.mCurrentDate = new SelectedDate(selectedDate);
                    SublimeDatePicker.this.onDateChanged(false, false, false);
                }
            }

            @Override // com.appeaser.sublimepickerlibrary.datepicker.DayPickerView.ProxyDaySelectionEventListener
            public void onDateRangeSelectionUpdated(SelectedDate selectedDate) {
                SublimeDatePicker.this.mCurrentDate = new SelectedDate(selectedDate);
                SublimeDatePicker.this.onDateChanged(false, false, false);
            }
        };
        this.mOnYearSelectedListener = new YearPickerView.OnYearSelectedListener() { // from class: com.appeaser.sublimepickerlibrary.datepicker.SublimeDatePicker.2
            @Override // com.appeaser.sublimepickerlibrary.datepicker.YearPickerView.OnYearSelectedListener
            public void onYearChanged(YearPickerView yearPickerView, int i22) {
                int i3 = SublimeDatePicker.this.mCurrentDate.getStartDate().get(5);
                int daysInMonth = SUtils.getDaysInMonth(SublimeDatePicker.this.mCurrentDate.getStartDate().get(2), i22);
                if (i3 > daysInMonth) {
                    SublimeDatePicker.this.mCurrentDate.set(5, daysInMonth);
                }
                SublimeDatePicker.this.mCurrentDate.set(1, i22);
                SublimeDatePicker.this.onDateChanged(true, true, true);
                SublimeDatePicker.this.setCurrentView(0);
            }
        };
        this.mOnHeaderClickListener = new View.OnClickListener() { // from class: com.appeaser.sublimepickerlibrary.datepicker.SublimeDatePicker.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SUtils.vibrateForDatePicker(SublimeDatePicker.this);
                if (view.getId() == R.id.date_picker_header_year) {
                    SublimeDatePicker.this.setCurrentView(1);
                    return;
                }
                if (view.getId() == R.id.date_picker_header_date) {
                    SublimeDatePicker.this.setCurrentView(0);
                    return;
                }
                if (view.getId() == R.id.tv_header_date_start) {
                    SublimeDatePicker.this.mCurrentlyActivatedRangeItem = 1;
                    SublimeDatePicker.this.tvHeaderDateStart.setActivated(true);
                    SublimeDatePicker.this.tvHeaderDateEnd.setActivated(false);
                } else if (view.getId() == R.id.tv_header_date_end) {
                    SublimeDatePicker.this.mCurrentlyActivatedRangeItem = 2;
                    SublimeDatePicker.this.tvHeaderDateStart.setActivated(false);
                    SublimeDatePicker.this.tvHeaderDateEnd.setActivated(true);
                } else if (view.getId() == R.id.iv_header_date_reset) {
                    SublimeDatePicker sublimeDatePicker = SublimeDatePicker.this;
                    sublimeDatePicker.mCurrentDate = new SelectedDate(sublimeDatePicker.mCurrentDate.getStartDate());
                    SublimeDatePicker.this.onDateChanged(true, false, true);
                }
            }
        };
        initializeLayout(attributeSet, i, i2);
    }

    private void initializeLayout(AttributeSet attributeSet, int i, int i2) {
        Context context = getContext();
        this.mContext = context;
        this.mIsInLandscapeMode = context.getResources().getConfiguration().orientation == 2;
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
            this.mContainer = (ViewGroup) ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.date_picker_layout, (ViewGroup) this, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addView(this.mContainer);
        ViewGroup viewGroup = (ViewGroup) this.mContainer.findViewById(R.id.date_picker_header);
        this.llHeaderDateSingleCont = (LinearLayout) viewGroup.findViewById(R.id.ll_header_date_single_cont);
        TextView textView = (TextView) viewGroup.findViewById(R.id.date_picker_header_year);
        this.mHeaderYear = textView;
        textView.setOnClickListener(this.mOnHeaderClickListener);
        TextView textView2 = (TextView) viewGroup.findViewById(R.id.date_picker_header_date);
        this.mHeaderMonthDay = textView2;
        textView2.setOnClickListener(this.mOnHeaderClickListener);
        this.llHeaderDateRangeCont = (LinearLayout) viewGroup.findViewById(R.id.ll_header_date_range_cont);
        TextView textView3 = (TextView) viewGroup.findViewById(R.id.tv_header_date_start);
        this.tvHeaderDateStart = textView3;
        textView3.setOnClickListener(this.mOnHeaderClickListener);
        TextView textView4 = (TextView) viewGroup.findViewById(R.id.tv_header_date_end);
        this.tvHeaderDateEnd = textView4;
        textView4.setOnClickListener(this.mOnHeaderClickListener);
        ImageView imageView = (ImageView) viewGroup.findViewById(R.id.iv_header_date_reset);
        this.ivHeaderDateReset = imageView;
        imageView.setOnClickListener(this.mOnHeaderClickListener);
        TypedArray typedArrayObtainStyledAttributes2 = getContext().obtainStyledAttributes(R.styleable.SublimePicker);
        try {
            int color = typedArrayObtainStyledAttributes2.getColor(R.styleable.SublimePicker_spOverflowIconColor, SUtils.COLOR_TEXT_PRIMARY_INVERSE);
            int color2 = typedArrayObtainStyledAttributes2.getColor(R.styleable.SublimePicker_spOverflowIconPressedBgColor, SUtils.COLOR_TEXT_PRIMARY);
            typedArrayObtainStyledAttributes2.recycle();
            SUtils.setImageTintList(this.ivHeaderDateReset, ColorStateList.valueOf(color));
            SUtils.setViewBackground(this.ivHeaderDateReset, SUtils.createOverflowButtonBg(color2));
            ColorStateList colorStateList = typedArrayObtainStyledAttributes.getColorStateList(R.styleable.SublimeDatePicker_spHeaderTextColor);
            if (colorStateList == null) {
                colorStateList = TextColorHelper.resolveMaterialHeaderTextColor();
            }
            if (colorStateList != null) {
                this.mHeaderYear.setTextColor(colorStateList);
                this.mHeaderMonthDay.setTextColor(colorStateList);
            }
            if (SUtils.isApi_22_OrHigher()) {
                if (typedArrayObtainStyledAttributes.hasValueOrEmpty(R.styleable.SublimeDatePicker_spHeaderBackground)) {
                    SUtils.setViewBackground(viewGroup, typedArrayObtainStyledAttributes.getDrawable(R.styleable.SublimeDatePicker_spHeaderBackground));
                }
            } else if (typedArrayObtainStyledAttributes.hasValue(R.styleable.SublimeDatePicker_spHeaderBackground)) {
                SUtils.setViewBackground(viewGroup, typedArrayObtainStyledAttributes.getDrawable(R.styleable.SublimeDatePicker_spHeaderBackground));
            }
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
            ViewAnimator viewAnimator = (ViewAnimator) this.mContainer.findViewById(R.id.animator);
            this.mAnimator = viewAnimator;
            this.mDayPickerView = (DayPickerView) viewAnimator.findViewById(R.id.date_picker_day_picker);
            setFirstDayOfWeek(i3);
            this.mDayPickerView.setMinDate(this.mMinDate.getTimeInMillis());
            this.mDayPickerView.setMaxDate(this.mMaxDate.getTimeInMillis());
            this.mDayPickerView.setDate(this.mCurrentDate);
            this.mDayPickerView.setProxyDaySelectionEventListener(this.mProxyDaySelectionEventListener);
            YearPickerView yearPickerView = (YearPickerView) this.mAnimator.findViewById(R.id.date_picker_year_picker);
            this.mYearPickerView = yearPickerView;
            yearPickerView.setRange(this.mMinDate, this.mMaxDate);
            this.mYearPickerView.setOnYearSelectedListener(this.mOnYearSelectedListener);
            this.mSelectDay = resources.getString(R.string.select_day);
            this.mSelectYear = resources.getString(R.string.select_year);
            onLocaleChanged(this.mCurrentLocale);
            setCurrentView(0);
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes2.recycle();
            throw th;
        }
    }

    private void onLocaleChanged(Locale locale) {
        String bestDateTimePattern;
        if (this.mHeaderYear == null) {
            return;
        }
        if (SUtils.isApi_18_OrHigher()) {
            bestDateTimePattern = DateFormat.getBestDateTimePattern(locale, "EMMMd");
        } else {
            bestDateTimePattern = DateTimePatternHelper.getBestDateTimePattern(locale, 0);
        }
        this.mMonthDayFormat = new SimpleDateFormat(bestDateTimePattern, locale);
        this.mYearFormat = new SimpleDateFormat(com.ibm.icu.text.DateFormat.YEAR, locale);
        onCurrentDateChanged(false);
    }

    private void onCurrentDateChanged(boolean z) {
        if (this.mHeaderYear == null) {
            return;
        }
        this.mHeaderYear.setText(this.mYearFormat.format(this.mCurrentDate.getStartDate().getTime()));
        this.mHeaderMonthDay.setText(this.mMonthDayFormat.format(this.mCurrentDate.getStartDate().getTime()));
        String str = this.mYearFormat.format(this.mCurrentDate.getStartDate().getTime());
        String str2 = str + "\n" + this.mMonthDayFormat.format(this.mCurrentDate.getStartDate().getTime());
        String str3 = this.mYearFormat.format(this.mCurrentDate.getEndDate().getTime());
        String str4 = str3 + "\n" + this.mMonthDayFormat.format(this.mCurrentDate.getEndDate().getTime());
        SpannableString spannableString = new SpannableString(str2);
        spannableString.setSpan(new RelativeSizeSpan(0.7f), 0, str.length(), 33);
        SpannableString spannableString2 = new SpannableString(str4);
        spannableString2.setSpan(new RelativeSizeSpan(0.7f), 0, str3.length(), 33);
        if (!this.mIsInLandscapeMode && !SUtils.isApi_17_OrHigher()) {
            spannableString2.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE), 0, str4.length(), 33);
        }
        this.tvHeaderDateStart.setText(spannableString);
        this.tvHeaderDateEnd.setText(spannableString2);
        if (z) {
            AccessibilityUtils.makeAnnouncement(this.mAnimator, DateUtils.formatDateTime(this.mContext, this.mCurrentDate.getStartDate().getTimeInMillis(), 20));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCurrentView(int i) {
        if (i != 0) {
            if (i != 1) {
                return;
            }
            if (this.mCurrentView != i) {
                this.mHeaderMonthDay.setActivated(false);
                this.mHeaderYear.setActivated(true);
                this.mAnimator.setDisplayedChild(1);
                this.mCurrentView = i;
            }
            AccessibilityUtils.makeAnnouncement(this.mAnimator, this.mSelectYear);
            return;
        }
        this.mDayPickerView.setDate(this.mCurrentDate);
        if (this.mCurrentDate.getType() == SelectedDate.Type.SINGLE) {
            switchToSingleDateView();
        } else if (this.mCurrentDate.getType() == SelectedDate.Type.RANGE) {
            switchToDateRangeView();
        }
        if (this.mCurrentView != i) {
            if (this.mAnimator.getDisplayedChild() != 0) {
                this.mAnimator.setDisplayedChild(0);
            }
            this.mCurrentView = i;
        }
        AccessibilityUtils.makeAnnouncement(this.mAnimator, this.mSelectDay);
    }

    public void init(SelectedDate selectedDate, boolean z, OnDateChangedListener onDateChangedListener) {
        this.mCurrentDate = new SelectedDate(selectedDate);
        this.mDayPickerView.setCanPickRange(z);
        this.mDateChangedListener = onDateChangedListener;
        onDateChanged(false, false, true);
    }

    public void updateDate(int i, int i2, int i3) {
        this.mCurrentDate.set(1, i);
        this.mCurrentDate.set(2, i2);
        this.mCurrentDate.set(5, i3);
        onDateChanged(false, true, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDateChanged(boolean z, boolean z2, boolean z3) {
        OnDateChangedListener onDateChangedListener;
        int i = this.mCurrentDate.getStartDate().get(1);
        if (z2 && (onDateChangedListener = this.mDateChangedListener) != null) {
            onDateChangedListener.onDateChanged(this, this.mCurrentDate);
        }
        updateHeaderViews();
        this.mDayPickerView.setDate(new SelectedDate(this.mCurrentDate), false, z3);
        if (this.mCurrentDate.getType() == SelectedDate.Type.SINGLE) {
            this.mYearPickerView.setYear(i);
        }
        onCurrentDateChanged(z);
        if (z) {
            SUtils.vibrateForDatePicker(this);
        }
    }

    private void updateHeaderViews() {
        if (this.mCurrentDate.getType() == SelectedDate.Type.SINGLE) {
            switchToSingleDateView();
        } else if (this.mCurrentDate.getType() == SelectedDate.Type.RANGE) {
            switchToDateRangeView();
        }
    }

    private void switchToSingleDateView() {
        this.mCurrentlyActivatedRangeItem = 0;
        this.ivHeaderDateReset.setVisibility(8);
        this.llHeaderDateRangeCont.setVisibility(4);
        this.llHeaderDateSingleCont.setVisibility(0);
        this.mHeaderMonthDay.setActivated(true);
        this.mHeaderYear.setActivated(false);
    }

    private void switchToDateRangeView() {
        if (this.mCurrentlyActivatedRangeItem == 0) {
            this.mCurrentlyActivatedRangeItem = 1;
        }
        this.llHeaderDateSingleCont.setVisibility(4);
        this.ivHeaderDateReset.setVisibility(0);
        this.llHeaderDateRangeCont.setVisibility(0);
        this.tvHeaderDateStart.setActivated(this.mCurrentlyActivatedRangeItem == 1);
        this.tvHeaderDateEnd.setActivated(this.mCurrentlyActivatedRangeItem == 2);
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
                onDateChanged(false, true, true);
            }
            this.mMinDate.setTimeInMillis(j);
            this.mDayPickerView.setMinDate(j);
            this.mYearPickerView.setRange(this.mMinDate, this.mMaxDate);
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
                onDateChanged(false, true, true);
            }
            this.mMaxDate.setTimeInMillis(j);
            this.mDayPickerView.setMaxDate(j);
            this.mYearPickerView.setRange(this.mMinDate, this.mMaxDate);
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
        this.mYearPickerView.setEnabled(z);
        this.mHeaderYear.setEnabled(z);
        this.mHeaderMonthDay.setEnabled(z);
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
        int firstVisiblePosition;
        int firstPositionOffset;
        Parcelable parcelableOnSaveInstanceState = super.onSaveInstanceState();
        int i = this.mCurrentView;
        if (i == 0) {
            firstVisiblePosition = this.mDayPickerView.getMostVisiblePosition();
        } else {
            if (i == 1) {
                firstVisiblePosition = this.mYearPickerView.getFirstVisiblePosition();
                firstPositionOffset = this.mYearPickerView.getFirstPositionOffset();
                return new SavedState(parcelableOnSaveInstanceState, this.mCurrentDate, this.mMinDate.getTimeInMillis(), this.mMaxDate.getTimeInMillis(), this.mCurrentView, firstVisiblePosition, firstPositionOffset, this.mCurrentlyActivatedRangeItem);
            }
            firstVisiblePosition = -1;
        }
        firstPositionOffset = -1;
        return new SavedState(parcelableOnSaveInstanceState, this.mCurrentDate, this.mMinDate.getTimeInMillis(), this.mMaxDate.getTimeInMillis(), this.mCurrentView, firstVisiblePosition, firstPositionOffset, this.mCurrentlyActivatedRangeItem);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        View.BaseSavedState baseSavedState = (View.BaseSavedState) parcelable;
        super.onRestoreInstanceState(baseSavedState.getSuperState());
        SavedState savedState = (SavedState) baseSavedState;
        Calendar calendar = Calendar.getInstance(this.mCurrentLocale);
        Calendar calendar2 = Calendar.getInstance(this.mCurrentLocale);
        calendar.set(savedState.getSelectedYearStart(), savedState.getSelectedMonthStart(), savedState.getSelectedDayStart());
        calendar2.set(savedState.getSelectedYearEnd(), savedState.getSelectedMonthEnd(), savedState.getSelectedDayEnd());
        this.mCurrentDate.setFirstDate(calendar);
        this.mCurrentDate.setSecondDate(calendar2);
        int currentView = savedState.getCurrentView();
        this.mMinDate.setTimeInMillis(savedState.getMinDate());
        this.mMaxDate.setTimeInMillis(savedState.getMaxDate());
        this.mCurrentlyActivatedRangeItem = savedState.getCurrentlyActivatedRangeItem();
        onCurrentDateChanged(false);
        setCurrentView(currentView);
        int listPosition = savedState.getListPosition();
        if (listPosition != -1) {
            if (currentView == 0) {
                this.mDayPickerView.setPosition(listPosition);
            } else if (currentView == 1) {
                this.mYearPickerView.setSelectionFromTop(listPosition, savedState.getListPositionOffset());
            }
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
    }

    private static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.appeaser.sublimepickerlibrary.datepicker.SublimeDatePicker.SavedState.1
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
        private final int mCurrentView;
        private final int mListPosition;
        private final int mListPositionOffset;
        private final long mMaxDate;
        private final long mMinDate;
        private final int mSelectedDayEnd;
        private final int mSelectedDayStart;
        private final int mSelectedMonthEnd;
        private final int mSelectedMonthStart;
        private final int mSelectedYearEnd;
        private final int mSelectedYearStart;
        private final int ssCurrentlyActivatedRangeItem;

        private SavedState(Parcelable parcelable, SelectedDate selectedDate, long j, long j2, int i, int i2, int i3, int i4) {
            super(parcelable);
            this.mSelectedYearStart = selectedDate.getStartDate().get(1);
            this.mSelectedMonthStart = selectedDate.getStartDate().get(2);
            this.mSelectedDayStart = selectedDate.getStartDate().get(5);
            this.mSelectedYearEnd = selectedDate.getEndDate().get(1);
            this.mSelectedMonthEnd = selectedDate.getEndDate().get(2);
            this.mSelectedDayEnd = selectedDate.getEndDate().get(5);
            this.mMinDate = j;
            this.mMaxDate = j2;
            this.mCurrentView = i;
            this.mListPosition = i2;
            this.mListPositionOffset = i3;
            this.ssCurrentlyActivatedRangeItem = i4;
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.mSelectedYearStart = parcel.readInt();
            this.mSelectedMonthStart = parcel.readInt();
            this.mSelectedDayStart = parcel.readInt();
            this.mSelectedYearEnd = parcel.readInt();
            this.mSelectedMonthEnd = parcel.readInt();
            this.mSelectedDayEnd = parcel.readInt();
            this.mMinDate = parcel.readLong();
            this.mMaxDate = parcel.readLong();
            this.mCurrentView = parcel.readInt();
            this.mListPosition = parcel.readInt();
            this.mListPositionOffset = parcel.readInt();
            this.ssCurrentlyActivatedRangeItem = parcel.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mSelectedYearStart);
            parcel.writeInt(this.mSelectedMonthStart);
            parcel.writeInt(this.mSelectedDayStart);
            parcel.writeInt(this.mSelectedYearEnd);
            parcel.writeInt(this.mSelectedMonthEnd);
            parcel.writeInt(this.mSelectedDayEnd);
            parcel.writeLong(this.mMinDate);
            parcel.writeLong(this.mMaxDate);
            parcel.writeInt(this.mCurrentView);
            parcel.writeInt(this.mListPosition);
            parcel.writeInt(this.mListPositionOffset);
            parcel.writeInt(this.ssCurrentlyActivatedRangeItem);
        }

        public int getSelectedDayStart() {
            return this.mSelectedDayStart;
        }

        public int getSelectedMonthStart() {
            return this.mSelectedMonthStart;
        }

        public int getSelectedYearStart() {
            return this.mSelectedYearStart;
        }

        public int getSelectedDayEnd() {
            return this.mSelectedDayEnd;
        }

        public int getSelectedMonthEnd() {
            return this.mSelectedMonthEnd;
        }

        public int getSelectedYearEnd() {
            return this.mSelectedYearEnd;
        }

        public long getMinDate() {
            return this.mMinDate;
        }

        public long getMaxDate() {
            return this.mMaxDate;
        }

        public int getCurrentView() {
            return this.mCurrentView;
        }

        public int getListPosition() {
            return this.mListPosition;
        }

        public int getListPositionOffset() {
            return this.mListPositionOffset;
        }

        public int getCurrentlyActivatedRangeItem() {
            return this.ssCurrentlyActivatedRangeItem;
        }
    }
}
