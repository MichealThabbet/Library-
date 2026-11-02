package com.huawei.hms.hatool;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.ibm.icu.impl.locale.BaseLocale;
import java.util.Map;

/* loaded from: classes2.dex */
public class g0 {
    public static long a(Context context, String str, String str2, long j) {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            y.f("hmsSdk", "context is null or spName empty or spkey is empty");
            return j;
        }
        SharedPreferences sharedPreferencesB = b(context, str);
        return sharedPreferencesB != null ? sharedPreferencesB.getLong(str2, j) : j;
    }

    public static String a(Context context, String str, String str2, String str3) {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            y.f("hmsSdk", "context is null or spName empty or spkey is empty");
            return str3;
        }
        SharedPreferences sharedPreferencesB = b(context, str);
        return sharedPreferencesB != null ? sharedPreferencesB.getString(str2, str3) : str3;
    }

    public static Map<String, ?> a(Context context, String str) {
        return b(context, str).getAll();
    }

    public static void a(Context context, String str, String... strArr) {
        String str2;
        if (context == null || TextUtils.isEmpty(str)) {
            str2 = "clearData(): parameter error.context,spname";
        } else {
            if (strArr != null) {
                SharedPreferences sharedPreferencesB = b(context, str);
                if (sharedPreferencesB != null) {
                    SharedPreferences.Editor editorEdit = sharedPreferencesB.edit();
                    if (strArr.length == 0) {
                        editorEdit.clear();
                        editorEdit.commit();
                        return;
                    }
                    for (String str3 : strArr) {
                        if (sharedPreferencesB.contains(str3)) {
                            editorEdit.remove(str3);
                            editorEdit.commit();
                        }
                    }
                    return;
                }
                return;
            }
            str2 = "clearData(): No data need to be deleted,keys is null";
        }
        y.f("hmsSdk", str2);
    }

    public static SharedPreferences b(Context context, String str) {
        return context.getSharedPreferences(c(context, str), 0);
    }

    public static void b(Context context, String str, String str2, long j) {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            y.f("hmsSdk", "context is null or spName empty or spkey is empty");
            return;
        }
        SharedPreferences sharedPreferencesB = b(context, str);
        if (sharedPreferencesB != null) {
            SharedPreferences.Editor editorEdit = sharedPreferencesB.edit();
            editorEdit.putLong(str2, j);
            editorEdit.commit();
        }
    }

    public static void b(Context context, String str, String str2, String str3) {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            y.e("hmsSdk", "context is null or spName empty or spkey is empty");
            return;
        }
        SharedPreferences sharedPreferencesB = b(context, str);
        if (sharedPreferencesB != null) {
            SharedPreferences.Editor editorEdit = sharedPreferencesB.edit();
            editorEdit.putString(str2, str3);
            editorEdit.commit();
        }
    }

    public static String c(Context context, String str) {
        String packageName = context.getPackageName();
        String strN = c.n("_hms_config_tag", "oper");
        if (TextUtils.isEmpty(strN)) {
            return "hms_" + str + BaseLocale.SEP + packageName;
        }
        return "hms_" + str + BaseLocale.SEP + packageName + BaseLocale.SEP + strN;
    }
}
