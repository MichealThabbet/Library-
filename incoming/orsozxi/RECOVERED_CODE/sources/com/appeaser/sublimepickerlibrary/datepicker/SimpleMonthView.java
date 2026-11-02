package com.appeaser.sublimepickerlibrary.datepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.appeaser.sublimepickerlibrary.R;
import com.appeaser.sublimepickerlibrary.common.DateTimePatternHelper;
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.utilities.SUtils;
import com.ibm.icu.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
class SimpleMonthView extends View {
    private static final int DAYS_IN_WEEK = 7;
    private static final String DAY_OF_WEEK_FORMAT;
    private static final int DEFAULT_SELECTED_DAY = -1;
    private static final String DEFAULT_TITLE_FORMAT = "MMMMy";
    private static final int DEFAULT_WEEK_START = 1;
    private static final int MAX_WEEKS_IN_MONTH = 6;
    private static final String TAG = SimpleMonthView.class.getSimpleName();
    private final int DRAW_RECT;
    private final int DRAW_RECT_WITH_CURVE_ON_LEFT;
    private final int DRAW_RECT_WITH_CURVE_ON_RIGHT;
    private final ActivatedDays mActivatedDays;
    private final Calendar mCalendar;
    private int mCellWidth;
    private Context mContext;
    private NumberFormat mDayFormatter;
    private int mDayHeight;
    private final Paint mDayHighlightPaint;
    private SimpleDateFormat mDayOfWeekFormatter;
    private int mDayOfWeekHeight;
    private final Calendar mDayOfWeekLabelCalendar;
    private final TextPaint mDayOfWeekPaint;
    private int mDayOfWeekStart;
    private final TextPaint mDayPaint;
    private final Paint mDayRangeSelectorPaint;
    private final Paint mDaySelectorPaint;
    private int mDaySelectorRadius;
    private ColorStateList mDayTextColor;
    private int mDaysInMonth;
    private int mDesiredCellWidth;
    private int mDesiredDayHeight;
    private int mDesiredDayOfWeekHeight;
    private int mDesiredDaySelectorRadius;
    private int mDesiredMonthHeight;
    private int mDownX;
    private int mDownY;
    private int mEnabledDayEnd;
    private int mEnabledDayStart;
    private int mInitialTarget;
    private int mMonth;
    private int mMonthHeight;
    private final TextPaint mMonthPaint;
    private OnDayClickListener mOnDayClickListener;
    private int mPaddedHeight;
    private int mPaddedWidth;
    private float mPaddingRangeIndicator;
    private CheckForTap mPendingCheckForTap;
    private CharSequence mTitle;
    private SimpleDateFormat mTitleFormatter;
    private int mToday;
    private MonthViewTouchHelper mTouchHelper;
    private int mTouchSlopSquared;
    private int mTouchedItem;
    private int mWeekStart;
    private int mYear;

    public interface OnDayClickListener {
        void onDayClick(SimpleMonthView simpleMonthView, Calendar calendar);
    }

    private static boolean isValidDayOfWeek(int i) {
        return i >= 1 && i <= 7;
    }

    private static boolean isValidMonth(int i) {
        return i >= 0 && i <= 11;
    }

    static {
        if (SUtils.isApi_18_OrHigher()) {
            DAY_OF_WEEK_FORMAT = "EEEEE";
        } else {
            DAY_OF_WEEK_FORMAT = DateFormat.ABBR_WEEKDAY;
        }
    }

    public SimpleMonthView(Context context) {
        this(context, null);
    }

