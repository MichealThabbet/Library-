package com.appeaser.sublimepickerlibrary.utilities;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ImageView;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

/* loaded from: classes.dex */
public class SUtils {
    private static final int CHANGE_YEAR = 1582;
    public static int COLOR_ACCENT = 0;
    public static int COLOR_BACKGROUND = 0;
    public static int COLOR_BUTTON_NORMAL = 0;
    public static int COLOR_CONTROL_ACTIVATED = 0;
    public static int COLOR_CONTROL_HIGHLIGHT = 0;
    public static int COLOR_PRIMARY = 0;
    public static int COLOR_PRIMARY_DARK = 0;
    public static int COLOR_TEXT_PRIMARY = 0;
    public static int COLOR_TEXT_PRIMARY_INVERSE = 0;
    public static int COLOR_TEXT_SECONDARY = 0;
    public static int COLOR_TEXT_SECONDARY_INVERSE = 0;
    public static final int CORNERS_ALL = 15;
    public static final int CORNER_BOTTOM_LEFT = 8;
    public static final int CORNER_BOTTOM_RIGHT = 4;
    public static int CORNER_RADIUS = 0;
    public static final int CORNER_TOP_LEFT = 1;
    public static final int CORNER_TOP_RIGHT = 2;
    public static final int STATE_ACTIVATED = 2;
    public static final int STATE_ENABLED = 1;
    public static final int STATE_PRESSED = 4;
    private static final String TAG = SUtils.class.getSimpleName();
    private static final int[][] STATE_SETS = {new int[]{0}, new int[]{R.attr.state_enabled}, new int[]{R.attr.state_activated}, new int[]{R.attr.state_enabled, R.attr.state_activated}, new int[]{R.attr.state_pressed}, new int[]{R.attr.state_enabled, R.attr.state_pressed}, new int[]{R.attr.state_activated, R.attr.state_pressed}, new int[]{R.attr.state_enabled, R.attr.state_activated, R.attr.state_pressed}};
    private static final String DATE_FORMAT = "MM/dd/yyyy";
    private static final DateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    public static int constrain(int i, int i2, int i3) {
        return i < i2 ? i2 : i > i3 ? i3 : i;
    }

    public static long constrain(long j, long j2, long j3) {
        return j < j2 ? j2 : j > j3 ? j3 : j;
    }

