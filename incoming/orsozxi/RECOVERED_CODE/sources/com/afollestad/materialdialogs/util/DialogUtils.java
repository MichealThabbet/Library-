package com.afollestad.materialdialogs.util;

import android.R;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.IBinder;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

/* loaded from: classes.dex */
public class DialogUtils {
    public static int getDisabledColor(Context context) {
        return adjustAlpha(isColorDark(resolveColor(context, R.attr.textColorPrimary)) ? ViewCompat.MEASURED_STATE_MASK : -1, 0.3f);
    }

    public static int adjustAlpha(int i, float f) {
        return Color.argb(Math.round(Color.alpha(i) * f), Color.red(i), Color.green(i), Color.blue(i));
    }

    public static int resolveColor(Context context, int i) {
        return resolveColor(context, i, 0);
    }

    public static int resolveColor(Context context, int i, int i2) {
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{i});
        try {
            return typedArrayObtainStyledAttributes.getColor(0, i2);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public static ColorStateList resolveActionTextColorStateList(Context context, int i, ColorStateList colorStateList) {
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{i});
        try {
            TypedValue typedValuePeekValue = typedArrayObtainStyledAttributes.peekValue(0);
            if (typedValuePeekValue == null) {
                return colorStateList;
            }
            if (typedValuePeekValue.type >= 28 && typedValuePeekValue.type <= 31) {
                return getActionTextStateList(context, typedValuePeekValue.data);
            }
            ColorStateList colorStateList2 = typedArrayObtainStyledAttributes.getColorStateList(0);
            return colorStateList2 != null ? colorStateList2 : colorStateList;
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public static ColorStateList getActionTextColorStateList(Context context, int i) throws Resources.NotFoundException {
        TypedValue typedValue = new TypedValue();
        context.getResources().getValue(i, typedValue, true);
        if (typedValue.type >= 28 && typedValue.type <= 31) {
            return getActionTextStateList(context, typedValue.data);
        }
        if (Build.VERSION.SDK_INT <= 22) {
            return context.getResources().getColorStateList(i);
        }
        return context.getColorStateList(i);
    }

    public static int getColor(Context context, int i) {
        return ContextCompat.getColor(context, i);
    }

    public static String resolveString(Context context, int i) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(i, typedValue, true);
        return (String) typedValue.string;
    }

    /* renamed from: com.afollestad.materialdialogs.util.DialogUtils$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$afollestad$materialdialogs$GravityEnum;

        static {
            int[] iArr = new int[GravityEnum.values().length];
            $SwitchMap$com$afollestad$materialdialogs$GravityEnum = iArr;
            try {
                iArr[GravityEnum.CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$afollestad$materialdialogs$GravityEnum[GravityEnum.END.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private static int gravityEnumToAttrInt(GravityEnum gravityEnum) {
        int i = AnonymousClass2.$SwitchMap$com$afollestad$materialdialogs$GravityEnum[gravityEnum.ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                return 0;
            }
        }
        return i2;
    }

    public static GravityEnum resolveGravityEnum(Context context, int i, GravityEnum gravityEnum) {
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{i});
        try {
            int i2 = typedArrayObtainStyledAttributes.getInt(0, gravityEnumToAttrInt(gravityEnum));
            if (i2 == 1) {
                return GravityEnum.CENTER;
            }
            if (i2 == 2) {
                return GravityEnum.END;
            }
            return GravityEnum.START;
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public static Drawable resolveDrawable(Context context, int i) {
        return resolveDrawable(context, i, null);
    }

    private static Drawable resolveDrawable(Context context, int i, Drawable drawable) {
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{i});
        try {
            Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(0);
            if (drawable2 != null || drawable == null) {
                drawable = drawable2;
            }
            return drawable;
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public static int resolveDimension(Context context, int i) {
        return resolveDimension(context, i, -1);
    }

    private static int resolveDimension(Context context, int i, int i2) {
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{i});
        try {
            return typedArrayObtainStyledAttributes.getDimensionPixelSize(0, i2);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public static boolean resolveBoolean(Context context, int i, boolean z) {
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{i});
        try {
            return typedArrayObtainStyledAttributes.getBoolean(0, z);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public static boolean resolveBoolean(Context context, int i) {
        return resolveBoolean(context, i, false);
    }

    public static boolean isColorDark(int i) {
        return 1.0d - ((((((double) Color.red(i)) * 0.299d) + (((double) Color.green(i)) * 0.587d)) + (((double) Color.blue(i)) * 0.114d)) / 255.0d) >= 0.5d;
    }

    public static void setBackgroundCompat(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT < 16) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }

    public static void showKeyboard(DialogInterface dialogInterface, final MaterialDialog.Builder builder) {
        final MaterialDialog materialDialog = (MaterialDialog) dialogInterface;
        if (materialDialog.getInputEditText() == null) {
            return;
        }
        materialDialog.getInputEditText().post(new Runnable() { // from class: com.afollestad.materialdialogs.util.DialogUtils.1
            @Override // java.lang.Runnable
            public void run() {
                materialDialog.getInputEditText().requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) builder.getContext().getSystemService("input_method");
                if (inputMethodManager != null) {
                    inputMethodManager.showSoftInput(materialDialog.getInputEditText(), 1);
                }
            }
        });
    }

    public static void hideKeyboard(DialogInterface dialogInterface, MaterialDialog.Builder builder) {
        InputMethodManager inputMethodManager;
        MaterialDialog materialDialog = (MaterialDialog) dialogInterface;
        if (materialDialog.getInputEditText() == null || (inputMethodManager = (InputMethodManager) builder.getContext().getSystemService("input_method")) == null) {
            return;
        }
        View currentFocus = materialDialog.getCurrentFocus();
        IBinder windowToken = null;
        if (currentFocus != null) {
            windowToken = currentFocus.getWindowToken();
        } else if (materialDialog.getView() != null) {
            windowToken = materialDialog.getView().getWindowToken();
        }
        if (windowToken != null) {
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
        }
    }

    public static ColorStateList getActionTextStateList(Context context, int i) {
        int iResolveColor = resolveColor(context, R.attr.textColorPrimary);
        if (i == 0) {
            i = iResolveColor;
        }
        return new ColorStateList(new int[][]{new int[]{-16842910}, new int[0]}, new int[]{adjustAlpha(i, 0.4f), i});
    }

    public static int[] getColorArray(Context context, int i) throws Resources.NotFoundException {
        if (i == 0) {
            return null;
        }
        TypedArray typedArrayObtainTypedArray = context.getResources().obtainTypedArray(i);
        int[] iArr = new int[typedArrayObtainTypedArray.length()];
        for (int i2 = 0; i2 < typedArrayObtainTypedArray.length(); i2++) {
            iArr[i2] = typedArrayObtainTypedArray.getColor(i2, 0);
        }
        typedArrayObtainTypedArray.recycle();
        return iArr;
    }

    public static <T> boolean isIn(T t, T[] tArr) {
        if (tArr != null && tArr.length != 0) {
            for (T t2 : tArr) {
                if (t2.equals(t)) {
                    return true;
                }
            }
        }
        return false;
    }
}
