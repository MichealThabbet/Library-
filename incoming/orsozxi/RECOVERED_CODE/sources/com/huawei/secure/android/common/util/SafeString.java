package com.huawei.secure.android.common.util;

import android.util.Log;

/* loaded from: classes2.dex */
public class SafeString {
    private static final String a = "SafeString";
    private static final String b = "";

    public static String replace(String str, CharSequence charSequence, CharSequence charSequence2) {
        if (str != null && charSequence != null && charSequence2 != null) {
            try {
                return str.replace(charSequence, charSequence2);
            } catch (Exception e) {
                Log.e(a, "replace: " + e.getMessage());
            }
        }
        return str;
    }

    public static String substring(String str, int i) {
        if (str != null && str.length() >= i && i >= 0) {
            try {
                return str.substring(i);
            } catch (Exception e) {
                Log.e(a, "substring exception: " + e.getMessage());
            }
        }
        return "";
    }

    public static String substring(String str, int i, int i2) {
        if (str != null && i >= 0 && i2 <= str.length() && i2 >= i) {
            try {
                return str.substring(i, i2);
            } catch (Exception e) {
                Log.e(a, "substring: " + e.getMessage());
            }
        }
        return "";
    }
}