    public static void initializeResources(Context context) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{com.appeaser.sublimepickerlibrary.R.attr.colorAccent, com.appeaser.sublimepickerlibrary.R.attr.colorControlHighlight, com.appeaser.sublimepickerlibrary.R.attr.colorControlActivated, com.appeaser.sublimepickerlibrary.R.attr.colorButtonNormal, R.attr.textColorPrimary, R.attr.textColorPrimaryInverse, com.appeaser.sublimepickerlibrary.R.attr.colorPrimary, com.appeaser.sublimepickerlibrary.R.attr.colorPrimaryDark, R.attr.textColorSecondary, R.attr.colorBackground, R.attr.textColorSecondaryInverse});
        if (typedArrayObtainStyledAttributes.hasValue(0)) {
            COLOR_ACCENT = typedArrayObtainStyledAttributes.getColor(0, 0);
        }
        if (typedArrayObtainStyledAttributes.hasValue(1)) {
            COLOR_CONTROL_HIGHLIGHT = typedArrayObtainStyledAttributes.getColor(1, 0);
        }
        if (typedArrayObtainStyledAttributes.hasValue(2)) {
            COLOR_CONTROL_ACTIVATED = typedArrayObtainStyledAttributes.getColor(2, 0);
        }
        if (typedArrayObtainStyledAttributes.hasValue(3)) {
            COLOR_BUTTON_NORMAL = typedArrayObtainStyledAttributes.getColor(3, 0);
        }
        if (typedArrayObtainStyledAttributes.hasValue(4)) {
            COLOR_TEXT_PRIMARY = typedArrayObtainStyledAttributes.getColor(4, 0);
        }
        if (typedArrayObtainStyledAttributes.hasValue(5)) {
            COLOR_TEXT_PRIMARY_INVERSE = typedArrayObtainStyledAttributes.getColor(5, 0);
        }
        if (typedArrayObtainStyledAttributes.hasValue(6)) {
            COLOR_PRIMARY = typedArrayObtainStyledAttributes.getColor(6, 0);
        }
        if (typedArrayObtainStyledAttributes.hasValue(7)) {
            COLOR_PRIMARY_DARK = typedArrayObtainStyledAttributes.getColor(7, 0);
        }
        if (typedArrayObtainStyledAttributes.hasValue(8)) {
            COLOR_TEXT_SECONDARY = typedArrayObtainStyledAttributes.getColor(8, 0);
        }
        if (typedArrayObtainStyledAttributes.hasValue(9)) {
            COLOR_BACKGROUND = typedArrayObtainStyledAttributes.getColor(9, 0);
        }
        if (typedArrayObtainStyledAttributes.hasValue(10)) {
            COLOR_TEXT_SECONDARY_INVERSE = typedArrayObtainStyledAttributes.getColor(10, 0);
        }
        typedArrayObtainStyledAttributes.recycle();
        CORNER_RADIUS = context.getResources().getDimensionPixelSize(com.appeaser.sublimepickerlibrary.R.dimen.control_corner_material);
    }

    public static boolean isApi_16_OrHigher() {
        return Build.VERSION.SDK_INT >= 16;
    }

    public static boolean isApi_17_OrHigher() {
        return Build.VERSION.SDK_INT >= 17;
    }

    public static boolean isApi_18_OrHigher() {
        return Build.VERSION.SDK_INT >= 18;
    }

    public static boolean isApi_21_OrHigher() {
        return Build.VERSION.SDK_INT >= 21;
    }

    public static boolean isApi_22_OrHigher() {
        return Build.VERSION.SDK_INT >= 22;
    }

    public static boolean isApi_23_OrHigher() {
        return Build.VERSION.SDK_INT >= 23;
    }

    public static void setViewBackground(View view, Drawable drawable) {
        int paddingLeft = view.getPaddingLeft();
        int paddingTop = view.getPaddingTop();
        int paddingRight = view.getPaddingRight();
        int paddingBottom = view.getPaddingBottom();
        if (isApi_16_OrHigher()) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
        view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    public static Drawable createButtonBg(Context context, int i, int i2) {
        if (isApi_21_OrHigher()) {
            return createButtonRippleBg(context, i, i2);
        }
        return createButtonNormalBg(context, i2);
    }

    private static Drawable createButtonRippleBg(Context context, int i, int i2) {
        return new RippleDrawable(ColorStateList.valueOf(i2), null, createButtonShape(context, i));
    }

    private static Drawable createButtonNormalBg(Context context, int i) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{R.attr.state_pressed}, createButtonShape(context, i));
        stateListDrawable.addState(new int[0], new ColorDrawable(0));
        return stateListDrawable;
    }

    private static Drawable createButtonShape(Context context, int i) throws Resources.NotFoundException {
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(com.appeaser.sublimepickerlibrary.R.dimen.button_padding_horizontal_material);
        int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(com.appeaser.sublimepickerlibrary.R.dimen.button_padding_vertical_material);
        int dimensionPixelSize3 = context.getResources().getDimensionPixelSize(com.appeaser.sublimepickerlibrary.R.dimen.button_inset_horizontal_material);
        int dimensionPixelSize4 = context.getResources().getDimensionPixelSize(com.appeaser.sublimepickerlibrary.R.dimen.button_inset_vertical_material);
        float[] fArr = new float[8];
        Arrays.fill(fArr, CORNER_RADIUS);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(fArr, null, null));
        shapeDrawable.getPaint().setColor(i);
        shapeDrawable.setPadding(dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize, dimensionPixelSize2);
        return new InsetDrawable((Drawable) shapeDrawable, dimensionPixelSize3, dimensionPixelSize4, dimensionPixelSize3, dimensionPixelSize4);
    }

    public static Drawable createImageViewBg(int i, int i2) {
        if (isApi_21_OrHigher()) {
            return createImageViewRippleBg(i, i2);
        }
        return createImageViewNormalBg(i2);
    }

    private static Drawable createImageViewRippleBg(int i, int i2) {
        return new RippleDrawable(ColorStateList.valueOf(i2), null, createImageViewShape(i));
    }

    private static Drawable createImageViewNormalBg(int i) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{R.attr.state_pressed}, createImageViewShape(i));
        stateListDrawable.addState(new int[0], new ColorDrawable(0));
        return stateListDrawable;
    }

    private static Drawable createImageViewShape(int i) {
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setColor(i);
        return shapeDrawable;
    }

    public static boolean isLayoutRtlCompat(View view) {
        return ViewCompat.getLayoutDirection(view) == 1;
    }

    public static Drawable createBgDrawable(int i, int i2, int i3, int i4, int i5) {
        float f = i2;
        float f2 = i3;
        float f3 = i4;
        float f4 = i5;
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{f, f, f2, f2, f3, f3, f4, f4}, null, null));
        shapeDrawable.getPaint().setColor(i);
        return shapeDrawable;
    }

    public static Drawable createOverflowButtonBg(int i) {
        if (isApi_21_OrHigher()) {
            return createOverflowButtonBgLollipop(i);
        }
        return createOverflowButtonBgBC(i);
    }

    private static Drawable createOverflowButtonBgLollipop(int i) {
        return new RippleDrawable(ColorStateList.valueOf(i), null, null);
    }

    private static Drawable createOverflowButtonBgBC(int i) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{R.attr.state_pressed}, createBgDrawable(i, 0, CORNER_RADIUS, 0, 0));
        stateListDrawable.addState(new int[0], new ColorDrawable(0));
        return stateListDrawable;
    }

    public static Calendar getCalendarForLocale(Calendar calendar, Locale locale) {
        if (calendar == null) {
            return Calendar.getInstance(locale);
        }
        long timeInMillis = calendar.getTimeInMillis();
        Calendar calendar2 = Calendar.getInstance(locale);
        calendar2.setTimeInMillis(timeInMillis);
        return calendar2;
    }

    public static ContextThemeWrapper createThemeWrapper(Context context, int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{i});
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, i2);
        typedArrayObtainStyledAttributes.recycle();
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(resourceId, new int[]{i3});
        int resourceId2 = typedArrayObtainStyledAttributes2.getResourceId(0, i4);
        typedArrayObtainStyledAttributes2.recycle();
        return new ContextThemeWrapper(context, resourceId2);
    }

    public static void setViewBackground(View view, int i, int i2) {
        if (isApi_21_OrHigher()) {
            view.setBackgroundColor(i);
        } else {
            setViewBackground(view, createBgDrawable(i, (i2 & 1) != 0 ? CORNER_RADIUS : 0, (i2 & 2) != 0 ? CORNER_RADIUS : 0, (i2 & 4) != 0 ? CORNER_RADIUS : 0, (i2 & 8) != 0 ? CORNER_RADIUS : 0));
        }
    }

    public static void setImageTintList(ImageView imageView, ColorStateList colorStateList) {
        if (isApi_21_OrHigher()) {
            imageView.setImageTintList(colorStateList);
            return;
        }
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            Drawable drawableWrap = DrawableCompat.wrap(drawable);
            DrawableCompat.setTintList(drawableWrap, colorStateList);
            imageView.setImageDrawable(drawableWrap);
        }
    }

    public static int[] resolveStateSet(int i) {
        return STATE_SETS[i];
    }

    public static boolean parseDate(String str, Calendar calendar) {
        if (str != null && !str.isEmpty()) {
            try {
                calendar.setTime(DATE_FORMATTER.parse(str));
                return true;
            } catch (ParseException unused) {
                Log.w(TAG, "Date: " + str + " not in format: " + DATE_FORMAT);
            }
        }
        return false;
    }

    private static boolean isLeapYear(int i) {
        return i > 1582 ? i % 4 == 0 && (i % 100 != 0 || i % 400 == 0) : i % 4 == 0;
    }

    public static int getDaysInMonth(int i, int i2) {
        switch (i) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return 31;
            case 1:
                return isLeapYear(i2) ? 29 : 28;
            case 3:
            case 5:
            case 8:
            case 10:
                return 30;
            default:
                throw new IllegalArgumentException("Invalid Month");
        }
    }

    public static void vibrateForDatePicker(View view) {
        view.performHapticFeedback(1);
    }

    public static void vibrateForTimePicker(View view) {
        view.performHapticFeedback(isApi_21_OrHigher() ? 4 : 1);
    }
}