    public SimpleMonthView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.spMonthViewStyle);
    }

    public SimpleMonthView(Context context, AttributeSet attributeSet, int i) throws Resources.NotFoundException {
        super(context, attributeSet, i);
        this.DRAW_RECT = 0;
        this.DRAW_RECT_WITH_CURVE_ON_LEFT = 1;
        this.DRAW_RECT_WITH_CURVE_ON_RIGHT = 2;
        this.mMonthPaint = new TextPaint();
        this.mDayOfWeekPaint = new TextPaint();
        this.mDayPaint = new TextPaint();
        this.mDaySelectorPaint = new Paint();
        this.mDayHighlightPaint = new Paint();
        this.mDayRangeSelectorPaint = new Paint();
        this.mCalendar = Calendar.getInstance();
        this.mDayOfWeekLabelCalendar = Calendar.getInstance();
        this.mActivatedDays = new ActivatedDays();
        this.mToday = -1;
        this.mWeekStart = 1;
        this.mEnabledDayStart = 1;
        this.mEnabledDayEnd = 31;
        this.mTouchedItem = -1;
        this.mInitialTarget = -1;
        init();
    }

    public SimpleMonthView(Context context, AttributeSet attributeSet, int i, int i2) throws Resources.NotFoundException {
        super(context, attributeSet, i, i2);
        this.DRAW_RECT = 0;
        this.DRAW_RECT_WITH_CURVE_ON_LEFT = 1;
        this.DRAW_RECT_WITH_CURVE_ON_RIGHT = 2;
        this.mMonthPaint = new TextPaint();
        this.mDayOfWeekPaint = new TextPaint();
        this.mDayPaint = new TextPaint();
        this.mDaySelectorPaint = new Paint();
        this.mDayHighlightPaint = new Paint();
        this.mDayRangeSelectorPaint = new Paint();
        this.mCalendar = Calendar.getInstance();
        this.mDayOfWeekLabelCalendar = Calendar.getInstance();
        this.mActivatedDays = new ActivatedDays();
        this.mToday = -1;
        this.mWeekStart = 1;
        this.mEnabledDayStart = 1;
        this.mEnabledDayEnd = 31;
        this.mTouchedItem = -1;
        this.mInitialTarget = -1;
        init();
    }

    private void init() throws Resources.NotFoundException {
        String bestDateTimePattern;
        Context context = getContext();
        this.mContext = context;
        this.mTouchSlopSquared = ViewConfiguration.get(context).getScaledTouchSlop() * ViewConfiguration.get(this.mContext).getScaledTouchSlop();
        Resources resources = this.mContext.getResources();
        this.mDesiredMonthHeight = resources.getDimensionPixelSize(R.dimen.sp_date_picker_month_height);
        this.mDesiredDayOfWeekHeight = resources.getDimensionPixelSize(R.dimen.sp_date_picker_day_of_week_height);
        this.mDesiredDayHeight = resources.getDimensionPixelSize(R.dimen.sp_date_picker_day_height);
        this.mDesiredCellWidth = resources.getDimensionPixelSize(R.dimen.sp_date_picker_day_width);
        this.mDesiredDaySelectorRadius = resources.getDimensionPixelSize(R.dimen.sp_date_picker_day_selector_radius);
        this.mPaddingRangeIndicator = resources.getDimensionPixelSize(R.dimen.sp_month_view_range_padding);
        MonthViewTouchHelper monthViewTouchHelper = new MonthViewTouchHelper(this);
        this.mTouchHelper = monthViewTouchHelper;
        ViewCompat.setAccessibilityDelegate(this, monthViewTouchHelper);
        ViewCompat.setImportantForAccessibility(this, 1);
        Locale locale = resources.getConfiguration().locale;
        if (SUtils.isApi_18_OrHigher()) {
            bestDateTimePattern = android.text.format.DateFormat.getBestDateTimePattern(locale, DEFAULT_TITLE_FORMAT);
        } else {
            bestDateTimePattern = DateTimePatternHelper.getBestDateTimePattern(locale, 1);
        }
        this.mTitleFormatter = new SimpleDateFormat(bestDateTimePattern, locale);
        this.mDayOfWeekFormatter = new SimpleDateFormat(DAY_OF_WEEK_FORMAT, locale);
        this.mDayFormatter = NumberFormat.getIntegerInstance(locale);
        initPaints(resources);
    }

    private ColorStateList applyTextAppearance(Paint paint, int i) {
        TextView textView = new TextView(this.mContext);
        if (SUtils.isApi_23_OrHigher()) {
            textView.setTextAppearance(i);
        } else {
            textView.setTextAppearance(this.mContext, i);
        }
        paint.setTypeface(textView.getTypeface());
        paint.setTextSize(textView.getTextSize());
        ColorStateList textColors = textView.getTextColors();
        if (textColors != null) {
            paint.setColor(textColors.getColorForState(ENABLED_STATE_SET, 0));
        }
        return textColors;
    }

    public int getMonthHeight() {
        return this.mMonthHeight;
    }

    public int getCellWidth() {
        return this.mCellWidth;
    }

    public void setMonthTextAppearance(int i) {
        applyTextAppearance(this.mMonthPaint, i);
        invalidate();
    }

    public void setDayOfWeekTextAppearance(int i) {
        applyTextAppearance(this.mDayOfWeekPaint, i);
        invalidate();
    }

    public void setDayTextAppearance(int i) {
        ColorStateList colorStateListApplyTextAppearance = applyTextAppearance(this.mDayPaint, i);
        if (colorStateListApplyTextAppearance != null) {
            this.mDayTextColor = colorStateListApplyTextAppearance;
        }
        invalidate();
    }

    public CharSequence getTitle() {
        if (this.mTitle == null) {
            this.mTitle = this.mTitleFormatter.format(this.mCalendar.getTime());
        }
        return this.mTitle;
    }

    private void initPaints(Resources resources) throws Resources.NotFoundException {
        String string = resources.getString(R.string.sp_date_picker_month_typeface);
        String string2 = resources.getString(R.string.sp_date_picker_day_of_week_typeface);
        String string3 = resources.getString(R.string.sp_date_picker_day_typeface);
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sp_date_picker_month_text_size);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.sp_date_picker_day_of_week_text_size);
        int dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.sp_date_picker_day_text_size);
        this.mMonthPaint.setAntiAlias(true);
        this.mMonthPaint.setTextSize(dimensionPixelSize);
        this.mMonthPaint.setTypeface(Typeface.create(string, 0));
        this.mMonthPaint.setTextAlign(Paint.Align.CENTER);
        this.mMonthPaint.setStyle(Paint.Style.FILL);
        this.mDayOfWeekPaint.setAntiAlias(true);
        this.mDayOfWeekPaint.setTextSize(dimensionPixelSize2);
        this.mDayOfWeekPaint.setTypeface(Typeface.create(string2, 0));
        this.mDayOfWeekPaint.setTextAlign(Paint.Align.CENTER);
        this.mDayOfWeekPaint.setStyle(Paint.Style.FILL);
        this.mDaySelectorPaint.setAntiAlias(true);
        this.mDaySelectorPaint.setStyle(Paint.Style.FILL);
        this.mDayHighlightPaint.setAntiAlias(true);
        this.mDayHighlightPaint.setStyle(Paint.Style.FILL);
        this.mDayRangeSelectorPaint.setAntiAlias(true);
        this.mDayRangeSelectorPaint.setStyle(Paint.Style.FILL);
        this.mDayPaint.setAntiAlias(true);
        this.mDayPaint.setTextSize(dimensionPixelSize3);
        this.mDayPaint.setTypeface(Typeface.create(string3, 0));
        this.mDayPaint.setTextAlign(Paint.Align.CENTER);
        this.mDayPaint.setStyle(Paint.Style.FILL);
    }

    void setMonthTextColor(ColorStateList colorStateList) {
        this.mMonthPaint.setColor(colorStateList.getColorForState(ENABLED_STATE_SET, 0));
        invalidate();
    }

    void setDayOfWeekTextColor(ColorStateList colorStateList) {
        this.mDayOfWeekPaint.setColor(colorStateList.getColorForState(ENABLED_STATE_SET, 0));
        invalidate();
    }

    void setDayTextColor(ColorStateList colorStateList) {
        this.mDayTextColor = colorStateList;
        invalidate();
    }

    void setDaySelectorColor(ColorStateList colorStateList) {
        int colorForState = colorStateList.getColorForState(SUtils.resolveStateSet(3), 0);
        this.mDaySelectorPaint.setColor(colorForState);
        this.mDayRangeSelectorPaint.setColor(colorForState);
        this.mDayRangeSelectorPaint.setAlpha(150);
        invalidate();
    }

    void setDayHighlightColor(ColorStateList colorStateList) {
        this.mDayHighlightPaint.setColor(colorStateList.getColorForState(SUtils.resolveStateSet(5), 0));
        invalidate();
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        this.mOnDayClickListener = onDayClickListener;
    }

    @Override // android.view.View
    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return this.mTouchHelper.dispatchHoverEvent(motionEvent) || super.dispatchHoverEvent(motionEvent);
    }

    private boolean isStillAClick(int i, int i2) {
        int i3 = this.mDownX;
        int i4 = (i - i3) * (i - i3);
        int i5 = this.mDownY;
        return i4 + ((i2 - i5) * (i2 - i5)) <= this.mTouchSlopSquared;
    }

    private final class CheckForTap implements Runnable {
        private CheckForTap() {
        }

        @Override // java.lang.Runnable
        public void run() {
            SimpleMonthView simpleMonthView = SimpleMonthView.this;
            simpleMonthView.mTouchedItem = simpleMonthView.getDayAtLocation(simpleMonthView.mDownX, SimpleMonthView.this.mDownY);
            SimpleMonthView.this.invalidate();
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int x = (int) (motionEvent.getX() + 0.5f);
        int y = (int) (motionEvent.getY() + 0.5f);
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mDownX = x;
            this.mDownY = y;
            int dayAtLocation = getDayAtLocation(x, y);
            this.mInitialTarget = dayAtLocation;
            if (dayAtLocation < 0) {
                return false;
            }
            if (this.mPendingCheckForTap == null) {
                this.mPendingCheckForTap = new CheckForTap();
            }
            postDelayed(this.mPendingCheckForTap, ViewConfiguration.getTapTimeout());
        } else {
            if (action == 1) {
                onDayClicked(this.mInitialTarget);
            } else if (action != 2) {
                if (action == 3) {
                }
            } else if (!isStillAClick(x, y)) {
                CheckForTap checkForTap = this.mPendingCheckForTap;
                if (checkForTap != null) {
                    removeCallbacks(checkForTap);
                }
                this.mInitialTarget = -1;
                if (this.mTouchedItem >= 0) {
                    this.mTouchedItem = -1;
                    invalidate();
                }
            }
            CheckForTap checkForTap2 = this.mPendingCheckForTap;
            if (checkForTap2 != null) {
                removeCallbacks(checkForTap2);
            }
            this.mTouchedItem = -1;
            this.mInitialTarget = -1;
            invalidate();
        }
        return true;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.translate(getPaddingLeft(), getPaddingTop());
        drawMonth(canvas);
        drawDaysOfWeek(canvas);
        drawDays(canvas);
        canvas.translate(-r0, -r1);
    }

    private void drawMonth(Canvas canvas) {
        canvas.drawText(getTitle().toString(), this.mPaddedWidth / 2.0f, (this.mMonthHeight - (this.mMonthPaint.ascent() + this.mMonthPaint.descent())) / 2.0f, this.mMonthPaint);
    }

    private void drawDaysOfWeek(Canvas canvas) {
        TextPaint textPaint = this.mDayOfWeekPaint;
        int i = this.mMonthHeight;
        int i2 = this.mDayOfWeekHeight;
        int i3 = this.mCellWidth;
        float fAscent = (textPaint.ascent() + textPaint.descent()) / 2.0f;
        int i4 = i + (i2 / 2);
        for (int i5 = 0; i5 < 7; i5++) {
            int i6 = (i3 * i5) + (i3 / 2);
            if (SUtils.isLayoutRtlCompat(this)) {
                i6 = this.mPaddedWidth - i6;
            }
            canvas.drawText(getDayOfWeekLabel((this.mWeekStart + i5) % 7), i6, i4 - fAscent, textPaint);
        }
    }

    private String getDayOfWeekLabel(int i) {
        this.mDayOfWeekLabelCalendar.set(7, i);
        return this.mDayOfWeekFormatter.format(this.mDayOfWeekLabelCalendar.getTime());
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01a1  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01cd  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01cf A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void drawDays(android.graphics.Canvas r29) {
        /*
            Method dump skipped, instructions count: 471
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appeaser.sublimepickerlibrary.datepicker.SimpleMonthView.drawDays(android.graphics.Canvas):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDayEnabled(int i) {
        return i >= this.mEnabledDayStart && i <= this.mEnabledDayEnd;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isValidDayOfMonth(int i) {
        return i >= 1 && i <= this.mDaysInMonth;
    }

    public void selectAllDays() {
        setSelectedDays(1, SUtils.getDaysInMonth(this.mMonth, this.mYear), SelectedDate.Type.RANGE);
    }

    public void setSelectedDays(int i, int i2, SelectedDate.Type type) {
        this.mActivatedDays.startingDay = i;
        this.mActivatedDays.endingDay = i2;
        this.mActivatedDays.selectedDateType = type;
        this.mTouchHelper.invalidateRoot();
        invalidate();
    }

    public void setFirstDayOfWeek(int i) {
        if (isValidDayOfWeek(i)) {
            this.mWeekStart = i;
        } else {
            this.mWeekStart = this.mCalendar.getFirstDayOfWeek();
        }
        this.mTouchHelper.invalidateRoot();
        invalidate();
    }

    void setMonthParams(int i, int i2, int i3, int i4, int i5, int i6, int i7, SelectedDate.Type type) {
        if (isValidMonth(i)) {
            this.mMonth = i;
        }
        this.mYear = i2;
        this.mCalendar.set(2, this.mMonth);
        this.mCalendar.set(1, this.mYear);
        this.mCalendar.set(5, 1);
        this.mDayOfWeekStart = this.mCalendar.get(7);
        if (isValidDayOfWeek(i3)) {
            this.mWeekStart = i3;
        } else {
            this.mWeekStart = this.mCalendar.getFirstDayOfWeek();
        }
        Calendar calendar = Calendar.getInstance();
        this.mToday = -1;
        this.mDaysInMonth = SUtils.getDaysInMonth(this.mMonth, this.mYear);
        int i8 = 0;
        while (true) {
            int i9 = this.mDaysInMonth;
            if (i8 < i9) {
                i8++;
                if (sameDay(i8, calendar)) {
                    this.mToday = i8;
                }
            } else {
                int iConstrain = SUtils.constrain(i4, 1, i9);
                this.mEnabledDayStart = iConstrain;
                this.mEnabledDayEnd = SUtils.constrain(i5, iConstrain, this.mDaysInMonth);
                this.mTitle = null;
                this.mActivatedDays.startingDay = i6;
                this.mActivatedDays.endingDay = i7;
                this.mActivatedDays.selectedDateType = type;
                this.mTouchHelper.invalidateRoot();
                return;
            }
        }
    }

    private boolean sameDay(int i, Calendar calendar) {
        return this.mYear == calendar.get(1) && this.mMonth == calendar.get(2) && i == calendar.get(5);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(resolveSize((this.mDesiredCellWidth * 7) + (SUtils.isApi_17_OrHigher() ? getPaddingStart() : getPaddingLeft()) + (SUtils.isApi_17_OrHigher() ? getPaddingEnd() : getPaddingRight()), i), resolveSize((this.mDesiredDayHeight * 6) + this.mDesiredDayOfWeekHeight + this.mDesiredMonthHeight + getPaddingTop() + getPaddingBottom(), i2));
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        requestLayout();
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (z) {
            int i5 = i3 - i;
            int i6 = i4 - i2;
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int paddingRight = getPaddingRight();
            int paddingBottom = getPaddingBottom();
            int i7 = (i5 - paddingRight) - paddingLeft;
            int i8 = (i6 - paddingBottom) - paddingTop;
            if (i7 == this.mPaddedWidth || i8 == this.mPaddedHeight) {
                return;
            }
            this.mPaddedWidth = i7;
            this.mPaddedHeight = i8;
            float measuredHeight = i8 / ((getMeasuredHeight() - paddingTop) - paddingBottom);
            int i9 = this.mPaddedWidth / 7;
            this.mMonthHeight = (int) (this.mDesiredMonthHeight * measuredHeight);
            this.mDayOfWeekHeight = (int) (this.mDesiredDayOfWeekHeight * measuredHeight);
            this.mDayHeight = (int) (this.mDesiredDayHeight * measuredHeight);
            this.mCellWidth = i9;
            this.mDaySelectorRadius = Math.min(this.mDesiredDaySelectorRadius, Math.min((i9 / 2) + Math.min(paddingLeft, paddingRight), (this.mDayHeight / 2) + paddingBottom));
            this.mTouchHelper.invalidateRoot();
        }
    }

    private int findDayOffset() {
        int i = this.mDayOfWeekStart;
        int i2 = this.mWeekStart;
        int i3 = i - i2;
        return i < i2 ? i3 + 7 : i3;
    }

    public int getDayAtLocation(int i, int i2) {
        int i3;
        int paddingTop;
        int paddingLeft = i - getPaddingLeft();
        if (paddingLeft < 0 || paddingLeft >= this.mPaddedWidth || (paddingTop = i2 - getPaddingTop()) < (i3 = this.mMonthHeight + this.mDayOfWeekHeight) || paddingTop >= this.mPaddedHeight) {
            return -1;
        }
        if (SUtils.isLayoutRtlCompat(this)) {
            paddingLeft = this.mPaddedWidth - paddingLeft;
        }
        int iFindDayOffset = ((((paddingLeft * 7) / this.mPaddedWidth) + (((paddingTop - i3) / this.mDayHeight) * 7)) + 1) - findDayOffset();
        if (isValidDayOfMonth(iFindDayOffset)) {
            return iFindDayOffset;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getBoundsForDay(int i, Rect rect) {
        int paddingLeft;
        if (!isValidDayOfMonth(i)) {
            return false;
        }
        int iFindDayOffset = (i - 1) + findDayOffset();
        int i2 = iFindDayOffset % 7;
        int i3 = this.mCellWidth;
        if (SUtils.isLayoutRtlCompat(this)) {
            paddingLeft = (getWidth() - getPaddingRight()) - ((i2 + 1) * i3);
        } else {
            paddingLeft = getPaddingLeft() + (i2 * i3);
        }
        int i4 = this.mDayHeight;
        int paddingTop = getPaddingTop() + this.mMonthHeight + this.mDayOfWeekHeight + ((iFindDayOffset / 7) * i4);
        rect.set(paddingLeft, paddingTop, i3 + paddingLeft, i4 + paddingTop);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onDayClicked(int i) {
        if (!isValidDayOfMonth(i) || !isDayEnabled(i)) {
            return false;
        }
        if (this.mOnDayClickListener != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(this.mYear, this.mMonth, i);
            this.mOnDayClickListener.onDayClick(this, calendar);
        }
        this.mTouchHelper.sendEventForVirtualView(i, 1);
        return true;
    }

    private class MonthViewTouchHelper extends ExploreByTouchHelper {
        private static final String DATE_FORMAT = "dd MMMM yyyy";
        private final Calendar mTempCalendar;
        private final Rect mTempRect;

        public MonthViewTouchHelper(View view) {
            super(view);
            this.mTempRect = new Rect();
            this.mTempCalendar = Calendar.getInstance();
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected int getVirtualViewAt(float f, float f2) {
            int dayAtLocation = SimpleMonthView.this.getDayAtLocation((int) (f + 0.5f), (int) (f2 + 0.5f));
            if (dayAtLocation != -1) {
                return dayAtLocation;
            }
            return Integer.MIN_VALUE;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected void getVisibleVirtualViews(List<Integer> list) {
            for (int i = 1; i <= SimpleMonthView.this.mDaysInMonth; i++) {
                list.add(Integer.valueOf(i));
            }
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected void onPopulateEventForVirtualView(int i, AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.setContentDescription(getDayDescription(i));
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            if (!SimpleMonthView.this.getBoundsForDay(i, this.mTempRect)) {
                this.mTempRect.setEmpty();
                accessibilityNodeInfoCompat.setContentDescription("");
                accessibilityNodeInfoCompat.setBoundsInParent(this.mTempRect);
                accessibilityNodeInfoCompat.setVisibleToUser(false);
                return;
            }
            accessibilityNodeInfoCompat.setText(getDayText(i));
            accessibilityNodeInfoCompat.setContentDescription(getDayDescription(i));
            accessibilityNodeInfoCompat.setBoundsInParent(this.mTempRect);
            boolean zIsDayEnabled = SimpleMonthView.this.isDayEnabled(i);
            if (zIsDayEnabled) {
                accessibilityNodeInfoCompat.addAction(16);
            }
            accessibilityNodeInfoCompat.setEnabled(zIsDayEnabled);
            if (SimpleMonthView.this.mActivatedDays.isValid() && SimpleMonthView.this.mActivatedDays.isActivated(i)) {
                accessibilityNodeInfoCompat.setChecked(true);
            }
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
            if (i2 != 16) {
                return false;
            }
            return SimpleMonthView.this.onDayClicked(i);
        }

        private CharSequence getDayDescription(int i) {
            if (!SimpleMonthView.this.isValidDayOfMonth(i)) {
                return "";
            }
            this.mTempCalendar.set(SimpleMonthView.this.mYear, SimpleMonthView.this.mMonth, i);
            return android.text.format.DateFormat.format(DATE_FORMAT, this.mTempCalendar.getTimeInMillis());
        }

        private CharSequence getDayText(int i) {
            if (SimpleMonthView.this.isValidDayOfMonth(i)) {
                return SimpleMonthView.this.mDayFormatter.format(i);
            }
            return null;
        }
    }

    public Calendar composeDate(int i) {
        if (!isValidDayOfMonth(i) || !isDayEnabled(i)) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(this.mYear, this.mMonth, i);
        return calendar;
    }

    public class ActivatedDays {
        public SelectedDate.Type selectedDateType;
        public int startingDay = -1;
        public int endingDay = -1;

        public ActivatedDays() {
        }

        public void reset() {
            this.endingDay = -1;
            this.startingDay = -1;
        }

        public boolean isValid() {
            return (this.startingDay == -1 || this.endingDay == -1) ? false : true;
        }

        public boolean isActivated(int i) {
            return i >= this.startingDay && i <= this.endingDay;
        }

        public boolean isStartingDayOfRange(int i) {
            return i == this.startingDay;
        }

        public boolean isEndingDayOfRange(int i) {
            return i == this.endingDay;
        }

        public boolean isSingleDay() {
            return this.startingDay == this.endingDay;
        }

        public boolean isSelected(int i) {
            return this.selectedDateType == SelectedDate.Type.SINGLE && this.startingDay == i && this.endingDay == i;
        }

        public int getSelectedDay() {
            int i;
            if (this.selectedDateType == SelectedDate.Type.SINGLE && (i = this.startingDay) == this.endingDay) {
                return i;
            }
            return -1;
        }

        public boolean hasSelectedDay() {
            int i;
            return this.selectedDateType == SelectedDate.Type.SINGLE && (i = this.startingDay) == this.endingDay && i != -1;
        }

        public boolean isStartOfMonth() {
            return this.startingDay == 1;
        }
    }
}
